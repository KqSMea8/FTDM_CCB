package com.sunyard.sunfintech.thirdparty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.thirdparty.utils.PropertiesUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.cache.annotation.CacheConfig;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author heroy
 * @version 2018/1/8
 */


@CacheConfig(cacheNames="ccbPayService")
@org.springframework.stereotype.Service("ccbPayService")
public class CCBPayService extends BaserTirdpartyService implements IAdapterService {
    @Override
    public Map<String, Object> apply4ElementsAuth(Map<String, Object> params) {
        return null;
    }

    @Override
    public Map<String, Object> confirm4ElementsAuth(Map<String, Object> params) {
        return null;
    }

    @Override
    public Map<String, Object> apply4ElementsBindCard(Map<String, Object> params) {
        logger.info("===============调用【短验绑卡申请】入参参数：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        realParams.put("partner_terminal_id", MapUtils.getString(params, "partner_terminal_id", ""));
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        realParams.put("func_code", MapUtils.getString(params, "func_code", ""));
        realParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        realParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        //TODO 生成签名串
        realParams.put("cert_sign", "testsign1111111");

        logger.info("===========【短验绑卡申请】发送给支付参数：" + JSON.toJSON(realParams));
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse.getEntityString() || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【短验绑卡申请】===========第三方接口调用失败，【" +
                        baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【短验绑卡申请】========支付返回：" + result_json);

            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONObject("data");
            String authStatus = data.getString("auth_status");

            if ("13".equals(authStatus)) {
                logger.info("【短验绑卡申请】=========绑卡申请成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("10".equals(authStatus)) {
                logger.info("【短验绑卡申请】=========绑卡确认成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                if (null != data.get("bank_id")) {
                    responseMap.put("bank_no", data.get("bank_id"));
                }
            } else if ("11".equals(authStatus)){
                logger.info("【短验绑卡申请】=========绑卡确认失败：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                if (data.get("respMsg").toString().contains("CFCA")) {
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    responseMap.put("remsg", data.get("respMsg"));
                }
            } else if ("12".equals(authStatus)){
                logger.info("【短验绑卡申请】=========请求短信失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                responseMap.put("remsg", "请求短信失败");
            } else if ("14".equals(authStatus)){
                logger.info("【短验绑卡申请】=========短验绑卡申请处理中");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                responseMap.put("remsg", "短验绑卡申请处理中");
            } else {
                if (result_json.getJSONObject("error_no")!= null){
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                    responseMap.put("remsg", result_json.getJSONObject("error_info"));
                } else {
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                    responseMap.put("remsg", "验证状态未知，绑卡失败！");
                }
            }
            return responseMap;
        } catch (Exception e) {
            logger.error("【短验绑卡申请】===========未知异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> confirm4ElementsBindCard(Map<String, Object> params) {
        logger.info("===============调用【短验绑卡确认】入参参数：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //platPaycode中的partner_id(合作商编号)
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        //流水号
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        //交易日期
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        //交易时间
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        //用户姓名
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        //证件类型(0-身份证)
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        //证件号码
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        //手机号
        realParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        //银行卡号
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        //1-短验申请，2-短验确认，3-无短验确认
        realParams.put("func_code", MapUtils.getString(params, "func_code", ""));
        //验证码(无短验确认无需传值)
        realParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        //账户类型
        realParams.put("pay_bankacct_type", MapUtils.getString(params, "pay_bankacct_type", ""));
        realParams.put("sendercomp_id", MapUtils.getString(params, "sendercomp_id", ""));
        realParams.put("targetcomp_id", MapUtils.getString(params, "targetcomp_id", ""));
        realParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        //=========雅酷必填字段============
        realParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));
        realParams.put("cert_sign", "testsign1111111");

        logger.info("===========【短验绑卡确认】发送给支付参数：" + JSON.toJSON(realParams));
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse.getEntityString() || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【短验绑卡确认】===========第三方接口调用失败，【" +
                        baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【短验绑卡确认】========支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【短验绑卡确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                if ("001003".equals(result_json.get("error_no"))) {
                    responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
                } else if ("001002".equals(result_json.get("error_no"))) {
                    responseMap.put("recode", BusinessMsg.VERIFICATION_OUT_OF_DATE);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
                } else if ("020513".equals(result_json.get("error_no"))) {
                    responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR_TOO_MUCH);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
                } else if (BusinessMsg.CFCA_AUTH_FAILED.equals(result_json.get("error_no"))) {
                    responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    responseMap.put("recode", BusinessMsg.FOUR_VERIFY_FAILED);
                    responseMap.put("remsg", "msgBindCardResponse：短验确认接口返回数据异常：" + result_json.get("error_info"));
                }
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONObject("data");
            String authStatus = data.getString("auth_status");
            if ("10".equals(authStatus)) {
                logger.info("【短验绑卡确认】=========绑卡确认成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("12".equals(authStatus)) {
                logger.info("【短验绑卡确认】=========绑卡确认请求短信失败");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                if (null != data.get("bank_id")) {
                    responseMap.put("bank_no", data.get("bank_id"));
                }
            } else if ("11".equals(authStatus)) {
                logger.info("【短验绑卡确认】=========绑卡确认失败：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                if (data.get("respMsg").toString().contains("CFCA")) {
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    responseMap.put("remsg", data.get("respMsg"));
                }
            } else if ("13".equals(authStatus) || "14".equals(authStatus)) {
                logger.info("【短验绑卡确认】=========绑卡确认状态处理中：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("remsg", "CFCA验证状态未知，绑卡失败！");
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
            }
        } catch (Exception e) {
            logger.error("【短验绑卡确认】===========未知异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> threeElementsAuth(Map<String, Object> params) {
        return null;
    }

    @Override
    public Map<String, Object> threeElementsBindCard(Map<String, Object> params) {
        logger.info("【信用卡绑卡】===========入参参数："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String,Object> realParams = new HashMap<String, Object>();
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        realParams.put("func_code", MapUtils.getString(params, "func_code", ""));
        realParams.put("pay_bankacct_type", MapUtils.getString(params, "pay_bankacct_type", ""));
        realParams.put("sendercomp_id", MapUtils.getString(params, "sendercomp_id", ""));
        realParams.put("targetcomp_id", "91000");
        realParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        //=========雅酷必填字段============
        realParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));
        //泛泰内部支付2,3,4要素鉴权参数
        realParams.put("sub_type","3");
        realParams.put("cert_sign", "testsign1111111");
        logger.info("【信用卡绑卡】===========发送支付参数："+JSON.toJSON(realParams));
        try{
            String url = MapUtils.getString(params,"url","");
            String host = MapUtils.getString(params,"host","");
            url = host+url;
            BaseHttpResponse baseHttpResponse = doPost(realParams,url,"");
            if(null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()){
                logger.info("【信用卡绑卡】===========第三方接口调用失败，【" +
                        baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将支付返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【信用卡绑卡】===========第三方支付返回结果："+result_json);
            if (null == result_json) {
                logger.info("【信用卡绑卡】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null != result_json.get("error_info")){
                responseMap.put("recode",result_json.get("error_no"));
                responseMap.put("remsg",result_json.get("error_info"));
                responseMap.put("success",false);
            }
            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String auth_status = data.getString("auth_status");
            if("10".equals(auth_status)){
                logger.info("【信用卡绑卡】=========信用卡绑卡成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else if("14".equals(auth_status)){
                logger.info("【信用卡绑卡】==========信用卡绑卡处理中");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else{
                logger.info("【信用卡绑卡】==========信用卡绑卡失败");
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                responseMap.put("recode",BusinessMsg.FAIL);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.FAIL));
            }
        }catch (Exception ex){
            logger.info("【信用卡绑卡】==========未知异常："+ex);
            if(ex instanceof BusinessException){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse = ((BusinessException) ex).getBaseResponse();
                responseMap.put("recode",baseResponse.getRecode());
                responseMap.put("remsg",baseResponse.getRemsg());
            }else{
                responseMap.put("recode",BusinessMsg.RUNTIME_EXCEPTION);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            responseMap.put("order_status",OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> twoElementsBindCard(Map<String, Object> params) {
        logger.info("【实名认证】===========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> verifyParams = new HashMap<String, Object>();
        verifyParams.put("cert_sign", "testsign");
        verifyParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        verifyParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        verifyParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        verifyParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        verifyParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        verifyParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        verifyParams.put("sub_type","2");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        url = host + url;
        BaseHttpResponse baseHttpResponse = null;
        try {
            logger.info("===========【实名认证】post请求参数：" + JSON.toJSON(verifyParams));
            baseHttpResponse = doPost(verifyParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【实名认证】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【实名认证】=========支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【实名认证】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                responseMap.put("success", false);
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String auth_status = data.getString("auth_status");
            if("10".equals(auth_status)){
                logger.info("【实名认证】=========实名认证成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else if("14".equals(auth_status)){
                logger.info("【实名认证】==========实名认证处理中");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }else{
                logger.info("【实名认证】==========实名认证失败");
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                responseMap.put("recode",BusinessMsg.FAIL);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.FAIL));
            }
        } catch (Exception e) {
            logger.error("【实名认证】===========异常：", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> applyQuickPay(Map<String, Object> params) {
        logger.info("【快捷充值申请】==========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//平台流水
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        sendParams.put("prod_code", MapUtils.getString(params, "prod_code", ""));//产品编号
        sendParams.put("prod_name", MapUtils.getString(params, "prod_name", ""));//产品名称
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));//摘要
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//客户姓名
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));//银行卡号
        sendParams.put("bank_id", MapUtils.getString(params, "bank_id", ""));//银行ID
        sendParams.put("cvv2", MapUtils.getString(params, "cvv2", ""));//cvv2码：信用卡填
        sendParams.put("valid_date", MapUtils.getString(params, "valid_date", ""));//有效日期：信用卡填
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));//证件号
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//手机号
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));//后台回调地址 异步通知地址
        sendParams.put("verify_mode", MapUtils.getString(params, "verify_mode", "0"));//验证模式 1需要短信验证 0不需要
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//支付通道
        sendParams.put("cert_sign", "quick_pay_test");//签名串
        //获取请求URL
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        logger.info("【快捷充值申请】请求支付参数：" + JSON.toJSON(sendParams));
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(sendParams, host + url, MapUtils.getString(params, "partner_serial_no", ""));
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【快捷充值申请】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【快捷充值申请】==========返回：" + result_json);
            if (null == result_json) {
                logger.info("【快捷充值申请】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                logger.info("【快捷充值申请】 支付组返回错误信息，请求失败");
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONObject("data");

            if ("22".equals(data.get("pay_status"))) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：快捷支付请求短信失败,返回pay_status【22】");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            } else  if ("25".equals(data.get("pay_status"))) {
                if (null != data.get("host_req_serial_no")) {
                    responseMap.put("host_req_serial_no", data.get("host_req_serial_no"));
                }
                if (null != data.get("hsepay_order_no")) {
                    responseMap.put("hsepay_order_no", data.get("hsepay_order_no"));
                }
                if (null != data.get("self_bank_flag")) {
                    responseMap.put("self_bank_flag", data.get("self_bank_flag"));
                }
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                return responseMap;
            } else  {
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS) + "：快捷支付请求未知状态,返回pay_status【"+data.get("pay_status")+"】");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
        } catch (Exception e) {
            logger.error("【快捷充值申请】===========异常：", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
            return responseMap;
        }
    }

    @Override
    public Map<String, Object> confirmyQuickPay(Map<String, Object> params) {
        logger.info("【快捷充值确认】===========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//支付通道
        sendParams.put("terminal_id", MapUtils.getString(params, "terminal_id", "0"));//终端号
        sendParams.put("cert_sign", "quick_pay_test");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");

        //------------------------------请求第三方支付---------------------------------------------
        logger.info("【快捷充值确认】=========获取支付响应开始，确认交易流水号：" + MapUtils.getString(params, "partner_serial_no", ""));
        BaseHttpResponse baseHttpResponse = null;
        try {
            try {
                logger.info("===========【快捷充值确认】传给支付参数：" + JSON.toJSON(sendParams));
                baseHttpResponse = doPost(sendParams, host + url, "");
            } catch (Exception ex) {
                logger.error("【向支付发送请求异常】", ex);
                if (ex instanceof BusinessException) {
                    BaseResponse baseResponse = ((BusinessException) ex).getBaseResponse();
                    if (baseResponse.getRemsg().equals("连接被拒绝")
                            || baseResponse.getRemsg().equals("请求第三方连接超时")
                            || baseResponse.getRemsg().equals("连接被重置")) {
                        logger.info("【向支付发送请求异常】" + baseResponse.getRemsg());
                        responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                        responseMap.put("remsg", baseResponse.getRemsg());
                        responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    } else {
                        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                        responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                        responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    }
                }
                return responseMap;
            }
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【快捷充值确认】=========请求第三方支付远程未知状态【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【快捷充值确认】========支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【快捷充值确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no") && !"".equals(result_json.get("error_no"))) {
                logger.info("【快捷充值确认】=========存在验证码相关错误：" + result_json.get("error_info"));
                if (CbbRecode.VERIFICATION_ERROR1.getCode().equals(result_json.get("error_no")) || CbbRecode.VERIFICATION_ERROR2.getCode().equals(result_json.get("error_no"))) {
                    //验证码错误  状态不变
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
                    responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR);
                } else if (CbbRecode.COMMUNICATION_OVERTIME1.getCode().equals(result_json.get("error_no")) || CbbRecode.COMMUNICATION_OVERTIME3.getCode().equals(result_json.get("error_no")) || CbbRecode.COMMUNICATION_OVERTIME3.getCode().equals(result_json.get("error_no"))) {
                    //验证码超时  交易置为失败
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
                    responseMap.put("recode", BusinessMsg.VERIFICATION_OUT_OF_DATE);
                } else if (CbbRecode.VERIFICATION_ERROR_TOO_MUCH1.getCode().equals(result_json.get("error_no")) || CbbRecode.VERIFICATION_ERROR_TOO_MUCH2.getCode().equals(result_json.get("error_no"))) {
                    //验证码错误次数过多  交易置为失败
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
                    responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR_TOO_MUCH);
                } else if (CbbRecode.COMMUNICATION_OVERTIME.getCode().equals(result_json.get("error_no"))) {
                    //支付请求三方通讯超时  交易不变
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.TRANSMISSION_TIMEOUT));
                    responseMap.put("recode", BusinessMsg.TRANSMISSION_TIMEOUT);
                } else if(CbbRecode.ORDER_HAS_PAY.getCode().equals(result_json.get("error_no"))){
                    //支付返回订单已支付,交易不变
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("recode", OrderStatus.PROCESSING.getCode());
                } else{
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("recode", result_json.get("error_no"));
                }
                return responseMap;
            }
            //--------------------------------获取成功返回信息-----------------------------------------------------
            if (result_json.get("data") == null && result_json.getJSONObject("data") == null) {
                logger.info("【快捷充值确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            //获取支付状态
            String payStatus = data.getString("pay_status");
            //如果payStatus==3 则处理成功 转账 记录流水 记录充值信息表
            if (PayStatus.SUCCESS.getCode().equals(payStatus)) {
                logger.info("【快捷充值确认】===========第三方支付返回状态交易成功");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (PayStatus.FAIL.getCode().equals(payStatus)) {
                logger.info("【快捷充值确认】===========第三方支付返回状态交易失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", data.get("error_no") == null ? BusinessMsg.PAYMENT_FAILED : data.get("error_no"));
                responseMap.put("remsg", data.get("error_info") == null ? BusinessMsg.getMsg(BusinessMsg.PAYMENT_FAILED) : data.get("error_info"));
            } else {
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("recode", result_json.get("error_no"));
            }
            logger.info("【快捷充值确认】===========成功请求支付---返回中-------");
            logger.info("【快捷充值确认】===========同步响应结果：" + JSONObject.toJSONString(responseMap));
        } catch (Exception e) {
            logger.error("【快捷充值确认】ERROR:", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            logger.info("【快捷充值确认】===========同步响应结果：" + JSONObject.toJSONString(responseMap));
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> applyGatewayPay(Map<String, Object> params) {
        logger.info("【网关支付请求】=========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));    //合作商编号
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));    //合作商流水
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));    //合作商交易日期
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));    //合作商交易时间
        sendParams.put("prod_code", MapUtils.getString(params, "prod_code", ""));    //产品编号
        sendParams.put("prod_name", MapUtils.getString(params, "prod_name", ""));    //产品名称
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));        //银行卡号
        sendParams.put("currency_code", MapUtils.getString(params, "currency_code", ""));  //币种
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));  //金额
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));    //摘要
        sendParams.put("pay_bankacct_type", MapUtils.getString(params, "pay_bankacct_type", ""));//账户类型
        sendParams.put("bank_id", MapUtils.getString(params, "bank_id", ""));//行号
        sendParams.put("pickup_url", MapUtils.getString(params, "pickup_url", ""));//前台回调地址(展示页面接受通知的地址)
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));//后台回调地址(充值回调通知)
        sendParams.put("p2p_terminal_type", MapUtils.getString(params, "p2p_terminal_type", ""));//终端类型
        sendParams.put("client_property", MapUtils.getString(params, "client_property", ""));//公私标志
        sendParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));//商户用户编号
        sendParams.put("user_info_identify_type", MapUtils.getString(params, "user_info_identify_type", ""));//实名认证类型
        sendParams.put("user_info_identify_state", MapUtils.getString(params, "user_info_identify_state", ""));//实名认证状态
        sendParams.put("user_info_dt_register", MapUtils.getString(params, "user_info_dt_register", ""));//用户注册时间
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));//证件类型
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));//证件号码
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//客户姓名
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//通道ID
        sendParams.put("cert_sign", "quick_pay_test");//签名串
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额
        logger.info("网关支付申请开始...");
        logger.info("===========网关支付请求参数：" + JSON.toJSON(sendParams));
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(sendParams, host + url, MapUtils.getString(params, "partner_serial_no", ""));
            //远程调用失败
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【网关支付请求】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【网关支付请求】========支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【网关支付请求】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("===========================" + result_json.get("error_no") + "云融惠付返回错误信息，第三方请求失败！==============================");
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info") + "返回错误信息，第三方请求失败！");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            //获取支付状态
            String payStatus = data.getString("pay_status");
            logger.info("===========【支付状态为】:" + payStatus + "================【请求成功】==============================");
            if (null != data.get("view")) {
                responseMap.put("view", data.getString("view"));//支付网关页面
            }

            if (null != data.get("hsepay_order_no")) {
                responseMap.put("hsepay_order_no", data.get("hsepay_order_no"));
            }

            if (null != data.get("host_req_serial_no")) {
                responseMap.put("host_req_serial_no", data.get("host_req_serial_no"));
            }
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("【网关充值申请】===========异常：", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    /**
     *
     * 代发提现
     * @param params 参数
     * @return
     */
    @Override
    public Map<String, Object> payFromCompanyToUser(Map<String, Object> params) {
        logger.info("【代发提现】传入参数" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//合作商编号
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//通道ID
        sendParams.put("third_batch_no", MapUtils.getString(params, "third_batch_no", ""));//流水号
        sendParams.put("tran_type", MapUtils.getString(params, "tran_type", ""));//交易类型
        sendParams.put("third_no", MapUtils.getString(params, "third_no", ""));//协议号
        sendParams.put("notify_url", MapUtils.getString(params, "notify_url", ""));//异步通知地址
        sendParams.put("trandata", MapUtils.getString(params, "trandata", ""));//具体数据
        sendParams.put("cert_sign", MapUtils.getString(params, "cert_sign", ""));//签名串

        logger.info("【代发提现】【" + MapUtils.getString(params, "third_batch_no", "") + "】【请求的数据: " + JSON.toJSON(sendParams) + "】");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        url = host + url;
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【代发提现】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【代发提现】========支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【代发提现请求】【" + MapUtils.getString(params, "third_batch_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (!BusinessMsg.SUCCESS.equals(result_json.get("recode"))) {
                //返回处理失败
                logger.info("===========================" + result_json.get("recode") + "支付返回错误信息，第三方请求失败！==============================");
                responseMap.put("recode", result_json.getString("recode"));
                responseMap.put("remsg", result_json.getString("remsg") + "支付返回错误信息，第三方请求失败！");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }else {
                //返回处理成功
                logger.info("===========================" + result_json.get("recode") + "支付返回错误信息，第三方请求成功！==============================");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                return responseMap;
            }
        }catch (Exception e) {
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> payFromUserToCompany(Map<String, Object> params) {
        logger.info("【代扣充值】传入参数" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        //设置请求参数 如果有电子账户  这三个参数是必传的 如果没有电子账户 则根据入参充值到相应账户
        sendParams.put("cr_acct", MapUtils.getString(params, "cr_acct", ""));//电子账号
        sendParams.put("cr_acct_type", MapUtils.getString(params, "cr_acct_type", ""));//0 借记卡
        sendParams.put("cr_acct_name", MapUtils.getString(params, "cr_acct_name", ""));//03 用户电子账户
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//平台流水
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        sendParams.put("prod_code", MapUtils.getString(params, "prod_code", ""));//产品编号
        sendParams.put("prod_name", MapUtils.getString(params, "prod_name", ""));//产品名称
        sendParams.put("currency_code", MapUtils.getString(params, "currency_code", "")); //币种
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));//摘要
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//客户姓名
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));//银行卡号
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));//证件号
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));//后台回调地址 异步通知地址
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//支付通道
        sendParams.put("verify_mode", MapUtils.getString(params, "verify_mode", ""));//验证模式 1需要短信验证 0不需要
        sendParams.put("cert_sign", "quick_pay_test");//签名串
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//手机号
        sendParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));//商户用户编号
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));//证件类型
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额

        logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【请求的数据: " + JSON.toJSON(sendParams) + "】");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        url = host + url;
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【代扣充值】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【代扣充值】========支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                if (BusinessMsg.TRANSMISSION_TIMEOUT.equals(result_json.getString("error_no"))) {
                    //交易处理中
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                } else {
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                }
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", BusinessMsg.getMsg(result_json.getString("error_no")));
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String pay_status = data.getString("pay_status");
            if ("24".equals(pay_status)) {
                //交易处理中
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【处理中】");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", data.get("error_no") == null ? BusinessMsg.SUCCESS : data.get("error_no"));
                responseMap.put("remsg", data.get("error_info") == null ? BusinessMsg.getMsg(BusinessMsg.SUCCESS) : data.get("error_info"));
            } else if ("20".equals(pay_status)) {
                //交易成功
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【交易成功】");
                //返回订单状态
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("21".equals(pay_status)) {
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", data.get("error_no") == null ? BusinessMsg.FAIL : data.get("error_no"));
                responseMap.put("remsg", data.get("error_info") == null ? BusinessMsg.getMsg(BusinessMsg.FAIL) : data.get("error_info"));
            } else {
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【支付返回未知状态>>：】" + pay_status);
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", "支付返回未知状态>>：" + pay_status);
            }
        } catch (Exception e) {
            logger.error("【代扣充值】异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> withdrawInBatch(Map<String, Object> params) {
        logger.info("【提现】传入参数" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//合作商编号
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//合作商流水，即批次号
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));//合作商交易日期
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));//合作商交易时间
        sendParams.put("currency_code", MapUtils.getString(params, "currency_code", ""));
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));//
        sendParams.put("memo",MapUtils.getString(params, "memo", ""));
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//付款姓名
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));//付款账号
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));//
        sendParams.put("province_code", MapUtils.getString(params, "province_code", ""));//
        sendParams.put("city_code", MapUtils.getString(params, "city_code", ""));//
        sendParams.put("brabank_name", MapUtils.getString(params, "brabank_name", ""));//
        sendParams.put("bank_id", MapUtils.getString(params, "bank_id", ""));//开户行号（总行）
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));//后台回调地址
        sendParams.put("withdraw_type", MapUtils.getString(params, "withdraw_type", ""));//
        sendParams.put("client_property", MapUtils.getString(params, "client_property", ""));//公私标志
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        sendParams.put("func_code", MapUtils.getString(params, "func_code", ""));
        sendParams.put("cert_sign", "SUNYARD");//签名串
        logger.info("【向行内支付发送提现请求】开始代付");
        Long start = System.currentTimeMillis();
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host + url, null);
            Long end = System.currentTimeMillis();
            logger.info("【向支付发送请求】返回时间：" + (end - start) + "毫秒");
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【提现】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", CcbPayStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【提现返回】" + result_json);
            if (null == result_json) {
                logger.info("【提现】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", CcbPayStatus.FAIL.getCode());
                return responseMap;
            }
            if (result_json.get("error_no") != null) {
                logger.info("【向支付发送提现请求】同步返回异常，开始更新数据库流水状态为失败");
                responseMap.put("order_status", CcbPayStatus.FAIL.getCode());
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", CcbPayStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            logger.info("【支付代付同步返回】" + data);
            if ("24".equals(data.get("pay_status"))) {
                logger.info("【向支付发送体现请求】开始更新数据库流水状态： 处理中");
                logger.info("【支付代付同步返回】返回数据异常");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", CcbPayStatus.PROCESSING.getCode());
            }
        } catch (Exception e) {
            logger.error("【向支付发送提现请求】代付异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                if (baseResponse.getRemsg().equals("连接被拒绝")
                        || baseResponse.getRemsg().equals("请求第三方连接超时")
                        || baseResponse.getRemsg().equals("连接被重置")) {
                    logger.info("【向支付发送提现请求】" + baseResponse.getRemsg() + " 开始退款");
                    responseMap.put("order_status", CcbPayStatus.FAIL.getCode());
                    responseMap.put("recode", baseResponse.getRecode());
                    responseMap.put("remsg", baseResponse.getRemsg());
                } else {
                    responseMap.put("order_status", CcbPayStatus.PROCESSING.getCode());
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                }
            } else {
                responseMap.put("order_status", CcbPayStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> queryWithdraw(Map<String, Object> params) {
        logger.info("【批量提现查询订单状态】-->传入参数:" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }

        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//合作商编号
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//合作商流水，代表本次查询动作
        sendParams.put("original_serial_no", MapUtils.getString(params, "original_serial_no", ""));//原合作商流水号
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额
        sendParams.put("cert_sign", MapUtils.getString(params, "cert_sign", ""));//签名
        logger.info("【批量提现查询订单状态】-->开始代付发起查询");

        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            long start = System.currentTimeMillis();
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host + url, null);
            long end = System.currentTimeMillis();
            logger.info("【批量提现查询订单状态】-->返回时间：" + (end - start) + "毫秒");
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【批量提现查询订单状态】-->第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口调用失败");
                responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【批量提现查询订单状态】-->【提现返回】:" + result_json);
            if (null == result_json || null == result_json.getJSONObject("data")) {
                logger.info("【批量提现查询订单状态】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口返回值解析失败");
                responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if("BOBSP10005".equals(result_json.get("error_no"))){
                logger.info("【批量提现查询订单状态】-->无此原交易当失败处理");
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("pay_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (result_json.get("error_no") != null) {
                logger.info("【批量提现查询订单状态】-->同步返回异常");
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            logger.info("【批量提现查询订单状态】-->【支付代付同步返回】" + data);
            if ("24".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->处理中");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if ("20".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->处理成功");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("pay_status", OrderStatus.SUCCESS.getCode());
                return responseMap;
            }
            if ("21".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->失败未扣款,需要回滚转账");
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", "【query】"+data.get("fail_cause"));
                responseMap.put("pay_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
        } catch (Exception e) {
            logger.error("【批量提现查询订单状态】-->【向支付发送提现请求】-->代付异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                if (baseResponse.getRemsg().equals("连接被拒绝")
                        || baseResponse.getRemsg().equals("请求第三方连接超时")
                        || baseResponse.getRemsg().equals("连接被重置")) {
                    logger.info("【批量提现查询订单状态】-->" + baseResponse.getRemsg());
                    responseMap.put("recode", baseResponse.getRecode());
                    responseMap.put("remsg", baseResponse.getRemsg());
                    responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                    return responseMap;
                } else {
                    logger.info("【批量提现查询订单状态】-->" + baseResponse.getRemsg());
                    responseMap.put("recode", BusinessMsg.FAIL);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                    responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                    return responseMap;
                }
            } else {
                logger.info("【批量提现查询订单状态】未知异常");
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL) + ",未知异常");
                responseMap.put("pay_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
        }
        return responseMap;

    }

    @Override
    public Map<String, Object> transferOfAccountInBank(Map<String, Object> params) {
        logger.info("【行内划转】========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("channelId", MapUtils.getString(params, "channelId", "04491610"));
        sendParams.put("third_batch_no", MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("tran_type", MapUtils.getString(params, "tran_type", "pay"));
        sendParams.put("third_no", MapUtils.getString(params, "third_no", ""));
        sendParams.put("trandata", MapUtils.getString(params, "trandata", ""));
        sendParams.put("notify_url", MapUtils.getString(params, "receive_url", ""));
        sendParams.put("cert_sign", "testsign");
        try {
            //调支付的
            logger.info("======准备向支付发送借款人线下还款请求的数据:eMap>> " + JSON.toJSON(sendParams));
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【支付代付第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【行内划转】========支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【【行内划转】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                if("200400".equals(result_json.get("error_no"))){
                    //改响应码表示为通讯超时
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                }else{
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                }
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                return responseMap;
            }
            if (null == result_json.get("recode") || !"10000" .equals(result_json.get("recode"))) {
                responseMap.put("recode", BusinessMsg.UNKNOW_ERROE);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        } catch (Exception e) {
            logger.error("【行内划转】异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> queryAccountOfCompany(Map<String, Object> params) {
        logger.info("***【支付平台对公账户查询】入参参数***：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        try {
            sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
            sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));
            sendParams.put("third_batch_no", MapUtils.getString(params, "third_batch_no", ""));
            sendParams.put("tran_type", MapUtils.getString(params, "tran_type", ""));
            sendParams.put("trandata", MapUtils.getString(params, "trandata", ""));
            sendParams.put("cert_sign", "CCBqueryBalance");
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            logger.info("***【支付平台对公账户查询】传入param参数值***：" + JSON.toJSON(sendParams));
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("支付平台对公账户查询】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【支付平台对公账户查询】========支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【支付平台对公账户查询】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null == result_json.get("remsg") || null == result_json.getJSONObject("remsg")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject remsg = result_json.getJSONObject("remsg");
            responseMap.put("open_bank", remsg.get("open_bank"));
            responseMap.put("acct_name_ch", remsg.get("acct_name_ch"));
            responseMap.put("real_time_balance", remsg.get("real_time_balance"));
            responseMap.put("today_amt", remsg.get("today_amt"));
            responseMap.put("yesterday_amt", remsg.get("yesterday_amt"));
            responseMap.put("acct_status",remsg.get("acct_status"));
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("【支付平台对公账户查询】异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> queryListOfCompanyTransfer(Map<String, Object> params) {
        logger.info("***【平台真实账务往来明细查询】入参参数***：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        try {
            sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
            sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//合作商流水号为空，入账结果不确定
            sendParams.put("third_batch_no", MapUtils.getString(params, "third_batch_no", ""));
            sendParams.put("tran_type", MapUtils.getString(params, "tran_type", ""));
            sendParams.put("trandata", MapUtils.getString(params, "trandata", ""));
            sendParams.put("cert_sign", "testsign");
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            logger.info("***【平台真实账务往来明细查询】传入param参数值***：" + JSON.toJSON(sendParams));
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【平台真实账务往来明细查询】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【平台真实账务往来明细查询】========支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【平台真实账务往来明细查询】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【平台真实账务往来明细查询】支付响应错误");
                responseMap.put("array_tran_list", "");
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                return responseMap;
            }

            if (null == result_json.get("remsg") || result_json.getJSONArray("remsg").size()<0) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONArray result_list = result_json.getJSONArray("remsg");
            if (result_list == null || result_list.size() == 0) {
                logger.error("***返回array_tran_list数组为空***");
                throw new BusinessException(BusinessMsg.UNKNOW_REMOTE_STATUS, BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
            }
            responseMap.put("array_tran_list", result_list);
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("【平台真实账务往来明细查询】异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> queryPayStatus(Map<String, Object> params) {
        logger.info("!***单笔订单状态查询*****入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//合作商编号
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//合作商流水，代表本次查询动作
        sendParams.put("original_serial_no", MapUtils.getString(params, "original_serial_no", ""));//原合作商流水号
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));
        sendParams.put("cert_sign", "sunyard");
        //请求第三方
        BaseHttpResponse baseHttpResponse = null;
        try {
            logger.info("***【单笔订单状态查询】传给支付参数***：" + JSON.toJSON(sendParams));
            baseHttpResponse = doPost(sendParams, host + url, null);
            if (null == baseHttpResponse ||200 != baseHttpResponse.getStatusCode()) {
                logger.info("【单笔订单状态查询】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }

            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【单笔订单状态查询】========支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【单笔订单状态查询】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【单笔订单状态查询】========支付返回错误：" + result_json);
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");

//            if (StringUtils.isBlank(data.getString("pay_status")) ||
//                    StringUtils.isBlank(data.getString("original_serial_no")) ||
//                    StringUtils.isBlank(data.getString("occur_balance")) ||
//                    StringUtils.isBlank(data.getString("cert_sign"))) {
//                logger.info("【单笔订单状态查询】========支付返回缺少必要参数：" + data);
//                responseMap.put("recode", BusinessMsg.PARAMETER_LACK);
//                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) + ":支付返回缺少必要参数");
//                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
//                return responseMap;
//            }
            String pay_status = data.getString("pay_status");

            if (CcbPayStatus.SUCCESS.getCode().equals(pay_status)) {
                logger.info("！*****充值订单查询 ==============该订单为交易成功========================");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
            } else if (CcbPayStatus.FAIL.getCode().equals(pay_status)) {
                logger.info("！*****充值订单查询 ==============该订单为交易失败========================");
                //修改用户充值信息表状态 状态为失败
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
            } else if (CcbPayStatus.PROCESSING.getCode().equals(pay_status)) {
                logger.info("！*****充值订单查询 ==============该订单为处理中========================" );
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status",OrderStatus.PROCESSING.getCode());
            } else {
                logger.info("！*****充值订单查询 ==============支付反馈未标注订单状态：" +pay_status);
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status",OrderStatus.PROCESSING.getCode());
            }
        }catch (Exception e){
            logger.error("！*****充值订单查询 异常",e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> unbindCard(Map<String, Object> params) {
        logger.info("【解绑】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        realParams.put("original_serial_no", MapUtils.getString(params, "original_serial_no", ""));
        realParams.put("sendercomp_id", MapUtils.getString(params, "sendercomp_id", ""));
        realParams.put("targetcomp_id",MapUtils.getString(params, "targetcomp_id", ""));
        realParams.put("card_no",MapUtils.getString(params,"card_no",""));
        realParams.put("channelId",MapUtils.getString(params,"channelId",""));
        realParams.put("cert_sign", "testsign123456");
        try {
            //TODO 设置URL地址
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse ||200 != baseHttpResponse.getStatusCode()) {
                logger.info("【解绑第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【解绑第三方接口调用】========支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【解绑第三方接口调用】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【解绑第三方接口调用】========支付返回错误：" + result_json);
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null == result_json.get("data") || ".".equals(result_json.getJSONObject("data"))) {
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }
        } catch (Exception e) {
            logger.error("【解绑第三方接口调用】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getRandomKey(Map<String, Object> params) {
        logger.info("【获取密码因子】=========入参："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String,Object> realParams = new HashMap<String,Object>();
        realParams.put("mall_no",MapUtils.getString(params,"mall_no",""));
        realParams.put("mer_no",MapUtils.getString(params,"mer_no",""));
        realParams.put("password_type",MapUtils.getString(params,"password_type",""));
        realParams.put("sign","testsign");
        //开始请求第三方支付
        try{
            String host = MapUtils.getString(params,"host","");
            String url = MapUtils.getString(params,"url","");
            url = host+url;
            BaseHttpResponse baseHttpResponse = doPost(realParams,url,"");
            if(null == baseHttpResponse || 200!=baseHttpResponse.getStatusCode()){
                logger.info("【获取密码因子第三方接口调用】============第三方接口调用失败");
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【获取密码因子第三方接口调用】============返回结果："+result_json);
            if(null == result_json ) {
                logger.info("【获取密码因子第三方接口调用】============【" + MapUtils.getString(params, "password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【获取密码因子第三方接口调用】============返回错误：" + result_json.get("error_info"));
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null == result_json.get("data") || ".".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            if(null != data.get("random_key")){
                responseMap.put("random_key",data.get("random_key"));
            }
//            if(null != result_json.get("random_value")){
//                responseMap.put("random_value",result_json.get("random_value"));
//            }
            responseMap.put("recode",BusinessMsg.SUCCESS);
            responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        }catch (Exception ex){
            logger.error("【获取密码因子第三方接口调用】=========未知异常" + ex);
            BaseResponse baseResponse = new BaseResponse();
            if(ex instanceof BusinessException){
                baseResponse = ((BusinessException) ex).getBaseResponse();
                responseMap.put("remsg",baseResponse.getRemsg());
                responseMap.put("recode",baseResponse.getRecode());
            }else{
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode",BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status",OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> checkPassowrd(Map<String, Object> params) {
        logger.info("【密码验证】============入参："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String,Object> realParams = new HashMap<String, Object>();
        realParams.put("mall_no",MapUtils.getString(params,"mall_no",""));
        realParams.put("mer_no",MapUtils.getString(params,"mer_no",""));
        realParams.put("plat_cust",MapUtils.getString(params,"plat_cust",""));
        realParams.put("id_kind",MapUtils.getString(params,"id_kind",""));
        realParams.put("id_no",MapUtils.getString(params,"id_no",""));
        realParams.put("passwod",MapUtils.getString(params,"password",""));
        realParams.put("type",StringUtils.isEmpty(MapUtils.getString(params,"type",""))==false?MapUtils.getString(params,"type",""):"0");
        realParams.put("random_key",MapUtils.getString(params,"random_key",""));
        realParams.put("cert_sign", "testsign");
        //请求第三方支付
        BaseHttpResponse baseHttpResponse = null;
        try {
            String host = MapUtils.getString(params,"host","");
            String url = MapUtils.getString(params,"url","");
            url = host+url;
            baseHttpResponse = doPost(realParams,url,null);
            if(null == baseHttpResponse || 200!=baseHttpResponse.getStatusCode()){
                logger.info("【密码验证第三方接口调用】============第三方接口调用失败,【"+baseHttpResponse.getStatusCode()+"】"+baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【密码验证第三方接口调用】=============返回结果:"+result_json);
            if(null == result_json){
                logger.info("【isic密码验证第三方接口调用】============【" + MapUtils.getString(params, "password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【密码验证第三方接口调用】============返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null == result_json.get("data") ||"".equals(result_json.get("data"))) {
                logger.info("【密码验证第三方接口调用】============【" + MapUtils.getString(params, "password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if("10".equals(status)){
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            }else if("11".equals(status)) {
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", data.get("result")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):data.get("result"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
//            }else if("5".equals(status)){
//                responseMap.put("recode",BusinessMsg.FAIL);
//                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.FAIL)+"账户锁定");
//                responseMap.put("order_status", OrderStatus.FAIL.getCode());
//            }else if("9".equals(status)){
//                responseMap.put("recode", result_json.get("error_no"));
//                responseMap.put("remsg", result_json.get("error_info"));
//                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)+"未知状态");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }
        }catch (Exception ex){
            logger.error("【密码验证第三方接口调用】===========未知异常" + ex);
            BaseResponse baseResponse = new BaseResponse();
            if (ex instanceof BusinessException) {
                baseResponse = ((BusinessException) ex).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> setOrResetPassword(Map<String, Object> params) {
        logger.info("【密码修改重置】===============入参："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String,Object> realParams = new HashMap<String,Object>();
        realParams.put("mall_no",MapUtils.getString(params,"mall_no",""));
        realParams.put("mer_no",MapUtils.getString(params,"mer_no",""));
        realParams.put("plat_cust",MapUtils.getString(params,"plat_cust",""));
        String id_kind=MapUtils.getString(params,"id_kind","");
        if(CusType.PERSONAL.getCode().equals(id_kind)){
            id_kind="0";
        }
        realParams.put("id_kind",id_kind);
        realParams.put("id_no",MapUtils.getString(params,"id_no",""));
        realParams.put("tran_flag",MapUtils.getString(params,"tran_flag",""));
        realParams.put("ori_random_key",MapUtils.getString(params,"ori_random_key",""));
        realParams.put("ori_password",MapUtils.getString(params,"ori_password",""));
        realParams.put("random_key",MapUtils.getString(params,"random_key",""));
        realParams.put("passwod",MapUtils.getString(params,"password",""));
        realParams.put("type",MapUtils.getString(params,"type",""));
        realParams.put("cert_sign", "testsign");
        //请求第三方支付
        BaseHttpResponse baseHttpResponse = null;
        try{
            String host = MapUtils.getString(params,"host","");
            String url = MapUtils.getString(params,"url","");
            url = host+url;
            baseHttpResponse = doPost(realParams,url,null);
            if(null == baseHttpResponse || 200!=baseHttpResponse.getStatusCode()){
                logger.info("【密码修改重置第三方接口调用】============第三方接口调用失败,【"+baseHttpResponse.getStatusCode()+"】"+baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【密码修改重置第三方接口调用】=============返回结果:"+result_json);
            if(null == result_json){
                logger.info("【密码修改重置第三方接口调用】============【" + MapUtils.getString(params, "ori_password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【密码修改重置第三方接口调用】============返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if( null==result_json.get("data") ||"".equals(result_json.get("data"))) {
                logger.info("【密码修改重置第三方接口调用】============【" + MapUtils.getString(params, "ori_password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if("10".equals(status)){
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            }else if("11".equals(status)) {
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
//            }else if("9".equals(status)){
//                responseMap.put("recode",BusinessMsg.FAIL);
//                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.FAIL));
//                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)+"未知状态");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }

        }catch (Exception ex){
            logger.error("【密码修改重置第三方接口调用】===========未知异常" + ex);
            BaseResponse baseResponse = new BaseResponse();
            if (ex instanceof BusinessException) {
                baseResponse = ((BusinessException) ex).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicBindCard(Map<String, Object> params) {
        logger.info("【icis四要素绑卡】==========入参："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//Y
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//Y
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));//Y
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));//Y
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));//Y
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));//Y
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));//Y
        realParams.put("cvv2", MapUtils.getString(params, "cvv2", ""));
        realParams.put("valid_date", MapUtils.getString(params, "valid_date", ""));
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));//Y
        realParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//四要素则必填
        realParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        realParams.put("func_code", MapUtils.getString(params, "func_code", ""));//Y
        realParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("mobile", MapUtils.getString(params, "mobile", ""));
        realParams.put("email", MapUtils.getString(params, "email", ""));
        realParams.put("sex", MapUtils.getString(params, "sex", ""));
        realParams.put("cus_type", MapUtils.getString(params, "cus_type", ""));
        realParams.put("random_key", MapUtils.getString(params, "random_key", ""));//Y
        realParams.put("passwod", MapUtils.getString(params, "passwod", ""));//Y
        realParams.put("type",MapUtils.getString(params,"type",""));
        realParams.put("cjr_role", MapUtils.getString(params, "cjr_role", ""));
        realParams.put("jkr_role", MapUtils.getString(params, "jkr_role", ""));
        realParams.put("dzr_role", MapUtils.getString(params, "dzr_role", ""));
        realParams.put("dbr_role", MapUtils.getString(params, "dbr_role", ""));
        realParams.put("open_account", MapUtils.getString(params, "open_account", ""));
        realParams.put("is_bankcheck", MapUtils.getString(params, "is_bankcheck", ""));//Y
        realParams.put("cert_sign", "testsign123456");

        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【icis四要素绑卡】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【icis四要素绑卡】========第三方返回：" + result_json);

            if (null == result_json) {
                logger.info("【icis四要素绑卡】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【icis四要素绑卡】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") || "".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String auth_status = data.get("auth_status").toString();
            if ("10".equals(auth_status)) {
                //认证成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if ("11".equals(auth_status)) {
                //认证失败
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else if("13".equals(auth_status)){
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS)+"绑卡申请成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            }else if("12".equals(auth_status)){
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)+"绑卡申请失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)+"绑卡失败,支付返回未知状态");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }
            if(null!=result_json.get("bank_id")){
                responseMap.put("bank_id",result_json.get("bank_id"));
            }
            if(null!=result_json.get("card_no")){
                responseMap.put("card_no",result_json.get("card_no"));
            }
            if(null!=result_json.get("mobile_tel")){
                responseMap.put("mobile_tel",result_json.get("mobile_tel"));
            }
        } catch (Exception e) {
            logger.error("【icis四要素绑卡】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicCardUnbing(Map<String, Object> params) {
        logger.info("【isic解绑】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//Y
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//Y
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));//Y
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));//Y
        realParams.put("original_serial_no", MapUtils.getString(params, "original_serial_no", ""));//Y
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        realParams.put("channelId",MapUtils.getString(params, "channelId", ""));
        realParams.put("cus_type",MapUtils.getString(params, "cus_type", ""));
        realParams.put("cert_sign", "testsign123456");
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【isic解卡】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic解绑】=======第三方返回：" + result_json);

            if (null == result_json ) {
                logger.info("【isic解绑】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【isic解绑】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") ||"".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if ("15".equals(status)) {
                //解绑成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
//            } else if (IcisAuthStatus.UNBINDFAIL.getCode().equals(status)) {
//                //解绑失败
//                responseMap.put("recode", BusinessMsg.FAIL);
//                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
//                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else if(null != result_json.get("error_no")){
                //处理中
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }
        } catch (Exception e) {
            logger.error("【isic解绑】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicPhoneChange(Map<String, Object> params) {
        logger.info("【isic预留手机变更】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//Y
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//Y
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));//Y
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));//Y
        realParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));//Y
        realParams.put("mobie", MapUtils.getString(params, "mobie", ""));//Y
        realParams.put("cert_sign", "testsign123456");
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【isic预留手机变更】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic预留手机变更】=======第三方返回：" + result_json);

            if (null == result_json) {
                logger.info("【isic预留手机变更】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【isic预留手机变更】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") ||"".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if ("10".equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if ("11".equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            }
        } catch (Exception e) {
            logger.error("【isic预留手机变更】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicCustomerChange(Map<String, Object> params) {
        logger.info("【isic客户信息变更】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("mobile", MapUtils.getString(params, "mobile", ""));
        realParams.put("email", MapUtils.getString(params, "email", ""));
        realParams.put("id_valid_type", MapUtils.getString(params, "id_valid_type", ""));
        realParams.put("date_from", MapUtils.getString(params, "date_from", ""));
        realParams.put("date_to", MapUtils.getString(params, "date_to", ""));
        realParams.put("date_to", MapUtils.getString(params, "date_to", ""));
        realParams.put("date_birth", MapUtils.getString(params, "date_birth", ""));
        realParams.put("address", MapUtils.getString(params, "address", ""));
        realParams.put("sex", MapUtils.getString(params, "sex", ""));
        realParams.put("cert_sign", "testsign123456");
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【isic客户信息变更】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic客户信息变更】=======第三方返回：" + result_json);

            if (null == result_json ) {
                logger.info("【isic客户信息变更】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【isic客户信息变更】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") ||"".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if ("10".equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if ("11".equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            }
        } catch (Exception e) {
            logger.error("【isic客户信息变更】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicCustomerFreeze(Map<String, Object> params) {
        logger.info("【isic客户冻结】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("client_name",MapUtils.getString(params,"client_name",""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("cert_sign", "testsign123456");
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【isic客户冻结】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic客户冻结】=======第三方返回：" + result_json);

            if (null == result_json ) {
                logger.info("【isic客户冻结】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【isic客户冻结】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") ||"".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if ("10".equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if ("11".equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            }
        } catch (Exception e) {
            logger.error("【isic客户冻结】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicCustomerUnfreeze(Map<String, Object> params) {
        logger.info("【isic客户解冻】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("client_name",MapUtils.getString(params,"client_name",""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("cert_sign", "testsign123456");
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【isic客户解冻】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic客户解冻】=======第三方返回：" + result_json);

            if (null == result_json ) {
                logger.info("【isic客户解冻】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【isic客户解冻】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") ||"".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data =result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if ("10".equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if ("11".equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            }
        } catch (Exception e) {
            logger.error("【isic客户解冻】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> isicCustomerCancel(Map<String, Object> params) {
        logger.info("【isic客户销户】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //Y必填 N选填
        realParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));//Y
        realParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));//Y
        realParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));//Y
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        if(realParams.put("id_kind",MapUtils.getString(params, "id_kind", ""))==null){
            realParams.put("id_kind","0000000");
        }
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));

        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("cert_sign", "testsign123456");
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【isic客户销户】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic客户销户】=======第三方返回：" + result_json);

            if (null == result_json ) {
                logger.info("【isic客户销户】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【isic客户销户】========第三方接口返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null==result_json.get("data") ||"".equals(result_json.get("data"))){
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data =result_json.getJSONObject("data");
            String status = data.get("status").toString();
            if ("10".equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if ("11".equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            }
        } catch (Exception e) {
            logger.error("【isic客户销户】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> epayBankFourInOneQuery(Map<String,Object> params){
        logger.info("【平台真实账务往来明细查询(带附言)】入参参数***：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        try {
            sendParams.put("partner_id",MapUtils.getString(params, "partner_id", ""));
            sendParams.put("channelId",MapUtils.getString(params, "channelId", ""));
            sendParams.put("third_batch_no",MapUtils.getString(params, "third_batch_no", ""));
            sendParams.put("tran_type",MapUtils.getString(params, "tran_type", ""));
            sendParams.put("trandata",MapUtils.getString(params, "trandata", ""));
            sendParams.put("cert_sign",MapUtils.getString(params, "cert_sign", ""));
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            logger.info("【平台真实账务往来明细查询(带附言)】传入param参数值***：" + JSON.toJSON(sendParams));
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【平台真实账务往来明细查询(带附言)】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【平台真实账务往来明细查询(带附言)】===========第三方支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【平台真实账务往来明细查询(带附言)】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (!BusinessMsg.SUCCESS.equals(result_json.get("recode"))) {
                logger.info("【平台真实账务往来明细查询(带附言)】支付响应错误");
                responseMap.put("array_tran_list", "");
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                return responseMap;
            }

            if (null == result_json.get("remsg") || null == result_json.getJSONArray("remsg").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONArray result_list = result_json.getJSONArray("remsg");
            if (result_list == null || result_list.size() == 0) {
                logger.error("【平台真实账务往来明细查询(带附言)】返回array_tran_list数组为空");
                throw new BusinessException(BusinessMsg.UNKNOW_REMOTE_STATUS, BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
            }
            responseMap.put("array_tran_list", result_list);
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("【平台真实账务往来明细查询(带附言)】异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public String getAndSaveCheckFile(Map<String, Object> params, String fileName, String clearDate) {
        logger.info("【获取对账文件】入参：" + JSON.toJSON(params));
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        url = host + url;
        HttpClient httpClient = new HttpClient();
        httpClient.setConnectionTimeout(10 * 60 * 1000);
        PostMethod method = new PostMethod(url);
        String[] result;
        if (null != params) {
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                method.addParameter(key, params.get(key) + "");
            }
        }
        logger.info("==========请求参数为" + params.toString() + "请求地址为：" + url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        try {
            httpClient.executeMethod(method);
            if (method.getStatusCode() == 200) {
                String rs = method.getResponseBodyAsString();
                logger.info("==========请求返回结果为：" + rs);
                net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(rs).getJSONObject("data");
                if (data.size() == 0) {
                    throw new BusinessException("对账文件未生成，下载失败");
                }
                String content = data.getString("content");
                result = content.split("\n");
                List<String> finalResult = Arrays.asList(content.split("\n"));
                SingleThreadPool.getThreadPool().execute(() -> {
                    try {
                        FileUtils.writeLines(new File(fileName), finalResult);
                    } catch (IOException e) {
                        logger.info("写入文件失败:" + e.getMessage());
                    }
                    //上传至FTP
                    String addr = PropertiesUtil.getVal("addr");
                    String username = PropertiesUtil.getVal("username");
                    String password = PropertiesUtil.getVal("password");
                    FtpUtils f = new FtpUtils(addr, 21, username, password);
                    if (f.open()) {
                        logger.info("====================连接FTP成功！！！====================");
                        f.upload(fileName, fileName.substring(fileName.lastIndexOf(File.separator) + 1) + ".txt", "out_clear_files" + File.separator + clearDate);
                        logger.info("====================FTP文件写成功====================");
                    } else {
                        logger.info("====================连接FTP失败！！！====================");
                    }
                });
            } else {
                logger.info("【支付对账文件下载】===========对账文件下载接口调用失败，【" + method.getStatusCode() + "】");
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                        BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：查询对账文件接口错误");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e);
        } catch (ConnectTimeoutException e) {
            throw new BusinessException(BusinessMsg.RUNTIME_EXCEPTION, "下载文件超时");
        } catch (Exception e) {
            throw new BusinessException(e);
        } finally {
            method.releaseConnection(); // 释放连接
        }
        StringBuilder sb = new StringBuilder();
        // ftdm:thirdparty:check:(plat_no):(clear_date):(pay_code)
        sb.append("ftdm:thirdparty:check:").append(params.get("partner_id")).append(":")
                .append(params.get("paycheck_date")).append(":").append(params.get("pay_code"));
        String key = sb.toString();
        CacheUtil.getCache().leftPushAll(key, result);
        return key;
    }

    @Override
    public Map<String, Object> refundPay(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> registerEaccount(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> registerEaccountOld(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> queryRealEaccountBalance(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> modifyMobile(Map<String, Object> params) throws BusinessException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//合作商编号
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//合作商流水，代表本次查询动作
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));//原合作商流水号
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        sendParams.put("channelId", MapUtils.getString(params, "bank_acct_no", ""));
        sendParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));
        sendParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));
        sendParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));
        sendParams.put("card_no",MapUtils.getString(params, "card_no", ""));
        sendParams.put("mobile_tel",MapUtils.getString(params, "mobile", ""));
        sendParams.put("sendercomp_id",MapUtils.getString(params, "sendercomp_id", ""));
        sendParams.put("targetcomp_id",MapUtils.getString(params, "targetcomp_id", ""));
        sendParams.put("original_serial_no",MapUtils.getString(params, "original_serial_no", ""));
        sendParams.put("cert_sign","testsign123456");
        try {
            logger.info("【预留手机号变更】===========请求参数：" + sendParams);
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host +url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【预留手机号变更】===========预留手机号变更接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：预留手机号变更接口调用失败");
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            try {
                //将接口返回的数据转换成对象后返回
                logger.info("【预留手机号变更】===========E支付返回：" + baseHttpResponse.getEntityString());
                JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
                if(result_json == null){
                    logger.info("【预留手机号变更】===========支付接口返回的数据为空");
                    responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：预留手机号变更接口调用支付返回数据为空");
                    responseMap.put("order_status",OrderStatus.FAIL.getCode());
                    return responseMap;
                }
                if(null != result_json.get("error_no")){
                    logger.info("【预留手机号变更】===========支付接口返回错误信息");
                    responseMap.put("recode",result_json.getString("error_no"));
                    responseMap.put("remsg",result_json.getString("error_info"));
                    responseMap.put("order_status",OrderStatus.FAIL.getCode());
                }
                JSONObject data = result_json.getJSONObject("data");
                String status = data.getString("status");
                if(null == data ||".".equals(data)){
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
                if ("10".equals(status)) {
                    //成功
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                } else if ("11".equals(status)) {
                    //失败
                    responseMap.put("recode", BusinessMsg.FAIL);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                } else {
                    //处理中
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                }
            } catch (Exception e) {
                logger.info("【预留手机号变更】===========接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
                logger.error(e);
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            }
        } catch (Exception e) {
            logger.error("【预留手机号变更】===========未知异常" , e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> bindAccountVerify(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> autoAddValue(Map<String, Object> params) throws BusinessException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no",  MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("partner_trans_date",MapUtils.getString(params, "partner_trans_date", ""));
        sendParams.put("partner_trans_time",MapUtils.getString(params, "partner_trans_time", ""));
        sendParams.put("payee_card_no",MapUtils.getString(params, "payee_card_no", ""));//收款方账号
        sendParams.put("payer_card_no",MapUtils.getString(params, "payer_card_no", ""));//付款方账号(存管户)
        sendParams.put("payer_cust_name",MapUtils.getString(params, "payer_cust_name", ""));//付款方开户名
        sendParams.put("occur_balance",MapUtils.getString(params, "occur_balance", ""));
        sendParams.put("channelId",MapUtils.getString(params, "channelId", ""));
        sendParams.put("notify_url","");
        sendParams.put("cert_sign","testsign123456");
        try {
            logger.info("【自动加值】请求参数：" + sendParams);
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host +url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【自动加值】接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",String.format(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+"|http_status:%s|http_message:%s",
                        baseHttpResponse.getStatusCode(),baseHttpResponse.getErrorMessage()));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            try {
                //将接口返回的数据转换成对象后返回
                logger.info("【自动加值】三方支付返回：" + baseHttpResponse.getEntityString());
                JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
                if(result_json == null){
                    logger.info("【自动加值】支付接口返回的数据为空");
                    responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：预留手机号变更接口调用支付返回数据为空");
                    responseMap.put("order_status",OrderStatus.PROCESSING.getCode());
                    return responseMap;
                }
                if(null != result_json.get("error_no")){
                    logger.info(String.format("【自动加值】支付接口返回错误信息|error_no:%s|error_info:%s",
                            result_json.get("error_no"),result_json.get("error_info")));
                    responseMap.put("recode",result_json.getString("error_no"));
                    responseMap.put("remsg",result_json.getString("error_info"));
                    responseMap.put("order_status",OrderStatus.FAIL.getCode());
                    return responseMap;
                }
                JSONObject data = result_json.getJSONObject("data");
                if(null == data || ObjectUtils.equals("",data)){
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
                String status = data.getString("pay_status");
                if ("20".equals(status)) {
                    //成功
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                } else if ("21".equals(status)) {
                    //失败
                    responseMap.put("recode", BusinessMsg.FAIL);
                    responseMap.put("remsg", StringUtils.isBlank(data.getString("fail_msg"))?BusinessMsg.getMsg(BusinessMsg.FAIL):data.getString("fail_msg"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                } else {
                    //处理中
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                }
            } catch (Exception e) {
                logger.info("【自动加值】接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
                logger.error(e);
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            }
        } catch (Exception e) {
            logger.error("【自动加值】未知异常" , e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> queryAutoAddValue(Map<String, Object> params) throws BusinessException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no",  MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("original_serial_no", MapUtils.getString(params, "original_serial_no", ""));
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        sendParams.put("cert_sign","testsign123456");
        try {
            logger.info("【自动加值查询】请求参数：" + sendParams);
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host +url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【自动加值查询】接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",String.format(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR)+"|http_status:%s|http_message:%s",
                        baseHttpResponse.getStatusCode(),baseHttpResponse.getErrorMessage()));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            try {
                //将接口返回的数据转换成对象后返回
                logger.info("【自动加值查询】三方支付返回：" + baseHttpResponse.getEntityString());
                JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
                if(result_json == null){
                    logger.info("【自动加值查询】支付接口返回的数据为空");
                    responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：预留手机号变更接口调用支付返回数据为空");
                    responseMap.put("order_status",OrderStatus.PROCESSING.getCode());
                    return responseMap;
                }
                if(null != result_json.get("error_no")){
                    logger.info(String.format("【自动加值查询】支付接口返回错误信息|error_no:%s|error_info:%s",
                            result_json.get("error_no"),result_json.get("error_info")));
                    responseMap.put("recode",result_json.getString("error_no"));
                    responseMap.put("remsg",result_json.getString("error_info"));
                    responseMap.put("order_status",OrderStatus.FAIL.getCode());
                    return responseMap;
                }
                JSONObject data = result_json.getJSONObject("data");
                if(null == data || ObjectUtils.equals("",data)){
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
                String status = data.getString("pay_status");
                if ("20".equals(status)) {
                    //成功
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                } else if ("21".equals(status)) {
                    //失败
                    responseMap.put("recode", BusinessMsg.FAIL);
                    responseMap.put("remsg", StringUtils.isBlank(data.getString("fail_msg"))?BusinessMsg.getMsg(BusinessMsg.FAIL):data.getString("fail_msg"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                } else {
                    //处理中
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                }
            } catch (Exception e) {
                logger.info("【自动加值查询】接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
                logger.error(e);
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            }
        } catch (Exception e) {
            logger.error("【自动加值查询】未知异常" , e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> batchPayQueryTo(Map<String, Object> params) throws BusinessException {
        return null;
    }


    @Override
    public Map<String, Object> queryContractApp(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> queryContractAppTo(Map<String, Object> params) throws BusinessException {
        return null;
    }



    @Override
    public Map<String, Object> agrPayApply(Map<String, Object> params) {
        logger.info("【协议支付申请】==========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//平台流水
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        sendParams.put("prod_code", MapUtils.getString(params, "prod_code", ""));//产品编号
        sendParams.put("prod_name", MapUtils.getString(params, "prod_name", ""));//产品名称
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));//摘要
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//客户姓名
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));//银行卡号
        sendParams.put("bank_id", MapUtils.getString(params, "bank_id", ""));//银行ID
        sendParams.put("cvv2", MapUtils.getString(params, "cvv2", ""));//cvv2码：信用卡填
        sendParams.put("valid_date", MapUtils.getString(params, "valid_date", ""));//有效日期：信用卡填
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));//证件号
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//手机号
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));//后台回调地址 异步通知地址
        sendParams.put("verify_mode", MapUtils.getString(params, "verify_mode", "0"));//验证模式 1需要短信验证 0不需要
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//支付通道
        sendParams.put("cert_sign", "quick_pay_test");//签名串
        //获取请求URL
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        logger.info("【协议支付申请】请求支付参数：" + JSON.toJSON(sendParams));
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(sendParams, host + url, MapUtils.getString(params, "partner_serial_no", ""));
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【协议支付申请】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【协议支付申请】==========返回：" + result_json);
            if (null == result_json) {
                logger.info("【协议支付申请】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                logger.info("【协议支付申请】 支付组返回错误信息，请求失败");
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONObject("data")) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONObject("data");

            if ("22".equals(data.get("pay_status"))) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：快捷支付请求短信失败,返回pay_status【22】");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            } else  if ("25".equals(data.get("pay_status"))) {
                if (null != data.get("host_req_serial_no")) {
                    responseMap.put("host_req_serial_no", data.get("host_req_serial_no"));
                }
                if (null != data.get("hsepay_order_no")) {
                    responseMap.put("hsepay_order_no", data.get("hsepay_order_no"));
                }
                if (null != data.get("self_bank_flag")) {
                    responseMap.put("self_bank_flag", data.get("self_bank_flag"));
                }
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                return responseMap;
            } else  {
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS) + "：快捷支付请求未知状态,返回pay_status【"+data.get("pay_status")+"】");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
        } catch (Exception e) {
            logger.error("【协议支付申请】===========异常：", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
            return responseMap;
        }
    }

    @Override
    public Map<String, Object> agrPayConfirm(Map<String, Object> params) {
        logger.info("【协议支付确认】===========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));//支付通道
        sendParams.put("terminal_id", MapUtils.getString(params, "terminal_id", "0"));//终端号
        sendParams.put("cert_sign", "quick_pay_test");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");

        //------------------------------请求第三方支付---------------------------------------------
        logger.info("【协议支付确认】=========获取支付响应开始，确认交易流水号：" + MapUtils.getString(params, "partner_serial_no", ""));
        BaseHttpResponse baseHttpResponse = null;
        try {
            try {
                logger.info("===========【协议支付确认】传给支付参数：" + JSON.toJSON(sendParams));
                baseHttpResponse = doPost(sendParams, host + url, "");
            } catch (Exception ex) {
                logger.error("【向支付发送请求异常】", ex);
                if (ex instanceof BusinessException) {
                    BaseResponse baseResponse = ((BusinessException) ex).getBaseResponse();
                    if (baseResponse.getRemsg().equals("连接被拒绝")
                            || baseResponse.getRemsg().equals("请求第三方连接超时")
                            || baseResponse.getRemsg().equals("连接被重置")) {
                        logger.info("【向支付发送请求异常】" + baseResponse.getRemsg());
                        responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                        responseMap.put("remsg", baseResponse.getRemsg());
                        responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    } else {
                        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
                        responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                        responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    }
                }
                return responseMap;
            }
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【协议支付确认】=========请求第三方支付远程未知状态【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【协议支付确认】========支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【协议支付确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no") && !"".equals(result_json.get("error_no"))) {
                logger.info("【协议支付确认】=========存在验证码相关错误：" + result_json.get("error_info"));
                if (CbbRecode.VERIFICATION_ERROR1.getCode().equals(result_json.get("error_no")) || CbbRecode.VERIFICATION_ERROR2.getCode().equals(result_json.get("error_no"))) {
                    //验证码错误  状态不变
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR));
                    responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR);
                } else if (CbbRecode.COMMUNICATION_OVERTIME1.getCode().equals(result_json.get("error_no")) || CbbRecode.COMMUNICATION_OVERTIME3.getCode().equals(result_json.get("error_no")) || CbbRecode.COMMUNICATION_OVERTIME3.getCode().equals(result_json.get("error_no"))) {
                    //验证码超时  交易置为失败
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_OUT_OF_DATE));
                    responseMap.put("recode", BusinessMsg.VERIFICATION_OUT_OF_DATE);
                } else if (CbbRecode.VERIFICATION_ERROR_TOO_MUCH1.getCode().equals(result_json.get("error_no")) || CbbRecode.VERIFICATION_ERROR_TOO_MUCH2.getCode().equals(result_json.get("error_no"))) {
                    //验证码错误次数过多  交易置为失败
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.VERIFICATION_ERROR_TOO_MUCH));
                    responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR_TOO_MUCH);
                } else if (CbbRecode.COMMUNICATION_OVERTIME.getCode().equals(result_json.get("error_no"))) {
                    //支付请求三方通讯超时  交易不变
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.TRANSMISSION_TIMEOUT));
                    responseMap.put("recode", BusinessMsg.TRANSMISSION_TIMEOUT);
                } else if(CbbRecode.ORDER_HAS_PAY.getCode().equals(result_json.get("error_no"))){
                    //支付返回订单已支付,交易不变
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("recode", OrderStatus.PROCESSING.getCode());
                } else{
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("recode", result_json.get("error_no"));
                }
                return responseMap;
            }
            //--------------------------------获取成功返回信息-----------------------------------------------------
            if (result_json.get("data") == null && result_json.getJSONObject("data") == null) {
                logger.info("【协议支付确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONObject("data");
            //获取支付状态
            String payStatus = data.getString("pay_status");
            //如果payStatus==3 则处理成功 转账 记录流水 记录充值信息表
            if (PayStatus.SUCCESS.getCode().equals(payStatus)) {
                logger.info("【协议支付确认】===========第三方支付返回状态交易成功");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (PayStatus.FAIL.getCode().equals(payStatus)) {
                logger.info("【协议支付确认】===========第三方支付返回状态交易失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", data.get("error_no") == null ? BusinessMsg.PAYMENT_FAILED : data.get("error_no"));
                responseMap.put("remsg", data.get("error_info") == null ? BusinessMsg.getMsg(BusinessMsg.PAYMENT_FAILED) : data.get("error_info"));
            } else {
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("recode", result_json.get("error_no"));
            }
            logger.info("【协议支付确认】===========成功请求支付---返回中-------");
            logger.info("【协议支付确认】===========同步响应结果：" + JSONObject.toJSONString(responseMap));
        } catch (Exception e) {
            logger.error("【协议支付确认】ERROR:", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                responseMap.put("remsg", baseResponse.getRemsg());
                responseMap.put("recode", baseResponse.getRecode());
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
            }
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            logger.info("【协议支付确认】===========同步响应结果：" + JSONObject.toJSONString(responseMap));
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> batchCollection(Map<String, Object> params) {
        {
            logger.info("【批量代扣】=========入参参数：" + params);
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap = checkParams(params);
            if (!(boolean) responseMap.get("success")) {
                return responseMap;
            }
            Map<String, Object> sendParams = new HashMap<>();
            sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
            sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
            sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
            sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
            sendParams.put("third_no", MapUtils.getString(params, "third_no", ""));
            sendParams.put("total_num", MapUtils.getString(params, "total_num", ""));
            sendParams.put("total_balance", MapUtils.getString(params, "total_balance", ""));
            sendParams.put("details", MapUtils.getString(params, "details", ""));
            sendParams.put("sendercomp_id", MapUtils.getString(params, "sendercomp_id", ""));
            sendParams.put("targetcomp_id", MapUtils.getString(params, "targetcomp_id", ""));
            sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));
            sendParams.put("cert_sign", "123456789");
            //电子帐户开户接口
            //TODO 设置URL地址
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host + url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【批量代扣第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            responseMap.put("data",JSON.parseObject(baseHttpResponse.getEntityString()));
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            return responseMap;
        }
    }
}
