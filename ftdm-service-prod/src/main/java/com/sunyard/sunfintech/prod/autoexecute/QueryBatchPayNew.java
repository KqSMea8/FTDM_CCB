package com.sunyard.sunfintech.prod.autoexecute;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.BatchPayQueryResponse;
import com.sunyard.sunfintech.account.model.bo.BatchPayQueryResponseDataDetail;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.prod.job.CronDateUtils;
import com.sunyard.sunfintech.prod.provider.IProductRefundService;
import com.sunyard.sunfintech.prod.quartz.QuartzJob;
import com.sunyard.sunfintech.prod.quartz.QuartzJobManager;
import com.sunyard.sunfintech.prod.utils.ProductPublicParams;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by terry on 2017/7/20.
 */
public class QueryBatchPayNew extends BaseServiceSimple implements Job {

    @Autowired
    private QuartzJobManager quartzJobManager;

    @Autowired
    private IAccountTransferThirdParty accountTransferThirdParty;

    @Autowired
    private IProductRefundService productRefundService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("【批量代付查询】===========开始查询，两分钟查询一次，HashCode="+this.getClass().hashCode()+",运行时间："+DateUtils.todayStr());

        //job名称
        String jobName = context.getJobDetail().getKey().getName();
        //根据job名称得到数据
        QuartzJob quartzJob = (QuartzJob)context.getJobDetail().getJobDataMap().get(jobName);
        Map<String,Object> dataMap=quartzJob.getData();
        String trans_serial= (String) dataMap.get("trans_serial");
        String mall_no= (String) dataMap.get("mall_no");
        String plat_no=(String) dataMap.get("plat_no");
        logger.info("【批量代付查询】===========查询流水号："+trans_serial+"，查询时间："+DateUtils.todayStr());
        String openFlag="OPEN_QUERY_"+trans_serial;
        try{
            if(!CacheUtil.getCache().setnx(openFlag,this.getClass().hashCode())){
                logger.info("【批量代付查询】===========查询流水号："+trans_serial+"，已有job在查询，本次job不执行。");
                return;
            }
            //更新最后查询时间
            Object valueObj=CacheUtil.getCache().getAndNotSetAlive(ProductPublicParams.waitQueryOrder+trans_serial);
            Map<String,Object> queryMap=null;
            if(valueObj!=null){
                String valueStr=String.valueOf(valueObj);
                queryMap=JSON.parseObject(valueStr);
                queryMap.put("lastTime",(new Date()).getTime());
                CacheUtil.getCache().set(ProductPublicParams.waitQueryOrder+trans_serial,JSON.toJSONString(queryMap));
            }else {
                queryMap=new HashMap<>();
                queryMap.put("trans_serial",trans_serial);
                queryMap.put("mall_no",mall_no);
                queryMap.put("plat_no",plat_no);
                queryMap.put("startTime",(new Date()).getTime());
                queryMap.put("lastTime",(new Date()).getTime());
                CacheUtil.getCache().set(ProductPublicParams.waitQueryOrder+trans_serial,JSON.toJSONString(queryMap));
                CacheUtil.getCache().addToSet(ProductPublicParams.fundPayWaitQueryListKey,trans_serial);
            }

            Map<String,Object> queryParams=new HashMap<>();
            queryParams.put("partner_id","10000001");
            queryParams.put("partner_serial_no", SeqUtil.getSeqNum());
            queryParams.put("partner_trans_date", DateUtils.getyyyyMMddDate());
            queryParams.put("partner_trans_time",DateUtils.getHHmmssTime());
            queryParams.put("original_serial_no",trans_serial);
            BatchPayQueryResponse response=accountTransferThirdParty.batchPayQuery(queryParams,mall_no);
            if(response.getData()==null){
                logger.info("【批量代付查询】===========流水"+trans_serial+"查询失败："+response.getError_info());
//                if("013033".equals(response.getError_code())){
                //无此原交易
//                    long dateBetween=(Long.valueOf(String.valueOf(queryMap.get("lastTime")))-(Long.valueOf(String.valueOf(queryMap.get("startTime")))))/60000;
                long dateBetween=((long)queryMap.get("lastTime")-((long)queryMap.get("startTime")))/60000;
                if(dateBetween>=30){
                    //订单时间超过30分钟，订单置为状态未知
                    productRefundService.executeFailedRefund(trans_serial,plat_no);
                    //删除相关redis
                    CacheUtil.getCache().removeFromSet(ProductPublicParams.fundPayWaitQueryListKey, trans_serial);
                    CacheUtil.getCache().del(ProductPublicParams.waitQueryOrder+trans_serial);
                    //开启定时查询失败订单
                    CacheUtil.getCache().addToSet(ProductPublicParams.batchPayUnknownList,trans_serial+","+mall_no);
                }else{
                    String time = CronDateUtils.getCron(jobName);
                    logger.info("【批量代付查询】===========流水"+trans_serial+"查询结果无此原订单，开启job，继续查询，开启时间"
                            +DateUtils.todayStr()+"，下次开启时间："+time);
                    quartzJob.setCronExpression(time);
                    quartzJobManager.updateQuartzJob(quartzJob);
                }
//                }
            }else{
                logger.info("【批量代付查询】===========流水"+trans_serial+"查询成功，检查数据状态是否都为终态");
                List<BatchPayQueryResponseDataDetail> detailList=response.getData().get(0).getDetail();
                logger.info("【批量代付查询】===========流水"+trans_serial+"的detailList："+JSON.toJSONString(detailList));
                int endStatus=0;
                for(BatchPayQueryResponseDataDetail detail:detailList){
                    if("2".equals(detail.getPay_status())){
                        break;
                    }
                    endStatus++;
                }
                if(endStatus==detailList.size()){
                    //如果全部为终态，交易成功执行，结束job
                    //执行还款交易
                    logger.info("【批量代付查询】===========流水"+trans_serial+"的所有明细都为终态，执行还款");
                    boolean success=productRefundService.executeBatchRefund(detailList,trans_serial);
                    if(success){
                        logger.info("【批量代付查询】===========流水"+trans_serial+"还款全部执行成功，删除job，删除时间"+DateUtils.todayStr());
                        quartzJobManager.deleteJob(quartzJob);
                        //查询完成之后，删除查询缓存
                        CacheUtil.getCache().removeFromSet(ProductPublicParams.fundPayWaitQueryListKey, trans_serial);
                        CacheUtil.getCache().del(ProductPublicParams.waitQueryOrder+trans_serial);
                    }else{
                        //有失败的还款，重新开启job
                        String time = CronDateUtils.getCron(jobName);
                        logger.info("【批量代付查询】===========流水"+trans_serial+"有失败的还款，重新开启job，开启时间"
                                +DateUtils.todayStr()+"，下次开启时间："+time);
                        quartzJob.setCronExpression(time);
                        quartzJobManager.updateQuartzJob(quartzJob);
                    }
                }else{
                    //查询结果有非终态，开启job，继续查询
                    String time = CronDateUtils.getCron(jobName);
                    logger.info("【批量代付查询】===========流水"+trans_serial+"查询结果有非终态，开启job，继续查询，开启时间"
                            +DateUtils.todayStr()+"，下次开启时间："+time);
                    quartzJob.setCronExpression(time);
                    quartzJobManager.updateQuartzJob(quartzJob);
                }
            }
        }catch (Exception ex){
            logger.error("【批量代付查询】===========异常：",ex);
            String time = CronDateUtils.getCron(jobName);
            logger.info("【批量代付查询】===========流水"+trans_serial+"还款异常，重新开启job，开启时间"
                    +DateUtils.todayStr()+"，下次开启时间："+time);
            quartzJob.setCronExpression(time);
            quartzJobManager.updateQuartzJob(quartzJob);
        }finally {
            logger.info("【批量代付查询】===========查询流水号："+trans_serial+"，job执行完成，删除job开启标志："+openFlag);
            CacheUtil.getCache().del(openFlag);
        }

    }
}
