//package com.sunyard.sunfintech.account.service;
//
//import com.sunyard.sunfintech.account.provider.IAccountQueryService;
//import com.sunyard.sunfintech.account.provider.INewAccountTransferService;
//import com.sunyard.sunfintech.core.dic.Ssubject;
//import com.sunyard.sunfintech.core.dic.Tsubject;
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.core.util.DateUtils;
//import com.sunyard.sunfintech.core.util.SeqUtil;
//import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//import org.junit.Test;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//import static org.junit.Assert.*;
//
///**
// * 转账测试案例
// * Created by terry on 2018/1/13.
// */
//public class NewAccountTransferServiceTest {
//
//    private static INewAccountTransferService newAccountTransferService=null;
//    private static IAccountQueryService accountQueryService;
//
//    public static void main(String[] args) {
//        if(newAccountTransferService==null){
//            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
//            context.start();
//            newAccountTransferService=context.getBean(INewAccountTransferService.class);
//            accountQueryService=context.getBean(IAccountQueryService.class);
//        }
//        String plat_no="BOB-U51-51-C-20170821";
//        do{
//            List<AccountSubjectInfo> account001List=accountQueryService.queryAccountlist(plat_no,"00000000000001");
//            List<AccountSubjectInfo> account002List=accountQueryService.queryAccountlist(plat_no,"00000000000002");
//            List<AccountSubjectInfo> accountPTList=accountQueryService.queryAccountlist(plat_no,"BOB-U51-51-C-20170821-test");
//            String platcust="";
//            BigDecimal cashInvest=BigDecimal.ZERO;
//            BigDecimal floatInvest=BigDecimal.ZERO;
//            BigDecimal cashFin=BigDecimal.ZERO;
//            BigDecimal floatFin=BigDecimal.ZERO;
//            System.out.println("\n\n=============================================================");
//            for(AccountSubjectInfo account:account001List){
//                platcust=account.getPlatcust();
//                if(Tsubject.CASH.getCode().equals(account.getSubject())){
//                    if(Ssubject.INVEST.getCode().equals(account.getSub_subject())){
//                        cashInvest=account.getN_balance();
//                    }else if(Ssubject.FINANCING.getCode().equals(account.getSub_subject())){
//                        cashFin=account.getN_balance();
//                    }
//                }else if(Tsubject.FLOAT.getCode().equals(account.getSubject())){
//                    if(Ssubject.INVEST.getCode().equals(account.getSub_subject())){
//                        floatInvest=account.getN_balance();
//                    }else if(Ssubject.FINANCING.getCode().equals(account.getSub_subject())){
//                        floatFin=account.getN_balance();
//                    }
//                }
//            }
//            System.out.println(String.format("用户账户：%s\n    01 01-现金投资：%s\t\t02 01-在途投资：%s\t\t01 02-现金融资：%s\t\t02 02-在途融资：%s",
//                    platcust,cashInvest,floatInvest,cashFin,floatFin));
//            for(AccountSubjectInfo account:account002List){
//                platcust=account.getPlatcust();
//                if(Tsubject.CASH.getCode().equals(account.getSubject())){
//                    if(Ssubject.INVEST.getCode().equals(account.getSub_subject())){
//                        cashInvest=account.getN_balance();
//                    }else if(Ssubject.FINANCING.getCode().equals(account.getSub_subject())){
//                        cashFin=account.getN_balance();
//                    }
//                }else if(Tsubject.FLOAT.getCode().equals(account.getSubject())){
//                    if(Ssubject.INVEST.getCode().equals(account.getSub_subject())){
//                        floatInvest=account.getN_balance();
//                    }else if(Ssubject.FINANCING.getCode().equals(account.getSub_subject())){
//                        floatFin=account.getN_balance();
//                    }
//                }
//            }
//            System.out.println(String.format("用户账户：%s\n    01 01-现金投资：%s\t\t02 01-在途投资：%s\t\t01 02-现金融资：%s\t\t02 02-在途融资：%s",
//                    platcust,cashInvest,floatInvest,cashFin,floatFin));
//            for(AccountSubjectInfo account:accountPTList){
//                platcust=account.getPlatcust();
//                if(Tsubject.CASH.getCode().equals(account.getSubject())){
//                    if(Ssubject.PLAT.getCode().equals(account.getSub_subject())){
//                        cashInvest=account.getN_balance();
//                    }
//                }else if(Tsubject.FLOAT.getCode().equals(account.getSubject())){
//                    if(Ssubject.PLAT.getCode().equals(account.getSub_subject())){
//                        floatInvest=account.getN_balance();
//                    }
//                }
//            }
//            System.out.println(String.format("平台账户：%s\n    01 11-现金：%s\t\t02 11-在途：%s",
//                    platcust,cashInvest,floatInvest));
//
//            Scanner in=new Scanner(System.in);
//            System.out.println("请输入转出账户及科目：");
//            String outAccount=in.nextLine();
//            System.out.println("请输入转入账户及科目：");
//            String inAccount=in.nextLine();
//            System.out.println("请输入转账金额：");
//            BigDecimal transAmt=in.nextBigDecimal();
//            String[] outParams=outAccount.split(" ");
//            String[] inParams=inAccount.split(" ");
//            Long startTime=(new Date()).getTime();
//            transfer(outParams[0],outParams[1],outParams[2],inParams[0],inParams[1],inParams[2],transAmt);
//            Long endTime=(new Date()).getTime();
//            System.out.println("转账耗时："+(endTime-startTime)+"ms");
//        }while (true);
//    }
//
//    @Test
//    public static void transfer(String platcust,String subject,String sub_subject, String oppo_platcust, String oppo_subject, String oppo_sub_subject, BigDecimal amt) {
//        //=================转账测试案例==================
//        try{
//            String plat_no="BOB-U51-51-C-20170821";
//            EaccAccountamtlist transparams=getTransParams(plat_no,platcust,subject,sub_subject,oppo_platcust,oppo_subject,oppo_sub_subject,amt);
//            Integer result=newAccountTransferService.transfer(transparams);
//            System.out.println(String.format("order_no:%s|trans_serial:%s",transparams.getOrder_no(),transparams.getTrans_serial()));
//            System.out.println("result:"+result );
//            System.out.println("=================================================\n\n");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    public void batchTransfer() throws Exception {
//    }
//
//    private static EaccAccountamtlist getTransParams(String plat_no, String platcust, String subject, String sub_subject, String oppo_platcust, String oppo_subject, String oppo_sub_subject, BigDecimal amt){
//        EaccAccountamtlist eaccAccountamtlist=new EaccAccountamtlist();
//        eaccAccountamtlist.setOrder_no(SeqUtil.getSeqNum());
//        eaccAccountamtlist.setTrans_serial(SeqUtil.getSeqNum());
//        eaccAccountamtlist.setPlat_no(plat_no);
//        eaccAccountamtlist.setPlatcust(platcust);
//        eaccAccountamtlist.setSubject(subject);
//        eaccAccountamtlist.setSub_subject(sub_subject);
//        eaccAccountamtlist.setOppo_platcust(oppo_platcust);
//        eaccAccountamtlist.setOppo_subject(oppo_subject);
//        eaccAccountamtlist.setOppo_sub_subject(oppo_sub_subject);
//        eaccAccountamtlist.setAmt(amt);
//        eaccAccountamtlist.setTrans_code("000000");
//        eaccAccountamtlist.setTrans_name("转账单元测试");
//        eaccAccountamtlist.setTrans_date(DateUtils.getyyyyMMddDate());
//        eaccAccountamtlist.setTrans_time(DateUtils.getHHmmssTime());
//        return eaccAccountamtlist;
//    }
//
//}