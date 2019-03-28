package com.sunyard.sunfintech.prod.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.PayEaccountBind;
import com.sunyard.sunfintech.dao.entity.PayTransSerialTransfer;
import com.sunyard.sunfintech.dao.entity.ProdRefundReqData;
import com.sunyard.sunfintech.prod.model.bo.ProdBatchRepayOldRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdBusinessData;

import java.util.List;

/**
 * Created by terry on 2018/7/19.
 */
public interface IProductRefundV2Service {

    /**
     * 一借多贷标的批量还款
     * @author Terry
     * @param prodBatchRepayRequest
     * @return
     * @throws BusinessException 抛出的异常
     */
    public List<ProdBusinessData> batchRefund(ProdBatchRepayOldRequest prodBatchRepayRequest)throws BusinessException;

    /**
     * 插入还款数据
     * @param prodRefundReqData
     * @return
     * @throws BusinessException
     */
    public Integer insertProdRefundReqData(ProdRefundReqData prodRefundReqData)throws BusinessException;

    /**
     * 根据状态查询还款数据
     * @param limit
     * @return
     * @throws BusinessException
     */
    public List<ProdRefundReqData> queryProdRefundReqData(Integer limit, String... queryStatus)throws BusinessException;

    /**
     * 根据当前状态更新还款数据状态
     * @param dataId
     * @param nowStatus
     * @param nextStatus
     * @return
     * @throws BusinessException
     */
    public Integer updateProdRefundReqDataStatus(Integer dataId,String nowStatus,String nextStatus)throws BusinessException;

    /**
     * 插入支付映射流水信息
     * @param payTransSerialTransfer
     * @return
     * @throws BusinessException
     */
    public Integer insertPayTransSerialTransfer(PayTransSerialTransfer payTransSerialTransfer)throws BusinessException;

    /**
     * 查询支付映射流水信息
     * @param pay_trans_serial
     * @param trans_serial
     * @return
     * @throws BusinessException
     */
    public List<PayTransSerialTransfer> queryPayTransSerialTransfer(String pay_trans_serial,String trans_serial)throws BusinessException;

    /**
     * 插入电子账户号和订单的绑定关系
     * @param payEaccountBind
     * @return
     * @throws BusinessException
     */
    public Integer insertPayEaccountBind(PayEaccountBind payEaccountBind)throws BusinessException;

    /**
     * 根据电子账户号和流水号查询订单
     * @param trans_serial
     * @param eaccount
     * @return
     * @throws BusinessException
     */
    public List<PayEaccountBind> queryPayEaccountBind(String trans_serial,String eaccount,String order_no)throws BusinessException;

    /**
     * 处理还款数据，调用一借多贷
     * @param prodRefundReqDataList
     * @throws BusinessException
     */
    public void doWaitPayRefund(List<ProdRefundReqData> prodRefundReqDataList)throws BusinessException;
}
