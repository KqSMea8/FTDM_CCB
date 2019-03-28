package com.sunyard.sunfintech.web.controller;

import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import com.sunyard.sunfintech.dao.entity.EaccUserinfoExample;
import com.sunyard.sunfintech.dao.mapper.EaccUserinfoMapper;
import com.sunyard.sunfintech.web.business.SearchBusiness;
import com.sunyard.sunfintech.web.model.vo.account.BatchRegisterRequest;
import com.sunyard.sunfintech.web.model.vo.account.BatchRegisterResponse;
import com.sunyard.sunfintech.web.threads.SingleThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by terry on 2017/8/18.
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    /*@Resource(name = "jmsQueueTemplate")
    private JmsTemplate jmsQueueTemplate;

    private static boolean runFlag;

    private static int count;

    @Resource(name = "searchBusiness")
    private SearchBusiness searchBusiness;

    @RequestMapping("/mq_option")
    public String batchRegister(HttpServletRequest httpServletRequest, boolean run){

        runFlag=run;
        if(run){
            count=0;
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    startSend();
                }
            });
        }
        return "success";
    }

    public void startSend(){
        while (runFlag){
            String destination="TestQueue";
            jmsQueueTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    logger.info("【测试队列】============发送消息");
                    Message message=jmsQueueTemplate.getMessageConverter().toMessage("测试消息"+(count++), session);
                    logger.info("【测试队列】============消息发送成功！");
                    return message;
                }
            });
        }
    }*/


}


