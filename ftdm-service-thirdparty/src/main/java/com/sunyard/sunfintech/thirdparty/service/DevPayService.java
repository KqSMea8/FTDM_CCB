package com.sunyard.sunfintech.thirdparty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.base.BaseHttpResponse;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.EpayStatus;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.DateUtils;
import com.sunyard.sunfintech.core.util.MathUtil;
import com.sunyard.sunfintech.core.util.SeqUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.thirdparty.provider.IAdapterService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames="devPayService")
@org.springframework.stereotype.Service("devPayService")
public class DevPayService extends BaserTirdpartyService implements IAdapterService {

    @Override
    public Map<String, Object> apply4ElementsAuth(Map<String, Object> params) {
        return null;
    }

    @Override
    public Map<String, Object> confirm4ElementsAuth(Map<String, Object> params) {
        return null;
    }

    /**
     * //四要素绑卡【需要验证码】
     *
     * @param params 参数
     *               params 在原有基础上需增加 host，url，mall_no 这3个参数
     * @return 返回值
     */
    @Override
    public Map<String, Object> apply4ElementsBindCard(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> confirm4ElementsBindCard(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> threeElementsAuth(Map<String, Object> params) {
        return null;
    }

    @Override
    public Map<String, Object> threeElementsBindCard(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> twoElementsBindCard(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    private Map<String, Object> sendQuickPayMsg(Map<String, Object> params) throws BusinessException {
        Map<String, Object> responseMap = new HashMap<>();
        //TODO 签名
        params.put("cert_sign", "testsign");
        //TODO 设置URL地址
        String host = MapUtils.getString(params, "host", "");
        String url = MapUtils.getString(params, "url_msgRequest", "");
        url = host + "/" + url;
        BaseHttpResponse baseHttpResponse = null;
        try {
            baseHttpResponse = doPost(params, url, null);
            if (baseHttpResponse.getStatusCode() != 200) {
                logger.info("【短验申请接口调用】===========第三方接口调用失败，【" + baseHttpResponse.getStatusCode() + "】" + baseHttpResponse.getErrorMessage());
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setRecode(BusinessMsg.CALL_REMOTE_ERROR);
                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.CALL_REMOTE_ERROR) + "：第三方接口调用失败");
                throw new BusinessException(baseResponse);
            }
            //将接口返回的数据转换成对象后返回
            JSONObject msgRequest_result = JSON.parseObject(baseHttpResponse.getEntityString());
            logger.info("【短验申请接口调用】========E支付返回：" + msgRequest_result);
            if (null != msgRequest_result) {
                JSONObject msgRequest_data = msgRequest_result.getJSONArray("data").getJSONObject(0);
                if (msgRequest_data == null) {
                    logger.info("【短验申请接口调用】========短信发送失败！");
                    responseMap.put("success", false);
                    responseMap.put("error_info", msgRequest_result.get("error_info"));
                } else {
                    responseMap.put("success", true);
                }
            } else {
                responseMap.put("success", false);
                responseMap.put("error_info", "请求异常");
            }
            return responseMap;
        } catch (Exception e) {
            logger.info("【短验申请接口调用】===========第三方接口返回的数据无法解析：" + baseHttpResponse.getEntityString());
            logger.error(e);
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
    public Map<String, Object> applyQuickPay(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("query_id", MapUtils.getString(params, "partner_serial_no", ""));
        responseMap.put("process_date", DateUtils.todayfulldata());
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> confirmyQuickPay(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("self_bank_flag","0");
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> applyGatewayPay(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("self_bank_flag","0");
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> payFromCompanyToUser(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> payFromUserToCompany(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("self_bank_flag","0");
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> withdrawInBatch(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        Map<String, Object> responseDateil_map = new HashMap<String, Object>();
        responseDateil_map.put("order_status", "1");
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> queryWithdraw(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        Map<String, Object> responseDateil_map = new HashMap<String, Object>();
        responseDateil_map.put("order_status", "1");
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return responseMap;
    }

    @Override
    public Map<String, Object> transferOfAccountInBank(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("self_bank_flag","0");
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        responseMap.put("host_req_serial_no","9999");
        return responseMap;
    }

    @Override
    public Map<String, Object> queryAccountOfCompany(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("open_bank", "1");
        responseMap.put("acct_name_ch", "1");
        responseMap.put("client_name", "1");
        responseMap.put("real_time_balance", "1");
        responseMap.put("today_amt", "1");
        responseMap.put("today_balance", "1");
        responseMap.put("yesterday_amt", "1");
        responseMap.put("yesterday_balance", "1");
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        return responseMap;
    }

    @Override
    public Map<String, Object> queryListOfCompanyTransfer(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("self_bank_flag","0");
        responseMap.put("order_status", OrderStatus.SUCCESS.getCode());
        responseMap.put("recode", BusinessMsg.SUCCESS);
        responseMap.put("remsg", BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        responseMap.put("array_tran_list",new ArrayList<>());
        return responseMap;
    }

    @Override
    public Map<String, Object> queryPayStatus(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("self_bank_flag","0");
        responseMap.put("recode",BusinessMsg.SUCCESS);
        responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        return responseMap;
    }

    @Override
    public Map<String, Object> unbindCard(Map<String, Object> params) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("recode",BusinessMsg.SUCCESS);
        responseMap.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        responseMap.put("order_status",OrderStatus.SUCCESS.getCode());
        return responseMap;
    }

    @Override
    public Map<String, Object> getRandomKey(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        if(null == params){
            result.put("recode",BusinessMsg.PARAMETER_ERROR);
            result.put("remsg",BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            result.put("order_status",OrderStatus.FAIL.getCode());
        }
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("random_key", MathUtil.getRandom(10000, 99999));
        result.put("random_value", MathUtil.getRandom(10000, 99999));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> checkPassowrd(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        if(null == params){
            result.put("recode",BusinessMsg.PARAMETER_ERROR);
            result.put("remsg",BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            result.put("order_status",OrderStatus.FAIL.getCode());
        }
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> setOrResetPassword(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        if(null == params){
            result.put("recode",BusinessMsg.PARAMETER_ERROR);
            result.put("remsg",BusinessMsg.getMsg(BusinessMsg.PARAMETER_ERROR));
            result.put("order_status",OrderStatus.FAIL.getCode());
        }
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicBindCard(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicCardUnbing(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicPhoneChange(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicCustomerChange(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicCustomerFreeze(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicCustomerUnfreeze(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> isicCustomerCancel(Map<String, Object> params) {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> epayBankFourInOneQuery(Map<String,Object> params){
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public String getAndSaveCheckFile(Map<String, Object> params, String fileName, String clearDate) {
        return null;
    }

    @Override
    public Map<String, Object> refundPay(Map<String, Object> params) throws BusinessException {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        return result;
    }

    @Override
    public Map<String, Object> registerEaccount(Map<String, Object> params) throws BusinessException {
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        result.put("bank_elec_no", SeqUtil.getRadomNum());
        return result;
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
        Map<String, Object> result = new HashedMap();
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return result;
    }

    @Override
    public Map<String, Object> bindAccountVerify(Map<String, Object> params) throws BusinessException {
        return null;
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
    public Map<String, Object> batchPayQueryTo(Map<String, Object> params) throws BusinessException {
        Map<String, Object> result = new HashedMap();
        result.put("pay_status","2");
        result.put("partner_serial_no","");
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg"," 信息不符");
        return result;
    }

    @Override
    public Map<String, Object> queryContractApp(Map<String, Object> params) throws BusinessException {
        Map<String, Object> result = new HashedMap();
        result.put("order_status","5");
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg"," 信息不符");
        return result;
    }

    @Override
    public Map<String, Object> queryContractAppTo(Map<String, Object> params) throws BusinessException {
        Map<String, Object> result = new HashedMap();
        result.put("order_status","4");
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg"," 签约成功");
        return result;
    }


    @Override
    public Map<String, Object> agrPayApply(Map<String, Object> sendParams) {
        Map<String, Object> result = new HashedMap();
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return result;
    }

    @Override
    public Map<String, Object> agrPayConfirm(Map<String, Object> sendParams) {
        Map<String, Object> result = new HashedMap();
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
        return result;
    }

    @Override
    public Map<String, Object> batchCollection(Map<String, Object> params) {
        logger.info("批量代扣入参："+ JSON.toJSON(params));
        /*JSONArray ja= JSON.parseArray(params.get("details").toString());
        JSONArray jsonresult=new JSONArray() ;
        for(int i=0;i<ja.size();i++){
            JSONObject jb= (JSONObject) ja.get(i);
            jb.put("pay_status","3");
            jsonresult.add(jb);
        }*/
        Map<String, Object> result = new HashedMap();
        result.put("order_status",OrderStatus.SUCCESS.getCode());
        result.put("recode",BusinessMsg.SUCCESS);
        result.put("remsg",BusinessMsg.getMsg(BusinessMsg.SUCCESS));
       // result.put("data",jsonresult);
        return result;
    }
}
