package com.sunyard.sunfintech.prod.modulepublic;

import com.sunyard.sunfintech.pub.provider.IAuthCheckService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by terry on 2018/7/17.
 */
public class AuthCheckServiceTest {
    @Test
    public void checkAuth() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IAuthCheckService authCheckService=context.getBean(IAuthCheckService.class);
        //true
        System.out.println("授权检查："+authCheckService.checkAuth("BOB-U51-51-C-20170821","BOB-U51-C-20170821",
                "20180122155603082911469261372574","1",new BigDecimal(1234)));

        //true
        System.out.println("授权检查："+authCheckService.checkAuth("BOB-U51-51-C-20170821","BOB-U51-C-20170821",
                "20180122155603082911469261372574","1",new BigDecimal(1000)));

        //false
        System.out.println("授权检查："+authCheckService.checkAuth("BOB-U51-51-C-20170821","BOB-U51-C-20170821",
                "20180122155603082911469261372574","1",new BigDecimal(2000)));

        //true
        System.out.println("授权检查："+authCheckService.checkAuth("BOB-U51-51-C-20170821","BOB-U51-C-20170821",
                "20180122155603082911469261372574","2",new BigDecimal(1000)));

        //true
        System.out.println("授权检查："+authCheckService.checkAuth("BOB-U51-51-C-20170821","BOB-U51-C-20170821",
                "20180122155603082911469261372574","3",new BigDecimal(1000)));

        //false
        System.out.println("授权检查："+authCheckService.checkAuth("BOB-U51-51-C-20170821","BOB-U51-C-20170821",
                "20180122155603082911469261372574","4",new BigDecimal(1000)));
    }

}