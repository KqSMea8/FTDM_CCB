package com.sunyard.sunfintech.prod.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.provider.IAccountQueryService;
import com.sunyard.sunfintech.account.provider.IAccountTransferService;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.TransConsts;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.custdao.mapper.CustProdProdInfoMapper;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.EaccUserinfoMapper;
import com.sunyard.sunfintech.dao.mapper.ProdBorrowerRealrepayMapper;
import com.sunyard.sunfintech.dao.mapper.ProdShareListMapper;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.model.mq.*;
import com.sunyard.sunfintech.pub.model.MsgModel;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.prod.provider.*;
import com.sunyard.sunfintech.pub.provider.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by terry on 2017/7/28.
 */
@Service(interfaceClass=IProdMQOptionService.class)
@CacheConfig(cacheNames="prodMQOptionService")
@org.springframework.stereotype.Service
public class ProdMQOptionService extends BaseServiceSimple implements IProdMQOptionService {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    private IProdInvestmentExtService prodInvestmentExtService;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private EaccUserinfoMapper eaccUserinfoMapper;//todo

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IProductRefundExtService productRefundExtService;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private ProdBorrowerRealrepayMapper prodBorrowerRealrepayMapper;

    @Autowired
    private CustProdProdInfoMapper custProdProdInfoMapper;

    @Autowired
    private ProdShareListMapper prodShareListMapper;

    @Autowired
    private IAccountTransferService accountTransferService;
    @Autowired
    private IAuthCheckService authCheckService;

    @Autowired
    private IProductRefundDBOptionService productRefundDBOptionService;

