package com.sunyard.sunfintech.web.business;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.pub.model.NotifyData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by PengZY on 2017/6/4.
 */
@Service("notifyBusiness")
public class  NotifyBusiness {

    @Resource(name = "BankSign")
    private SignAdapter signAdapter;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void sendNotify(NotifyData notifyData){
        logger.info("【异步通知队列】=========开始发送异步通知");
        logger.info("【异步通知队列】=============url:"+notifyData.getNotifyUrl()+",json:"+ notifyData.getNotifyContent());
        String rediskey = Constants.NOTIFY_SEDNDING_COUNT + notifyData.getId();
        long num = CacheUtil.getCache().increment(rediskey, 1);
        if(num >= 5){
            return;
        }
        try {
            Map<String,Object> contentMap= (Map<String, Object>) JSON.parse(notifyData.getNotifyContent());
            boolean notifyResult = this.nofity(notifyData.getMall_no(), notifyData.getNotifyUrl(), JSON.toJSONString(contentMap, GlobalConfig.serializerFeature));
            if(notifyResult == false){
                MQUtils.send(amqpTemplate, "ftdm.web.direct.exchange", "NotifyQueue", notifyData);
            }else{
                //
                CacheUtil.getCache().del(rediskey);
            }
        }catch (Exception e){
            logger.error("【异步通知队列】=========发送通知异常"+e.getMessage());
        }
    }

    public void sendNotify(String mall_no, String URL, String json) throws BusinessException {
        if (json != null && !"".equals(json)) {
            logger.info("【异步通知队列】通知内容：" + json);
            try {
                NotifyData notifyData = new NotifyData();
                notifyData.setMall_no(mall_no);
                notifyData.setNotifyContent(json);
                notifyData.setNotifyUrl(URL);
                String rediskey = Constants.NOTIFY_SEDNDING_COUNT + notifyData.getId();
                long num = CacheUtil.getCache().increment(rediskey, 1);
                if(num >= 5){
                    return;
                }
                Map<String,Object> contentMap= (Map<String, Object>) JSON.parse(notifyData.getNotifyContent());
                boolean notifyResult = this.nofity(notifyData.getMall_no(), notifyData.getNotifyUrl(), JSON.toJSONString(contentMap, GlobalConfig.serializerFeature));
                if(notifyResult == false){
                    MQUtils.send(amqpTemplate, "ftdm.web.direct.exchange", "NotifyQueue", notifyData);
                }else{
                    //
                    CacheUtil.getCache().del(rediskey);
                }
            } catch (Exception e) {
                logger.info("【异步通知】发送失败：" + e.getMessage());
            }
        }
    }

    /**
     *
     * @param mall_no 集团号
     * @param URL 通知地址
     * @param json 通知内容
     * @return 是否成功
     */
    public boolean nofity(String mall_no, String URL, String json){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("URL",URL);
        map.put("JSON",json);
        if(StringUtils.isBlank(URL)){
            return false;
        }
        String entityString = null;
        try{
            //调用对外请求的服务
            BaseHttpResponse baseHttpResponse = this.notifyDoPost(mall_no, URL, json);
            if(baseHttpResponse==null){
                entityString = "FAIL";
            }else{
                entityString = baseHttpResponse.getEntityString();
                entityString = entityString.toUpperCase();
                logger.info("返回数据：" + entityString);
            }
        }catch (BusinessException e){
            entityString = "FAIL";
        }
        logger.info("【平台异步通知】通知地址：url:---" + URL);
        logger.info("-----  通知数据：JSON:---" + json);
        logger.info("-----  rediskey：rediskey:---");
        logger.info("-----  通知次数：num:---");

        //如果这个服务返回
        if(entityString.contains("SUCCESS") || entityString.contains("success")){
            logger.info("【平台异步通知】成功");
            return true;
        }else{
            logger.info("【平台异步通知】失败，进入下一次");
            return false;
        }
    }

    /**
     * http通知
     * @param mall_no 集团号
     * @param URL 通知地址
     * @param json 通知内容
     * @return http返回内容
     * @throws BusinessException 业务异常
     */
    public BaseHttpResponse notifyDoPost(String mall_no, String URL, String json) throws BusinessException {
        logger.info("start post  job通知地址:" + URL);
        logger.info("start post  job通知数据 未加签:" + json);
        BaseHttpResponse baseHttpResponse = null;
        try {
            Map<String, String> map = (Map<String, String>) JSON.parse(json);
            TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
            logger.info("start post  job通知集团:" + mall_no);
            Map<String,String> params=new HashMap<>();
            params.put("origin_order_no_str",null);
            params.put("token_str",null);
            params.put("order_no_str",map.get("order_no"));
            String sign = signAdapter.signUp(mall_no, SunSignatureUtil.getSignContentFromTreeMap(treeMap),params);
            map.put("sign",sign);
            String newjson = JSON.toJSONString(map, GlobalConfig.serializerFeature);
            logger.info("start post  job通知数据:" + newjson);

            baseHttpResponse = HttpClientUtils.httpPostRequest(URL, new HashMap<String, Object>(){
                {
                    put("Content-Type", "application/json");
                }
            },newjson);
            logger.info("baseHttpResponse:"+JSON.toJSONString(baseHttpResponse));
            if(baseHttpResponse!=null){
                logger.info("end  post  通知第三方  ------" + "返回码：" + baseHttpResponse.getStatusCode() + "返回数据：" + baseHttpResponse.getEntityString());
            }else{
                logger.info("baseHttpResponse为空，order_no："+map.get("order_no"));
            }

        } catch (UnsupportedEncodingException e) {
            logger.info("error：---------------IO异常",e);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "通知第三方调用时转码异常");
            throw new BusinessException(baseResponse);
        } catch (Exception e) {
            logger.info("error：--------------IO异常",e);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "通知第三方调用时未知异常");
            throw new BusinessException(baseResponse);
        }
        return baseHttpResponse;
    }
}
