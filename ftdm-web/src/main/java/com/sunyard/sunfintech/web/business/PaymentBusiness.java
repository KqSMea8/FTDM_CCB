package com.sunyard.sunfintech.web.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.pub.model.NotifyData;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.user.model.bo.*;
import com.sunyard.sunfintech.user.provider.IRwWithdrawOptionService;
import com.sunyard.sunfintech.user.provider.IUserTransService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by PengZY on 2017/5/28.
 */
@Service("paymentBusiness")
public class PaymentBusiness extends BaseServiceSimple {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IUserTransService userTransService;

    @Autowired
    private IRwWithdrawOptionService rwWithdrawOptionService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Resource(name = "BankSign")
    private SignAdapter signAdapter;

    @Resource(name = "notifyBusiness")
    private NotifyBusiness notifyBusiness;
    /*@Autowired
    private IFundService fundService;

    @Autowired
    private IManagementPayOutService managementPayOutService;

    @Resource(name = "notifyBusiness")
    private NotifyBusiness notifyBusiness;

    @Autowired
    private ISendMsgService sendMsgService;

    @Autowired
    private IAccountSearchService accountSearchService;*/


    /**
     *  批量提现
     * @author pzy
     * @param batchWithdrawRequest
     * @return BatchWithdrawResponse
     */
    public BatchWithdrawResponse batchWithdraw(BatchWithdrawRequest batchWithdrawRequest) {
        BatchWithdrawResponse batchWithdrawResponse = new BatchWithdrawResponse();
        try {
            if (batchWithdrawRequest.getMall_no().contains("FENGJR")) {
                boolean batch_flag = CacheUtil.getCache().setnx(Constants.RW_WITHDRAW_REDIS_KEY + batchWithdrawRequest.getMer_no() + batchWithdrawRequest.getOrder_no(), batchWithdrawRequest.getOrder_no());
                if (!batch_flag) {
                    throw new BusinessException(BusinessMsg.FAIL, "提现订单重复");
                }
                CacheUtil.getCache().expire(Constants.RW_WITHDRAW_REDIS_KEY + batchWithdrawRequest.getMer_no() + batchWithdrawRequest.getOrder_no(), 24 * 60 * 60);

                if (StringUtils.isNotBlank(batchWithdrawRequest.getDatedetail().get(0).getWithdraw_type()) && "03".equals(batchWithdrawRequest.getDatedetail().get(0).getWithdraw_type())) {
                    try {
                        String url = sysParameterService.querySysParameter(batchWithdrawRequest.getMall_no(), "withdrawacquirenotify");
                        logger.info("【提现确认】查询地址，url：" + url);
                        if(StringUtils.isNotBlank(url)){
                            Map<String, Object> param = new HashMap<String, Object>();
                            param.put("order_no", batchWithdrawRequest.getDatedetail().get(0).getDetail_no());
                            param.put("mall_no", batchWithdrawRequest.getMall_no());
                            param.put("plat_no", batchWithdrawRequest.getMer_no());
                            param.put("platcust", batchWithdrawRequest.getDatedetail().get(0).getPlatcust());
                            param.put("trans_date", batchWithdrawRequest.getPartner_trans_date());
                            param.put("trans_time", batchWithdrawRequest.getPartner_trans_time());
                            param.put("out_amt", batchWithdrawRequest.getDatedetail().get(0).getAmt());
                            param.put("fee_amt", batchWithdrawRequest.getDatedetail().get(0).getFee_amt());
                            logger.info("【提现确认】发送给平台参数：" + JSON.toJSON(param));
                            BaseHttpResponse baseHttpResponse = notifyDoPostAcquire(url, JSONObject.toJSONString(param));
                            String entityString = baseHttpResponse.getEntityString().toUpperCase();
                            if (!entityString.contains("10000")) {
                                throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + ",返回确认失败," + entityString);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("【提现确认】异常：", e);
                        BaseResponse baseResponse = new BaseResponse();
                        if (e instanceof BusinessException) {
                            baseResponse = ((BusinessException) e).getBaseResponse();
                        } else {
                            logger.error("批量提现------------------" + e);
                            baseResponse.setRecode(BusinessMsg.UNKNOW_ERROE);
                            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE));
                        }
                        rwWithdrawOptionService.batchWithdrawTrue(batchWithdrawRequest, baseResponse.getRemsg());
                        throw new BusinessException(BusinessMsg.FAIL, BusinessMsg.getMsg(BusinessMsg.FAIL) + "," + baseResponse.getRemsg());
                    }
                }
            }
            batchWithdrawResponse = rwWithdrawOptionService.batchWithdraw(batchWithdrawRequest);
        } catch (BusinessException e) {
            batchWithdrawResponse.setRecode(e.getBaseResponse().getRecode());
            batchWithdrawResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return batchWithdrawResponse;
    }


