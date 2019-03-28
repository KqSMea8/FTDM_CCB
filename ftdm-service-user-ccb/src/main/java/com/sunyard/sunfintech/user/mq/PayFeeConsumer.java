package com.sunyard.sunfintech.user.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 【功能描述】
 *
 * @author wyc  2018/2/2.
 */
public class PayFeeConsumer implements ChannelAwareMessageListener {


    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Override
    public void onMessage(Message message, Channel channel) {
        TransTransreq transTransReq =  null;
        List< EaccAccountamtlist > eaccAccountamtlist =null;
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            eaccAccountamtlist = MQUtils.getArrayObject(message, EaccAccountamtlist.class);
            logger.info("开始消费orderno="+eaccAccountamtlist.get(0).getOrder_no());
            transTransReq=new TransTransreq();
            transTransReq.  setOrder_no(eaccAccountamtlist.get(0).getOrder_no());
            transTransReq.setTrans_serial(eaccAccountamtlist.get(0).getTrans_serial());
            accountTransferService.batchTransfer(eaccAccountamtlist);

            transTransReq.setStatus(OrderStatus.SUCCESS.getCode());
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(transTransReq);
        } catch (Exception e) {
            logger.error("缴费消费异常：",e);
            e.printStackTrace();
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                logger.error(e);
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            if (transTransReq!=null) {
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                if (eaccAccountamtlist!=null) {
                    transTransReq.setRemark(JSON.toJSONString(eaccAccountamtlist));
                }
                transReqService.insert(transTransReq);
            }
        }
    }
}