    @Override
    public void addProdRepay(BaseRequest baseRequest, BatchCustRepay batchCustRepay, String notifyURL) throws BusinessException {
        if(baseRequest!=null && batchCustRepay!=null){
            ProdRfundData prodRfundData=new ProdRfundData();
            prodRfundData.setProdProductinfo(new ProdProductinfo());
            prodRfundData.setBaseRequest(baseRequest);
            prodRfundData.setBatchCustRepay(batchCustRepay);
            prodRfundData.setNotifyURL(notifyURL);
            prodRfundData.setTime(DateUtils.todayStr());

            MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "ProductRefundQueue", prodRfundData);
            /*String destination="BatchRefundQueue"+baseRequest.getMall_no();
            amqpTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    logger.info("标的批量还款发送消息："+batchCustRepay.getDetail_no());
                    Message message=amqpTemplate.getMessageConverter().toMessage(prodRfundData, session);
                    logger.info("标的批量还款消息发送成功："+batchCustRepay.getDetail_no());
                    return message;
                }
            });*/
        }
    }

    @Override
    public void addBatchRepayAsyn(BaseRequest baseRequest, BatchRepayRequestDetail batchRepayRequestDetail, String notifyURL) throws BusinessException {

        if(baseRequest!=null && batchRepayRequestDetail!=null){
            BorrowData borrowData=new BorrowData();
            borrowData.setBaseRequest(baseRequest);
            borrowData.setBatchRepayRequestDetail(batchRepayRequestDetail);
            borrowData.setNotifyURL(notifyURL);
            MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "BatchBorrowQueue", borrowData);
            /*String destination="BatchBorrowQueue";
            amqpTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message message=amqpTemplate.getMessageConverter().toMessage(borrowData, session);
                    logger.info("【授权借款人还款申请】-->发送成功："+batchRepayRequestDetail.getDetail_no());
                    return message;
                }
            });*/
        }
    }

    @Override
    public void addInvest(BaseRequest baseRequest,ProdInvestData prodInvestData,String notifyURL) throws BusinessException {

        if(baseRequest!=null && prodInvestData!=null){
            InvestData investData=new InvestData();
            investData.setProdProductinfo(new ProdProductinfo());
            investData.setBaseRequest(baseRequest);
            investData.setProdInvestData(prodInvestData.copy());
            investData.setNotifyURL(notifyURL);
            investData.setTime(DateUtils.todayStr());

            MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "BatchInvestQueue", investData);
            /*String destination="BatchInvestQueue";
            amqpTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    logger.info("批量投标发送消息："+investData.getProdInvestData().getDetail_no());
                    Message message=amqpTemplate.getMessageConverter().toMessage(investData, session);
                    logger.info("批量投标消息发送成功："+investData.getProdInvestData().getDetail_no());
                    return message;
                }
            });*/
        }
    }
    @Override
    public void addInvests(BaseRequest baseRequest,List<ProdInvestData > prodInvestDatas,String notifyURL,boolean authValid) throws BusinessException {
        if(baseRequest!=null && prodInvestDatas!=null){
            List<InvestData> investDatas=new ArrayList<>();
            for (ProdInvestData prodInvestData : prodInvestDatas) {
                InvestData investData=new InvestData();
                investData.setProdProductinfo(new ProdProductinfo());
                investData.setBaseRequest(baseRequest);
                investData.setProdInvestData(prodInvestData.copy());
                investData.setNotifyURL(notifyURL);
                investData.setTime(DateUtils.todayStr());
                investData.setAuthValid(authValid);
                investDatas.add(investData);
            }

        boolean isSucc=    MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "BatchInvestQueue", investDatas);
            logger.info("addInvests批量投标发送消息："+baseRequest.getOrder_no()+",isSucc="+isSucc);
            if (!isSucc){
                throw  new BusinessException(BusinessMsg.NETWORK_ERROR,"进入队列失败"+baseRequest.getOrder_no());
            }


        }
    }

    @Override
    public void addTransProd(List<ProdTransferDebtRequestBo> prodTransferDebtRequestBoList,String notifyURL) throws BusinessException {
        if(prodTransferDebtRequestBoList!=null && prodTransferDebtRequestBoList.size()>0){
            TransProdData transProdData=new TransProdData();
            transProdData.setProdTransferDebtRequestBoList(prodTransferDebtRequestBoList);
            transProdData.setNotifyURL(notifyURL);
            transProdData.setTime(DateUtils.todayStr());
            MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "BatchProdTransQueue", transProdData);
            /*String destination="BatchProdTransQueue";
            amqpTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    logger.info("批量债转发送消息："+prodTransferDebtRequestBo.getDetail_no());
                    Message message=amqpTemplate.getMessageConverter().toMessage(transProdData, session);
                    logger.info("批量债转消息发送成功："+prodTransferDebtRequestBo.getDetail_no());
                    return message;
                }
            });*/
        }
    }

    @Override
    public void addRefund(List<BatchCustRepay> batchCustRepayList, ProdProductinfo prodProductinfo) throws BusinessException {
        if(batchCustRepayList!=null && batchCustRepayList.size()>0){
            ProdRefundData prodRefundData=new ProdRefundData();
            prodRefundData.setCustRepayList(batchCustRepayList);
            prodRefundData.setProductinfo(prodProductinfo);
            prodRefundData.setTime(DateUtils.todayStr());
            MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "BatchRefundTransQueueForFengJR", prodRefundData);
            /*String destination="BatchRefundQueue"+batchCustRepayList.get(0).getMall_no();
            amqpTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    logger.info("发送消息，队列："+destination);
                    Message message=amqpTemplate.getMessageConverter().toMessage(prodRefundData, session);
                    logger.info("消息发送成功！");
                    return message;
                }
            });*/
        }
    }

    @Override
    public void addSingleRefund(BatchCustRepay batchCustRepay, ProdProductinfo prodProductinfo, EaccUserinfo eaccUserinfo) throws BusinessException {
        ProdSingleRefundData prodSingleRefundData=new ProdSingleRefundData();
        prodSingleRefundData.setCustRepay(batchCustRepay);
        prodSingleRefundData.setProductinfo(prodProductinfo);
        prodSingleRefundData.setEaccUserinfo(eaccUserinfo);
        prodSingleRefundData.setTime(DateUtils.todayStr());
        MQUtils.send(amqpTemplate, "ftdm.prod.direct.exchange", "ProductRefundQueue", prodSingleRefundData);
    }

    @Override
    public void doInvest(BaseRequest baseRequest, ProdInvestData prodInvestData, String notifyURL,boolean authValid) throws BusinessException {
        //发送异步通知
        TransTransreq transTransReq = transReqService.queryTransTransReqByOrderno(prodInvestData.getDetail_no());
        if(transTransReq==null || FlowStatus.FAIL.getCode().equals(transTransReq.getStatus()) || FlowStatus.SUCCESS.getCode().equals(transTransReq.getStatus())){
            return;
        }
        ProdBaseVestResponse prodBaseResponse=new ProdBaseVestResponse();
        NotifyData notifyData=new NotifyData();
        TransTransreq originTransreq =null;
        notifyData.setNotifyUrl(notifyURL);
        notifyData.setMall_no(baseRequest.getMall_no());
        prodBaseResponse.setOrder_no(prodInvestData.getDetail_no());
        prodBaseResponse.setPlatcust(prodInvestData.getPlatcust());
        prodBaseResponse.setProd_id(prodInvestData.getProd_id());
        BigDecimal amt = BigDecimal.ZERO;
        if(prodInvestData.getExperience_amt()!=null){
            amt = amt.add(prodInvestData.getExperience_amt());
        }
        if(prodInvestData.getCoupon_amt()!=null){
            amt = amt.add(prodInvestData.getCoupon_amt());
        }
        if(prodInvestData.getSelf_amt()!=null){
            amt = amt.add(prodInvestData.getSelf_amt());
        }
        if(prodInvestData.getCommissionObj()!=null && prodInvestData.getCommissionObj().getPayout_amt()!=null){
            amt = amt.add(prodInvestData.getCommissionObj().getPayout_amt());
        }
        prodBaseResponse.setTrans_amt(amt);
        try{

                //检查是否授权
            if (authValid) {
                boolean isSucc = authCheckService.checkAuth(baseRequest.getMer_no(), baseRequest.getMall_no(), prodInvestData.getPlatcust(), AuthType.AUTH_INVEST.getCode(), prodInvestData.getTrans_amt());
                logger.info("投标检查是否授权：" + new Date() + "，订单号：" + prodInvestData.getDetail_no() + ",结果：" + isSucc);
                if (!isSucc) {
                    throw new BusinessException(BusinessMsg.AUTH_ERROR,
                            String.format(BusinessMsg.AUTH_ERROR + "|授权数据失败|platcust:%s|auth_type:%s", prodInvestData.getPlatcust(), AuthType.AUTH_RECHARGE.getCode()));
                }
                //给北京银行特地写的逻辑 test bob
                ProdProductinfo productInfo = prodSearchService.queryProdInfo(baseRequest.getMer_no(), prodInvestData.getProd_id());
                if (authValid &&productInfo!=null  ) {
                    if (productInfo.getRemain_limit()!=null&&productInfo.getRemain_limit().compareTo(prodInvestData.getTrans_amt()) < 0) {
                        BaseResponse baseResponse = new BaseResponse();
                        baseResponse.setRecode(BusinessMsg.PROD_LIMIT_NOTENOUGH);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PROD_LIMIT_NOTENOUGH));
                        throw new BusinessException(baseResponse);
                    }
                    Date expireDate=DateUtils.getDateWithoutTime(productInfo.getExpire_date());

                    if (expireDate!=null&&DateUtils.today(DateUtils.DEF_FORMAT_NOTIME).compareTo(  expireDate ) > 0) {
                        BaseResponse baseResponse = new BaseResponse();
                        baseResponse.setRecode(BusinessMsg.OVER_DATE);
                        baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.OVER_DATE));
                        throw new BusinessException(baseResponse);
                    }
                }
            }

            if(StringUtils.isNotEmpty(prodInvestData.getOrigin_order_no())){
                // 确认投资订单，必须确认原申请请订单
                originTransreq = transReqService.queryTransTransReqByOrderno(prodInvestData.getOrigin_order_no());
                if(originTransreq == null){
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
                    throw new BusinessException(baseResponse);
                }
            }

            prodInvestmentExtService.investForNoSyn(baseRequest,prodInvestData);
            prodBaseResponse.setRecode(BusinessMsg.SUCCESS);
            prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error("【批量投标】==========投标异常："+e.getMessage());
            BaseResponse baseResponse = null;
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
            }else{
                baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                transTransReq.setReturn_code(BusinessMsg.RUNTIME_EXCEPTION);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }

            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);

            if(originTransreq!=null){
                originTransreq.setReturn_code(transTransReq.getReturn_code());
                originTransreq.setReturn_msg(transTransReq.getReturn_msg());
                originTransreq.setStatus(transTransReq.getStatus());
                transReqService.insert(originTransreq);
            }

            prodBaseResponse.setRecode(baseResponse.getRecode());
            prodBaseResponse.setRemsg(baseResponse.getRemsg());
            prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
            notifyService.addNotify(notifyData);
        }

    }

    @Override
    public void doBatchRepayAsyn(BaseRequest baseRequest, BatchRepayRequestDetail batchRepayRequestDetail, String notifyURL) throws BusinessException {

        logger.info("【借款人还款申请】-->消费成功-->order_no:"+batchRepayRequestDetail.getDetail_no());

        TransTransreq transTransReq = null;

        ProdBaseVestResponse prodBaseResponse=new ProdBaseVestResponse();
        NotifyData notifyData=new NotifyData();
        notifyData.setNotifyUrl(notifyURL);
        notifyData.setMall_no(baseRequest.getMall_no());
        prodBaseResponse.setOrder_no(batchRepayRequestDetail.getDetail_no());
        prodBaseResponse.setPlatcust(batchRepayRequestDetail.getPlatcust());
        prodBaseResponse.setProd_id(batchRepayRequestDetail.getProd_id());
        prodBaseResponse.setRecode(BusinessMsg.SUCCESS);
        prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        prodBaseResponse.setTrans_amt(batchRepayRequestDetail.getTrans_amt());
        prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
        prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());

        try{

            transTransReq = transReqService.queryTransTransReqByOrderno(batchRepayRequestDetail.getDetail_no());
//            transTransReq=transReqService.getTransTransReq(baseRequest,TransConsts.BORROWREPAY_CODE, TransConsts.BORROWREPAY_NAME,true);
            transTransReq.setOrder_no(batchRepayRequestDetail.getDetail_no());
            transTransReq.setTrans_serial(batchRepayRequestDetail.getTrans_serial());
            transReqService.insert(transTransReq);
            logger.info("【借款人还款申请】-->查询明细订单成功-->order_no:"+batchRepayRequestDetail.getDetail_no());

            ProdProductinfo productInfo = prodSearchService.queryProdInfo(baseRequest.getMer_no(),batchRepayRequestDetail.getProd_id());
            if(productInfo == null){
                logger.info("【借款人还款申请】-->检查标的是否存在-->order_no:"+batchRepayRequestDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + ",标不存在  标的id：" + batchRepayRequestDetail.getProd_id());
            }
            logger.info("【借款人还款申请】-->标的存在-->order_no:"+batchRepayRequestDetail.getDetail_no());


            AccountSubjectInfo prodAccountSubjectInfo = accountQueryService.queryAccount(productInfo.getPlat_no(),productInfo.getProd_account(), Tsubject.CASH.getCode(), Ssubject.PROD.getCode());
            if(prodAccountSubjectInfo == null){
                logger.info("【借款人还款申请】-->标的账号不存在-->order_no:"+batchRepayRequestDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.INVALID_ACCOUNT,BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",标的账户不存在platcust:" + productInfo.getProd_account());
            }
            logger.info("【借款人还款申请】-->标的账号存在-->order_no:"+batchRepayRequestDetail.getDetail_no());

            AccountSubjectInfo custAccountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(), batchRepayRequestDetail.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
            if(custAccountSubjectInfo == null){
                logger.info("【借款人还款申请】-->借款人账号不存在-->order_no:"+batchRepayRequestDetail.getDetail_no());
                throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF,BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF) + ",借款人账户不存在,请检查融资账户："+batchRepayRequestDetail.getPlatcust());
            }

            //插入进借款人信息表
            ProdBorrowerRealrepay prodBorrowerRealrepay = new ProdBorrowerRealrepay();
            prodBorrowerRealrepay.setBorrower_id(batchRepayRequestDetail.getDetail_no());//融资编号
            prodBorrowerRealrepay.setPlat_no(productInfo.getPlat_no());//平台编号
            prodBorrowerRealrepay.setRepay_num(batchRepayRequestDetail.getRepay_num());//还款期别
            prodBorrowerRealrepay.setProd_id(batchRepayRequestDetail.getProd_id());//产品编号
            prodBorrowerRealrepay.setRepay_date(DateUtils.formatDateToStr(batchRepayRequestDetail.getRepay_date()));//计划还款日期
            prodBorrowerRealrepay.setRepay_amt(batchRepayRequestDetail.getRepay_amt());//计划还款金额
            prodBorrowerRealrepay.setPlatcust(batchRepayRequestDetail.getPlatcust());//借款人编号
            prodBorrowerRealrepay.setRemark(batchRepayRequestDetail.getRemark());//备注
            prodBorrowerRealrepay.setCreate_time(DateUtils.getNow());//创建时间
            prodBorrowerRealrepay.setUpdate_time(DateUtils.getNow());//修改时间
            prodBorrowerRealrepay.setTrans_date(baseRequest.getPartner_trans_date());//交易日期
            prodBorrowerRealrepay.setTrans_time(baseRequest.getPartner_trans_time());//交易时间
            prodBorrowerRealrepay.setEnabled(Constants.ENABLED);//删除标记
            prodBorrowerRealrepay.setStatus(RepaymentStatus.SUCCPAY.getCode());
            prodBorrowerRealrepayMapper.insert(prodBorrowerRealrepay);

            EaccAccountamtlist eaccAccountamtlist = new EaccAccountamtlist();
            //科目
            eaccAccountamtlist.setPlat_no(productInfo.getPlat_no());
            eaccAccountamtlist.setPlatcust(custAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setSubject(custAccountSubjectInfo.getSubject());
            eaccAccountamtlist.setSub_subject(custAccountSubjectInfo.getSub_subject());

            eaccAccountamtlist.setOrder_no(transTransReq.getOrder_no());
            eaccAccountamtlist.setTrans_serial(transTransReq.getTrans_serial());
            eaccAccountamtlist.setTrans_code(TransConsts.BORROWREPAY_CODE);
            eaccAccountamtlist.setTrans_name(TransConsts.BORROWREPAY_NAME);
            eaccAccountamtlist.setTrans_date(baseRequest.getPartner_trans_date());
            eaccAccountamtlist.setTrans_time(baseRequest.getPartner_trans_time());
            eaccAccountamtlist.setAmt(batchRepayRequestDetail.getTrans_amt());
            eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());

            //对手科目
            eaccAccountamtlist.setOppo_plat_no(prodAccountSubjectInfo.getPlat_no());
            eaccAccountamtlist.setOppo_platcust(prodAccountSubjectInfo.getPlatcust());
            eaccAccountamtlist.setOppo_subject(prodAccountSubjectInfo.getSubject());
            eaccAccountamtlist.setOppo_sub_subject(prodAccountSubjectInfo.getSub_subject());

            eaccAccountamtlist.setRemark("借款人批量还款，借款人还款给标的账户");

            accountTransferService.transfer(eaccAccountamtlist);

            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
            logger.info("【借款人还款申请】-->修改明细订单成功-->order_no:"+batchRepayRequestDetail.getDetail_no());
            //给user发送异步消息
        }catch (Exception e){
            logger.error("【借款人还款申请】-->异常-->order_no:"+batchRepayRequestDetail.getDetail_no(),e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            if(transTransReq != null){
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransReq);
            }

            prodBaseResponse.setRecode(baseResponse.getRecode());
            prodBaseResponse.setRemsg(baseResponse.getRemsg());
            prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
        notifyService.addNotify(notifyData);
        logger.info("【借款人还款申请】-->添加mq异步通知成功-->order_no:"+batchRepayRequestDetail.getDetail_no());
    }

    @Override
    public void doProdRepay(BaseRequest baseRequest, BatchCustRepay batchCustRepay, String notifyURL) throws BusinessException {
        ProdBaseVestResponse prodBaseResponse=new ProdBaseVestResponse();
        NotifyData notifyData=new NotifyData();
        try{
            ProdBatchRepayAsynRequest prodBatchRepayAsynRequest = (ProdBatchRepayAsynRequest)baseRequest;
            //发送异步通知
            notifyData.setNotifyUrl(notifyURL);
            notifyData.setMall_no(baseRequest.getMall_no());
            prodBaseResponse.setOrder_no(batchCustRepay.getDetail_no());
            prodBaseResponse.setPlatcust(batchCustRepay.getCust_no());
            prodBaseResponse.setProd_id(prodBatchRepayAsynRequest.getProd_id());
            BigDecimal rates_amt = BigDecimal.ZERO;
            BigDecimal experience_amt = BigDecimal.ZERO;
            if(batchCustRepay.getRates_amt()!=null){
                rates_amt = rates_amt.add(batchCustRepay.getRates_amt());
            }
            if(batchCustRepay.getExperience_amt()!=null){
                experience_amt = experience_amt.add(batchCustRepay.getExperience_amt());
            }
            prodBaseResponse.setTrans_amt(rates_amt.add(batchCustRepay.getReal_repay_amount()).add(batchCustRepay.getReal_repay_val()).add(experience_amt));
            prodInvestmentExtService.prodRepayForNoSyn(baseRequest,batchCustRepay);
            prodBaseResponse.setRecode(BusinessMsg.SUCCESS);
            prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error("【标的还款】==========还款异常："+e.getMessage());
            TransTransreq transTransReq = transReqService.queryTransTransReqByOrderno(batchCustRepay.getDetail_no());
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                transTransReq.setReturn_code(BusinessMsg.RUNTIME_EXCEPTION);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transReqService.insert(transTransReq);
            prodBaseResponse.setRecode(baseResponse.getRecode());
            prodBaseResponse.setRemsg(baseResponse.getRemsg());
            prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
        notifyService.addNotify(notifyData);
    }

    @Override
    public void doTransProd(List<ProdTransferDebtRequestBo> prodTransferDebtRequestBoList,String notifyURL) throws BusinessException {
        for(ProdTransferDebtRequestBo prodTransferDebtRequestBo:prodTransferDebtRequestBoList){
            //发送异步通知
            ProdBaseResponse prodBaseResponse=new ProdBaseResponse();
            NotifyData notifyData=new NotifyData();
            notifyData.setNotifyUrl(notifyURL);
            notifyData.setMall_no(prodTransferDebtRequestBo.getMall_no());
            String orginiOrderNo =  prodTransferDebtRequestBo.getOrigin_order_no();
            //非授权模式
            TransTransreq transTransReq = transReqService.queryTransTransReqByOrderno(prodTransferDebtRequestBo.getDetail_no());
            if(transTransReq==null || FlowStatus.FAIL.getCode().equals(transTransReq.getStatus()) || FlowStatus.SUCCESS.getCode().equals(transTransReq.getStatus())){
                return;
            }
            if(!StringUtils.isEmpty(orginiOrderNo)){
                //检查原申请订单号是否存在
                TransTransreq transTransreq = transReqService.queryTransTransReqByOrderno(orginiOrderNo);
                if(transTransreq == null){
                    //更新确认订单为失败
                    TransTransreq comfirmOrder = new TransTransreq();
                    comfirmOrder.setStatus(OrderStatus.FAIL.getCode());
                    comfirmOrder.setReturn_code(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY);
                    comfirmOrder.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY));
                    boolean updateFlag = transReqService.updateOrderInfo(comfirmOrder,prodTransferDebtRequestBo.getDetail_no());
                    if(!updateFlag){
                        logger.error("ProdMQOptionService|doTransProd|updateError"+ prodTransferDebtRequestBo.getDetail_no());
                    }
                    prodBaseResponse.setRecode(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY);
                    prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY));
                    prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                    prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
                    notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
                    notifyService.addNotify(notifyData);
                    return;
                }
            }
            try{
                if(prodTransferDebtRequestBo.getDetail_no()==null){
                    prodBaseResponse.setOrder_info(prodTransferDebtRequestBo.getOrder_no());
                }else{
                    prodBaseResponse.setOrder_info(prodTransferDebtRequestBo.getDetail_no());
                }
                prodBaseResponse.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
                prodBaseResponse.setProd_id(prodTransferDebtRequestBo.getProd_id());
                prodBaseResponse.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
                BigDecimal comAmt = BigDecimal.ZERO;
                BigDecimal comAmt_ext = BigDecimal.ZERO;
                BigDecimal coupon_amt=BigDecimal.ZERO;
                if(prodTransferDebtRequestBo.getCommissionObject()!=null && prodTransferDebtRequestBo.getCommissionObject().getPayout_amt()!=null){
                    comAmt = comAmt.add(prodTransferDebtRequestBo.getCommissionObject().getPayout_amt());
                }
                if(prodTransferDebtRequestBo.getCommission_extObject()!=null && prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt()!=null){
                    comAmt_ext = comAmt_ext.add(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
                }
                if(prodTransferDebtRequestBo.getCoupon_amt()!=null && prodTransferDebtRequestBo.getCoupon_amt().compareTo(BigDecimal.ZERO)>0){
                    coupon_amt = coupon_amt.add(prodTransferDebtRequestBo.getCoupon_amt());
                }
                prodBaseResponse.setAmt(prodTransferDebtRequestBo.getDeal_amount().add(coupon_amt).add(comAmt).add(comAmt_ext).add(prodTransferDebtRequestBo.getTransfer_income()));
                prodBaseResponse.setOppo_platcust(prodTransferDebtRequestBo.getDeal_platcust());
                //记录数据到redis
                String dataKey=Constants.PROD_TRANS_NOTIFY_DATA+"prodBaseResponse:"+prodTransferDebtRequestBo.getDetail_no();
                notifyData.setNotifyContent(JSON.toJSONString(prodTransferDebtRequestBo));
                CacheUtil.getCache().set(dataKey,JSON.toJSONString(notifyData));
                prodInvestmentExtService.transProd(prodTransferDebtRequestBo,null);
            }catch (Exception e){
                logger.error("【批量债转】==========债转异常："+e.getMessage());
                BaseResponse baseResponse=new BaseResponse();
                if(e instanceof BusinessException){
                    baseResponse=((BusinessException)e).getBaseResponse();
                }else{
                    baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                }
                prodBaseResponse.setRecode(baseResponse.getRecode());
                prodBaseResponse.setRemsg(baseResponse.getRemsg());
                prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
                notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
                notifyService.addNotify(notifyData);
            }
        }
    }

    @Override
    public void doRefund(ProdRefundData prodRefundData) throws BusinessException {
        ProdProductinfo prodInfo=prodRefundData.getProductinfo();
        List<BatchCustRepay> batchCustRepayList=prodRefundData.getCustRepayList();
        for(BatchCustRepay batchCustRepay:batchCustRepayList){
            TransTransreq transTransReq=batchCustRepay.getTransTransreq();
            NotifyData notifyData=new NotifyData();
            notifyData.setMall_no(batchCustRepay.getMall_no());
            notifyData.setNotifyUrl(batchCustRepay.getNotify_url());
            ProdBaseResponse prodBaseResponse=new ProdBaseResponse();
            prodBaseResponse.setOrder_no(batchCustRepay.getDetail_no());
            prodBaseResponse.setPlatcust(batchCustRepay.getCust_no());
            prodBaseResponse.setProd_id(batchCustRepay.getProd_id());
            prodBaseResponse.setAmt(batchCustRepay.getReal_repay_amt());
            String lockKey="ProdRefundLock"+transTransReq.getOrder_no();
            TransTransreq oldTransTransReq = transReqService.queryTransTransReqByOrderno(transTransReq.getOrder_no());
            if(oldTransTransReq==null || FlowStatus.FAIL.getCode().equals(oldTransTransReq.getStatus())
                    || FlowStatus.SUCCESS.getCode().equals(oldTransTransReq.getStatus())) {
                return;
            }
            try{
                if(prodInfo == null){
                    logger.info(String.format("【标的还款】标的不存在|prod_id:%s|detail_no:%s",
                            batchCustRepay.getProd_id(),batchCustRepay.getDetail_no()));
                    throw new BusinessException(BusinessMsg.INVALID_PRODUCT_ID,BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID));
                }

                //不再支持活期还款
                logger.info(String.format("【标的还款】当前标的状态为:%s|prod_id:%s|detail_no:%s",
                        prodInfo.getProd_state(),prodInfo.getProd_id(),batchCustRepay.getDetail_no()));
                if(ProdType.PERIOD.getCode().equals(prodInfo.getProd_type()) && !ProdState.FOUND.getCode().equals(prodInfo.getProd_state())){
                    logger.info(String.format("【标的还款】标的未成标|prod_id:%s|detail_no:%s",
                            prodInfo.getProd_id(),batchCustRepay.getDetail_no()));
                    throw new BusinessException(BusinessMsg.ESTABLISH_PRODUCT_FAILED,BusinessMsg.getMsg(BusinessMsg.ESTABLISH_PRODUCT_FAILED));
                }


				checkRefundData(batchCustRepay,prodInfo);
                logger.info("【标的还款】开始执行还款");
                //======================设置还款成功后所需要的数据===============
                prodBaseResponse.setOrder_info(JSON.toJSONString(batchCustRepay));
                notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse));
                CacheUtil.getCache().set(lockKey,JSON.toJSONString(notifyData));
                logger.info(String.format("【标的还款】redis值set完成|prod_id:%s|detail_no:%s|redis_key:%s|redis_value:%s|ttl:%s",
                        prodInfo.getProd_id(),batchCustRepay.getDetail_no(),lockKey,JSON.toJSONString(notifyData),CacheUtil.getCache().ttl(lockKey)));
                //====================================================================================================
                Boolean refundSuccess=productRefundExtService.refundForAsyn(batchCustRepay,transTransReq,prodInfo);
                logger.info(String.format("【标的还款】还款逻辑执行完成|prod_id:%s|detail_no:%s|refundSuccess:%s",
                        prodInfo.getProd_id(),batchCustRepay.getDetail_no(),refundSuccess));
            }catch (Exception e){
                BaseResponse baseResponse=new BaseResponse();
                if(e instanceof BusinessException){
                    baseResponse=((BusinessException) e).getBaseResponse();
                }else{
                    logger.error("【标的还款】==========异常："+e.getMessage());
                    baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                }
                prodBaseResponse.setRecode(baseResponse.getRecode());
                prodBaseResponse.setRemsg(baseResponse.getRemsg());
                prodBaseResponse.setOrder_status(FlowStatus.FAIL.getCode());
                prodBaseResponse.setOrder_info(BusinessMsg.getMsg(BusinessMsg.FAIL));
                notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse));
                notifyService.addNotify(notifyData);
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                logger.info("【标的还款】==========添加流水");
                transReqService.insert(transTransReq);
                //异常后删除缓存数据
                logger.info("【标的还款】删除还款缓存|detail_no:{}|key{}",batchCustRepay.getDetail_no(),lockKey);
                CacheUtil.getCache().del(lockKey);
            }

        }
    }

    @Override
    public void doSingleRefund(ProdSingleRefundData prodSingleRefundData) throws BusinessException {
        ProdProductinfo prodInfo=prodSingleRefundData.getProductinfo();
        BatchCustRepay batchCustRepay=prodSingleRefundData.getCustRepay();
        TransTransreq transTransReq=batchCustRepay.getTransTransreq();
        try{
            logger.info(String.format("【标的还款】开始执行还款|trans_serial:%s",transTransReq.getTrans_serial()));
            Boolean refundSuccess=productRefundExtService.refundForAsyn(batchCustRepay,transTransReq,prodInfo);
            if(refundSuccess){
                transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
                transTransReq.setReturn_code(BusinessMsg.SUCCESS);
                transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

                try{
                    logger.info(String.format("【标的还款】还款完成,给用户发送短信|trans_serial:%s|platcust:%s",
                            transTransReq.getTrans_serial(),batchCustRepay.getCust_no()));

                    EaccUserinfo eaccUserinfo=getEaccUserInfo(batchCustRepay.getMall_no(),batchCustRepay.getCust_no());
                    if(eaccUserinfo!=null){
                        MsgModel msgModel=new MsgModel();
                        String mall_name=sysParameterService.querySysParameter(batchCustRepay.getMall_no(), SysParamterKey.MALL_NAME);

                        BigDecimal allAmt=sendMsgService.getAccountAllAmount(batchCustRepay.getCust_no());
                        msgModel.setOrder_no(batchCustRepay.getOrder_no());
                        msgModel.setPlat_no(batchCustRepay.getPlat_no());
                        msgModel.setTrans_code(transTransReq.getTrans_code());
                            msgModel.setMobile(eaccUserinfo.getMobile());
                        msgModel.setMall_no(batchCustRepay.getMall_no());
                        msgModel.setPlatcust(batchCustRepay.getCust_no());
                        BigDecimal trans_amt=getRefundAmt(batchCustRepay);
                        String content=sysParameterService.querySysParameter(batchCustRepay.getMall_no(),MsgContent.INVEST_REFUND_NOTIFY.getMsg());
                        if(StringUtils.isNotBlank(content)){
                            msgModel.setMsgContent(String.format(content,
                                    mall_name, NumberUtils.formatNumber(trans_amt),NumberUtils.formatNumber(allAmt)));
                            //=========ccb参数===========
                            msgModel.setTrans_serial(transTransReq.getTrans_serial());
                            msgModel.setMsg_type(MsgType.INVEST_REFUND_NOTIFY.getType());
                            msgModel.setAmount(trans_amt);
//                        //===========================
                            sendMsgService.addMsgToQueue(msgModel);
                        }
            // ServiceAsyncHelper serviceAsyncHelper =new ServiceAsyncHelper();

            //    ServiceAsyncHelper.publishTopic(amqpTemplate,msgModel);
                     logger.info(String.format("【标的还款】短信已加入发送队列|trans_serial:%s",transTransReq.getTrans_serial()));
                  }
                }catch (Exception e){
                    logger.info("【标的还款】短信发送异常:",e);
                    //MQ异常之后的解决方案
                }
            }
        }catch (Exception e){
            logger.error("【标的还款】异常：",e);
            BaseResponse baseResponse=new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse=((BusinessException) e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            transTransReq.setStatus(FlowStatus.FAIL.getCode());
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
        }

        try{
            logger.info("【标的还款】========扣除redis中待还款数额");
            Object amtObj= CacheUtil.getCache().getAndNotSetAlive("Prod"+prodInfo.getProd_account()+"AllAmt");
            logger.info("【标的还款】==========标的账户："+prodInfo.getProd_account()+
                    "，已经需累计还款："+amtObj);
            if(amtObj!=null){
                BigDecimal prodFundAllAmt=(BigDecimal) amtObj;
                if(batchCustRepay.getReal_repay_amount()!=null){
                    prodFundAllAmt=prodFundAllAmt.subtract(batchCustRepay.getReal_repay_amount());
                }
                if(batchCustRepay.getReal_repay_val()!=null){
                    prodFundAllAmt=prodFundAllAmt.subtract(batchCustRepay.getReal_repay_val());
                }
                logger.info("【标的还款】==========剩余需还款："+prodFundAllAmt);
                if(prodFundAllAmt.compareTo(BigDecimal.ZERO)<=0){
                    logger.info("【标的还款】==========删除需还款金额缓存");
                    CacheUtil.getCache().del("Prod"+prodInfo.getProd_account()+"AllAmt");
                }else{
                    logger.info("【标的还款】==========重新设置需还款金额");
                    CacheUtil.getCache().set("Prod"+prodInfo.getProd_account()+"AllAmt",prodFundAllAmt);
                }
            }
        }catch (Exception e){
            logger.error("【标的还款】========扣除redis中待还款数额失败：",e);
        }
        logger.info("【标的还款】==========添加流水");
        transReqService.insert(transTransReq);
    }

    @Override
    public void finishTransProd(TransTransreq transTransreq) throws BusinessException {
        //redis加锁，判断当前转让订单是否已完成
        boolean sendMsgToBuyer=false;
        BigDecimal amtBigDecimal=BigDecimal.ZERO;
        String lockKey="AssignmentLock"+transTransreq.getOrder_no();
        boolean lock=CacheUtil.getLock(lockKey);
        while (!lock){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.error("【批量债转】==========休眠异常");
            }
            lock=CacheUtil.getLock(lockKey);
        }
        try{
            ProdBaseResponse prodBaseResponse=new ProdBaseResponse();
            NotifyData notifyData=new NotifyData();
            ProdTransferDebtRequestBo prodTransferDebtRequestBo=new ProdTransferDebtRequestBo();
            String dataKey=Constants.PROD_TRANS_NOTIFY_DATA+"prodBaseResponse:"+transTransreq.getOrder_no();
            Object dataObj=CacheUtil.getCache().get(dataKey);
            String countKey="Assignment"+transTransreq.getOrder_no();
            if(dataObj!=null){
                notifyData=JSON.parseObject(String.valueOf(dataObj),NotifyData.class);
                prodTransferDebtRequestBo=JSON.parseObject(notifyData.getNotifyContent(),ProdTransferDebtRequestBo.class);
                BigDecimal successAmt=prodTransferDebtRequestBo.getDeal_amount();
                if(prodTransferDebtRequestBo.getCommissionObject()!=null && prodTransferDebtRequestBo.getCommissionObject().getPayout_amt()!=null){
                    successAmt=successAmt.subtract(prodTransferDebtRequestBo.getCommissionObject().getPayout_amt());
                }
                if(prodTransferDebtRequestBo.getTransfer_income()!=null){
                    successAmt=successAmt.add(prodTransferDebtRequestBo.getTransfer_income());
                }
                if(prodTransferDebtRequestBo.getDetail_no()==null){
                    prodBaseResponse.setOrder_info(prodTransferDebtRequestBo.getOrder_no());
                }else{
                    prodBaseResponse.setOrder_info(prodTransferDebtRequestBo.getDetail_no());
                }
                prodBaseResponse.setOrder_no(prodTransferDebtRequestBo.getDetail_no());
                prodBaseResponse.setProd_id(prodTransferDebtRequestBo.getProd_id());
                prodBaseResponse.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
                BigDecimal comAmt = BigDecimal.ZERO;
                BigDecimal comAmt_ext = BigDecimal.ZERO;
                BigDecimal coupon_amt=BigDecimal.ZERO;
                if(prodTransferDebtRequestBo.getCommissionObject()!=null && prodTransferDebtRequestBo.getCommissionObject().getPayout_amt()!=null){
                    comAmt = comAmt.add(prodTransferDebtRequestBo.getCommissionObject().getPayout_amt());
                }
                if(prodTransferDebtRequestBo.getCommission_extObject()!=null && prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt()!=null){
                    comAmt_ext = comAmt_ext.add(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
                }
                if(prodTransferDebtRequestBo.getCoupon_amt()!=null && prodTransferDebtRequestBo.getCoupon_amt().compareTo(BigDecimal.ZERO)>0){
                    coupon_amt = coupon_amt.add(prodTransferDebtRequestBo.getCoupon_amt());
                }
                prodBaseResponse.setAmt(prodTransferDebtRequestBo.getDeal_amount().add(coupon_amt).add(comAmt).add(comAmt_ext).add(prodTransferDebtRequestBo.getTransfer_income()));
                prodBaseResponse.setOppo_platcust(prodTransferDebtRequestBo.getDeal_platcust());
                prodBaseResponse.setRecode(transTransreq.getReturn_code());
                prodBaseResponse.setRemsg(transTransreq.getReturn_msg());
                if(FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
                    //处理成功
                    prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
                    prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
                }else{
                    //处理失败，回滚数据
                    logger.info("【批量债转】转账失败，回滚份额|order_no:"+transTransreq.getOrder_no());
                    productRefundDBOptionService.backProdRefund(transTransreq);
                    prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                    prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
                }
                Object count=CacheUtil.getCache().getAndNotSetAlive(countKey+"Count");
                logger.info("【批量债转】==========剩余转让笔数"+count+"，本笔转让金额："+successAmt);
                if(count!=null){
                    Object amt=CacheUtil.getCache().getAndNotSetAlive(countKey+"Amt");
                    int countInt= (int) count;
                    amtBigDecimal= (BigDecimal) amt;
                    countInt--;
                    amtBigDecimal=amtBigDecimal.add(successAmt);
                    if(countInt<=0){
                        if(amtBigDecimal.compareTo(BigDecimal.ZERO)>0){
                            //可以发送短信给转让人
                            sendMsgToBuyer=true;
                        }
                        //删除redis数据
                        CacheUtil.getCache().del(countKey+"Count");
                        CacheUtil.getCache().del(countKey+"Amt");
                    }else{
                        //修改redis的值
                        CacheUtil.getCache().set(countKey+"Count",countInt);
                        CacheUtil.getCache().set(countKey+"Amt",amtBigDecimal);
                    }

                }
            }else {
                CacheUtil.getCache().del(countKey);
            }
            CacheUtil.unlock(lockKey);

            if(dataObj!=null){
                notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
                notifyService.addNotify(notifyData);
                //发送短信
                try{
                    TransTransreq transTransReq=transReqService.queryTransTransReqByOrderno(transTransreq.getOrder_no());

                    if(sendMsgToBuyer){
                        logger.info("【标的转让】========发送通知短信给转让人");


                        MsgModel msgModel=new MsgModel();
                        String mall_name=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(), SysParamterKey.MALL_NAME);
                        BigDecimal allAmt=sendMsgService.getAccountAllAmount(prodTransferDebtRequestBo.getDeal_platcust());
                        msgModel.setOrder_no(prodTransferDebtRequestBo.getOrder_no());
                        msgModel.setPlat_no(prodTransferDebtRequestBo.getMer_no());
                        msgModel.setTrans_code(transTransReq.getTrans_code());
                        msgModel.setMall_no(prodTransferDebtRequestBo.getMall_no());
                        msgModel.setPlatcust(prodTransferDebtRequestBo.getDeal_platcust());
                        String content=sysParameterService.querySysParameter(transTransreq.getExt1(),MsgContent.INVEST_SUCCESS_NOTIFY.getMsg());
                        if(StringUtils.isNotBlank(content)){
                            msgModel.setMsgContent(String.format(content,
                                    mall_name, NumberUtils.formatNumber(amtBigDecimal),NumberUtils.formatNumber(allAmt)));
                            //=========ccb参数===========
                            msgModel.setTrans_serial(transTransReq.getTrans_serial());
                            msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                            msgModel.setAmount(amtBigDecimal);

                            sendMsgService.addMsgToQueue(msgModel);
                        }
                    }

                    logger.info("【标的转让】========发送通知短信给受让人");
                    BigDecimal transAmt=BigDecimal.ZERO;
                    transAmt=transAmt.add(prodTransferDebtRequestBo.getDeal_amount());
                    if(prodTransferDebtRequestBo.getCommission_extObject()!=null && prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt()!=null){
                        transAmt=transAmt.add(prodTransferDebtRequestBo.getCommission_extObject().getPayout_amt());
                    }
                    MsgModel msgModel=new MsgModel();
                    String mall_name=sysParameterService.querySysParameter(prodTransferDebtRequestBo.getMall_no(), SysParamterKey.MALL_NAME);
                    BigDecimal allAmt=sendMsgService.getAccountAllAmount(prodTransferDebtRequestBo.getDeal_platcust());
                    msgModel.setOrder_no(prodTransferDebtRequestBo.getOrder_no());
                    msgModel.setPlat_no(prodTransferDebtRequestBo.getMer_no());
                    msgModel.setTrans_code(transTransReq.getTrans_code());
//                msgModel.setMobile(eaccUserinfo.getMobile());
                    msgModel.setPlatcust(prodTransferDebtRequestBo.getPlatcust());
                    String content=sysParameterService.querySysParameter(transTransreq.getExt1(),MsgContent.INVEST_SUCCESS_NOTIFY.getMsg());
                    if(StringUtils.isNotBlank(content)){
                        msgModel.setMsgContent(String.format(content,
                                mall_name, NumberUtils.formatNumber(transAmt),NumberUtils.formatNumber(allAmt)));
                        //=========ccb参数===========
                        msgModel.setTrans_serial(transTransReq.getTrans_serial());
                        msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                        msgModel.setAmount(transAmt);
//                //===========================
                        sendMsgService.addMsgToQueue(msgModel);
                    }
                }catch (Exception e){
                    logger.info("【短信发送异常】========"+e);
                }
            }
        }catch (Exception e){
            logger.error("【标的转让】结束异常：",e);
            throw e;
        } finally {
            CacheUtil.unlock(lockKey);
        }
        transReqService.insert(transTransreq);
    }
    @Override
    @Transactional
