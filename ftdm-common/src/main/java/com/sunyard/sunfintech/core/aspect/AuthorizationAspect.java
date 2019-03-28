package com.sunyard.sunfintech.core.aspect;

import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.support.security.AuthorizationAdapter;
import com.sunyard.sunfintech.core.support.security.SignAdapter;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by heroy on 2017/4/7.
 */
@Aspect
@Order(1)
public class AuthorizationAspect {

    private final Logger logger = LogManager.getLogger();

    private AuthorizationAdapter authorizationAdapter;

    private SignAdapter signAdapter;

    //Controller层切点
    @Pointcut("@annotation(com.sunyard.sunfintech.core.annotation.Authorization)")
    public void controllerAspect() {

    }

    @Before("controllerAspect() && args(httpServletRequest,baseModel)")
    public void doBefore(JoinPoint joinPoint, HttpServletRequest httpServletRequest, BaseRequest baseModel) {
        Map<String, Object> authItems = new HashedMap(5);
        authItems.put("mall_no", baseModel.getMall_no());
        authItems.put("mer_no", baseModel.getMer_no());
        Enumeration paramNames = httpServletRequest.getParameterNames();
        int countParams = 0;
        while (paramNames.hasMoreElements()) {
            if(countParams ==3){
                break;
            }
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = httpServletRequest.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if("platcust".equals(paramName)) {
                    authItems.put("platcust", paramName);
                    countParams ++;
                }
                if("password".equals(paramName)){
                    authItems.put("password", paramName);
                    countParams ++;
                }
                if ("random_key".equals(paramValue)) {
                    authItems.put("random_key", paramName);
                    countParams ++;
                }
            }
        }
        BaseResponse baseResponse = new BaseResponse();
        if(countParams<3){

        }

    }

    @AfterReturning(pointcut = "controllerAspect()&&args(httpServletRequest,..)", returning = "returnValue")
    public void doAfter(JoinPoint joinPoint, Object returnValue, HttpServletRequest httpServletRequest) {

    }
}
