package com.sunyard.sunfintech.web.security;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import com.sunyard.sunfintech.core.util.*;
import com.sunyard.sunfintech.web.business.SysBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by terry on 2018/3/21.
 */
@Service("BankSign")
public class BankSign implements SignAdapter {

    private SignAdapter signSelector;

    @Value("${deploy.environment}")
    private String deployEnvironment;

    @Autowired
    private SysBusiness sysParameterService;

    public static final Map<String, String> BANK_SIGN_SET = new HashMap<String, String>() {
        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 1L;

        {
            put("BOB", "BOBSign"); //北京银行
            put("CCB", "CCBSign"); //建行
        }
    };

    public BankSign(){
        super();
    }

    private void selectBank(){
        String paymentChannel = BANK_SIGN_SET.get(deployEnvironment);
        if (null == paymentChannel || "".equals(paymentChannel.trim())) {
            throw new BusinessException("999999", "【三方模块】错误的银行通道配置");
        }
        //利用反射byName创建bean
        signSelector = SpringContextHolder.getBean(paymentChannel);
    }

    @Override
    public String signUp(String mall_no, String destContent, Map<String,String> params) {
        //判断验签是否加签
//        Boolean isSignUp=checkIsSign(params,2);
//        if(!isSignUp){
//            return "该交易不加签";
//        }
        selectBank();
        return signSelector.signUp(mall_no,destContent,null);
    }

    @Override
    public boolean checkSign(String mall_no, String signData, String destContent, Map<String,String> params) {
        //判断是否验签
        Boolean isSign=checkIsSign(params,1);
        if(!isSign){
            return true;
        }
        selectBank();
        return signSelector.checkSign(mall_no,signData,destContent,null);
    }

    private Boolean checkIsSign(Map<String,String> params,Integer signFlag){
        Boolean isSign=true;
        //获取token
        if(StringUtils.isNotBlank(params.get("token_str")) && signFlag==1){
            //获取原订单号
            String new_origin_token_str= Constants.TOKEN_STATIC_STR+params.get("origin_order_no_str");
            Object redis_token_obj= CacheUtil.getCache().get(new_origin_token_str);
            if(redis_token_obj==null){
                throw new BusinessException(BusinessMsg.SIGNATURE_ERROR,"token已过期");
            }
            //重置过期时间
            CacheUtil.getCache().expire(new_origin_token_str,1800);
            String new_token_str= MD5.MD5Encode(new_origin_token_str,Constants.CHARACTERENCODING);
            if(new_token_str.equals(params.get("token_str"))){
                //如果token验证通过，不验签
                isSign=false;
            }else{
                throw new BusinessException(BusinessMsg.SIGNATURE_ERROR,BusinessMsg.getMsg(BusinessMsg.SIGNATURE_ERROR));
            }
        }else{
            //获取订单号
            if(StringUtils.isNotBlank(params.get("order_no_str"))){
                String redis_key=Constants.ORDER_NO_IN_WHITELIST_KEY+params.get("order_no_str");
                Object redis_obj= CacheUtil.getCache().get(redis_key);
                if(redis_obj!=null){
                    //如果redis中存在相应的key-value，在白名单中，不验签
                    isSign=false;
                    if(2==signFlag){
                        CacheUtil.getCache().del(redis_key);
                    }
                }else{
                    //不在白名单中
                    if(1==signFlag){
                        //验签
                        String signSwitch=sysParameterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.IS_SIGN);
                        if(StringUtils.isBlank(signSwitch) || "false".equals(signSwitch)){
                            //未配置验签或者验签关闭，不验签
                            isSign=false;
                        }
                    }
                }
            }else{
                isSign=false;
            }
        }
        return isSign;
    }
}