public  void investAsyncCallBack(TransTransreq callbackTrans) throws BusinessException {
        TransTransreq cachedTrans = transReqService.queryTransTransReqByOrderNoAndBatchOrderNo(callbackTrans.getTrans_serial(), callbackTrans.getOrder_no());
        if (cachedTrans == null) {
            logger.error(String.format("【异步回调-invest】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。查找不到该流水",
                    callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg()));
            throw new BusinessException(BusinessMsg.PARAMETER_ERROR, BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
        }
        investAsyncUpdateTransStatus(callbackTrans,cachedTrans);
        investAsyncUpdateShareList(callbackTrans,cachedTrans);
        if (OrderStatus.SUCCESS.getCode().equals(callbackTrans.getStatus())) {
            investAsyncMessage(callbackTrans,cachedTrans);
        }
            investAsyncNotify(callbackTrans,cachedTrans);

    }

    private void investAsyncUpdateShareList(TransTransreq callbackTrans, TransTransreq cachedTrans) {
        String serial=callbackTrans==null?"":callbackTrans.getTrans_serial();
        try {
            Object cached = CacheUtil.getCache().get(Constants.PROD_INVEST_NOTIFY_DATA + callbackTrans.getOrder_no());
            logger.info("【批量异步投标】serial="+serial+"========份额明细更新，从redis中获取授权数据"+cached);
            if(cached == null){
                logger.info("【批量异步投标】redis中不存在该订单信息");
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
            BaseRequest baseRequest = JSON.parseObject((String) cached, BaseRequest.class);
            String extData=baseRequest.getRemark();

            String platcust="";
            String prodId="";
            String realVol="";
            BigDecimal trans_amt=new BigDecimal(0);
            if  (StringUtils.isNotBlank(extData)&&extData.indexOf("|")>=0) {
                platcust =StringUtils.split(extData,"|") [0];
                prodId =StringUtils.split(extData,"|") [2];
                realVol=StringUtils.split(extData,"|") [3];
            }
            if (OrderStatus.SUCCESS.getCode().equals(callbackTrans.getStatus())) {
               updateShrealistSucc(callbackTrans);

            }else {
                try {
                    Map<String, Object> params = new HashMap<>();
                    params.put("prod_id",prodId);
                    params.put("plat_no", baseRequest.getMer_no());
                    params.put("vol", realVol);
                    params.put("update_by",SeqUtil.RANDOM_KEY);
                    params.put("update_time",new Date());
                    custProdProdInfoMapper.addProdLimit(params);
                } catch (Exception ex) {
                    String content = "【批量投标】========标的剩余份额更新反向操作失败 platcust:" + platcust +  ",prod_id:" + prodId + ",vol:" + realVol;
                    //给管理员发送通知
                   logger.error(content,ex);
                   sendMsgService.sendErrorToAdmin(content,baseRequest.getMer_no());
                }
            }
        } catch (Exception e) {
            logger.error("serial="+serial+e.getMessage() , e);
        }

    }

    private void updateShrealistSucc(TransTransreq callbackTrans) {
        if (StringUtils.isNotBlank(callbackTrans.getTrans_serial())) {
            ProdShareListExample prodShareListExample = new ProdShareListExample();
            ProdShareListExample.Criteria criteria = prodShareListExample.createCriteria();
            criteria.andTrans_serialEqualTo(callbackTrans.getTrans_serial());
            criteria.andEnabledEqualTo(Constants.DISABLED);
            List<ProdShareList> prodShareLists = prodShareListMapper.selectByExample(prodShareListExample);
            logger.info("【批量异步投标-回调】prodShareLists=======" + JSON.toJSON(prodShareLists) + "size===" + prodShareLists.size());
            if (prodShareLists.size() > 0) {
                prodShareLists.forEach(prodShareList -> {
                    prodShareList.setEnabled(Constants.ENABLED);
                    if(null == prodShareList.getUpdate_by()) prodShareList.setUpdate_by(SeqUtil.RANDOM_KEY);
                    prodShareList.setUpdate_time(new Date());
                    int i = prodShareListMapper.updateByPrimaryKeySelective(prodShareList);
                    logger.info("【批量异步投标-回调】更新份额成功=======" + JSON.toJSON(prodShareLists) + "size===" + prodShareLists.size()+",id="+prodShareList.getId()+"，setEnabled=1");
                });
//                int i = prodShareListMapper.deleteByExample(prodShareListExample);
//                logger.info("【批量异步投标-回调】deleteByExample返回值为：" + i);
//                if (i == 1) {
//                    logger.info("【批量异步投标-回调】删除未回滚份额成功=======" + JSON.toJSON(prodShareLists) + "size===" + prodShareLists.size());
//                }
            }
        }else{
            logger.error("【批量异步投标-回调】callbackTrans.getTrans_serial()=null"  );
        }
    }

    private void investAsyncUpdateTransStatus(TransTransreq callbackTrans ,TransTransreq cachedTrans) {
    String serial=callbackTrans==null?"":callbackTrans.getTrans_serial();
    try {
        transReqService.insert(callbackTrans);
        logger.info(String.format("【异步回调-invest】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。batchTranorderno=%s",
                        callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(),  callbackTrans.getOrder_no()));
//        if (StringUtils.isNotBlank(cachedTrans.getBatch_order_no())) {
//            List<TransTransreq> batchOrderTrans = transReqService.queryTransTransReq(cachedTrans.getBatch_order_no());
//            if (batchOrderTrans != null && batchOrderTrans.size() > 0) {
//               // boolean isNeedUpdateBatch = true;
//                TransTransreq batchTran = null;
//                int totalTrans=0;
//                int successTrans=0;
//                int failTrans=0;
//                for (int i = 0; i < batchOrderTrans.size(); i++) {
//                    if (batchOrderTrans.get(i).getOrder_no().equals(cachedTrans.getBatch_order_no())) {
//                        batchTran = batchOrderTrans.get(i);
//                        continue;
//                    }
//
//                    if(OrderStatus.SUCCESS.getCode().equals(batchOrderTrans.get(i).getStatus())){
//                        successTrans++;
//                    }else{
//                        failTrans++;
//                    }
//                    totalTrans++;
//
//                }
//                if (  batchTran != null) {
//                    if (totalTrans==successTrans) {
//                        batchTran.setStatus(OrderStatus.SUCCESS.getCode());
//                    }else if(totalTrans==failTrans){
//                        batchTran.setStatus(OrderStatus.FAIL.getCode());
//                    }else{
//                        batchTran.setStatus(OrderStatus.PROCESSING.getCode());
//                    }
//                    batchTran.setReturn_code(cachedTrans.getReturn_code());
//                    batchTran.setReturn_msg(cachedTrans.getReturn_msg());
//                    transReqService.insert(batchTran);
//                }
//                logger.info(String.format("【异步回调-invest】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。batchTranorderno=%s",
//                        callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(),  batchTran.getOrder_no()));
//            }
//        } else {
//            logger.error(String.format("【异步回调-invest】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。找不到批次订单号",
//                    callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg()));
//        }

    } catch (Exception e) {
        logger.error("serial="+serial+e.getMessage() , e);
    }

}
    private void investAsyncMessage(TransTransreq callbackTrans, TransTransreq cachedTrans){
        String serial=callbackTrans==null?"":callbackTrans.getTrans_serial();
        try{
            logger.info("【批量异步投标】serial="+serial+"========发送通知短信");
            Object cached = CacheUtil.getCache().get(Constants.PROD_INVEST_NOTIFY_DATA + callbackTrans.getOrder_no());
            logger.info("【批量异步投标】serial="+serial+"========发送通知短信，从redis中获取授权数据"+cached);
            if(cached == null){
                logger.info("【批量异步投标】redis中不存在该订单信息");
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT,BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
            BaseRequest baseRequest = JSON.parseObject((String) cached, BaseRequest.class);
            String extData=baseRequest.getRemark();

            String platcust="";
            BigDecimal trans_amt=new BigDecimal(0);
            if  (StringUtils.isNotBlank(extData)&&extData.indexOf("|")>=0) {
                trans_amt = new BigDecimal(StringUtils.split(extData,"|") [1]);
                platcust = StringUtils.split(extData,"|") [0];
            }
            EaccUserinfo eaccUserinfo=getEaccUserInfo(baseRequest.getMall_no(),platcust);

            if(eaccUserinfo!=null){
                MsgModel msgModel=new MsgModel();
                String mall_name=sysParameterService.querySysParameter(baseRequest.getMall_no(), SysParamterKey.MALL_NAME);
                BigDecimal allAmt=sendMsgService.getAccountAllAmount(platcust);
                msgModel.setOrder_no(callbackTrans.getOrder_no());
                msgModel.setPlat_no(baseRequest.getMer_no());
                msgModel.setTrans_code(TransConsts.BUY_CODE);
                msgModel.setMobile(eaccUserinfo.getMobile());
                String content=sysParameterService.querySysParameter(baseRequest.getMall_no(),MsgContent.INVEST_SUCCESS_NOTIFY.getMsg());
                if(StringUtils.isNotBlank(content)){
                    msgModel.setMsgContent(String.format(content,
                            mall_name, NumberUtils.formatNumber(trans_amt),NumberUtils.formatNumber(allAmt)));
                    //=========ccb参数===========
                    msgModel.setTrans_serial(callbackTrans.getTrans_serial());
                    msgModel.setMsg_type(MsgType.INVEST_SUCCESS_NOTIFY.getType());
                    msgModel.setAmount(trans_amt);
                    //===========================
                    sendMsgService.addMsgToQueue(msgModel);
                }
            }
        }catch (Exception e){

            logger.error( "【短信发送异常】serial="+serial+"========"+e.getCause(),e);
        }
    }
    public void investAsyncNotify(TransTransreq callbackTrans, TransTransreq cachedTrans) {
        try {
            String key = Constants.AUTHPAYFEE_ORDER_KEY + serviceName + "_order:" + callbackTrans.getOrder_no();
            String notifyURL = cachedTrans.getNotify_url();
            Object cached = CacheUtil.getCache().get(Constants.PROD_INVEST_NOTIFY_DATA + callbackTrans.getOrder_no());
            logger.info("【批量异步投标】serial=" + callbackTrans.getTrans_serial() + "========发送通知，从redis中获取授权数据" + cached);
            if (cached == null) {
                logger.info("【批量异步投标】redis中不存在该订单信息");
                throw new BusinessException(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT, BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NOEXISTENT));
            }
            BaseRequest baseRequest = JSON.parseObject((String) cached, BaseRequest.class);
            String extData = baseRequest.getRemark();

            String platcust = "";
            String prodId = "";
            String realVol = "";
            String amt = "";
            BigDecimal trans_amt = new BigDecimal(0);
            if (StringUtils.isNotBlank(extData) && extData.indexOf("|") >= 0) {
                platcust = StringUtils.split(extData, "|")[0];
                amt = StringUtils.split(extData, "|")[1];
                prodId = StringUtils.split(extData, "|")[2];
                realVol = StringUtils.split(extData, "|")[3];
            }
            if (StringUtils.isNotBlank(notifyURL)) {
                NotifyData notifyData = new NotifyData();
                notifyData.setNotifyUrl(notifyURL);
                notifyData.setMall_no(baseRequest.getMall_no());
                ProdBaseVestResponse prodBaseResponse = new ProdBaseVestResponse();
                prodBaseResponse.setRecode(callbackTrans.getReturn_code());
                prodBaseResponse.setRemsg(callbackTrans.getReturn_msg());
                prodBaseResponse.setOrder_status(callbackTrans.getStatus());
                prodBaseResponse.setOrder_info(callbackTrans.getRemark());
                prodBaseResponse.setTrans_amt(new BigDecimal(amt));
                prodBaseResponse.setOrder_no(callbackTrans.getOrder_no());
                prodBaseResponse.setPlatcust(platcust);
                prodBaseResponse.setProd_id(prodId);
                String notifyContent = JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature);
                notifyData.setNotifyContent(notifyContent);
                notifyService.addNotify(notifyData);
                logger.info(String.format("【批量异步投标回调-通知】|trans_serial:%s|status:%s|return_code:%s|return_msg:%s。notifyurl：%s，notifyInfo：%s,进入notify队列成功",
                        callbackTrans.getTrans_serial(), callbackTrans.getStatus(), callbackTrans.getReturn_code(), callbackTrans.getReturn_msg(), notifyURL, notifyContent));


            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void finishProdRefund(TransTransreq transTransreq) throws BusinessException {
        NotifyData notifyData=null;
        ProdBaseResponse prodBaseResponse=null;
        String lockKey="ProdRefundLock"+transTransreq.getOrder_no();
        Object notifyDataObj=CacheUtil.getCache().get(lockKey);
        if(notifyDataObj!=null){
            notifyData=JSON.parseObject(String.valueOf(notifyDataObj),NotifyData.class);
            prodBaseResponse=JSON.parseObject(notifyData.getNotifyContent(),ProdBaseResponse.class);
            BatchCustRepay batchCustRepay=JSON.parseObject(prodBaseResponse.getOrder_info(),BatchCustRepay.class);
            prodBaseResponse.setOrder_status(FlowStatus.SUCCESS.getCode());
            prodBaseResponse.setOrder_info(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            prodBaseResponse.setRecode(transTransreq.getReturn_code());
            prodBaseResponse.setRemsg(transTransreq.getReturn_msg());
            if(FlowStatus.SUCCESS.getCode().equals(transTransreq.getStatus())){
                //转账成功
                try{
                    logger.info("【标的还款】========还款完成，给用户“"+batchCustRepay.getCust_no()+"”发送短信");
                    EaccUserinfo eaccUserinfo=getEaccUserInfo(batchCustRepay.getMall_no(),batchCustRepay.getCust_no());
                    if(eaccUserinfo!=null){
                        MsgModel msgModel=new MsgModel();
                        String mall_name=sysParameterService.querySysParameter(batchCustRepay.getMall_no(),
                                MsgContent.MALL_NAME_KEY.getMsg());

                        BigDecimal allAmt=sendMsgService.getAccountAllAmount(batchCustRepay.getCust_no());
                        msgModel.setOrder_no(batchCustRepay.getOrder_no());
                        msgModel.setPlat_no(batchCustRepay.getPlat_no());
                        msgModel.setTrans_code(transTransreq.getTrans_code());
                        msgModel.setMall_no(eaccUserinfo.getMall_no());
                        msgModel.setPlatcust(eaccUserinfo.getMallcust());
                        BigDecimal trans_amt=BigDecimal.ZERO;
                        logger.info("【标的还款】=========本金:"+batchCustRepay.getReal_repay_amount());
                        if(batchCustRepay.getReal_repay_amount()!=null){
                            trans_amt=trans_amt.add(batchCustRepay.getReal_repay_amount());
                        }
                        logger.info("【标的还款】=========利息:"+batchCustRepay.getReal_repay_val());
                        if(batchCustRepay.getReal_repay_val()!=null){
                            trans_amt=trans_amt.add(batchCustRepay.getReal_repay_val());
                        }
                        logger.info("【标的还款】=========加息金:"+batchCustRepay.getRates_amt());
                        if (batchCustRepay.getRates_amt()!=null){
                            trans_amt=trans_amt.add(batchCustRepay.getRates_amt());
                        }
                        msgModel.setMsgContent(String.format(MsgContent.INVEST_REFUND_NOTIFY.getMsg(),
                                mall_name, NumberUtils.formatNumber(trans_amt),NumberUtils.formatNumber(allAmt)));
                        logger.info("【标的还款】=========实际发送金额:"+trans_amt);
                        //=========ccb参数===========
                        msgModel.setTrans_serial(transTransreq.getTrans_serial());
                        msgModel.setMsg_type(MsgType.INVEST_REFUND_NOTIFY.getType());
                        msgModel.setAmount(trans_amt);
                        //===========================
                        sendMsgService.addMsgToQueue(msgModel);
                        logger.info("【标的还款】==========短信已加入发送队列");
                    }
                }catch (Exception e){
                    logger.info("【短信发送异常】========"+e);
                }
            }else{
                //转账失败
                productRefundDBOptionService.backProdRefund(transTransreq);
                prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            }
            notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse));
            notifyService.addNotify(notifyData);
        }

        //更新订单状态
        transReqService.insert(transTransreq);
    }

    private EaccUserinfo getEaccUserInfo(String mall_no,String mallcust){
        EaccUserinfoExample eaccUserinfoExample=new EaccUserinfoExample();
        EaccUserinfoExample.Criteria criteria=eaccUserinfoExample.createCriteria();
        criteria.andMall_noEqualTo(mall_no);
        criteria.andMallcustEqualTo(mallcust);
        List<EaccUserinfo> eaccUserinfoList=eaccUserinfoMapper.selectByExample(eaccUserinfoExample);
        if(eaccUserinfoList.size()>0){
            return eaccUserinfoList.get(0);
        }
        return null;
    }

    private BigDecimal getRefundAmt(BatchCustRepay batchCustRepay){
        BigDecimal trans_amt=BigDecimal.ZERO;
        logger.info("【标的还款】=========本金:"+batchCustRepay.getReal_repay_amount());
        if(batchCustRepay.getReal_repay_amount()!=null){
            trans_amt=trans_amt.add(batchCustRepay.getReal_repay_amount());
        }
        logger.info("【标的还款】=========利息:"+batchCustRepay.getReal_repay_val());
        if(batchCustRepay.getReal_repay_val()!=null){
            trans_amt=trans_amt.add(batchCustRepay.getReal_repay_val());
        }
        logger.info("【标的还款】=========加息金:"+batchCustRepay.getRates_amt());
        if (batchCustRepay.getRates_amt()!=null){
            trans_amt=trans_amt.add(batchCustRepay.getRates_amt());
        }
        return trans_amt;
    }

    /**
     * 校验还款数据
     * @param batchCustRepay
     * @return
     * @throws BusinessException
     */
    private boolean checkRefundData(BatchCustRepay batchCustRepay,ProdProductinfo prodProductinfo)throws BusinessException{
        ProdShareInall queryProdShareInall =null;
        logger.info("判断活期还是定期");
        if(!ProdType.CURRENT.getCode().equals(prodProductinfo.getProd_type())){
            logger.info("定期");
            //定期产品
            queryProdShareInall = prodSearchService.queryProdShareInall(prodProductinfo.getPlat_no(), prodProductinfo.getProd_id(), batchCustRepay.getCust_no());
            if (null == queryProdShareInall) {
                logger.info("-------------份额表里没有匹配的prod_id:" + prodProductinfo.getProd_id() + "信息，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+batchCustRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投标信息不存在");
            }
            if(queryProdShareInall.getVol().compareTo(BigDecimal.ZERO)<=0){
                //剩余需还份额小于等于0，无需还款
                logger.info("-------------用户" + batchCustRepay.getCust_no() + "已还清，无需还款，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+batchCustRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的投资已还清，无需再次还款");
            }else if(queryProdShareInall.getVol().compareTo(batchCustRepay.getReal_repay_amount())<0){
                logger.info("-------------用户" + batchCustRepay.getCust_no() + "剩余需还款份额不足，请确认---------------");
                throw new BusinessException(BusinessMsg.PARAMETER_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR)+"：platcust:"+batchCustRepay.getCust_no()+"，prod_id:"+prodProductinfo.getProd_id()+"的剩余需还款份额不足");
            }
        }
        return true;
    }
}
