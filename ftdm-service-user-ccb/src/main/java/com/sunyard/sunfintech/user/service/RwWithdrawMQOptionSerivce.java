package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustRwWithdrawMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.RwWithdrawMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.user.provider.IRwWithdrawMQOptionSerivce;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.sunyard.sunfintech.core.Constants.REDISKEY_SYS_PARAMETER;

/**
 * Created by PengZY on 2018/1/30.
 */
@Service(interfaceClass = IRwWithdrawMQOptionSerivce.class)
@CacheConfig(cacheNames = "rwWithdrawMQOptionSerivce")
@org.springframework.stereotype.Service
public class RwWithdrawMQOptionSerivce extends BaseServiceSimple implements IRwWithdrawMQOptionSerivce {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private RwWithdrawMapper rwWithdrawMapper;

    @Autowired
    private CustRwWithdrawMapper custRwWithdrawMapper;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Override
    public void addRwWithdrawAffirm(RwWithdrawAffirmMQEntity rwWithdrawAffirmMQEntity) throws BusinessException {

        if (rwWithdrawAffirmMQEntity != null) {
            logger.info("【提现确认】开始发送MQ消息");
            MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "RwWithdrawAffirmQueue", rwWithdrawAffirmMQEntity);
            logger.info("【提现确认】消息发送成功");
        }
    }

    @Override
    public void addBatchRwWithdraw(BatchRwWithdrawMQEntity batchRwWithdrawMQEntity) throws BusinessException {

        if (batchRwWithdrawMQEntity != null) {
            logger.info("【批量提现】开始发送MQ消息");
            MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "BatchRwWithdrawQueue", batchRwWithdrawMQEntity);
            logger.info("【批量提现】消息发送成功");
        }
    }

    @Override
    public void doRwWithdrawAffirm(RwWithdrawAffirmMQEntity rwWithdrawAffirmMQEntity) throws BusinessException {

        logger.info("【提现确认】开始消费-->order_no:"+rwWithdrawAffirmMQEntity.getDateDetailAffirm());

        BaseRequest baseRequest = rwWithdrawAffirmMQEntity.getBaseRequest();
        DateDetailAffirm dateDetail = rwWithdrawAffirmMQEntity.getDateDetailAffirm();
        TransTransreq transTransreqAffirm = transReqService.queryTransTransReqByOrderno(dateDetail.getDetail_no());
        logger.info("【提现确认】查询提现确认流水成功-->order_no:"+dateDetail.getDetail_no());

        //判断清算标识是否开启,如果开启则丢入redis,否则丢入mq
        boolean isChechData = true;
        while(isChechData){
            if (!SortingType.END.getCode().equals(CacheUtil.getCache().get(REDISKEY_SYS_PARAMETER + baseRequest.getMall_no()))) {
                isChechData = false;
            }
            logger.info("【提现确认】清算正在进行-->sleep-->order_no:"+dateDetail.getDetail_no());
            try {
                Thread.sleep((new Random()).nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try{

            boolean flag = CacheUtil.getCache().setnx(Constants.RW_WITHDRAW_REDIS_ORIGIN_ORDER_NO_KEY + dateDetail.getOrigin_order_no(), dateDetail.getOrigin_order_no());
            if (!flag) {
                throw new BusinessException(BusinessMsg.FAIL, "当前正在处理,请不要重新发起确认");
            }
            CacheUtil.getCache().expire(Constants.RW_WITHDRAW_REDIS_ORIGIN_ORDER_NO_KEY + dateDetail.getOrigin_order_no(),2*60);

            TransTransreq transTransreq = null;
            try {
                transTransreq = transReqService.queryTransTransReqByOrderno(dateDetail.getOrigin_order_no());
                logger.info("【提现确认】查询原提现申请流水成功-->order_no:" + dateDetail.getDetail_no());
            }catch (Exception e){
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",无原提现申请");
            }

            if(!OrderStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",原提现申请已失败,请重新发起提现申请");
            }

            RwWithdraw rwWithdrawApp = custRwWithdrawMapper.selectTransSerialAndInitPay(transTransreq.getTrans_serial());
            if(null == rwWithdrawApp){
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",原提现申请记录不存在");
            }
            logger.info("【提现确认】查询原提现申请记录成功-->order_no:" + dateDetail.getDetail_no());

            String overdueTime = sysParameterService.querySysParameter(baseRequest.getMall_no(), SysParamterKey.OVERDUE_TIME);
            if(StringUtils.isBlank(overdueTime)){
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",提现确认超时时间未设置");
            }
            Date date = new Date();
            if((date.getTime()-rwWithdrawApp.getCreate_time().getTime()) > Long.valueOf(overdueTime)*24*60*60*1000){
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",原提现申请已过期(2个月)");
            }

            List<RwWithdraw> queryrwWithdrawOriginOrderNo = custRwWithdrawMapper.selectByOriginOrderNo(dateDetail.getOrigin_order_no());
            if(null != queryrwWithdrawOriginOrderNo && queryrwWithdrawOriginOrderNo.size() > 0){
                for (RwWithdraw rwWithdraw : queryrwWithdrawOriginOrderNo){
                    if(rwWithdraw.getPay_status().equals(PayStatus.SUCCESS.getCode())){
                        throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",提现已确认,请重新发起申请后确认");
                    }else if(!rwWithdraw.getPay_status().equals(PayStatus.SUCCESS.getCode())
                            || !rwWithdraw.getPay_status().equals(PayStatus.FAIL.getCode())){
                        throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",提现正在处理中,无需重新发起提现");
                    }
                }
            }

            if(!dateDetail.getPlatcust().equals(rwWithdrawApp.getPlatcust()))
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致-->确认platcust:【"+dateDetail.getPlatcust()+"】,申请-->platcust:"+rwWithdrawApp.getPlatcust());

            if(dateDetail.getAmt().compareTo(rwWithdrawApp.getOut_amt()) != 0)
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认金额:【"+dateDetail.getAmt()+"】,申请金额-->"+rwWithdrawApp.getOut_amt());

            if(!dateDetail.getPay_code().equals(rwWithdrawApp.getPay_code()))
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认通道:【"+dateDetail.getPay_code()+"】,申请通道-->"+rwWithdrawApp.getPay_code());

            if(dateDetail.getFee_amt().compareTo(rwWithdrawApp.getFee_amt()) != 0)
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认手续费:【"+dateDetail.getFee_amt()+"】,申请手续费-->"+rwWithdrawApp.getFee_amt());

            if(!dateDetail.getCard_no().equals(rwWithdrawApp.getOppo_account()))
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认卡号:【"+dateDetail.getCard_no()+"】,申请卡号-->"+rwWithdrawApp.getOppo_account());

            if (!dateDetail.getName().equals(rwWithdrawApp.getPayee_name()))
                throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",确认数据与申请数据不一致,确认户名:【" + dateDetail.getName() + "】,申请开户名-->" + rwWithdrawApp.getPayee_name());

            if (!dateDetail.getIs_advance().equals(rwWithdrawApp.getIs_advance()))
                throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",确认数据与申请数据不一致,确认垫付类型:【" + dateDetail.getIs_advance() + "】,申请垫付类型-->" + rwWithdrawApp.getIs_advance());

            if(!dateDetail.getClient_property().equals(rwWithdrawApp.getClient_property()))
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认公私标识:【"+dateDetail.getClient_property()+"】,申请公私标识-->"+rwWithdrawApp.getClient_property());

            if(!dateDetail.getWithdraw_type().equals(rwWithdrawApp.getSub_subject()))
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认提现人类型:【"+dateDetail.getWithdraw_type()+"】,申请提现人类型-->"+rwWithdrawApp.getSub_subject());

       /*     if(!dateDetail.getNotify_url().equals(rwWithdrawApp.getNotify_url()))
                throw new BusinessException(BusinessMsg.FAIL,BusinessMsg.getMsg(BusinessMsg.FAIL)+",确认数据与申请数据不一致,确认通知地址:【"+dateDetail.getNotify_url()+"】,申请通知地址-->"+rwWithdrawApp.getNotify_url());
*/

            if(CusType.COMPANY.getCode().equals(rwWithdrawApp.getClient_property())){
                if(StringUtils.isNotBlank(dateDetail.getBank_id())) {
                    if (!dateDetail.getBank_id().equals(rwWithdrawApp.getBank_id()))
                        throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",确认数据与申请数据不一致,确认银行编号:【" + dateDetail.getBank_id() + "】,申请银行编号-->" + rwWithdrawApp.getBank_id());
                }
                if(StringUtils.isNotBlank(rwWithdrawApp.getBank_id())) {
                    if (!rwWithdrawApp.getBank_id().equals(dateDetail.getBank_id()))
                        throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",确认数据与申请数据不一致,确认银行编号:【" + dateDetail.getBank_id() + "】,申请银行编号-->" + rwWithdrawApp.getBank_id());
                }
                if(StringUtils.isNotBlank(dateDetail.getOpen_branch())) {
                    if (!dateDetail.getOpen_branch().equals(rwWithdrawApp.getOpen_branch()))
                        throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",确认数据与申请数据不一致,确认开户行号:【" + dateDetail.getOpen_branch() + "】,申请开户行号-->" + rwWithdrawApp.getOpen_branch());
                }
                if(StringUtils.isNotBlank(rwWithdrawApp.getOpen_branch())) {
                    if (!rwWithdrawApp.getOpen_branch().equals(dateDetail.getOpen_branch()))
                        throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",确认数据与申请数据不一致,确认开户行号:【" + dateDetail.getOpen_branch() + "】,申请开户行号-->" + rwWithdrawApp.getOpen_branch());
                }
            }

            RwWithdrawEaccAccountAmtList rwWithdrawEaccAccountAmtList = getAffirmEaccAccountAmtList(baseRequest,transTransreqAffirm,dateDetail);
            EaccCardinfo eaccCardinfo = rwWithdrawEaccAccountAmtList.getEaccCardinfo();
            EaccUserinfo eaccUserinfo = rwWithdrawEaccAccountAmtList.getEaccUserinfo();
            AccountSubjectInfo accountSubjectInfo = rwWithdrawEaccAccountAmtList.getAccountSubjectInfo();

            logger.info("【批量确认】-->开始转账-->detail_no:" + dateDetail.getDetail_no());
            accountTransferService.batchFundTransfer(rwWithdrawEaccAccountAmtList.getEaccAccountamtlists());
            logger.info("【批量确认】-->转账成功-->detail_no:" + dateDetail.getDetail_no());

            try {

                RwWithdraw rwWithdraw = new RwWithdraw();
                rwWithdraw.setTrans_serial(transTransreqAffirm.getTrans_serial());
                rwWithdraw.setTrans_date(transTransreqAffirm.getTrans_date());
                rwWithdraw.setTrans_time(transTransreqAffirm.getTrans_time());
                rwWithdraw.setPlat_no(baseRequest.getMer_no());
                rwWithdraw.setPlatcust(dateDetail.getPlatcust());
                rwWithdraw.setTrans_code(TransConsts.WITHDRAW_AFFIRM_CODE);
                rwWithdraw.setTrans_name(TransConsts.WITHDRAW_AFFIRM_NAME);
                rwWithdraw.setOut_amt(dateDetail.getAmt());
                rwWithdraw.setSubject(accountSubjectInfo.getSubject());
                rwWithdraw.setSub_subject(accountSubjectInfo.getSub_subject());
                rwWithdraw.setPay_code(dateDetail.getPay_code());
                rwWithdraw.setFee_amt(dateDetail.getFee_amt());
                rwWithdraw.setClient_property(dateDetail.getClient_property());
                rwWithdraw.setCity_code(dateDetail.getCity_code());

                rwWithdraw.setOppo_account(eaccCardinfo.getCard_no());

                if (StringUtils.isNotBlank(eaccUserinfo.getName())) {
                    rwWithdraw.setPayee_name(eaccUserinfo.getName());
                } else {
                    rwWithdraw.setPayee_name(eaccUserinfo.getOrg_name());
                }

                //对个人
                if (rwWithdraw.getClient_property().equals(CusType.PERSONAL.getCode())) {
                    rwWithdraw.setOpen_branch(eaccCardinfo.getOpen_branch());
                    rwWithdraw.setBank_id(eaccCardinfo.getBank_no());
                }

                //对公
                if (rwWithdraw.getClient_property().equals(CusType.COMPANY.getCode())) {
                    rwWithdraw.setOpen_branch(dateDetail.getOpen_branch());
                    rwWithdraw.setBank_id(dateDetail.getBank_id());
                    rwWithdraw.setBrabank_name(dateDetail.getBrabank_name());
                }

                //默认set为借记卡
                rwWithdraw.setCard_type(dateDetail.getCard_type());
                rwWithdraw.setIs_advance(dateDetail.getIs_advance());
                rwWithdraw.setAdvance_amt(rwWithdrawEaccAccountAmtList.getAdvance_amt());
                rwWithdraw.setPay_status(PayStatus.CCB_INIT_PAY.getCode());
                rwWithdraw.setOrder_no(dateDetail.getDetail_no());
                rwWithdraw.setOrigin_order_no(dateDetail.getOrigin_order_no());
                rwWithdraw.setQuery_no_num(0);
                rwWithdraw.setNotify_url(dateDetail.getNotify_url());
                rwWithdraw.setRemark(dateDetail.getRemark());
                rwWithdraw.setExt1(dateDetail.getRemark());
                rwWithdraw.setEnabled(Constants.ENABLED);
                rwWithdraw.setCreate_time(new Date());
                rwWithdraw.setUpdate_time(new Date());

                if(StringUtils.isNotBlank(dateDetail.getCity_code())){
                    rwWithdraw.setCity_code(dateDetail.getCity_code());
                }
                if(StringUtils.isNotBlank(dateDetail.getProvince_code())){
                    rwWithdraw.setProvince_code(dateDetail.getProvince_code());
                }
                rwWithdrawMapper.insert(rwWithdraw);
                logger.info("【提现确认】-->添加提现信息成功-->detail_no:" + dateDetail.getDetail_no());

            }catch (Exception e){
                logger.info("【提现确认】-->修改提现表异常，准备反向回滚转账-->detail_no:" + dateDetail.getDetail_no(),e);
                accountTransferService.queryEaccAccountamtlistByTransSerialAndPlatcust(transTransreq.getTrans_serial());
                throw new BusinessException(BusinessMsg.FAIL,"插入提现异常");
            }

        }catch (Exception e){

            if(e instanceof BusinessException){
                logger.info("【提现确认】-->BusinessException异常-->detail_no:" + dateDetail.getDetail_no(),e);
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();

                CacheUtil.getCache().del(Constants.RW_WITHDRAW_REDIS_ORIGIN_ORDER_NO_KEY + dateDetail.getOrigin_order_no());

                transTransreqAffirm.setStatus(OrderStatus.FAIL.getCode());
                transTransreqAffirm.setReturn_code(baseResponse.getRecode());
                transTransreqAffirm.setReturn_msg(baseResponse.getRemsg());
                transReqService.insert(transTransreqAffirm);

                try {
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(baseRequest.getMall_no());
                    notityPaymentReq.setPlat_no(baseRequest.getMer_no());
                    notityPaymentReq.setPlatcust(dateDetail.getPlatcust());
                    notityPaymentReq.setOrder_no(dateDetail.getDetail_no());
                    notityPaymentReq.setOrder_amt(dateDetail.getAmt());
                    notityPaymentReq.setTrans_date(baseRequest.getPartner_trans_date());
                    notityPaymentReq.setTrans_time(baseRequest.getPartner_trans_time());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                    notityPaymentReq.setPay_amt(dateDetail.getFee_amt().add(dateDetail.getAmt()));
                    notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                    notityPaymentReq.setError_no(OrderStatus.REFUNDPSUCCESS.getCode());
                    notityPaymentReq.setError_info("交易失败，" + baseResponse.getRemsg());
                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    notifyData.setMall_no(baseRequest.getMall_no());
                    notifyData.setNotifyUrl(dateDetail.getNotify_url());
                    notifyService.addNotify(notifyData);
                    logger.info("【提现确认】-->发送异步通知-->order_no:"+dateDetail.getDetail_no());
                }catch (Exception e1){
                    logger.info("【提现确认】发送异步通知异常-->order_no:"+dateDetail.getDetail_no(),e1);
                }

            }else {
                logger.info("【提现确认】-->未知异常，可能存在bug-->detail_no:" + dateDetail.getDetail_no(),e);
            }
        }


    }

    @Override
    public void doBatchRwWithdraw(BatchRwWithdrawMQEntity batchRwWithdrawMQEntity) throws BusinessException {

        BaseRequest baseRequest = batchRwWithdrawMQEntity.getBaseRequest();
        DateDetail dateDetail = batchRwWithdrawMQEntity.getDateDetail();
        /*DateDetailAffirm dateDetailAffirm = new DateDetailAffirm();
        dateDetailAffirm.setDetail_no(dateDetail.getDetail_no());
        dateDetailAffirm.setPlatcust(dateDetail.getPlatcust());
        dateDetailAffirm.setAmt(dateDetail.getAmt());
        dateDetailAffirm.setIs_advance(dateDetail.getIs_advance());
        //dateDetailAffirm.setAdvance_platcust(dateDetail.getAdvance_platcust());
        dateDetailAffirm.setPay_code(dateDetail.getPay_code());
        dateDetailAffirm.setFee_amt(dateDetail.getFee_amt());
        dateDetailAffirm.setWithdraw_type(dateDetail.getWithdraw_type());
        dateDetailAffirm.setClient_property(dateDetail.getClient_property());
        dateDetailAffirm.setCity_code(dateDetail.getCity_code());
        dateDetailAffirm.setNotify_url(dateDetail.getNotify_url());
        dateDetailAffirm.setCard_no(dateDetail.getCard_no());
        dateDetailAffirm.setName(dateDetail.getName());
        dateDetailAffirm.setBank_id(dateDetail.getBank_id());
        dateDetailAffirm.setOpen_branch(dateDetail.getOpen_branch());
        dateDetailAffirm.setCard_type(dateDetail.getCard_type());
        dateDetailAffirm.setProvince_code(dateDetail.getProvince_code());
        dateDetailAffirm.setBrabank_name(dateDetail.getBrabank_name());
        dateDetailAffirm.setRemark(dateDetail.getRemark());*/

        TransTransreq transTransreq = null;
        logger.info("【批量提现】开始消费-->order_no:"+dateDetail.getDetail_no());

        try{
            transTransreq = transReqService.queryTransTransReqByOrderno(dateDetail.getDetail_no());

            RwWithdrawEaccAccountAmtList rwWithdrawEaccAccountAmtList = getEaccAccountAmtList(baseRequest,transTransreq,dateDetail);

            logger.info("【批量提现】-->开始转账-->detail_no:" + dateDetail.getDetail_no());
            accountTransferService.batchFundTransfer(rwWithdrawEaccAccountAmtList.getEaccAccountamtlists());
            logger.info("【批量提现】-->转账成功-->detail_no:" + dateDetail.getDetail_no());

            try {
                //添加提现信息
                logger.info("【批量提现】-->开始添加提现信息-->detail_no:" + dateDetail.getDetail_no());
                RwWithdraw rwWithdraw = new RwWithdraw();
                rwWithdraw.setTrans_serial(transTransreq.getTrans_serial());
                rwWithdraw.setTrans_date(transTransreq.getTrans_date());
                rwWithdraw.setTrans_time(transTransreq.getTrans_time());
                rwWithdraw.setPlat_no(baseRequest.getMer_no());
                rwWithdraw.setPlatcust(rwWithdrawEaccAccountAmtList.getAccountSubjectInfo().getPlatcust());
                rwWithdraw.setTrans_code(TransConsts.BATCH_WITHDRAW_CODE);
                rwWithdraw.setTrans_name(TransConsts.BATCH_WITHDRAW_NAME);
                rwWithdraw.setOut_amt(dateDetail.getAmt());
                rwWithdraw.setSubject(rwWithdrawEaccAccountAmtList.getAccountSubjectInfo().getSubject());
                rwWithdraw.setSub_subject(rwWithdrawEaccAccountAmtList.getAccountSubjectInfo().getSub_subject());
                rwWithdraw.setPay_code(dateDetail.getPay_code());
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    rwWithdraw.setFee_amt(dateDetail.getFee_amt());
                }else {
                    rwWithdraw.setFee_amt(BigDecimal.ZERO);
                }
                rwWithdraw.setOppo_account(rwWithdrawEaccAccountAmtList.getEaccCardinfo().getCard_no());

                rwWithdraw.setClient_property(dateDetail.getClient_property());
                rwWithdraw.setCity_code(dateDetail.getCity_code());

                if (StringUtils.isNotBlank(rwWithdrawEaccAccountAmtList.getEaccUserinfo().getName())) {
                    rwWithdraw.setPayee_name(rwWithdrawEaccAccountAmtList.getEaccUserinfo().getName());
                } else {
                    rwWithdraw.setPayee_name(rwWithdrawEaccAccountAmtList.getEaccUserinfo().getOrg_name());
                }

                //对个人
                if (rwWithdraw.getClient_property().equals(CusType.PERSONAL.getCode())) {
                    rwWithdraw.setOpen_branch(rwWithdrawEaccAccountAmtList.getEaccCardinfo().getOpen_branch());
                    rwWithdraw.setBank_id(rwWithdrawEaccAccountAmtList.getEaccCardinfo().getBank_no());

                }
                //对公司
                if (rwWithdraw.getClient_property().equals(CusType.COMPANY.getCode())) {
                    rwWithdraw.setOpen_branch(dateDetail.getOpen_branch());
                    rwWithdraw.setBank_id(dateDetail.getBank_id());
                    rwWithdraw.setBrabank_name(dateDetail.getBrabank_name());
                }
                //默认set为借记卡
                rwWithdraw.setCard_type(dateDetail.getCard_type());
                rwWithdraw.setBrabank_name(dateDetail.getBrabank_name());
                rwWithdraw.setIs_advance(rwWithdrawEaccAccountAmtList.getIs_advance());
                rwWithdraw.setAdvance_amt(rwWithdrawEaccAccountAmtList.getAdvance_amt());
                rwWithdraw.setPay_status(PayStatus.INIT_PAY.getCode());
                rwWithdraw.setOrder_no(dateDetail.getDetail_no());
                rwWithdraw.setQuery_no_num(0);
                rwWithdraw.setNotify_url(dateDetail.getNotify_url());
                rwWithdraw.setRemark(dateDetail.getRemark());
                rwWithdraw.setEnabled(Constants.ENABLED);
                rwWithdraw.setCreate_time(new Date());
                rwWithdraw.setUpdate_time(new Date());
                if(StringUtils.isNotBlank(dateDetail.getCity_code())){
                    rwWithdraw.setCity_code(dateDetail.getCity_code());
                }
                if(StringUtils.isNotBlank(dateDetail.getProvince_code())){
                    rwWithdraw.setProvince_code(dateDetail.getProvince_code());
                }
                rwWithdrawMapper.insert(rwWithdraw);
                logger.info("【批量提现】-->添加提现信息成功-->detail_no:" + dateDetail.getDetail_no());

            }catch (Exception e){

                logger.info("【批量提现】-->插入提现表异常，准备反向回滚转账-->detail_no:" + dateDetail.getDetail_no(),e);
                accountTransferService.queryEaccAccountamtlistByTransSerialAndPlatcust(transTransreq.getTrans_serial());
                throw new BusinessException(BusinessMsg.FAIL,"插入提现异常");

            }
        }catch (Exception e){
            if(e instanceof BusinessException){
                logger.info("【批量提现】-->BusinessException异常-->detail_no:" + dateDetail.getDetail_no(),e);
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();

                transTransreq.setStatus(OrderStatus.FAIL.getCode());
                transTransreq.setReturn_code(baseResponse.getRecode());
                transTransreq.setReturn_msg("交易失败，" + baseResponse.getRemsg());
                transReqService.insert(transTransreq);

                try {
                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                    notityPaymentReq.setMall_no(baseRequest.getMall_no());
                    notityPaymentReq.setPlat_no(baseRequest.getMer_no());
                    notityPaymentReq.setPlatcust(dateDetail.getPlatcust());
                    notityPaymentReq.setOrder_no(dateDetail.getDetail_no());
                    notityPaymentReq.setOrder_amt(dateDetail.getAmt());
                    notityPaymentReq.setTrans_date(baseRequest.getPartner_trans_date());
                    notityPaymentReq.setTrans_time(baseRequest.getPartner_trans_time());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));
                    notityPaymentReq.setPay_amt(dateDetail.getFee_amt().add(dateDetail.getAmt()));
                    notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                    notityPaymentReq.setError_no(OrderStatus.REFUNDPSUCCESS.getCode());
                    notityPaymentReq.setError_info("交易失败，" + baseResponse.getRemsg());
                    NotifyData notifyData = new NotifyData();
                    notifyData.setNotifyContent(JSONObject.toJSONString(notityPaymentReq));
                    notifyData.setMall_no(baseRequest.getMall_no());
                    notifyData.setNotifyUrl(dateDetail.getNotify_url());
                    notifyService.addNotify(notifyData);
                    logger.info("【批量提现】-->发送异步通知-->order_no:"+dateDetail.getDetail_no());
                }catch (Exception e1){
                    logger.info("【批量提现】发送异步通知-->order_no:"+dateDetail.getDetail_no(),e1);
                }

            }else {
                logger.info("【批量提现】-->未知异常，可能存在bug-->detail_no:" + dateDetail.getDetail_no(),e);
            }
        }
    }

    public RwWithdrawEaccAccountAmtList getEaccAccountAmtList(BaseRequest baseRequest, TransTransreq transTransReq, DateDetail dateDetail) throws BusinessException {

        RwWithdrawEaccAccountAmtList rwWithdrawEaccAccountAmtList = new RwWithdrawEaccAccountAmtList();

        List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();

        try {

            if(!dateDetail.getClient_property().equals(CusType.PERSONAL.getCode())
                    && !dateDetail.getClient_property().equals(CusType.COMPANY.getCode()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"公私标示有误");

            if(!CardType.DEBIT.getCode().equals(dateDetail.getCard_type())
                    && !CardType.CREDIT.getCode().equals(dateDetail.getCard_type()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",卡类型有误");
            logger.info("【批量提现】-->卡类型正常-->detail_no:" + dateDetail.getDetail_no());

            //判断对公  必填项
            if(dateDetail.getClient_property().equals(CusType.COMPANY.getCode())){
                if(StringUtils.isEmpty(dateDetail.getOpen_branch()) || StringUtils.isEmpty(dateDetail.getBank_id()) )
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,"对公缺少必要参数open_branch或bank_id");
            }

            //查询出平台的中间账户  用于普通转账
            AccountSubjectInfo account_middle = accountQueryService.queryAccount(baseRequest.getMer_no(),baseRequest.getMer_no(), Tsubject.CASH.getCode(), dateDetail.getPay_code());
            if (account_middle == null) {
                logger.info("【批量提现】-->平台中间账户不存在-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",缺少平台中间户账户");
            }
            logger.info("【批量提现】-->平台中间账户存在-->detail_no:" + dateDetail.getDetail_no());

            //检查用户绑卡是否存在
            List<EaccCardinfo> eaccCardInfos = accountSearchService.queryEaccCardInfo(baseRequest.getMall_no(), dateDetail.getPlatcust(),dateDetail.getCard_no(),null);
            if (null == eaccCardInfos || eaccCardInfos.size() == 0) {
                logger.info("【批量提现】-->绑卡账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "绑卡账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            EaccCardinfo eaccCardInfo = eaccCardInfos.get(0);
            logger.info("【批量提现】-->用户绑卡信息存在-->detail_no:" + dateDetail.getDetail_no());


            //检查用户信息是否存在
            EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(baseRequest.getMall_no(), baseRequest.getMer_no(), dateDetail.getPlatcust());
            if (eaccUserInfo == null) {
                logger.info("【批量提现】-->用户账号不存在" + "-->用户账户号：" + dateDetail.getPlatcust()+"-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "用户账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【批量提现】-->用户信息存在-->detail_no:" + dateDetail.getDetail_no());

            logger.info("【批量提现】-->查询虚拟账户-->detail_no:" + dateDetail.getDetail_no());

            //用户现金账户
            AccountSubjectInfo accountSubjectInfo = null;
            //用户在途账户
            AccountSubjectInfo accountSubjectInfo_float = null;
            if(!Ssubject.EACCOUNT.getCode().equals(dateDetail.getWithdraw_type())){
                accountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getPlatcust(), Tsubject.CASH.getCode(), dateDetail.getWithdraw_type());
                accountSubjectInfo_float = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getPlatcust(), Tsubject.FLOAT.getCode(), dateDetail.getWithdraw_type());
            }else {
                accountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMall_no(), dateDetail.getPlatcust(), Tsubject.CASH.getCode(), dateDetail.getWithdraw_type());
                accountSubjectInfo_float = accountQueryService.queryAccount(baseRequest.getMall_no(), dateDetail.getPlatcust(), Tsubject.FLOAT.getCode(), dateDetail.getWithdraw_type());
            }
            if (accountSubjectInfo == null || accountSubjectInfo_float == null) {
                logger.info("【批量提现】-->账户明细不存在-->用户账户号：" + dateDetail.getPlatcust()+"-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "账户明细不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【批量提现】-->账户明细账户信息存在-->detail_no:" + dateDetail.getDetail_no());

            //手续费现金账户
            AccountSubjectInfo plat_account = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
            //手续费在途账户
            AccountSubjectInfo plat_account_float = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode());

            //随机数获取行内垫资子科目
            String subSubject = Payment.getRandom();
            //行内垫资现金账户
            AccountSubjectInfo plat_account_inline = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.CASH.getCode(), subSubject);
            //行内垫资在途账户
            AccountSubjectInfo plat_account_inline_float = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.FLOAT.getCode(), subSubject);

            if (plat_account == null || plat_account_float == null || plat_account_inline == null || plat_account_inline_float == null) {

                logger.info("【批量提现】平台账户不存在-->order_no" + dateDetail.getDetail_no());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",平台账户不存在");
                throw new BusinessException(baseResponse);
            }
            logger.info("【批量提现】提现所用平台账户存在-->order_no:" + dateDetail.getDetail_no());

            //查询平台映射信息
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(baseRequest.getMer_no(), dateDetail.getPay_code());
            if (platPayCode == null) {
                logger.info("【批量提现】-->支付平台映射信息不存在" + "客户编号：" + baseRequest.getMer_no() + "支付通道：" + dateDetail.getPay_code());
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "支付平台映射信息不存在" + "客户编号："
                        + baseRequest.getMer_no() + "支付通道：" + dateDetail.getPay_code());
            }
            logger.info("【批量提现】-->平台映射信息存在-->detail_no:" + dateDetail.getDetail_no());

            BigDecimal bigDecimal_c =  accountSubjectInfo_float.getN_balance().add(accountSubjectInfo.getN_balance());
            BigDecimal bigDecimal_s = dateDetail.getAmt();
            if(dateDetail.getFee_amt() != null && dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                bigDecimal_s = bigDecimal_s.add(dateDetail.getFee_amt());
            }

            if(bigDecimal_c.compareTo(bigDecimal_s) == -1){
                logger.info("【批量提现】-->账户余额不足，在途和现金的总余额不够-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK));
            }
            logger.info("【批量提现】-->账户总余额足够-->detail_no:" + dateDetail.getDetail_no());



            //判断垫付状态     行内垫付
            logger.info("【批量提现】-->判断垫付类型-->detail_no:" + dateDetail.getDetail_no());
            if (dateDetail.getIs_advance().equals(AdvanceType.INLINEADVANCE.getCode())) {
                logger.info("【批量提现】-->行内垫付-->detail_no:" + dateDetail.getDetail_no());

                //如果有手续费
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    logger.info("【批量提现】-->行内垫付有手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【批量提现】-->行内垫付时现金资金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, dateDetail.getFee_amt(), dateDetail.getPay_code(), "批量提现：转账手续费金额到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                        //当账户资金大于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) > 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当 现金资金 > 提现金额 , 现金资金 < 提现金额 + 手续费出一半时-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  现金-->detail_no:" + dateDetail.getDetail_no());
                        //先转一半现金手续费手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, accountSubjectInfo.getN_balance().subtract(dateDetail.getAmt()), dateDetail.getPay_code(), "批量提现：转账手续费另一半到手续费账户 现金"));

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：转账手续费另一半到手续费账户 在途"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());


                        //当账户资金等于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) == 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当资金刚好够提现金额，手续费在途出的时候-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：转账手续费到手续费账户 在途"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户 现金-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户 现金"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【批量提现】-->行内垫付时现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费在途到手续费在途账户 在途"));

                        logger.info("【批量提现】-->用户在途金额转行内垫付在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转行内垫付在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_inline_float, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：用户在途金额转行内垫付在途 在途"));

                        logger.info("【批量提现】-->行内垫付现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //行内垫付现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_inline, accountSubjectInfo, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：行内垫付现金转用户现金"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()));

                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.INLINEADVANCE.getCode());

                    }


                } else {//如果没有手续费
                    logger.info("【批量提现】-->行内垫付无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【批量提现】-->行内垫付现金资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【批量提现】-->行内垫付现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->用户在途金额转行内垫付在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转平台在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_inline_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：用户在途金额转行内垫付 在途"));

                        logger.info("【批量提现】-->行内垫付现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //平台现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_inline, accountSubjectInfo, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：行内垫付现金转用户 现金"));

                        logger.info("【批量提现】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户"));


                        rwWithdrawEaccAccountAmtList.setAdvance_amt(bigDecimal_s.subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.INLINEADVANCE.getCode());
                    }
                }
            } else if (dateDetail.getIs_advance().equals(AdvanceType.TOADVANCE.getCode())) {//普通垫资

                logger.info("【批量提现】-->普通垫付-->detail_no:" + dateDetail.getDetail_no());

                /*if (StringUtils.isBlank(dateDetail.getAdvance_platcust())) {
                    logger.info("【批量提现】-->垫资账户为空-->detail_no:" + dateDetail.getDetail_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK, BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) + ",垫资账户不能为空");
                }*/
                AccountSubjectInfo plat_account_d = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.CASH.getCode(), Ssubject.PAYMENT.getCode());
                AccountSubjectInfo plat_account_d_f = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.FLOAT.getCode(), Ssubject.PAYMENT.getCode());
                if (plat_account_d == null || plat_account_d_f == null) {
                    logger.info("【批量提现】-->垫资账户不存在-->advance_platcust:" + baseRequest.getMer_no() + "-->detail_no:" + dateDetail.getDetail_no());
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",垫资账户不存在,advance_platcust:" + baseRequest.getMer_no());
                }

                //如果有手续费
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    logger.info("【批量提现】-->普通垫付有手续费-->detail_no:" + dateDetail.getDetail_no());
                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【批量提现】-->普通垫付时现金资金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费金额到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                        //当账户资金大于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) > 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当 现金资金 > 提现金额  现金 < 提现金额 + 手续费出一半时-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  现金-->detail_no:" + dateDetail.getDetail_no());
                        //先转一半现金手续费手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, accountSubjectInfo.getN_balance().subtract(dateDetail.getAmt()), null, "批量提现：转账手续费另一半到手续费账户 现金"));

                        logger.info("【批量提现】-->转账一半手续费金额到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：转账手续费另一半到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());


                        //当账户资金等于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) == 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【批量提现】-->当资金刚好够提现金额，手续费在途出的时候-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：转账手续费到手续费账户 在途"));

                        logger.info("【批量提现】-->转账提现金额到中间账户 现金-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户 现金"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【批量提现】-->普通垫付时现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费在途到手续费在途账户"));

                        logger.info("【批量提现】-->用户在途金额转垫付账户在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转行内垫付在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_d_f, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：用户在途金额转平台在途"));

                        logger.info("【批量提现】-->垫资账户现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //行内垫付现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_d, accountSubjectInfo, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：垫资用户现金转用户现金"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.TOADVANCE.getCode());

                    }
                } else {//如果没有手续费
                    logger.info("【批量提现】-->普通垫付无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【批量提现】-->普通垫付现金资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【批量提现】-->普通垫付现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->用户在途金额转垫资账户在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转平台在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_d_f, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：用户在途金额转平台在途"));

                        logger.info("【批量提现】-->垫资账户现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //平台现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_d, accountSubjectInfo, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：平台现金转用户现金"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(bigDecimal_s.subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.TOADVANCE.getCode());

                    }

                }
            } else if (dateDetail.getIs_advance().equals(AdvanceType.NOADVANCE.getCode())) { //如果不需要垫付
                logger.info("【批量提现】-->不需要垫付-->detail_no:" + dateDetail.getDetail_no());

                //如果有手续费
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    logger.info("【批量提现】-->不需要垫付有手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果用户账户现金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {
                        logger.info("【批量提现】-->不需要垫付现金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【批量提现】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费金额到手续费账户"));

                        logger.info("【批量提现】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {

                        logger.info("【批量提现】-->不需要垫付，现金资金不足，直接抛异常-->detail_no:" + dateDetail.getDetail_no());

                        throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK) + "，不需要垫付时， 账户现金不足");

                    }

                } else {

                    logger.info("【批量提现】-->不需要垫付，无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果用户账户现金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【批量提现】-->不需要垫付，资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {//用户现金不足

                        logger.info("【批量提现】-->不需要垫付，资金不足，直接抛异常-->detail_no:" + dateDetail.getDetail_no());

                        throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK) + "，不需要垫付时，账户现金不足");
                    }

                }

            } else {

                logger.info("【批量提现】-->是否垫资参数有误-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK) + ",请检查【是否垫资】");

            }

            rwWithdrawEaccAccountAmtList.setEaccCardinfo(eaccCardInfo);
            rwWithdrawEaccAccountAmtList.setEaccUserinfo(eaccUserInfo);
            rwWithdrawEaccAccountAmtList.setAccountSubjectInfo(accountSubjectInfo);
            rwWithdrawEaccAccountAmtList.setEaccAccountamtlists(eaccAccountamtlists);

        }catch (Exception e){
            logger.info("【批量提现】-->异常-->detail_no:" + dateDetail.getDetail_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL)+",验证参数时未知异常,请发起重试");
            }
            throw new BusinessException(baseResponse);
        }

        return rwWithdrawEaccAccountAmtList;
    }

    public RwWithdrawEaccAccountAmtList getAffirmEaccAccountAmtList(BaseRequest baseRequest, TransTransreq transTransReq, DateDetailAffirm dateDetail) throws BusinessException {

        RwWithdrawEaccAccountAmtList rwWithdrawEaccAccountAmtList = new RwWithdrawEaccAccountAmtList();

        List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<EaccAccountamtlist>();

        try {

            if(!dateDetail.getClient_property().equals(CusType.PERSONAL.getCode())
                    && !dateDetail.getClient_property().equals(CusType.COMPANY.getCode()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,"公私标示有误");

            if(!CardType.DEBIT.getCode().equals(dateDetail.getCard_type())
                    && !CardType.CREDIT.getCode().equals(dateDetail.getCard_type()))
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR) + ",卡类型有误");
            logger.info("【提现确认】-->卡类型正常-->detail_no:" + dateDetail.getDetail_no());

            //判断对公  必填项
            if(dateDetail.getClient_property().equals(CusType.COMPANY.getCode())){
                if(StringUtils.isEmpty(dateDetail.getOpen_branch()) || StringUtils.isEmpty(dateDetail.getBank_id()) )
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK,"对公缺少必要参数open_branch或bank_id");
            }

            //查询出平台的中间账户  用于普通转账
            AccountSubjectInfo account_middle = accountQueryService.queryAccount(baseRequest.getMer_no(),baseRequest.getMer_no(), Tsubject.CASH.getCode(), dateDetail.getPay_code());
            if (account_middle == null) {
                logger.info("【提现确认】-->平台中间账户不存在-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",缺少平台中间户账户");
            }
            logger.info("【提现确认】-->平台中间账户存在-->detail_no:" + dateDetail.getDetail_no());

            //检查用户绑卡是否存在
            List<EaccCardinfo> eaccCardInfos = accountSearchService.queryEaccCardInfo(baseRequest.getMall_no(), dateDetail.getPlatcust(),dateDetail.getCard_no(),null);
            if (null == eaccCardInfos || eaccCardInfos.size() == 0) {
                logger.info("【提现确认】-->绑卡账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "绑卡账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            EaccCardinfo eaccCardInfo = eaccCardInfos.get(0);
            logger.info("【提现确认】-->用户绑卡信息存在-->detail_no:" + dateDetail.getDetail_no());


            //检查用户信息是否存在
            EaccUserinfo eaccUserInfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(baseRequest.getMall_no(), baseRequest.getMer_no(), dateDetail.getPlatcust());
            if (eaccUserInfo == null) {
                logger.info("【提现确认】-->用户账号不存在" + "-->用户账户号：" + dateDetail.getPlatcust()+"-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "用户账号不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【提现确认】-->用户信息存在-->detail_no:" + dateDetail.getDetail_no());

            logger.info("【提现确认】-->查询虚拟账户-->detail_no:" + dateDetail.getDetail_no());

            //用户现金账户
            AccountSubjectInfo accountSubjectInfo = null;
            //用户在途账户
            AccountSubjectInfo accountSubjectInfo_float = null;
            if(!Ssubject.EACCOUNT.getCode().equals(dateDetail.getWithdraw_type())){
                accountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getPlatcust(), Tsubject.CASH.getCode(), dateDetail.getWithdraw_type());
                accountSubjectInfo_float = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getPlatcust(), Tsubject.FLOAT.getCode(), dateDetail.getWithdraw_type());
            }else {
                accountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMall_no(), dateDetail.getPlatcust(), Tsubject.CASH.getCode(), dateDetail.getWithdraw_type());
                accountSubjectInfo_float = accountQueryService.queryAccount(baseRequest.getMall_no(), dateDetail.getPlatcust(), Tsubject.FLOAT.getCode(), dateDetail.getWithdraw_type());
            }
            if (accountSubjectInfo == null || accountSubjectInfo_float == null) {
                logger.info("【提现确认】-->账户明细不存在-->用户账户号：" + dateDetail.getPlatcust()+"-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + "账户明细不存在" + "用户账户号：" + dateDetail.getPlatcust());
            }
            logger.info("【提现确认】-->账户明细账户信息存在-->detail_no:" + dateDetail.getDetail_no());

            //手续费现金账户
            AccountSubjectInfo plat_account = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.CASH.getCode(), Ssubject.FEE.getCode());
            //手续费在途账户
            AccountSubjectInfo plat_account_float = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.FLOAT.getCode(), Ssubject.FEE.getCode());

            //随机数获取行内垫资子科目
            String subSubject = Payment.getRandom();
            //行内垫资现金账户
            AccountSubjectInfo plat_account_inline = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.CASH.getCode(), subSubject);
            //行内垫资在途账户
            AccountSubjectInfo plat_account_inline_float = accountQueryService.queryAccount(baseRequest.getMer_no(), baseRequest.getMer_no(), Tsubject.FLOAT.getCode(), subSubject);

            if (plat_account == null || plat_account_float == null || plat_account_inline == null || plat_account_inline_float == null) {

                logger.info("【提现确认】平台账户不存在-->order_no" + dateDetail.getDetail_no());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",平台账户不存在");
                throw new BusinessException(baseResponse);
            }
            logger.info("【提现确认】提现所用平台账户存在-->order_no:" + dateDetail.getDetail_no());

            //查询平台映射信息
            PlatPaycode platPayCode = accountSearchService.queryPlatPayCode(baseRequest.getMer_no(), dateDetail.getPay_code());
            if (platPayCode == null) {
                logger.info("【提现确认】-->支付平台映射信息不存在" + "客户编号：" + baseRequest.getMer_no() + "支付通道：" + dateDetail.getPay_code());
                throw new BusinessException(BusinessMsg.DATEBASE_EXCEPTION, BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + "支付平台映射信息不存在" + "客户编号："
                        + baseRequest.getMer_no() + "支付通道：" + dateDetail.getPay_code());
            }
            logger.info("【提现确认】-->平台映射信息存在-->detail_no:" + dateDetail.getDetail_no());

            BigDecimal bigDecimal_c =  accountSubjectInfo_float.getN_balance().add(accountSubjectInfo.getN_balance());
            BigDecimal bigDecimal_s = dateDetail.getAmt();
            if(dateDetail.getFee_amt() != null && dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
                bigDecimal_s = bigDecimal_s.add(dateDetail.getFee_amt());
            }

            if(bigDecimal_c.compareTo(bigDecimal_s) == -1){
                logger.info("【提现确认】-->账户余额不足，在途和现金的总余额不够-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK));
            }
            logger.info("【提现确认】-->账户总余额足够-->detail_no:" + dateDetail.getDetail_no());



            //判断垫付状态     行内垫付
            logger.info("【提现确认】-->判断垫付类型-->detail_no:" + dateDetail.getDetail_no());
            if (dateDetail.getIs_advance().equals(AdvanceType.INLINEADVANCE.getCode())) {
                logger.info("【提现确认】-->行内垫付-->detail_no:" + dateDetail.getDetail_no());

                //如果有手续费
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    logger.info("【提现确认】-->行内垫付有手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【提现确认】-->行内垫付时现金资金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, dateDetail.getFee_amt(), dateDetail.getPay_code(), "批量提现：转账手续费金额到手续费账户"));

                        logger.info("【提现确认】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                        //当账户资金大于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) > 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【提现确认】-->当 现金资金 > 提现金额 , 现金资金 < 提现金额 + 手续费出一半时-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账一半手续费金额到手续费账户  现金-->detail_no:" + dateDetail.getDetail_no());
                        //先转一半现金手续费手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, accountSubjectInfo.getN_balance().subtract(dateDetail.getAmt()), dateDetail.getPay_code(), "批量提现：转账手续费另一半到手续费账户 现金"));

                        logger.info("【提现确认】-->转账一半手续费金额到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：转账手续费另一半到手续费账户 在途"));

                        logger.info("【提现确认】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());


                        //当账户资金等于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) == 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【提现确认】-->当资金刚好够提现金额，手续费在途出的时候-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：转账手续费到手续费账户 在途"));

                        logger.info("【提现确认】-->转账提现金额到集团行内账户 现金-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户 现金"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【提现确认】-->行内垫付时现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费在途到手续费在途账户 在途"));

                        logger.info("【提现确认】-->用户在途金额转行内垫付在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转行内垫付在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_inline_float, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：用户在途金额转行内垫付在途 在途"));

                        logger.info("【提现确认】-->行内垫付现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //行内垫付现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_inline, accountSubjectInfo, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：行内垫付现金转用户现金"));

                        logger.info("【提现确认】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.INLINEADVANCE.getCode());

                    }


                } else {//如果没有手续费
                    logger.info("【提现确认】-->行内垫付无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【提现确认】-->行内垫付现金资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【提现确认】-->行内垫付现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->用户在途金额转行内垫付在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转平台在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_inline_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：用户在途金额转行内垫付 在途"));

                        logger.info("【提现确认】-->行内垫付现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //平台现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_inline, accountSubjectInfo, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), dateDetail.getPay_code(), "批量提现：行内垫付现金转用户 现金"));

                        logger.info("【提现确认】-->转账提现金额到集团行内账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户"));


                        rwWithdrawEaccAccountAmtList.setAdvance_amt(bigDecimal_s.subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.INLINEADVANCE.getCode());
                    }
                }
            } else if (dateDetail.getIs_advance().equals(AdvanceType.TOADVANCE.getCode())) {//普通垫资

                logger.info("【提现确认】-->普通垫付-->detail_no:" + dateDetail.getDetail_no());

                if (StringUtils.isBlank(dateDetail.getAdvance_platcust())) {
                    logger.info("【提现确认】-->垫资账户为空-->detail_no:" + dateDetail.getDetail_no());
                    throw new BusinessException(BusinessMsg.PARAMETER_LACK, BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) + ",垫资账户不能为空");
                }
                AccountSubjectInfo plat_account_d = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getAdvance_platcust(), Tsubject.CASH.getCode(), Ssubject.INVEST.getCode());
                AccountSubjectInfo plat_account_d_f = accountQueryService.queryAccount(baseRequest.getMer_no(), dateDetail.getAdvance_platcust(), Tsubject.FLOAT.getCode(), Ssubject.INVEST.getCode());
                if (plat_account_d == null || plat_account_d_f == null) {
                    logger.info("【提现确认】-->垫资账户不存在-->advance_platcust:" + dateDetail.getAdvance_platcust() + "-->detail_no:" + dateDetail.getDetail_no());
                    throw new BusinessException(BusinessMsg.INVALID_ACCOUNT, BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",垫资账户不存在,advance_platcust:" + dateDetail.getAdvance_platcust());
                }

                //如果有手续费
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    logger.info("【提现确认】-->普通垫付有手续费-->detail_no:" + dateDetail.getDetail_no());
                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【提现确认】-->普通垫付时现金资金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费金额到手续费账户"));

                        logger.info("【提现确认】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                        //当账户资金大于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) > 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【提现确认】-->当 现金资金 > 提现金额  现金 < 提现金额 + 手续费出一半时-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账一半手续费金额到手续费账户  现金-->detail_no:" + dateDetail.getDetail_no());
                        //先转一半现金手续费手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, accountSubjectInfo.getN_balance().subtract(dateDetail.getAmt()), null, "批量提现：转账手续费另一半到手续费账户 现金"));

                        logger.info("【提现确认】-->转账一半手续费金额到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：转账手续费另一半到手续费账户"));

                        logger.info("【提现确认】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());


                        //当账户资金等于提现金额  小于提现金加手续费时
                    } else if (accountSubjectInfo.getN_balance().compareTo(dateDetail.getAmt()) == 0 && accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) < 0) {

                        logger.info("【提现确认】-->当资金刚好够提现金额，手续费在途出的时候-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费到手续费账户  在途-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：转账手续费到手续费账户 在途"));

                        logger.info("【提现确认】-->转账提现金额到中间账户 现金-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到集团行内账户 现金"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【提现确认】-->普通垫付时现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_float, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费在途到手续费在途账户"));

                        logger.info("【提现确认】-->用户在途金额转垫付账户在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转行内垫付在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_d_f, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：用户在途金额转平台在途"));

                        logger.info("【提现确认】-->垫资账户现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //行内垫付现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_d, accountSubjectInfo, transTransReq, dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()), null, "批量提现：垫资用户现金转用户现金"));

                        logger.info("【提现确认】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到集团行内账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(dateDetail.getAmt().subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.TOADVANCE.getCode());

                    }
                } else {//如果没有手续费
                    logger.info("【提现确认】-->普通垫付无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果账户现金资金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【提现确认】-->普通垫付现金资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {
                        logger.info("【提现确认】-->普通垫付现金资金不充足，垫付出金-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->用户在途金额转垫资账户在途-->detail_no:" + dateDetail.getDetail_no());
                        //用户在途金额转平台在途
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo_float, plat_account_d_f, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：用户在途金额转平台在途"));

                        logger.info("【提现确认】-->垫资账户现金转用户现金-->detail_no:" + dateDetail.getDetail_no());
                        //平台现金转用户现金
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(plat_account_d, accountSubjectInfo, transTransReq, bigDecimal_s.subtract(accountSubjectInfo.getN_balance()), null, "批量提现：平台现金转用户现金"));

                        logger.info("【提现确认】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(bigDecimal_s.subtract(accountSubjectInfo.getN_balance()));
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.TOADVANCE.getCode());

                    }

                }
            } else if (dateDetail.getIs_advance().equals(AdvanceType.NOADVANCE.getCode())) { //如果不需要垫付
                logger.info("【提现确认】-->不需要垫付-->detail_no:" + dateDetail.getDetail_no());

                //如果有手续费
                if (dateDetail.getFee_amt().compareTo(BigDecimal.ZERO) > 0) {
                    logger.info("【提现确认】-->不需要垫付有手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果用户账户现金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {
                        logger.info("【提现确认】-->不需要垫付现金充足-->detail_no:" + dateDetail.getDetail_no());

                        logger.info("【提现确认】-->转账手续费金额到手续费账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账手续费金额到手续费账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, plat_account, transTransReq, dateDetail.getFee_amt(), null, "批量提现：转账手续费金额到手续费账户"));

                        logger.info("【提现确认】-->转账提现金额到中间账户-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {

                        logger.info("【提现确认】-->不需要垫付，现金资金不足，直接抛异常-->detail_no:" + dateDetail.getDetail_no());

                        throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK) + "，不需要垫付时， 账户现金不足");

                    }

                } else {

                    logger.info("【提现确认】-->不需要垫付，无手续费-->detail_no:" + dateDetail.getDetail_no());

                    //如果用户账户现金充足
                    if (accountSubjectInfo.getN_balance().compareTo(bigDecimal_s) >= 0) {

                        logger.info("【提现确认】-->不需要垫付，资金充足-->detail_no:" + dateDetail.getDetail_no());
                        //转账提现金额到中间账户
                        eaccAccountamtlists.add(eaccAccountamtlistTransfer(accountSubjectInfo, account_middle, transTransReq, dateDetail.getAmt(), dateDetail.getPay_code(), "批量提现：转账提现金额到中间账户"));

                        rwWithdrawEaccAccountAmtList.setAdvance_amt(BigDecimal.ZERO);
                        rwWithdrawEaccAccountAmtList.setIs_advance(AdvanceType.NOADVANCE.getCode());

                    } else {//用户现金不足

                        logger.info("【提现确认】-->不需要垫付，资金不足，直接抛异常-->detail_no:" + dateDetail.getDetail_no());

                        throw new BusinessException(BusinessMsg.BALANCE_LACK, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK) + "，不需要垫付时，账户现金不足");
                    }

                }

            } else {

                logger.info("【提现确认】-->是否垫资参数有误-->detail_no:" + dateDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.BALANCE_LACK) + ",请检查【是否垫资】");

            }

            rwWithdrawEaccAccountAmtList.setEaccCardinfo(eaccCardInfo);
            rwWithdrawEaccAccountAmtList.setEaccUserinfo(eaccUserInfo);
            rwWithdrawEaccAccountAmtList.setAccountSubjectInfo(accountSubjectInfo);
            rwWithdrawEaccAccountAmtList.setEaccAccountamtlists(eaccAccountamtlists);

        }catch (Exception e){
            logger.info("【提现确认】-->异常-->detail_no:" + dateDetail.getDetail_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL)+",验证参数时未知异常,请发起重试");
            }
            throw new BusinessException(baseResponse);
        }

        return rwWithdrawEaccAccountAmtList;
    }

    //提现转账
    public EaccAccountamtlist eaccAccountamtlistTransfer(AccountSubjectInfo accountSubjectInfo_oneself, AccountSubjectInfo accountSubjectInfo_rival, TransTransreq transTransReq, BigDecimal b, String pay_code, String rem) throws BusinessException {

        EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();

        //账户，科目，子科目
        eaccAccountamtlist.setPlat_no(accountSubjectInfo_oneself.getPlat_no());
        eaccAccountamtlist.setPlatcust(accountSubjectInfo_oneself.getPlatcust());
        eaccAccountamtlist.setSubject(accountSubjectInfo_oneself.getSubject());
        eaccAccountamtlist.setSub_subject(accountSubjectInfo_oneself.getSub_subject());

        //对手的账户，科目，子科目
        eaccAccountamtlist.setOppo_plat_no(accountSubjectInfo_rival.getPlat_no());
        eaccAccountamtlist.setOppo_platcust(accountSubjectInfo_rival.getPlatcust());
        eaccAccountamtlist.setOppo_subject(accountSubjectInfo_rival.getSubject());
        eaccAccountamtlist.setOppo_sub_subject(accountSubjectInfo_rival.getSub_subject());

        //交易流水号
        eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
        //订单号
        eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());

        //出账的交易代码，交易名称
        eaccAccountamtlist.setTrans_code(TransConsts.BATCH_WITHDRAW_CODE);
        eaccAccountamtlist.setTrans_name(TransConsts.BATCH_WITHDRAW_NAME);
        eaccAccountamtlist.setTrans_date(transTransReq.getTrans_date());
        eaccAccountamtlist.setTrans_time(transTransReq.getTrans_time());


        eaccAccountamtlist.setPay_code(pay_code);
        eaccAccountamtlist.setRemark(rem);

        //转账金额
        eaccAccountamtlist.setAmt(b);

        return eaccAccountamtlist;
    }

}
