package com.sunyard.sunfintech.prod.listener;

import com.sunyard.sunfintech.prod.autoexecute.RefundAutoExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by terry on 2017/7/12.
 */
@Service
public class RefundListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource(name = "refundAutoExecute")
    private RefundAutoExecute refundAutoExecute;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            createRefundAutoExecute();
        }

    }

    public void createRefundAutoExecute(){
        //开启扫描
        refundAutoExecute.initExecute();;
    }
}
