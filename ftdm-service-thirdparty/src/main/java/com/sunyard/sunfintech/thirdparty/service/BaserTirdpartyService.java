package com.sunyard.sunfintech.thirdparty.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.security.ccbSecurity.SecurityKeyUtil;
import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.SunSignatureUtil;
import org.apache.commons.collections.MapUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.sunyard.sunfintech.core.support.security.ccbSecurity.SignResponseUtil.getSignByMap;

/**
 * @author heroy
 * @version 2018/1/12
 */
public abstract class BaserTirdpartyService extends BaseServiceSimple {
    public BaseHttpResponse doPost(Map<String, Object> params, String URL, String seriNo) throws BusinessException {
        logger.info("start-->post-->请求地址:" + URL);
        logger.info("start-->post-->请求数据:" + JSONUtils.toJSONString(params));

        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = HttpClientUtils.httpPostRequest(URL, params);

            logger.info("end-->post-->请求返回-->返回码:" + baseHttpResponse.getStatusCode() + "-->返回数据:" + baseHttpResponse.getEntityString());

        } catch (UnsupportedEncodingException e) {
            logger.info("【HTTP请求异常】-->第三方调用时转码异常:",e);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "第三方调用时转码异常");
            throw new BusinessException(baseResponse);
        } catch (BusinessException e) {
            logger.info("【HTTP请求异常】-->" + e.getBaseResponse().getRemsg(),e);
            throw new BusinessException(e.getBaseResponse());
        } catch (Exception e) {
            logger.info("【HTTP请求异常】-->第三方调用时未知异常", e);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "第三方调用时未知异常");
            throw new BusinessException(baseResponse);
        }
        return baseHttpResponse;
    }

    public BaseHttpResponse doPost(Map<String, Object> params, String URL, Boolean isSign, String key) throws BusinessException {
        logger.info("start post  请求第三方  ------" + URL);
        logger.info("start post  请求第三方数据  ------" + JSONUtils.toJSONString(params));

        BaseHttpResponse baseHttpResponse = null;
        try {
            if (isSign){
                sunpaySignUp(params,key);
            }
            baseHttpResponse = HttpClientUtils.httpPostRequest(URL, params);
            logger.info("end  post  请求第三方  ------" + "返回码：" + baseHttpResponse.getStatusCode() + "返回数据：" + baseHttpResponse.getEntityString());
            if (isSign){
                boolean result = sunpayCheckSign(baseHttpResponse.getEntityString(),key);
                if(!result){
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setRecode(BusinessMsg.SIGNATURE_ERROR);
                    baseResponse.setRemsg("验签失败");
                    throw new BusinessException(baseResponse);
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("【HTTP请求异常】========第三方调用时转码异常");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "第三方调用时转码异常");
            throw new BusinessException(baseResponse);
        } catch (BusinessException e) {
            logger.info("【HTTP请求异常】========" + e.getBaseResponse().getRemsg());
            throw new BusinessException(e.getBaseResponse());
        } catch (Exception e) {
            logger.info("【HTTP请求异常】========第三方调用时未知异常");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "第三方调用时未知异常");
            throw new BusinessException(baseResponse);
        }
        return baseHttpResponse;
    }

    public Map<String,Object> checkParams(Map<String,Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        if (null == params || StringUtils.isBlank(MapUtils.getString(params, "host", "")) || StringUtils.isBlank(MapUtils.getString(params, "url", ""))) {
            logger.info("【绑卡第三方接口调用】=========缺少必要参数，请检查params,host,url 参数是否为空");
            responseMap.put("success", false);
            responseMap.put("order_status", OrderStatus.FAIL.getCode());
            responseMap.put("recode", BusinessMsg.PARAMETER_LACK);
            responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.PARAMETER_LACK));
            return responseMap;
        }
        responseMap.put("success",true);
        return responseMap;
    }

    private void sunpaySignUp(Map<String,Object> params,String key) {
        try {
            logger.info("【支付组加签】===========入参参数：" + params);
            TreeMap<String, String> m = new TreeMap<String, String>();
            String sign = SecurityKeyUtil.getSignString(SunSignatureUtil.getSignContentFromTreeMap(m), key);
            logger.info("【支付组加签】【加签参数】【destContent】" + SunSignatureUtil.getSignContentFromTreeMap(m));
            logger.info("【支付组加签】【加签结果】【sign】:" + sign);
            params.put("cert_sign",sign);
        } catch (Exception e) {
            logger.error("加签异常："+e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean sunpayCheckSign(String result,String key) {
        try {
            logger.info("【支付组验签】===========入参参数：" + result);
            Map<String, Object> result_map = JSONObject.parseObject(result,Map.class);
                if (result_map.get("error_no")!=null){
                    return true;
                } else {
                    result_map.remove("cert_sign");
                    String selfSign = getSignByMap(result_map, key);
                    if (selfSign.equals(result_map.get("cert_sign").toString())) {
                        logger.info("【支付组验签】【验签成功】");
                        return true;
                    } else {
                        logger.error("【checkSign】【对方签名】【cert_sign】" + result_map.get("cert_sign").toString());
                        logger.error("【checkSign】【我方签名】【selfSign】" + selfSign);
                        return false;
                    }
                }
            } catch (Exception e) {
                logger.error("验签异常："+e.getMessage());
                e.printStackTrace();
            }
        return false;
    }
}
