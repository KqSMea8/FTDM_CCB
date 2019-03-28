package com.sunyard.sunfintech.web.business;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseErrorData;
import com.sunyard.sunfintech.core.base.BaseMessage;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;

import com.sunyard.sunfintech.core.dic.FlowStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.dic.ProdState;
import com.sunyard.sunfintech.core.dic.Tsubject;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.dao.entity.EaccFinancinfo;
import com.sunyard.sunfintech.dao.entity.ProdCompensationList;
import com.sunyard.sunfintech.dao.entity.ProdProductinfo;

import com.sunyard.sunfintech.prod.model.bo.*;
import com.sunyard.sunfintech.prod.model.bo.ProdAbortInvestmentRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdAbortInvestmentResponse;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortRequest;
import com.sunyard.sunfintech.prod.model.bo.ProdEstablishOrAbortResponse;
import com.sunyard.sunfintech.prod.model.bo.ProdInvestNoSynResquest;
import com.sunyard.sunfintech.prod.provider.IProductInvestmentService;
import com.sunyard.sunfintech.prod.provider.IProductOptionService;
import com.sunyard.sunfintech.prod.provider.IProductRefundService;
import com.sunyard.sunfintech.web.model.vo.product.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Ycv
 */
@Service("prodBusiness")
public class ProdBusiness {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IProductOptionService productOptionService;

    @Autowired
    private IProductInvestmentService productInvestmentService;

    @Autowired
    private IProductRefundService productRefundService;

    /**
     * 标的成废
     *
     * @param prodEstablishOrAbortRequest
     * @return
     * @throws BusinessException
     * @author wubin
     */
    public ProdEstablishOrAbortResponse establishOrAbort(ProdEstablishOrAbortRequest prodEstablishOrAbortRequest) {
        ProdEstablishOrAbortResponse response=new ProdEstablishOrAbortResponse();
        try {
            response= productOptionService.establishOrAbort(prodEstablishOrAbortRequest);
        } catch (BusinessException b) {
            response.setRecode(b.getBaseResponse().getRecode());
            response.setRemsg(b.getBaseResponse().getRemsg());
        }
        response.setOrder_no(prodEstablishOrAbortRequest.getOrder_no());
        response.setSign("");
        return response;
    }

