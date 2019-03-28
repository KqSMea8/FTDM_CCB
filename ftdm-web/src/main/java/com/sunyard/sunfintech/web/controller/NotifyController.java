package com.sunyard.sunfintech.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.billcheck.model.bo.SortingResponse;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.base.BaseController;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.dao.entity.RwWithdraw;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.user.model.bo.BankPayPayNotifyData;
import com.sunyard.sunfintech.user.model.bo.NotifyEpayAgentPayRealReturnData;
import com.sunyard.sunfintech.user.model.bo.NotityPaymentReq;
import com.sunyard.sunfintech.user.model.bo.NotityPaymentRequest;
import com.sunyard.sunfintech.user.provider.IAccountSearchService;
import com.sunyard.sunfintech.web.business.NotifyBusiness;
import com.sunyard.sunfintech.web.business.PaymentBusiness;
import com.sunyard.sunfintech.web.business.PlatformBusiness;
import com.sunyard.sunfintech.web.business.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * pzy
 * 异步通知
 * Created by PengZY on 2017/6/3.
 */
@RestController
@RequestMapping("/notify")
public class NotifyController extends BaseController {

//    @Autowired
//    private QuartzJobManager quartzJobManager;
    @Autowired
    private ISysParameterService sysParameterService;

    @Resource(name = "notifyBusiness")
    private NotifyBusiness notifyBusiness;

    @Resource(name = "platformBusiness")
    private PlatformBusiness platformBusiness;
//
    @Resource(name = "paymentBusiness")
    private PaymentBusiness paymentBusiness;

    @Autowired
    private IAccountSearchService accountSearchService;

    @Resource(name = "userBusiness")
    private UserBusiness userBusiness;

//
//    @Autowired
//    private IManagementPayOutService managementPayOutService;
//
//    public Boolean runJob(String url, String json) {
//
//        try {
//            notifyBusiness.nofity(url, json);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
    /**
     * 充值回调通知（快捷网关通用）
     * @author ZZ
     * @param httpServletRequest
     * @return NotityPaymentResponse
     */
    @RequestMapping("/notify_payment")
    @Log(method = "notifyPayment")
    public String notityPayment(HttpServletRequest httpServletRequest){
        logger.info("========开始【充值异步回调】=======");
        //获取参数
        StringBuilder stringBuilder = HttpClientUtils.baseHttpResponseToString(httpServletRequest);
        Boolean result = false;
        logger.info("========【充值异步回调】收到回调参数=======" + stringBuilder.toString() + "=============================");
        NotityPaymentRequest notityPaymentRequest = JSON.parseObject(stringBuilder.toString(), NotityPaymentRequest.class);
        if (null == notityPaymentRequest) {
            logger.info("========【充值异步回调】对象为空=============================");
            return "fail";
        }
        logger.info("========【充值异步回调】准备异步通知平台=======" + JSON.toJSONString(notityPaymentRequest));
        try {
            result = paymentBusiness.notifyCharge(notityPaymentRequest);
            logger.info("========【充值异步回调】结果=======" + result);
            if (result) {
                return "success";
            }
        } catch (Exception e) {
            logger.error("【充值异步回调】异常",e);
            return "fail";
        }
        return "fail";
    }

    /**
     * 平台充值异步通知
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/plat_charge_asyn")
    @Log(method = "platChargeAsyn")
    public BaseResponse platChargeAsyn(HttpServletRequest httpServletRequest) {

        logger.info("========同步完成开始进入平台充值异步通知=======");
        //获取参数
        Map<String, Object> map = ParamterUtil.getParamterMap(httpServletRequest);
        //验证参数
        BaseResponse baseResponse = verifyBankParamter(httpServletRequest, map);
        if (baseResponse != null) {
            return baseResponse;
        }
        //异步通知
        return platformBusiness.platChargeAsyn(map);
    }

    /**
     * 平台提现异步通知
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/plat_withdraw_asyn")
    public BaseResponse platWithdrawAsyn(HttpServletRequest httpServletRequest) {

        logger.info("【平台提现异步回调】");
        //获取参数
        Map<String, Object> map = ParamterUtil.getParamterMap(httpServletRequest);
        //验证参数
        BaseResponse baseResponse = verifyBankParamter(httpServletRequest, map);
        if (baseResponse != null) {
            return baseResponse;
        }
        //异步通知
        return platformBusiness.platWithdrawAsyn(map);
    }

//    /**
//     * 投资人线下充值异步通知
//     *
//     * @param httpServletRequest
//     * @return
//     */
//    @RequestMapping("/invest_charge_asyn")
//    public BaseResponse investChargeAsyn(HttpServletRequest httpServletRequest) {
//
//        logger.info("========同步完成开始进入投资人线下充值异步通知接口=======");
//        //获取参数
//        Map<String, Object> map = ParamterUtil.getParamterMap(httpServletRequest);
//        //验证参数
//        BaseResponse baseResponse = verifyBankParamter(httpServletRequest, map);
//        if (baseResponse != null) {
//            return baseResponse;
//        }
//        //异步通知
//        return paymentBusiness.investChargeAsyn(map);
//    }
//
    private BaseResponse verifyBankParamter(HttpServletRequest httpServletRequest, Map<String, Object> map) {
        BaseResponse baseResponse = null;
        logger.info("========银行返回异步通知数据：" + JSON.toJSON(map));
        if (ParamterUtil.isNull(map, "tran_type", "third_no", "third_batch_no", "trandata")) {
            baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            return baseResponse;
        }
        List<BankPayPayNotifyData> bankPayPayDataList = JSON.parseArray(map.get("trandata").toString(), BankPayPayNotifyData.class);
        Map<String, Object> tranDataMap = BeanUtil.transBean2Map(bankPayPayDataList.get(0));
        if (ParamterUtil.isNull(tranDataMap, "seq_no", "tran_amt", "status")) {
            baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.PARAMETER_LACK);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            return baseResponse;
        }
        logger.info("========返回数据通过验证");
        return baseResponse;
    }
