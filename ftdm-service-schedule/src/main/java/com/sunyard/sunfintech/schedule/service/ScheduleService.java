package com.sunyard.sunfintech.schedule.service;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.custdao.mapper.CustAccountSubjectInfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by KouKi on 2017/6/17.
 */
@CacheConfig(cacheNames = "billCheckService")
@org.springframework.stereotype.Service
public class ScheduleService extends BaseServiceSimple{

    @Autowired
    private ClearResultMapper clearResultMapper;

    @Autowired
    private ClearTransSerialErrorMapper clearTransSerialErrorMapper;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private ClearCheckErrorMapper clearCheckErrorMapper;

    @Autowired
    private CustAccountSubjectInfoMapper custAccountSubjectInfoMapper;

    @Autowired
    private PlatCardinfoMapper platCardinfoMapper;

    @Autowired
    private EaccAccountamtlistMapper eaccAccountamtlistMapper;

    @Autowired
    private RwRechargeMapper rwRechargeMapper;

    @Autowired
    private ClearHolidayMapper clearHolidayMapper;

    @Autowired
    private PlatPaycodeMapper platPaycodeMapper;

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
     * 插入clear_result
     *
     * @param clearResult
     */
    public void insertClearResult(ClearResult clearResult) {
        try {
            clearResultMapper.insert(clearResult);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION);
        }
    }

    /**
     * 插入充值、提现、流水表差错数据
     * @param clearTransSerialError
     * @throws BusinessException
     */
    public void insertClearTransSerialError(ClearTransSerialError clearTransSerialError) throws BusinessException {
        try {
            clearTransSerialErrorMapper.insert(clearTransSerialError);
        } catch (Exception e) {
            throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
        }
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


    /**
     * 查询所有分账户总余额
     * @param plat_no
     * @return
     */
    public String querySubAmt(String plat_no) {
        return custAccountSubjectInfoMapper.querySubAmt(plat_no);
    }

    /**
     * 更新平台卡信息总分账
     * @param plat_no
     * @param today_amt
     * @param subAmt
     * @param remark
     */
    public void updateCheckAll(String plat_no, String today_amt, String subAmt, String remark) {
        PlatCardinfo platCardinfo = new PlatCardinfo();
        platCardinfo.setToday_amt(new BigDecimal(today_amt));
        platCardinfo.setYesterday_amt(new BigDecimal(subAmt));
        platCardinfo.setRemark(remark);
        platCardinfo.setUpdate_time(new Date());
        platCardinfo.setUpdate_by("Schedule");
        PlatCardinfoExample example = new PlatCardinfoExample();
        PlatCardinfoExample.Criteria criteria = example.createCriteria();
        criteria.andPlat_noEqualTo(plat_no);
        criteria.andCard_typeEqualTo(PlatAccType.PLATTRUST.getCode());
        platCardinfoMapper.updateByExampleSelective(platCardinfo, example);
    }

    /**
     * 查询未对账的数据
     * @param date
     * @return
     */
    public List<ClearResult> queryNoCheckedClearResultByDate(Date date){
        ClearResultExample example = new ClearResultExample();
        ClearResultExample.Criteria criteria = example.createCriteria();
        criteria.andClear_dateEqualTo(date);
        criteria.andClear_statusNotEqualTo(ClearStatus.CHECKED.getCode());
        return clearResultMapper.selectByExample(example);
    }

    /**
     * 查询未对账的数据
     * @param date
     * @return
     */
    public List<ClearResult> queryNoClearedClearResultByDate(Date date){
        ClearResultExample example = new ClearResultExample();
        ClearResultExample.Criteria criteria = example.createCriteria();
        criteria.andClear_dateEqualTo(date);
        criteria.andLiquidationNotEqualTo(LiquidationStatus.CHECKED.getCode());
        return clearResultMapper.selectByExample(example);
    }

    public ClearHoliday queryHoliday(String date){
        ClearHolidayExample example = new ClearHolidayExample();
        ClearHolidayExample.Criteria criteria = example.createCriteria();
        criteria.andDateEqualTo(date);
        List<ClearHoliday> list = null;
        try {
            list = clearHolidayMapper.selectByExample(example);
        }catch (Exception e){
            return null;
        }
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public List<ClearResult> queryClearResultByDateAndPlatNo(Date date, String plat_no) {
        ClearResultExample example = new ClearResultExample();
        ClearResultExample.Criteria criteria = example.createCriteria();
        criteria.andClear_dateEqualTo(date);
        criteria.andPlat_noEqualTo(plat_no);
        return clearResultMapper.selectByExample(example);
    }

}
