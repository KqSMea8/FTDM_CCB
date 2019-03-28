package com.sunyard.sunfintech.account.provider;

import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;

/**
 * Created by terry on 2018/1/29.
 */
public interface IServiceBackNotifySendService {

    //给上一级模块发送异步通知
    public void sendMsgToUp(TransTransreq transTreanreq)throws BusinessException;

}
