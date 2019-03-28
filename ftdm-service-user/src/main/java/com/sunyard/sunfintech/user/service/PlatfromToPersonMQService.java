package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustPlatlimitMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.PlatTransCustMapper;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.*;
import com.sunyard.sunfintech.user.model.bo.BatchTransferToPersonNotifyData;
import com.sunyard.sunfintech.user.model.bo.PlatfromToPersonData;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IPlatformOptionService;
import com.sunyard.sunfintech.user.provider.IPlatfromToPersonMQService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by PengZY on 2017/10/17.
 */
@Service(interfaceClass = IPlatfromToPersonMQService.class)
@CacheConfig(cacheNames = "platfromToPersonMQService")
@org.springframework.stereotype.Service
public class PlatfromToPersonMQService extends BaseServiceSimple implements IPlatfromToPersonMQService {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IPlatformOptionService platformOptionService;

    @Autowired
    private PlatTransCustMapper platTransCustMapper;

    @Autowired
    private CustPlatlimitMapper custPlatlimitMapper;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Override
    public void addPlatfromToPerson(PlatfromToPersonData platfromToPersonData) throws BusinessException {

        if (platfromToPersonData != null) {
//            String destination = "BatchPlatfromToPersonQueue";
//            jmsQueueTemplate.send(destination, new MessageCreator() {
//                @Override
//                public javax.jms.Message createMessage(Session session) throws JMSException {
//                    logger.info("发送消息");
//                    javax.jms.Message message=jmsQueueTemplate.getMessageConverter().toMessage(platfromToPersonData, session);
//                    logger.info("消息发送成功！");
//                    return message;
//                }
//            });
            logger.info("【批量平台转个人】开始发送MQ消息");
            MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "BatchPlatfromToPersonQueue", platfromToPersonData);
            logger.info("【批量平台转个人】消息发送成功");
        }
    }

    @Override
    public void doPlatfromToPerson(TransTransreq transTransreq) throws BusinessException {
        logger.info("【批量平台转个人】，开始消费数据进入转账，参数：" + JSON.toJSON(transTransreq));
        TransTransreq userTransTransreq= transReqService.queryTransTransReqByOrderno(transTransreq.getOrder_no());
        logger.info("【批量平台转个人】开始处理user模块流水：" + JSON.toJSON(userTransTransreq));
        PlatTransCustExample platTransCustExample=new PlatTransCustExample();
        platTransCustExample.createCriteria().andOrder_noEqualTo(userTransTransreq.getOrder_no()).andPlat_noEqualTo(userTransTransreq.getPlat_no());
        List<PlatTransCust> platTransCustList=platTransCustMapper.selectByExample(platTransCustExample);
        if(platTransCustList.size()==0){
            logger.error("【批量平台转个人】无plat_trans_cust记录" );
            return ;
        }
        if(platTransCustList.size()>1){
            logger.error("【批量平台转个人】疑似存在重复plat_trans_cust记录：" + JSON.toJSON(platTransCustList));
            return ;
        }
        PlatTransCust platTransCust=platTransCustList.get(0);

        NotifyData notifyData = new NotifyData();
        notifyData.setMall_no(platTransCust.getMall_no());
        notifyData.setNotifyUrl(userTransTransreq.getNotify_url());

        BatchTransferToPersonNotifyData batchTransferToPersonNotifyData = new BatchTransferToPersonNotifyData();
        batchTransferToPersonNotifyData.setAmount(platTransCust.getTotal_amt());
        batchTransferToPersonNotifyData.setOrder_no(userTransTransreq.getOrder_no());
        batchTransferToPersonNotifyData.setPlatcust(platTransCust.getPlatcust());
        batchTransferToPersonNotifyData.setRecode(BusinessMsg.SUCCESS);
        batchTransferToPersonNotifyData.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        batchTransferToPersonNotifyData.setTrans_date(DateUtils.getyyyyMMddDate());

        if (FlowStatus.FAIL.getCode().equals(transTransreq.getStatus())) {
            //说明转账失败
            logger.info("【平台转个人消费】异常:转账失败，删除plat_trans_cust记录" + transTransreq.getOrder_no());
            //删除
            if(null!=transTransreq.getOrder_no() && !"".equals(transTransreq.getOrder_no())){
                platTransCustMapper.deleteByPrimaryKey(platTransCust.getId());
            }
            userTransTransreq.setStatus(OrderStatus.FAIL.getCode());
            userTransTransreq.setReturn_code(BusinessMsg.FAIL);
            userTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            transReqService.insert(userTransTransreq);

            batchTransferToPersonNotifyData.setOrder_info(OrderStatus.FAIL.getMsg());
            batchTransferToPersonNotifyData.setOrder_status(OrderStatus.FAIL.getCode());
            notifyData.setNotifyContent(JSON.toJSONString(batchTransferToPersonNotifyData, GlobalConfig.serializerFeature));
            logger.info("【批量平台转个人】：发送异步通知，订单号：" + batchTransferToPersonNotifyData.getOrder_no());
            notifyService.addNotify(notifyData);
            return;
        }else if(FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
            //将plat_cust_trans 流水设置为可用状态

            platTransCust.setEnabled(Constants.ENABLED);
            platTransCustMapper.updateByPrimaryKey(platTransCust);

            logger.info("【批量平台转个人】，转账成功，开始修改订单为成功状态");
            //更新订单流水为交易成功
            userTransTransreq.setStatus(OrderStatus.SUCCESS.getCode());
            userTransTransreq.setReturn_code(BusinessMsg.SUCCESS);
            userTransTransreq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transReqService.insert(userTransTransreq);

            batchTransferToPersonNotifyData.setOrder_info(OrderStatus.SUCCESS.getMsg());
            batchTransferToPersonNotifyData.setOrder_status(OrderStatus.SUCCESS.getCode());
            notifyData.setNotifyContent(JSON.toJSONString(batchTransferToPersonNotifyData, GlobalConfig.serializerFeature));
            notifyService.addNotify(notifyData);
        }

        if (!platTransCust.getCause_type().equals("9")) {
            logger.info("【平台转账个人】========发送通知短信给个人");
            //检查用户信息是否存在
            logger.info("检查用户信息是否存在");
            EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(platTransCust.getMall_no(), platTransCust.getPlat_no(), platTransCust.getPlatcust());
            if (eaccUserInfo == null) {
                logger.info("用户信息不存在" + "用户信息账号：mallcust：" + platTransCust.getPlatcust());

                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",用户账号不存在");
            }
            logger.info("用户信息存在");
            MsgModel msgModel = new MsgModel();
            String mall_name = sysParameterService.querySysParameter(platTransCust.getMall_no(),
                    MsgContent.MALL_NAME_KEY.getMsg());
            msgModel.setOrder_no(userTransTransreq.getOrder_no());
            msgModel.setPlat_no(userTransTransreq.getPlat_no());
            msgModel.setTrans_code(userTransTransreq.getTrans_code());
            msgModel.setMobile(eaccUserInfo.getMobile());
            String content=sysParameterService.querySysParameter(transTransreq.getExt1(),MsgContent.REPAY_NOTIFY.getMsg());
            if(StringUtils.isNotBlank(content)){
                msgModel.setMsgContent(String.format(content,
                        mall_name, NumberUtils.formatNumber(platTransCust.getTotal_amt()), mall_name));
                //=========ccb参数===========
                msgModel.setTrans_serial(userTransTransreq.getTrans_serial());
                msgModel.setMsg_type(MsgType.REPAY_NOTIFY.getType());
                msgModel.setAmount(platTransCust.getTotal_amt());
                //===========================
                sendMsgService.addMsgToQueue(msgModel);
            }
        }

    }
}
