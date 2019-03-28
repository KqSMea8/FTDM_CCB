package com.sunyard.sunfintech.billcheck.handler.task;

import com.sunyard.sunfintech.billcheck.handler.AllAccountHandler;
import com.sunyard.sunfintech.billcheck.pool.BillThreadPool;
import com.sunyard.sunfintech.billcheck.service.AccountClearService;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.List;

//import org.apache.log4j.LogManager;

/**
 * 清算账户资金
 *
 * import org.apache.log4j.LogManager;
 import org.apache.log4j.Logger;
 * Created by djh on 2017/11/23.
 */
public class AllAccountTask implements Runnable {


    List<AccountSubjectInfo> accountList;

    private AllAccountHandler handler;

    public AllAccountTask(List<AccountSubjectInfo> accountList,AllAccountHandler allAccountHandler){
        this.accountList = accountList;
        this.handler = allAccountHandler;
    }
    @Override
    public void run() {
        AccountClearService accountClearService = SpringContextHolder.getBean(AccountClearService.class);
        PlatformTransactionManager platformTransactionManager = SpringContextHolder.getBean(PlatformTransactionManager.class);
        //当有账户失败
        try {
            while (true){
                logger.info("当前线程:"+Thread.currentThread().getName()+"========本次清算账户数量："+accountList.size()+"===============");
                logger.info("当前线程池活跃线程数量:"+ BillThreadPool.getThreadPool().getPool().getActiveCount());

                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                final TransactionStatus transaction = platformTransactionManager.getTransaction(def);
                int i = 0;
                AccountSubjectInfo account = null;
                try {
                    for (;i<accountList.size();i++ ) {
                        account = accountList.get(i);
                        if (account.getN_balance().compareTo(BigDecimal.ZERO) > 0) {
                            accountClearService.clearFloat(account);
                        }
                    }
                    platformTransactionManager.commit(transaction);
                    handler.totolNum.addAndGet(accountList.size());

                    logger.info("总的已执行数量数量："+handler.totolNum.get());
                    break;
                } catch (Exception e) {
                    //当出现一个账户转账失败，移除该账户继续
                    if(account != null) accountList.remove(i);
                    logger.error("账户扣款失败：" + account, e);
                    platformTransactionManager.rollback(transaction);
                }
            }
        }finally {
            handler.getLatch().countDown();
        }
    }

    protected Logger logger = LogManager.getLogger(getClass());

}
