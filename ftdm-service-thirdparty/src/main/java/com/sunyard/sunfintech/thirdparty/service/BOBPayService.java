package com.sunyard.sunfintech.thirdparty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.*;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import com.sunyard.sunfintech.thirdparty.model.AccountVerifyResponse;
import com.sunyard.sunfintech.thirdparty.model.PlatcustOpenResponse;
import com.sunyard.sunfintech.thirdparty.model.WithholdResponseDetail;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import com.sunyard.sunfintech.thirdparty.utils.BobEpayCode;
import com.sunyard.sunfintech.thirdparty.utils.PropertiesUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * o
 * @author heroy
 * @version 2018/1/2
 */
@CacheConfig(cacheNames="bobPayService")
@org.springframework.stereotype.Service("bobPayService")
public class BOBPayService extends BaserTirdpartyService implements IAdapterService {


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
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        realParams.put("func_code", MapUtils.getString(params, "func_code", ""));
        realParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        realParams.put("pay_bankacct_type", MapUtils.getString(params, "pay_bankacct_type", ""));
        realParams.put("sendercomp_id", MapUtils.getString(params, "sendercomp_id", ""));
        realParams.put("targetcomp_id", MapUtils.getString(params, "targetcomp_id", ""));
        realParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        //=========雅酷必填字段============
        realParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));
        realParams.put("cert_sign", "testsign1111111");

        logger.info("===========【短验绑卡申请】发送给E支付参数：" + JSON.toJSON(realParams));
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
            logger.info("【短验绑卡申请】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【短验绑卡申请】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            String authStatus = data.getString("auth_status");
            if ("0".equals(authStatus)) {
                logger.info("【短验绑卡申请】=========绑卡申请成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("2".equals(authStatus)
                    || "013068".equals(result_json.get("error_no"))) {
                logger.info("【短验绑卡申请】=========绑卡确认成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                if (null != data.get("bank_id")) {
                    responseMap.put("bank_no", data.get("bank_id"));
                }
            } else if ("4".equals(authStatus)) {
                logger.info("【短验绑卡申请】=========绑卡确认失败：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                if (data.get("respMsg").toString().contains("CFCA")) {
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    responseMap.put("remsg", data.get("respMsg"));
                }
            } else if ("5".equals(authStatus)) {
                logger.info("【短验绑卡申请】=========绑卡确认状态未知：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("remsg", "CFCA验证状态未知，绑卡失败！");
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
            }
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
        logger.info("===============调用【绑卡确认参数】入参参数：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        //platPaycode中的partner_id
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

        logger.info("===========【绑卡确认参数】发送给E支付参数：" + JSON.toJSON(realParams));
        try {
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse.getEntityString() || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【绑卡确认参数】===========第三方接口调用失败，【" +
                        baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【绑卡确认参数】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【绑卡确认参数】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
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

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            String authStatus = data.getString("auth_status");
            if ("0".equals(authStatus)) {
                logger.info("【绑卡确认参数】=========绑卡确认成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("2".equals(authStatus)
                    || "013068".equals(result_json.get("error_no"))) {
                logger.info("【绑卡确认参数】=========绑卡确认成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                if (null != data.get("bank_id")) {
                    responseMap.put("bank_no", data.get("bank_id"));
                }
            } else if ("4".equals(authStatus)) {
                logger.info("【绑卡确认参数】=========绑卡确认失败：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                if (data.get("respMsg").toString().contains("CFCA")) {
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    responseMap.put("remsg", data.get("respMsg"));
                }
            } else if ("5".equals(authStatus)) {
                logger.info("【绑卡确认参数】=========绑卡确认状态未知：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("remsg", "CFCA验证状态未知，绑卡失败！");
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
            }
        } catch (Exception e) {
            logger.error("【绑卡确认参数】===========未知异常", e);
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
        logger.info("【信用卡绑卡】=========入参参数：" + params);
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
        realParams.put("cert_sign", "testsign1111111");

        logger.info("===========【信用卡绑卡】发送E支付参数：" + JSON.toJSON(realParams));
        try {
            //查询支付url ，需要realParams 传入mall_no  key
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (null == baseHttpResponse.getEntityString() || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【信用卡绑卡】===========第三方接口调用失败，【" +
                        baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【信用卡绑卡】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【信用卡绑卡】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
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

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            String authStatus = data.getString("auth_status");
            if ("0".equals(authStatus)) {
                logger.info("【信用卡绑卡】=========绑卡申请成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("2".equals(authStatus)
                    || "013068".equals(result_json.get("error_no"))) {
                logger.info("【信用卡绑卡】=========绑卡确认成功");
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                if (null != data.get("bank_id")) {
                    responseMap.put("bank_no", data.get("bank_id"));
                }
            } else if ("4".equals(authStatus)) {
                logger.info("【信用卡绑卡】=========绑卡确认失败：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                if (data.get("respMsg").toString().contains("CFCA")) {
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CFCA_AUTH_FAILED));
                } else {
                    responseMap.put("remsg", data.get("respMsg"));
                }
            } else if ("5".equals(authStatus)) {
                logger.info("【信用卡绑卡】=========绑卡确认状态未知：" + data.get("respMsg"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("remsg", "CFCA验证状态未知，绑卡失败！");
                responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
            }
        } catch (Exception e) {
            logger.error("【信用卡绑卡】===========未知异常", e);
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
            logger.info("【实名认证】=========E支付返回：" + result_json);

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

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            String authStatus = data.getString("pay_status");
            if ("2".equals(authStatus)) {
                logger.info("【实名认证】=========认证失败：" + result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("recode", result_json.get("error_no"));
            } else {
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
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

    private Map<String, Object> sendQuickPayMsg(Map<String, Object> params) throws BusinessException {
        logger.info("【快捷短验申请接口调用】===========入参参数" + params);
        Map<String, Object> responseMap = new HashMap<>();
        params.put("cert_sign", "testsign");
        String url = MapUtils.getString(params, "url_msgRequest", "");
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(params, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【快捷短验申请接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject msgRequest_result = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【快捷短验申请接口调用】========E支付返回：" + msgRequest_result);
            if (null == msgRequest_result) {
                logger.info("【快捷短验申请接口调用】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != msgRequest_result.get("error_no")) {
                logger.info("【快捷短验申请接口调用】========短信发送失败！");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                //responseMap.put("success", false);
                responseMap.put("recode", msgRequest_result.getString("error_no"));
                responseMap.put("remsg", msgRequest_result.getString("error_info"));
            } else {
                if (null == msgRequest_result.get("data") || null == msgRequest_result.getJSONArray("data").getJSONObject(0)) {
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                } else {
                    //responseMap.put("success", true);
                    responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                }
            }
        } catch (Exception e) {
            logger.error("【短验申请接口调用】===========异常", e);
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
        logger.info("【快捷充值申请】===========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
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
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额

        sendParams.put("terminal_id",MapUtils.getString(params, "terminal_id", "0"));
        sendParams.put("terminal_type",MapUtils.getString(params, "terminal_type", "1"));
        sendParams.put("partner_userid",MapUtils.getString(params, "partner_userid", "01"));
        sendParams.put("prod_name",MapUtils.getString(params, "prod_name", "快捷充值"));
        sendParams.put("registtime",MapUtils.getString(params, "registtime", DateUtils.today("yyyy-MM-dd HH:mm:ss").toString()));
        sendParams.put("lastloginterminalid",MapUtils.getString(params, "lastloginterminalid", "192.0.0.168"));
        sendParams.put("issetpaypwd",MapUtils.getString(params, "issetpaypwd", "0"));
        //获取请求URL
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        logger.info("===========【快捷充值申请】请求支付参数：" + JSON.toJSON(sendParams));
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
            logger.info("【快捷充值申请】========返回：" + result_json);
            if (null == result_json) {
                logger.info("【快捷充值申请】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                logger.info("*****【快捷充值申请】 云融惠付返回错误信息，请求失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            if (null != data.get("host_req_serial_no")) {
                responseMap.put("host_req_serial_no", data.get("host_req_serial_no"));
            }
            if (null != data.get("hsepay_order_no")) {
                responseMap.put("hsepay_order_no", data.get("hsepay_order_no"));
            }
            if (null != data.get("self_bank_flag")) {
                responseMap.put("self_bank_flag", data.get("self_bank_flag"));
            }

            if (!"".equals(MapUtils.getString(params, "url_msgRequest", ""))) {
                logger.info("【快捷充值申请】==========支付公司收单，调用支付公司快捷支付短验申请。");
                Map<String, Object> sendMsgParams = new HashMap<>();
                sendMsgParams.put("host", MapUtils.getString(params, "host", ""));
                sendMsgParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
                sendMsgParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
                sendMsgParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
                sendMsgParams.put("url_msgRequest", MapUtils.getString(params, "url_msgRequest", "")); //支付发送短信接口
                logger.info("【快捷充值申请】支付收单，发送短信申请，请求参数："+JSON.toJSON(sendMsgParams));
                Map<String, Object> res = sendQuickPayMsg(sendMsgParams);
                logger.info("【快捷充值申请】支付收单，短信申请结果："+JSON.toJSON(res));
                if (!OrderStatus.SUCCESS.getCode().equals(res.get("order_status"))) {
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("recode", res.get("recode"));
                    responseMap.put("remsg", res.get("remsg"));
                    return responseMap;
                }
            }
            responseMap.put("query_id", MapUtils.getString(params, "partner_serial_no", ""));
            responseMap.put("process_date", DateUtils.todayfulldata());
            responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
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
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> confirmyQuickPay(Map<String, Object> params) {
        logger.info("【快捷充值确认】=========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        sendParams.put("cert_sign", "quick_pay_test");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");

        //------------------------------请求第三方支付（云融惠付）---------------------------------------------
        logger.info("【快捷充值确认】=========获取支付响应开始，确认交易流水号：" + MapUtils.getString(params, "partner_serial_no", ""));
        BaseHttpResponse baseHttpResponse = null;
        try {
            try {
                logger.info("===========【快捷充值确认】传给E支付参数：" + JSON.toJSON(sendParams));
                baseHttpResponse = doPost(sendParams, host + url, "");
            } catch (Exception ex) {
                logger.error("【向E支付发送请求异常】", ex);
                if (ex instanceof BusinessException) {
                    BaseResponse baseResponse = ((BusinessException) ex).getBaseResponse();
                    if (baseResponse.getRemsg().equals("连接被拒绝")
                            || baseResponse.getRemsg().equals("请求第三方连接超时")
                            || baseResponse.getRemsg().equals("连接被重置")) {
                        logger.info("【向E支付发送请求异常】" + baseResponse.getRemsg());
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
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用结果未明");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【快捷充值确认】========E支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【快捷充值确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no") && !"".equals(result_json.get("error_no"))) {
                //其他异常时
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                for(Map.Entry<String,String> entry: BobEpayCode.BobEpayCode.entrySet()){
                    if(entry.getKey().equals(result_json.get("error_no"))){
                        responseMap.put("order_status", entry.getValue());
                        responseMap.put("remsg",result_json.get("error_info"));
                        responseMap.put("recode", result_json.get("error_no"));
                        break;
                    }
                }
                return responseMap;
            }
            //--------------------------------获取成功返回信息-----------------------------------------------------
            if (result_json.get("data") == null && result_json.getJSONArray("data").getJSONObject(0) == null) {
                logger.info("【快捷充值确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            //添加支付账期返回
            if(null!=data.get("pay_finish_date") && !"".equals(data.get("pay_finish_date"))){
                responseMap.put("pay_finish_date",data.get("pay_finish_date"));
            }

            //添加支付账期返回
            if(null!=data.get("self_bank_flag") && !"".equals(data.get("self_bank_flag"))){
                responseMap.put("self_bank_flag",data.get("self_bank_flag"));
            }

            //获取支付状态
            String payStatus = data.getString("pay_status");
            // responseMap.put("is_bank_return","0");
            //如果payStatus==3 则处理成功 转账 记录流水 记录充值信息表
            if (EpayStatus.SUCCESS.getCode().equals(payStatus)) {
                logger.info("【快捷充值确认】===========第三方支付返回状态交易成功");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (EpayStatus.PAYING.getCode().equals(payStatus)) {
                //更新充值信息
                logger.info("【快捷充值确认】===========第三方支付返回状态处理中");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
            } else if (EpayStatus.FAIL.getCode().equals(payStatus)) {
                logger.info("【快捷充值确认】===========第三方支付返回状态交易失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", data.get("respCode") == null ? BusinessMsg.PAYMENT_FAILED : data.get("respCode"));
                responseMap.put("remsg", data.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.PAYMENT_FAILED) : data.get("respMsg"));
            } else {
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", data.get("respCode") == null ? BusinessMsg.UNKNOW_ERROE : data.get("respCode"));
                responseMap.put("remsg", data.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE) : data.get("respMsg"));
            }
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
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//客户手机
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
            logger.info("【网关支付请求】========E支付返回：" + result_json);
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
            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
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

            if(null!=data.get("pay_finish_date") && !"".equals(data.get("pay_finish_date"))){
                responseMap.put("pay_finish_date",data.get("pay_finish_date"));
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

    @Override
    public Map<String, Object> payFromCompanyToUser(Map<String, Object> params) {
        return null;
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
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【代扣充值】========E支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
/*            if (null != result_json.get("error_no")) {
                if (BusinessMsg.TRANSMISSION_TIMEOUT.equals(result_json.getString("error_no"))) {
                    //交易处理中
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                } else {
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                }
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", BusinessMsg.getMsg(result_json.getString("error_no")));
                return responseMap;
            }*/

            if (null != result_json.get("error_no") && !"".equals(result_json.get("error_no"))) {
                //其他异常时
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                for(Map.Entry<String,String> entry: BobEpayCode.BobEpayCode.entrySet()){
                    if(entry.getKey().equals(result_json.get("error_no"))){
                        responseMap.put("order_status", entry.getValue());
                        responseMap.put("remsg",result_json.get("error_info"));
                        responseMap.put("recode", result_json.get("error_no"));
                        break;
                    }
                }
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            //添加支付账期
            if(null!=data.get("pay_finish_date") && !"".equals(data.get("pay_finish_date"))){
                responseMap.put("pay_finish_date",data.get("pay_finish_date"));
            }
            String pay_status = data.getString("pay_status");
            responseMap.put("self_bank_flag",data.get("self_bank_flag")==null?null:data.getString("self_bank_flag"));
            if ("2".equals(pay_status)) {
                //交易处理中
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【处理中】");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("3".equals(pay_status)) {
                //交易成功
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【交易成功】");
                //返回订单状态
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("7".equals(pay_status)) {
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
            } else {
                logger.info("【代扣充值】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【E支付返回未知状态>>：】" + pay_status);
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", "支付返回未知状态>>：" + pay_status);
            }
        } catch (Exception e) {
            logger.error("【代扣充值】异常", e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                if (baseResponse.getRemsg().equals("连接被拒绝")
                        || baseResponse.getRemsg().equals("请求第三方连接超时")
                        || baseResponse.getRemsg().equals("连接被重置")) {
                    logger.info("【向E支付发送请求异常】" + baseResponse.getRemsg());
                    responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                    responseMap.put("remsg", baseResponse.getRemsg());
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                } else {
                    baseResponse = ((BusinessException) e).getBaseResponse();
                    responseMap.put("remsg", baseResponse.getRemsg());
                    responseMap.put("recode", baseResponse.getRecode());
                    responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                }
            } else {
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
                responseMap.put("recode", BusinessMsg.RUNTIME_EXCEPTION);
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            }
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> withdrawInBatch(Map<String, Object> params) {
        logger.info("【批量提现】-->【向E支付发送提现请求】-->传入参数:" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));//合作商编号
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));//合作商交易流水号
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));//合作商交易日期
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));//合作商交易时间
        sendParams.put("partner_child_id", MapUtils.getString(params, "partner_child_id", ""));//子商户编号
        sendParams.put("partner_child_type", MapUtils.getString(params, "partner_child_type", ""));//子商户类型
        sendParams.put("channel_id", MapUtils.getString(params, "channel_id", ""));//渠道编号
        sendParams.put("df_flag", MapUtils.getString(params, "df_flag", ""));//是否垫资标志
        sendParams.put("df_amount", MapUtils.getString(params, "df_amount", ""));//垫资金额
        sendParams.put("payer_name", MapUtils.getString(params, "payer_name", ""));//付款姓名
        sendParams.put("payer_no", MapUtils.getString(params, "payer_no", ""));//付款账号
        sendParams.put("is_bill_partner", MapUtils.getString(params, "is_bill_partner", ""));//是否收单商户
        sendParams.put("currency_code", MapUtils.getString(params, "currency_code", ""));//币种
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));//摘要
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//账户姓名
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));//银行卡号
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));//证件类型
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));//证件号码
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//电话号码
        sendParams.put("open_province", MapUtils.getString(params, "open_province", ""));//开户行省份名称/编码
        sendParams.put("open_city_no", MapUtils.getString(params, "open_city_no", ""));//开户行城市名称/编码
        sendParams.put("open_bank_no", MapUtils.getString(params, "open_bank_no", ""));//开户行联行号（支行）
        sendParams.put("bank_id", MapUtils.getString(params, "bank_id", ""));//开户行号（总行）
        sendParams.put("withdraw_type", MapUtils.getString(params, "withdraw_type", ""));//提现方式
        sendParams.put("client_property", MapUtils.getString(params, "client_property", ""));//公私标志
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));//后台回调地址
        sendParams.put("seller_account", MapUtils.getString(params, "seller_account", ""));//收方账号
        sendParams.put("hsepay_prod_type", MapUtils.getString(params, "hsepay_prod_type", ""));//产品类型
        sendParams.put("prod_code", MapUtils.getString(params, "prod_code", ""));//产品编号
        sendParams.put("prod_name", MapUtils.getString(params, "prod_name", ""));//产品名称
        sendParams.put("subbank_name", MapUtils.getString(params, "subbank_name", ""));//分行名称
        sendParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));//商户用户编号
        sendParams.put("p2p_fee_mode", MapUtils.getString(params, "p2p_fee_mode", ""));//收费方式
        sendParams.put("prcptcd", MapUtils.getString(params, "prcptcd", ""));//大额行号
        sendParams.put("cert_sign", "SUNYARD");//签名串
        sendParams.put("account_type", MapUtils.getString(params, "account_type", ""));//卡类型   1代表信用卡

        logger.info("【批量提现】-->【向E支付发送提现请求】-->开始代付");

        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            long start = System.currentTimeMillis();
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host + url, null);
            long end = System.currentTimeMillis();
            logger.info("【批量提现】-->【向E支付发送提现请求】-->返回时间：" + (end - start) + "毫秒");
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【批量提现】-->【向E支付发送提现请求】-->第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口调用失败");
                responseMap.put("pay_status", PayStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            if (null != result_json.get("error_no")) {
                logger.info("【批量提现】-->【向E支付发送提现请求】-->同步返回异常");
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("pay_status", PayStatus.FAIL.getCode());
                return responseMap;
            }
            logger.info("【批量提现】-->【向E支付发送提现请求】-->【提现返回】:" + result_json);
            if (null == result_json || null == result_json.getJSONArray("data").getJSONObject(0)) {
                logger.info("【批量提现】-->【向E支付发送提现请求】-->【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口返回值解析失败");
                responseMap.put("pay_status", PayStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            logger.info("【批量提现】-->【向E支付发送提现请求】-->" + data);
            if ("2".equals(data.get("pay_status"))) {
                logger.info("【批量提现】-->【向E支付发送提现请求】-->处理中");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
                responseMap.put("payment_status",PayStatus.PAYMENTPROCESSING.getCode());
                return responseMap;
            }
            if ("6".equals(data.get("pay_status"))) {
                logger.info("【批量提现】-->【向E支付发送提现请求】-->失败未扣款,需要回滚转账");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("pay_status", PayStatus.FAIL.getCode());
                responseMap.put("payment_status",PayStatus.PAYMENTFAILNO.getCode());
                return responseMap;
            }
        } catch (Exception e) {
            logger.error("【批量提现】-->【向E支付发送提现请求】-->代付异常", e);
            if (e instanceof BusinessException) {
                BaseResponse baseResponse = ((BusinessException) e).getBaseResponse();
                if (baseResponse.getRemsg().equals("连接被拒绝")
                        || baseResponse.getRemsg().equals("请求第三方连接超时")
                        || baseResponse.getRemsg().equals("连接被重置")) {
                    logger.info("【批量提现】-->" + baseResponse.getRemsg());
                    responseMap.put("recode", baseResponse.getRecode());
                    responseMap.put("remsg", baseResponse.getRemsg());
                    responseMap.put("pay_status", PayStatus.FAIL.getCode());
                    return responseMap;
                } else {
                    logger.info("【批量提现】-->【向E支付发送提现请求】-->" + baseResponse.getRemsg());
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("pay_status", PayStatus.PAYING.getCode());
                    return responseMap;
                }
            } else {
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS) + ",未知异常");
                return responseMap;
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
        logger.info("【批量提现查询订单状态】-->开始代付发起查询-->order_no:"+params.get("order_no"));

        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            long start = System.currentTimeMillis();
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host + url, null);
            long end = System.currentTimeMillis();
            logger.info("【批量提现查询订单状态】-->返回时间：" + (end - start) + "毫秒-->order_no:"+params.get("order_no"));
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【批量提现查询订单状态】-->第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage()+"-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口调用失败");
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());

            logger.info("【批量提现查询订单状态】-->【提现返回】:" + result_json+"-->order_no:"+params.get("order_no"));
            if (null == result_json) {
                logger.info("【批量提现查询订单状态】【第三方接口返回值解析失败】-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口返回值解析失败");
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
                return responseMap;
            }

            if("BOBSP10005".equals(result_json.get("error_no"))){
                logger.info("【批量提现查询订单状态】-->无此原交易当失败处理-->order_no:"+params.get("order_no"));
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg",  "【query】"+result_json.get("error_info"));
                responseMap.put("pay_status", PayStatus.FAIL.getCode());
                return responseMap;
            }
            if (result_json.get("error_no") != null) {
                logger.info("【批量提现查询订单状态】-->同步返回异常-->order_no:"+params.get("order_no"));
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg",  "【query】"+result_json.get("error_info"));
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
                return responseMap;
            }

            if (null == result_json || null == result_json.getJSONArray("data").getJSONObject(0)) {
                logger.info("【批量提现查询订单状态】【第三方接口返回值解析失败】-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + ",第三方接口返回值解析失败");
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            logger.info("【批量提现查询订单状态】-->【E支付代付同步返回】" + data+"-->order_no:"+params.get("order_no"));
            if ("2".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->处理中-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
                return responseMap;
            }
            if ("3".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->处理成功-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("pay_status", PayStatus.PAYMENTSUCCESS.getCode());
                responseMap.put("pay_finish_date", data.get("pay_finish_date"));
                return responseMap;
            }
            if ("6".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->失败未扣款,需要回滚转账-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", "【query】"+data.get("fail_cause"));
                responseMap.put("pay_status", PayStatus.PAYMENTFAILNO.getCode());
                return responseMap;
            }
            if ("7".equals(data.get("pay_status"))) {
                logger.info("【批量提现查询订单状态】-->失败已扣款,需要退款-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", "【query】"+data.get("fail_cause"));
                responseMap.put("pay_status", PayStatus.PAYMENTFAILYES.getCode());
                return responseMap;
            }

        } catch (Exception e) {
            logger.error("【批量提现查询订单状态】-->【向E支付发送提现请求】-->代付异常-->order_no:"+params.get("order_no"), e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
                if (baseResponse.getRemsg().equals("连接被拒绝")
                        || baseResponse.getRemsg().equals("请求第三方连接超时")
                        || baseResponse.getRemsg().equals("连接被重置")) {
                    logger.info("【批量提现查询订单状态】-->" + baseResponse.getRemsg()+"-->order_no:"+params.get("order_no"));
                    responseMap.put("recode", baseResponse.getRecode());
                    responseMap.put("remsg",  "【query】"+baseResponse.getRemsg());
                    responseMap.put("pay_status", PayStatus.PAYING.getCode());
                    return responseMap;
                } else {
                    logger.info("【批量提现查询订单状态】-->" + baseResponse.getRemsg()+"-->order_no:"+params.get("order_no"));
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("pay_status", PayStatus.PAYING.getCode());
                    return responseMap;
                }
            } else {
                logger.info("【批量提现查询订单状态】未知异常-->order_no:"+params.get("order_no"));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg",  "【query】"+BusinessMsg.getMsg(BusinessMsg.SUCCESS) + ",未知异常");
                responseMap.put("pay_status", PayStatus.PAYING.getCode());
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
        //行内划转接口一定是北京银行本行卡
        responseMap.put("self_bank_flag","0");
        Map<String, Object> sendParams = new HashMap<String, Object>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        sendParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));
        sendParams.put("summary", MapUtils.getString(params, "summary", ""));
        sendParams.put("bank_id", MapUtils.getString(params, "bank_id", ""));
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));//入金一方卡号
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));//入金一方户名
        sendParams.put("dr_acct", MapUtils.getString(params, "dr_acct", ""));//出金一方卡号
        sendParams.put("dr_acct_name", MapUtils.getString(params, "dr_acct_name", ""));//出金一方户名
        sendParams.put("receive_url", MapUtils.getString(params, "receive_url", ""));
        sendParams.put("client_property", MapUtils.getString(params, "client_property", ""));//公私标识,0-个人，1-公司
        sendParams.put("remark", MapUtils.getString(params, "remark", ""));
        sendParams.put("prcptcd", MapUtils.getString(params, "prcptcd", ""));//大额行号
        sendParams.put("dr_acct_type", MapUtils.getString(params, "dr_acct_type", ""));//出金一方账户类型,0-借记卡,1-贷记卡,2-存折,3-对公户
        sendParams.put("requestTime", new Date());//记录请求开始时间
        sendParams.put("bank_no", MapUtils.getString(params, "bank_no", ""));
        sendParams.put("cert_sign", "testsign");
        try {
            //调E支付的
            logger.info("======行内划转请求的数据:eMap>> " + JSON.toJSON(sendParams));
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【行内划转第三方接口调用】===========第三方接口调用失败，【" +
                        (baseHttpResponse==null?null:baseHttpResponse.getStatusCode()) + "】" +
                        (baseHttpResponse==null?null:baseHttpResponse.getErrorMessage()));
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【行内划转】========E支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【【行内划转】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                responseMap.put("order_status", BobEpayCode.BobEpayCode.get(result_json.get("error_no").toString())==null?
                        OrderStatus.FAIL.getCode():BobEpayCode.BobEpayCode.get(result_json.get("error_no").toString()));
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            String pay_status = data.getString("pay_status");
            if ("2".equals(pay_status)) {//处理中
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("3".equals(pay_status)) {//交易成功
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            } else if ("7".equals(pay_status)) {//交易失败
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", data.get("error_no"));
                responseMap.put("remsg", data.get("error_info"));
            } else {
                logger.info("【行内划转】未知状态：" + pay_status);
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }
            responseMap.put("sign", sendParams.get("card_no").toString());
            if (null != data.get("host_req_serial_no")) {
                responseMap.put("host_req_serial_no", data.get("host_req_serial_no"));
            }
            if (null != data.get("pay_finish_date")) {
                responseMap.put("pay_finish_date", data.get("pay_finish_date"));
            }
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
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> queryAccountOfCompany(Map<String, Object> params) {
        logger.info("***【E支付平台对公账户查询】入参参数***：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        try {
            sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
            sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
            sendParams.put("client_property", MapUtils.getString(params, "client_property", ""));
            sendParams.put("bank_acct_no", MapUtils.getString(params, "bank_acct_no", ""));
            sendParams.put("cert_sign", "testsign");
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            logger.info("***【E支付平台对公账户查询】传入param参数值***：" + JSON.toJSON(sendParams));
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("E支付平台对公账户查询】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【E支付平台对公账户查询】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【E支付平台对公账户查询】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            responseMap.put("open_bank", data.get("open_bank"));
            responseMap.put("client_name", data.get("client_name"));
            responseMap.put("real_time_balance", data.get("real_time_balance"));
            responseMap.put("today_balance", data.get("today_balance"));
            responseMap.put("yesterday_balance", data.get("yesterday_balance"));
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("【E支付平台对公账户查询】异常", e);
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
        logger.info("***【E支付账户往来明细查询】入参参数***：" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
        try {
            sendParams.put("partner_id",MapUtils.getString(params, "partner_id", ""));
            //合作商流水号为空，入账结果不确定
            sendParams.put("partner_serial_no",MapUtils.getString(params, "partner_serial_no", ""));
            sendParams.put("date_from",MapUtils.getString(params, "date_from", ""));
            sendParams.put("date_to",MapUtils.getString(params, "date_to", ""));
            sendParams.put("acct_no",MapUtils.getString(params, "acct_no", ""));
            sendParams.put("flag_dc",MapUtils.getString(params, "flag_dc", ""));//借贷标志【D：借 C:贷 A:全部】
            sendParams.put("cert_sign", "testsign");
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            logger.info("***【E支付账户往来明细查询】传入param参数值***：" + JSON.toJSON(sendParams));
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【E支付账户往来明细查询】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【E支付账户往来明细查询】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【E支付账户往来明细查询】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null != result_json.get("error_no")) {
                logger.info("【E支付账户往来明细查询】支付响应错误");
                responseMap.put("array_tran_list", "");
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            JSONArray result_list = data.getJSONArray("array_tran_list");
            if (result_list == null || result_list.size() == 0) {
                logger.error("***返回array_tran_list数组为空***");
                throw new BusinessException(BusinessMsg.UNKNOW_REMOTE_STATUS, BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
            }
            responseMap.put("array_tran_list", result_list);
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        } catch (Exception e) {
            logger.error("【E支付账户往来明细查询】异常", e);
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
            sendParams.put("io_flag",MapUtils.getString(params, "io_flag", ""));
            sendParams.put("date_flag",MapUtils.getString(params, "date_flag", ""));
            sendParams.put("date_from",MapUtils.getString(params, "date_from", ""));
            sendParams.put("date_to",MapUtils.getString(params, "date_to", ""));
            sendParams.put("acct_no",MapUtils.getString(params, "acct_no", ""));
            sendParams.put("currency_code",MapUtils.getString(params, "currency_code", ""));
            sendParams.put("cert_sign", "testsign");
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

            if (null != result_json.get("error_no")) {
                logger.info("【平台真实账务往来明细查询(带附言)】支付响应错误");
                responseMap.put("array_tran_list", "");
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            JSONArray result_list = data.getJSONArray("account_details");
            if (result_list == null || result_list.size() == 0) {
                logger.error("【平台真实账务往来明细查询(带附言)】返回account_details数组为空");
                throw new BusinessException(BusinessMsg.UNKNOW_REMOTE_STATUS, BusinessMsg.getMsg(BusinessMsg.UNKNOW_REMOTE_STATUS));
            }
            responseMap.put("account_details", result_list);
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
                logger.info("【E支付对账文件下载】===========对账文件下载接口调用失败，【" + method.getStatusCode() + "】");
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

    /**
     * 行内退款接口
     * @param params
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> refundPay(Map<String, Object> params) throws BusinessException {
        logger.info("【退款】-->order_no：" + params.get("order_no"));
        Map<String, Object> responseMap;
        try {
            responseMap = checkParams(params);
            if (!(boolean) responseMap.get("success")) {
                return responseMap;
            }
            Map<String, Object> sendParams = new HashMap<>();
            sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
            sendParams.put("original_serial_no", MapUtils.getString(params, "original_serial_no", ""));
            sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));
            sendParams.put("cert_sign", MapUtils.getString(params, "cert_sign", ""));
            String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;
            BaseHttpResponse baseHttpResponse = doPost(sendParams, url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【退款】-->退款接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage()+"-->order_no：" + params.get("order_no"));
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：退款接口调用失败");
                throw new BusinessException(baseResponse);
            } else {
                //将接口返回的数据转换成对象后返回
                JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
                logger.info("【退款】-->E支付返回：" + result_json+"-->order_no：" + params.get("order_no"));
                if (null == result_json) {
                    logger.info("【退款】-->【" + MapUtils.getString(params, "original_serial_no", "") + "】【第三方接口返回值解析失败】-->order_no：" + params.get("order_no"));
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }

                if (null != result_json.get("error_no")) {
                    logger.info("【退款】-->支付响应错误-->order_no：" + params.get("order_no"));
                    responseMap.put("data", "");
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    return responseMap;
                }

                if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    return responseMap;
                }
                JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
                responseMap.put("data", data);
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            }
        } catch (Exception e) {
            logger.error("【退款】-->未知异常-->order_no：" + params.get("order_no") + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> registerEaccount(Map<String, Object> params) throws BusinessException {
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
        sendParams.put("bank_acct_no", MapUtils.getString(params, "bank_acct_no", ""));
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        sendParams.put("mobile", MapUtils.getString(params, "mobile", ""));
        sendParams.put("password", MapUtils.getString(params, "password", ""));
        sendParams.put("random_key", MapUtils.getString(params, "random_key", ""));
        sendParams.put("terminal_type", MapUtils.getString(params, "terminal_type", ""));
        sendParams.put("address", MapUtils.getString(params, "address", "北京银行杭州分行"));
        sendParams.put("cert_sign", "sunyard");
        sendParams.put("type_chan",MapUtils.getString(params, "type_chan", ""));
        sendParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));
        sendParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));
        sendParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));
        sendParams.put("cus_type",MapUtils.getString(params, "cus_type", ""));
        sendParams.put("open_account",MapUtils.getString(params, "open_account", ""));
        sendParams.put("is_bankcheck",MapUtils.getString(params, "is_bankcheck", ""));
        sendParams.put("func_code",MapUtils.getString(params, "func_code", ""));
        //开始调用电子账户开户接口并解析
        try {
            logger.info("【电子账户开户接口调用】===========请求参数：" + sendParams);
            //调用电子帐户开户接口
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host +url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【电子账户开户接口调用】===========电子账户开户接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：电子账户开户接口调用失败");
                throw new BusinessException(baseResponse);
            }
            try {
                //将接口返回的数据转换成对象后返回
                logger.info("【电子账户开户接口调用】===========E支付返回：" + baseHttpResponse.getEntityString());
                JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
                if(result_json!=null && IcisAuthStatus.Success.getCode().equals(result_json.getString("status"))){
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
                    responseMap.put("bank_elec_no",result_json.getString("bank_elec_no"));
                }else{
                    if(result_json!=null){
                        if(StringUtils.isBlank(result_json.getString("error_no"))){
                            responseMap.put("recode", BusinessMsg.FAIL);
                        }else{
                            responseMap.put("recode", result_json.getString("error_no"));
                        }
                        if(StringUtils.isBlank(result_json.getString("error_info"))){
                            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL));
                        }else{
                            responseMap.put("remsg",result_json.getString("error_info"));
                        }
                        responseMap.put("order_status",OrderStatus.FAIL.getCode());
                    }
                }
            } catch (Exception e) {
                logger.info("【电子帐户开户接口调用】===========接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
                logger.error(e);
                throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
            }
        } catch (Exception e) {
            logger.error("【电子账户开户接口调用】===========未知异常" , e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            responseMap.put("recode", baseResponse.getRecode());
            responseMap.put("remsg", baseResponse.getRemsg());
            responseMap.put("order_status",OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> registerEaccountOld(Map<String, Object> params) throws BusinessException {
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
        sendParams.put("bank_acct_no", MapUtils.getString(params, "bank_acct_no", ""));
        sendParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        sendParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        sendParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        sendParams.put("terminal_type", MapUtils.getString(params, "terminal_type", ""));
        sendParams.put("type_chan",MapUtils.getString(params, "type_chan", ""));
        sendParams.put("bank_acct_pwd",MapUtils.getString(params, "bank_acct_pwd", ""));
        sendParams.put("address", MapUtils.getString(params, "address", "北京银行杭州分行"));
        sendParams.put("cert_sign", "sunyard");
        //开始调用电子账户开户接口并解析
        try {
            //将对象bean-->map
            logger.info("【电子账户开户接口调用】===========请求参数：" + sendParams);
			//logger.info("【电子账户开户接口调用】===========请求参数：" + params);
            //调用电子帐户开户接口
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host +url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【电子账户开户接口调用】===========电子账户开户接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：电子账户开户接口调用失败");
                throw new BusinessException(baseResponse);
            }
            try {
                //将接口返回的数据转换成对象后返回
                logger.info("【电子账户开户接口调用】===========E支付返回：" + baseHttpResponse.getEntityString());
                PlatcustOpenResponse platcustOpenResponse=JSON.parseObject(baseHttpResponse.getEntityString(), PlatcustOpenResponse.class);
                if(platcustOpenResponse.getData()!=null && StringUtils.isNotBlank(platcustOpenResponse.getData().get(0).getBank_elec_no())) {
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
                    responseMap.put("bank_elec_no",platcustOpenResponse.getData().get(0).getBank_elec_no());
                }else if(platcustOpenResponse.getData()==null){
                    responseMap.put("recode", platcustOpenResponse.getError_code());
                    responseMap.put("remsg", platcustOpenResponse.getError_info());
                    responseMap.put("order_status",OrderStatus.FAIL.getCode());
                }else{
                    responseMap.put("recode", BusinessMsg.FAIL);
                    responseMap.put("remsg", "支付未返回电子账户号，无法开户");
                    responseMap.put("order_status",OrderStatus.FAIL.getCode());
                }
            } catch (Exception e) {
                logger.info("【电子帐户开户接口调用】===========接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
                logger.error(e);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：电子账户开户接口返回的数据无法解析");
                throw new BusinessException(baseResponse);
            }
        } catch (Exception e) {
            logger.error("【电子账户开户接口调用】===========未知异常" , e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            responseMap.put("recode", baseResponse.getRecode());
            responseMap.put("remsg", baseResponse.getRemsg());
            responseMap.put("order_status",OrderStatus.FAIL.getCode());
        }

        return responseMap;
    }

    @Override
    public Map<String, Object> queryRealEaccountBalance(Map<String, Object> params) throws BusinessException {
        logger.info("【真实电子账户余额查询】:" + JSON.toJSON(params));
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
        sendParams.put("bank_acct_no", MapUtils.getString(params, "bank_acct_no", ""));//原合作商流水号
        sendParams.put("cert_sign", "sunyard");

        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(sendParams, host + url, null);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("msg",baseHttpResponse.getEntityString());
            return map;
        }catch (Exception e){
            logger.info("【真实电子账户余额查询】异常:" + JSON.toJSON(params));
        }
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
        sendParams.put("channelId", MapUtils.getString(params, "channelId", ""));
        sendParams.put("mall_no", MapUtils.getString(params, "mall_no", ""));
        sendParams.put("mer_no", MapUtils.getString(params, "mer_no", ""));
        sendParams.put("plat_cust", MapUtils.getString(params, "plat_cust", ""));
        sendParams.put("card_no",MapUtils.getString(params, "card_no", ""));
        sendParams.put("mobile",MapUtils.getString(params, "mobile", ""));
        //开始调用电子账户开户接口并解析
        try {
            logger.info("【预留手机号变更】===========请求参数：" + sendParams);
            //调用电子帐户开户接口
            BaseHttpResponse baseHttpResponse = doPost(sendParams, host +url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【预留手机号变更】===========预留手机号变更接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：预留手机号变更接口调用失败");
                throw new BusinessException(baseResponse);
            }
            try {
                //将接口返回的数据转换成对象后返回
                logger.info("【预留手机号变更】===========E支付返回：" + baseHttpResponse.getEntityString());
                JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
                if(result_json == null){
                    logger.info("【预留手机号变更】===========接口返回的数据为空");
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                    baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：预留手机号变更接口返回的数据为空");
                    throw new BusinessException(baseResponse);
                }else {
                    if("0".equals(result_json.getString("status"))){
                        logger.info("【预留手机号变更】接口调用成功");
                        responseMap.put("recode", BusinessMsg.SUCCESS);
                        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    }else {
                        logger.info("【预留手机号变更】接口调用失败");
//                        responseMap.put("recode", result_json.get("error_no"));
//                        responseMap.put("remsg", result_json.get("error_info"));
                        responseMap.put("recode", BusinessMsg.FOUR_VERIFY_FAILED);
                        responseMap.put("remsg", result_json.get("error_info"));
                    }
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
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            responseMap.put("recode", baseResponse.getRecode());
            responseMap.put("remsg", baseResponse.getRemsg());
            responseMap.put("order_status",OrderStatus.FAIL.getCode());
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> bindAccountVerify(Map<String, Object> params) throws BusinessException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        realParams.put("partner_id", params.get("partner_id"));
        realParams.put("partner_serial_no", params.get("partner_serial_no"));
        realParams.put("partner_trans_date", params.get("partner_trans_date"));
        realParams.put("partner_trans_time", params.get("partner_trans_time"));
        realParams.put("client_name", params.get("client_name"));
        realParams.put("id_kind", params.get("id_kind"));
        realParams.put("id_no", params.get("id_no"));
        realParams.put("mobile_tel", params.get("mobile_tel"));
        realParams.put("card_no", params.get("card_no"));
        realParams.put("func_code", params.get("func_code"));
        realParams.put("verify_info", params.get("verify_info"));
        realParams.put("pay_bankacct_type", params.get("pay_bankacct_type"));
        realParams.put("sendercomp_id", params.get("sendercomp_id"));
        realParams.put("targetcomp_id", "91000");
        realParams.put("channelId", params.get("channelId"));
        //=========雅酷必填字段============
        realParams.put("partner_userid",params.get("partner_userid"));
        //===================================
        //TODO 生成签名串
        String sign = "testsign1111111";
        realParams.put("cert_sign", sign);
        try {
            String host = String.valueOf(params.get("host"));
            String url = String.valueOf(params.get("url"));
            url = host + url;
            logger.info("===========调用绑卡确认参数：" + JSON.toJSON(realParams));
            BaseHttpResponse baseHttpResponse = doPost(realParams, url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【绑卡第三方接口调用】===========第三方接口调用失败，【" +
                        baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                throw new BusinessException(baseResponse);
            }
            try {
                //将接口返回的数据转换成对象后返回
                AccountVerifyResponse msgBindCardResponse = JSON.parseObject(baseHttpResponse.getEntityString(), AccountVerifyResponse.class);
                logger.info("【绑卡第三方接口调用】=========E支付返回：" + msgBindCardResponse);
                if (msgBindCardResponse.getData() != null && msgBindCardResponse.getData().get(0).getAuth_status().equals("0")) {
                    logger.info("【绑卡第三方接口调用】=========绑卡申请成功");
                    responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                } else if ((msgBindCardResponse.getData() != null && msgBindCardResponse.getData().get(0).getAuth_status().equals("2"))
                        || "013068".equals(msgBindCardResponse.getError_no())) {
                    logger.info("【绑卡第三方接口调用】=========绑卡确认成功");
                    responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                    if (msgBindCardResponse.getData() != null) {
                        responseMap.put("bank_id", msgBindCardResponse.getData().get(0).getBank_id());
                    }
                } else if ((msgBindCardResponse.getData() != null && msgBindCardResponse.getData().get(0).getAuth_status().equals("4"))) {
                    logger.info("【绑卡第三方接口调用】=========绑卡确认失败：" + msgBindCardResponse.getData().get(0).getRespMsg());
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    if (msgBindCardResponse.getData().get(0).getRespMsg().contains("CFCA")) {
                        responseMap.put("recode", BusinessMsg.CFCA_AUTH_FAILED);
                    }
                    responseMap.put("remsg", msgBindCardResponse.getData().get(0).getRespMsg());
                } else if ((msgBindCardResponse.getData() != null && msgBindCardResponse.getData().get(0).getAuth_status().equals("5"))) {
                    logger.info("【绑卡第三方接口调用】=========绑卡确认状态未知：" + msgBindCardResponse.getData().get(0).getRespMsg());
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("remsg", "CFCA验证状态未知，绑卡失败！");
                } else {
                    logger.info("【绑卡第三方接口调用】=========绑卡确认失败：" + msgBindCardResponse.getError_info());
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    if (msgBindCardResponse.getData() != null) {
                        responseMap.put("remsg", msgBindCardResponse.getData().get(0).getRespMsg());
                    } else {
                        responseMap.put("recode", msgBindCardResponse.getError_code());
                        responseMap.put("remsg", msgBindCardResponse.getError_info());
                    }
                }
                return responseMap;
            } catch (Exception e) {
                logger.info("【绑卡第三方接口调用】===========第三方接口返回的数据无法解析：" +
                        baseHttpResponse.getEntityString());
                logger.error(e);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
                throw new BusinessException(baseResponse);
            }
        } catch (Exception e) {
            logger.error("【绑卡第三方接口调用】===========未知异常" + e);
            BaseResponse baseResponse = new BaseResponse();
            if (e instanceof BusinessException) {
                baseResponse = ((BusinessException) e).getBaseResponse();
            } else {
                baseResponse.setRecode(BusinessMsg.RUNTIME_EXCEPTION);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.RUNTIME_EXCEPTION));
            }
            throw new BusinessException(baseResponse);
        }
    }

    @Override
    public Map<String, Object> autoAddValue(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String, Object> queryAutoAddValue(Map<String, Object> params) throws BusinessException {
        return null;
    }

    @Override
    public Map<String,Object>queryContractAppTo(Map<String,Object>params)throws  BusinessException{
        logger.info("【签约查询】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        realParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        realParams.put("channelId", MapUtils.getString(params, "channelid", ""));
        realParams.put("cert_sign", "testsign123456");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            //TODO 设置URL地址
           /* String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;*/
            BaseHttpResponse  baseHttpResponse = doPost(realParams, host + url, MapUtils.getString(params, "partner_serial_no", ""));
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【签约查询第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【签约查询第三方接口调用】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【签约查询第三方接口调用】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【签约查询第三方接口调用】========E支付返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONArray dataList=result_json.getJSONArray("data");
            if (null == dataList || dataList.size() <= 0) {
                String respMsg = result_json.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.FAIL) : result_json.get("respMsg").toString();
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", respMsg);
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                JSONArray detailList=((JSONObject)dataList.get(0)).getJSONArray("details");
                if(detailList==null || detailList.size()==0){
                    List<JSONObject> newData=new ArrayList<>();
                    responseMap.put("data",newData);
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                }else {
                    responseMap.put("data", detailList);
                    responseMap.put("recode", BusinessMsg.SUCCESS);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                    responseMap.put("order_status", (((JSONObject)detailList.get(0))).getString("arg_status"));
                }
            }
        } catch (Exception e) {
            logger.error("【签约查询第三方接口调用】===========未知异常" , e);
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
    public Map<String,Object>queryContractApp(Map<String,Object>params)throws  BusinessException{
        logger.info("【签约申请】入参:" + JSON.toJSON(params));
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> realParams = new HashMap<String, Object>();
        realParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        realParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        realParams.put("partner_trans_time", MapUtils.getString(params, "partner_trans_time", ""));
        realParams.put("partner_trans_date", MapUtils.getString(params, "partner_trans_date", ""));
        realParams.put("client_name", MapUtils.getString(params, "client_name", ""));
        realParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("card_no", MapUtils.getString(params, "card_no", ""));
        realParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
        realParams.put("func_code",MapUtils.getString(params,"func_code",""));
        realParams.put("sendercomp_id", MapUtils.getString(params, "sendercomp_id", ""));
        realParams.put("targetcomp_id",MapUtils.getString(params,"targetcomp_id",""));
        realParams.put("channelid", MapUtils.getString(params, "channelid", ""));
        realParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        realParams.put("cert_sign", "testsign123456");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            //TODO 设置URL地址
           /* String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;*/
            BaseHttpResponse  baseHttpResponse = doPost(realParams, host + url, MapUtils.getString(params, "partner_serial_no", ""));
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【签约申请第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【签约申请第三方接口调用】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【签约申请第三方接口调用】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【签约申请第三方接口调用】========E支付返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null == result_json.get("data") || result_json.getJSONArray("data").size() <= 0) {
                JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
                String respMsg = data.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.FAIL) : data.get("respMsg").toString();
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", respMsg);
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                responseMap.put("data",((JSONObject)result_json.getJSONArray("data").get(0)));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
            }
        } catch (Exception e) {
            logger.error("【签约申请第三方接口调用】===========未知异常" + e);
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
    public Map<String,Object> batchPayQueryTo(Map<String, Object> params) throws BusinessException {
        logger.info("【查询】入参:" + JSON.toJSON(params));
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
        realParams.put("targetcomp_id", MapUtils.getString(params, "targetcomp_id", ""));
        realParams.put("cert_sign", "testsign123456");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        try {
            //TODO 设置URL地址
           /* String host = MapUtils.getString(params, "host", "");
            String url = MapUtils.getString(params, "url", "");
            url = host + url;*/
            BaseHttpResponse baseHttpResponse = doPost(realParams, host+url, MapUtils.getString(params, "partner_serial_no", ""));
            if (null == baseHttpResponse || 200 != baseHttpResponse.getStatusCode()) {
                logger.info("【代扣查询第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【代扣查询第三方接口调用】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【代扣查询第三方接口调用】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【代扣查询第三方接口调用】========E支付返回错误：" + result_json);
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null == result_json.get("data") || result_json.getJSONArray("data").size() <= 0) {
                JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
                String respMsg = data.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.FAIL) : data.get("respMsg").toString();
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", respMsg);
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                responseMap.put("data",((JSONObject)result_json.getJSONArray("data").get(0)).getJSONArray("details"));
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", result_json.get("pay_status"));
            }
        } catch (Exception e) {
            logger.error("【代扣查询第三方接口调用】===========未知异常" + e);
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
        /* //签名串
        params.put("cert_sign", "123456789");
        //电子帐户开户接口
        //TODO 设置URL地址

        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【E支付批量代付查询第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【E支付批量代付查询第三方接口调用】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), WithholdResponseDetail.class);
        } catch (Exception e) {
            logger.info("【E支付批量代付查询第三方接口调用】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【E支付批量代付查询第三方接口调用】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }*/



    @Override
    public Map<String, Object> agrPayApply(Map<String, Object> params) {
        logger.info("【协议支付申请】===========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<String, Object>();
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
        sendParams.put("cert_sign", "agrPayApply");//签名串
        sendParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));//手机号
        sendParams.put("partner_userid", MapUtils.getString(params, "partner_userid", ""));//商户用户编号
        sendParams.put("id_kind", MapUtils.getString(params, "id_kind", ""));
        sendParams.put("occur_balance", MapUtils.getString(params, "occur_balance", ""));//金额

        sendParams.put("terminal_id",MapUtils.getString(params, "terminal_id", "0"));
        sendParams.put("terminal_type",MapUtils.getString(params, "terminal_type", "1"));
        sendParams.put("partner_userid",MapUtils.getString(params, "partner_userid", "01"));
        sendParams.put("prod_name",MapUtils.getString(params, "prod_name", "快捷充值"));
        sendParams.put("registtime",MapUtils.getString(params, "registtime", DateUtils.today("yyyy-MM-dd HH:mm:ss").toString()));
        sendParams.put("lastloginterminalid",MapUtils.getString(params, "lastloginterminalid", "192.0.0.168"));
        sendParams.put("issetpaypwd",MapUtils.getString(params, "issetpaypwd", "0"));
        //获取请求URL
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");
        logger.info("===========【协议支付申请】请求支付参数：" + JSON.toJSON(sendParams));
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
            logger.info("【协议支付申请】========返回：" + result_json);
            if (null == result_json) {
                logger.info("【协议支付申请】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            //将接口返回的数据转换成对象后返回
            if (null != result_json.get("error_no")) {
                logger.info("*****【协议支付申请】 行内支付返回错误信息，请求失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", result_json.getString("error_no"));
                responseMap.put("remsg", result_json.getString("error_info"));
                return responseMap;
            }
            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            if (null != data.get("host_req_serial_no")) {
                responseMap.put("host_req_serial_no", data.get("host_req_serial_no"));
            }
            if (null != data.get("hsepay_order_no")) {
                responseMap.put("hsepay_order_no", data.get("hsepay_order_no"));
            }
            if (null != data.get("self_bank_flag")) {
                responseMap.put("self_bank_flag", data.get("self_bank_flag"));
            }

            if (!"".equals(MapUtils.getString(params, "url_msgRequest", ""))) {
                logger.info("【协议支付申请】==========支付公司收单，调用支付公司快捷支付短验申请。");
                Map<String, Object> sendMsgParams = new HashMap<>();
                sendMsgParams.put("host", MapUtils.getString(params, "host", ""));
                sendMsgParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
                sendMsgParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
                sendMsgParams.put("mobile_tel", MapUtils.getString(params, "mobile_tel", ""));
                sendMsgParams.put("url_msgRequest", MapUtils.getString(params, "url_msgRequest", "")); //支付发送短信接口
                logger.info("【协议支付申请】支付收单，发送短信申请，请求参数："+JSON.toJSON(sendMsgParams));
                Map<String, Object> res = sendQuickPayMsg(sendMsgParams);
                logger.info("【协议支付申请】支付收单，短信申请结果："+JSON.toJSON(res));
                if (!OrderStatus.SUCCESS.getCode().equals(res.get("order_status"))) {
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    responseMap.put("recode", res.get("recode"));
                    responseMap.put("remsg", res.get("remsg"));
                    return responseMap;
                }
            }
            responseMap.put("query_id", MapUtils.getString(params, "partner_serial_no", ""));
            responseMap.put("process_date", DateUtils.todayfulldata());
            responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            responseMap.put("recode", BusinessMsg.SUCCESS);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
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
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> agrPayConfirm(Map<String, Object> params) {
        logger.info("【协议支付确认】=========入参参数：" + params);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if (!(boolean) responseMap.get("success")) {
            return responseMap;
        }
        Map<String, Object> sendParams = new HashMap<>();
        sendParams.put("partner_id", MapUtils.getString(params, "partner_id", ""));
        sendParams.put("partner_serial_no", MapUtils.getString(params, "partner_serial_no", ""));
        sendParams.put("verify_info", MapUtils.getString(params, "verify_info", ""));
        sendParams.put("cert_sign", "agrPayConfirm");
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url", "");

        //------------------------------请求第三方支付（云融惠付）---------------------------------------------
        logger.info("【协议支付确认】=========获取支付响应开始，确认交易流水号：" + MapUtils.getString(params, "partner_serial_no", ""));
        BaseHttpResponse baseHttpResponse = null;
        try {
            try {
                logger.info("===========【协议支付确认】传给E支付参数：" + JSON.toJSON(sendParams));
                baseHttpResponse = doPost(sendParams, host + url, "");
            } catch (Exception ex) {
                logger.error("【向E支付发送请求异常】", ex);
                if (ex instanceof BusinessException) {
                    BaseResponse baseResponse = ((BusinessException) ex).getBaseResponse();
                    if (baseResponse.getRemsg().equals("连接被拒绝")
                            || baseResponse.getRemsg().equals("请求第三方连接超时")
                            || baseResponse.getRemsg().equals("连接被重置")) {
                        logger.info("【向E支付发送请求异常】" + baseResponse.getRemsg());
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
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用结果未明");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
            logger.info("【协议支付确认】========E支付返回：" + result_json);
            if (null == result_json) {
                logger.info("【协议支付确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no") && !"".equals(result_json.get("error_no"))) {
                //其他异常时
                responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                for(Map.Entry<String,String> entry: BobEpayCode.BobEpayCode.entrySet()){
                    if(entry.getKey().equals(result_json.get("error_no"))){
                        responseMap.put("order_status", entry.getValue());
                        responseMap.put("remsg",result_json.get("error_info"));
                        responseMap.put("recode", result_json.get("error_no"));
                        break;
                    }
                }
                return responseMap;
            }
            //--------------------------------获取成功返回信息-----------------------------------------------------
            if (result_json.get("data") == null && result_json.getJSONArray("data").getJSONObject(0) == null) {
                logger.info("【协议支付确认】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
            //添加支付账期返回
            if(null!=data.get("pay_finish_date") && !"".equals(data.get("pay_finish_date"))){
                responseMap.put("pay_finish_date",data.get("pay_finish_date"));
            }

            //添加支付账期返回
            if(null!=data.get("self_bank_flag") && !"".equals(data.get("self_bank_flag"))){
                responseMap.put("self_bank_flag",data.get("self_bank_flag"));
            }

            if(null!=data.get("hsepay_order_no") && !"".equals(data.get("hsepay_order_no"))){
                responseMap.put("hsepay_order_no",data.get("hsepay_order_no"));
            }

            //获取支付状态
            String payStatus = data.getString("pay_status");
            // responseMap.put("is_bank_return","0");
            //如果payStatus==3 则处理成功 转账 记录流水 记录充值信息表
            if (EpayStatus.SUCCESS.getCode().equals(payStatus)) {
                logger.info("【协议支付确认】===========第三方支付返回状态交易成功");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (EpayStatus.PAYING.getCode().equals(payStatus)) {
                //更新充值信息
                logger.info("【协议支付确认】===========第三方支付返回状态处理中");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS) + "：充值订单处理中");
            } else if (EpayStatus.FAIL.getCode().equals(payStatus)) {
                logger.info("【协议支付确认】===========第三方支付返回状态交易失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                responseMap.put("recode", data.get("respCode") == null ? BusinessMsg.PAYMENT_FAILED : data.get("respCode"));
                responseMap.put("remsg", data.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.PAYMENT_FAILED) : data.get("respMsg"));
            } else {
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                responseMap.put("recode", data.get("respCode") == null ? BusinessMsg.UNKNOW_ERROE : data.get("respCode"));
                responseMap.put("remsg", data.get("respMsg") == null ? BusinessMsg.getMsg(BusinessMsg.UNKNOW_ERROE) : data.get("respMsg"));
            }
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

        JSONObject result_json = JSONObject.parseObject(baseHttpResponse.getEntityString());
        logger.info("【批量代扣】========E支付返回：" + result_json);
        if (null == result_json) {
            logger.info("【批量代扣】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
            responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            return responseMap;
        }
        if (null != result_json.get("error_no") && !"".equals(result_json.get("error_no"))) {
            //其他异常时
            responseMap.put("recode", BusinessMsg.UNKNOW_REMOTE_STATUS);
            responseMap.put("remsg", result_json.get("error_info"));
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            for (Map.Entry<String, String> entry : BobEpayCode.BobEpayCode.entrySet()) {
                if (entry.getKey().equals(result_json.get("error_no"))) {
                    responseMap.put("order_status", entry.getValue());
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("recode", result_json.get("error_no"));
                    break;
                }
            }
            return responseMap;
        }
        //--------------------------------获取成功返回信息-----------------------------------------------------
        if (result_json.get("data") == null) {
            logger.info("【批量代扣】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【" + result_json.toString() + "】");
            responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
            responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            return responseMap;
        }
        responseMap.put("data", JSON.parseObject(baseHttpResponse.getEntityString()));
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
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
            logger.info("***【单笔订单状态查询】传给E支付参数***：" + JSON.toJSON(sendParams));
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
            logger.info("【单笔订单状态查询】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【单笔订单状态查询】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                for(Map.Entry<String,String> entry: BobEpayCode.BobEpayCode.entrySet()){
                    if(entry.getKey().equals(result_json.get("error_no"))){
                        responseMap.put("order_status", entry.getValue());
                        responseMap.put("remsg",result_json.get("error_info"));
                        responseMap.put("recode", result_json.get("error_no"));
                        break;
                    }
                }
                logger.info("【单笔订单状态查询】========E支付返回错误：" + result_json);
                return responseMap;
            }

            if (null == result_json.get("data") || null == result_json.getJSONArray("data").getJSONObject(0)) {
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            JSONObject data = result_json.getJSONArray("data").getJSONObject(0);

            if (StringUtils.isBlank(data.getString("pay_status")) ||
                    StringUtils.isBlank(data.getString("original_serial_no")) ||
                    StringUtils.isBlank(data.getString("occur_balance")) ||
                    StringUtils.isBlank(data.getString("cert_sign"))) {
                logger.info("【单笔订单状态查询】========支付返回缺少必要参数：" + data);
                responseMap.put("recode", BusinessMsg.PARAMETER_LACK);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK) + ":支付返回缺少必要参数");
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
                return responseMap;
            }
            if(null!=data.get("pay_check_date") && !"".equals(data.get("pay_check_date"))){
                responseMap.put("pay_check_date",data.get("pay_check_date").toString());
            }
            String pay_status = data.getString("pay_status");

            if (EpayStatus.SUCCESS.getCode().equals(pay_status)) {
                logger.info("！*****充值订单查询 ==============该订单为交易成功========================");
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
            } else if (EpayStatus.FAIL.getCode().equals(pay_status)) {
                logger.info("！*****充值订单查询 ==============该订单为交易失败========================");
                //修改用户充值信息表状态 状态为失败
                String respMsg=data.get("respMsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):data.get("respMsg").toString();
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", respMsg);
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
            } else if (EpayStatus.PAYING.getCode().equals(pay_status)) {
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
            if(null!=data.get("self_bank_flag")){
                responseMap.put("self_bank_flag",data.get("self_bank_flag"));
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
            logger.info("【解绑第三方接口调用】========E支付返回：" + result_json);

            if (null == result_json) {
                logger.info("【解绑第三方接口调用】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no")) {
                logger.info("【解绑第三方接口调用】========E支付返回错误：" + result_json);
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if (null == result_json.get("data") || result_json.getJSONArray("data").size()<=0) {
                JSONObject data = result_json.getJSONArray("data").getJSONObject(0);
                String respMsg = data.get("respMsg")==null?BusinessMsg.getMsg(BusinessMsg.FAIL):data.get("respMsg").toString();
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", respMsg);
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
        logger.info("【icis获取密码因子】=============入参："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String, Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String,Object> realParams = new HashMap<String, Object>();
        realParams.put("mall_no",MapUtils.getString(params,"mall_no",""));
        realParams.put("mer_no",MapUtils.getString(params,"mer_no",""));
        realParams.put("password_type",MapUtils.getString(params,"password_type",""));
        realParams.put("sign","testsign");
        //开始请求第三方
        try{
            String host = MapUtils.getString(params,"host","");
            String url = MapUtils.getString(params,"url","");
            url = host+url;
            BaseHttpResponse baseHttpResponse = doPost(realParams,url,null);
            if(null == baseHttpResponse || 200!=baseHttpResponse.getStatusCode()){
                logger.info("【icis获取密码因子第三方接口调用】============第三方接口调用失败");
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【icis获取密码因子第三方接口调用】============返回结果："+result_json);
            if(null == result_json ) {
                logger.info("【icis获取密码因子第三方接口调用】============【" + MapUtils.getString(params, "password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if (null != result_json.get("error_no") && !"000000".equals(result_json.get("error_no"))) {
                logger.info("【icis获取密码因子第三方接口调用】============返回错误：" + result_json);
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null != result_json.get("random_key")){
                responseMap.put("random_key",result_json.get("random_key"));
            }
            if(null != result_json.get("random_value")){
                responseMap.put("random_value",result_json.get("random_value"));
            }
            responseMap.put("recode",BusinessMsg.SUCCESS);
            responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
            responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        }catch (Exception ex){
            logger.error("【icis获取密码因子第三方接口调用】===========未知异常" + ex);
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
    public Map<String, Object> checkPassowrd(Map<String, Object> params) {
        logger.info("【icis密码验证】===============入参："+JSON.toJSON(params));
        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap = checkParams(params);
        if(!(boolean)responseMap.get("success")){
            return responseMap;
        }
        Map<String,Object> realParams = new HashMap<String,Object>();
        realParams.put("mall_no",MapUtils.getString(params,"mall_no",""));
        realParams.put("mer_no",MapUtils.getString(params,"mer_no",""));
        realParams.put("plat_cust",MapUtils.getString(params,"plat_cust",""));
        realParams.put("id_kind",MapUtils.getString(params,"id_kind",""));
        realParams.put("id_no",MapUtils.getString(params,"id_no",""));
        realParams.put("password",MapUtils.getString(params,"password",""));
        realParams.put("random_key",MapUtils.getString(params,"random_key",""));
        realParams.put("flag",MapUtils.getString(params,"flag",""));
        realParams.put("cert_sign", "testsign");
        //请求第三方支付
        BaseHttpResponse baseHttpResponse = null;
        try{
            String host = MapUtils.getString(params,"host","");
            String url = MapUtils.getString(params,"url","");
            url = host+url;
            baseHttpResponse = doPost(realParams,url,null);
            if(null == baseHttpResponse || 200!=baseHttpResponse.getStatusCode()){
                logger.info("【icis密码验证第三方接口调用】============第三方接口调用失败,【"+baseHttpResponse.getStatusCode()+"】"+baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【icis密码验证第三方接口调用】=============返回结果:"+result_json);
            if(null == result_json){
                logger.info("【isic密码验证第三方接口调用】============【" + MapUtils.getString(params, "password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if(null == result_json.get("status") ||"".equals(result_json.get("status"))) {
                if (null != result_json.get("error_no")) {
                    logger.info("【icis密码验证第三方接口调用】============返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                } else {
                    logger.info("【icis密码验证第三方接口调用】============【" + MapUtils.getString(params, "password", "") + "】" + "第三方返回值解析失败");
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if("0".equals(status)){
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            }else if("7".equals(status)) {
                responseMap.put("recode", BusinessMsg.PASSWORD_OUT_OFF_DATE);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.PASSWORD_OUT_OFF_DATE)); //密钥失效
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else if("5".equals(status)){
                responseMap.put("recode",BusinessMsg.USER_LOCKED);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.USER_LOCKED)); //"账户锁定"
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else if("9".equals(status)){
                responseMap.put("recode",  BusinessMsg.PASSWORD_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.PASSWORD_ERROR)+","+result_json.get("error_info"));//验密失败
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)+"未知状态");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }

        }catch (Exception ex){
            logger.error("【icis密码验证第三方接口调用】===========未知异常" + ex);
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
        logger.info("【icis密码修改重置】===============入参："+JSON.toJSON(params));
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
        //四要素
        realParams.put("card_no",MapUtils.getString(params,"card_no",""));
        realParams.put("mobile_tel",MapUtils.getString(params,"mobile_tel",""));

        realParams.put("tran_flag",MapUtils.getString(params,"tran_flag",""));
        realParams.put("ori_random_key",MapUtils.getString(params,"ori_random_key",""));
        realParams.put("ori_password",MapUtils.getString(params,"ori_password",""));
        realParams.put("random_key",MapUtils.getString(params,"random_key",""));
        realParams.put("password",MapUtils.getString(params,"password",""));
        realParams.put("cert_sign", "testsign");
        //请求第三方支付
        BaseHttpResponse baseHttpResponse = null;
        try{
            String host = MapUtils.getString(params,"host","");
            String url = MapUtils.getString(params,"url","");
            url = host+url;
            baseHttpResponse = doPost(realParams,url,null);
            if(null == baseHttpResponse || 200!=baseHttpResponse.getStatusCode()){
                logger.info("【icis密码修改重置第三方接口调用】============第三方接口调用失败,【"+baseHttpResponse.getStatusCode()+"】"+baseHttpResponse.getErrorMessage());
                responseMap.put("recode",BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status",OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【icis密码修改重置第三方接口调用】=============返回结果:"+result_json);
            if(null == result_json){
                logger.info("【isic密码修改重置第三方接口调用】============【" + MapUtils.getString(params, "ori_password", "") + "】" + "第三方返回值解析失败");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            if( null==result_json.get("status") ||"".equals(result_json.get("status"))) {
                if (null != result_json.get("error_no")) {
                    logger.info("【icis密码修改重置第三方接口调用】============返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                } else {
                    logger.info("【icis密码修改重置第三方接口调用】============【" + MapUtils.getString(params, "ori_password", "") + "】" + "第三方返回值解析失败");
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if("0".equals(status)){
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            }else if("7".equals(status)) {
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL) + "密钥失效");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else if("9".equals(status)){
                responseMap.put("recode",BusinessMsg.FAIL);
                responseMap.put("remsg",result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }else{
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.FAIL)+"未知状态");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            }

        }catch (Exception ex){
            logger.error("【icis密码修改重置第三方接口调用】===========未知异常" + ex);
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


    /*
        isic绑卡接口
     */
    @Override
    public Map<String, Object> isicBindCard(Map<String, Object> params) {
        logger.info("【isic绑卡】入参:" + JSON.toJSON(params));
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
        realParams.put("password", MapUtils.getString(params, "passwod", ""));//Y
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
                logger.info("【isic绑卡】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
            JSONObject result_json = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【isic绑卡】========第三方返回：" + result_json);

            if (null == result_json) {
                logger.info("【isic绑卡】【" + MapUtils.getString(params, "partner_serial_no", "") + "】【第三方接口返回值解析失败】");
                responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }

            if(null==result_json.get("status") || "".equals(result_json.get("status"))){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic绑卡】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //认证成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
                //认证失败
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else if(IcisAuthStatus.UNBINDFAIL.getCode().equals(status)) {
                responseMap.put("recode", BusinessMsg.VERIFICATION_ERROR);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
            } else{
                //处理中
                responseMap.put("recode", result_json.get("error_no"));
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.PROCESSING.getCode());
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
            logger.error("【isic绑卡】===========未知异常" + e);
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

    /*
        isic解绑接口
     */
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
            String status = result_json.get("status")==null?"":result_json.get("status").toString();
            if(StringUtils.isBlank(status)){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic解绑】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }

            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //解绑成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
                return responseMap;
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
                //解绑失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
                return responseMap;
            }
        } catch (Exception e) {
            logger.error("【cis解绑】===========未知异常" + e);
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

    /*
       isic预留手机号变更接口
    */
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

            if(null==result_json.get("status") ||"".equals(result_json.get("status"))){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic预留手机变更】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }

            String status = result_json.get("status").toString();
            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
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

    /*
       isic用户信息变更接口
    */
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
            if(null==result_json.get("status") ||"".equals(result_json.get("status"))){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic客户信息变更】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
                responseMap.put("order_status", OrderStatus.FAIL.getCode());
            } else {
                //处理中
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", result_json.get("error_info"));
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

    /*
       isic客户冻结接口
    */
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
            if(null==result_json.get("status") ||"".equals(result_json.get("status"))){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic客户冻结】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
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

    /*
       isic客户解冻接口
    */
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
            if(null==result_json.get("status") ||"".equals(result_json.get("status"))){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic客户解冻】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
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

    /*
       isic客户销户接口
    */
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
        realParams.put("id_no", MapUtils.getString(params, "id_no", ""));
        realParams.put("mobile", MapUtils.getString(params, "mobile", ""));
        realParams.put("email", MapUtils.getString(params, "email", ""));
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
            if(null==result_json.get("status") ||"".equals(result_json.get("status"))){
                if (null != result_json.get("error_no")) {
                    logger.info("【isic客户销户】========第三方接口返回错误：" + result_json);
                    responseMap.put("recode", result_json.get("error_no"));
                    responseMap.put("remsg", result_json.get("error_info"));
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }else{
                    responseMap.put("recode", BusinessMsg.CALL_REMOTE_ERROR);
                    responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回值解析失败");
                    responseMap.put("order_status", OrderStatus.FAIL.getCode());
                    return responseMap;
                }
            }
            String status = result_json.get("status").toString();
            if (IcisAuthStatus.Success.getCode().equals(status)) {
                //成功
                responseMap.put("recode", BusinessMsg.SUCCESS);
                responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
                responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
            } else if (IcisAuthStatus.Fail.getCode().equals(status)) {
                //失败
                responseMap.put("recode", BusinessMsg.FAIL);
                responseMap.put("remsg", result_json.get("error_info"));
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
}
