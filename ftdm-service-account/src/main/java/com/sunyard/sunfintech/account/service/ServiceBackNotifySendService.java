package com.sunyard.sunfintech.account.service;

import com.sunyard.sunfintech.account.provider.IServiceBackNotifySendService;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by terry on 2018/1/30.
 */
@Service
public class ServiceBackNotifySendService extends BaseServiceSimple implements IServiceBackNotifySendService {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMsgToUp(TransTransreq transTreanreq) throws BusinessException {
        logger.info(String.format("向User发送模块间异步通知|order_no:%s|trans_serial:%s|status:%s|return_code:%s|return_msg:%s",
                transTreanreq.getOrder_no(), transTreanreq.getTrans_serial(), transTreanreq.getStatus(), transTreanreq.getReturn_code(), transTreanreq.getReturn_msg()));
        MQUtils.send(amqpTemplate, "ftdm.notify.topic.exchange", "fromAccount.toUserOrProd", transTreanreq);

    }
}
