//package com.sunyard.sunfintech.billcheck.service;
//
//import com.sunyard.sunfintech.dao.entity.ClearAccountError;
//import com.sunyard.sunfintech.dao.mapper.ClearAccountErrorMapper;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * Created by djh on 2017/12/21.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("classpath:spring/test-spring-context.xml")
//public class AccountClearServiceTest {
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    ClearAccountErrorMapper clearAccountErrorMapper;
//    @Before
//    public void setUp() throws Exception {
//        clearAccountErrorMapper = wac.getBean(ClearAccountErrorMapper.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void clearN_balance() throws Exception {
//    }
//
//    @Test
//    public void clearF_balance() throws Exception {
//    }
//
//    @Test(timeout = 500)
//    public void insertClearCheckError() throws Exception {
//        //Thread.sleep(10000);
//        ClearModel clearModel = new ClearModel("Plat_sunyard","2017-12-21","");
//        ClearAccount account = new ClearAccount("platcustA","02","01");
//        account.insConcurrenceFailTimes();
//        account.addAmt(BigDecimal.TEN);
//        ClearAccountError accountError = new ClearAccountError();
//        accountError.setPlat_no(clearModel.getPlat_no());
//        accountError.setClear_date(clearModel.getClear_date());
//        accountError.setPay_code(clearModel.getPay_code());
//        accountError.setPlatcust(account.getPlatcust());
//        accountError.setSubject(account.getSubject());
//        accountError.setSub_subject(account.getSub_subject());
//        accountError.setError_fail_times(account.getError_fail_times());
//        accountError.setN_balance(account.getN_balance());
//        accountError.setF_balance(account.getF_balance());
//        accountError.setLast_f_balance(account.getLast_f_balance());
//        accountError.setLast_n_balance(account.getLast_n_balance());
//        accountError.setFail_type(String.valueOf(account.getFail_type()));
//        accountError.setConcurrence_fail_times(account.getConcurrence_fail_times());
//        accountError.setCreate_time(new Date());
//        clearAccountErrorMapper.insertSelective(accountError);
//    }
//
//}