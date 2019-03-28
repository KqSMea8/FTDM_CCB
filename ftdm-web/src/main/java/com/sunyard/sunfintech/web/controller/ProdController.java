package com.sunyard.sunfintech.web.controller;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.annotation.CheckPassword;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseMessage;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.prod.model.bo.ProdAbortInvestmentRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdAbortInvestmentResponse;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortResponse;
import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.web.business.ProdBusiness;
import com.sunyard.sunfintech.web.model.vo.product.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标的对外服务接口
 * @author Ycv
 */
@RestController
@RequestMapping("/product")
public class ProdController extends BaseController {

    @Resource(name = "prodBusiness")
    private ProdBusiness prodBusiness;

    /**
     * 标的成废
     * @author wubin
     * @para5m httpServletRequeste
     * @param prodEstablishOrAbortRequest
     * @return
     */
    @RequestMapping("/establish_or_abort")
    @Sign
    @Log(method = "establishOrAbort")
    public ProdEstablishOrAbortResponse establishOrAbort(HttpServletRequest httpServletRequest, ProdEstablishOrAbortRequest prodEstablishOrAbortRequest) {
        logger.info("【标的成废】=======请求参数"+prodEstablishOrAbortRequest);
        validate(prodEstablishOrAbortRequest);
        //标的成废业务
        ProdEstablishOrAbortResponse prodEstablishOrAbortResponse = prodBusiness.establishOrAbort(prodEstablishOrAbortRequest);
        prodEstablishOrAbortResponse.setOrder_no(prodEstablishOrAbortRequest.getOrder_no());
        prodEstablishOrAbortResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return prodEstablishOrAbortResponse;
    }

    /**
     * 标的成废
     * @author wubin
     * @para5m httpServletRequeste
     * @param prodEstablishOrAbortRequest
     * @return
     */
    @RequestMapping("/establish_or_abort_old")
    @Sign
    @Log(method = "establishOrAbortOld")
    public ProdEstablishOrAbortResponse establishOrAbortOld(HttpServletRequest httpServletRequest, ProdEstablishOrAbortRequestOld prodEstablishOrAbortRequest) {
        logger.info("【标的成废】=======请求参数"+prodEstablishOrAbortRequest);
        validate(prodEstablishOrAbortRequest);
        //标的成废业务
        ProdEstablishOrAbortResponse prodEstablishOrAbortResponse = prodBusiness.establishOrAbortOld(prodEstablishOrAbortRequest);
        prodEstablishOrAbortResponse.setOrder_no(prodEstablishOrAbortRequest.getOrder_no());
        prodEstablishOrAbortResponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return prodEstablishOrAbortResponse;
    }

