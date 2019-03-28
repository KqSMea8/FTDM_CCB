package com.sunyard.sunfintech.billcheck.service;

import com.sunyard.sunfintech.billcheck.model.ClearModel;
import com.sunyard.sunfintech.billcheck.model.bo.ClearAccount;
import com.sunyard.sunfintech.billcheck.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.AmtType;
import com.sunyard.sunfintech.core.dic.SwitchState;
import com.sunyard.sunfintech.core.dic.Tsubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.AccountSubjectInfo;
import com.sunyard.sunfintech.dao.entity.ClearAccountError;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.mapper.ClearAccountErrorMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Date;

/**
 * Created by djh on 2017/6/24.
 * 清算
 */
@CacheConfig(cacheNames = "accountClearService")
@org.springframework.stereotype.Service
public class AccountClearService extends BaseServiceSimple  {



    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Autowired
    private ClearAccountErrorMapper clearAccountErrorMapper;

    @Autowired
    private IAccountTransferService accountTransferService;


    public int clearN_balance(ClearAccount account, ClearModel clearModel) {
        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setPlat_no(clearModel.getPlat_no());
        eaccAccountamtlist.setAmt(account.getN_balance());
        eaccAccountamtlist.setPlatcust(account.getPlatcust());
        eaccAccountamtlist.setSub_subject(account.getSub_subject());
        eaccAccountamtlist.setSubject(Tsubject.FLOAT.getCode());
        eaccAccountamtlist.setOppo_platcust(account.getPlatcust());
        eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setOppo_sub_subject(account.getSub_subject());
        eaccAccountamtlist.setTrans_date(DateUtils.format(new Date(), "yyyyMMdd"));
        eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
        eaccAccountamtlist.setTrans_code(TransConsts.CLEAR_N_CODE);
        eaccAccountamtlist.setTrans_name(TransConsts.CLEAR_N_NAME);
        eaccAccountamtlist.setAccount_date(clearModel.getParsedClear_date());
        EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
        eaccAccountamtlist.setSwitch_amt(eaccAccountamtlist.getAmt());
        eaccAccountamtlist.setSwitch_state(SwitchState.ALLSWITCH.getCode());
        eaccAccountamtlist.setOrder_no("");
        try {
            BeanUtils.copyProperties(incomeAccount, eaccAccountamtlist);
            incomeAccount.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            incomeAccount.setOppo_platcust(eaccAccountamtlist.getPlatcust());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
            incomeAccount.setAmt_type(AmtType.INCOME.getCode());
            incomeAccount.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());
            incomeAccount.setSubject(eaccAccountamtlist.getOppo_subject());
            incomeAccount.setOppo_subject(eaccAccountamtlist.getSubject());
            incomeAccount.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
            //logger.debug("【转账】==========incomeAccount：" + incomeAccount.toString() + "\n\n");
            if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccAccountamtlist.setUpdate_time(new Date());
            int flag = custAccountSubjectInfoMapper.substractAmtByUnionKeyNoNeg(eaccAccountamtlist);
            if(flag == 0){
                account.insConcurrenceFailTimes();
                return 0;//该账户清算失败，记录重新查询之后在清算
            }
            if(null == incomeAccount.getUpdate_by()) incomeAccount.setUpdate_by(SeqUtil.RANDOM_KEY);
            incomeAccount.setUpdate_time(new Date());
            int flagAdd =  custAccountSubjectInfoMapper.addAmtByUnionKey(incomeAccount);
            if(flagAdd == 0){
               throw new BusinessException("账户加款异常："+incomeAccount);
            }
            accountTransferService.addTransFlow(eaccAccountamtlist);
            accountTransferService.addTransFlow(incomeAccount);
            return flag;
        } catch (Exception e) {
            account.insErrorFailTimes();
            throw new BusinessException(e);
        }
    }


    //@Transactional
    public int clearFloat(AccountSubjectInfo accountSubjectInfo) {
        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setPlat_no(accountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setAmt(accountSubjectInfo.getN_balance());
        eaccAccountamtlist.setPlatcust(accountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setSub_subject(accountSubjectInfo.getSub_subject());
        eaccAccountamtlist.setSubject(Tsubject.FLOAT.getCode());
        eaccAccountamtlist.setOppo_platcust(accountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setOppo_sub_subject(accountSubjectInfo.getSub_subject());
        eaccAccountamtlist.setTrans_date(DateUtils.format(new Date(), "yyyyMMdd"));
        eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
        eaccAccountamtlist.setTrans_code(TransConsts.CLEAR_N_CODE);
        eaccAccountamtlist.setTrans_name(TransConsts.CLEAR_N_NAME);
        eaccAccountamtlist.setAccount_date(DateUtils.getNow());
        EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
        eaccAccountamtlist.setSwitch_amt(eaccAccountamtlist.getAmt());
        eaccAccountamtlist.setSwitch_state(SwitchState.ALLSWITCH.getCode());
        eaccAccountamtlist.setOrder_no("");
        try {
            BeanUtils.copyProperties(incomeAccount, eaccAccountamtlist);
            incomeAccount.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            incomeAccount.setOppo_platcust(eaccAccountamtlist.getPlatcust());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
            incomeAccount.setAmt_type(AmtType.INCOME.getCode());
            incomeAccount.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());
            incomeAccount.setSubject(eaccAccountamtlist.getOppo_subject());
            incomeAccount.setOppo_subject(eaccAccountamtlist.getSubject());
            incomeAccount.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
            //logger.debug("【转账】==========incomeAccount：" + incomeAccount.toString() + "\n\n");
            if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccAccountamtlist.setUpdate_time(new Date());
            int flag = custAccountSubjectInfoMapper.substractAmtByUnionKeyNoNeg(eaccAccountamtlist);
            if(flag == 0){
                return 0;//该账户清算失败，记录重新查询之后在清算
            }
            if(null == incomeAccount.getUpdate_by()) incomeAccount.setUpdate_by(SeqUtil.RANDOM_KEY);
            incomeAccount.setUpdate_time(new Date());
            int flagAdd =  custAccountSubjectInfoMapper.addAmtByUnionKey(incomeAccount);
            if(flagAdd == 0){
                throw new BusinessException("账户加款异常："+incomeAccount);
            }
            accountTransferService.addTransFlow(eaccAccountamtlist);
            accountTransferService.addTransFlow(incomeAccount);
            return flag;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 清算冻结金额
     * @param account 账户
     * @param clearModel 清算对象
     * @return  修改在途资金是否成功（0-不成功，1-成功）
     */
    public int clearF_balance(ClearAccount account,ClearModel clearModel) {
        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
        eaccAccountamtlist.setPlat_no(clearModel.getPlat_no());
        eaccAccountamtlist.setAmt(account.getF_balance());
        eaccAccountamtlist.setAccount_date(clearModel.getParsedClear_date());
        eaccAccountamtlist.setPlatcust(account.getPlatcust());
        eaccAccountamtlist.setSub_subject(account.getSub_subject());
        eaccAccountamtlist.setSubject(Tsubject.FLOAT.getCode());
        eaccAccountamtlist.setOppo_platcust(account.getPlatcust());
        eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
        eaccAccountamtlist.setOppo_sub_subject(account.getSub_subject());
        eaccAccountamtlist.setTrans_date(DateUtils.format(new Date(), "yyyyMMdd"));
        eaccAccountamtlist.setTrans_time(DateUtils.format(new Date(), "HHmmss"));
        eaccAccountamtlist.setTrans_code(TransConsts.CLEAR_F_CODE);
        eaccAccountamtlist.setTrans_name(TransConsts.CLEAR_F_NAME);
        eaccAccountamtlist.setSwitch_amt(eaccAccountamtlist.getAmt());
        eaccAccountamtlist.setSwitch_state(SwitchState.ALLSWITCH.getCode());
        EaccAccountamtlist incomeAccount = new EaccAccountamtlist();
        eaccAccountamtlist.setOrder_no("");
        try {
            BeanUtils.copyProperties(incomeAccount, eaccAccountamtlist);
            incomeAccount.setPlatcust(eaccAccountamtlist.getOppo_platcust());
            incomeAccount.setOppo_platcust(eaccAccountamtlist.getPlatcust());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());
            incomeAccount.setAmt_type(AmtType.INCOME.getCode());
            incomeAccount.setSub_subject(eaccAccountamtlist.getOppo_sub_subject());
            incomeAccount.setSubject(eaccAccountamtlist.getOppo_subject());
            incomeAccount.setOppo_subject(eaccAccountamtlist.getSubject());
            incomeAccount.setOppo_sub_subject(eaccAccountamtlist.getSub_subject());
            if(null == eaccAccountamtlist.getUpdate_by()) eaccAccountamtlist.setUpdate_by(SeqUtil.RANDOM_KEY);
            eaccAccountamtlist.setUpdate_time(new Date());
            int flag = custAccountSubjectInfoMapper.clearFloatFrozenNoNeg(eaccAccountamtlist);
            if(flag == 0){
                account.insConcurrenceFailTimes();
                return flag;//清算失败
            }
            if(null == incomeAccount.getUpdate_by()) incomeAccount.setUpdate_by(SeqUtil.RANDOM_KEY);
            incomeAccount.setUpdate_time(new Date());
            custAccountSubjectInfoMapper.clearCashFrozen(incomeAccount);
            accountTransferService.addTransFlow(eaccAccountamtlist);
            accountTransferService.addTransFlow(incomeAccount);
            return  flag;
        } catch (Exception e) {
            account.insErrorFailTimes();
            throw new BusinessException(e);
        }
    }



    /**
     * 无法清算的账户
     * @param account    账户
     * @param clearModel 清算信息
     */
    public void insertClearCheckError(ClearAccount account,ClearModel clearModel){
        ClearAccountError accountError = new ClearAccountError();
        accountError.setPlat_no(clearModel.getPlat_no());
        accountError.setClear_date(clearModel.getClear_date());
        accountError.setPay_code(clearModel.getPay_code());
        accountError.setPlatcust(account.getPlatcust());
        accountError.setSubject(account.getSubject());
        accountError.setSub_subject(account.getSub_subject());
        accountError.setError_fail_times(account.getError_fail_times());
        accountError.setN_balance(account.getN_balance());
        accountError.setF_balance(account.getF_balance());
        accountError.setLast_f_balance(account.getLast_f_balance());
        accountError.setLast_n_balance(account.getLast_n_balance());
        accountError.setFail_type(String.valueOf(account.getFail_type()));
        accountError.setConcurrence_fail_times(account.getConcurrence_fail_times());
        accountError.setCreate_time(new Date());
        clearAccountErrorMapper.insertSelective(accountError);
    }
}
