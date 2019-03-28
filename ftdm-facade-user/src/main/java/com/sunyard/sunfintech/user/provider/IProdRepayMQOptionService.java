package com.sunyard.sunfintech.user.provider;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.user.model.bo.BatchRepayAsynMQEntity;
import com.sunyard.sunfintech.user.model.bo.BatchRepayRequestDetail;
import com.sunyard.sunfintech.user.modelold.bo.BatchRepayRequestDetailAsyn;

import java.util.List;

/**
 * Created by terry on 2017/7/28.
 */
public interface IProdRepayMQOptionService {

    //借款人还款申请添加消息到mq
    public void addBatchRepayAsyn(BatchRepayAsynMQEntity batchRepayAsynMQEntity)throws BusinessException;

    //借款人还款申请消费mq
    public void doBatchRepayAsyn(BatchRepayAsynMQEntity batchRepayAsynMQEntity)throws BusinessException;

    //异步转账之后通知平台等操作
    public void afterBatchRepayAsynNotify(TransTransreq transTransreq) throws BusinessException;

    public void addBorrow(BaseRequest baseRequest, BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn, String notifyURL)throws BusinessException;

    public void doBorrow(BaseRequest baseRequest, BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn,String notifyURL)throws BusinessException;

}
