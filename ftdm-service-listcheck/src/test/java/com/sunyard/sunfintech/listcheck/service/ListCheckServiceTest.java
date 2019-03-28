package com.sunyard.sunfintech.listcheck.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by terry on 2018/6/26.
 */
public class ListCheckServiceTest {
    @Test
    public void doCheck() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IListCheckService listCheckService=context.getBean(IListCheckService.class);
//        listCheckService.doCheck("P00001","201806291434470107112939610930","fs100_1530254079211363853","1");
    }

}