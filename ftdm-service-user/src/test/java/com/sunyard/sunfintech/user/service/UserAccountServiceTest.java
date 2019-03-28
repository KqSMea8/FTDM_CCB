package com.sunyard.sunfintech.user.service;

import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.custdao.mapper.CustEaccUserinfoMapper;
import com.sunyard.sunfintech.dao.entity.EaccAccountinfo;
import com.sunyard.sunfintech.dao.entity.EaccUserinfo;
import com.sunyard.sunfintech.dao.mapper.EaccUserinfoMapper;
import com.sunyard.sunfintech.user.provider.IRechargeOptionService;
import com.sunyard.sunfintech.user.provider.IUserAccountService;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by terry on 2018/5/2.
 */
public class UserAccountServiceTest {

    @Test
    public void register() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        IUserAccountService userAccountService=context.getBean(IUserAccountService.class);
        EaccUserinfo eaccUserinfo=new EaccUserinfo();
        EaccUserinfo eaccUserinfo2=new EaccUserinfo();
        eaccUserinfo.setMall_no("abc");
        eaccUserinfo.setMallcust("1232456789");
        eaccUserinfo.setName("terry");
        eaccUserinfo.setId_code("111111111");
        eaccUserinfo.setId_type("1");
        eaccUserinfo.setCus_type("1");
        eaccUserinfo.setEnabled("0");
        eaccUserinfo.setMobile("321654987");
        BeanUtils.copyProperties(eaccUserinfo,eaccUserinfo2);
        EaccAccountinfo eaccAccountinfo=new EaccAccountinfo();
        eaccAccountinfo.setPlat_no("aaa");
        eaccAccountinfo.setMall_no("abc");
        eaccAccountinfo.setMallcust("123456789");
        eaccAccountinfo.setPlatcust("987654321");
        eaccAccountinfo.setEnabled("1");
        userAccountService.register(eaccAccountinfo,eaccUserinfo);
        eaccUserinfo2.setDefault_mobile("753951456");
        eaccUserinfo2.setDefault_card_no("852471963");
        eaccUserinfo2.setEnabled("1");
        userAccountService.register(eaccAccountinfo,eaccUserinfo2);
    }

    @Test
    public void test(){
        int a=1;
        switch (a){
            case 0:{
                System.out.println("0");
                break;
            }
            case 1:{
                System.out.println("1");
                break;
            }
        }
    }

    @Test
    public void testTransfer(){
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
//        context.start();
//        IRechargeOptionService rechargeOptionService=context.getBean(IRechargeOptionService.class);
//        rechargeOptionService.charge();
//        String a="1,2，3，4,5，6";
//        System.out.println(a.replaceAll("，",","));
        //|x|²

//        for(int i=-10;i<=10;i++){
//            if(i==0){
//                System.out.print("0,");
//            }else{
//                System.out.print(new BigDecimal(Math.pow(1.0/Math.abs(i),2)*10).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+",");
//            }
//        }
        BigDecimal sbd=new BigDecimal(10.25);
        System.out.println("\n\n\n\n==================================================");
//        String bd100=String.valueOf(sbd.multiply(new BigDecimal(100)));
//        System.out.println(CacheUtil.getCache().increment("456789",Long.parseLong(bd100.substring(0,bd100.indexOf(".")))));
//        System.out.println(CacheUtil.getCache().increment("456789",Long.parseLong(bd100.substring(0,bd100.indexOf(".")))));
        System.out.println(new BigDecimal(100030).divide(new BigDecimal(100)));
        System.out.println("==================================================\n\n\n\n");
    }

}