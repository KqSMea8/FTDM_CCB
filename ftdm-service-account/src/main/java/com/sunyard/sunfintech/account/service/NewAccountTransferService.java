//package com.sunyard.sunfintech.account.service;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.sunyard.sunfintech.account.provider.IAccountQueryService;
//import com.sunyard.sunfintech.account.provider.INewAccountTransferExecuteService;
//import com.sunyard.sunfintech.account.provider.INewAccountTransferService;
//import com.sunyard.sunfintech.core.TransConsts;
//import com.sunyard.sunfintech.core.base.BaseServiceSimple;
//import com.sunyard.sunfintech.core.dic.Ssubject;
//import com.sunyard.sunfintech.core.dic.Tsubject;
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.core.exception.BusinessMsg;
//import com.sunyard.sunfintech.core.util.StringUtils;
//import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
//import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.security.PrivateKey;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by terry on 2018/1/9.
// */
//@Service(interfaceClass = INewAccountTransferService.class)
//@CacheConfig(cacheNames = "newAccountTransferService")
//@org.springframework.stereotype.Service
//public class NewAccountTransferService extends BaseServiceSimple implements INewAccountTransferService {
//
//    @Autowired
//    private INewAccountTransferExecuteService newAccountTransferExecuteService;
//
//    @Autowired
//    private IAccountQueryService accountQueryService;
//
//    @Autowired
//    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;
//
//    @Override
//    public Integer transfer(EaccAccountamtlist expenseAccount) throws BusinessException {
//
//        logger.info("【单笔转账】转账参数："+expenseAccount.toString());
//        if(StringUtils.isBlank(expenseAccount.getPlatcust()) || StringUtils.isBlank(expenseAccount.getSubject())
//                || StringUtils.isBlank(expenseAccount.getSub_subject()) || StringUtils.isBlank(expenseAccount.getOppo_platcust())
//                || StringUtils.isBlank(expenseAccount.getOppo_subject()) || StringUtils.isBlank(expenseAccount.getOppo_sub_subject())
//                || expenseAccount.getAmt()==null || StringUtils.isBlank(expenseAccount.getPlat_no())
//                || StringUtils.isBlank(expenseAccount.getTrans_serial()) || StringUtils.isBlank(expenseAccount.getTrans_code())
//                || StringUtils.isBlank(expenseAccount.getTrans_name()) || StringUtils.isBlank(expenseAccount.getTrans_date())
//                || StringUtils.isBlank(expenseAccount.getTrans_time()) || StringUtils.isBlank(expenseAccount.getOrder_no())){
//            logger.info("【单笔转账】转账参数错误，请程序员检查代码！！！");
//            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"转账失败：转账参数错误，请程序员检查代码！！！");
//        }
//
//        if(!TransConsts.REFUND_CODE.equals(expenseAccount.getTrans_code()) && !TransConsts.PRODREPAY_CODE.equals(expenseAccount.getTrans_code())){
//            //平台现金转有电子账户的个人现金投资，优先电子账户
//            if(Tsubject.CASH.getCode().equals(expenseAccount.getSubject())){
//                if(!Ssubject.INVEST.getCode().equals(expenseAccount.getSub_subject()) &&
//                        !Ssubject.FINANCING.getCode().equals(expenseAccount.getSub_subject()) &&
//                        !Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject()) &&
//                        Ssubject.INVEST.getCode().equals(expenseAccount.getOppo_sub_subject())){
//                    AccountSubjectInfo checkEaccount=accountQueryService.queryAccount(null,
//                            expenseAccount.getOppo_platcust(),Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
//                    if(checkEaccount!=null){
//                        expenseAccount.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
//                    }
//                }
//            }
//        }
//        //暂不考虑电子账户转账
//        return newAccountTransferExecuteService.transfer(expenseAccount);
//    }
//
//    @Override
//    public Integer batchTransfer(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException {
//        for(EaccAccountamtlist expenseAccount:eaccAccountamtlists){
//            logger.info("【批量转账】转账参数："+expenseAccount.toString());
//            if(StringUtils.isBlank(expenseAccount.getPlatcust()) || StringUtils.isBlank(expenseAccount.getSubject())
//                    || StringUtils.isBlank(expenseAccount.getSub_subject()) || StringUtils.isBlank(expenseAccount.getOppo_platcust())
//                    || StringUtils.isBlank(expenseAccount.getOppo_subject()) || StringUtils.isBlank(expenseAccount.getOppo_sub_subject())
//                    || expenseAccount.getAmt()==null || StringUtils.isBlank(expenseAccount.getPlat_no())
//                    || StringUtils.isBlank(expenseAccount.getTrans_serial()) || StringUtils.isBlank(expenseAccount.getTrans_code())
//                    || StringUtils.isBlank(expenseAccount.getTrans_name()) || StringUtils.isBlank(expenseAccount.getTrans_date())
//                    || StringUtils.isBlank(expenseAccount.getTrans_time()) || StringUtils.isBlank(expenseAccount.getOrder_no())){
//                logger.info("【批量转账】转账参数错误，请程序员检查代码！！！");
//                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"转账失败：转账参数错误，请程序员检查代码！！！");
//            }
//
//            if(!TransConsts.REFUND_CODE.equals(expenseAccount.getTrans_code()) && !TransConsts.PRODREPAY_CODE.equals(expenseAccount.getTrans_code())){
//                //平台现金转有电子账户的个人现金投资，优先电子账户
//                if(Tsubject.CASH.getCode().equals(expenseAccount.getSubject())){
//                    if(!Ssubject.INVEST.getCode().equals(expenseAccount.getSub_subject()) &&
//                            !Ssubject.FINANCING.getCode().equals(expenseAccount.getSub_subject()) &&
//                            !Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject()) &&
//                            Ssubject.INVEST.getCode().equals(expenseAccount.getOppo_sub_subject())){
//                        AccountSubjectInfo checkEaccount=accountQueryService.queryAccount(null,
//                                expenseAccount.getOppo_platcust(),Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
//                        if(checkEaccount!=null){
//                            expenseAccount.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
//                        }
//                    }
//                }
//            }
//        }
//        //暂不考虑电子账户转账
//        return newAccountTransferExecuteService.batchTransfer(eaccAccountamtlists);
//    }
//
//    @Override
//    @Transactional
//    public boolean singleCharge(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        AccountSubjectInfo account=new AccountSubjectInfo();
//        account=accountQueryService.queryAccount(
//                eaccAccountamtlist.getPlat_no(),eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getSubject(),eaccAccountamtlist.getSub_subject());
//        List<Long> ids=new ArrayList<Long>();
//        ids.add(account.getId());
//        //根据id添加行锁
//        custAccountSubjectInfoMapper.queryAccountListByIdsForUpdate(ids);
//        return newAccountTransferExecuteService.charge(eaccAccountamtlist,account.getId());
//    }
//
//    @Override
//    @Transactional
//    public void batchCharge(List<EaccAccountamtlist> eaccAccountamtlistList) throws BusinessException {
//        for(EaccAccountamtlist eaccAccountamtlist :eaccAccountamtlistList){
//            singleCharge(eaccAccountamtlist);
//        }
//    }
//}
