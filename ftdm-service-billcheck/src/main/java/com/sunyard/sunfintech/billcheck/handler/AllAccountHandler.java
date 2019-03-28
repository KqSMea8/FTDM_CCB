package com.sunyard.sunfintech.billcheck.handler;


import com.sunyard.sunfintech.billcheck.handler.task.AllAccountTask;
import com.sunyard.sunfintech.billcheck.pool.BillThreadPool;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 查询管理
 * Created by djh on 2017/11/22.
 */
public class AllAccountHandler {
    public AtomicInteger totolNum =  new AtomicInteger(0);

    private QueryListHandler queryListHandler;

    private static  final  int BATCH_SUBMIT_COUNT = 200;//一次提交数量

    public CountDownLatch getLatch() {
        return latch;
    }

    private volatile CountDownLatch latch = null;//new CountDownLatch(2);
    //private int tryCount = 0;
    //private static  final  int MAX_TRY_COUNT = 3;

    public AllAccountHandler(QueryListHandler queryListHandler){
        this.queryListHandler = queryListHandler;
    }

    /**
     * for(int i=0;i<state.length;i++){
     for(EaccAccountamtlist eacc:nodes.get(i).getHandData()){
     allSwitch.add(eacc.getId());
     if(allSwitch.size()==BATCH_SUBMIT_COUNT){
     BillThreadPool.getThreadPool().addTask(new FinishedFlowTask(allSwitch));
     allSwitch = new ArrayList<>();//每一千条提交一次
     }
     }
     }
     */
    public void asynExecute() {
        final BillThreadPool pool = BillThreadPool.getThreadPool();
        long accountSize = queryListHandler.getAllElement();
        long taskNum = accountSize/BATCH_SUBMIT_COUNT + 1;
        latch = new CountDownLatch((int)taskNum);
        List<AccountSubjectInfo> accountList = new ArrayList<>();
        for (int i=0;i<queryListHandler.getNodes().size();i++) {
            List<AccountSubjectInfo> nodes = queryListHandler.getNodes().get(i).getHandData();
            logger.info(i+ " nodes size:" + nodes.size());
            for(AccountSubjectInfo account:nodes){
                accountList.add(account);
                if(accountList.size()==BATCH_SUBMIT_COUNT){
                    pool.addTask(new AllAccountTask(accountList,this));
                    accountList = new ArrayList();
                }
            }
            logger.info(i+ " nodes size end:" + nodes.size());

        }
        logger.info("addTask...");
        pool.addTask(new AllAccountTask(accountList,this));
        try {
            logger.info("begin wait...");
            latch.await(10, TimeUnit.MINUTES);//锁定主线程，等待所有任务执行完
        } catch (InterruptedException e) {
            logger.error("线程锁定超时",e);
        }

    }
    protected Logger logger = LogManager.getLogger(getClass());

}
