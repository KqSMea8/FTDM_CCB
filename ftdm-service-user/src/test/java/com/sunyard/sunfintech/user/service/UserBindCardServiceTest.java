package com.sunyard.sunfintech.user.service;

import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.user.model.bo.CompanyRegisterRequest;
import com.sunyard.sunfintech.user.provider.IUserBindCardService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by terry on 2018/3/20.
 */
public class UserBindCardServiceTest {
    @Test
    public void applyOrgRegister() throws Exception {
        CompanyRegisterRequest registerRequest=new CompanyRegisterRequest();
        registerRequest.setBusiness_license("213");
        registerRequest.setBank_license("");
        System.out.println(StringUtils.isBlank(registerRequest.getBank_license())?registerRequest.getBusiness_license():registerRequest.getBank_license());
    }

    @Test
    public void insertTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IUserBindCardService userBindCardService=context.getBean(IUserBindCardService.class);
//        userBindCardService.insertAndQueryTest();
    }

}