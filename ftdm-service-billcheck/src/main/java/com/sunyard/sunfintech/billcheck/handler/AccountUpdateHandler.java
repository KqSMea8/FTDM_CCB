package com.sunyard.sunfintech.billcheck.handler;


import com.sunyard.sunfintech.billcheck.handler.task.AccountErrorTask;
import com.sunyard.sunfintech.billcheck.handler.task.AccountTask;
import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.pool.BillThreadPool;
import com.sunyard.sunfintech.billcheck.service.ClearServiceFor51;
import com.sunyard.sunfintech.core.dic.Ssubject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 查询管理
 * Created by djh on 2017/11/22.
 */
public class AccountUpdateHandler {

    private ClearModel clearModel;

    private static  final  int BATCH_SUBMIT_COUNT = 200;//一次提交数量

    public CountDownLatch getLatch() {
        return latch;
    }

    private volatile CountDownLatch latch = null;//new CountDownLatch(2);
    //private int tryCount = 0;
    //private static  final  int MAX_TRY_COUNT = 3;

    public AccountUpdateHandler(ClearModel clearModel){
        this.clearModel = clearModel;
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
        ConcurrentHashMap<String, ClearAccount> accounts = clearModel.getAccounts();
        final BillThreadPool pool = BillThreadPool.getThreadPool();
        //先删除不需要清算的账户
        for (final Map.Entry<String, ClearAccount> entry : accounts.entrySet()) {
            if(entry.getValue().getT_balance().compareTo(BigDecimal.ZERO) == 0){
                accounts.remove(entry.getKey());
            }
            //垫付金额统计，行内或平台垫付统一记成垫付金额
            String sub_subject = entry.getValue().getSub_subject();
            if(Ssubject.INLINEPAYMENT.getCode().equals(sub_subject) || ClearServiceFor51.advanceSubject.contains(sub_subject) ){
                clearModel.addAdvance(entry.getValue().getT_balance());
            }
        }
        long taskNum = accounts.size()/BATCH_SUBMIT_COUNT + 1;
        latch = new CountDownLatch((int)taskNum);
        List<ClearAccount> accountList = new ArrayList<>();
        for (final Map.Entry<String, ClearAccount> entry : accounts.entrySet()) {
            accountList.add(entry.getValue());
            if(accountList.size()==BATCH_SUBMIT_COUNT){
                pool.addTask(new AccountTask(accountList,clearModel,this));
                accountList = new ArrayList<>();
            }
        }
        pool.addTask(new AccountTask(accountList,clearModel,this));
        try {
            latch.await();//锁定主线程，等待所有任务执行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //删除清算成功的账户
        for (final Map.Entry<String, ClearAccount> entry1 : accounts.entrySet()) {
            ClearAccount account = entry1.getValue();
            if(account.getT_balance().compareTo(BigDecimal.ZERO) == 0){
                accounts.remove(entry1.getKey());
            }else {
                if(account.isNoNeedToTry()){
                    BillThreadPool.getThreadPool().addTask(new AccountErrorTask(entry1.getValue(),clearModel));
                }else {
                    //垫付金额回滚，会进入这分支的情况：清算未成功，并且继续重试清算的时候
                    String sub_subject = entry1.getValue().getSub_subject();
                    if(Ssubject.INLINEPAYMENT.getCode().equals(sub_subject) || ClearServiceFor51.advanceSubject.contains(sub_subject)){
                        clearModel.addAdvance(entry1.getValue().getT_balance().negate());//垫付清算失败，回滚垫付
                    }
                }
            }
        }
    }
}
