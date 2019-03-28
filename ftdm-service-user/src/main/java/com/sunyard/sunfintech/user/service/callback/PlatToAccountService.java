package com.sunyard.sunfintech.user.service.callback;

import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.provider.IPlatfromToPersonMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/11.
 */
@Service
public class PlatToAccountService extends BaseServiceSimple implements  TransRouter {
    @Autowired
    private ITransReqService transReqService;
    @Autowired
    private IPlatfromToPersonMQService platfromToPersonMQService;
    @Override
    public void onCallBack(TransTransreq transTransreq) {
        logger.info("【User异步通知接收】接收到平台转个人消息|trans_serial:%s|status:%s|return_code:%s|return_msg:%",
                transTransreq.getTrans_serial(), transTransreq.getStatus(), transTransreq.getReturn_code(), transTransreq.getReturn_msg());
        platfromToPersonMQService.doPlatfromToPerson(transTransreq);
    }
}
