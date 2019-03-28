package com.sunyard.sunfintech.user.service;

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
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.dao.entity.*;
import com.sunyard.sunfintech.dao.mapper.ProdBorrowerRealrepayMapper;
import com.sunyard.sunfintech.dao.mapper.ProdCompensationListMapper;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.INotifyService;
import com.sunyard.sunfintech.pub.provider.ITransReqService;
import com.sunyard.sunfintech.pub.provider.ITransferService;
import com.sunyard.sunfintech.user.model.bo.BatchRepayAsynMQEntity;
import com.sunyard.sunfintech.user.model.bo.BatchRepayRequestDetail;
import com.sunyard.sunfintech.user.model.bo.BorrowData;
import com.sunyard.sunfintech.user.model.bo.ProdBaseVestResponse;
import com.sunyard.sunfintech.user.modelold.bo.*;
import com.sunyard.sunfintech.user.provider.IProdRepayMQOptionService;
import com.sunyard.sunfintech.user.provider.IProdSearchService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by terry on 2018/2/2.
 */
@org.springframework.stereotype.Service
public class ProdRepayMQOptionService extends BaseServiceSimple implements IProdRepayMQOptionService {

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ITransReqService transReqService;

    @Autowired
    private IProdSearchService prodSearchService;

    @Autowired
    private IAccountQueryService accountQueryService;

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private ProdBorrowerRealrepayMapper prodBorrowerRealrepayMapper;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IAccountTransferService accountTransferService;

    @Autowired
    private ProdCompensationListMapper prodCompensationListMapper;

    @Override
    public void addBatchRepayAsyn(BatchRepayAsynMQEntity batchRepayAsynMQEntity) throws BusinessException {
        if(batchRepayAsynMQEntity!=null){
            MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "BatchBorrowQueue", batchRepayAsynMQEntity);
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
    public void doBatchRepayAsyn(BatchRepayAsynMQEntity batchRepayAsynMQEntity) throws BusinessException {

        BaseRequest baseRequest = batchRepayAsynMQEntity.getBaseRequest();
        BatchRepayRequestDetail batchRepayRequestDetail = batchRepayAsynMQEntity.getBatchRepayRequestDetail();
        String notify = batchRepayAsynMQEntity.getNotify();

        logger.info("【借款人还款申请】-->消费成功-->order_no:"+batchRepayRequestDetail.getDetail_no());

        TransTransreq transTransReq = null;

        ProdBaseVestResponse prodBaseResponse=new ProdBaseVestResponse();
        NotifyData notifyData=new NotifyData();
        notifyData.setNotifyUrl(notify);
        notifyData.setMall_no(baseRequest.getMall_no());
        prodBaseResponse.setOrder_no(batchRepayRequestDetail.getDetail_no());
        prodBaseResponse.setPlatcust(batchRepayRequestDetail.getPlatcust());
        prodBaseResponse.setProd_id(batchRepayRequestDetail.getProd_id());
        prodBaseResponse.setRecode(BusinessMsg.SUCCESS);
        prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        prodBaseResponse.setTrans_amt(batchRepayRequestDetail.getTrans_amt());
        prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
        prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
        prodBaseResponse.setTrans_date(DateUtils.getyyyyMMddDate());

        try{

            transTransReq = transReqService.queryTransTransReqByOrderno(batchRepayRequestDetail.getDetail_no());

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

            //检查标的融资信息
            List<EaccFinancinfo> listEaccFinancinfo = prodSearchService.queryEaccFinancinfo(baseRequest.getMer_no(),batchRepayRequestDetail.getProd_id(),batchRepayRequestDetail.getPlatcust());
            if(listEaccFinancinfo == null || listEaccFinancinfo.size() == 0){
                logger.info("【借款人还款申请】-->请求账户与融资人提取账户信息不一致-->platcust:"+batchRepayRequestDetail.getPlatcust());
                throw new BusinessException(BusinessMsg.ACCOUNT_INFO_DIFF,BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF));
            }
            logger.info("【借款人还款申请】-->请求账户与融资人提取账户信息一致-->order_no:"+batchRepayRequestDetail.getDetail_no());


            //插入进借款人信息表
            ProdBorrowerRealrepay prodBorrowerRealrepay = new ProdBorrowerRealrepay();
            prodBorrowerRealrepay.setBorrower_id(batchRepayRequestDetail.getDetail_no());//融资编号
            prodBorrowerRealrepay.setPlat_no(productInfo.getPlat_no());//平台编号
            prodBorrowerRealrepay.setRepay_num(batchRepayRequestDetail.getRepay_num());//还款期别
            prodBorrowerRealrepay.setProd_id(batchRepayRequestDetail.getProd_id());//产品编号
            prodBorrowerRealrepay.setRepay_date(DateUtils.formatDateToStr(batchRepayRequestDetail.getRepay_date()));//计划还款日期
            prodBorrowerRealrepay.setRepay_amt(batchRepayRequestDetail.getRepay_amt());//计划还款金额
            prodBorrowerRealrepay.setReal_repay_amt(batchRepayRequestDetail.getTrans_amt());
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

            logger.info("【借款人还款申请】-->丢入mq-->order_no:"+batchRepayRequestDetail.getDetail_no());
            notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
            String key=Constants.APPLY_AUTH_REPAY+":"+batchRepayRequestDetail.getDetail_no();
            CacheUtil.getCache().setnx(key,JSON.toJSONString(notifyData));

            transferService.transfer(baseRequest,eaccAccountamtlist);

            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.INPROCESS.getCode());
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

            if(transTransReq!=null){
                transTransReq.setReturn_code(baseResponse.getRecode());
                transTransReq.setReturn_msg(baseResponse.getRemsg());
                transTransReq.setStatus(FlowStatus.FAIL.getCode());
                transReqService.insert(transTransReq);

                prodBaseResponse.setRecode(baseResponse.getRecode());
                prodBaseResponse.setRemsg(baseResponse.getRemsg());
                prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
                notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
                notifyService.addNotify(notifyData);
            }

        }

