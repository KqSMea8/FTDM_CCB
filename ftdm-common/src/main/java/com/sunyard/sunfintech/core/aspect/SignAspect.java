package com.sunyard.sunfintech.core.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.annotation.CheckPassword;
import com.sunyard.sunfintech.core.annotation.Sign;
import com.sunyard.sunfintech.core.annotation.SignType;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.dic.OrderStatus;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.support.security.AuthorizationAdapter;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import com.sunyard.sunfintech.core.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 验签切面
 * Created by heroy on 2017/4/7.
 */
@Aspect
//@Component
public class SignAspect {

    private final Logger logger = LogManager.getLogger();

    private SignAdapter signAdapter;
    private AuthorizationAdapter authorizationAdapter;



//    public SignAspect(){
//        super();
//    }
//
//    public SignAspect(SignAdapter signAdapter){
//        super();
//        this.signAdapter = signAdapter;
//        System.out.print("%%%%%%%%%%%%%%%%");
//    }

    @Autowired
    public SignAspect(SignAdapter signAdapter, AuthorizationAdapter authorizationAdapter){
        super();
        this.signAdapter = signAdapter;
        this.authorizationAdapter = authorizationAdapter;
        System.out.print("&&&&&&&&&&&&&&&&&&");
        logger.info("&&&&&&&&&&&&&&&&&&");
    }

    //Controller层切点
    @Pointcut("@annotation(com.sunyard.sunfintech.core.annotation.Sign)")
    public void controllerAspect() {

    }

    @Before(value = "controllerAspect() && args(httpServletRequest,baseModel)&& @annotation(sign)") //
    public void doBefore(JoinPoint joinPoint, HttpServletRequest httpServletRequest, BaseRequest baseModel , Sign sign) throws BusinessException  {
        //验证白名单，白名单是蓝且其过滤
//        boolean b = (boolean)httpServletRequest.getAttribute("in_white_list");
//        httpServletRequest.removeAttribute("in_white_list");
        boolean b =false;


        Map<String, Object> authItems = new HashedMap(5); //验密参数

        String signData = "";
        String orderNo = "";
        String origin_order_no_str="";
        String token_str="";
        TreeMap<String,String> map = new TreeMap();
        Enumeration paramNames = httpServletRequest.getParameterNames();
        //验密客户字段，谁出钱，验证谁的密码
        String platcust = sign.platcust();

        String requestUrl=httpServletRequest.getRequestURI();
        String methodName=requestUrl.substring(requestUrl.lastIndexOf("/")+1);
        //如果请求地址为提现申请，添加密码验证参数flag,默认值为T
        if (methodName.equalsIgnoreCase("withdraw_application")) {
            authItems.put("flag", "T");
        }

        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = httpServletRequest.getParameterValues(paramName);
            if (paramValues.length == 1) {
                //logger.info(paramName+ ":" + paramValues[0]);
                String paramValue = paramValues[0];
                if("sign".equals(paramName)) {
                    signData = paramValue;
                }
                if("order_no".equals(paramName)){
                    orderNo = paramValue;
                }
                if(platcust.equals(paramName)) {
                    //验密客户号，对应的是注解配置的客户号字段
                    authItems.put("platcust", paramValue);
                }
                if("trans_pwd".equals(paramName)){
                    authItems.put("trans_pwd", paramValue);
                }
                if ("random_key".equals(paramName)) {
                    authItems.put("random_key", paramValue);
                }
                if("password_type".equals(paramName)){
                    authItems.put("password_type",paramValue);
                }
                if("origin_order_no".equals(paramName)){
                    origin_order_no_str = paramValue;
                }
                if("token".equals(paramName)){
                    token_str = paramValue;
                }
                //将前端传送的报文参数放入map
                if (StringUtils.isNoneBlank(paramValue)) {
                    map.put(paramName, paramValue);
                }
            }
        }
        Map<String,String> params=new HashMap<>();
        params.put("origin_order_no_str",origin_order_no_str);
        params.put("token_str",token_str);
        params.put("order_no_str",orderNo);

        //是否参与验签
        SignType signType = sign.signType(); //SignType.NONE;//
        if(!SignType.NONE.equals(signType)){

            //String mallKey = Constants.CACHE_NAMESPACE + httpServletRequest.getParameter("order_no");
            //CacheUtil.getCache().set(mallKey, httpServletRequest.getParameter("mall_no"));
            logger.info("【authItems】："+ JSON.toJSON(authItems));
            boolean result = true;
            if("SDK".equals(baseModel.getVersion())){
                result = signAdapter.checkSign("SDK", signData, SunSignatureUtil.getSignContentFromTreeMap(map),params);
            }else {
                result = signAdapter.checkSign(baseModel.getMall_no(), signData, SunSignatureUtil.getSignContentFromTreeMap(map),params);
            }
            //验签异常，返回结果，加签
            if(!result){
                TreeMap<String, String> exTM = new TreeMap<>();
                BaseResponse baseResponse = new BaseResponse();
                if(StringUtils.isNoneBlank(orderNo)){
                    baseResponse.setOrder_no(orderNo);
                    exTM.put("order_no", orderNo);
                }
                baseResponse.setRecode(BusinessMsg.SIGNATURE_ERROR);
                baseResponse.setRemsg("验签失败");
                baseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                exTM.put("recode", BusinessMsg.SIGNATURE_ERROR);
                exTM.put("remsg", "验签失败");
                baseResponse.setSign(signAdapter.signUp(baseModel.getMall_no(),SunSignatureUtil.getSignContentFromTreeMap(exTM),params));
                throw new BusinessException(baseResponse);
            }
        }

