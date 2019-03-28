package com.sunyard.sunfintech.billcheck.handler.task;

import com.sunyard.sunfintech.billcheck.handler.AccountUpdateHandler;
import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.service.AccountClearService;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 清算账户资金
 * Created by djh on 2017/11/23.
 */
public class AccountTask implements Runnable {


    private ClearModel clearModel;

    private List<ClearAccount> accountList;

    private ConcurrentHashMap<String,ClearAccount> accounts;


    private AccountUpdateHandler handler;

    public AccountTask(List<ClearAccount> accountList, ClearModel clearModel, AccountUpdateHandler handler){
        this.accountList = accountList;
        this.clearModel = clearModel;
        accounts = clearModel.getAccounts();
        this.handler = handler;
    }
    @Override
    public void run() {
        AccountClearService accountClearService = SpringContextHolder.getBean(AccountClearService.class);
        PlatformTransactionManager platformTransactionManager = SpringContextHolder.getBean(PlatformTransactionManager.class);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        final TransactionStatus transaction = platformTransactionManager.getTransaction(def);
        backups();//清算前备份，出错后好回滚
        try {
            for (ClearAccount account : accountList){
                if(account.getF_balance().compareTo(BigDecimal.ZERO) > 0){
                    int fFlag = accountClearService.clearF_balance(account, clearModel);
                    if (fFlag == 1) {
                        account.removeF();
                    }
                }
                if(account.getN_balance().compareTo(BigDecimal.ZERO) > 0) {
                    int nFlag = accountClearService.clearN_balance(account, clearModel);
                    if (nFlag == 1) {
                        account.removeN();
                    }
                }

            }
            platformTransactionManager.commit(transaction);
        }catch (Exception e){
            logger.error("账户扣款失败："+accountList,e);
            recovers();//内存数据回滚
            platformTransactionManager.rollback(transaction);//数据回滚
        }finally {
            handler.getLatch().countDown();
        }
    }

    private void backups(){
        for (ClearAccount account : accountList){
            account.backup();
        }
    }
    private void recovers(){
        for (ClearAccount account : accountList){
            account.recover();
        }
    }

    protected Logger logger = LogManager.getLogger(getClass());

}
