package com.sunyard.sunfintech.account.mq;

import com.rabbitmq.client.Channel;
import com.sunyard.sunfintech.account.model.bo.BankReverse;
import com.sunyard.sunfintech.account.model.bo.BankReverseResponse;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.EaccTransTransreqWithBLOBs;
import com.sunyard.sunfintech.pub.provider.IEaccTransTransreqService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terry on 2017/9/13.
 */
public class BankReverseConsumer extends BaseServiceSimple implements ChannelAwareMessageListener {
    @Autowired
    private IAccountTransferThirdParty accountTransferThirdParty;

    @Autowired
    private IEaccTransTransreqService eaccTransTransreqService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            System.out.println("接收到消息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            BankReverse bankReverse =  MQUtils.getObject(message, BankReverse.class);
            if(bankReverse!=null){
                String key="CZ"+bankReverse.getOriginal_serial_no();
                Long count= CacheUtil.getCache().increment(key,1);
                logger.info("订单"+bankReverse.getOriginal_serial_no()+"冲正次数："+count);
                Map<String,Object> params=new HashMap<>();
                params.put("partner_id","10000001");
                params.put("partner_trans_date", DateUtils.getyyyyMMddDate());
                params.put("original_serial_no",bankReverse.getOriginal_serial_no());
                params.put("biz_type","HDF");
                params.put("occur_balance",bankReverse.getOccur_balance());
                try{
                    Thread.sleep(10000);
                    BankReverseResponse response=accountTransferThirdParty.bankReverse(params,bankReverse.getMall_no());
                    if(response.getData()==null || !"3".equals(response.getData().get(0).getPay_status())){
                        if(count<=50){
                            logger.info("订单号"+bankReverse.getOriginal_serial_no()+"冲正未成功，加入队列，继续冲正。");
                            accountTransferThirdParty.addReverseToQueue(bankReverse);
//                            CacheUtil.getCache().set(key,count);
                        }else{
                            logger.info("冲正超过50次，冲正失败");
                            CacheUtil.getCache().del(key);
                            EaccTransTransreqWithBLOBs updateEacctransTransreq=new EaccTransTransreqWithBLOBs();
                            updateEacctransTransreq.setStatus(4);//4为冲正失败
                            eaccTransTransreqService.updateFlowByTransSerial(bankReverse.getOriginal_serial_no(),updateEacctransTransreq);
                        }
                    }else{
                        logger.info("冲正成功");
                        //修改电子账户订单状态
                        EaccTransTransreqWithBLOBs updateEacctransTransreq=new EaccTransTransreqWithBLOBs();
                        updateEacctransTransreq.setStatus(3);//3为冲正成功
                        eaccTransTransreqService.updateFlowByTransSerial(bankReverse.getOriginal_serial_no(),updateEacctransTransreq);
                    }
                }catch (Exception e){
                    //重新加入队列
                    if(count<=50){
                        logger.error("冲正异常：",e);
                        accountTransferThirdParty.addReverseToQueue(bankReverse);
                    }else {
                        //重试超过20次，放弃重试
                        logger.info("冲正超过50次，冲正失败");
                        CacheUtil.getCache().del(key);
                        EaccTransTransreqWithBLOBs updateEacctransTransreq=new EaccTransTransreqWithBLOBs();
                        updateEacctransTransreq.setStatus(4);//4为冲正失败
                        eaccTransTransreqService.updateFlowByTransSerial(bankReverse.getOriginal_serial_no(),updateEacctransTransreq);
                    }
                }
            }
        } catch (MessageConversionException e) {
            logger.error("冲正失败：",e);
        }
    }
}