    public BaseHttpResponse notifyDoPostAcquire(String URL, String json) throws BusinessException {

        logger.info("【提现确认】通知地址:" + URL);
        logger.info("【提现确认】通知数据 未加签:" + json);
        BaseHttpResponse baseHttpResponse = null;
        try {

            Map<String, String> map = (Map<String, String>) JSON.parse(json);
            TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
            String mall_no = treeMap.get("mall_no");
            logger.info("【提现确认】通知集团号:" + mall_no);
            Map<String,String> params=new HashMap<>();
            String orderNo = "";
            String origin_order_no_str="";
            String token_str="";
            params.put("origin_order_no_str",origin_order_no_str);
            params.put("token_str",token_str);
            params.put("order_no_str",orderNo);

            String sign = signAdapter.signUp(mall_no, SunSignatureUtil.getSignContentFromTreeMap(treeMap),params);
            map.put("sign",sign);
            String newjson = JSON.toJSONString(map, GlobalConfig.serializerFeature);
            logger.info("【提现确认】通知数据加签后:" + newjson);

            baseHttpResponse = HttpClientUtils.httpPostRequest(URL, new HashMap<String, Object>(){
                {
                    put("Content-Type", "application/json");
                }
            },newjson);

            logger.info("【提现确认】通知第fengjr  ------" + "返回码：" + baseHttpResponse.getStatusCode() + "返回数据：" + baseHttpResponse.getEntityString());

        } catch (UnsupportedEncodingException e) {

            logger.info("【提现确认】转码异常",e);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "通知第三方调用时转码异常");
            throw new BusinessException(baseResponse);

        }catch (BusinessException e) {

            logger.info("【提现确认】BusinessException异常",e);
            throw new BusinessException(((BusinessException) e).getBaseResponse());

        } catch (Exception e) {

            logger.info("【提现确认】未知异常",e);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "通知第三方调用时未知异常");
            throw new BusinessException(baseResponse);
        }

        return baseHttpResponse;
    }


    /**
     *  提现申请
     * @author pzy
     * @param withdrawApplicationRequest
     * @return BatchWithdrawResponse
     */
    public BatchWithdrawResponse withdrawApplication(WithdrawApplicationRequest withdrawApplicationRequest){
        BatchWithdrawResponse batchWithdrawResponse = new BatchWithdrawResponse();
        try{
            batchWithdrawResponse = rwWithdrawOptionService.withdrawApplication(withdrawApplicationRequest);
        }catch (BusinessException e){
            batchWithdrawResponse.setRecode(e.getBaseResponse().getRecode());
            batchWithdrawResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return batchWithdrawResponse;
    }

    /**
     *  提现确认
     * @author pzy
     * @param withdrawAffirmRequest
     * @return BatchWithdrawResponse
     */
    public BatchWithdrawResponse withdrawAffirm(WithdrawAffirmRequest withdrawAffirmRequest){
        BatchWithdrawResponse batchWithdrawResponse = new BatchWithdrawResponse();
        try{
            batchWithdrawResponse = rwWithdrawOptionService.withdrawAffirm(withdrawAffirmRequest);
        }catch (BusinessException e){
            batchWithdrawResponse.setRecode(e.getBaseResponse().getRecode());
            batchWithdrawResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        return batchWithdrawResponse;
    }

    /**
     * 快捷充值请求
     *
     * @param applyQuickPayRequest
     * @return ApplyQuickPayResponse
     * @author dingjy
     * wml
     */
    public ApplyQuickPayResponse quickPayApply(ApplyQuickPayRequest applyQuickPayRequest) {
        ApplyQuickPayResponse applyQuickPayResponse = new ApplyQuickPayResponse();
        try {

            applyQuickPayResponse = userTransService.applyQuickPay(applyQuickPayRequest);

        } catch (Exception e) {
            logger.error(e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + ",快捷申请失败");
            }
            applyQuickPayResponse.setRecode(baseResponse.getRecode());
            applyQuickPayResponse.setRemsg(baseResponse.getRemsg());
        }
        return applyQuickPayResponse;
    }

    /**
     * 借款人线下还款
     *
     * @param repayOffLineRequest
     * @return BaseResponse
     * @author bobguo
     */
    public OfferChargeResponse repayOffLine(RepayOffLineRequest repayOffLineRequest) {
        OfferChargeResponse offerChargeResponse = new OfferChargeResponse();
        OrderData orderData=new OrderData();
        try {
            offerChargeResponse = userTransService.repayOffLine(repayOffLineRequest);
            orderData = offerChargeResponse.getOrderData();
            orderData.setQuery_id(repayOffLineRequest.getMer_no());
            orderData.setOrder_no(repayOffLineRequest.getOrder_no());
            orderData.setProcess_date(DateUtils.formatDateToStr(new Date()));
//            if (null != orderData.getOrder_status() && !"".equals(orderData.getOrder_status())&&!OrderStatus.PROCESSING.getCode().equals(orderData.getOrder_status())) {
//                String order_status=orderData.getOrder_status();
//                //发送借款人线下还款异步通知
//                String url = repayOffLineRequest.getNotify_url();//平台地址
//                OfferPlatAsynRequest offerPlatAsynRequest = new OfferPlatAsynRequest();
//                offerPlatAsynRequest.setPlat_no(repayOffLineRequest.getMer_no());
//                offerPlatAsynRequest.setAmt(repayOffLineRequest.getAmt());
//                offerPlatAsynRequest.setOrder_no(repayOffLineRequest.getOrder_no());
//                offerPlatAsynRequest.setMall_no(repayOffLineRequest.getMall_no());
//                offerPlatAsynRequest.setData_detail(repayOffLineRequest.getPlatcustlistdetail());
//                offerPlatAsynRequest.setCode(order_status);
//                String asynRqString = JSON.toJSONString(offerPlatAsynRequest, GlobalConfig.serializerFeature);
//
//                if (OrderStatus.SUCCESS.getCode().equals(order_status)) {//交易成功
//                    offerPlatAsynRequest.setMsg(OrderStatus.SUCCESS.getMsg());
//                } else if (OrderStatus.FAIL.getCode().equals(order_status)) {//交易失败
//                    offerPlatAsynRequest.setMsg(OrderStatus.FAIL.getMsg());
//                }
//                logger.info("【准备发送借款人线下还款异步通知】=============url:" + url + ",json:" + asynRqString);
//                // todo
//                notifyBusiness.sendNotify(repayOffLineRequest.getMall_no(),url, asynRqString);
//            }
        } catch (BusinessException b) {
            offerChargeResponse.setRecode(b.getBaseResponse().getRecode());
            offerChargeResponse.setRemsg(b.getBaseResponse().getRemsg());
            orderData.setProcess_date(DateUtils.formatDateToStr(new Date()));
            orderData.setOrder_status(OrderStatus.FAIL.getCode());
        }
        offerChargeResponse.setProcess_date(DateUtils.formatDateToStr(new Date()));
        offerChargeResponse.setOrder_no(repayOffLineRequest.getOrder_no());
        offerChargeResponse.setOrderData(orderData);
        return offerChargeResponse;
    }


    /**
     * 快捷充值确认重写
     *
     * @param confirmQuickPayRequest
     * @return ConfirmQuickPayResponse
     * @author wml
     */
    public ConfirmQuickPayResponse confirmQuickPay(ConfirmQuickPayRequest confirmQuickPayRequest) {
        logger.info("【快捷充值确认】=========确认原快捷充值申请订单号："+confirmQuickPayRequest.getOrigin_order_no());
        Long t1=System.currentTimeMillis();
        ConfirmQuickPayResponse confirmQuickPayResponse = new ConfirmQuickPayResponse();
        try{
            confirmQuickPayResponse = userTransService.confirmQuickPay(confirmQuickPayRequest);
//            ConfirmQuickPayResponseDetail data_detail=confirmQuickPayResponse.getData_detail();
//            //同步响应处理成功或者处理中 向平台发异步
//            logger.info("【快捷充值确认】服务端收到的响应："+JSON.toJSON(confirmQuickPayResponse));
//            if(OrderStatus.SUCCESS.getCode().equals(data_detail.getOrder_status())||
//                    OrderStatus.FAIL.getCode().equals(data_detail.getOrder_status())){
//                Map<String, Object> requestMap =confirmQuickPayResponse.getNotify_msg();
//                if(null==requestMap|| null == requestMap.get("notify_url")){
//                    logger.info("【快捷充值同步发送异步通知】异常：异步通知参数缺失，order_no:"+confirmQuickPayResponse.getOrder_no());
//                }else{
//                    String notify_url=requestMap.get("notify_url").toString();
//                    requestMap.remove("notify_url");
//                    String data = JSONObject.toJSONString(requestMap);
//                    logger.info("【充值异步回调】异步通知平台处理数据：" + data);
//                    logger.info("【充值异步回调】异步地址：" + notify_url);
//
//                    NotifyData notifyData=new NotifyData();
//                    notifyData.setNotifyContent(data);
//                    notifyData.setNotifyUrl(notify_url);
//                    notifyData.setMall_no(confirmQuickPayRequest.getMall_no());
//                    notifyBusiness.sendNotify(notifyData);
//                }
//            }
        }catch(BusinessException e){
            confirmQuickPayResponse.setRecode(e.getBaseResponse().getRecode());
            confirmQuickPayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        Long t2=System.currentTimeMillis();
        logger.info("【快捷充值确认】=========完成："+confirmQuickPayRequest.getOrder_no()+",耗时："+(t2-t1)+"ms");
        confirmQuickPayResponse.setNotify_msg(null);
        return confirmQuickPayResponse;
    }


    /**
     * 网关充值请求接口
     *
     * @param getewayPayRequest
     * @return GetewayPayResponse
     */
    public GetewayPayResponse gatewayRechargeRequest(GetewayPayRequest getewayPayRequest) {
        GetewayPayResponse getewayPayResponse = new GetewayPayResponse();
        logger.info("===========【网关充值请求接口】=====开始，订单号："+getewayPayRequest.getOrder_no()+"===========");
        try {
            getewayPayResponse = userTransService.gatewayRechargeRequest(getewayPayRequest);

        } catch (BusinessException e) {
            logger.info("===========【网关充值请求接口】=====异常，订单号："+getewayPayRequest.getOrder_no()+"===========" + e.getBaseResponse().getRecode() + ":" + e.getBaseResponse().getRemsg());
            getewayPayResponse.setRecode(e.getBaseResponse().getRecode());
            getewayPayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        logger.info("===========【网关充值请求接口】=====开始，订单号："+getewayPayRequest.getOrder_no()+"===========");
        return getewayPayResponse;
    }


   /* *//**
     * 充值回调
     *
     * @param notityPaymentRequest
     */
   public Boolean notifyCharge(NotityPaymentRequest notityPaymentRequest) {
        logger.info("【充值异步回调】" + notityPaymentRequest.toString());
        NotifyConfirmQPres notifyConfirmQPres = null;
        Boolean re = false;
        try {
            notifyConfirmQPres = userTransService.notifyCharge(notityPaymentRequest);
            logger.info("【充值异步回调】dubbo返回参数："+notifyConfirmQPres.toString());
            if (notifyConfirmQPres != null && notifyConfirmQPres.getStatus()) {
                logger.info("【充值异步回调】dubbo返回status："+notifyConfirmQPres.getStatus());
                //异步通知商户
                NotifyData notifyData=new NotifyData();
                notifyData.setNotifyContent( notifyConfirmQPres.getData());
                notifyData.setNotifyUrl(notifyConfirmQPres.getURL());
                notifyData.setMall_no(notifyConfirmQPres.getMall_no());
                notifyBusiness.sendNotify(notifyData);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                logger.info("快捷回调处理失败**失败原因》》" + businessException.getBaseResponse().getRemsg());
            }
            logger.error("快捷充值确认回调接口**>存管操作失败", e);
        }
        return false;
    }

   /* public BaseResponse investChargeAsyn(Map<String, Object> map) {

        BaseResponse baseResponse = new BaseResponse();
        try {
            Map<String, Object> investChargeBussMap = fundService.investChargeAsyn(map);
            OfferPlatAsynRequest offerPlatAsynRequest = setRequestAsynObject(investChargeBussMap);
            String offerPlatAsynRqString = JSON.toJSONString(offerPlatAsynRequest, GlobalConfig.serializerFeature);
            logger.info("【准备发送投资人线下充值异步通知】=============url:" + investChargeBussMap.get("notify").toString() + ",json:" + offerPlatAsynRqString);
            notifyBusiness.nofity(investChargeBussMap.get("notify").toString(), offerPlatAsynRqString);
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException b) {
            baseResponse.setRecode(b.getBaseResponse().getRecode());
            baseResponse.setRemsg(b.getBaseResponse().getRemsg());
        }
        return baseResponse;
    }*/

    public BaseResponse repayOfflineAsyn(Map<String, Object> map) {

        BaseResponse baseResponse = new BaseResponse();
        try {
            RepayOffLineNotifyResponse repayOffLineNotifyResponse = userTransService.borrowRepayAsyn(map);
            String notify_url=repayOffLineNotifyResponse.getNotifyUrl();
            repayOffLineNotifyResponse.setNotifyUrl(null);
            String repayOffLineNotifyStr = JSON.toJSONString(repayOffLineNotifyResponse, GlobalConfig.serializerFeature);
            logger.info("【准备发送借款人线下充值异步通知】=============url:" + notify_url + ",json:" + repayOffLineNotifyStr);
            //todo 异步通知平台
            if(StringUtils.isNotBlank(notify_url)){
                notifyBusiness.sendNotify("",notify_url, repayOffLineNotifyStr);
            }
            baseResponse.setRecode(BusinessMsg.SUCCESS);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (BusinessException b) {
            baseResponse.setRecode(b.getBaseResponse().getRecode());
            baseResponse.setRemsg(b.getBaseResponse().getRemsg());
        }
        return baseResponse;
    }

    /*private OfferPlatAsynRequest setRequestAsynObject(Map<String, Object> bussMap) {
        OfferPlatAsynRequest offerPlatAsynRequest = new OfferPlatAsynRequest();
        offerPlatAsynRequest.setPlat_no(bussMap.get("plat_no").toString());
        offerPlatAsynRequest.setAmt(new BigDecimal(bussMap.get("amt").toString()));
        offerPlatAsynRequest.setOrder_no(bussMap.get("order_no").toString());
        offerPlatAsynRequest.setSign("");
//        offerPlatAsynRequest.setPlatcustlistdetails((List<PlatcustListObject>)bussMap.get("platcustList"));
        return offerPlatAsynRequest;
    }*/

    /**
     * 批量提现请求订单号并发送异步通知
     *
     * @author pzy
     */
    /*public void batchWithdrawNotify() {

        logger.info("【向E支付请求查询订单状态 - 提现表】开始批量提现请求订单号并发送异步通知  web");

        List<RwWithdraw> rwWithdrawList = new ArrayList<RwWithdraw>();

        try {

            rwWithdrawList = managementPayOutService.queryRwWithdraw(TransConsts.BATCH_WITHDRAW_CODE);
            if (null == rwWithdrawList) {
                return;
            }
            if (0 == rwWithdrawList.size()) {
                return;
            }
            logger.info("【向E支付请求查询订单状态 - 提现表】查询到的提现数据  web :" + rwWithdrawList.size());

            for (RwWithdraw rwWithdraw : rwWithdrawList) {

                RwWithdraw rwWithdrawNotify = managementPayOutService.epayBatchQuery(rwWithdraw);

                if (rwWithdrawNotify != null && (rwWithdrawNotify.getPay_status().equals(PayStatus.SUCCESS.getCode()) || rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()))) {

                    logger.info("【E支付返回成功后 - 提现表】开始发送异步通知 success or fail 的通知   RwWithdraw   id:" + rwWithdrawNotify.getId());

                    NotityPaymentReq notityPaymentReq = new NotityPaymentReq();

                    String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());

                    notityPaymentReq.setMall_no(mall_no);
                    notityPaymentReq.setPlat_no(rwWithdrawNotify.getPlat_no());
                    notityPaymentReq.setPlatcust(rwWithdrawNotify.getPlatcust());
                    notityPaymentReq.setOrder_no(rwWithdrawNotify.getOrder_no());
                    notityPaymentReq.setOrder_amt(rwWithdrawNotify.getOut_amt());
                    notityPaymentReq.setTrans_date(rwWithdrawNotify.getTrans_date());
                    notityPaymentReq.setTrans_time(rwWithdrawNotify.getTrans_time());
                    notityPaymentReq.setPay_order_no(rwWithdrawNotify.getTrans_serial());
                    notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                    notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

                    if (rwWithdrawNotify.getPay_status().equals(PayStatus.SUCCESS.getCode()))
                        notityPaymentReq.setOrder_status(OrderStatus.SUCCESS.getCode());

                    if (rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()))
                        notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());

                    if (rwWithdrawNotify.getFee_amt() == null)
                        rwWithdrawNotify.setFee_amt(BigDecimal.ZERO);

                    notityPaymentReq.setPay_amt(rwWithdrawNotify.getFee_amt().add(rwWithdrawNotify.getOut_amt()));

                    String data = JSONObject.toJSONString(notityPaymentReq);
                    notifyBusiness.nofity(rwWithdrawNotify.getNotify_url(), data);

                }

            }

        } catch (Exception e) {

            logger.error("开始批量提现请求订单号并发送异步通知  web  异常 - 提现表" + e);
            e.printStackTrace();

        }
    }

    //行内代付订单查询
    public void inlineQueryWithdrawNotify() {

        logger.info("【行内代付订单查询 - 提现表】开始批量提现请求订单号并发送异步通知  web");

        List<RwWithdraw> rwWithdrawList = new ArrayList<>();

        String lockKey = getLockKey(Constants.RW_WITHDRAW_REDIS_QUERY_KEY);

        try {

            boolean lock = CacheUtil.getLockTime(lockKey, 600L);

            if (!lock) {
                return;
            }

            rwWithdrawList = managementPayOutService.queryRwWithdraw(TransConsts.BATCH_WITHDRAW_CODE);
            if (null == rwWithdrawList) {
                return;
            }
            if (0 == rwWithdrawList.size()) {
                return;
            }


            logger.info("【行内代付订单查询 - 提现表】查询到的提现数据  web :" + rwWithdrawList.size());

            for (RwWithdraw rwWithdraw : rwWithdrawList) {

                try {

                    RwWithdraw rwWithdrawNotify = managementPayOutService.inlineQueryRwWithdraw(rwWithdraw);

                    if (rwWithdrawNotify != null) {

                        if (rwWithdrawNotify.getPay_status().equals(PayStatus.SUCCESS.getCode())
                                || (rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()) && rwWithdrawNotify.getAcct_state().equals(OrderStatus.REFUNDPROCESSING.getCode()))
                                || (rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()) && rwWithdrawNotify.getAcct_state().equals(OrderStatus.REFUNDPSUCCESS.getCode()))
                                ) {
                            NotityPaymentReq notityPaymentReq = new NotityPaymentReq();

                            String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());

                            notityPaymentReq.setMall_no(mall_no);
                            notityPaymentReq.setPlat_no(rwWithdrawNotify.getPlat_no());
                            notityPaymentReq.setPlatcust(rwWithdrawNotify.getPlatcust());
                            notityPaymentReq.setOrder_no(rwWithdrawNotify.getOrder_no());
                            notityPaymentReq.setOrder_amt(rwWithdrawNotify.getOut_amt());
                            notityPaymentReq.setTrans_date(rwWithdrawNotify.getTrans_date());
                            notityPaymentReq.setTrans_time(rwWithdrawNotify.getTrans_time());
                            notityPaymentReq.setPay_order_no(rwWithdrawNotify.getTrans_serial());
                            notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                            notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

                            if (rwWithdrawNotify.getFee_amt() == null)
                                rwWithdrawNotify.setFee_amt(BigDecimal.ZERO);

                            notityPaymentReq.setPay_amt(rwWithdrawNotify.getFee_amt().add(rwWithdrawNotify.getOut_amt()));


                            //发送成功的通知
                            if (rwWithdrawNotify.getPay_status().equals(PayStatus.SUCCESS.getCode())) {
                                notityPaymentReq.setOrder_status(OrderStatus.SUCCESS.getCode());
                                String data = JSONObject.toJSONString(notityPaymentReq);
                                notifyBusiness.nofity(rwWithdrawNotify.getNotify_url(), data);
                            }
                            //发送失败未退款的通知
                            else if (rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()) && rwWithdrawNotify.getAcct_state().equals(OrderStatus.REFUNDPROCESSING.getCode())) {
                                notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                                notityPaymentReq.setError_no(OrderStatus.REFUNDPROCESSING.getCode());
                                notityPaymentReq.setError_info("交易失败，" + OrderStatus.REFUNDPROCESSING.getMsg());
                                String data = JSONObject.toJSONString(notityPaymentReq);
                                notifyBusiness.nofity(rwWithdrawNotify.getNotify_url(), data);
                            }
                            //发送失败已退款的通知
                            else if (rwWithdrawNotify.getPay_status().equals(PayStatus.FAIL.getCode()) && rwWithdrawNotify.getAcct_state().equals(OrderStatus.REFUNDPSUCCESS.getCode())) {
                                notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                                notityPaymentReq.setError_no(OrderStatus.REFUNDPSUCCESS.getCode());
                                notityPaymentReq.setError_info("交易失败，" + OrderStatus.REFUNDPSUCCESS.getMsg());
                                String data = JSONObject.toJSONString(notityPaymentReq);
                                notifyBusiness.nofity(rwWithdrawNotify.getNotify_url(), data);
                            }
                        }

                    }

                } catch (Exception exception) {
                    logger.error("查询处理中，请求行内代付是异常");
                    logger.error(exception);
                }
            }
            CacheUtil.unlock(lockKey);
        } catch (Exception e) {
            CacheUtil.unlock(lockKey);
            logger.error("【行内代付订单查询 - 提现表】  web  异常 - 提现表", e);

        }
    }
*/

    /**
     * job扫描提现表
     *
     * @author pzy
     */
    /*public void queryRwWithdrawMqThirdparty() {

        logger.info("job扫描提现表 发起第三方提现");

        try {
            managementPayOutService.queryRwWithdrawMqThirdparty();
        } catch (Exception e) {

            logger.error("job扫描提现表 发起第三方提现:" + e);
            e.printStackTrace();
        }
    }

    public RwWithdraw payRealReturn(EpayAgentPayRealReturnDetail epayAgentPayRealReturnDetail) {
        logger.info("【云融惠付返回】");
        RwWithdraw rwWithdraw = new RwWithdraw();
        try {
            rwWithdraw = managementPayOutService.updateRwWithdrawAndTransTransreq(epayAgentPayRealReturnDetail);

            String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());

            NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
            notityPaymentReq.setMall_no(mall_no);
            notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
            notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
            notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
            notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
            notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
            notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
            notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
            notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
            notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

            if (rwWithdraw.getPay_status().equals(PayStatus.FAIL.getCode()))
                notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());

            if (rwWithdraw.getPay_status().equals(PayStatus.SUCCESS.getCode()))
                notityPaymentReq.setOrder_status(OrderStatus.SUCCESS.getCode());

            if (rwWithdraw.getFee_amt() == null)
                rwWithdraw.setFee_amt(BigDecimal.ZERO);

            notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));
            logger.info("【开始发送异步通知】");
            String data = JSONObject.toJSONString(notityPaymentReq);
            notifyBusiness.nofity(rwWithdraw.getNotify_url(), data);

        } catch (Exception e) {
            logger.error("【云融惠付返回】更新提现信息和流水异常:" + e);
            e.printStackTrace();
        }
        return rwWithdraw;
    }*/

    /**
     * CCB短信测试模块
     *
     * @param ccbSendMsgReq
     * @return
     */
   /* public BaseResponse ccbSendMsg(CcbSendMsgReq ccbSendMsgReq) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse = sendMsgService.ccbSendMsg(ccbSendMsgReq);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg("发送失败");
        }
        return baseResponse;
    }

    public void batchQuicRwRechargeNotify() {
        logger.info("****************************快捷充值定时查询任务>>start*************************");
        String transCode = TransConsts.QUICK_CONFIRM_CODE;
        //查询快捷充值的 未处理中的信息
        List<RwRecharge> listRwRecharge;
        listRwRecharge = managementPayOutService.queryRwRecharge(transCode);
        if (null != listRwRecharge && 0 != listRwRecharge.size()) {
            for (RwRecharge rwRecharge : listRwRecharge) {
                try {
                    String mall_no = accountSearchService.queryMallNoByPlatNo(rwRecharge.getPlat_no());
                    logger.info("查询集团号" + mall_no);
                    RwRecharge rwRechargeNotify = managementPayOutService.queryEpayStatus(rwRecharge);
                    if (null == rwRechargeNotify) {
                        logger.info("rwRechargeNotify为空 查询参数：" + rwRecharge.toString());
                        logger.info("去看三方日志managementPayOutService.queryEpayStatus");
                        continue;
                    }
                    if (OrderStatus.SUCCESS.getCode().equals(rwRechargeNotify.getStatus()) || OrderStatus.FAIL.getCode().equals(rwRechargeNotify.getStatus())) {
                        //如果状态是成功或者失败
                        logger.info("查询到的信息" + rwRechargeNotify.toString());
                        Map<String, Object> requestMap = new HashMap<>();
                        requestMap.put("mall_no", mall_no);
                        requestMap.put("plat_no", rwRecharge.getPlat_no());
                        requestMap.put("order_no", rwRecharge.getOrder_no());
                        requestMap.put("platcust", rwRecharge.getPlatcust());
                        requestMap.put("type", "1");
                        requestMap.put("order_amt", rwRechargeNotify.getTrans_amt());
                        requestMap.put("trans_date", rwRechargeNotify.getTrans_date());
                        requestMap.put("trans_time", rwRechargeNotify.getTrans_time());
                        requestMap.put("pay_order_no", rwRechargeNotify.getHsepay_order_no());
                        requestMap.put("pay_finish_date", DateUtils.today(DateUtils.DEF_FORMAT_NOTIME));
                        requestMap.put("pay_finish_time", DateUtils.today("HHmmss"));
                        requestMap.put("pay_amt", rwRecharge.getTrans_amt());
                        requestMap.put("sign", " ");
                        requestMap.put("order_status", rwRechargeNotify.getStatus());
                        String data = JSONObject.toJSONString(requestMap);
                        logger.info("********************订单号-->" + rwRecharge.getOrder_no() + "该订单状态" + rwRechargeNotify.getStatus());
                        logger.info("********************异步通知平台信息" + data);
                        logger.info("********************异步通知平台URL" + rwRechargeNotify.getNotify_url());
                        //发送通知
                        notifyBusiness.nofity(rwRechargeNotify.getNotify_url(), data);
                    }
                } catch (Exception e) {
                    logger.error("快捷充值订单：" + rwRecharge.getOrder_no() + "--job扫描失败", e);
                }
            }
        }
    }
*/
    /*public void batchRepayChargeNotify() {

        List<RwRecharge> rwRecharges = new ArrayList<RwRecharge>();

        try {
            rwRecharges = managementPayOutService.queryPayOffOnRecharge(TransConsts.BORROW_REPAY_CODE);
            if (null == rwRecharges) {
                return;
            }
            if (0 == rwRecharges.size()) {
                return;
            }
            for (RwRecharge rwRecharge : rwRecharges) {
                Map<String, Object> map = managementPayOutService.epayRepayChargeQuery(rwRecharge);
                logger.info("【准备发送借款人线下还款异步通知】返回map" + JSON.toJSON(map));
                String data = JSONObject.toJSONString(map.get("offerPlatAsynRequest"));
                logger.info("【准备发送借款人线下还款异步通知】=============url:" + map.get("url").toString() + ",json:" + data);
                notifyBusiness.nofity(map.get("url").toString(), data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("开始借款人线下还款请求订单号并发送异步通知  web  异常 - 充值表", e);
        }
    }

    public void batchOffLineChargeNotify() {

        List<RwRecharge> rwRecharges = new ArrayList<RwRecharge>();

        try {
            rwRecharges = managementPayOutService.queryPayOffOnRecharge(TransConsts.INVEST_CHANGE_CODE);
            if (null == rwRecharges) {
                return;
            }
            if (0 == rwRecharges.size()) {
                return;
            }
            Map<String, Object> map = managementPayOutService.epayOffLineChargeQuery(rwRecharges);
            String data = JSONObject.toJSONString(map.get("offerPlatAsynRequest"));
            logger.info("【准备发送投资人线下充值异步通知】=============url:" + map.get("url").toString() + ",json:" + data);
            notifyBusiness.nofity(map.get("url").toString(), data);

        } catch (Exception e) {
            logger.error("开始投资人线下充值请求订单号并发送异步通知  web  异常 - 充值表" + e);
            e.printStackTrace();
        }
    }
*/

    /**
     * 行内代付异步通知
     * @param notifyEpayAgentPayRealReturnData
     * @author pzy
     */
    public void payplatSinglePayReturn(NotifyEpayAgentPayRealReturnData notifyEpayAgentPayRealReturnData) {
        logger.info("【接受行内代付的异步通知】" + notifyEpayAgentPayRealReturnData.toString());
        try {
            rwWithdrawOptionService.doRwWithdrawNotify(notifyEpayAgentPayRealReturnData);
        } catch (Exception e) {
            logger.error("【接受行内代付的异步通知】异常", e);
        }
    }

    /**
     * 代扣充值
     *
     * @author bob
     */
    public CollectionResponse collection(CollectionRequest collectionRequest) {

        CollectionResponse collectionResponse = new CollectionResponse();
        try {
            collectionResponse = userTransService.collectionService(collectionRequest);
        } catch (Exception e) {
            logger.error(e);
            if (e instanceof BusinessException) {
                collectionResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
                collectionResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
            } else {
                collectionResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                collectionResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            }
        }
        collectionRequest.setOrder_no(collectionRequest.getOrder_no());
        return collectionResponse;
    }

    public ApplyQuickPayResponse agrPayApply(ApplyQuickPayRequest applyQuickPayRequest) {
        ApplyQuickPayResponse applyQuickPayResponse = new ApplyQuickPayResponse();
        try {
            applyQuickPayResponse = userTransService.agrPayApply(applyQuickPayRequest);
        } catch (Exception e) {
            logger.error(e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION) + ",协议支付申请失败");
            }
            applyQuickPayResponse.setRecode(baseResponse.getRecode());
            applyQuickPayResponse.setRemsg(baseResponse.getRemsg());
        }
        return applyQuickPayResponse;
    }

    public ConfirmQuickPayResponse agrPayConfirm(ConfirmQuickPayRequest confirmQuickPayRequest) {
        logger.info("【协议支付确认】=========确认原快捷充值申请订单号："+confirmQuickPayRequest.getOrigin_order_no());
        Long t1=System.currentTimeMillis();
        ConfirmQuickPayResponse confirmQuickPayResponse = new ConfirmQuickPayResponse();
        try{
            confirmQuickPayResponse = userTransService.agrPayConfirm(confirmQuickPayRequest);
//            ConfirmQuickPayResponseDetail data_detail=confirmQuickPayResponse.getData_detail();
//            //同步响应处理成功或者处理中 向平台发异步
//            logger.info("【协议支付确认】服务端收到的响应："+JSON.toJSON(confirmQuickPayResponse));
//            if(OrderStatus.SUCCESS.getCode().equals(data_detail.getOrder_status())||
//                    OrderStatus.FAIL.getCode().equals(data_detail.getOrder_status())){
//                Map<String, Object> requestMap =confirmQuickPayResponse.getNotify_msg();
//                if(null==requestMap|| null == requestMap.get("notify_url")){
//                    logger.info("【协议支付同步发送异步通知】异常：异步通知参数缺失，order_no:"+confirmQuickPayResponse.getOrder_no());
//                }else{
//                    String notify_url=requestMap.get("notify_url").toString();
//                    requestMap.remove("notify_url");
//                    String data = JSONObject.toJSONString(requestMap);
//                    logger.info("【协议支付异步回调】异步通知平台处理数据：" + data);
//                    logger.info("【协议支付异步回调】异步地址：" + notify_url);
//
//                    NotifyData notifyData=new NotifyData();
//                    notifyData.setNotifyContent(data);
//                    notifyData.setNotifyUrl(notify_url);
//                    notifyData.setMall_no(confirmQuickPayRequest.getMall_no());
//                    notifyBusiness.sendNotify(notifyData);
//                }
//            }
        }catch(BusinessException e){
            confirmQuickPayResponse.setRecode(e.getBaseResponse().getRecode());
            confirmQuickPayResponse.setRemsg(e.getBaseResponse().getRemsg());
        }
        Long t2=System.currentTimeMillis();
        logger.info("【协议支付值确认】=========完成："+confirmQuickPayRequest.getOrder_no()+",耗时："+(t2-t1)+"ms");
        confirmQuickPayResponse.setNotify_msg(null);
        return confirmQuickPayResponse;
    }

    public BatchCollectionResponse batchcollection(BatchCollectionRequest collectionRequest) {
        BatchCollectionResponse collectionResponse = new BatchCollectionResponse();
        try {
            collectionResponse = userTransService.batchCollectionService(collectionRequest);
        } catch (Exception e) {
            logger.error(e);
            if (e instanceof BusinessException) {
                collectionResponse.setRecode(((BusinessException) e).getBaseResponse().getRecode());
                collectionResponse.setRemsg(((BusinessException) e).getBaseResponse().getRemsg());
            } else {
                collectionResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.DATEBASE_EXCEPTION));
                collectionResponse.setRecode(BusinessMsg.DATEBASE_EXCEPTION);
            }
        }
        collectionResponse.setOrder_no(collectionRequest.getOrder_no());
        return collectionResponse;
    }

    /*public void collectionJob() {
        logger.info("【代扣充值定时查询任务>>>>>>>>>>>>>>>>>>>>start<<<<<<<<<<<<<<<<<<<<】");
        String transCode = TransConsts.COLLECTION_CODE;
        //查询代扣充值的处理中的信息
        List<RwRecharge> listRwRecharge;
        try {
            listRwRecharge = managementPayOutService.queryRwRecharge(transCode);
            if (null != listRwRecharge && 0 != listRwRecharge.size()) {
                for (RwRecharge rwRecharge : listRwRecharge) {
                    try {
                        String mall_no = accountSearchService.queryMallNoByPlatNo(rwRecharge.getPlat_no());
                        logger.info("【代扣充值定时查询任务】【" + rwRecharge.getTrans_serial() + "】【查询集团号】【" + mall_no + "】");
                        RwRecharge rwRechargeNotify = managementPayOutService.queryEpayStatus(rwRecharge);
                        if (null == rwRechargeNotify) {
                            logger.info("【代扣充值定时查询任务】【" + rwRecharge.getTrans_serial() + "】【queryEpayStatusForCollection查询结果为空】");
                            continue;
                        }
                        logger.info("【代扣充值定时查询任务】【" + rwRecharge.getTrans_serial() + "】【queryEpayStatusForCollection查询结果】【" + rwRechargeNotify.toString() + "】");
                        Map<String, Object> requestMap = new HashMap<>();
                        requestMap.put("mall_no", mall_no);
                        requestMap.put("plat_no", rwRecharge.getPlat_no());
                        requestMap.put("order_no", rwRecharge.getOrder_no());
                        requestMap.put("platcust", rwRecharge.getPlatcust());
                        requestMap.put("type", "1");
                        requestMap.put("order_amt", rwRechargeNotify.getTrans_amt());
                        requestMap.put("trans_date", rwRechargeNotify.getTrans_date());
                        requestMap.put("trans_time", rwRechargeNotify.getTrans_time());
                        requestMap.put("pay_order_no", rwRechargeNotify.getHsepay_order_no());
                        requestMap.put("pay_finish_date", DateUtils.today(DateUtils.DEF_FORMAT_NOTIME));
                        requestMap.put("pay_finish_time", DateUtils.today("HHmmss"));
                        requestMap.put("pay_amt", rwRecharge.getTrans_amt());
                        requestMap.put("order_status", rwRechargeNotify.getStatus());

                        String data = JSONObject.toJSONString(requestMap);
                        logger.info("【代扣充值定时查询任务】【"+rwRecharge.getTrans_serial()+"】【该订单状态】【" + rwRechargeNotify.getStatus()+"】");
                        logger.info("【代扣充值定时查询任务】【"+rwRecharge.getTrans_serial()+"】【异步通知平台URL】【" + rwRechargeNotify.getNotify_url()+"】");
                        logger.info("【代扣充值定时查询任务】【"+rwRecharge.getTrans_serial()+"】【异步通知平台信息】【" + data+"】");
                        //如果状态是成功或者失败
                        if (OrderStatus.SUCCESS.getCode().equals(rwRechargeNotify.getStatus()) || OrderStatus.FAIL.getCode().equals(rwRechargeNotify.getStatus())) {
                            notifyBusiness.nofity(rwRechargeNotify.getNotify_url(), data);
                        }
                    } catch (Exception e) {
                        logger.info("【代扣充值定时查询任务】【"+rwRecharge.getTrans_serial()+"】【通知异常】");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            logger.info("【代扣充值定时查询任务通知异常】");
            e.printStackTrace();
        }
    }*/


}
