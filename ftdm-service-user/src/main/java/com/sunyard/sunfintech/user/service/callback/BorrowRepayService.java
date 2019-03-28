package com.sunyard.sunfintech.user.service.callback;

import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.provider.IProdRepayMQOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/11.
 */
@Service
public class BorrowRepayService extends BaseServiceSimple implements  TransRouter {
    @Autowired
    private ITransReqService transReqService;
    @Autowired
    private IProdRepayMQOptionService prodRepayMQOptionService;
    @Override
    public void onCallBack(TransTransreq transTransreq) {
        transReqService.insert(transTransreq);
        logger.info(String.format("【User异步通知接收-借款人还款申请】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。更新成功",
                transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg()));
        prodRepayMQOptionService.afterBatchRepayAsynNotify(transTransreq);
    }
}