    /**
     * 借款人募集申请
     * @author pengziyuan
     * @param httpServletRequest
     * @param prodPublishRequest
     * @return
     */
    @RequestMapping("/publish")
    @Sign
    @Log(method = "publish")
    public BaseResponse publish(HttpServletRequest httpServletRequest, ProdPublishRequest prodPublishRequest){
        logger.info("【借款人募集申请】-->开始-->order_no:"+prodPublishRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(prodPublishRequest);
        validate(prodPublishRequest.getEaccFinancinfo());
        ProdPublishResponse publicresponse = prodBusiness.prodPublish(prodPublishRequest);
        long end = System.currentTimeMillis();
        logger.info("【借款人募集申请】-->结束-->order_no:"+prodPublishRequest.getOrder_no()+"-->耗时:"+(end-start));
        return publicresponse;
    }
//    @RequestMapping("/publish_old")
//    @Sign
//    public BaseResponse publish_old(HttpServletRequest httpServletRequest, ProdPublishRequestOld prodPublishRequest){
//        logger.info("【借款人募集申请】-->开始-->order_no:"+prodPublishRequest.getOrder_no());
//        long start = System.currentTimeMillis();
//        validate(prodPublishRequest);
//        validate(prodPublishRequest.getEaccFinancinfo());
//
//        ProdPublishResponse publicresponse = prodBusiness.prodPublish(prodPublishRequest.getProdPublishRequest());
//        long end = System.currentTimeMillis();
//        logger.info("【借款人募集申请】-->结束-->order_no:"+prodPublishRequest.getOrder_no()+"-->耗时:"+(end-start));
//        return publicresponse;
//    }


    /**
     * 标的发布-老接口
     *
     * @param httpServletRequest
     * @param prodpublishrequest
     * @return
     */
    @RequestMapping("/publish_old")
    @Sign
    @Log(method = "publish_old")
    public BaseResponse publish_old(HttpServletRequest httpServletRequest, ProdPublishRequestOldVer prodpublishrequest){
        logger.info("【标的发布】=======请求参数"+prodpublishrequest);
        ProdPublishResponse publicresponse = new ProdPublishResponse();

        validate(prodpublishrequest);
        try {
            logger.info("ProdpublicRequest: " + prodpublishrequest);

            //TODO prodBusiness.prodPublish(prodpublishrequest);l
            publicresponse.setRecode(BusinessMsg.SUCCESS);
            publicresponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            ProdPublishResponseData data=prodBusiness.prodPublishOld(prodpublishrequest);
            publicresponse.setData_obj(data);

        }catch (BusinessException e){
            publicresponse.setRecode(e.getBaseResponse().getRecode());
            publicresponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        publicresponse.setOrder_no(prodpublishrequest.getOrder_no());
        return publicresponse;


    }

    /**
     * 投标
     * @param httpServletRequest
     * @param prodInvestResquest
     * @return
     */
    @RequestMapping("/invest")
    @Sign(checkPassword= CheckPassword.YES)
    @Log(method = "invest")
    public BaseResponse invest(HttpServletRequest httpServletRequest, ProdInvestResquest prodInvestResquest) {
        logger.info("【出借】=======请求参数"+ prodInvestResquest);
        ProdInvestResponse prodInvestResponse = new ProdInvestResponse();
        validate(prodInvestResquest);
        try {
            prodInvestResponse =prodBusiness.invest(prodInvestResquest);
        } catch (BusinessException e) {
            prodInvestResponse.setRecode(e.getBaseResponse().getRecode());
            prodInvestResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodInvestResponse.setOrder_no(prodInvestResquest.getOrder_no());
        return prodInvestResponse;
    }

    /**
     * 批量投标-老接口
     * @param httpServletRequest
     * @param prodBatchInvestResquest
     * @return
     */
    @RequestMapping("/invest_asyn_old")
    @Sign
    @Log(method = "invest_asyn_old",batchDataName = "data")
    public BaseResponse invest_asyn_old(HttpServletRequest httpServletRequest, ProdInvestNoSynResquest prodBatchInvestResquest) {
        logger.info("【批量投标】=======请求参数"+ prodBatchInvestResquest);
        ProdBatchInvestResponse prodBatchInvestResponse = new ProdBatchInvestResponse();
        validate(prodBatchInvestResquest);
        try {
            prodBatchInvestResponse =prodBusiness.batchInvest(prodBatchInvestResquest);
        } catch (BusinessException e) {
            prodBatchInvestResponse.setRecode(e.getBaseResponse().getRecode());
            prodBatchInvestResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodBatchInvestResponse.setOrder_no(prodBatchInvestResquest.getOrder_no());
        return prodBatchInvestResponse;
    }



    /**
     * 标的转让
     * @param httpServletRequest
     * @param prodTransferDebtRequest
     * @return
     */
    @RequestMapping("/transfer")
    @Sign(checkPassword = CheckPassword.YES  , platcust = "deal_platcust")
    @Log(method = "assignment")
    public ProdTransferDebtResponse assignment(HttpServletRequest httpServletRequest, ProdTransferDebtRequest prodTransferDebtRequest) {
        ProdTransferDebtResponse prodTransferDebtResponse = new ProdTransferDebtResponse();
        //数据校验
        validate(prodTransferDebtRequest);
        try {
            logger.info("ProdTransferDebtRequestBo:" + prodTransferDebtRequest.toString());
            //校验时间格式
            Date trans_date = prodTransferDebtRequest.getTrans_date();
            String trans_dateStr = DateUtils.format(trans_date, DateUtils.DEF_FORMAT);
            if (!"".equals(trans_dateStr)) {
                //转让具体流程
                ProdTransferDebtResponseData responseData=prodBusiness.assignment(prodTransferDebtRequest);

                prodTransferDebtResponse.setData_obj(responseData);
                prodTransferDebtResponse.setRecode(BusinessMsg.SUCCESS);
                prodTransferDebtResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else{
                //格式错误
                prodTransferDebtResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
                prodTransferDebtResponse.setRemsg("trans_date: " + BusinessMsg.PARAMETER_ERROR + ",格式要求[YYYY-MM-DD HH:mm:ss]。");
            }
        } catch (BusinessException e) {
            prodTransferDebtResponse.setRecode(e.getBaseResponse().getRecode());
            prodTransferDebtResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodTransferDebtResponse.setOrder_no(prodTransferDebtRequest.getOrder_no());
        return prodTransferDebtResponse;
    }

    /**
     * 标的转让（批量）
     * @param httpServletRequest
     * @param prodBatchTransferRequest
     * @return
     */
    @RequestMapping("/batch_transfer_asyn")
    @Sign
    @Log(method = "batch_transfer",batchDataName = "data")
    public ProdBatchTransferResponse batch_transfer(HttpServletRequest httpServletRequest, ProdBatchTransferRequestBo prodBatchTransferRequest) {
        ProdBatchTransferResponse prodBatchTransferResponse = new ProdBatchTransferResponse();
        validate(prodBatchTransferRequest);
        try {
            //参数验证通过后，开始执行转让业务
//            prodBatchTransferResponse=prodBusiness.batchAssignment(prodBatchTransferRequest);
            //新老接口变更，老接口参数转新接口参数
            ProdBatchTransferAsynRequestBo newTransferParams=new ProdBatchTransferAsynRequestBo();
            List<ProdBatchTransferRequestDataBo> newDataArray=new ArrayList<>();
            newTransferParams.setNotify_url(prodBatchTransferRequest.getNotify_url());
            newTransferParams.setVersion(prodBatchTransferRequest.getVersion());
            newTransferParams.setMall_no(prodBatchTransferRequest.getMall_no());
            newTransferParams.setMall_name(prodBatchTransferRequest.getMall_name());
            newTransferParams.setMer_no(prodBatchTransferRequest.getMer_no());
            newTransferParams.setMall_name(prodBatchTransferRequest.getMer_name());
            newTransferParams.setOrder_no(prodBatchTransferRequest.getOrder_no());
            newTransferParams.setPartner_trans_date(prodBatchTransferRequest.getPartner_trans_date());
            newTransferParams.setPartner_trans_time(prodBatchTransferRequest.getPartner_trans_time());
            newTransferParams.setRemark(prodBatchTransferRequest.getRemark());
            newTransferParams.setSign(prodBatchTransferRequest.getSign());
            List<ProdBatchTransferRequestDataBoTb> oldDataArray=prodBatchTransferRequest.getDataArray();
            String prod_id=prodBatchTransferRequest.getProd_id();
            String platcust=prodBatchTransferRequest.getPlatcust();
            for(ProdBatchTransferRequestDataBoTb oldData:oldDataArray){
                ProdBatchTransferRequestDataBo newData=new ProdBatchTransferRequestDataBo();
                newData.setPlatcust(platcust);
                newData.setDetail_no(oldData.getDetail_no());
                newData.setProd_id(prod_id);
                newData.setTrans_share(oldData.getTrans_share());
                newData.setTrans_amt(oldData.getTrans_amt());
                newData.setDeal_amount(oldData.getDeal_amount());
                newData.setCoupon_amt(oldData.getCoupon_amt());
                newData.setDeal_platcust(oldData.getDeal_platcust());
                newData.setCommissionObj(oldData.getCommissionObj());
                newData.setCommission_extObj(oldData.getCommission_extObj());
                newData.setPublish_date(oldData.getPublish_date());
                newData.setTrans_date(oldData.getTrans_date());
                newData.setTransfer_income(oldData.getTransfer_income());
                newData.setIncome_acct(oldData.getIncome_acct());
                newData.setRelated_prod_ids(oldData.getRelated_prod_ids());
                newData.setSubject_priority(oldData.getSubject_priority());
                newData.setRemark(oldData.getRemark());
                newData.setInterface_version(Constants.OLD_INTERFACE);
                newDataArray.add(newData);
            }
            newTransferParams.setDataArray(newDataArray);
            ProdInvestNoSynResponse response=prodBusiness.batchAssignmentAsyn(newTransferParams);
            prodBatchTransferResponse.setRecode(response.getRecode());
            prodBatchTransferResponse.setRemsg(response.getRemsg());
        } catch (BusinessException e) {
            prodBatchTransferResponse.setRecode(e.getBaseResponse().getRecode());
            prodBatchTransferResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodBatchTransferResponse.setOrder_no(prodBatchTransferRequest.getOrder_no());
        return prodBatchTransferResponse;
    }

    /**
     * 借款人还款计划更新
     * @param httpServletRequest
     * @param prodUpdateRepaymentPlanRequest
     * @return
     */
    @RequestMapping("/update_repayment_plan")
    @Sign
    @Log(method = "update_repayment_plan")
    public ProdUpdateRepaymentPlanResponse update_repayment_plan(HttpServletRequest httpServletRequest, ProdUpdateRepaymentPlanRequest prodUpdateRepaymentPlanRequest){
        logger.info("【借款人还款计划更新】-->开始-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no());
        long start = System.currentTimeMillis();
        validate(prodUpdateRepaymentPlanRequest);
        ProdUpdateRepaymentPlanResponse prodUpdateRepaymentPlanResponse = prodBusiness.updateRefundPlan(prodUpdateRepaymentPlanRequest);
        long end = System.currentTimeMillis();
        logger.info("【借款人还款计划更新】-->结束-->order_no:"+prodUpdateRepaymentPlanRequest.getOrder_no()+"耗时:"+(end-start));
        return prodUpdateRepaymentPlanResponse;
    }

    /**
     * 标的出账信息修改
     * @param httpServletRequest
     * @param prodUpdatePayoutAccountRequest
     * @return
     */
    @RequestMapping("/update_payout_account")
    @Sign
    @Log(method = "update_payout_account")
    public BaseResponse update_payout_account(HttpServletRequest httpServletRequest, ProdUpdatePayoutAccountRequest prodUpdatePayoutAccountRequest){
        BaseResponse prodUpdatePayoutAccountResponse=new BaseResponse();
        try {
            validate(prodUpdatePayoutAccountRequest);
            //请求service
            BaseMessage update_payout_account = prodBusiness.update_payout_account(prodUpdatePayoutAccountRequest);
            prodUpdatePayoutAccountResponse.setRecode(update_payout_account.getRecode());
            prodUpdatePayoutAccountResponse.setRemsg(update_payout_account.getRemsg());
        }catch (BusinessException e){
            prodUpdatePayoutAccountResponse.setRecode(e.getBaseResponse().getRecode());
            prodUpdatePayoutAccountResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodUpdatePayoutAccountResponse.setOrder_no(prodUpdatePayoutAccountRequest.getOrder_no());
        return prodUpdatePayoutAccountResponse;
    }

    /**
     * 投资撤销
     * @param httpServletRequest
     * @param prodAbortInvestmentRequest
     * @return
     */
    @RequestMapping("/abort_investment")
    @Sign
    @Log(method = "abort_investment")
    public BaseResponse abort_investment(HttpServletRequest httpServletRequest, ProdAbortInvestmentRequest prodAbortInvestmentRequest){
        validate(prodAbortInvestmentRequest);
        ProdAbortInvestmentResponse prodAbortInvestmentResponse = prodBusiness.abortInvestment(prodAbortInvestmentRequest);
        prodAbortInvestmentResponse.setOrder_no(prodAbortInvestmentRequest.getOrder_no());
        return prodAbortInvestmentResponse;
    }

    /**
     * 标的还款（一借多贷）
     * @param httpServletRequest
     * @param prodRepayRequest
     * @return
     */
    @RequestMapping("/batch_repay")
    @Sign
    @Log(method = "batchRepay",batchDataName = "funddata")
    public BaseResponse batchRepay(HttpServletRequest httpServletRequest, ProdBatchRepayOldRequest prodRepayRequest) {
        logger.info("【标的还款】========请求参数："+prodRepayRequest.toString());
        ProdRepayResponse prodRepayResponse = new ProdRepayResponse();
        validate(prodRepayRequest);
        try {
            prodRepayResponse = prodBusiness.batchRefund(prodRepayRequest);

        } catch (BusinessException e) {
            prodRepayResponse.setRecode(e.getBaseResponse().getRecode());
            prodRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
        return prodRepayResponse;
    }

    /**
     * 标的还款(异步一借一代)
     * @param httpServletRequest
     * @param prodRepayRequest
     * @return
     */
    @RequestMapping("/batch_repay_asyn_old")
    @Sign
    @Log(method = "batchRepayAsynOld",batchDataName = "funddata")
    public BaseResponse batchRepayAsynOld(HttpServletRequest httpServletRequest, ProdBatchRepayAsynRequest prodRepayRequest) {
        logger.info("【标的还款】========请求参数："+prodRepayRequest.toString());
        ProdRepayResponse prodRepayResponse = new ProdRepayResponse();
        validate(prodRepayRequest);
        try {
            List<BatchCustRepay> custRepayList=prodRepayRequest.getCustRepayList();
            ProdBatchRepayRequest prodBatchRepayRequest=new ProdBatchRepayRequest();
            prodBatchRepayRequest.setVersion(prodRepayRequest.getVersion());
            prodBatchRepayRequest.setMall_no(prodRepayRequest.getMall_no());
            prodBatchRepayRequest.setMall_name(prodRepayRequest.getMall_name());
            prodBatchRepayRequest.setMer_no(prodRepayRequest.getMer_no());
            prodBatchRepayRequest.setMall_name(prodRepayRequest.getMer_name());
            prodBatchRepayRequest.setOrder_no(prodRepayRequest.getOrder_no());
            prodBatchRepayRequest.setPartner_trans_date(prodRepayRequest.getPartner_trans_date());
            prodBatchRepayRequest.setPartner_trans_time(prodRepayRequest.getPartner_trans_time());
            prodBatchRepayRequest.setRemark(prodRepayRequest.getRemark());
            prodBatchRepayRequest.setSign(prodRepayRequest.getSign());
            prodBatchRepayRequest.setTrans_amt(prodRepayRequest.getTrans_amt());
            prodBatchRepayRequest.setNotify_url(prodRepayRequest.getNotify_url());
            for(BatchCustRepay custRepay:custRepayList){
                custRepay.setProd_id(prodRepayRequest.getProd_id());
                custRepay.setIs_payoff(prodRepayRequest.getIs_payoff());
                custRepay.setRepay_num(prodRepayRequest.getRepay_num());
                custRepay.setRepay_flag(prodRepayRequest.getRepay_flag());
            }
            prodBatchRepayRequest.setCustRepayList(custRepayList);
            prodRepayResponse = prodBusiness.batchRefundAsyn(prodBatchRepayRequest);

        } catch (BusinessException e) {
            prodRepayResponse.setRecode(e.getBaseResponse().getRecode());
            prodRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
        return prodRepayResponse;
    }

    /**
     * 标的还款(异步一借一代)
     * @param httpServletRequest
     * @param prodRepayRequest
     * @return
     */
    @RequestMapping("/batch_repay_asyn")
    @Sign
    @Log(method = "batchRepayAsyn",batchDataName = "funddata")
    public BaseResponse batchRepayAsyn(HttpServletRequest httpServletRequest, ProdBatchRepayRequest prodRepayRequest) {
        logger.info("【标的还款】========请求参数："+prodRepayRequest.toString());
        ProdRepayResponse prodRepayResponse = new ProdRepayResponse();
        validate(prodRepayRequest);
        try {
            List<BatchCustRepay> custRepayList=prodRepayRequest.getCustRepayList();
            for(BatchCustRepay custRepay:custRepayList){
                custRepay.setRepay_fee(BigDecimal.ZERO);
            }
            prodRepayResponse = prodBusiness.batchRefundAsyn(prodRepayRequest);

        } catch (BusinessException e) {
            prodRepayResponse.setRecode(e.getBaseResponse().getRecode());
            prodRepayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
        return prodRepayResponse;
    }

    @RequestMapping("/start_fund_query_job")
    @Log(method = "startFundQueryJob")
    public String startFundQueryJob(HttpServletRequest httpServletRequest, String trans_serial,String mall_no) {
        try {
            prodBusiness.startRefundQueryJob(trans_serial,mall_no);
        } catch (BusinessException e) {
            logger.error(e.getBaseResponse().getRemsg());
        }

        return "success";
    }

    /**
     * 批量投标（异步）
     * @param httpServletRequest
     * @param prodInvestResquest
     * @return
     */
    @RequestMapping("/invest_asyn")
    @Sign
    @Log(method = "investNoSyn",batchDataName = "data")
    public BaseResponse investNoSyn(HttpServletRequest httpServletRequest, ProdInvestNoSynResquest prodInvestResquest) {
        logger.info("【批量投标异步】=======请求参数"+prodInvestResquest);
        ProdInvestNoSynResponse prodInvestResponse = new ProdInvestNoSynResponse();
        validate(prodInvestResquest);
        try {
            prodInvestResponse=prodBusiness.batchInvestAsyn(prodInvestResquest);
        } catch (BusinessException e) {
            prodInvestResponse.setRecode(e.getBaseResponse().getRecode());
            prodInvestResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodInvestResponse.setOrder_no(prodInvestResquest.getOrder_no());
        return prodInvestResponse;
    }

    /**
     * 标的转让（批量-异步）
     * @param httpServletRequest
     * @param prodBatchTransferRequest
     * @return
     */
    @RequestMapping("/batch_transfer_asyn_old")
    @Sign
    @Log(method = "batch_transfer_asyn_old",batchDataName = "data")
    public ProdInvestNoSynResponse batch_transfer_asyn_old(HttpServletRequest httpServletRequest, ProdBatchTransferAsynRequestBo prodBatchTransferRequest) {
        logger.info("【标的转让异步】========请求参数："+prodBatchTransferRequest.toString());
        ProdInvestNoSynResponse prodBatchTransferResponse = new ProdInvestNoSynResponse();
        validate(prodBatchTransferRequest);
        try {
            //参数验证通过后，开始执行转让业务
            List<ProdBatchTransferRequestDataBo> dataArray=prodBatchTransferRequest.getDataArray();
            for(ProdBatchTransferRequestDataBo data:dataArray){
                data.setInterface_version(Constants.OLD_INTERFACE);
            }
            prodBatchTransferResponse=prodBusiness.batchAssignmentAsyn(prodBatchTransferRequest);

        } catch (BusinessException e) {
            prodBatchTransferResponse.setRecode(e.getBaseResponse().getRecode());
            prodBatchTransferResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodBatchTransferResponse.setOrder_no(prodBatchTransferRequest.getOrder_no());
        return prodBatchTransferResponse;
    }

    /**
     * 标的转让（批量-异步）
     * @param httpServletRequest
     * @param prodBatchTransferRequest
     * @return
     */
    @RequestMapping("/auth_batch_transfer_asyn")
    @Sign
    @Log(method = "auth_batch_transfer_asyn",batchDataName = "data")
    public ProdInvestNoSynResponse batch_transfer_asyn(HttpServletRequest httpServletRequest, ProdBatchTransferAsynRequestBo prodBatchTransferRequest) {
        logger.info("【标的转让异步】========请求参数："+prodBatchTransferRequest.toString());
        ProdInvestNoSynResponse prodBatchTransferResponse = new ProdInvestNoSynResponse();
        validate(prodBatchTransferRequest);
        try {
            //参数验证通过后，开始执行转让业务
            prodBatchTransferResponse=prodBusiness.batchAssignmentAsyn(prodBatchTransferRequest);

            //向转让人发送短信
            //TODO 发送通知短信

        } catch (BusinessException e) {
            prodBatchTransferResponse.setRecode(e.getBaseResponse().getRecode());
            prodBatchTransferResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodBatchTransferResponse.setOrder_no(prodBatchTransferRequest.getOrder_no());
        return prodBatchTransferResponse;
    }

    @RequestMapping("/transfer_apply")
    @Sign(checkPassword = CheckPassword.YES)
    @Log(method = "nonAuthTransferApply")
    public ProdTransferNonAuthResponse nonAuthTransferApply(HttpServletRequest httpServletRequest, ProdTransferNonAuthRequest request){
        ProdTransferNonAuthResponse response = new ProdTransferNonAuthResponse();
        validate(request);
        try{
            response = prodBusiness.prodTransferNonAuthApply(request);
        }catch (Exception e){
            logger.info("ProdController|nonAuthTransferApply|dealException|{}",e);
        }
        return response;
    }

    @RequestMapping("/batch_transfer_confirm")
    @Sign
    @Log(method = "batch_transfer_confirm",batchDataName = "data")
    public ProdInvestNoSynResponse nonAuthBatchTransfer(HttpServletRequest httpServletRequest,ProdBatchTransferAsynRequestBo requestBo) {
        ProdInvestNoSynResponse response = new ProdInvestNoSynResponse();
        validate(requestBo);
        try {
            response = prodBusiness.nonAuthbatchAssignmentAsyn(requestBo);

        } catch (BusinessException e) {
            logger.info("ProdController|nonAuthBatchTransfer|dealException|{}", e);
            response.setRecode(e.getBaseResponse().getRecode());
            response.setRemsg(e.getBaseResponse().getRemsg());
        }
        response.setOrder_no(requestBo.getOrder_no());
        return response;
    }
    /**
     * 投标申请-验证密码
     * @param httpServletRequest
     * @param prodInvestApplyRequest
     * @return
     */
    @RequestMapping("/apply_invest")
    @Sign(checkPassword = CheckPassword.YES)
    @Log(method = "applyInvest")
    public BaseResponse applyInvest(HttpServletRequest httpServletRequest, ProdInvestApplyRequest prodInvestApplyRequest) {
        logger.info("【投标申请】=======请求参数"+prodInvestApplyRequest);
        ProdInvestApplyResponse prodInvestApplyResponse = new ProdInvestApplyResponse();
        validate(prodInvestApplyRequest);
        try {
            prodInvestApplyResponse=prodBusiness.applyInvest(prodInvestApplyRequest);
        } catch (BusinessException e) {
            prodInvestApplyResponse.setRecode(e.getBaseResponse().getRecode());
            prodInvestApplyResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodInvestApplyResponse.setOrder_no(prodInvestApplyRequest.getOrder_no());
        return prodInvestApplyResponse;
    }


    /**
     * 投标确认-验证密码
     * @param httpServletRequest
     * @param prodInvestNoSynResquest
     * @return
     */
    @RequestMapping("/confirm_invest")
    @Sign
    @Log(method = "confirmInvest",batchDataName = "data")
    public BaseResponse confirmInvest(HttpServletRequest httpServletRequest, ProdInvestNoSynResquest prodInvestNoSynResquest) {
        logger.info("【投标异步确认】=======请求参数 "+ JSON.toJSONString(prodInvestNoSynResquest));
        ProdInvestNoSynResponse prodInvestNoSynResponse = new ProdInvestNoSynResponse();
        validate(prodInvestNoSynResquest);
        try {
            prodInvestNoSynResponse=prodBusiness.confirmInvest(prodInvestNoSynResquest);
        } catch (BusinessException e) {
            prodInvestNoSynResponse.setRecode(e.getBaseResponse().getRecode());
            prodInvestNoSynResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        prodInvestNoSynResponse.setOrder_no(prodInvestNoSynResquest.getOrder_no());
        return prodInvestNoSynResponse;
    }

    /**
     * 结标
     * @param httpServletRequest
     * @param prodTruncationResquest
     * @return ProdTruncationResponse
     */
    @RequestMapping("/end_prod")
    @Sign
    @Log(method = "endProd")
    public ProdTruncationResponse endProd(HttpServletRequest httpServletRequest,ProdTruncationResquest prodTruncationResquest) {
        logger.info("【结标】-->开始-->order_no:"+prodTruncationResquest.getOrder_no());
        Long start = System.currentTimeMillis();
        validate(prodTruncationResquest);
        ProdTruncationResponse prodTruncationResponse = prodBusiness.endProd(prodTruncationResquest);
        Long end = System.currentTimeMillis();
        logger.info("【结标】-->结束-->order_no:"+prodTruncationResquest.getOrder_no()+"-->耗时:"+(end-start));
        return prodTruncationResponse;
    }
}