    /**
     * 标的成废
     *
     * @param prodEstablishOrAbortRequest
     * @return
     * @throws BusinessException
     * @author wubin
     */
    public ProdEstablishOrAbortResponse establishOrAbortOld(ProdEstablishOrAbortRequestOld prodEstablishOrAbortRequest) {
        ProdEstablishOrAbortResponse response = new ProdEstablishOrAbortResponse();
        try {
            response = productOptionService.establishOrAbortOld(prodEstablishOrAbortRequest);
        } catch (BusinessException b) {
            logger.info("收到异常："+JSON.toJSONString(b.getBaseResponse()));
            response.setRecode(b.getBaseResponse().getRecode());
            response.setRemsg(b.getBaseResponse().getRemsg());
        }
        response.setOrder_no(prodEstablishOrAbortRequest.getOrder_no());
        response.setSign("");
        return response;
    }
    /**
     * 借款人募集申请
     * @author pengziyuan
     * @param prodPublishRequest
     * @return ProdPublishResponseData
     */
    public ProdPublishResponse prodPublish(ProdPublishRequest prodPublishRequest) throws BusinessException {

        ProdPublishResponse publicresponse = new ProdPublishResponse();
        try {
            publicresponse = productOptionService.publish(prodPublishRequest);
        }catch (BusinessException e){
            publicresponse.setRecode(e.getBaseResponse().getRecode());
            publicresponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        publicresponse.setOrder_no(prodPublishRequest.getOrder_no());
        publicresponse.setTrans_date(DateUtils.getyyyyMMddDate());
        return publicresponse;


    }
    /**
     * 标的发布
     *
     * @param prodpublishrequest
     * @return ProdPublishResponseData
     */
    public ProdPublishResponseData prodPublishOld(ProdPublishRequestOldVer prodpublishrequest) throws BusinessException {


        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setSign(prodpublishrequest.getSign());
        baseRequest.setOrder_no(prodpublishrequest.getOrder_no());
        baseRequest.setPartner_trans_date(prodpublishrequest.getPartner_trans_date());
        baseRequest.setPartner_trans_time(prodpublishrequest.getPartner_trans_time());
        baseRequest.setVersion(prodpublishrequest.getVersion());
        baseRequest.setMall_no(prodpublishrequest.getMall_no());
        baseRequest.setMall_name(prodpublishrequest.getMall_name());
        baseRequest.setMer_no(prodpublishrequest.getMer_no());
        baseRequest.setMer_name(prodpublishrequest.getMer_name());
        baseRequest.setRemark(prodpublishrequest.getRemark());


        ProdProductinfo prodProductInfo = new ProdProductinfo();


        //把productRequest映射到prodProductInfo
        prodProductInfo.setProd_id(prodpublishrequest.getProd_id());
        prodProductInfo.setPlat_no(prodpublishrequest.getMer_no());
        prodProductInfo.setProd_name(prodpublishrequest.getProd_name());
        prodProductInfo.setProd_type(prodpublishrequest.getProd_type());
        prodProductInfo.setTotal_limit(prodpublishrequest.getTotal_limit());
        prodProductInfo.setRemain_limit(prodpublishrequest.getTotal_limit());
        //prodProductInfo.setSaled_limit();
        //prodProductInfo.setPayout_type();
        prodProductInfo.setSell_date(prodpublishrequest.getSell_date());
        prodProductInfo.setValue_date(prodpublishrequest.getValue_date());
        prodProductInfo.setValue_type(prodpublishrequest.getValue_type());
        prodProductInfo.setCreate_type(prodpublishrequest.getCreate_type());
        prodProductInfo.setExpire_date(prodpublishrequest.getExpire_date());
        prodProductInfo.setCycle(prodpublishrequest.getCycle());
        prodProductInfo.setCycle_unit(prodpublishrequest.getCycle_unit());
        // prodProductInfo.setParti_num();
        prodProductInfo.setIst_year(prodpublishrequest.getIst_year());
        prodProductInfo.setRepay_type(prodpublishrequest.getRepay_type());
        prodProductInfo.setProd_state(ProdState.PUBLISH.getCode());
        // prodProductInfo.setProd_account(); 还款账号
        prodProductInfo.setCharge_off_auto(prodpublishrequest.getChargeOff_auto());
        prodProductInfo.setRisk_lvl(prodpublishrequest.getRisk_lvl());
        prodProductInfo.setProd_info(prodpublishrequest.getProd_info());
        prodProductInfo.setBuyer_num_limit(String.valueOf(prodpublishrequest.getBuyer_num_limit()));
        prodProductInfo.setOverLimit(prodpublishrequest.getOverLimit());
        prodProductInfo.setOver_total_limit(String.valueOf(prodpublishrequest.getOver_total_limit()));
        prodProductInfo.setEnabled(Constants.ENABLED);
        //prodProductInfo.setCreate_by(prodpublishrequest.getCreate_type());
        prodProductInfo.setCreate_time(DateUtils.getNow());
        //prodProductInfo.setCreate_type(prodpublishrequest.getCreate_type());
        prodProductInfo.setUpdate_time(DateUtils.getNow());

        List<ProdCompensationList> compensationList = prodpublishrequest.getCompensationList();

        ProdPublishResponseData prodPublishResponseData = productOptionService.publishOld(baseRequest, prodProductInfo, prodpublishrequest.getEaccFinancinfo(), compensationList);

        return prodPublishResponseData;


    }
    /**
     * 标的转让
     *
     * @param prodTransferDebtRequest
     * @return ProdTransferDebtResponseData
     * @author terry
     */
    public ProdTransferDebtResponseData assignment(ProdTransferDebtRequest prodTransferDebtRequest) throws BusinessException {
        ProdTransferDebtRequestBo prodTransferDebtRequestBo = new ProdTransferDebtRequestBo();
        BeanUtils.copyProperties(prodTransferDebtRequest, prodTransferDebtRequestBo);
        prodTransferDebtRequestBo.setDetail_no(prodTransferDebtRequest.getOrder_no());
        productInvestmentService.assignment(prodTransferDebtRequestBo);
        ProdTransferDebtResponseData responseData = new ProdTransferDebtResponseData();
        Amtlist amtlist = new Amtlist();
        amtlist.setAmt(prodTransferDebtRequest.getTrans_amt());
        amtlist.setAmttype(prodTransferDebtRequest.getSubject_priority());
        amtlist.setPlatcust(prodTransferDebtRequest.getPlatcust());
        responseData.setAmtlist(amtlist);
        return responseData;
    }

    /**
     * 标的批量转让
     *
     * @param prodBatchTransferRequest
     * @return prodBatchTransferResponse
     * @author terry
     */
    public ProdBatchTransferResponse batchAssignment(ProdBatchTransferRequestBo prodBatchTransferRequest) {
        ProdBatchTransferResponse prodBatchTransferResponse = new ProdBatchTransferResponse();
        try {
            ProdBatchTransferReturnData returnData = productInvestmentService.batchAssignment(prodBatchTransferRequest);
            prodBatchTransferResponse.setSuccess_data_obj(returnData.getSuccessData());
            prodBatchTransferResponse.setSuccess_num(String.valueOf(returnData.getSuccessData().size()));
            prodBatchTransferResponse.setRecode(BusinessMsg.SUCCESS);
            prodBatchTransferResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            prodBatchTransferResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
            prodBatchTransferResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
            prodBatchTransferResponse.setError_data_obj(returnData.getErrorData());
        } catch (BusinessException e) {
            prodBatchTransferResponse.setRecode(e.getBaseResponse().getRecode());
            prodBatchTransferResponse.setRemsg(e.getBaseResponse().getRemsg());
            prodBatchTransferResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodBatchTransferResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        prodBatchTransferResponse.setPlat_no(prodBatchTransferRequest.getMer_no());
        prodBatchTransferResponse.setOrder_no(prodBatchTransferRequest.getOrder_no());
        prodBatchTransferResponse.setFinish_datetime(DateUtils.todayStr());
        prodBatchTransferResponse.setTotal_num(String.valueOf(prodBatchTransferRequest.getDataArray().size()));
        return prodBatchTransferResponse;
    }

    /**
     * 投标
     *
     * @param prodInvestResquest
     * @return
     * @author terry
     */
    public ProdInvestResponse invest(ProdInvestResquest prodInvestResquest) {
        ProdInvestResponse prodInvestResponse = new ProdInvestResponse();

        ProdInvestDataTb prodInvestDataTb=new ProdInvestDataTb();
        prodInvestDataTb.setDetail_no(prodInvestResquest.getOrder_no());
        prodInvestDataTb.setPlatcust(prodInvestResquest.getPlatcust());
        prodInvestDataTb.setTrans_amt(prodInvestResquest.getTrans_amt());
        prodInvestDataTb.setExperience_amt(prodInvestResquest.getExperience_amt());
        prodInvestDataTb.setCoupon_amt(prodInvestResquest.getCoupon_amt());
        prodInvestDataTb.setSelf_amt(prodInvestResquest.getSelf_amt());
        prodInvestDataTb.setIn_interest(prodInvestResquest.getIn_interest());
        prodInvestDataTb.setSubject_priority(prodInvestResquest.getSubject_priority());
        prodInvestDataTb.setNotify_url(prodInvestResquest.getNotify_url());


        ProdProductinfo prodProductInfo = new ProdProductinfo();
        prodProductInfo.setPlat_no(prodInvestResquest.getMer_no());
        prodProductInfo.setProd_id(prodInvestResquest.getProd_id());
        try {
            ProdInvestReturnData returnData = productInvestmentService.invest(prodInvestResquest, prodProductInfo, prodInvestDataTb);
            if(returnData.getErrorData()!=null && returnData.getErrorData().size()>0){
                BaseErrorData baseErrorData=returnData.getErrorData().get(0);
                prodInvestResponse.setRecode(baseErrorData.getError_no());
                prodInvestResponse.setRemsg(baseErrorData.getError_info());
                prodInvestResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodInvestResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            }else{
                prodInvestResponse.setRecode(BusinessMsg.SUCCESS);
                prodInvestResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                prodInvestResponse.setOrder_status(OrderStatus.SUCCESS.getCode());
                prodInvestResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());

            }


        } catch (BusinessException e) {
            prodInvestResponse.setRecode(e.getBaseResponse().getRecode());
            prodInvestResponse.setRemsg(e.getBaseResponse().getRemsg());
            prodInvestResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodInvestResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        prodInvestResponse.setOrder_no(prodInvestResquest.getOrder_no());
        prodInvestResponse.setTrans_date(DateUtils.todayStr());
        return prodInvestResponse;
    }

    /**
     * 批量投标
     *
     * @param prodBatchInvestResquest
     * @return
     * @author terry
     */
    public ProdBatchInvestResponse batchInvest(ProdInvestNoSynResquest prodBatchInvestResquest) {
        ProdBatchInvestResponse prodBatchInvestResponse = new ProdBatchInvestResponse();
         List<ProdInvestData> prodInvestDataList = prodBatchInvestResquest.getDataArray();
        if (prodBatchInvestResquest.getTotal_num() != null &&prodBatchInvestResquest.getTotal_num() >0&& prodBatchInvestResquest.getTotal_num() == prodInvestDataList.size()) {
            ProdProductinfo prodProductInfo = new ProdProductinfo();
            prodProductInfo.setPlat_no(prodBatchInvestResquest.getMer_no());
            prodProductInfo.setProd_id(prodInvestDataList.get(0).getProd_id());
            try {
//                ProdInvestReturnData returnData = productUserOptionService.batchInvest(prodBatchInvestResquest, prodProductInfo, prodInvestDataList);
           //   ProdInvestReturnData returnData = productInvestmentService.batchInvest(prodBatchInvestResquest, prodProductInfo, prodInvestDataList);
//                ProdInvestNoSynResquest asyncRequest=prodBatchInvestResquest.getProdInvestNoSynResquest();
//              logger.info("同步出借转异步,orderid="+prodBatchInvestResquest.getOrder_no()+",prodBatchInvestResquest="+ JSON.toJSONString( prodBatchInvestResquest)+",ProdInvestNoSynResquest="+ JSON.toJSONString( asyncRequest));

                BaseResponse baseResponse=  productInvestmentService.  batchInvestNoSync(prodBatchInvestResquest,false);
//                logger.info("同步出借转异步,orderid="+prodBatchInvestResquest.getOrder_no()+",prodBatchInvestResquest="+ JSON.toJSONString( prodBatchInvestResquest)+",ProdInvestNoSynResquest="+ JSON.toJSONString( asyncRequest)+",response:"+JSON.toJSONString( baseResponse));
             //   ProdInvestReturnData returnData=   getReturnData(prodInvestDataList,baseResponse,prodBatchInvestResquest);

//                prodBatchInvestResponse.setError_data_obj(returnData.getErrorData());
//                prodBatchInvestResponse.setSuccess_data_obj(returnData.getSuccessData());
//                prodBatchInvestResponse.setSuccess_num(String.valueOf(returnData.getSuccessData().size()));
                prodBatchInvestResponse.setRecode(BusinessMsg.SUCCESS);
                prodBatchInvestResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                prodBatchInvestResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
                prodBatchInvestResponse.setOrder_info(OrderStatus.SUCCESS.getMsg());
            } catch (BusinessException e) {
//                prodBatchInvestResponse.setError_data_obj(getErrorDataList(e));
                prodBatchInvestResponse.setRecode(e.getBaseResponse().getRecode());
                prodBatchInvestResponse.setRemsg(e.getBaseResponse().getRemsg());
                prodBatchInvestResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodBatchInvestResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            }
            prodBatchInvestResponse.setPlat_no(prodBatchInvestResquest.getMer_no());
            prodBatchInvestResponse.setOrder_no(prodBatchInvestResquest.getOrder_no());
            prodBatchInvestResponse.setFinish_datetime(DateUtils.todayStr());
            prodBatchInvestResponse.setTotal_num(String.valueOf(prodInvestDataList.size()));
        } else {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            throw new BusinessException(baseResponse);
        }
        return prodBatchInvestResponse;
    }

    private ProdInvestReturnData getReturnData(List<ProdInvestDataTb> prodInvestDataList, BaseResponse baseResponse,ProdBatchInvestResquest prodBatchInvestResquest) {
        List<ProdInvestSuccessData> successData = new ArrayList<>();
        List<BaseErrorData> errorData = new ArrayList<>();
        for (ProdInvestDataTb investTb : prodInvestDataList) {
            boolean isSuc = false;
            BaseResponse errResp = new BaseResponse();
            Map<String, BaseResponse> map = baseResponse.getOrderAfterProcessMap();
            if (map != null && map.containsKey(investTb.getDetail_no())) {
                BaseResponse obj = map.get(investTb.getDetail_no());
                isSuc = obj != null && (BusinessMsg.SUCCESS.equals(obj.getRecode()));
            }
            if (isSuc) {
                ProdInvestSuccessData prodInvestSuccessData = new ProdInvestSuccessData();
                prodInvestSuccessData.setDetail_no(investTb.getDetail_no());
                prodInvestSuccessData.setPlatcust(investTb.getPlatcust());
                prodInvestSuccessData.setProd_id(prodBatchInvestResquest.getProd_id());
                prodInvestSuccessData.setTrans_amt(String.valueOf(investTb.getTrans_amt()));
                //   prodInvestSuccessData.setLink_trans_serial(investTb.getLink_trans_serial());
                Amtlist amtlist = new Amtlist();
                amtlist.setPlatcust(investTb.getPlatcust());
                if ("0".equals(investTb.getSubject_priority())) {
                    amtlist.setAmttype(Tsubject.CASH.getCode());
                } else if ("1".equals(investTb.getSubject_priority())) {
                    amtlist.setAmttype(Tsubject.FLOAT.getCode());
                }
                amtlist.setAmt(investTb.getTrans_amt());
                prodInvestSuccessData.setAmtlist(amtlist);
                successData.add(prodInvestSuccessData);
            } else {
                BaseErrorData baseErrorData = new BaseErrorData();
                baseErrorData.setError_info(errResp.getRemsg());
                baseErrorData.setError_no(errResp.getRecode());
                baseErrorData.setDetail_no(investTb.getDetail_no());

                errorData.add(baseErrorData);

            }
        }
        ProdInvestReturnData returnData=new ProdInvestReturnData();
        returnData.setSuccessData(successData);
        returnData.setErrorData(errorData);
        return returnData;

    }

    /**
     * 借款人还款计划更新
     *
     * @param prodUpdateRepaymentPlanRequest
     * @return BaseMessage
     * @throws BusinessException
     * @author dingjy
     * @time:2017年5月15日 上午11:30:09
     */
    public ProdUpdateRepaymentPlanResponse updateRefundPlan(ProdUpdateRepaymentPlanRequest prodUpdateRepaymentPlanRequest) throws BusinessException {

        ProdUpdateRepaymentPlanResponse prodRepayPlanUpdate = new ProdUpdateRepaymentPlanResponse();
        prodRepayPlanUpdate.setTrans_date(DateUtils.getyyyyMMddDate());
        try {
            prodRepayPlanUpdate = productRefundService.updateRefundPlan(prodUpdateRepaymentPlanRequest);
        } catch (BusinessException e) {
            prodRepayPlanUpdate.setRecode(e.getBaseResponse().getRecode());
            prodRepayPlanUpdate.setRemsg(e.getBaseResponse().getRemsg());
        }
        return prodRepayPlanUpdate;
    }


    public List<BaseErrorData> getErrorDataList(BusinessException e) {
        BaseErrorData baseErrorData = new BaseErrorData();
        baseErrorData.setDetail_no(e.getBaseResponse().getOrder_no());
        baseErrorData.setError_no(e.getBaseResponse().getRecode());
        baseErrorData.setError_info(e.getBaseResponse().getRemsg());
        List<BaseErrorData> errorDataList = new ArrayList<BaseErrorData>();
        errorDataList.add(baseErrorData);
        return errorDataList;
    }

    /**
     * 标的出账信息修改Business
     *
     * @param prodUpdatePayoutAccountRequest
     * @return BaseMessage
     * @author dingjy
     * @time:2017年5月15日 下午5:18:44
     */
    public BaseMessage update_payout_account(ProdUpdatePayoutAccountRequest prodUpdatePayoutAccountRequest) {

        // prodUpdatePayoutAccountRequest映射到BaseRequest
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setSign(prodUpdatePayoutAccountRequest.getSign());
        baseRequest.setOrder_no(prodUpdatePayoutAccountRequest.getOrder_no());
        baseRequest.setPartner_trans_date(prodUpdatePayoutAccountRequest.getPartner_trans_date());
        baseRequest.setPartner_trans_time(prodUpdatePayoutAccountRequest.getPartner_trans_time());
        baseRequest.setVersion(prodUpdatePayoutAccountRequest.getVersion());
        baseRequest.setMall_no(prodUpdatePayoutAccountRequest.getMall_no());
        baseRequest.setMall_name(prodUpdatePayoutAccountRequest.getMall_name());
        baseRequest.setMer_no(prodUpdatePayoutAccountRequest.getMer_no());
        baseRequest.setMer_name(prodUpdatePayoutAccountRequest.getMer_name());
        baseRequest.setRemark(prodUpdatePayoutAccountRequest.getRemark());
        // prodUpdatePayoutAccountRequest映射到EaccFinancinfo
        EaccFinancinfo eaccFinancinfo = new EaccFinancinfo();
        eaccFinancinfo.setProd_id(prodUpdatePayoutAccountRequest.getProd_id());
        eaccFinancinfo.setOpen_branch((prodUpdatePayoutAccountRequest.getOpen_branch()));
        eaccFinancinfo.setWithdraw_account(prodUpdatePayoutAccountRequest.getWithdraw_account());
        eaccFinancinfo.setAccount_type(prodUpdatePayoutAccountRequest.getAccount_type());
        eaccFinancinfo.setPayee_name(prodUpdatePayoutAccountRequest.getPayee_name());
        eaccFinancinfo.setPlat_no(prodUpdatePayoutAccountRequest.getMer_no());
        eaccFinancinfo.setUpdate_time(DateUtils.today());
        // eaccFinancinfo.setUpdate_by("");
        // 请求service
        BaseResponse updateExpenditureInfo = new BaseResponse();
        BaseMessage repayPlanResponse = new BaseMessage();
        try {
            updateExpenditureInfo = productOptionService.updateExpenditureInfo(baseRequest, eaccFinancinfo);
            repayPlanResponse.setRecode(updateExpenditureInfo.getRecode());
            repayPlanResponse.setRemsg(updateExpenditureInfo.getRemsg());
        } catch (BusinessException e) {
            repayPlanResponse.setRecode(e.getBaseResponse().getRecode());
            repayPlanResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return repayPlanResponse;
    }

    /**
     * 投资撤销
     *
     * @param prodAbortInvestmentRequest
     * @return
     * @author wubin
     */
    public ProdAbortInvestmentResponse abortInvestment(ProdAbortInvestmentRequest prodAbortInvestmentRequest) {
        ProdAbortInvestmentResponse response = new ProdAbortInvestmentResponse();
        ProdAbortInvestment prodBusinessData=response.getData_obj();
        try {
            response = productOptionService.abortInvestment(prodAbortInvestmentRequest);
        } catch (BusinessException b) {
            prodBusinessData.setProcess_date(DateUtils.formatDateToStr(new Date()));
            prodBusinessData.setOrder_status(OrderStatus.FAIL.getCode());
            response.setRecode(b.getBaseResponse().getRecode());
            response.setRemsg(b.getBaseResponse().getRemsg());
        }
        return response;

    }

    /**
     * 标的还款()
     *
     * @param prodRepayRequest
     * @return
     * @author Lid
     */
    public ProdRepayResponse batchRefund(ProdBatchRepayOldRequest prodRepayRequest) {
        ProdRepayResponse prodRepayResponse = new ProdRepayResponse();

        List<ProdBusinessData> businessData = new ArrayList<>();
        try {
            businessData = productRefundService.batchRefund(prodRepayRequest);
            prodRepayResponse.setRecode(BusinessMsg.SUCCESS);
            prodRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            prodRepayResponse.setData_obj(businessData);
            prodRepayResponse.setData(JSON.toJSONString(businessData));
            prodRepayResponse.setSign(prodRepayRequest.getSign());
            prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
        } catch (BusinessException b) {
            prodRepayResponse.setRecode(b.getBaseResponse().getRecode());
            prodRepayResponse.setRemsg(b.getBaseResponse().getRemsg());
            prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
            prodRepayResponse.setSign(prodRepayRequest.getSign());

        }
        return prodRepayResponse;
    }

    /**
     * 标的还款(一借一代)
     *
     * @param prodRepayRequest
     * @return
     * @author Lid
     */
    public ProdRepayResponse batchRefundAsyn(ProdBatchRepayRequest prodRepayRequest) {
        ProdRepayResponse prodRepayResponse = new ProdRepayResponse();
        if(prodRepayRequest.getCustRepayList().size()>500){
            logger.info("【标的还款】数组长度太长|最大限制：500|当前长度：%s",prodRepayRequest.getCustRepayList().size());
            throw new BusinessException(BusinessMsg.ARRAY_SIZE_IS_TOO_LARGE,BusinessMsg.getMsg(BusinessMsg.ARRAY_SIZE_IS_TOO_LARGE));
        }

        try {
            productRefundService.batchRefundAsyn(prodRepayRequest);
            prodRepayResponse.setRecode(BusinessMsg.SUCCESS);
            prodRepayResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            prodRepayResponse.setSign(prodRepayRequest.getSign());
            prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
            prodRepayResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
        } catch (BusinessException b) {
            prodRepayResponse.setRecode(b.getBaseResponse().getRecode());
            prodRepayResponse.setRemsg(b.getBaseResponse().getRemsg());
            prodRepayResponse.setOrder_no(prodRepayRequest.getOrder_no());
            prodRepayResponse.setSign(prodRepayRequest.getSign());
            prodRepayResponse.setOrder_status(FlowStatus.FAIL.getCode());
        }
        return prodRepayResponse;
    }

    public boolean startRefundQueryJob(String trans_serial,String mall_no)throws BusinessException{
        productRefundService.startClosedFundQueryJob(trans_serial,mall_no);
        return true;
    }

    /**
     * 批量投标（异步）
     *
     * @param prodInvestResquest
     * @return
     * @author terry
     */
    public ProdInvestNoSynResponse batchInvestAsyn(ProdInvestNoSynResquest prodInvestResquest) {
        ProdInvestNoSynResponse prodInvestResponse = new ProdInvestNoSynResponse();
        List<ProdInvestData> prodInvestDataList = prodInvestResquest.getDataArray();
        if (prodInvestResquest.getTotal_num() != null && prodInvestResquest.getTotal_num() == prodInvestDataList.size()) {
            try {
               productInvestmentService.batchInvestNoSync(prodInvestResquest,true);
//                productUserOptionService.batchInvestAsync(prodInvestResquest);
                prodInvestResponse.setRecode(BusinessMsg.SUCCESS);
                prodInvestResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                prodInvestResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
                prodInvestResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
            } catch (BusinessException e) {
//                prodInvestResponse.setError_data_obj(getErrorDataList(e));
                prodInvestResponse.setRecode(e.getBaseResponse().getRecode());
                prodInvestResponse.setRemsg(e.getBaseResponse().getRemsg());
                prodInvestResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodInvestResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            }
            prodInvestResponse.setOrder_no(prodInvestResquest.getOrder_no());
        } else {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_ERROR);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            throw new BusinessException(baseResponse);
        }
        return prodInvestResponse;
    }

    /**
     * 标的批量转让
     *
     * @param prodBatchTransferRequest
     * @return prodBatchTransferResponse
     * @author terry
     */
    public ProdInvestNoSynResponse batchAssignmentAsyn(ProdBatchTransferAsynRequestBo prodBatchTransferRequest) {
        ProdInvestNoSynResponse prodBatchTransferResponse = new ProdInvestNoSynResponse();
        try {
            productInvestmentService.batchAssignmentNoSync(prodBatchTransferRequest);
            prodBatchTransferResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
            prodBatchTransferResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
            prodBatchTransferResponse.setRecode(BusinessMsg.SUCCESS);
            prodBatchTransferResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException e) {
            prodBatchTransferResponse.setRecode(e.getBaseResponse().getRecode());
            prodBatchTransferResponse.setRemsg(e.getBaseResponse().getRemsg());
            prodBatchTransferResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodBatchTransferResponse.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        prodBatchTransferResponse.setOrder_no(prodBatchTransferRequest.getOrder_no());
        return prodBatchTransferResponse;
    }

    /**
     * 验密债转申请
     * @param request
     * @return
     */
    public ProdTransferNonAuthResponse  prodTransferNonAuthApply(ProdTransferNonAuthRequest request){
        ProdTransferNonAuthResponse response = new ProdTransferNonAuthResponse();
        try {
            productInvestmentService.nonAuthProdTransferApply(request);
            response.setRecode( BusinessMsg.SUCCESS);
            response.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            response.setOrder_status(OrderStatus.SUCCESS.getCode());
        }catch (BusinessException e){
            response.setRecode(e.getBaseResponse().getRecode());
            response.setRemsg(e.getBaseResponse().getRemsg());
            response.setOrder_status(OrderStatus.FAIL.getCode());
        }
        return response;
    }

    public ProdInvestNoSynResponse nonAuthbatchAssignmentAsyn(ProdBatchTransferAsynRequestBo requestBo){
        ProdInvestNoSynResponse response = new ProdInvestNoSynResponse();
        try {
            //原申请订单号不能为空
            List<ProdBatchTransferRequestDataBo> dataArray  =  requestBo.getDataArray();
            for(ProdBatchTransferRequestDataBo dataBo : dataArray){
                if(StringUtils.isEmpty(dataBo.getOrigin_order_no())){
                    response.setRecode(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY);
                    response.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY));
                    response.setOrder_status(OrderStatus.FAIL.getCode());
                    response.setOrder_info(OrderStatus.FAIL.getMsg());
                    return response;
                }
            }
            productInvestmentService.batchAssignmentNoSync(requestBo);
            response.setOrder_status(OrderStatus.PROCESSING.getCode());
            response.setOrder_info(OrderStatus.PROCESSING.getMsg());
            response.setRecode(BusinessMsg.SUCCESS);
            response.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException e) {
            response.setRecode(e.getBaseResponse().getRecode());
            response.setRemsg(e.getBaseResponse().getRemsg());
            response.setOrder_status(OrderStatus.FAIL.getCode());
            response.setOrder_info(OrderStatus.FAIL.getMsg());
        }
        return response;
    }



    public ProdInvestApplyResponse applyInvest(ProdInvestApplyRequest prodInvestApplyRequest){
        logger.info("【投标申请】=======记录订单开始 "+ JSON.toJSONString(prodInvestApplyRequest));
        ProdInvestApplyResponse prodInvestApplyResponse = new ProdInvestApplyResponse();
        prodInvestApplyResponse.setOrder_no(prodInvestApplyRequest.getOrder_no());

        try {
            boolean applySuccess = productInvestmentService.applyInvest(prodInvestApplyRequest);

            logger.info("【投标申请】=======投标申请返回 order_no:" + prodInvestApplyRequest.getOrder_no()+",result:"+applySuccess);
            if(!applySuccess){
                prodInvestApplyResponse.setOrder_no(prodInvestApplyRequest.getOrder_no());
                prodInvestApplyResponse.setOrder_status(OrderStatus.FAIL.getCode());
                prodInvestApplyResponse.setOrder_info(OrderStatus.FAIL.getMsg());
                prodInvestApplyResponse.setRecode(BusinessMsg.FAIL);
                prodInvestApplyResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
            }else{
                prodInvestApplyResponse.setOrder_no(prodInvestApplyRequest.getOrder_no());
                prodInvestApplyResponse.setOrder_status(OrderStatus.REQUESTSUCCESS.getCode());
                prodInvestApplyResponse.setOrder_info(OrderStatus.REQUESTSUCCESS.getMsg());
                prodInvestApplyResponse.setRecode(BusinessMsg.SUCCESS);
                prodInvestApplyResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }

        }catch (Exception e){
            prodInvestApplyResponse.setOrder_no(prodInvestApplyRequest.getOrder_no());
            prodInvestApplyResponse.setOrder_status(OrderStatus.FAIL.getCode());
            prodInvestApplyResponse.setOrder_info(OrderStatus.FAIL.getMsg());
            prodInvestApplyResponse.setRecode(BusinessMsg.FAIL);
            prodInvestApplyResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL));
        }