        Object openAccountParamsObj = CacheUtil.getCache().get(Constants.SKIP_CHECK_PWD_KEY + "123456789");
        String url = openAccountParamsObj.toString();
        if(url!=null){
            String uri = httpServletRequest.getRequestURI();
            logger.info("库中值为", JSON.toJSONString(url));
            String name = uri.substring(requestUrl.lastIndexOf("/") + 1);
            logger.info("获取到的地址", JSON.toJSONString(uri));
            String[] textData = url.split(",");
            List<String> list = Arrays.asList(textData);
            for (String data : list) {
                if (data.equals(name)) {
                    logger.info("请求接口地址在白名单中，不验密");
                    return;
                }
            }
        }
            //密码验证
            CheckPassword checkPassword = sign.checkPassword(); //CheckPassword.YES;//
            if (CheckPassword.YES.equals(checkPassword)) {
                Map<String, Object> authResult;
                logger.info("【密码验证】开始");
                authItems.put("mall_no", baseModel.getMall_no());
                authItems.put("mer_no", baseModel.getMer_no());
                authResult = authorizationAdapter.checkPassword(authItems);
                String order_status = MapUtils.getString(authResult, "order_status");
                String recode = MapUtils.getString(authResult, "recode");
                String remsg = MapUtils.getString(authResult, "remsg");
                if (!OrderStatus.SUCCESS.getCode().equals(order_status)) {
                    logger.info("【密码验证】验密失败：" + recode + ":" + remsg);
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setRecode(recode);
                    baseResponse.setRemsg(MapUtils.getString(authResult, "remsg"));
                    baseResponse.setOrder_status(OrderStatus.FAIL.getCode());
                    TreeMap<String, String> signParams = new TreeMap<>();
                    signParams.put("recode", recode);
                    signParams.put("remsg", remsg);
                    baseResponse.setSign(signAdapter.signUp(baseModel.getMall_no(), SunSignatureUtil.getSignContentFromTreeMap(signParams), params));
                    throw new BusinessException(baseResponse);
                }
            } else {
                logger.info("【密码验证】:该交易不验密");
            }

    }



    @AfterReturning(pointcut = "controllerAspect()&&args(httpServletRequest,..)", returning = "returnValue")
    public void doAfter(JoinPoint joinPoint, BaseResponse returnValue, HttpServletRequest httpServletRequest) {
        Class returnValueClass = returnValue.getClass();
        Field[] returnValueFields = returnValueClass.getDeclaredFields();
        TreeMap<String, String> m = new TreeMap<String, String>();
        try {
            for(Field field : returnValueFields){
                //1、获取属性上的指定类型的注释
                Annotation annotation = field.getAnnotation(JSONField.class);
                //有该类型的注释存在
                if (annotation!=null || "serialVersionUID".equals(field.getName())) {
                    continue;
                }
                String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method method = returnValueClass.getMethod(methodName);
                Object str = method.invoke(returnValue);
                if(null != str)
                    m.put(field.getName(),String.valueOf(str));
            }
        } catch (NoSuchMethodException e) {
            logger.error("加签失败：",e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("加签失败：",e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            logger.error("加签失败：",e);
            e.printStackTrace();
        }

        m.put("recode",returnValue.getRecode());
        m.put("remsg",returnValue.getRemsg());
        m.put("trans_date",returnValue.getTrans_date());
        m.put("order_status",returnValue.getOrder_status());
        String order_no = returnValue.getOrder_no();
        if(StringUtils.isBlank(order_no)){
            order_no = httpServletRequest.getParameter("order_no");
        }
        m.put("order_no",order_no);
        returnValue.setOrder_no(order_no);

        //String mallKey = Constants.CACHE_NAMESPACE + returnValue.getOrder_no();
        //String mallNo = String.valueOf(CacheUtil.getCache().get(mallKey));

        Map<String,String> params=new HashMap<>();
        params.put("origin_order_no_str",httpServletRequest.getParameter("origin_order_no"));
        params.put("token_str",httpServletRequest.getParameter("token"));
        params.put("order_no_str",order_no);

        String sign = "加签失败";
        try{
            sign=signAdapter.signUp(httpServletRequest.getParameter("mall_no"),SunSignatureUtil.getSignContentFromTreeMap(m),params);
        }catch (Exception e){
            logger.error("加签失败：",e);
        }

        returnValue.setSign(sign);
    }

    public SignAdapter getSignAdapter() {
        return signAdapter;
    }

    public void setSignAdapter(SignAdapter signAdapter) {
        this.signAdapter = signAdapter;
    }
}
