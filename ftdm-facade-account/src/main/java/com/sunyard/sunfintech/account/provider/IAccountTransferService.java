package com.sunyard.sunfintech.account.provider;


import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author heroy
 * to git
 * @version 2017/4/12
 */
public interface IAccountTransferService {
    /**
     * do转账
     * @param eaccAccountamtlist
     * @return Boolean true：转账成功
     * @throws BusinessException 转账失败，抛异常
     */
    public List<EaccTransTransreqWithBLOBs> doTransfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;
    /**
     * 转账
     * @param eaccAccountamtlist
     * @return Boolean true：转账成功
     * @throws BusinessException 转账失败，抛异常
     */
    public Boolean transfer(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;

    /**
     * 批量转账
     * @param eaccAccountamtlist
     * @return Boolean true：批量转账成功
     * @throws BusinessException 转账失败，抛异常
     */
    public boolean batchTransfer(List<EaccAccountamtlist> eaccAccountamtlist) throws BusinessException;

    /**
     * do反向转账
     * @param eaccAccountamtlist 资金流水对象
     * @return Boolean true：反向转账成功
     * @throws BusinessException 转账失败，抛异常
     */
    public List<EaccTransTransreqWithBLOBs> doTransferRollBack(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;

    /**
     * 反向转账
     * @param eaccAccountamtlist 资金流水对象
     * @return Boolean true：反向转账成功
     * @throws BusinessException 转账失败，抛异常
     */
    public Boolean transferRollBack(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;

    /**
     * 反向批量转账
     * @param eaccAccountamtlists 资金流水对象数组
     * @return Boolean true：反向批量转账成功
     * @throws BusinessException 转账失败，抛异常
     */
    public boolean batchTransferRollBack(List<EaccAccountamtlist> eaccAccountamtlists) throws BusinessException;

    /**
     * 出账、提现专用转账接口
     * @param eaccAccountamtlist
     * @return
     * @throws BusinessException
     */
    public boolean fundTransfer(EaccAccountamtlist eaccAccountamtlist)throws BusinessException;

    /**
     * 批量 出账、提现专用转账接口
     * @param eaccAccountamtlist
     * @return
     * @throws BusinessException
     */
    public boolean batchFundTransfer(List<EaccAccountamtlist> eaccAccountamtlist)throws BusinessException;

    /**
     * 添加交易流水
     * @author PengZY
     * @param eaccAccountamtlist
     */
    public void addTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;

    /**
     * 修改交易流水
     * @author PengZY
     * @param eaccAccountamtlist
     */
    public void updateTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;


    /**
     * 查询交易流水
     * @author PengZY
     * @param eaccAccountamtlist
     */
    public EaccAccountamtlist queryTransFlow(EaccAccountamtlist eaccAccountamtlist) throws BusinessException;

    /**
     * 查询交易流水列表
     * @param eaccAccountamtlist
     * @return
     * @throws BusinessException
     */
    public List<EaccAccountamtlist> queryTransFlowList(EaccAccountamtlist eaccAccountamtlist)throws BusinessException;

    /**
     * 查询交易流水 便于逆向转账
     * @param trans_serial
     * @return List<EaccAccountamtlist>
     * @throws BusinessException
     */
    public boolean queryEaccAccountamtlistByTransSerialAndPlatcust(String trans_serial)throws BusinessException;
    /**
     * 查询交易流水 便于逆向转账
     * @param trans_serial
     * @return List<EaccAccountamtlist>
     * @throws BusinessException
     */
    public boolean rollbackBatchWithdraw(String trans_serial,String detailNo)throws BusinessException;
    /**
     * 查询交易流水修改账期
     * @param trans_serial
     * @return List<EaccAccountamtlist>
     * @throws BusinessException
     */
    public boolean queryEaccAccountamtlistByTransSerial(String trans_serial,String df_trans_date)throws BusinessException;

    /**
     * 查询交易流水   中间户减款
     * @param eaccAccountamtlist
     * @return boolean
     * @throws BusinessException
     */
    public boolean queryEaccAccountamtlistWithdrawByTransSerial(EaccAccountamtlist eaccAccountamtlist)throws BusinessException;

    /**
     * 还款退款
     * @param inAccount
     * @param outAccount
     * @return
     * @throws BusinessException
     */
    public boolean refundBack(String inAccount,String outAccount)throws BusinessException;

    public void transactionTest(String platcust, BigDecimal n_balance)throws BusinessException;

    public boolean pay(EaccTransTransreqWithBLOBs eaccTransTransreq)throws BusinessException;

    public boolean batchPay(List<EaccTransTransreqWithBLOBs> eaccTransTransreqList)throws BusinessException;

    public boolean batchCollection(List<EaccTransTransreqWithBLOBs> eaccTransTransreqList)throws BusinessException;

    public EaccAccountamtlist setSubjectAndAmt(EaccAccountamtlist expenseAccount, String subject,String sub_subject,
                                               String oppo_subject,String oppo_sub_subject,BigDecimal amt)throws BusinessException;

    public EaccTransTransreqWithBLOBs eaccountTrans(EaccAccountamtlist eaccAccountamtlist,String mall_no)throws BusinessException;

    /**
     * 根据流水号查资金流水
     * @param trans_serial
     * @return
     * @throws BusinessException
     */
    public List<EaccAccountamtlist> queryEaccAccountamtlistByTransSerial(String trans_serial)throws BusinessException;

    /**
     * 账户加款
     *
     * @param eaccAccountamtlist
     * @return Boolean   成功true   失败false
     */
    public Boolean doCharge(EaccAccountamtlist eaccAccountamtlist);

}