        logger.info("【投标申请】=======投标申请结果" + JSON.toJSONString(prodInvestApplyResponse));

        return prodInvestApplyResponse;
    }

    public ProdInvestNoSynResponse confirmInvest(ProdInvestNoSynResquest prodInvestNoSynResquest){
        logger.info("【投标确认】=======处理开始 "+ JSON.toJSONString(prodInvestNoSynResquest));
        ProdInvestNoSynResponse prodInvestNoSynResponse = new ProdInvestNoSynResponse();

        // 验证原订单号为空
        for(ProdInvestData prodInvestData : prodInvestNoSynResquest.getDataArray()){
            if(StringUtils.isEmpty(prodInvestData.getOrigin_order_no())){
                logger.info("【投标确认】=======校验原申请订单号为空 "+ JSON.toJSONString(prodInvestNoSynResquest));
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORIGINAL_ORDER_NO_EMPTY));
                throw new BusinessException(baseResponse);
            }
        }

        try {
            productInvestmentService.batchInvestConfirmAsync(prodInvestNoSynResquest);
        }catch (Exception e){
            logger.info("【投标确认】=======记录订单开始 "+ JSON.toJSONString(prodInvestNoSynResquest));
        }

        prodInvestNoSynResponse.setOrder_no(prodInvestNoSynResquest.getOrder_no());
        prodInvestNoSynResponse.setOrder_status(OrderStatus.PROCESSING.getCode());
        prodInvestNoSynResponse.setOrder_info(OrderStatus.PROCESSING.getMsg());
        prodInvestNoSynResponse.setRecode(BusinessMsg.SUCCESS);
        prodInvestNoSynResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));

        logger.info("【投标确认】=======记录订单成功" + JSON.toJSONString(prodInvestNoSynResponse));

        return prodInvestNoSynResponse;
    }

    public ProdTruncationResponse endProd(ProdTruncationResquest prodTruncationResquest)throws BusinessException{
        ProdTruncationResponse prodTruncationResponse = new ProdTruncationResponse();
        try{
            prodTruncationResponse = productOptionService.endProd(prodTruncationResquest);
        }catch (BusinessException e){
            prodTruncationResponse.setRecode(e.getBaseResponse().getRecode());
            prodTruncationResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return prodTruncationResponse;
    }

}
