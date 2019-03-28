package com.sunyard.sunfintech.listcheck.task;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.dic.DealStatus;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MathUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;
import com.sunyard.sunfintech.dao.mapper.TransTransreqMapper;
import com.sunyard.sunfintech.listcheck.service.IListCheckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by terry on 2018/7/9.
 */
@Component
public class ListCheckTask {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private static Integer num=0;
    private static String lastDate=DateUtils.getyyyyMMddDate();
    private final static String taskCacheKey="ListCheckSearchMinId";
    @Value("${listcheck.querySize}")
    private Integer querySize;
    @Value("${listcheck.maxThread}")
    private Integer maxThread;
    @Value("${listcheck.trans_codes}")
    private String trans_codes;
    @Value("${listcheck.start_id}")
    private Integer start_id;
    private Map<String,Boolean> trans_code_map=new TreeMap<>();
    private static Map<String,String> threadMap=new HashMap<>();
    private static Integer nowThreadNum=0;

    @Autowired
    private TransTransreqMapper transTransreqMapper;

    @Autowired
    private IListCheckService listCheckService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scannerJob(){
        String nowDate=DateUtils.getyyyyMMddDate();
        if(!nowDate.equals(lastDate)){
            lastDate=nowDate;
            num=0;
        }
        Integer taskNum=Integer.valueOf(num++);
        logger.info("{}第{}次任务开始执行，当前时间：{}",DateUtils.getyyyyMMddDate(),taskNum, DateUtils.getHHmmssTime());
        //计算线程数
        Integer needThreadNum=0;
        Integer maxId=getTransreqMaxId();
        //获取起始查询ID
        Integer minId=(int)CacheUtil.getCache().increment(taskCacheKey,0);
        if(minId==0 && start_id!=0){
            minId=start_id;
            CacheUtil.getCache().increment(taskCacheKey,start_id);
        }
        Integer needTimes=getLeftTime()/2;
        needThreadNum=(int)Math.ceil((maxId-minId)/new Double(needTimes));
        if(needThreadNum>maxThread){
            needThreadNum=maxThread;
        }
        Integer openNewThreadNum=needThreadNum-nowThreadNum;
        logger.info("{}第{}次任务开始执行，当前需要{}个线程，需新开启{}个线程",DateUtils.getyyyyMMddDate(),taskNum,needThreadNum,openNewThreadNum);
        if(openNewThreadNum>=0){
            for(int threadNum=0;threadNum<openNewThreadNum;threadNum++){
                SingleThreadPool.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("线程{}开启，开启时间：{}，当前线程数：{}",Thread.currentThread().getName(),DateUtils.curTimeStr(),addThreadNum());
                        threadMap.put(Thread.currentThread().getName(), DealStatus.ACTIVATED.getCode());
                        while (DealStatus.ACTIVATED.getCode().equals(threadMap.get(Thread.currentThread().getName()))){
                            try {
                                //计算查询起始ID
                                Integer endId=(int)CacheUtil.getCache().increment(taskCacheKey,querySize);
                                Integer startId=endId---querySize;

                                List<TransTransreq> transTransreqList=queryTrans(startId,endId);
                                if(transTransreqList.size()>0){
                                    List<TransTransreq> removeList=new ArrayList<>();
                                    for(TransTransreq transTransreq:transTransreqList){
                                        //去除批次订单
                                        if(StringUtils.isBlank(transTransreq.getOrder_no()) || transTransreq.getOrder_no().equals(transTransreq.getBatch_order_no())){
                                            logger.info("线程{},订单号{}为批次订单，移除",Thread.currentThread().getName(),transTransreq.getOrder_no());
                                            removeList.add(transTransreq);
                                        }
                                        //去除非资金订单
                                        if(!checkTransCode(transTransreq.getTrans_code())){
                                            logger.info("线程{},订单号{}为非资金订单，移除",Thread.currentThread().getName(),transTransreq.getOrder_no());
                                            removeList.add(transTransreq);
                                        }
                                    }
                                    //移除不需要的订单
                                    transTransreqList.removeAll(removeList);
                                    if(transTransreqList.size()>0){
                                        logger.info("线程{},执行对账操作，当前数据条数：{}",Thread.currentThread().getName(),transTransreqList.size());
                                        listCheckService.doCheck(transTransreqList);
                                    }

                                }
                                Thread.sleep(200);
                            } catch (Exception e) {
                                logger.error("线程{}异常：",Thread.currentThread().getName(),e);
                            }
                        }
                        threadMap.remove(Thread.currentThread().getName());
                        logger.info("线程{}结束，结束时间：{}，当前线程数：{}",Thread.currentThread().getName(),DateUtils.curTimeStr(),subThreadNum());
                    }
                });
            }
        }else{
            int threadNum=0;
            for (String key:threadMap.keySet()){
                if(threadNum>openNewThreadNum){
                    threadMap.put(key,DealStatus.UNACTIVATED.getCode());
                    threadNum--;
                }else {
                    break;
                }
            }
        }
        logger.info("{}第{}次任务执行结束，当前时间：{}",DateUtils.getyyyyMMddDate(),taskNum, DateUtils.getHHmmssTime());
    }

    private List<TransTransreq> queryTrans(Integer minId,Integer maxId){
        TransTransreqExample example=new TransTransreqExample();
        TransTransreqExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andService_nameNotEqualTo("account");
        criteria.andIdBetween(minId, maxId);
        return transTransreqMapper.selectByExample(example);
    }

    private Integer getTransreqMaxId(){
        TransTransreqExample example=new TransTransreqExample();
        TransTransreqExample.Criteria criteria=example.createCriteria();
        criteria.andEnabledEqualTo(Constants.ENABLED);
        example.setOrderByClause(" id desc limit 1");
        List<TransTransreq> transreqList=transTransreqMapper.selectByExample(example);
        return transreqList.get(0).getId();
    }

    private static int getLeftTime(){
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int)(tommorowDate.getTimeInMillis() - curDate .getTimeInMillis()) / 1000;
    }

    private synchronized Integer addThreadNum(){
        return ++nowThreadNum;
    }

    private synchronized Integer subThreadNum(){
        return --nowThreadNum;
    }

    private boolean checkTransCode(String trans_code){
        if(trans_code_map.size()<=0){
            initTransCodeMap();
        }
        return trans_code_map.get(trans_code)!=null;
    }

    private synchronized void initTransCodeMap(){
        String[] transCodeArrays=trans_codes.split(",");
        if(trans_code_map.size()==transCodeArrays.length){
            return;
        }
        for(String trans_code:transCodeArrays){
            trans_code_map.put(trans_code,true);
        }
    }
}
