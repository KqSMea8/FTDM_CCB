package com.sunyard.sunfintech.account.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.pub.model.TransferMQModel;
import com.sunyard.sunfintech.account.provider.IServiceBackNotifySendService;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.EaccAccountamtlist;
import com.sunyard.sunfintech.dao.entity.TransTransreq;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by terry on 2018/2/3.
 */
public class TransferConsumer extends BaseServiceSimple implements ChannelAwareMessageListener {

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private IServiceBackNotifySendService serviceBackNotifySendService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private ITransferService transferService;

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        TransTransreq transTransreq=null;
        TransferMQModel transferMQModel=null;
        boolean notifyFlag=false;
        try{
            logger.info(String.format("【转账队列消费】接收到消息|处理时间：%s|消息hash:%s", DateUtils.todayStr(),message.hashCode()));
            //转账
            transferMQModel= MQUtils.getObject(message, TransferMQModel.class);
            if(transferMQModel!=null && transferMQModel.getEaccAccountamtlistList()!=null && transferMQModel.getEaccAccountamtlistList().size()>0){
                List<EaccAccountamtlist> eaccAccountamtlistList= transferMQModel.getEaccAccountamtlistListClone();
                logger.info(String.format("【转账队列消费】转账参数|转账条数：%s|params：%s",
                        eaccAccountamtlistList.size(), JSON.toJSONString(eaccAccountamtlistList)));
                EaccAccountamtlist eaccAccountamtlist=eaccAccountamtlistList.get(0);
                transTransreq=transReqService.getTransTransReq(transferMQModel.getBaseRequest().clone(),
                        eaccAccountamtlist.getTrans_code(),eaccAccountamtlist.getTrans_name(),false);
                transTransreq.setOrder_no(eaccAccountamtlist.getOrder_no());
                transTransreq.setTrans_serial(eaccAccountamtlist.getTrans_serial());
                BaseResponse oldBaseResponse=transReqService.insert(transTransreq);
                if(oldBaseResponse!=null){
                    if(!FlowStatus.WILLTRY.getCode().equals(oldBaseResponse.getOrder_status())){
                        logger.info(String.format("【转账队列消费】原单已存在，不进行消费|trans_serial:%s|status:%s",
                                oldBaseResponse.getOrder_no(),oldBaseResponse.getTrans_serial()));
                        return;
                    }
                }else{
                    if(eaccAccountamtlist.getCreate_time()!=null){
                        //第一次消费切消费时间超过4.5小时的消息丢弃
                        Date now=new Date();
                        long useTime=now.getTime()-eaccAccountamtlist.getCreate_time().getTime();
                        if(useTime>=1000*3600*4.5){
                            return;
                        }
                    }
                }
                eaccAccountamtlist.setExt5("异步");
                if(eaccAccountamtlistList.size()>1){
                    accountTransferService.batchTransfer(eaccAccountamtlistList);
                }else if(eaccAccountamtlistList.size()==1){
                    accountTransferService.transfer(eaccAccountamtlist);
                }
                transTransreq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransreq.setReturn_code(BusinessMsg.SUCCESS);
                transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }
            logger.info(String.format("【转账队列消费】消费结束，确认消费|处理时间：%s",DateUtils.todayStr()));
            notifyFlag=true;
        }catch (Exception e){
            logger.error("【转账队列消费】异常|error:",e);
            if(e instanceof BusinessException){
                BaseResponse baseResponse=((BusinessException) e).getBaseResponse();
                if(BusinessMsg.DATEBASE_EXCEPTION.equals(baseResponse.getRecode()) ||
                        BusinessMsg.TRANSFER_FAILED_RETRY.equals(baseResponse.getRecode()) ||
                        BusinessMsg.RUNTIME_EXCEPTION.equals(baseResponse.getRecode())){
                    //数据库操作异常和转账暂时失败可以发起重试，不通知上一层
                    if(transTransreq!=null){
                        transTransreq.setStatus(FlowStatus.WILLTRY.getCode());
                        transTransreq.setReturn_code(baseResponse.getRecode());
                        transTransreq.setReturn_msg(baseResponse.getRemsg());
                    }
                    if(transferMQModel!=null){
                        TransferMQModel notifyTransferMQModel=new TransferMQModel(transferMQModel.getBaseRequest(),transferMQModel.getEaccAccountamtlistList());
                        SingleThreadPool.getThreadPool().execute(new Runnable() {
                            @Override
                            public void run() {
                                //休眠10-100毫秒后丢进队列
                                sleep(100);
                                transferService.transfer(notifyTransferMQModel.getBaseRequest(),notifyTransferMQModel.getEaccAccountamtlistList());
                            }
                        });
                    }
                }else{
                    //真实失败
                    logger.info(String.format("【转账队列消费】转账正常失败|失败原因：%s",baseResponse.getRemsg()));
                    notifyFlag=true;
                    if(transTransreq!=null){
                        transTransreq.setStatus(FlowStatus.FAIL.getCode());
                        transTransreq.setReturn_code(baseResponse.getRecode());
                        transTransreq.setReturn_msg(baseResponse.getRemsg());
                    }
                }
            }else{
                //未知异常
                logger.info("【转账队列消费】转账未知异常，通知上层将订单置为失败");
                notifyFlag=true;
                if(transTransreq!=null){
                    transTransreq.setStatus(FlowStatus.FAIL.getCode());
                    transTransreq.setReturn_code(BusinessMsg.UNKNOW_ERROE);
                    transTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE)+":"+e.getMessage());
                }
            }
        }finally {
            logger.info("【转账队列消费】确认消息");
            //确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            if(transTransreq!=null){
                transReqService.insert(transTransreq);
                if(notifyFlag){
                    logger.info(String.format("【转账队列消费】通知业务层订单状态|params:%s",JSON.toJSONString(transTransreq)));
                    serviceBackNotifySendService.sendMsgToUp(transTransreq);
                }
            }
        }
    }
}
