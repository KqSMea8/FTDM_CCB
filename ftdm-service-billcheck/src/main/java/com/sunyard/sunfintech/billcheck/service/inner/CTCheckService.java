package com.sunyard.sunfintech.billcheck.service.inner;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ClearCheckErrorMapper;
import com.sunyard.sunfintech.dao.mapper.EaccAccountamtlistMapper;
import com.sunyard.sunfintech.dao.mapper.RwRechargeMapper;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by djh on 2018/4/8.
 */
@CacheConfig(cacheNames = "cTCheckService")
@org.springframework.stereotype.Service
public class CTCheckService extends BaseServiceSimple{
    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private ClearCheckErrorMapper clearCheckErrorMapper;

    /**
     * 查询所有充值提现数据
     * @param account_date
     * @return
     */
    public List<EaccAccountamtlist> queryCTListByAccountDate(Date account_date, List<String> trans_codes) {
        List<EaccAccountamtlist> eaccAccountamtlists;
        try {
            EaccAccountamtlistExample example = new EaccAccountamtlistExample();
            EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
            criteria.andAccount_dateEqualTo(account_date);
            criteria.andTrans_codeIn(trans_codes);
/*            if (isWithdraw){
                criteria.andAmt_typeEqualTo(AmtType.EXPENSIVE.getCode());
                List<String> subSubject = Arrays.asList(Ssubject.INVEST.getCode(),Ssubject.FINANCING.getCode());
                criteria.andSub_subjectIn(subSubject);
                criteria.andSubjectEqualTo(Tsubject.CASH.getCode());
            }*/
            //example.setDistinct(true);要用distinct再怎么也先重写一下equals方法
            eaccAccountamtlists = eaccAccountamtlistMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
        return eaccAccountamtlists;
    }

    /**
     * 查询所有成功的充值记录
     * @param date
     * @return
     * @throws BusinessException
     */
    public List<RwRecharge> querySuccessRecharge(String date) throws BusinessException {
        RwRechargeExample example = new RwRechargeExample();
        RwRechargeExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(OrderStatus.SUCCESS.getCode());
        criteria.andPayment_dateEqualTo(date);
        List<RwRecharge> recharges = rwRechargeMapper.selectByExample(example);
        return recharges;
    }

    /**
     * 插入充值、提现、流水表差错数据
     * @param clearCheckError
     * @throws BusinessException
     */
    public void insertClearCheckError(ClearCheckError clearCheckError) throws BusinessException {
        try {
            clearCheckErrorMapper.insert(clearCheckError);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
    }

    /**
     * 查询成功的提现
     * @author pzy
     * @param date 参数
     * @throws BusinessException
     */
    public List<RwWithdraw> querySuccessRwWithdraw(String date) throws BusinessException {
        RwWithdrawExample example = new RwWithdrawExample();
        RwWithdrawExample.Criteria criteria = example.createCriteria();
        criteria.andPay_statusEqualTo(PayStatus.SUCCESS.getCode());
        criteria.andPayment_dateEqualTo(date);
        List<RwWithdraw> rwWithdraws = rwWithdrawMapper.selectByExample(example);
        return rwWithdraws;
    }

    /**
     * 根据流水号查询资金流水
     */
    public List<EaccAccountamtlist> queryAmtListByTransSerial(String trans_serial){
        EaccAccountamtlistExample example = new EaccAccountamtlistExample();
        EaccAccountamtlistExample.Criteria criteria = example.createCriteria();
        criteria.andTrans_serialEqualTo(trans_serial);
        criteria.andEnabledEqualTo(Constants.ENABLED);
        return eaccAccountamtlistMapper.selectByExample(example);
    }

    /**
     * 修改流水表的帐期
     * @param eaccAccountamtlist
     */
    public void updateAccountDate(EaccAccountamtlist eaccAccountamtlist) {
        eaccAccountamtlistMapper.updateByPrimaryKeySelective(eaccAccountamtlist);
    }
}
