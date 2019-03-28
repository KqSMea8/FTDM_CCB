package com.sunyard.sunfintech.web.listener;

import com.sunyard.sunfintech.web.business.NotifyBusiness;
import com.sunyard.sunfintech.web.threads.SingleThreadPool;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * Created by PengZY on 2017/6/22.
 */
@org.springframework.stereotype.Service
public class StartupListener  implements ApplicationListener<ContextRefreshedEvent> {

    @Resource(name = "notifyBusiness")
    private NotifyBusiness notifyBusiness;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if ( event.getApplicationContext().getParent() == null) {

            System.out.println("spring启动完成开始启动job");

            //向E支付发送订单查询
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    //TODO yanglei notifyBusiness.queryRwWithdraw();
                }
            });

            //代付
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                 @Override
                 public void run() {
                     //TODO yanglei notifyBusiness.queryRwWithdrawUpdatePayStatus();
                 }
            });
        }
    }

}
