package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransChangeCard;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.dao.entity.TransTransreqExample;

import java.util.List;

/**
 * Created by terry on 2017/5/11.
 */
public interface ITransReqService {

    /**
     * insert操作发送流水的插入和更新到MQ
     *
     * @param transTransReq
     * @return null：落单成功 3：落单失败，参数错误 0：订单处理中，无需重复落单 1：订单处理成功，无需重复落单 2：订单处理失败，无需重复落单
     * @throws BusinessException
     */
    public BaseResponse insert(TransTransreq transTransReq) throws BusinessException;

    /**
     * 流水的插入和更新操作，由MQ调用
     *
     * @param transTransReq
     * @return
     * @throws BusinessException
     */
    public boolean addFlow(TransTransreq transTransReq);

    /**
     * 获取TransTransReq的基本信息
     *
     * @param baseRequest
     * @param batchFlag   是否是批量流水 true：是 false：不是
     * @return
     */
    public TransTransreq getTransTransReq(BaseRequest baseRequest, String trans_code, String trans_name, boolean batchFlag);

    /**
     * 根据批量流水和平台客户号查询单个流水记录
     *
     * @param batchOrderNo
     * @return
     * @throws BusinessException
     */
    public List<TransTransreq> queryTransTransReq(String batchOrderNo) throws BusinessException;

    /**
     * 根据订单号查询单个流水记录
     *
     * @param order_no
     * @return
     * @throws BusinessException
     */
    public TransTransreq queryTransTransReqByOrderno(String order_no) throws BusinessException;

    /**
     * 根据订单号查询单个流水记录（不抛异常）
     *
     * @param order_no
     * @return TransTransreq
     */
    public TransTransreq queryTransReqByOrderNo(String order_no);

    /**
     * 根据流水号和订单号查询流水
     *
     * @param trans_serial
     * @param order_no
     * @return
     * @throws BusinessException
     */
    public TransTransreq queryTransTransReqByOrderNoAndBatchOrderNo(String trans_serial, String order_no) throws BusinessException;

    /**
     * 根据流水号和批量订单号查询流水
     *
     * @param platcust
     * @param order_no
     * @param trans_code
     * @return
     * @throws BusinessException
     */
    public TransTransreq queryTransTransReqByOrdernoAndPlatcust(String order_no, String platcust, String trans_code) throws BusinessException;

    /**
     * 记录短验换卡流水
     *
     * @param transChangeCard
     * @return
     * @throws BusinessException
     */
    public boolean insert(TransChangeCard transChangeCard) throws BusinessException;

    public TransTransreq queryApplyTransreqByConfirmOrderNo(String order_no)throws BusinessException;


    boolean updateOrderInfo(TransTransreq transTransReq,String orderNo);


    List<TransTransreq> queryTransByOriginalOrderno(String originalOrder_no, String trans_code, String excludeStatus, String excludeTransSerial) throws BusinessException;

}
