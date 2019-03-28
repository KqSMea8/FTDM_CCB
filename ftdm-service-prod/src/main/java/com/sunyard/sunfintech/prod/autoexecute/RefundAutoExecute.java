package com.sunyard.sunfintech.prod.autoexecute;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.prod.job.RefundJobAction;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepay;
import com.sunyard.sunfintech.prod.model.bo.BatchCustRepayOld;
import com.sunyard.sunfintech.prod.provider.IProductRefundService;
import com.sunyard.sunfintech.prod.utils.ProductPublicParams;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * Created by terry on 2017/7/12.
 */
@CacheConfig(cacheNames="refundAutoExecute")
@org.springframework.stereotype.Service("refundAutoExecute")
public class RefundAutoExecute extends BaseServiceSimple {
    private static final String refundListKey="refundList";
    private static final String refundListenerKey="refundListenerOpenFlag";
    private static final String waitRefundFlagKey="waitRefundFlag";
    private static final long waitTime=5000;//等待时间1分钟，60000毫秒
    private static final long maxWaitTime=60000;//最大等待时间15分钟，900000毫秒
    private static final Integer aliveTime=90;//心跳为一分半钟
    private static Map<String,Long> waitedTime= new HashMap<>();//已等待时间
    private static boolean startFlag=false;

    @Autowired
    private IProductRefundService productRefundService;

    @Resource(name = "refundJobAction")
    private RefundJobAction refundJobAction;

    @Autowired
    private ISysParameterService sysParameterService;

    /**
     * 初始化还款监听
     */
    public void initExecute(){
        if(!startFlag){
            logger.info("【还款监听】========初始化监听");
            //启动还款监听
            searchRefundList();
            logger.info("【还款监听】========监听初始化完成");
            startFlag=true;
        }
    }

    /**
     * 还款监听
     */
    public void searchRefundList(){
        Timer timer = new Timer("searchRefundList", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    //查询开启标识
                    Object object=CacheUtil.getCache().getAndNotSetAlive(refundListenerKey);
                    String openFlag= null ;
                    if(object!=null){
                        openFlag=String.valueOf(object);
                    }
                    if(openFlag==null || openFlag.equals(String.valueOf(this.getClass().hashCode()))){

                        if(openFlag==null){
                            //切换服务时重置触发点
                            String newMaxRepayNumStr=sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.PROD_REFUND_MAX_NUM);
                            if(StringUtils.isNotBlank(newMaxRepayNumStr)){
                                ProductPublicParams.setMaxRepayNum(Long.parseLong(newMaxRepayNumStr));
                            }
                            //切换一借多贷合成数据最大线程数
                            String newMaxRepayThreadsNumStr=sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.PROD_REFUND_MAX_THREADS_NUM);
                            if(StringUtils.isNotBlank(newMaxRepayThreadsNumStr)){
                                ProductPublicParams.setMaxRepayThreadsNum(Long.parseLong(newMaxRepayThreadsNumStr));
                            }
                        }

                        //查询转账状态查询缓存,重新开启死亡的job
                        Set<Serializable> queryList= CacheUtil.getCache().getFromSet(ProductPublicParams.fundPayWaitQueryListKey);
                        logger.info("【还款监听】========待确认转账数量："+queryList.size());
                        for(Serializable obj:queryList){
                            String queryKey=String.valueOf(obj);
                            Object valueObj=CacheUtil.getCache().get(queryKey);
                            if(valueObj!=null){
                                String valueStr=String.valueOf(valueObj);
                                Map<String,Object> queryMap= JSON.parseObject(valueStr);
                                String lastTimeStr=String.valueOf(queryMap.get("lastTime"));
                                Long lastTime=DateUtils.parseDate(lastTimeStr).getTime();
                                Long nowTime=(new Date()).getTime();
                                if(nowTime-lastTime>21000){//死亡时间为3分半钟
                                    refundJobAction.startQueryBatchPayNew(String.valueOf(queryMap.get("trans_serial")),String.valueOf(queryMap.get("mall_no")),String.valueOf(queryMap.get("plat_no")));
                                }
                            }else{
                                CacheUtil.getCache().removeFromSet(ProductPublicParams.fundPayWaitQueryListKey,queryKey);
                            }
                        }

                        if(openFlag==null){
                            CacheUtil.getCache().set(refundListenerKey,this.getClass().hashCode(),aliveTime);
                        }

                        logger.info("【还款监听】========开始扫描还款列表数量："+ DateUtils.getTime());
                        long listSize=CacheUtil.getCache().getSetSize(refundListKey);
                        logger.info("【还款监听】========当前还款队列数量："+listSize);
                        Set<Serializable> refundList=CacheUtil.getCache().getFromSet(refundListKey);
                        for(Serializable refundListItem:refundList){
                            String refundListItemKeyName=String.valueOf(refundListItem);
//                            Long leftWaitedTime= MapUtils.getLong(waitedTime,refundListItemKeyName,0L);
//                            if(0L==leftWaitedTime){
//                                //leftWaitedTime=0L;
//                                waitedTime.put(refundListItemKeyName,leftWaitedTime);
//                            }
//                            Long nowWaitedTime=waitedTime.get(refundListItemKeyName)+waitTime;
                            long refundListItemSize= CacheUtil.getCache().size(refundListItemKeyName);
//                            logger.info("【还款监听】========当前等待时间："+nowWaitedTime+"，最大等待时间："+maxWaitTime+"，当前队列："+refundListItemKeyName+"，队列元素数量："+refundListItemSize+"，最大还款数量："+ProductPublicParams.maxRepayNum);
//                            if(refundListItemSize>= ProductPublicParams.maxRepayNum || (nowWaitedTime>maxWaitTime && refundListItemSize>0)){
                                //计算最大线程数
                                Double needThreadDouble=Math.ceil((double) refundListItemSize / (double) ProductPublicParams.maxRepayNum)*1.4;
                                Long needThreadNum=Math.round(needThreadDouble);
                                if(needThreadNum>ProductPublicParams.maxRepayThreadsNum){
                                    needThreadNum=ProductPublicParams.maxRepayThreadsNum;
                                }
                                logger.info("【还款监听】========开始处理队列，需要线程数：{}",needThreadNum);
                                productRefundService.getBatchEaccountTransData(refundListItemKeyName,ProductPublicParams.maxRepayNum,ProductPublicParams.maxRepayThreadsNum,needThreadNum);
//                                if(nowWaitedTime>maxWaitTime){
//                                    nowWaitedTime=0L;
//                                }
//                            }
//                            waitedTime.put(refundListItemKeyName,nowWaitedTime);
                        }
                    }
                }catch (Exception e){
                    logger.info("【还款监听】========异常："+e.getMessage());
                }
            }
        }, waitTime,waitTime);

    }
}