//
//    /**
//     * 借款人线下还款异步通知
//     *
//     * @param httpServletRequest
//     * @return BaseResponse
//     * @author bobguo
//     */
    @RequestMapping("/repay_offline_asyn")
    @Log(method = "notifyOffLine")
    public BaseResponse notifyOffLine(HttpServletRequest httpServletRequest) {
        logger.info("========同步完成开始进入借款人还款异步通知=======");
        //获取参数
        Map<String, Object> map = ParamterUtil.getParamterMap(httpServletRequest);
        //验证参数
        BaseResponse baseResponse = verifyBankParamter(httpServletRequest, map);
        if (baseResponse != null) {
            return baseResponse;
        }
        //异步通知
        return paymentBusiness.repayOfflineAsyn(map);
    }
//
//    /**
//     * 对公绑卡异步通知
//     * @param notifyForOrgAccountVerifyRequest
//     * @return
//     */
//    @RequestMapping("/notify_for_org_account_verify")
//    public String notifyForOrgAccountVerifyRequest(NotifyForOrgAccountVerifyRequest notifyForOrgAccountVerifyRequest) {
//        validate(notifyForOrgAccountVerifyRequest);
//        Map<String,Object> params= new HashMap<>();
//        if (CardStatus.ACTIVE.getCode().equals(notifyForOrgAccountVerifyRequest.getCard_status())) {
//            params.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
//            params.put("recode",BusinessMsg.SUCCESS);
//            params.put("order_status","1");
//        } else {
//            params.put("remsg",BusinessMsg.getMsg(BusinessMsg.FAIL));
//            params.put("recode",BusinessMsg.FAIL);
//            params.put("error_info",notifyForOrgAccountVerifyRequest.getCause());
//            params.put("error_no",BusinessMsg.FAIL);
//            params.put("order_status","2");
//        }
//        params.put("order_no",notifyForOrgAccountVerifyRequest.getOrder_no());
//        params.put("sign","testSign");
//        params.put("mall_no",notifyForOrgAccountVerifyRequest.getMall_no());
//        params.put("plat_no",notifyForOrgAccountVerifyRequest.getPlat_no());
//        params.put("platcust",notifyForOrgAccountVerifyRequest.getPlatcust());
//        params.put("trans_date",notifyForOrgAccountVerifyRequest.getTrans_date());
//        params.put("trans_time",notifyForOrgAccountVerifyRequest.getTrans_time());
//        logger.info("【对公账户审核异步通知】=============url:" + notifyForOrgAccountVerifyRequest.getNotify_url() + ",json:" + JSON.toJSONString(params, GlobalConfig.serializerFeature));
//        boolean msg = notifyBusiness.nofity(notifyForOrgAccountVerifyRequest.getNotify_url(), JSON.toJSONString(params, GlobalConfig.serializerFeature));
//        return "success";
//    }
//
//    @RequestMapping("/notify_epay_agent_pay_real_return")
//    @ResponseBody
//    public String payRealReturn(HttpServletRequest httpServletRequest, EpayAgentPayRealReturn epayAgentPayRealReturn) {
//        logger.info("【云融惠付返回订单查询】");
//
//        StringBuilder stringBuilder = HttpClientUtils.baseHttpResponseToString(httpServletRequest);
//
//        EpayAgentPayRealReturn epayAgentPayRealResponse = JSON.parseObject(stringBuilder.toString(), EpayAgentPayRealReturn.class);
//
//        logger.info("【云融惠付返回订单详情】" + epayAgentPayRealResponse.toString());
//
//        validate(epayAgentPayRealResponse.getData().get(0));
//
//        RwWithdraw rwWithdraw = new RwWithdraw();
//        try {
//            rwWithdraw = paymentBusiness.payRealReturn(epayAgentPayRealResponse.getData().get(0));
//        } catch (Exception e) {
//            logger.error("【云融惠付返回订单查询】异常" ,e);
//        }
//        return "success";
//    }
//
//    @RequestMapping("/sendMsgTest")
//    public String sendMsgTest(String mobile, String content) {
//        notifyBusiness.sendMsgTest(mobile, content);
//        return "success";
//    }
//


    /**
     * 行内代付通知异步回调
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/payplat_single_pay_return")
    @Log(method = "payplatSinglePayReturn")
    public void payplatSinglePayReturn(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        logger.info("【接受行内代付的异步通知】");
        NotifyEpayAgentPayRealReturnData notifyEpayAgentPayRealReturnData = null;
        ServletOutputStream out = null;
        try {
            StringBuilder stringBuilder = HttpClientUtils.baseHttpResponseToString(httpServletRequest);
            logger.info("【接受行内代付的异步通知】通知参数:" + stringBuilder);
            notifyEpayAgentPayRealReturnData = JSON.parseObject(stringBuilder.toString(), NotifyEpayAgentPayRealReturnData.class);
            logger.info("【接受行内代付的异步通知】" + notifyEpayAgentPayRealReturnData.toString());
            validate(notifyEpayAgentPayRealReturnData);
            paymentBusiness.payplatSinglePayReturn(notifyEpayAgentPayRealReturnData);
            out = httpServletResponse.getOutputStream();
            out.write("success".getBytes());
        } catch (Exception e) {
            logger.error("【接受行内代付的异步通知】异常", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                logger.info("【接受行内代付的异步通知】异常", e);
            }
        }
    }

    @RequestMapping("/notify_refund_msg")
    @Log(method = "refundMangerNotifyFengj")
    public boolean refundMangerNotifyFengj(HttpServletRequest httpServletRequest, RwWithdraw rwWithdraw) {
        logger.info("【接受管理台的调用，异步通知fengj】");

        try {
            if (rwWithdraw != null) {

                String mall_no = accountSearchService.queryMallNoByPlatNo(rwWithdraw.getPlat_no());
                NotityPaymentReq notityPaymentReq = new NotityPaymentReq();
                notityPaymentReq.setMall_no(mall_no);
                notityPaymentReq.setPlat_no(rwWithdraw.getPlat_no());
                notityPaymentReq.setPlatcust(rwWithdraw.getPlatcust());
                notityPaymentReq.setOrder_no(rwWithdraw.getOrder_no());
                notityPaymentReq.setOrder_amt(rwWithdraw.getOut_amt());
                notityPaymentReq.setAdvance_amt(rwWithdraw.getAdvance_amt());
                notityPaymentReq.setTrans_date(rwWithdraw.getTrans_date());
                notityPaymentReq.setTrans_time(rwWithdraw.getTrans_time());
                notityPaymentReq.setPay_order_no(rwWithdraw.getTrans_serial());
                notityPaymentReq.setPay_finish_date(DateUtils.todayStr(DateUtils.DEF_FORMAT_NOTIME));
                notityPaymentReq.setPay_finish_time(DateUtils.todayStr("hhmmss"));

                notityPaymentReq.setOrder_status(OrderStatus.FAIL.getCode());
                notityPaymentReq.setError_no(OrderStatus.REFUNDPSUCCESS.getCode());
                notityPaymentReq.setError_info(rwWithdraw.getRemark()+","+OrderStatus.REFUNDPSUCCESS.getMsg());

                if (rwWithdraw.getFee_amt() == null) {
                    rwWithdraw.setFee_amt(BigDecimal.ZERO);
                }

                notityPaymentReq.setPay_amt(rwWithdraw.getFee_amt().add(rwWithdraw.getOut_amt()));

                String data = JSONObject.toJSONString(notityPaymentReq);

                notifyBusiness.nofity(mall_no,rwWithdraw.getNotify_url(),data);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("【接受行内代付的异步通知】异常", e);
            return false;
        }

    }
//
//
//    @RequestMapping("/notify_refund_manage")
//    public String notifyRefundManage(HttpServletRequest httpServletRequest, RwRecharge rwRecharge) {
//        logger.info("【接受管理台退票补单的调用，异步通知平台】");
//
//        try {
//            if (rwRecharge != null) {
//
//
//                String mall_no = accountSearchService.queryMallNoByPlatNo(rwRecharge.getPlat_no());
//                logger.info("【接受管理台退票补单的调用，异步通知平台】查询集团号" + mall_no);
//                EaccUserinfo eaccUserinfo = accountSearchService.queryEaccUserInfoByEaccAccountInfo(mall_no, rwRecharge.getPlat_no(), rwRecharge.getPlatcust());
//                logger.info("【接受管理台退票补单的调用，异步通知平台】查询电子账户" + eaccUserinfo.getEaccount());
//
//                Map<String, Object> requestMap = new HashMap<>();
//                requestMap.put("plat_no", rwRecharge.getPlat_no());
//                //todo 这里的订单号是流水号？
//                requestMap.put("mall_no",mall_no);
//                requestMap.put("order_no", rwRecharge.getOrder_no());
//                requestMap.put("platcust", eaccUserinfo.getEaccount());
//                requestMap.put("type", "1");
//                requestMap.put("order_amt", rwRecharge.getTrans_amt());
//                requestMap.put("trans_date", rwRecharge.getTrans_date());
//                requestMap.put("trans_time", rwRecharge.getTrans_time());
//                requestMap.put("pay_order_no", rwRecharge.getHsepay_order_no());
//                requestMap.put("pay_finish_date", DateUtils.today(DateUtils.DEF_FORMAT_NOTIME));
//                requestMap.put("pay_finish_time", DateUtils.today("HHmmss"));
//                requestMap.put("pay_amt", rwRecharge.getTrans_amt());
//                requestMap.put("sign", " ");
//                requestMap.put("order_status", rwRecharge.getStatus());
//
//                String data = JSONObject.toJSONString(requestMap);
//                logger.info("【接受管理台退票补单的调用，异步通知平台】-->" + rwRecharge.getOrder_no() + "该订单状态" + rwRecharge.getStatus());
//                logger.info("【接受管理台退票补单的调用，异步通知平台】异步通知平台信息" + data);
//                logger.info("【接受管理台退票补单的调用，异步通知平台】异步通知平台URL" + rwRecharge.getNotify_url());
//                //如果状态是成功或者失败
//                if (OrderStatus.SUCCESS.getCode().equals(rwRecharge.getStatus()) || OrderStatus.FAIL.getCode().equals(rwRecharge.getStatus())) {
//                    notifyBusiness.nofity(rwRecharge.getNotify_url(), data);
//                }
//            }
//            return "success";
//        } catch (Exception e) {
//            logger.error("【接受管理台退票补单的调用，异步通知平台】异常", e);
//            return "fail";
//        }
//
//    }

    /**
     * 通知清算开始或结束
     *
     * @param request
     * @return
     */
    @RequestMapping("/notify_clear_status")
    @Log(method = "notifyClearStatus")
    public String notifyClearStatus(HttpServletRequest request) {
        String mallNo = request.getParameter("mall_no");
        String clearDate = request.getParameter("clear_date");
        String type = request.getParameter("type");
        try {
            logger.info("==========添加redis清算状态");
            CacheUtil.getCache().set(Constants.REDISKEY_SYS_PARAMETER + mallNo, type, 15 * 60);
            String plat_server = sysParameterService.querySysParameter(mallNo, URLConfigUtil.PLAT_SERVER);
            String plat_sorting_url = sysParameterService.querySysParameter(mallNo, URLConfigUtil.PLAT_SORTING_URL);
            String notify_url = plat_server + "/" + plat_sorting_url;
            logger.info("==========通知地址:" + notify_url);
            SortingResponse sortingResponse = new SortingResponse();
            sortingResponse.setMall_no(mallNo);
            sortingResponse.setClear_date(clearDate);
            sortingResponse.setType(type);
            String json = JSON.toJSONString(sortingResponse);
            //调用对外请求的服务
            notifyBusiness.nofity(mallNo,notify_url, json);
            logger.info("==========通知清算状态成功");
        } catch (Exception e) {
            CacheUtil.getCache().set(Constants.REDISKEY_SYS_PARAMETER + mallNo, type, 15 * 60);
            logger.info("==========通知清算状态失败",e);
            throw new BusinessException("通知失败");
        }
        return "success";
    }

    /**
     * 代发异步通知
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/withdraw_send_asyn")
    @Log(method = "withdrawSendAsyn")
    public BaseResponse withdrawSendAsyn(HttpServletRequest httpServletRequest) {
        logger.info("========同步完成开始进入提现代发异步通知=======");
        //获取参数
        Map<String, Object> map = ParamterUtil.getParamterMap(httpServletRequest);
        //验证参数
        BaseResponse baseResponse = verifyBankParamter(httpServletRequest, map);
        if (baseResponse != null) {
            return baseResponse;
        }
        return userBusiness.withdrawSendAsyn(map);
    }

}
