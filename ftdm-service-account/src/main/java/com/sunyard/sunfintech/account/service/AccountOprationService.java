package com.sunyard.sunfintech.account.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.account.enums.AMTTYPE;
import com.sunyard.sunfintech.account.provider.IAccountOprationService;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.AccountType;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.Ssubject;
import com.sunyard.sunfintech.core.dic.Tsubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.AccOpenconfigMapper;
import com.sunyard.sunfintech.dao.mapper.AccountSubjectInfoMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author heroy
 * @version 2017/4/12
 */
@Service(interfaceClass = IAccountOprationService.class)
@CacheConfig(cacheNames="accountOprationService")
//@org.springframework.stereotype.Service("accountOprationService")
public class AccountOprationService implements IAccountOprationService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AccountSubjectInfoMapper accountSubjectInfoMapper;

    @Autowired
    private AccOpenconfigMapper accOpenconfigMapper;

    @Autowired
    private AccountAssetService accountAssetService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Override
    @Transactional
    public AccountSubjectInfo register(AccountSubjectInfo accountSubjectInfo) throws BusinessException {
        logger.info("开户信息：" + accountSubjectInfo.toString());
        if (StringUtils.isBlank(accountSubjectInfo.getPlat_no())) {
            logger.error("集团平台编号不能为空");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "集团平台编号不能为空");
        }

        if(StringUtils.isBlank(accountSubjectInfo.getAccount_type())) {
            logger.error("账户类型不能为空");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "账户类型不能为空");
        }

        String platcust = accountSubjectInfo.getPlatcust(); //代表集团客户号
        if (StringUtils.isBlank(platcust))
            platcust = SeqUtil.getSeqNum();//集团客户号，需要返回客户

        //验证平台编号是否正确
        AccOpenconfigExample accOpenconfigExample = new AccOpenconfigExample();
        accOpenconfigExample.createCriteria().andPlat_noEqualTo(accountSubjectInfo.getPlat_no());
        List<AccOpenconfig> accOpenconfigList = accOpenconfigMapper.selectByExample(accOpenconfigExample);
        if (null == accOpenconfigList || 0 == accOpenconfigList.size()) {
            logger.error("集团平台编号不存在，或者开户控制表中为空");
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, "集团平台编号不存在，或者开户控制表中为空");
        }

        //如果不是标的账户，全部平台下的账户都开户。否则是开平台下面的账户
        if(!AccountType.Product.getCode().equals(accountSubjectInfo.getAccount_type())){
            //通过集团号获取所有开户信息配置表
            accOpenconfigExample.clear();
            accOpenconfigExample = new AccOpenconfigExample();
            accOpenconfigExample.createCriteria().andMall_noEqualTo(accOpenconfigList.get(0).getMall_no())
                    .andAccount_typeIn(Arrays.asList(accountSubjectInfo.getAccount_type().split(",")));
            accOpenconfigList = accOpenconfigMapper.selectByExample(accOpenconfigExample);
        }else{
            accOpenconfigExample.clear();
            accOpenconfigExample = new AccOpenconfigExample();
            accOpenconfigExample.createCriteria().andPlat_noEqualTo(accountSubjectInfo.getPlat_no())
                    .andAccount_typeEqualTo(AccountType.Product.getCode());
            accOpenconfigList = accOpenconfigMapper.selectByExample(accOpenconfigExample);
        }

        for (AccOpenconfig accOpenconfig : accOpenconfigList) {
            AccountSubjectInfo accountSubjectInfoFor = new AccountSubjectInfo();
            //查询是否已经开了虚拟账户
            //如果是电子账户，账户类型是04，那么palt_no填的是集团编号
            AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
            accountSubjectInfoExample.createCriteria().andPlat_noEqualTo(accOpenconfig.getPlat_no())
                    .andSubjectEqualTo(accOpenconfig.getSubject()).andSub_subjectEqualTo(accOpenconfig.getSub_subject())
                    .andPlatcustEqualTo(platcust).andAccount_typeIn(Arrays.asList(accountSubjectInfo.getAccount_type().split(",")));
            List<AccountSubjectInfo> accountSubjectInfoList = accountSubjectInfoMapper.selectByExample(accountSubjectInfoExample);
            //如果没有开过该虚拟户
            //如果是标的账户
            if (0 == accountSubjectInfoList.size() || "03".equals(accountSubjectInfo.getAccount_type())) {
                //设置账户信息
                accountSubjectInfoFor.setN_balance(BigDecimal.ZERO);
                accountSubjectInfoFor.setT_balance(BigDecimal.ZERO);
                accountSubjectInfoFor.setF_balance(BigDecimal.ZERO);

                accountSubjectInfoFor.setPlat_no(accOpenconfig.getPlat_no());
                accountSubjectInfoFor.setAccount_type(accOpenconfig.getAccount_type());
                accountSubjectInfoFor.setSubject(accOpenconfig.getSubject());
                accountSubjectInfoFor.setSub_subject(accOpenconfig.getSub_subject());
                accountSubjectInfoFor.setPlatcust(platcust); //设置集团客户号
                accountSubjectInfoFor.setMall_platcust(SeqUtil.getSeqNum()); //生成一个平台客户号
                accountSubjectInfoFor.setEnabled(Constants.ENABLED);

                accountSubjectInfoFor.setCreate_time(new Date());
                accountSubjectInfoFor.setCreate_by(SeqUtil.RANDOM_KEY);
                accountSubjectInfoFor.setStatus("1");
                accountSubjectInfoMapper.insert(accountSubjectInfoFor);
            }
        }
        //返回集团客户号
        accountSubjectInfo.setPlatcust(platcust);
        return accountSubjectInfo;
    }

    @Override
    @Transactional
    public Map<String,Object> doTransfer(EaccAccountamtlist expenseAccount) throws BusinessException {
        List<EaccTransTransreqWithBLOBs> eaccTransTransreqList=new ArrayList<>();
        EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
        try {
            logger.info("转账参数："+expenseAccount.toString());
            if(StringUtils.isBlank(expenseAccount.getPlatcust()) || StringUtils.isBlank(expenseAccount.getSubject())
                    || StringUtils.isBlank(expenseAccount.getSub_subject()) || StringUtils.isBlank(expenseAccount.getOppo_platcust())
                    || StringUtils.isBlank(expenseAccount.getOppo_subject()) || StringUtils.isBlank(expenseAccount.getOppo_sub_subject())
                    || expenseAccount.getAmt()==null || StringUtils.isBlank(expenseAccount.getPlat_no())
                    || StringUtils.isBlank(expenseAccount.getTrans_serial()) || StringUtils.isBlank(expenseAccount.getTrans_code())
                    || StringUtils.isBlank(expenseAccount.getTrans_name())){
                logger.info("转账参数错误，请程序员检查代码！！！");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"转账失败");
            }

            if(!TransConsts.REFUND_CODE.equals(expenseAccount.getTrans_code()) && !TransConsts.PRODREPAY_CODE.equals(expenseAccount.getTrans_code())){
                //平台现金转有电子账户的个人现金投资，优先电子账户
                if(Tsubject.CASH.getCode().equals(expenseAccount.getSubject())){
                    if(!Ssubject.INVEST.getCode().equals(expenseAccount.getSub_subject()) &&
                            !Ssubject.FINANCING.getCode().equals(expenseAccount.getSub_subject()) &&
                            !Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject()) &&
                            Ssubject.INVEST.getCode().equals(expenseAccount.getOppo_sub_subject())){
                        AccountSubjectInfo checkEaccount=accountQueryService.queryAccount(null,
                                expenseAccount.getOppo_platcust(),Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
                        if(checkEaccount!=null){
                            expenseAccount.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
                        }
                    }
                }
            }
            //扣款流水将对手账户反向即为加款流水
            BeanUtils.copyProperties(incomeAccount, expenseAccount);
            incomeAccount.setPlatcust(expenseAccount.getOppo_platcust());
            incomeAccount.setOppo_platcust(expenseAccount.getPlatcust());
            expenseAccount.setAmt_type(AMTTYPE.Expense.getCode());
            incomeAccount.setAmt_type(AMTTYPE.Income.getCode());
//			incomeAccount.setSub_subject(expenseAccount.getOppo_sub_subject());
//			incomeAccount.setSubject(expenseAccount.getOppo_subject());
//			incomeAccount.setOppo_subject(expenseAccount.getSubject());
//			incomeAccount.setOppo_sub_subject(expenseAccount.getSub_subject());

            //定义转出现金账户
            AccountSubjectInfo cashExpenseAccountSubjectInfo=null;
            //定义转出在途账户
            AccountSubjectInfo floatExpenseAccountSubjectInfo=null;
            //定义转出现金电子账户
            AccountSubjectInfo eaccountCashExpenseAccountSubject=null;
            //定义转出在途电子账户
            AccountSubjectInfo eaccountFloatExpenseAccountSubject=null;
            //定义转入现金账户
            AccountSubjectInfo cashIncomeAccountSubjectInfo=null;
            //定义转入在途账户
            AccountSubjectInfo floatIncomeAccountSubjectInfo=null;
            //定义转入现金电子账户
            AccountSubjectInfo eaccountCashIncomeAccountSubject=null;
            //定义转入在途电子账户
            AccountSubjectInfo eaccountFloatIncomeAccountSubject=null;
            //定义转出平台号
            String expensePlatNo=null;
            //定义转入平台号
            String incomePlatNo=null;

            //定义转出账户是个人账户还是平台账户
            boolean expenseIsPerson=false;

            //定义集团编号
            String mall_no=null;

            //查询各类账户
            List<AccountSubjectInfo> expenseAccountSubjectInfoList=null;
            if(expenseAccount.getSub_subject().equals(Ssubject.EACCOUNT.getCode())){
                expenseAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
                        null,expenseAccount.getPlatcust(),null,expenseAccount.getSub_subject());
            }else{
                expenseAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
                        expenseAccount.getPlat_no(),expenseAccount.getPlatcust(),null,expenseAccount.getSub_subject());
            }
            if(expenseAccountSubjectInfoList.size()<=0){
                logger.info("【转账】=========出账账户不存在，platcust："+expenseAccount.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"：扣款账户不存在");
            }

            //查询出账现金和在途账户
            for(AccountSubjectInfo account:expenseAccountSubjectInfoList){
                if(!StringUtils.isBlank(account.getSubject()) && Tsubject.CASH.getCode().equals(account.getSubject())){
                    //设置现金账户
                    cashExpenseAccountSubjectInfo=account;
                }else if(!StringUtils.isBlank(account.getSubject()) && Tsubject.FLOAT.getCode().equals(account.getSubject())){
                    //设置在途账户
                    floatExpenseAccountSubjectInfo=account;
                }
                if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
                    mall_no=account.getPlat_no();
                }
            }

            List<AccountSubjectInfo> incomeAccountSubjectInfoList=null;
            if(expenseAccount.getOppo_sub_subject().equals(Ssubject.EACCOUNT.getCode())){
                incomeAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
                        null,expenseAccount.getOppo_platcust(),null,expenseAccount.getOppo_sub_subject());
            }else{
                incomeAccountSubjectInfoList=accountQueryService.queryPlatAccountList(
                        expenseAccount.getPlat_no(),expenseAccount.getOppo_platcust(),null,expenseAccount.getOppo_sub_subject());
            }
            if(incomeAccountSubjectInfoList.size()<=0){
                logger.info("【转账】=========入账账户不存在，platcust："+expenseAccount.getOppo_platcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"加款账户不存在");
            }

            //如果转出子科目为01或02，则为个人账户
            if(expenseAccount.getSub_subject().equals(Ssubject.INVEST.getCode()) ||
                    expenseAccount.getSub_subject().equals(Ssubject.FINANCING.getCode())){
                expenseIsPerson=true;
            }

            //查询入账现金和在途账户
            for(AccountSubjectInfo account:incomeAccountSubjectInfoList){
                if(!StringUtils.isBlank(account.getSubject()) && Tsubject.CASH.getCode().equals(account.getSubject())){
                    //设置现金账户
                    cashIncomeAccountSubjectInfo=account;
                }else if(!StringUtils.isBlank(account.getSubject()) && Tsubject.FLOAT.getCode().equals(account.getSubject())){
                    //设置在途账户
                    floatIncomeAccountSubjectInfo=account;
                }
                if(Ssubject.EACCOUNT.getCode().equals(account.getSub_subject())){
                    mall_no=account.getPlat_no();
                }
            }

            //为了防止查询出一些莫名其妙的账户，却查不出现金和在途账户，判断一下现金和在途账户是否为空
            if(cashExpenseAccountSubjectInfo==null && floatExpenseAccountSubjectInfo==null){
                logger.info("【转账】=========数据库有一些乱七八糟的账户，找不到现金或在途账户，platcust："+
                        expenseAccount.getPlatcust()+"，sub_subject："+expenseAccount.getSub_subject());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"扣款账户不存在，platcust："+expenseAccount.getPlatcust());
            }

            //判断转账优先级
            BigDecimal transAmt=expenseAccount.getAmt();
            BigDecimal cashAmt=BigDecimal.ZERO;
            BigDecimal floatAmt=BigDecimal.ZERO;
            BigDecimal eaccCashAmt=BigDecimal.ZERO;
            BigDecimal eaccFloatAmt=BigDecimal.ZERO;

            if(Tsubject.CASH.getCode().equals(expenseAccount.getSubject())){
                //现金优先，先判断现金账户余额是否充足
                if(cashExpenseAccountSubjectInfo==null || cashExpenseAccountSubjectInfo.getN_balance()==null){
                    logger.info("【转账】=========账户异常，现金优先，找不到现金账户或现金账户可用余额为null");
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"扣款人账户异常");
                }
                if(transAmt.compareTo(cashExpenseAccountSubjectInfo.getN_balance())>0){
                    logger.info("【转账】========虚拟现金余额不足，出账platcust："+expenseAccount.getPlatcust());
                    //如果转账金额大于现金可用余额，设置现金转账金额
                    cashAmt=cashExpenseAccountSubjectInfo.getN_balance();
                    //判断在途账户可用余额是否充足
                    if(floatExpenseAccountSubjectInfo!=null && floatExpenseAccountSubjectInfo.getN_balance()!=null){
                        BigDecimal saleAmt=BigDecimal.ZERO;
                        if(cashAmt.compareTo(BigDecimal.ZERO)>0){
                            saleAmt=transAmt.subtract(cashAmt);
                        }else{
                            saleAmt=transAmt;
                        }
                        if(saleAmt.compareTo(floatExpenseAccountSubjectInfo.getN_balance())>0){
                            logger.info("【转账】========虚拟在途余额不足，出账platcust："+expenseAccount.getPlatcust());
                            //如果在途账户余额不足，设置在途转账金额
                            floatAmt=floatExpenseAccountSubjectInfo.getN_balance();
                            //查询是否存在电子账户
                            if(expenseIsPerson){
                                //如果子科目是投资，必定是用户账户，需查询电子账户
                                eaccountCashExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                        null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
                                        Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
                                eaccountCashIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                        null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
                                        Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
                                if(eaccountCashExpenseAccountSubject!=null && eaccountCashExpenseAccountSubject.getN_balance()!=null){
                                    mall_no=eaccountCashExpenseAccountSubject.getPlat_no();
                                    saleAmt=saleAmt.subtract(floatAmt);
                                    //判断余额是否充足,不充足就抛异常
                                    if(saleAmt.compareTo(eaccountCashExpenseAccountSubject.getN_balance())>0){
                                        eaccCashAmt=eaccountCashExpenseAccountSubject.getN_balance();
                                        logger.info("【转账】========电子现金余额不足，出账platcust："+expenseAccount.getPlatcust());
                                        //判断电子账户在途是否充足
                                        eaccountFloatExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                                null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
                                                Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
                                        eaccountFloatIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                                null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
                                                Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
                                        if(eaccountFloatExpenseAccountSubject!=null && eaccountFloatExpenseAccountSubject.getN_balance()!=null){
                                            saleAmt=saleAmt.subtract(eaccCashAmt);
                                            if(saleAmt.compareTo(eaccountFloatExpenseAccountSubject.getN_balance())>0){
                                                logger.info("【转账】========电子在途余额不足，出账platcust："+expenseAccount.getPlatcust());
                                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                                        "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                                            }
                                            eaccFloatAmt=saleAmt;
                                        }else{
                                            logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                                            throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                                    "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                                        }
                                    }else{
                                        eaccCashAmt=saleAmt;
                                    }
                                }else{
                                    logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                                    throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                            "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                                }
                            }else{
                                logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                        "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                            }
                        }else{
                            floatAmt=saleAmt;
                        }
                    }else{
                        logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                        throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                    }
                }else{
                    cashAmt=transAmt;
                }
            }else{
                //在途优先，先判断在途账户余额是否充足
                if(floatExpenseAccountSubjectInfo==null || floatExpenseAccountSubjectInfo.getN_balance()==null){
                    logger.info("【转账】=========账户异常，现金优先，找不到现金账户或现金账户可用余额为null");
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,"扣款人账户异常");
                }
                if(transAmt.compareTo(floatExpenseAccountSubjectInfo.getN_balance())>0){
                    logger.info("【转账】========虚拟在途余额不足，出账platcust："+expenseAccount.getPlatcust());
                    //如果转账金额大于在途可用余额，设置在途转账金额
                    cashAmt=floatExpenseAccountSubjectInfo.getN_balance();
                    //判断现金账户可用余额是否充足
                    if(cashExpenseAccountSubjectInfo!=null && cashExpenseAccountSubjectInfo.getN_balance()!=null){
                        BigDecimal saleAmt=transAmt.subtract(cashAmt);
                        if(saleAmt.compareTo(cashExpenseAccountSubjectInfo.getN_balance())>0){
                            logger.info("【转账】========虚拟现金余额不足，出账platcust："+expenseAccount.getPlatcust());
                            //如果现金账户余额不足，设置现金转账金额
                            floatAmt=cashExpenseAccountSubjectInfo.getN_balance();
                            //查询是否存在电子账户
                            if(expenseIsPerson){
                                //如果子科目是投资，必定是用户账户，需查询电子账户
                                eaccountFloatExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                        null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
                                        Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
                                eaccountFloatIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                        null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
                                        Tsubject.FLOAT.getCode(),Ssubject.EACCOUNT.getCode());
                                if(eaccountFloatExpenseAccountSubject!=null && eaccountFloatExpenseAccountSubject.getN_balance()!=null){
                                    mall_no=eaccountFloatExpenseAccountSubject.getPlat_no();
                                    saleAmt=saleAmt.subtract(cashAmt);
                                    //判断余额是否充足,不充足就抛异常
                                    if(saleAmt.compareTo(eaccountFloatExpenseAccountSubject.getN_balance())>0){
                                        eaccFloatAmt=eaccountFloatExpenseAccountSubject.getN_balance();
                                        logger.info("【转账】========电子在途余额不足，出账platcust："+expenseAccount.getPlatcust());
                                        //查询电子现金
                                        eaccountCashExpenseAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                                null,expenseAccount.getPlatcust(), AccountType.ElectronicAccount.getCode(),
                                                Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
                                        eaccountCashIncomeAccountSubject=accountQueryService.queryFINANCINGPlatAccount(
                                                null,expenseAccount.getOppo_platcust(),AccountType.ElectronicAccount.getCode(),
                                                Tsubject.CASH.getCode(),Ssubject.EACCOUNT.getCode());
                                        if(eaccountCashExpenseAccountSubject!=null && eaccountCashExpenseAccountSubject.getN_balance()!=null){
                                            saleAmt=saleAmt.subtract(eaccFloatAmt);
                                            if(saleAmt.compareTo(eaccountCashExpenseAccountSubject.getN_balance())>0){
                                                logger.info("【转账】========电子现金余额不足，出账platcust："+expenseAccount.getPlatcust());
                                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                                        "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                                            }
                                            eaccCashAmt=saleAmt;
                                        }else{
                                            logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                                            throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                                    "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                                        }
                                    }else{
                                        eaccFloatAmt=saleAmt;
                                    }
                                }else{
                                    logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                                    throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                            "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                                }
                            }else{
                                logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                                throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                        "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                            }
                        }else{
                            cashAmt=saleAmt;
                        }
                    }else{
                        logger.info("【转账】========余额不足，出账platcust："+expenseAccount.getPlatcust());
                        throw new BusinessException(BusinessMsg.BALANCE_LACK,
                                "出账账户余额不足，出账platcust："+expenseAccount.getPlatcust());
                    }
                }else{
                    floatAmt=transAmt;
                }
            }

            //程序走到这说明账户可用余额充足，但是不知道要从哪些账户出金，需要判断几个出金账户
            if(cashAmt.compareTo(BigDecimal.ZERO)>0 && cashExpenseAccountSubjectInfo!=null){
                //现金账户转账,设置转出账户和转入账户
                if(cashIncomeAccountSubjectInfo!=null){
                    //如果入账账户是用户账户
                    expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,cashExpenseAccountSubjectInfo.getSubject(),
                            cashExpenseAccountSubjectInfo.getSub_subject(),
                            cashIncomeAccountSubjectInfo.getSubject(),
                            cashIncomeAccountSubjectInfo.getSub_subject(),cashAmt);
                }else{
                    logger.info("【转账】========找不到入账现金账户，入账platcust："+expenseAccount.getOppo_platcust());
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT)+"：入账账户异常");
                }
                incomeAccount=accountTransferService.setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
                        expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
                        expenseAccount.getSub_subject(),expenseAccount.getAmt());
                expenseAccount.setPlat_no(cashExpenseAccountSubjectInfo.getPlat_no());
                expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                incomeAccount.setOppo_plat_no(cashExpenseAccountSubjectInfo.getPlat_no());
                incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                //扣款
                accountAssetService.withdraw(expenseAccount);
                //加款
                accountAssetService.charge(incomeAccount);
                if(Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject())
                        || Ssubject.EACCOUNT.getCode().equals(expenseAccount.getOppo_sub_subject())){
                    //TODO 电子账户转账
                    eaccTransTransreqList.add(accountTransferService.eaccountTrans(expenseAccount,mall_no));
                }
            }
            if(floatAmt.compareTo(BigDecimal.ZERO)>0 && floatExpenseAccountSubjectInfo!=null){
                //在途账户转账,设置转出账户和转入账户
                //如果入账账户是平台账户，如果有在途账户
                if(floatIncomeAccountSubjectInfo!=null){
                    //如果是平台标的账户或垫付账户
                    expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,floatExpenseAccountSubjectInfo.getSubject(),
                            floatExpenseAccountSubjectInfo.getSub_subject(),
                            floatIncomeAccountSubjectInfo.getSubject(),
                            floatIncomeAccountSubjectInfo.getSub_subject(),floatAmt);
                    expenseAccount.setOppo_plat_no(floatIncomeAccountSubjectInfo.getPlat_no());
                    incomeAccount.setPlat_no(floatIncomeAccountSubjectInfo.getPlat_no());
                }else{
                    expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,floatExpenseAccountSubjectInfo.getSubject(),
                            cashExpenseAccountSubjectInfo.getSub_subject(),
                            cashIncomeAccountSubjectInfo.getSubject(),
                            cashIncomeAccountSubjectInfo.getSub_subject(),floatAmt);
                    expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                    incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                }
                incomeAccount=accountTransferService.setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
                        expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
                        expenseAccount.getSub_subject(),expenseAccount.getAmt());
                expenseAccount.setPlat_no(floatExpenseAccountSubjectInfo.getPlat_no());
                incomeAccount.setOppo_plat_no(floatExpenseAccountSubjectInfo.getPlat_no());
                //扣款
                accountAssetService.withdraw(expenseAccount);
                //加款
                accountAssetService.charge(incomeAccount);