        logger.info("【借款人还款申请】-->添加mq异步通知成功-->order_no:"+batchRepayRequestDetail.getDetail_no());
    }

    @Override
    public void afterBatchRepayAsynNotify(TransTransreq transTransreq) throws BusinessException {
        logger.info("【借款人还款申请】-->消费account转账后的消息-->order_no:"+transTransreq.getOrder_no());
        String key = Constants.APPLY_AUTH_REPAY + ":" + transTransreq.getOrder_no();
        Object valObj = CacheUtil.getCache().get(key);
        if (valObj == null) {
            logger.error("key的值在redis中找不到"+key);
        }
        NotifyData  notifyData = JSON.parseObject(CacheUtil.getCache().get(key).toString(), NotifyData.class);
        ProdBaseVestResponse prodBaseResponse = JSON.parseObject(notifyData.getNotifyContent(), ProdBaseVestResponse.class);

        if (OrderStatus.SUCCESS.getCode().equals(transTransreq.getStatus())) {
            prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
            prodBaseResponse.setRecode(BusinessMsg.SUCCESS);
            prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
            notifyService.addNotify(notifyData);
            logger.info("【借款人还款申请】-->丢入异步通知成功-->order_no:"+transTransreq.getOrder_no());
        } else if (OrderStatus.FAIL.getCode().equals(transTransreq.getStatus())) {

            ProdBorrowerRealrepayExample prodBorrowerRealrepayExample = new ProdBorrowerRealrepayExample();
            ProdBorrowerRealrepayExample.Criteria criteria = prodBorrowerRealrepayExample.createCriteria();
            criteria.andBorrower_idEqualTo(transTransreq.getOrder_no());
            prodBorrowerRealrepayMapper.deleteByExample(prodBorrowerRealrepayExample);

            prodBaseResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            prodBaseResponse.setRecode(transTransreq.getReturn_code());
            prodBaseResponse.setRemsg(transTransreq.getReturn_msg());
            notifyData.setNotifyContent(JSON.toJSONString(prodBaseResponse, GlobalConfig.serializerFeature));
            notifyService.addNotify(notifyData);
            logger.info("【借款人还款申请】-->丢入异步通知成功-->order_no:"+transTransreq.getOrder_no());
        }


    }

    @Override
    public void addBorrow(BaseRequest baseRequest, BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn, String notifyURL) throws BusinessException {

        if(baseRequest!=null && batchRepayRequestDetailAsyn!=null){
            BorrowDataOld borrowData=new BorrowDataOld();
            borrowData.setProdProductinfo(new ProdProductinfo());
            borrowData.setBaseRequest(baseRequest);
            borrowData.setBatchRepayRequestDetailAsyn(batchRepayRequestDetailAsyn);
            borrowData.setNotifyURL(notifyURL);
            borrowData.setTime(DateUtils.todayStr());
            MQUtils.send(amqpTemplate, "ftdm.user.direct.exchange", "BatchBorrowOldQueue", borrowData);
        }
    }

    @Override
    public void doBorrow(BaseRequest baseRequest, BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn, String notifyURL) throws BusinessException {
        //发送异步通知
        logger.info("【借款人批量还款old】-->成功消费-->order_no:"+batchRepayRequestDetailAsyn.getDetail_no());

        ProdBaseVestResponseOld prodBaseResponse=new ProdBaseVestResponseOld();
        NotifyData notifyData=new NotifyData();
        try{
            notifyData.setNotifyUrl(notifyURL);
            notifyData.setMall_no(baseRequest.getMall_no());
            prodBaseResponse.setOrder_no(batchRepayRequestDetailAsyn.getDetail_no());
            prodBaseResponse.setPlatcust(batchRepayRequestDetailAsyn.getPlatcust());
            prodBaseResponse.setProd_id(batchRepayRequestDetailAsyn.getProd_id());
            BigDecimal subAmt = BigDecimal.ZERO;
            BigDecimal platAmt = BigDecimal.ZERO;
            BigDecimal feeAmt = BigDecimal.ZERO;
            List<SubdataObject> subdataObjects = batchRepayRequestDetailAsyn.getSubdataObjectList();
            if(subdataObjects!=null && subdataObjects.size()>0){
                for(SubdataObject subdataObject : subdataObjects){
                    validate(subdataObject);
                    subAmt = subAmt.add(subdataObject.getAmt());
                }
            }
            List<PlatSubData> platSubDataList = batchRepayRequestDetailAsyn.getPlatSubDataObject();
            if(platSubDataList!=null && platSubDataList.size()>0){
                for(PlatSubData platSubData : platSubDataList){
                    validate(platSubData);
                    platAmt = platAmt.add(platSubData.getAmt());
                }
            }
            if(batchRepayRequestDetailAsyn.getFee_amt()!=null){
                feeAmt = feeAmt.add(batchRepayRequestDetailAsyn.getFee_amt());
            }
            prodBaseResponse.setTrans_amt(batchRepayRequestDetailAsyn.getReal_repay_amt().add(feeAmt).add(subAmt).add(platAmt));

            borrowForNoSyn(baseRequest,batchRepayRequestDetailAsyn);
            prodBaseResponse.setRecode(BusinessMsg.SUCCESS);
            prodBaseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            prodBaseResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            prodBaseResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
        }catch (Exception e){
            logger.error("【借款人批量还款old】-->还款异常-->order_no:"+batchRepayRequestDetailAsyn.getDetail_no(),e);
            BaseResponse baseResponse=new BaseResponse();
            TransTransreq transTransReq = transReqService.queryTransTransReqByOrderno(batchRepayRequestDetailAsyn.getDetail_no());
            if(e instanceof BusinessException){
                baseResponse=((BusinessException)e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.FAIL);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
                logger.error("【借款人批量还款】数据库未知异常");
            }
            transTransReq.setReturn_code(baseResponse.getRecode());
            transTransReq.setReturn_msg(baseResponse.getRemsg());
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

    public boolean borrowForNoSyn(BaseRequest baseRequest, BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn) throws BusinessException {
        //TODO 记录业务流水
        TransTransreq transTransReq = transReqService.queryTransTransReqByOrderno(batchRepayRequestDetailAsyn.getDetail_no());

        try {
            ProdProductinfo productInfo = prodSearchService.queryProdInfo(baseRequest.getMer_no(),batchRepayRequestDetailAsyn.getProd_id());
            if(productInfo == null){
                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.INVALID_PRODUCT_ID);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_PRODUCT_ID)  + ",标不存在  标的id：" + batchRepayRequestDetailAsyn.getProd_id());
                throw new BusinessException(baseResponse);
            }
            logger.info("【借款人批量还款old】========单个还款开始，用户：" + batchRepayRequestDetailAsyn.getPlatcust() + "，标的：" + productInfo.getProd_id()+"，订单号："+batchRepayRequestDetailAsyn.getDetail_no());

            borrowOption(baseRequest,productInfo,batchRepayRequestDetailAsyn,transTransReq);
            //更新业务流水
            transTransReq.setReturn_code(BusinessMsg.SUCCESS);
            transTransReq.setReturn_msg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            transTransReq.setStatus(FlowStatus.SUCCESS.getCode());
            transReqService.insert(transTransReq);
        } catch (Exception e) {
            logger.error("【借款人批量还款old】-->异常-->order_no:"+batchRepayRequestDetailAsyn.getDetail_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    public boolean borrowOption(BaseRequest baseRequest, ProdProductinfo productInfo, BatchRepayRequestDetailAsyn batchRepayRequestDetailAsyn, TransTransreq transTransReq){
        //检查标的账户是否存在
        logger.info("【借款人批量还款old】order_no:"+batchRepayRequestDetailAsyn.getDetail_no());

        AccountSubjectInfo prodAccountSubjectInfo = accountQueryService.queryAccount(productInfo.getPlat_no(),productInfo.getProd_account(), Tsubject.CASH.getCode(), Ssubject.PROD.getCode());
        if(prodAccountSubjectInfo == null){

            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",标的账户不存在   账户：" + productInfo.getProd_account());
            throw new BusinessException(baseResponse);
        }

        //检查借款人账户是否存在
        AccountSubjectInfo custAccountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(), batchRepayRequestDetailAsyn.getPlatcust(), Tsubject.CASH.getCode(),Ssubject.FINANCING.getCode());
        if(custAccountSubjectInfo == null){

            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.ACCOUNT_INFO_DIFF);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ACCOUNT_INFO_DIFF) + ",借款人账户不存在  请检查融资账户："+batchRepayRequestDetailAsyn.getPlatcust());
            throw new BusinessException(baseResponse);
        }

        //查询手续费账户
        AccountSubjectInfo feeAccountSubjectInfo = accountQueryService.queryAccount(productInfo.getPlat_no(),null, Tsubject.CASH.getCode(),Ssubject.FEE.getCode());
        if(feeAccountSubjectInfo == null){

            BaseResponse baseResponse=new BaseResponse();
            baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.INVALID_ACCOUNT) + ",手续费账户不存在 平台号：" + productInfo.getPlat_no());
            throw new BusinessException(baseResponse);
        }

        List<EaccAccountamtlist> eaccAccountamtlists = new ArrayList<>();

        //转账   借款人还款给标的账户
        logger.info("【借款人批量还款old】order_no:"+batchRepayRequestDetailAsyn.getDetail_no());

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
        eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());

        //对手科目
        eaccAccountamtlist.setOppo_plat_no(prodAccountSubjectInfo.getPlat_no());
        eaccAccountamtlist.setOppo_platcust(prodAccountSubjectInfo.getPlatcust());
        eaccAccountamtlist.setOppo_subject(prodAccountSubjectInfo.getSubject());
        eaccAccountamtlist.setOppo_sub_subject(prodAccountSubjectInfo.getSub_subject());

        eaccAccountamtlist.setAmt(batchRepayRequestDetailAsyn.getReal_repay_amt());
        eaccAccountamtlist.setRemark("借款人批量还款，借款人还款给标的账户");

        eaccAccountamtlists.add(eaccAccountamtlist);

        //判断是否有手续费
        if(batchRepayRequestDetailAsyn.getFee_amt() != null && batchRepayRequestDetailAsyn.getFee_amt().compareTo(BigDecimal.ZERO) > 0){

            logger.info("【借款人批量还款old】-->借款人还款给平台自有 手续费 order_no:"+batchRepayRequestDetailAsyn.getDetail_no());
            EaccAccountamtlist eacc=new EaccAccountamtlist();
            BeanUtils.copyProperties(eaccAccountamtlist,eacc);
            //借款人还款给平台自有账户
            //对手科目
            eacc.setOppo_plat_no(feeAccountSubjectInfo.getPlat_no());
            eacc.setOppo_platcust(feeAccountSubjectInfo.getPlatcust());
            eacc.setOppo_subject(feeAccountSubjectInfo.getSubject());
            eacc.setOppo_sub_subject(feeAccountSubjectInfo.getSub_subject());

            eacc.setAmt(batchRepayRequestDetailAsyn.getFee_amt());
            eacc.setRemark("借款人批量还款，借款人还款给平台自有手续费");

            eaccAccountamtlists.add(eacc);
        }

        List<SubdataObject> subdataObjects = batchRepayRequestDetailAsyn.getSubdataObjectList();//分账成标

        BigDecimal subAmt = BigDecimal.ZERO;
        if(subdataObjects!=null && subdataObjects.size()>0){
            logger.info("【借款人批量还款old】-->order_no:"+batchRepayRequestDetailAsyn.getDetail_no());
            for (SubdataObject subdataObject : subdataObjects){
                subAmt = subAmt.add(subdataObject.getAmt());
                //判断分账用户是不是在委托/代偿名单中
                ProdCompensationListExample prodCompensationListExample = new ProdCompensationListExample();
                ProdCompensationListExample.Criteria prodComListcriteria = prodCompensationListExample.createCriteria();
                prodComListcriteria.andProd_idEqualTo(productInfo.getProd_id());
                prodComListcriteria.andPlat_noEqualTo(baseRequest.getMer_no());
                prodComListcriteria.andPlatcustEqualTo(subdataObject.getPlat_cust());
                prodComListcriteria.andEnabledEqualTo(Constants.ENABLED);
                List<ProdCompensationList> prodCompensationLists = prodCompensationListMapper.selectByExample(prodCompensationListExample);
                if(prodCompensationLists==null || prodCompensationLists.size()==0){
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.INVALID_ACCOUNT);
                    baseResponse.setRemsg("分账用户不在委托/代偿名单中");
                    throw new BusinessException(baseResponse);
                }
                EaccUserinfo subplatcust=accountQueryService.getEaccUserinfoByExist(baseRequest.getMall_no(),subdataObject.getPlat_cust());
//						AccountSubjectInfo subplatcust = accountQueryService.queryFINANCINGPlatAccount(prodEstablishOrAbort.getMall_no(), subdataObject.getPlat_cust(), AccountType.ElectronicAccount.getCode(),
//								Tsubject.CASH.getCode(), Ssubject.EACCOUNT.getCode());
                eaccAccountamtlist = new EaccAccountamtlist();
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
                eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());

                eaccAccountamtlist.setOppo_platcust(subdataObject.getPlat_cust());
                eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
                if (subplatcust!=null)
                {//存在电子账户
                    eaccAccountamtlist.setOppo_sub_subject(Ssubject.EACCOUNT.getCode());
                } else {
                    eaccAccountamtlist.setOppo_sub_subject(Ssubject.FINANCING.getCode());
                }
                eaccAccountamtlist.setAmt(subdataObject.getAmt());
                eaccAccountamtlists.add(eaccAccountamtlist);
            }
            logger.info("【借款人批量还款old】-->order_no:"+batchRepayRequestDetailAsyn.getDetail_no());
        }

        //平台分账
        BigDecimal platSubAmt = BigDecimal.ZERO;
        List<PlatSubData> platSubDataList =  batchRepayRequestDetailAsyn.getPlatSubDataObject();
        AccountSubjectInfo fxjAccountSubjectInfo = accountQueryService.queryAccount(baseRequest.getMer_no(),baseRequest.getMer_no(), Tsubject.CASH.getCode(),Ssubject.DEPOSIT.getCode());
        if(platSubDataList!=null && platSubDataList.size()>0){
            platSubAmt = platSubDataList.get(0).getAmt();
            if(platSubAmt.compareTo(BigDecimal.ZERO)>0){
                eaccAccountamtlist = new EaccAccountamtlist();
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
                eaccAccountamtlist.setAmt_type(AmtType.EXPENSIVE.getCode());

                eaccAccountamtlist.setOppo_platcust(fxjAccountSubjectInfo.getPlatcust());
                eaccAccountamtlist.setOppo_subject(Tsubject.CASH.getCode());
                eaccAccountamtlist.setOppo_sub_subject(fxjAccountSubjectInfo.getSub_subject());
                eaccAccountamtlist.setAmt(platSubAmt);
                eaccAccountamtlists.add(eaccAccountamtlist);
            }
        }

        if(batchRepayRequestDetailAsyn.getFee_amt() != null && batchRepayRequestDetailAsyn.getFee_amt().compareTo(BigDecimal.ZERO) > 0){
            //检查手续费和实际还款金额是否等于交易金额
            if(batchRepayRequestDetailAsyn.getTrans_amt().compareTo(subAmt.add(platSubAmt).add(batchRepayRequestDetailAsyn.getFee_amt()).add(batchRepayRequestDetailAsyn.getReal_repay_amt())) != 0){

                BaseResponse baseResponse=new BaseResponse();
                baseResponse.setRecode(BusinessMsg.MONEY_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.MONEY_ERROR) + "---------------交易金额  不等于 （实际还款金额+手续费金额+三方分账金额+平台分账金额）");
                throw new BusinessException(baseResponse);
            }
        }

        //插入进借款人信息表
        ProdBorrowerRealrepay prodBorrowerRealrepay = new ProdBorrowerRealrepay();
        prodBorrowerRealrepay.setBorrower_id(batchRepayRequestDetailAsyn.getDetail_no());//融资编号
        prodBorrowerRealrepay.setPlat_no(productInfo.getPlat_no());//平台编号
        prodBorrowerRealrepay.setRepay_num(batchRepayRequestDetailAsyn.getRepay_num());//还款期别
        prodBorrowerRealrepay.setProd_id(batchRepayRequestDetailAsyn.getProd_id());//产品编号
        prodBorrowerRealrepay.setRepay_date(DateUtils.formatDateToStr(batchRepayRequestDetailAsyn.getRepay_date()));//计划还款日期
        prodBorrowerRealrepay.setRepay_amt(batchRepayRequestDetailAsyn.getRepay_amt());//计划还款金额
        prodBorrowerRealrepay.setRepay_fee(batchRepayRequestDetailAsyn.getFee_amt());//计划还款手续费
        prodBorrowerRealrepay.setReal_repay_amt(batchRepayRequestDetailAsyn.getReal_repay_amt());//实际还款金额
        prodBorrowerRealrepay.setReal_repay_fee(batchRepayRequestDetailAsyn.getFee_amt());//实际还款手续费
        prodBorrowerRealrepay.setPlatcust(batchRepayRequestDetailAsyn.getPlatcust());//借款人编号
        prodBorrowerRealrepay.setRemark(batchRepayRequestDetailAsyn.getRemark());//备注
        prodBorrowerRealrepay.setCreate_time(DateUtils.getNow());//创建时间
        prodBorrowerRealrepay.setUpdate_time(DateUtils.getNow());//修改时间
        prodBorrowerRealrepay.setTrans_date(baseRequest.getPartner_trans_date());//交易日期
        prodBorrowerRealrepay.setTrans_time(baseRequest.getPartner_trans_time());//交易时间
        prodBorrowerRealrepay.setEnabled(Constants.ENABLED);//删除标记
        prodBorrowerRealrepay.setStatus(RepaymentStatus.SUCCPAY.getCode());
        prodBorrowerRealrepayMapper.insert(prodBorrowerRealrepay);

        try {
            logger.info("【借款人批量还款old】转账开始时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
            accountTransferService.batchTransfer(eaccAccountamtlists);
            logger.info("【借款人批量还款old】转账结束时间："+new Date()+"，订单号："+baseRequest.getOrder_no());
        }catch (Exception e){
            logger.error("【借款人批量还款old】-->转账异常-->order_no:" + baseRequest.getOrder_no(),e);
            BaseResponse baseResponse = new BaseResponse();
            if(e instanceof BusinessException){
                baseResponse = ((BusinessException)e).getBaseResponse();
            }else{
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return true;
    }

}
