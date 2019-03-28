//package com.sunyard.sunfintech.user.service;
//
//import com.alibaba.fastjson.JSON;
//import com.sunyard.sunfintech.dao.entity.TransTransreq;
//import com.sunyard.sunfintech.user.provider.IUserServiceStatusQueryService;
//import org.junit.Test;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by terry on 2018/3/24.
// */
//public class UserServiceStatusQueryServiceTest {
//    @Test
//    public void autoQueryAccountStatus() throws Exception {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
//        context.start();
//        IUserServiceStatusQueryService userServiceStatusQueryService=context.getBean(IUserServiceStatusQueryService.class);
//        List<TransTransreq> list= userServiceStatusQueryService.autoQueryAccountStatus(10);
//        System.out.println("============================start================================");
//        System.out.println("list:"+JSON.toJSONString(list));
//        System.out.println("============================end================================");
//    }
//
//}