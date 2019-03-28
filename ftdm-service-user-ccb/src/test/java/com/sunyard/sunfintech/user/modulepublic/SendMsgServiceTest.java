package com.sunyard.sunfintech.user.modulepublic;

import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.pub.provider.ISendMsgService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by terry on 2018/3/29.
 */
public class SendMsgServiceTest {
    @Test
    public void sendMail() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        ISendMsgService sendMsgService=context.getBean(ISendMsgService.class);
        sendMsgService.sendMail("大河向东流啊，天上的星星参北斗啊，啊啊啊啊啊啊啊啊。。。","邮件队列测试");
    }

    @Test
    public void testHttp(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        Map<String,Object> params = new HashMap<>();
        params.put("sign","JHQXGpKWuI6zyouWXPDzNIYuR1bbVs28+Kjqco8WxbnmW66s30LvWpxGbUw7ICWeJzic9tDsZWP8eawXkIqy2+T0bLsgWcqXUelMwf9oyxjUYlJbM6FqYzWf/h23Gi/wJZ41yN9/EHHAvxCVB0BOZ9tDu9rGO+Jb7q39pq+3yoReCKdgVcIczu6MxXNFi/uWy3sTFm7haYfPeK97nryRTJQRaFoEpRNQWHfKD3LbXaFHGwNRo81sj9ZiYm4dLszDxqMpUj6ZciK9UKINAvWJPw==");
        params.put("name","baidu");
        params.put("url","http://www.baidu.com");
        try {
            HttpClientUtils.httpPostRequest("http://www.baidu.com",params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}