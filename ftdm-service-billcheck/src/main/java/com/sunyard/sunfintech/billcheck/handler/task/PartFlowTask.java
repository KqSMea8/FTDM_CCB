package com.sunyard.sunfintech.billcheck.handler.task;

import com.sunyard.sunfintech.core.util.SpringContextHolder;
import com.sunyard.sunfintech.custdao.mapper.CustEaccAccountamtlistMapper;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * 更新资金流水为部分清算
 * Created by djh on 2017/11/23.
 */
public class PartFlowTask implements Runnable {

    public static void main(String[] args) {
        System.out.println("Hello world");
    }

    private List<EaccAccountamtlist> list ;

    public PartFlowTask(List<EaccAccountamtlist> list){
        this.list = list;
    }
    @Override
    public void run() {
        CustEaccAccountamtlistMapper custEaccAccountamtlistMapper = SpringContextHolder.getBean(CustEaccAccountamtlistMapper.class);
        PlatformTransactionManager platformTransactionManager = SpringContextHolder.getBean(PlatformTransactionManager.class);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        final TransactionStatus transaction = platformTransactionManager.getTransaction(def);
        try {
            for(EaccAccountamtlist eacc:list) {
                custEaccAccountamtlistMapper.updatePartSwitch(eacc);
            }
            platformTransactionManager.commit(transaction);
        }catch (Exception e){
            logger.error("==========批次执行失败："+ list.toString(),e);
            platformTransactionManager.commit(transaction);
        }
    }
    protected Logger logger = LogManager.getLogger(getClass());

}
