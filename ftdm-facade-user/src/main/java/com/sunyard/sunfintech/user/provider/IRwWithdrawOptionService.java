package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.dao.entity.RwWithdrawThird;
import com.sunyard.sunfintech.user.model.bo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by PengZY on 2018/1/11.
 */
public interface IRwWithdrawOptionService {

    /**
     * 批量提现
     * @author pzy
     * @param batchWithdrawRequest 参数
     * @return BatchWithdrawResponse
     * @throws BusinessException
     */
    public BatchWithdrawResponse batchWithdraw(BatchWithdrawRequest batchWithdrawRequest)throws BusinessException;

    /**
     * 批量提现   提现申请
     * @author pzy
     * @param withdrawApplicationRequest 参数
     * @return BatchWithdrawResponse
     * @throws BusinessException
     */
    public BatchWithdrawResponse withdrawApplication(WithdrawApplicationRequest withdrawApplicationRequest) throws BusinessException;

    /**
     * 批量提现   提现确认
     * @author pzy
     * @param withdrawAffirmRequest 参数
     * @return BatchWithdrawResponse
     * @throws BusinessException
     */
    public BatchWithdrawResponse withdrawAffirm(WithdrawAffirmRequest withdrawAffirmRequest) throws BusinessException;

    /**
     * 批量提现  发往代付系统
     * @author pzy
     * @param rwWithdraw 提现数据
     * @throws BusinessException
     */
    public void doThirdpartyRwWithdraw(RwWithdraw rwWithdraw)throws BusinessException;

    /**
     * 批量提现  接受异步通知并发送异步通知给平台
     * @author pzy
     * @param notifyEpayAgentPayRealReturnData 提现数据
     * @throws BusinessException
     */
    public boolean doRwWithdrawNotify(NotifyEpayAgentPayRealReturnData notifyEpayAgentPayRealReturnData)throws BusinessException;

    /**
     * 批量提现  查询状态待处理的提现
     * @author pzy
     * @param withdrawNum 提现数量
     * @return List<RwWithdraw>
     * @throws BusinessException
     */
    public List<RwWithdraw> queryRwWithdrawByWaitPay(int withdrawNum)throws BusinessException;

    /**
     * 批量提现  查询处理中的提现
     * @author pzy
     * @param withdrawNum 提现数量
     * @return List<RwWithdraw>
     * @throws BusinessException
     */
    public List<RwWithdraw> queryRwWithdrawByPaying(int withdrawNum)throws BusinessException;

    /**
     * 批量提现  查询处理中的提现发往代付查询具体结果
     * @author pzy
     * @param rwWithdraw 提现数据
     * @throws BusinessException
     */
    public boolean doThirdpartyQueryRwWithdraw(RwWithdraw rwWithdraw)throws BusinessException;

    public List<RwWithdraw> querySuccessRwWithdraw(String date) throws BusinessException;

    /**
     * 查询所有自动退款的提现
     * @param count
     * @return
     */
    public List<RwWithdraw> queryAllAutomaticRefund(int count) throws BusinessException;

    /**
     * 处理退款的提现
     * @param rwWithdraw
     * @return
     */
    public BaseResponse doAutomaticRefund(RwWithdraw rwWithdraw) throws BusinessException;

    /**
     * 需自动加值订单查询
     * @param queryLimit
     * @return
     * @throws BusinessException
     */
    public List<RwWithdraw> queryAutoAddValueOrders(Integer queryLimit)throws BusinessException;

    /**
     * 执行自动加值
     * @param rwWithdraw
     * @throws BusinessException
     */
    public void doAutoAddValueOrders(RwWithdraw rwWithdraw)throws BusinessException;

    /**
     * 需自动加值订单查询
     * @param queryLimit
     * @return
     * @throws BusinessException
     */
    public List<RwWithdraw> queryAutoAddValueProcessingOrders(Integer queryLimit)throws BusinessException;

    /**
     * 执行自动加值
     * @param rwWithdraw
     * @throws BusinessException
     */
    public void doAutoAddValueProcessingOrders(RwWithdraw rwWithdraw)throws BusinessException;

    /**
     * 根据ID和原状态更新充值订单状态
     * @param id
     * @param old_status
     * @param status
     * @throws BusinessException
     */
    public Integer updateRwWithdrawByIdAndStatus(Integer id, String old_status, String status)throws BusinessException;

    /**
     * CCB-状态33
     * 查询状态待处理的提现
     * @author wkq
     * @param withdrawNum 提现数量
     * @return List<RwWithdraw>
     * @throws BusinessException
     */
    public List<RwWithdraw> queryRwWithdrawByWaitSend(int withdrawNum)throws BusinessException;

    /**
     * CCB-执行代发提现
     * @param task
     */
    void doRwWithdrawByWaitSend(RwWithdraw task);

    /**
     * CCB-提现代发异步通知
     * @param map
     * @return
     */
    BaseResponse withdrawSendAsyn(Map<String, Object> map);

    /**
     * 根据流水号和状态查询充值订单
     * @param trans_serial
     * @param pay_status
     * @return
     * @throws BusinessException
     */
    public RwWithdraw getRwWithdrawByTransSerialAndStatus(String trans_serial, String pay_status)throws BusinessException;

    void batchWithdrawTrue(BatchWithdrawRequest batchWithdrawRequest, String remsg);

    /**
     * 插入提现发往支付的信息表
     * @param type      类型0-代发1-自动加值2-代付给用户3-代付给平台
     * @param orderNo   提现订单号
     * @param name      用户名
     * @param cardNo   代付卡号
     * @param thirdNo   流水号
     * @return          发给支付的组装信息
     */
    RwWithdrawThird getRwWithdrawThird(String type, String orderNo, String name, String cardNo, String thirdNo);

    /**
     * 修改提现发往支付的订单状态
     * @param orderNo   订单号
     * @param thirdNo   请求支付的流水号
     * @param status    需要修改的状态
     * @param remark    备注
     */
    void updateRwWithdrawThirdStatus(String orderNo, String thirdNo, String status, String remark);

    /**
     * 根据流水号查询提现代发表信息
     * @param thirdNo   发往支付的流水号
     * @return
     */
    RwWithdrawThird queryRwWithdrawThridByThirdNo(String thirdNo);

    /**
     * 根据订单号查询处理中的提现代发表信息
     * @param orderNo   订单号
     * @return
     */
    RwWithdrawThird queryRwWithdrawThridByOrderNo(String orderNo);

    /**
     * 查询所有代付给平台账户的提现
     * @param catchNum
     * @return
     */
    List<RwWithdrawThird> queryRwWithdrawThrid(int catchNum);

    /**
     * 查询代付给平台账户的提现状态
     * @param task
     */
    void queryPayToPlatStatus(RwWithdrawThird task);

    /**
     * 代付给平台
     * @param rwWithdraw
     * @return
     */
    BaseResponse payToPlat(RwWithdraw rwWithdraw);
}