//				if(Ssubject.EACCOUNT.getCode().equals(expenseAccount.getSub_subject())
//						|| Ssubject.EACCOUNT.getCode().equals(expenseAccount.getOppo_sub_subject())){
//					//TODO 电子账户转账
//					eaccountTrans(expenseAccount,mall_no);
//				}
            }
            //电子账户现金
            if(eaccCashAmt.compareTo(BigDecimal.ZERO)>0 && eaccountCashExpenseAccountSubject!=null){
                if(eaccountCashIncomeAccountSubject!=null){
                    expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,eaccountCashExpenseAccountSubject.getSubject(),
                            eaccountCashExpenseAccountSubject.getSub_subject(),
                            eaccountCashIncomeAccountSubject.getSubject(),
                            eaccountCashIncomeAccountSubject.getSub_subject(),eaccCashAmt);
                    incomeAccount=accountTransferService.setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
                            expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
                            expenseAccount.getSub_subject(),expenseAccount.getAmt());
                    expenseAccount.setOppo_plat_no(eaccountCashIncomeAccountSubject.getPlat_no());
                    incomeAccount.setPlat_no(eaccountCashIncomeAccountSubject.getPlat_no());
                }else{
                    if(cashIncomeAccountSubjectInfo!=null){
                        expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,eaccountCashExpenseAccountSubject.getSubject(),
                                eaccountCashExpenseAccountSubject.getSub_subject(),
                                cashIncomeAccountSubjectInfo.getSubject(),
                                cashIncomeAccountSubjectInfo.getSub_subject(),eaccCashAmt);
                        expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                        incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                    }else if(floatIncomeAccountSubjectInfo!=null) {
                        expenseAccount = accountTransferService.setSubjectAndAmt(expenseAccount, eaccountCashExpenseAccountSubject.getSubject(),
                                eaccountCashExpenseAccountSubject.getSub_subject(),
                                floatIncomeAccountSubjectInfo.getSubject(),
                                floatIncomeAccountSubjectInfo.getSub_subject(), eaccCashAmt);
                        expenseAccount.setOppo_plat_no(floatIncomeAccountSubjectInfo.getPlat_no());
                        incomeAccount.setPlat_no(floatIncomeAccountSubjectInfo.getPlat_no());
                    }
                    incomeAccount=accountTransferService.setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
                            expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
                            expenseAccount.getSub_subject(),expenseAccount.getAmt());
                }
                expenseAccount.setPlat_no(eaccountCashExpenseAccountSubject.getPlat_no());
                incomeAccount.setOppo_plat_no(eaccountCashExpenseAccountSubject.getPlat_no());
                //扣款
                accountAssetService.withdraw(expenseAccount);
                //加款
                accountAssetService.charge(incomeAccount);
                if(Tsubject.CASH.getCode().equals(expenseAccount.getOppo_subject())){
                    //TODO 电子账户转账
                    eaccTransTransreqList.add(accountTransferService.eaccountTrans(expenseAccount,eaccountCashExpenseAccountSubject.getPlat_no()));
                }


            }
            //电子账户在途
            if(eaccFloatAmt.compareTo(BigDecimal.ZERO)>0 && eaccountFloatExpenseAccountSubject!=null){
                if(eaccountFloatIncomeAccountSubject!=null){
                    expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,eaccountFloatExpenseAccountSubject.getSubject(),
                            eaccountFloatExpenseAccountSubject.getSub_subject(),
                            eaccountFloatIncomeAccountSubject.getSubject(),
                            eaccountFloatIncomeAccountSubject.getSub_subject(),eaccFloatAmt);
                    incomeAccount=accountTransferService.setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
                            expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
                            expenseAccount.getSub_subject(),expenseAccount.getAmt());
                    expenseAccount.setOppo_plat_no(eaccountFloatIncomeAccountSubject.getPlat_no());
                    incomeAccount.setPlat_no(eaccountFloatIncomeAccountSubject.getPlat_no());
                }else{
                    if(floatIncomeAccountSubjectInfo!=null){
                        expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,eaccountFloatExpenseAccountSubject.getSubject(),
                                eaccountFloatExpenseAccountSubject.getSub_subject(),
                                floatIncomeAccountSubjectInfo.getSubject(),
                                floatIncomeAccountSubjectInfo.getSub_subject(),eaccFloatAmt);
                        expenseAccount.setOppo_plat_no(floatIncomeAccountSubjectInfo.getPlat_no());
                        incomeAccount.setPlat_no(floatIncomeAccountSubjectInfo.getPlat_no());
                    }else if(cashIncomeAccountSubjectInfo!=null){
                        expenseAccount=accountTransferService.setSubjectAndAmt(expenseAccount,eaccountFloatExpenseAccountSubject.getSubject(),
                                eaccountFloatExpenseAccountSubject.getSub_subject(),
                                cashIncomeAccountSubjectInfo.getSubject(),
                                cashIncomeAccountSubjectInfo.getSub_subject(),eaccFloatAmt);
                        expenseAccount.setOppo_plat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                        incomeAccount.setPlat_no(cashIncomeAccountSubjectInfo.getPlat_no());
                    }
                    incomeAccount=accountTransferService.setSubjectAndAmt(incomeAccount,expenseAccount.getOppo_subject(),
                            expenseAccount.getOppo_sub_subject(),expenseAccount.getSubject(),
                            expenseAccount.getSub_subject(),expenseAccount.getAmt());
                }
                expenseAccount.setPlat_no(eaccountFloatExpenseAccountSubject.getPlat_no());
                incomeAccount.setOppo_plat_no(eaccountFloatExpenseAccountSubject.getPlat_no());
                //扣款
                accountAssetService.withdraw(expenseAccount);
                //加款
                accountAssetService.charge(incomeAccount);
                //TODO 电子账户转账
