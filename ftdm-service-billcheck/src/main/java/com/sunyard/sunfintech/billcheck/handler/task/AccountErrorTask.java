package com.sunyard.sunfintech.billcheck.handler.task;

import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.service.AccountClearService;
import com.sunyard.sunfintech.core.util.SpringContextHolder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 添加错误账户
 * Created by djh on 2017/11/23.
 */
public class AccountErrorTask implements Runnable {


    private ClearModel clearModel;

    private ClearAccount account;

    private ConcurrentHashMap<String,ClearAccount> accounts;

    public AccountErrorTask(ClearAccount account, ClearModel clearModel){
        this.account = account;
        this.clearModel = clearModel;
        accounts = clearModel.getAccounts();
    }
    @Override
    public void run() {
        //无事务，异步
        AccountClearService accountClearService = SpringContextHolder.getBean(AccountClearService.class);
        try {
            accountClearService.insertClearCheckError(account,clearModel);
            accounts.remove(account.getKey());
        }catch (Exception e){
            logger.error("插入清算差错账户出错："+account,e);
        }
    }

    protected Logger logger = LogManager.getLogger(getClass());

}
