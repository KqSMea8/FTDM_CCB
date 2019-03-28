package com.sunyard.sunfintech.account.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.account.model.bo.*;
import com.sunyard.sunfintech.account.provider.IAccountTransferThirdParty;
import com.sunyard.sunfintech.account.util.ThirdPartyUrlLib;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.HttpClientUtils;
import com.sunyard.sunfintech.core.util.MQUtils;
import com.sunyard.sunfintech.core.util.URLConfigUtil;
import com.sunyard.sunfintech.pub.provider.ISysParameterService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by terry on 2017/7/4.
 */
@Service(interfaceClass = IAccountTransferThirdParty.class)
@CacheConfig(cacheNames="accountTransferThirdParty")
@org.springframework.stereotype.Service
public class AccountTransferThirdParty extends BaseServiceSimple implements IAccountTransferThirdParty {

//    @Resource(name = "jmsQueueTemplate")
//    private JmsTemplate jmsQueueTemplate;

    @Autowired
    private ISysParameterService sysParameterService;

    @Override
    public EpayAgentPayRealResponse eaccTransfer(Map<String, Object> params, String mall_no) throws BusinessException {
        //TODO 签名
        params.put("cert_sign", "testsign");
        //TODO 设置URL地址
        String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.AGENT_PAY_REAL);
        url = host + url;
        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【E支付代付第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【E支付代付第三方接口调用】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), EpayAgentPayRealResponse.class);
        } catch (Exception e) {
            logger.info("【E支付代付第三方接口调用】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【E支付代付第三方接口调用】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }
//        EpayAgentPayRealResponseDetail detail=new EpayAgentPayRealResponseDetail();
//        detail.setPay_status("2");
//        List<EpayAgentPayRealResponseDetail> data=new ArrayList<>();
//        data.add(detail);
//        EpayAgentPayRealResponse epayAgentPayRealResponse=new EpayAgentPayRealResponse();
//        epayAgentPayRealResponse.setData(data);
//        return epayAgentPayRealResponse;
    }

    @Override
    public BatchPayResponse batchPay(Map<String, Object> params, String mall_no) throws BusinessException {
        //签名串
        params.put("cert_sign", "123456789");
        //电子帐户开户接口
        //TODO 设置URL地址
        String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.BATCH_PAY);
        url = host + url;
        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【E支付批量代付第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【E支付批量代付第三方接口调用】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), BatchPayResponse.class);
        } catch (Exception e) {
            logger.info("【E支付批量代付第三方接口调用】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【E支付批量代付第三方接口调用】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }
        //生成模拟数据，放入redis
//        CacheUtil.getCache().set(String.valueOf(params.get("partner_serial_no")),String.valueOf(params.get("details")));
//        BatchPayResponse batchPayResponse=new BatchPayResponse();
//        List<BatchPayResponseData> dataList=new ArrayList<>();
//        BatchPayResponseData data=new BatchPayResponseData();
//        dataList.add(data);
//        batchPayResponse.setData(dataList);
//        return batchPayResponse;
    }

    @Override
    public BatchPayResponse batchCollection(Map<String, Object> params, String mall_no) throws BusinessException {
        //签名串
        params.put("cert_sign", "123456789");
        //电子帐户开户接口
        //TODO 设置URL地址
        String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.BATCH_COLLECTION);
        url = host + url;
        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【E支付批量代扣第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【E支付批量代扣第三方接口调用】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), BatchPayResponse.class);
        } catch (Exception e) {
            logger.info("【E支付批量代扣第三方接口调用】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【E支付批量代扣第三方接口调用】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }
    }

    @Override
    public BatchPayQueryResponse batchPayQuery(Map<String, Object> params, String mall_no) throws BusinessException {
        //签名串
        params.put("cert_sign", "123456789");
        //电子帐户开户接口
        //TODO 设置URL地址
        String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.BATCH_PAY_QUERY_NEW);
        url = host + url;
        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【E支付批量代付查询第三方接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【E支付批量代付查询第三方接口调用】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), BatchPayQueryResponse.class);
        } catch (Exception e) {
            logger.info("【E支付批量代付查询第三方接口调用】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【E支付批量代付查询第三方接口调用】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }
        //模拟生成返回数据
//        String detailStr=String.valueOf(CacheUtil.getCache().get(String.valueOf(params.get("original_serial_no"))));
//        List<BatchPayDetail> payList=JSON.parseArray(detailStr,BatchPayDetail.class);
//        Integer randNum= new Random().nextInt(2);
//        List<BatchPayQueryResponseDataDetail> queryDetailList=new ArrayList<>();
//        int index=0;
//        for(BatchPayDetail detail:payList){
//            index++;
//            BatchPayQueryResponseDataDetail queryDetail=new BatchPayQueryResponseDataDetail();
//            queryDetail.setCard_no(detail.getCard_no());
//            queryDetail.setOccur_balance(detail.getOccur_balance());
//            if(index==1){
//                if(randNum==0){
//                    queryDetail.setPay_status("2");
//                }else{
//                    queryDetail.setPay_status("3");
//                }
//            }else{
//                queryDetail.setPay_status("3");
//            }
//            queryDetailList.add(queryDetail);
//        }
//        BatchPayQueryResponse batchPayQueryResponse=new BatchPayQueryResponse();
//        List<BatchPayQueryResponseData> dataList=new ArrayList<>();
//        BatchPayQueryResponseData data=new BatchPayQueryResponseData();
//        data.setPartner_serial_no(String.valueOf(params.get("partner_serial_no")));
//        data.setOriginal_serial_no(String.valueOf(params.get("original_serial_no")));
//        data.setDetailsObj(queryDetailList);
//        dataList.add(data);
//        batchPayQueryResponse.setData(dataList);
//        return batchPayQueryResponse;
    }

    @Override
    public QueryPayStatusResponse queryPayStatusQuery(Map<String, Object> params, String mall_no) throws BusinessException {
        //签名串
        params.put("cert_sign", "123456789");
        //电子帐户开户接口
        //TODO 设置URL地址
        String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.QUERY_PAY_STATUS);
        url = host + url;
        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【单笔订单查询（电子账户转账）】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【单笔订单查询（电子账户转账）】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), QueryPayStatusResponse.class);
        } catch (Exception e) {
            logger.info("【单笔订单查询（电子账户转账）】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【单笔订单查询（电子账户转账）】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }
    }
    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;
    @Override
    public void addQueryToQueue(QueryPayStatus queryPayStatus) throws BusinessException {

        if(queryPayStatus!=null){
            MQUtils.send(amqpTemplate, "ftdm.account.direct.exchange", "QueryPayStatusQueue", queryPayStatus);
        }
    }
    @Override
    public void addReverseToQueue(BankReverse bankReverse) throws BusinessException {
        if(bankReverse!=null){
            MQUtils.send(amqpTemplate, "ftdm.account.direct.exchange", "BankReverseQueue", bankReverse);
        }
    }

    @Override
    public BankReverseResponse bankReverse(Map<String, Object> params, String mall_no) throws BusinessException {
        //签名串
        params.put("cert_sign", "123456789");
        //电子帐户开户接口
        //TODO 设置URL地址
        String host = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_SERVER_KEY);
        String url = sysParameterService.querySysParameter(mall_no, URLConfigUtil.EPAY_BANK_REVERSE);
        url = host + url;
        BaseHttpResponse baseHttpResponse = doPost(params, url, null);
        if (baseHttpResponse.getStatusCode() != 200) {
            logger.info("【冲正接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
        }
        try {
            //将接口返回的数据转换成对象后返回
            logger.info("【冲正接口调用】===========E支付返回："+baseHttpResponse.getEntityString());
            return JSON.parseObject(baseHttpResponse.getEntityString(), BankReverseResponse.class);
        } catch (Exception e) {
            logger.info("【冲正接口调用】=======" +
                    " ====第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error("【冲正接口调用】===========ERROR:"+e);
            throw new BusinessException(BusinessMsg.CALL_REMOTE_ERROR,
                    BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口返回的数据无法解析");
        }
    }

    @Override
    public BaseHttpResponse doPost(Map<String, Object> params, String URL, String seriNo) throws BusinessException {
        logger.info("【HTTP请求】========URL：" + URL);
        logger.info("【HTTP请求】========请求参数：" + JSONUtils.toJSONString(params));

        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = HttpClientUtils.httpPostRequest(URL, params);
            logger.info("【HTTP请求】========【" + baseHttpResponse.getStatusCode() + "】返回数据：" + baseHttpResponse.getEntityString());
        } catch (UnsupportedEncodingException e) {
            logger.info("【HTTP请求异常】========第三方调用时转码异常");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setRecode(BusinessMsg.FAIL);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.FAIL) + "第三方调用时转码异常");
            throw new BusinessException(baseResponse);
        }catch (BusinessException e){
            logger.info("【HTTP请求异常】========"+e.getBaseResponse().getRemsg());
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
}
