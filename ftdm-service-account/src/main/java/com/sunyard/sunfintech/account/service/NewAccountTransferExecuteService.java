//package com.sunyard.sunfintech.account.service;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.sunyard.sunfintech.account.enums.AMTTYPE;
//import com.sunyard.sunfintech.account.provider.IAccountQueryService;
//import com.sunyard.sunfintech.account.provider.IAccountTransferService;
//import com.sunyard.sunfintech.account.provider.INewAccountTransferExecuteService;
//import com.sunyard.sunfintech.account.provider.INewAccountTransferService;
//import com.sunyard.sunfintech.core.TransConsts;
//import com.sunyard.sunfintech.core.base.BaseResponse;
//import com.sunyard.sunfintech.core.dic.Ssubject;
//import com.sunyard.sunfintech.core.dic.TransferStatus;
//import com.sunyard.sunfintech.core.dic.Tsubject;
//import com.sunyard.sunfintech.core.exception.BusinessException;
//import com.sunyard.sunfintech.core.exception.BusinessMsg;
//import com.sunyard.sunfintech.core.util.CacheUtil;
//import com.sunyard.sunfintech.core.util.StringUtils;
//import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
//import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
//import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by terry on 2018/1/4.
// */
//@Service(interfaceClass = INewAccountTransferExecuteService.class)
//@CacheConfig(cacheNames = "newAccountTransferExecuteService")
//@org.springframework.stereotype.Service
//public class NewAccountTransferExecuteService implements INewAccountTransferExecuteService {
//    protected Logger logger = LogManager.getLogger(getClass());
//
//    @Autowired
//    private IAccountTransferService accountTransferService;
//
//    @Autowired
//    private IAccountQueryService accountQueryService;
//
//    @Autowired
//    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;
//
//    @Override
//    @Transactional
//    public Integer transfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException {
//        List<AccountSubjectInfo> outAccountList=new ArrayList<>();
//        List<AccountSubjectInfo> inAccountList=new ArrayList<>();
//        //查询出所有出金账户
//        if(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getSub_subject())
//                || Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getSub_subject())){
//            //如果是投资账户，查询投资账户和电子账户
//            List<String> sub_subject_list=new ArrayList<>();
//            sub_subject_list.add(Ssubject.INVEST.getCode());
//            sub_subject_list.add(Ssubject.EACCOUNT.getCode());
//            outAccountList=accountQueryService.queryPlatAccountList(null,eaccAccountamtlist.getPlatcust(),null,sub_subject_list);
//        }else{
//            outAccountList=accountQueryService.queryPlatAccountList(null,eaccAccountamtlist.getPlatcust(),null,eaccAccountamtlist.getSub_subject());
//        }
//        if(outAccountList.size()==0){
//            logger.info(String.format("【转账-单笔转账】出金账户不存在|platcust:%s|subject:%s|sub_subject%s",
//                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getSubject(),eaccAccountamtlist.getSub_subject()));
//            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                    String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"|platcust:%s|subject:%s|sub_subject%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getSubject(),eaccAccountamtlist.getSub_subject()));
//        }
//        //查询出所有入金账户
//        if(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getOppo_sub_subject())
//                || Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getOppo_sub_subject())){
//            //如果是投资账户，查询投资账户和电子账户
//            List<String> sub_subject_list=new ArrayList<>();
//            sub_subject_list.add(Ssubject.INVEST.getCode());
//            sub_subject_list.add(Ssubject.EACCOUNT.getCode());
//            inAccountList=accountQueryService.queryPlatAccountList(null,eaccAccountamtlist.getOppo_platcust(),null,sub_subject_list);
//        }else{
//            inAccountList=accountQueryService.queryPlatAccountList(null,eaccAccountamtlist.getOppo_platcust(),null,eaccAccountamtlist.getOppo_sub_subject());
//        }
//        if(inAccountList.size()==0){
//            logger.info(String.format("【转账-单笔转账】出金账户不存在|platcust:%s|subject:%s|sub_subject%s",
//                    eaccAccountamtlist.getOppo_platcust(),eaccAccountamtlist.getOppo_subject(),eaccAccountamtlist.getOppo_sub_subject()));
//            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                    String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"|platcust:%s|subject:%s|sub_subject%s",
//                            eaccAccountamtlist.getOppo_platcust(),eaccAccountamtlist.getOppo_subject(),eaccAccountamtlist.getOppo_sub_subject()));
//        }
//        List<Long> ids=new ArrayList<>();
//        for(AccountSubjectInfo account:outAccountList){
//            ids.add(account.getId());
//        }
//        for(AccountSubjectInfo account:inAccountList){
//            ids.add(account.getId());
//        }
//        //开始锁定转账涉及到的账户
//        try{
//            List<AccountSubjectInfo> accountSubjectInfoLock=custAccountSubjectInfoMapper.queryAccountListByIdsForUpdate(ids);
//            doTransfer(eaccAccountamtlist,accountSubjectInfoLock);
//        }catch (Exception e){
//            logger.error("【转账-单笔转账】转账异常：",e);
//            if(e instanceof BusinessException){
//                throw e;
//            }else{
//                throw new BusinessException(BusinessMsg.TRANS_FAIL,BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL));
//            }
//        }
//        return TransferStatus.SUCCESS.getCode();
//    }
//
//    @Override
//    @Transactional
//    public Integer batchTransfer(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException {
//        List<AccountSubjectInfo> outAccountList=new ArrayList<>();
//        List<AccountSubjectInfo> inAccountList=new ArrayList<>();
//        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
//            //查询出所有出金账户
//            List<AccountSubjectInfo> singleOutAccountList=new ArrayList<>();
//            if(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getSub_subject())
//                    || Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getSub_subject())){
//                //如果是投资账户，查询投资账户和电子账户
//                List<String> sub_subject_list=new ArrayList<>();
//                sub_subject_list.add(Ssubject.INVEST.getCode());
//                sub_subject_list.add(Ssubject.EACCOUNT.getCode());
//                singleOutAccountList=accountQueryService.queryPlatAccountList(
//                        null,eaccAccountamtlist.getPlatcust(),null,sub_subject_list);
//            }else{
//                singleOutAccountList=accountQueryService.queryPlatAccountList(
//                        null,eaccAccountamtlist.getPlatcust(),null,eaccAccountamtlist.getSub_subject());
//            }
//            if(singleOutAccountList.size()==0){
//                logger.info(String.format("【转账-单笔转账】出金账户不存在|platcust:%s|subject:%s|sub_subject%s",
//                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getSubject(),eaccAccountamtlist.getSub_subject()));
//                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                        String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"|platcust:%s|subject:%s|sub_subject%s",
//                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getSubject(),eaccAccountamtlist.getSub_subject()));
//            }
//            outAccountList.addAll(singleOutAccountList);
//            //查询出所有入金账户
//            List<AccountSubjectInfo> singleInAccountList=new ArrayList<>();
//            if(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getOppo_sub_subject())
//                    || Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getOppo_sub_subject())){
//                //如果是投资账户，查询投资账户和电子账户
//                List<String> sub_subject_list=new ArrayList<>();
//                sub_subject_list.add(Ssubject.INVEST.getCode());
//                sub_subject_list.add(Ssubject.EACCOUNT.getCode());
//                singleInAccountList=accountQueryService.queryPlatAccountList(
//                        null,eaccAccountamtlist.getOppo_platcust(),null,sub_subject_list);
//            }else{
//                singleInAccountList=accountQueryService.queryPlatAccountList(
//                        null,eaccAccountamtlist.getOppo_platcust(),null,eaccAccountamtlist.getOppo_sub_subject());
//            }
//            if(singleInAccountList.size()==0){
//                logger.info(String.format("【转账-单笔转账】出金账户不存在|platcust:%s|subject:%s|sub_subject%s",
//                        eaccAccountamtlist.getOppo_platcust(),eaccAccountamtlist.getOppo_subject(),eaccAccountamtlist.getOppo_sub_subject()));
//                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                        String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"|platcust:%s|subject:%s|sub_subject%s",
//                                eaccAccountamtlist.getOppo_platcust(),eaccAccountamtlist.getOppo_subject(),eaccAccountamtlist.getOppo_sub_subject()));
//            }
//            inAccountList.addAll(singleInAccountList);
//        }
//        List<Long> ids=new ArrayList<>();
//        for(AccountSubjectInfo account:outAccountList){
//            ids.add(account.getId());
//        }
//        for(AccountSubjectInfo account:inAccountList){
//            ids.add(account.getId());
//        }
//        //开始锁定转账涉及到的账户
//        try{
//            List<AccountSubjectInfo> accountSubjectInfoLock=custAccountSubjectInfoMapper.queryAccountListByIdsForUpdate(ids);
//            for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
//                doTransfer(eaccAccountamtlist,accountSubjectInfoLock);
//            }
//        }catch (Exception e){
//            logger.error("【转账-单笔转账】转账异常：",e);
//            if(e instanceof BusinessException){
//                throw e;
//            }else{
//                throw new BusinessException(BusinessMsg.TRANS_FAIL,BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL));
//            }
//        }
//        return TransferStatus.SUCCESS.getCode();
//    }
//
//    @Override
//    public Boolean doTransfer(EaccAccountamtlist eaccAccountamtlist, List<AccountSubjectInfo> accountSubjectInfoList) throws BusinessException {
//        //==========================定义出入金账户====================
//        //出金现金虚拟账户
//        AccountSubjectInfo outCashAccount=null;
//        //出金在途虚拟账户
//        AccountSubjectInfo outFloatAccount=null;
//        //出金现金电子账户
//        AccountSubjectInfo outCashEAccount=null;
//        //出金在途电子账户
//        AccountSubjectInfo outFloatEAccount=null;
//
//        //入金现金虚拟账户
//        AccountSubjectInfo inCashAccount=null;
//        //入金在途虚拟账户
//        AccountSubjectInfo inFloatAccount=null;
//        //入金现金电子账户
//        AccountSubjectInfo inCashEAccount=null;
//        //入金在途电子账户
//        AccountSubjectInfo inFloatEAccount=null;
//
//        //真实出金现金账户
//        AccountSubjectInfo realOutCashAccount=null;
//        BigDecimal realOutCashAmt=BigDecimal.ZERO;
//        //真实出金在途账户
//        AccountSubjectInfo realOutFloatAccount=null;
//        BigDecimal realOutFloatAmt=BigDecimal.ZERO;
//        //真实出金电子现金账户
//        AccountSubjectInfo realOutCashEAccount=null;
//        BigDecimal realOutCashEAmt=BigDecimal.ZERO;
//        //真实出金电子在途账户
//        AccountSubjectInfo realOutFloatEAccount=null;
//        BigDecimal realOutFloatEAmt=BigDecimal.ZERO;
//        //真实入金账户
//        AccountSubjectInfo realInAccount=null;
//        //==========================================================
//        for(AccountSubjectInfo account:accountSubjectInfoList){
//            if(eaccAccountamtlist.getPlatcust().equals(account.getPlatcust())){
//                //出金账户
//                if(Tsubject.CASH.getCode().equals(account.getSubject())){
//                    //现金科目
//                    if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
//                        outCashEAccount=account;
//                    }else if(eaccAccountamtlist.getSub_subject().equals(account.getSub_subject())){
//                        outCashAccount=account;
//                    }
//                }else if(Tsubject.FLOAT.getCode().equals(account.getSubject())){
//                    //在途科目
//                    if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
//                        outFloatEAccount=account;
//                    }else if(eaccAccountamtlist.getSub_subject().equals(account.getSub_subject())){
//                        outFloatAccount=account;
//                    }
//                }
//            }
//            if(eaccAccountamtlist.getOppo_platcust().equals(account.getPlatcust())){
//                //入金账户
//                if(Tsubject.CASH.getCode().equals(account.getSubject())){
//                    //现金科目
//                    if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
//                        inCashEAccount=account;
//                    }else if(eaccAccountamtlist.getOppo_sub_subject().equals(account.getSub_subject())){
//                        inCashAccount=account;
//                    }
//                }else if(Tsubject.FLOAT.getCode().equals(account.getSubject())){
//                    //在途科目
//                    if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
//                        inFloatEAccount=account;
//                    }else if(eaccAccountamtlist.getOppo_sub_subject().equals(account.getSub_subject())){
//                        inFloatAccount=account;
//                    }
//                }
//            }
//        }
//        //到目前为止，已经找出了出金账户和入金账户啦^V^~
//        if(Tsubject.CASH.getCode().equals(eaccAccountamtlist.getSubject())){
//            //现金优先
//            if(Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getSubject())){
//                /**
//                 * 如果要求从电子账户出金
//                 * 优先电子账户出金
//                 * 电子账户现金不够，电子账户在途垫
//                 * 电子账户在途不够，虚户现金垫
//                 * 虚户现金不够，虚户在途垫。
//                 */
//                if(outCashEAccount==null || outFloatEAccount==null){
//                    logger.info(String.format("【转账-处理转账】出金电子账户异常|platcust:%s|trans_serial:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                            String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金电子账户异常|platcust:%s",
//                                    eaccAccountamtlist.getPlatcust()));
//                }
//
//                if(outCashEAccount.getN_balance().compareTo(eaccAccountamtlist.getAmt())>=0){
//                    //电子账户现金足够，从电子账户现金扣钱
//                    realOutCashEAccount=outCashEAccount;
//                    realOutCashEAmt=eaccAccountamtlist.getAmt();
//                }else{
//                    //电子账户现金不足，多余部分从电子账户在途扣
//                    logger.info(String.format("【转账-处理转账】电子账户现金不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                            eaccAccountamtlist.getAmt(),outCashEAccount.getN_balance()));
//                    BigDecimal needECash=BigDecimal.ZERO;
//                    BigDecimal needEFloat=eaccAccountamtlist.getAmt();
//                    if(outCashEAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                        needEFloat=eaccAccountamtlist.getAmt().subtract(outCashEAccount.getN_balance());
//                        needECash=outCashEAccount.getN_balance();
//                    }
//                    //从电子账户扣部分钱
//                    realOutCashEAccount=outCashEAccount;
//                    realOutCashEAmt=needECash;
//                    if(outFloatEAccount.getN_balance().compareTo(needEFloat)>=0){
//                        //电子账户在途够，从电子账户在途扣钱
//                        realOutFloatEAccount=outFloatEAccount;
//                        realOutFloatEAmt=needEFloat;
//                    }else{
//                        //电子账户在途不足，多余部分从虚户投资现金扣
//                        logger.info(String.format("【转账-处理转账】电子账户在途不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                needEFloat,outFloatEAccount.getN_balance()));
//                        if(outCashAccount==null || outFloatAccount==null){
//                            logger.info(String.format("【转账-处理转账】出金投资账户异常|platcust:%s|trans_serial:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                                    String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金投资账户异常|platcust:%s",
//                                            eaccAccountamtlist.getPlatcust()));
//                        }
//                        BigDecimal needCash=BigDecimal.ZERO;
//                        if(outFloatEAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                            needCash=needEFloat.subtract(outFloatEAccount.getN_balance());
//                            needEFloat=outFloatEAccount.getN_balance();
//                        }
//                        //电子账户在途够，从电子账户在途扣钱
//                        realOutFloatEAccount=outFloatEAccount;
//                        realOutFloatEAmt=needEFloat;
//                        if(outCashAccount.getN_balance().compareTo(needCash)>=0){
//                            //虚户现金投资够,从电子账户现金扣钱
//                            realOutCashAccount=outCashAccount;
//                            realOutCashAmt=needCash;
//                        }else{
//                            //虚户现金投资不够，多余部分从虚户在途投资扣
//                            logger.info(String.format("【转账-处理转账】虚户现金投资不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                    needCash,outCashAccount.getN_balance()));
//                            BigDecimal needFloat=BigDecimal.ZERO;
//                            if(outCashAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                                needFloat=needCash.subtract(outCashAccount.getN_balance());
//                                needCash=outCashAccount.getN_balance();
//                            }
//                            realOutCashAccount=outCashAccount;
//                            realOutCashAmt=needCash;
//                            if(outFloatAccount.getN_balance().compareTo(needFloat)>=0){
//                                //虚户在途投资够，从虚户在途投资扣钱
//                                realOutFloatAccount=outFloatAccount;
//                                realOutFloatAmt=needFloat;
//                            }else{
//                                logger.info(String.format("【转账-处理转账】虚户在途投资不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                        needFloat,outFloatAccount.getN_balance()));
//                                logger.info(String.format("【转账-处理转账】出金账户余额不足|platcust:%s|trans_serial:%s",
//                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                        String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                                eaccAccountamtlist.getPlatcust()));
//                            }
//                        }
//                    }
//                }
//            }else{
//                /**
//                 * 如果要求从投资虚户出金
//                 * 优先投资虚户现金出金
//                 * 投资虚户现金现金不够，投资虚户现金在途垫
//                 * 投资虚户现金在途不够，电子账户现金垫
//                 * 电子账户现金不够，电子账户在途垫。
//                 *
//                 * 如果出金户不为投资账户
//                 * 优先虚户现金出金
//                 * 虚户现金现金不够，虚户现金在途垫
//                 */
//                if(outCashAccount==null || outFloatAccount==null){
//                    logger.info(String.format("【转账-处理转账】出金投资账户异常|platcust:%s|trans_serial:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                            String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金电子账户异常|platcust:%s",
//                                    eaccAccountamtlist.getPlatcust()));
//                }
//
//                if(outCashAccount.getN_balance().compareTo(eaccAccountamtlist.getAmt())>=0){
//                    //虚户现金足够，从虚户现金扣钱
//                    realOutCashAccount=outCashAccount;
//                    realOutCashAmt=eaccAccountamtlist.getAmt();
//                }else{
//                    //虚户现金不足，多余部分从虚户在途扣
//                    logger.info(String.format("【转账-处理转账】虚户现金不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                            eaccAccountamtlist.getAmt(),outCashAccount.getN_balance()));
//                    BigDecimal needCash=BigDecimal.ZERO;
//                    BigDecimal needFloat=eaccAccountamtlist.getAmt();
//                    if(outCashAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                        needFloat=eaccAccountamtlist.getAmt().subtract(outCashAccount.getN_balance());
//                        needCash=outCashAccount.getN_balance();
//                    }
//                    //从虚户现金扣部分钱
//                    realOutCashAccount=outCashAccount;
//                    realOutCashAmt=needCash;
//                    if(outFloatAccount.getN_balance().compareTo(needFloat)>=0){
//                        //虚户在途够，从虚户在途扣钱
//                        realOutFloatAccount=outFloatAccount;
//                        realOutFloatAmt=needFloat;
//                    }else{
//                        //判断虚户是否为投资账户，如果是投资账户，可以从电子账户出金
//                        logger.info(String.format("【转账-处理转账】判断出金账户类型，出金账户子科目为：%s",
//                                eaccAccountamtlist.getSub_subject()));
//                        if(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getSub_subject())){
//                            //虚户在途不足，多余部分从电子账户现金扣
//                            logger.info(String.format("【转账-处理转账】虚户在途不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                    needFloat,outFloatAccount.getN_balance()));
//                            if(outCashEAccount!=null && outFloatEAccount!=null){
////                                logger.info(String.format("【转账-处理转账】出金电子账户异常|platcust:%s|trans_serial:%s",
////                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
////                                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
////                                        String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金账户异常|platcust:%s",
////                                                eaccAccountamtlist.getPlatcust()));
//                                BigDecimal needECash=BigDecimal.ZERO;
//                                if(outFloatAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                                    needECash=needFloat.subtract(outFloatAccount.getN_balance());
//                                    needFloat=outFloatAccount.getN_balance();
//                                }
//                                //虚户投资在途够，从虚户投资在途扣钱
//                                realOutFloatAccount=outFloatAccount;
//                                realOutFloatAmt=needFloat;
//                                if(outCashEAccount.getN_balance().compareTo(needECash)>=0){
//                                    //电子账户现金够,从电子账户现金扣钱
//                                    realOutCashEAccount=outCashEAccount;
//                                    realOutCashEAmt=needECash;
//                                }else{
//                                    //电子账户现金不够，多余部分从电子账户在途扣
//                                    logger.info(String.format("【转账-处理转账】电子账户现金不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                            needECash,outCashEAccount.getN_balance()));
//                                    BigDecimal needEFloat=BigDecimal.ZERO;
//                                    if(outCashEAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                                        needEFloat=needECash.subtract(outCashEAccount.getN_balance());
//                                        needECash=outCashEAccount.getN_balance();
//                                    }
//                                    realOutCashEAccount=outCashEAccount;
//                                    realOutCashEAmt=needECash;
//                                    if(outFloatEAccount.getN_balance().compareTo(needEFloat)>=0){
//                                        //电子账户在途够，从电子账户在途扣钱
//                                        realOutFloatEAccount=outFloatEAccount;
//                                        realOutFloatEAmt=needEFloat;
//                                    }else{
//                                        logger.info(String.format("【转账-处理转账】电子账户在途不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                                needEFloat,outFloatEAccount.getN_balance()));
//                                        logger.info(String.format("【转账-处理转账】出金账户余额不足|platcust:%s|trans_serial:%s",
//                                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                                        throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                                String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                                        eaccAccountamtlist.getPlatcust()));
//                                    }
//                                }
//                            }else{
//                                logger.info(String.format("【转账-处理转账】出金投资账户余额不足|platcust:%s|trans_serial:%s",
//                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                        String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                                eaccAccountamtlist.getPlatcust()));
//                            }
//                        }else{
//                            logger.info(String.format("【转账-处理转账】出金投资账户余额不足|platcust:%s|trans_serial:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                            throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                    String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                            eaccAccountamtlist.getPlatcust()));
//                        }
//                    }
//                }
//            }
//        }else if(Tsubject.FLOAT.getCode().equals(eaccAccountamtlist.getSubject())){
//            //在途优先
//            //在途优先
//            if(Ssubject.EACCOUNT.getCode().equals(eaccAccountamtlist.getSubject())){
//                /**
//                 * 如果要求从电子账户出金
//                 * 优先电子账户出金
//                 * 电子账户在途不够，电子账户现金垫
//                 * 电子账户现金不够，虚户在途垫
//                 * 虚户在途不够，虚户现金垫。
//                 */
//                if(outFloatEAccount==null || outCashEAccount==null){
//                    logger.info(String.format("【转账-处理转账】出金电子账户异常|platcust:%s|trans_serial:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                            String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金电子账户异常|platcust:%s",
//                                    eaccAccountamtlist.getPlatcust()));
//                }
//
//                if(outFloatEAccount.getN_balance().compareTo(eaccAccountamtlist.getAmt())>=0){
//                    //电子账户在途足够，从电子账户在途扣钱
//                    realOutFloatEAccount=outFloatEAccount;
//                    realOutFloatEAmt=eaccAccountamtlist.getAmt();
//                }else{
//                    //电子账户在途不足，多余部分从电子账户现金扣
//                    logger.info(String.format("【转账-处理转账】电子账户在途不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                            eaccAccountamtlist.getAmt(),outFloatEAccount.getN_balance()));
//                    BigDecimal needEFloat=BigDecimal.ZERO;
//                    BigDecimal needECash=eaccAccountamtlist.getAmt();
//                    if(outFloatEAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                        needECash=eaccAccountamtlist.getAmt().subtract(outFloatEAccount.getN_balance());
//                        needEFloat=outFloatEAccount.getN_balance();
//                    }
//                    //从电子账户扣部分钱
//                    realOutFloatEAccount=outFloatEAccount;
//                    realOutFloatEAmt=needEFloat;
//                    if(outCashEAccount.getN_balance().compareTo(needECash)>=0){
//                        //电子账户现金够，从电子账户现金扣钱
//                        realOutCashEAccount=outCashEAccount;
//                        realOutCashEAmt=needECash;
//                    }else{
//                        //电子账户现金不足，多余部分从虚户投资在途扣
//                        logger.info(String.format("【转账-处理转账】电子账户现金不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                needECash,outCashEAccount.getN_balance()));
//                        if(outFloatAccount==null || outCashAccount==null){
//                            logger.info(String.format("【转账-处理转账】出金投资账户异常|platcust:%s|trans_serial:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                            throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                                    String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金投资账户异常|platcust:%s",
//                                            eaccAccountamtlist.getPlatcust()));
//                        }
//                        BigDecimal needFloat=BigDecimal.ZERO;
//                        if(outCashEAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                            needFloat=needECash.subtract(outCashEAccount.getN_balance());
//                            needECash=outCashEAccount.getN_balance();
//                        }
//                        //电子账户现金够，从电子账户现金扣钱
//                        realOutCashEAccount=outCashEAccount;
//                        realOutCashEAmt=needECash;
//                        if(outFloatAccount.getN_balance().compareTo(needFloat)>=0){
//                            //虚户在途投资够,从电子账户在途扣钱
//                            realOutFloatAccount=outFloatAccount;
//                            realOutFloatAmt=needFloat;
//                        }else{
//                            //虚户在途投资不够，多余部分从虚户现金投资扣
//                            logger.info(String.format("【转账-处理转账】虚户在途投资不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                    needFloat,outFloatAccount.getN_balance()));
//                            BigDecimal needCash=BigDecimal.ZERO;
//                            if(outFloatAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                                needCash=needFloat.subtract(outFloatAccount.getN_balance());
//                                needFloat=outFloatAccount.getN_balance();
//                            }
//                            realOutFloatAccount=outFloatAccount;
//                            realOutFloatAmt=needFloat;
//                            if(outCashAccount.getN_balance().compareTo(needCash)>=0){
//                                //虚户现金投资够，从虚户现金投资扣钱
//                                realOutCashAccount=outCashAccount;
//                                realOutCashAmt=needCash;
//                            }else{
//                                logger.info(String.format("【转账-处理转账】虚户现金投资不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                        needCash,outCashAccount.getN_balance()));
//                                logger.info(String.format("【转账-处理转账】出金账户余额不足|platcust:%s|trans_serial:%s",
//                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                        String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                                eaccAccountamtlist.getPlatcust()));
//                            }
//                        }
//                    }
//                }
//            }else{
//                /**
//                 * 如果要求从投资虚户出金
//                 * 优先投资虚户在途出金
//                 * 投资虚户在途在途不够，投资虚户在途现金垫
//                 * 投资虚户在途现金不够，电子账户在途垫
//                 * 电子账户在途不够，电子账户现金垫。
//                 *
//                 * 如果出金户不为投资账户
//                 * 优先虚户在途出金
//                 * 虚户在途在途不够，虚户在途现金垫
//                 */
//                if(outFloatAccount==null || outCashAccount==null){
//                    logger.info(String.format("【转账-处理转账】出金投资账户异常|platcust:%s|trans_serial:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
//                            String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金电子账户异常|platcust:%s",
//                                    eaccAccountamtlist.getPlatcust()));
//                }
//
//                if(outFloatAccount.getN_balance().compareTo(eaccAccountamtlist.getAmt())>=0){
//                    //虚户在途足够，从虚户在途扣钱
//                    realOutFloatAccount=outFloatAccount;
//                    realOutFloatAmt=eaccAccountamtlist.getAmt();
//                }else{
//                    //虚户在途不足，多余部分从虚户现金扣
//                    logger.info(String.format("【转账-处理转账】虚户在途不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                            eaccAccountamtlist.getAmt(),outFloatAccount.getN_balance()));
//                    BigDecimal needFloat=BigDecimal.ZERO;
//                    BigDecimal needCash=eaccAccountamtlist.getAmt();
//                    if(outFloatAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                        needCash=eaccAccountamtlist.getAmt().subtract(outFloatAccount.getN_balance());
//                        needFloat=outFloatAccount.getN_balance();
//                    }
//                    //从虚户在途扣部分钱
//                    realOutFloatAccount=outFloatAccount;
//                    realOutFloatAmt=needFloat;
//                    if(outCashAccount.getN_balance().compareTo(needCash)>=0){
//                        //虚户现金够，从虚户现金扣钱
//                        realOutCashAccount=outCashAccount;
//                        realOutCashAmt=needCash;
//                    }else{
//                        //判断虚户是否为投资账户，如果是投资账户，可以从电子账户出金
//                        logger.info(String.format("【转账-处理转账】判断出金账户类型，出金账户子科目为：%s",
//                                eaccAccountamtlist.getSub_subject()));
//                        if(Ssubject.INVEST.getCode().equals(eaccAccountamtlist.getSub_subject())){
//                            //虚户现金不足，多余部分从电子账户在途扣
//                            logger.info(String.format("【转账-处理转账】虚户现金不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                    needCash,outCashAccount.getN_balance()));
//                            if(outFloatEAccount!=null || outCashEAccount!=null){
////                                logger.info(String.format("【转账-处理转账】出金电子账户异常|platcust:%s|trans_serial:%s",
////                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
////                                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,
////                                        String.format(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"出金账户异常|platcust:%s",
////                                                eaccAccountamtlist.getPlatcust()));
//                                BigDecimal needEFloat=BigDecimal.ZERO;
//                                if(outCashAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                                    needEFloat=needCash.subtract(outCashAccount.getN_balance());
//                                    needCash=outCashAccount.getN_balance();
//                                }
//                                //虚户投资现金够，从虚户投资现金扣钱
//                                realOutCashAccount=outCashAccount;
//                                realOutCashAmt=needCash;
//                                if(outFloatEAccount.getN_balance().compareTo(needEFloat)>=0){
//                                    //电子账户在途够,从电子账户在途扣钱
//                                    realOutFloatEAccount=outFloatEAccount;
//                                    realOutFloatEAmt=needEFloat;
//                                }else{
//                                    //电子账户在途不够，多余部分从电子账户现金扣
//                                    logger.info(String.format("【转账-处理转账】电子账户在途不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                            eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                            needEFloat,outFloatEAccount.getN_balance()));
//                                    BigDecimal needECash=BigDecimal.ZERO;
//                                    if(outFloatEAccount.getN_balance().compareTo(BigDecimal.ZERO)>=0){
//                                        needECash=needEFloat.subtract(outFloatEAccount.getN_balance());
//                                        needEFloat=outFloatEAccount.getN_balance();
//                                    }
//                                    realOutFloatEAccount=outFloatEAccount;
//                                    realOutFloatEAmt=needEFloat;
//                                    if(outCashEAccount.getN_balance().compareTo(needECash)>=0){
//                                        //电子账户现金够，从电子账户现金扣钱
//                                        realOutCashEAccount=outCashEAccount;
//                                        realOutCashEAmt=needECash;
//                                    }else{
//                                        logger.info(String.format("【转账-处理转账】电子账户现金不足|platcust:%s|trans_serial:%s|needAmt:%s|realAmt:%s",
//                                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial(),
//                                                needECash,outCashEAccount.getN_balance()));
//                                        logger.info(String.format("【转账-处理转账】出金账户余额不足|platcust:%s|trans_serial:%s",
//                                                eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                                        throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                                String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                                        eaccAccountamtlist.getPlatcust()));
//                                    }
//                                }
//                            }else{
//                                logger.info(String.format("【转账-处理转账】出金投资账户余额不足|platcust:%s|trans_serial:%s",
//                                        eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                        String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                                eaccAccountamtlist.getPlatcust()));
//                            }
//                        }else{
//                            logger.info(String.format("【转账-处理转账】出金投资账户余额不足|platcust:%s|trans_serial:%s",
//                                    eaccAccountamtlist.getPlatcust(),eaccAccountamtlist.getTrans_serial()));
//                            throw new BusinessException(BusinessMsg.BALANCE_LACK,
//                                    String.format(BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK)+"出金账户余额不足|platcust:%s",
//                                            eaccAccountamtlist.getPlatcust()));
//                        }
//                    }
//                }
//            }
//        }
//        logger.info("转账参数运算完成！");
//        try{
//            //转账参数
//            EaccAccountamtlist incomeAccountamtlist = new EaccAccountamtlist();
////            BeanUtils.copyProperties(eaccAccountamtlist,incomeAccountamtlist);
//            incomeAccountamtlist=getInParams(eaccAccountamtlist);
//            incomeAccountamtlist.setPlatcust(eaccAccountamtlist.getOppo_platcust());
//            incomeAccountamtlist.setOppo_platcust(eaccAccountamtlist.getPlatcust());
//            eaccAccountamtlist.setAmt_type(AMTTYPE.Expense.getCode());
//            incomeAccountamtlist.setAmt_type(AMTTYPE.Income.getCode());
//            //出金账户和入金账户都已经找到了，开始转账
//            if(realOutCashAmt.compareTo(BigDecimal.ZERO)>0 && realOutCashAccount!=null){
//                //虚户现金出金
//                eaccAccountamtlist.setSubject(realOutCashAccount.getSubject());
//                eaccAccountamtlist.setSub_subject(realOutCashAccount.getSub_subject());
//                eaccAccountamtlist.setAmt(realOutCashAmt);
//                incomeAccountamtlist.setSubject(inCashAccount.getSubject());
//                incomeAccountamtlist.setSub_subject(inCashAccount.getSub_subject());
//                incomeAccountamtlist.setAmt(realOutCashAmt);
//                subtract(eaccAccountamtlist,realOutCashAccount.getId());
//                charge(incomeAccountamtlist,inCashAccount.getId());
//            }
//            if(realOutFloatAmt.compareTo(BigDecimal.ZERO)>0 && realOutFloatAccount!=null){
//                //虚户在途出金
//                eaccAccountamtlist.setSubject(realOutFloatAccount.getSubject());
//                eaccAccountamtlist.setSub_subject(realOutFloatAccount.getSub_subject());
//                eaccAccountamtlist.setAmt(realOutFloatAmt);
//                incomeAccountamtlist.setSubject(inFloatAccount.getSubject());
//                incomeAccountamtlist.setSub_subject(inFloatAccount.getSub_subject());
//                incomeAccountamtlist.setAmt(realOutFloatAmt);
//                subtract(eaccAccountamtlist,realOutFloatAccount.getId());
//                charge(incomeAccountamtlist,inFloatAccount.getId());
//            }
//            if(realOutCashEAmt.compareTo(BigDecimal.ZERO)>0 && realOutCashEAccount!=null){
//                //电子账户现金出金
//            }
//            if(realOutFloatEAmt.compareTo(BigDecimal.ZERO)>0 && realOutFloatEAccount!=null){
//                //电子账户在途出金
//            }
//        }catch (Exception e){
//            logger.error("【转账-处理转账】异常",e);
//            if(e instanceof BusinessException){
//                throw new BusinessException(((BusinessException) e).getBaseResponse());
//            }else{
//                throw new BusinessException(BusinessMsg.TRANS_FAIL,
//                        String.format(BusinessMsg.getMsg(BusinessMsg.TRANS_FAIL)+"|%s",e.getMessage()));
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public Boolean charge(EaccAccountamtlist income, Long accountId) throws BusinessException {
//        logger.info("【转账-加款】加款参数："+income.toString());
//        try {
//            //扣款流水将对手账户反向即为加款流水
//            income.setAmt_type(AMTTYPE.Income.getCode());
//            income.setId(accountId);
//            //加款
//            int rows=custAccountSubjectInfoMapper.addAmt(income);
//            if(rows<=0){
//                logger.info(String.format("【转账-加款】加款失败|trans_serial:%s|platcust:%s|amt:%s|id:%s",
//                        income.getTrans_serial(),income.getPlatcust(),income.getAmt(),accountId));
//                BaseResponse baseResponse = new BaseResponse();
//                baseResponse.setRecode(BusinessMsg.SQL_ERROR);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SQL_ERROR)+"：加款失败");
//                throw new BusinessException(baseResponse);
//            }
//
//            //记录流水
//            income.setId(null);
//            logger.info("【转账-加款】记录加款资金流水："+income.toString());
//            accountTransferService.addTransFlow(income);
//        } catch (Exception e) {
//            BaseResponse baseResponse=new BaseResponse();
//            if(e instanceof BusinessException){
//                baseResponse=((BusinessException) e).getBaseResponse();
//            }else{
//                logger.error("【转账-加款】异常：",e);
//                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            }
//            throw new BusinessException(baseResponse);
//        }
//        return true;
//    }
//
//    @Override
//    public Boolean subtract(EaccAccountamtlist expense, Long accountId) throws BusinessException {
//        logger.info("【转账-扣款】扣款参数："+expense.toString());
//        try {
//            //检查可用余额
//            expense.setAmt_type(AMTTYPE.Expense.getCode());
//            expense.setId(accountId);
//
//            int successCount=custAccountSubjectInfoMapper.substractAmt(expense);//传入的id为账户id
//            if(successCount<=0){
//                logger.info(String.format("【转账-扣款】扣款失败，余额不足|trans_serial:%s|platcust:%s|amt:%s|id:%s",
//                        expense.getTrans_serial(),expense.getPlatcust(),expense.getAmt(),accountId));
//                BaseResponse baseResponse = new BaseResponse();
//                baseResponse.setRecode("654321");
//                baseResponse.setRemsg("转账暂时失败，请重试。");
//                throw new BusinessException(baseResponse);
//            }
//
//            //添加转账流水
//            expense.setId(null);
//            logger.info("【转账-扣款】记录扣款资金流水："+expense.toString());
//            accountTransferService.addTransFlow(expense);
//        } catch (Exception e) {
//            BaseResponse baseResponse=new BaseResponse();
//            if(e instanceof BusinessException){
//                baseResponse=((BusinessException) e).getBaseResponse();
//            }else{
//                logger.error("【转账-扣款】异常：",e);
//                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
//            }
//            throw new BusinessException(baseResponse);
//        }
//        return true;
//    }
//
//    private EaccAccountamtlist getInParams(EaccAccountamtlist outEaccAccountamtlist){
//        EaccAccountamtlist inEaccAccountamtlist=new EaccAccountamtlist();
//        inEaccAccountamtlist.setOrder_no(outEaccAccountamtlist.getOrder_no());
//        inEaccAccountamtlist.setTrans_serial(outEaccAccountamtlist.getTrans_serial());
//        inEaccAccountamtlist.setPlat_no(outEaccAccountamtlist.getPlat_no());
//        inEaccAccountamtlist.setPlatcust(outEaccAccountamtlist.getPlatcust());
//        inEaccAccountamtlist.setSubject(outEaccAccountamtlist.getSubject());
//        inEaccAccountamtlist.setSub_subject(outEaccAccountamtlist.getSub_subject());
//        inEaccAccountamtlist.setOppo_platcust(outEaccAccountamtlist.getOppo_platcust());
//        inEaccAccountamtlist.setOppo_subject(outEaccAccountamtlist.getOppo_subject());
//        inEaccAccountamtlist.setOppo_sub_subject(outEaccAccountamtlist.getOppo_sub_subject());
//        inEaccAccountamtlist.setAmt(outEaccAccountamtlist.getAmt());
//        inEaccAccountamtlist.setTrans_code(outEaccAccountamtlist.getTrans_code());
//        inEaccAccountamtlist.setTrans_name(outEaccAccountamtlist.getTrans_name());
//        inEaccAccountamtlist.setTrans_date(outEaccAccountamtlist.getTrans_date());
//        inEaccAccountamtlist.setTrans_time(outEaccAccountamtlist.getTrans_time());
//        return inEaccAccountamtlist;
//    }
//}
