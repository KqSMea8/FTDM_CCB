package com.sunyard.sunfintech.account.service;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.AuthType;
import com.sunyard.sunfintech.core.dic.Ssubject;
import com.sunyard.sunfintech.core.dic.Tsubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by terry on 2017/5/31.
 */
public class AccountTransferServiceTest {
    @Test
    public void transferFull() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        context.start();
        ITransferService transferService=context.getBean(ITransferService.class);
        //个人用户转账个人用户
        BaseRequest baseRequest=new BaseRequest();
        EaccAccountamtlist eaccAccountamtlist=new EaccAccountamtlist();
        eaccAccountamtlist.setPlat_no("BOB-U51-51-C-20170821");
        eaccAccountamtlist.setPlatcust("20180121233407086211107907698333");
        eaccAccountamtlist.setSubject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setSub_subject(Ssubject.INVEST.getCode());
        eaccAccountamtlist.setOppo_platcust("20180121233521031611107982941336");
        eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setOppo_sub_subject(Ssubject.INVEST.getCode());
        eaccAccountamtlist.setAmt(BigDecimal.valueOf(15));
        eaccAccountamtlist.setTrans_code("000000");
        eaccAccountamtlist.setTrans_name("000000");
        String seq= SeqUtil.getSeqNum();
        eaccAccountamtlist.setTrans_serial(seq);
        eaccAccountamtlist.setOrder_no(seq);
        eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
        eaccAccountamtlist.setTrans_time(DateUtils.getHHmmssTime());
        try{
            for(int i=0;i<1;i++){
//                String seq= SeqUtil.getSeqNum();
//                eaccAccountamtlist.setTrans_serial(seq);
//                eaccAccountamtlist.setOrder_no(seq);
                System.out.println("===============【转账】订单号："+seq);
                transferService.transfer(baseRequest,eaccAccountamtlist);
                System.out.println("===============【转账】转账成功！");
            }
        }catch (BusinessException e){
            System.out.println("===============【转账】"+e.getBaseResponse().getRemsg());
        }
        Thread.sleep(100000);
    }

    @Test
    public void batchTransfer() throws Exception {
        EaccAccountamtlist eaccAccountamtlist1=new EaccAccountamtlist();
        EaccAccountamtlist eaccAccountamtlist2=new EaccAccountamtlist();
        String a="123";
        String b="12345";
        eaccAccountamtlist1.setPlat_no(a);
        System.out.println(eaccAccountamtlist1.getPlat_no());
        eaccAccountamtlist2.setPlat_no(eaccAccountamtlist1.getPlat_no());
        System.out.println(eaccAccountamtlist2.getPlat_no());
        eaccAccountamtlist1.setPlat_no(b);
        System.out.println(eaccAccountamtlist2.getPlat_no());



    }

    @Test
    public void showemne(){
        for (AuthType e : AuthType.values()) {
            System.out.println(e.getCode());
        }
    }

    @Test
    public void testJSON(){
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setOrder_no("111");
        System.out.println(JSON.toJSONString(baseResponse, GlobalConfig.serializerFeature));
        baseResponse.setOrder_status("");
        System.out.println(JSON.toJSONString(baseResponse, GlobalConfig.serializerFeature));
        baseResponse.setOrder_status("12");
        System.out.println(JSON.toJSONString(baseResponse, GlobalConfig.serializerFeature));
    }

}