//				eaccountTrans(expenseAccount,eaccountFloatExpenseAccountSubject.getPlat_no());
            }
        } catch (Exception e) {
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("eaccountTransferList",eaccTransTransreqList);
        List<EaccAccountamtlist> eaccAccountamtlists=new ArrayList<>();
        expenseAccount.setAmt_type(AmtType.EXPENSIVE.getCode());
        eaccAccountamtlists.add(expenseAccount);
        incomeAccount.setAmt_type(AmtType.INCOME.getCode());
        eaccAccountamtlists.add(incomeAccount);
        returnMap.put("virtualTransferList",eaccAccountamtlists);
        return returnMap;
    }

    @Override
    public void deleteAccountSubjectInfoByPlatcust(String platcust) throws BusinessException {
        try {
            logger.info("【借款人募集申请】-->删除已注册成功标的账户-->platcust:"+platcust);
            AccountSubjectInfoExample accountSubjectInfoExample = new AccountSubjectInfoExample();
            AccountSubjectInfoExample.Criteria criteria = accountSubjectInfoExample.createCriteria();
            if (platcust.contains("-") || platcust.contains("BOB"))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            criteria.andPlatcustEqualTo(platcust);
            accountSubjectInfoMapper.deleteByExample(accountSubjectInfoExample);
            logger.info("【借款人募集申请】-->删除已注册成功标的账户成功-->platcust:"+platcust);
        }catch (Exception e){
            logger.error("【借款人募集申请】-->删除已注册成功标的账户异常-->platcust:"+platcust,e);
        }
    }

    @Override
    public Integer updateAccountSubjectInfo(AccountSubjectInfo accountSubjectInfoParams,String where_platcust, String where_plat_no, String where_subject, String where_sub_subject) throws BusinessException {
        if(StringUtils.isEmpty(where_platcust)||StringUtils.isEmpty(where_plat_no)){
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR,BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
        }
        AccountSubjectInfoExample example=new AccountSubjectInfoExample();
        AccountSubjectInfoExample.Criteria criteria=example.createCriteria();
//        criteria.andEnabledEqualTo(Constants.ENABLED);
        criteria.andPlatcustEqualTo(where_platcust);
        criteria.andPlat_noEqualTo(where_plat_no);
        if(!StringUtils.isEmpty(where_subject)){
            criteria.andSubjectEqualTo(where_subject);
        }
        if(!StringUtils.isEmpty(where_sub_subject)){
            criteria.andSub_subjectEqualTo(where_sub_subject);
        }
        if(null == accountSubjectInfoParams.getUpdate_time()) accountSubjectInfoParams.setUpdate_time(new Date());
        if(null == accountSubjectInfoParams.getUpdate_by()) accountSubjectInfoParams.setUpdate_by(SeqUtil.RANDOM_KEY);
        return accountSubjectInfoMapper.updateByExampleSelective(accountSubjectInfoParams,example);
    }

    @Override
    @Transactional
    public boolean backTransfer(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException {
        logger.info("【转账回滚】======="+eaccAccountamtlists);
        for(EaccAccountamtlist eaccAccountamtlist:eaccAccountamtlists){
            eaccAccountamtlist.setTrans_code(TransConsts.REFUND_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.REFUND_NAME);
            if(AmtType.EXPENSIVE.getCode().equals(eaccAccountamtlist.getAmt_type())){
                //如果是转出，现在转入
                accountAssetService.charge(eaccAccountamtlist);
            }else if(AmtType.INCOME.getCode().equals(eaccAccountamtlist.getAmt_type())){
                accountAssetService.withdraw(eaccAccountamtlist);
            }
        }
        return true;
    }
    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateAccountStatus(List<AccountSubjectInfo> subjectInfoList,String enable_status)  throws BusinessException {
        int account_subject_info_result = 0;
        if (null != subjectInfoList && subjectInfoList.size() > 0) {

            for (AccountSubjectInfo accountSubjectInfo : subjectInfoList) {
                accountSubjectInfo.setEnabled(enable_status);
                if(null == accountSubjectInfo.getUpdate_by()) accountSubjectInfo.setUpdate_by(SeqUtil.RANDOM_KEY);
                if(null == accountSubjectInfo.getUpdate_time()) accountSubjectInfo.setUpdate_time(new Date());
                int result = custAccountSubjectInfoMapper.updateAccountSubjectInfoListByIds(accountSubjectInfo);
                account_subject_info_result += result;
            }
        }
        return account_subject_info_result;
    }



}
