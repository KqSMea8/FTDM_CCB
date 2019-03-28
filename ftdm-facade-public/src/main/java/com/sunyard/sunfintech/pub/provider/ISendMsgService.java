package com.sunyard.sunfintech.pub.provider;

import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.pub.model.CcbSendMsgReq;
import com.sunyard.sunfintech.pub.model.MsgModel;

import java.math.BigDecimal;

/**
 * Created by terry on 2017/6/19.
 */
public interface ISendMsgService {

    /**
     * 系统异常，给管理员发送短信
     * @param content
     * @throws BusinessException
     */
    public void sendErrorToAdmin(String content,String plat_no)throws BusinessException;

    /**
     * 添加短信到队列
     * @param msgModel
     * @throws BusinessException
     */
    public void addMsgToQueue(MsgModel msgModel)throws BusinessException;

    /**
     * 查询存管账户余额
     * @param platcust
     * @return
     * @throws BusinessException
     */
    public BigDecimal getAccountAllAmount(String platcust) throws BusinessException;

    /**
     * CCB短信发送
     * @param ccbSendMsgReq
     * @return
     * @throws BusinessException
     */
    public BaseResponse ccbSendMsg(CcbSendMsgReq ccbSendMsgReq)throws BusinessException;

    /**
     * 发送邮件
     * @param mail_subject 邮件主题
     * @param content 邮件内容
     * @throws BusinessException
     */
    public void sendMail(String content, String mail_subject)throws BusinessException;
}
