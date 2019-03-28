package com.sunyard.sunfintech.web.interceptor;

import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.web.business.SysBusiness;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * 用于对请求路径中的接口做权限拦截
 * @author RaoYL
 * @version 20190327
 */
public class RequestPermissionInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SysBusiness sysParamterService;

    /**
     * 在DispatcherServlet(在控制器方法调用)之前执行
     * 返回值为是否中断，true,表示继续执行（下一个拦截器或处理器）
     * false则会中断后续的所有操作，所以我们需要使用response来响应请求
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("RequestPermissionInterceptor preHandle executed requestURI:"+requestURI);
        String whiteList = sysParamterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.WHITE_LIST);
        String checkPasswordURL = sysParamterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.SKIP_CHECK_PWD);
        CacheUtil.getCache().set(SysParamterKey.SKIP_CHECK_PWD+"123456789",checkPasswordURL);
        //获取请求参数
        Map<String,String> paramMap = this.getParamMap(request);
        logger.info("RequestPermissionInterceptor preHandle executed paramMap:"+paramMap);
        String mall_no = paramMap.get("mall_no");
        String order_no = paramMap.get("order_no");
        String token = paramMap.get("token");
        //判断请求地址是否在白名单中
        String ipAddr = this.getIpAddr(request);
        logger.info("RequestPermissionInterceptor preHandle executed whiteList:"+whiteList);
        logger.info("RequestPermissionInterceptor preHandle executed ipAddr:"+ipAddr);
        if(whiteList.contains(ipAddr)){//如果请求IP在白名单中
            if(StringUtils.isNotBlank(order_no)){
                CacheUtil.getCache().set(Constants.ORDER_NO_IN_WHITELIST_KEY+order_no,true);
            }
            if(StringUtils.isBlank(mall_no)){
                return true;
            }
        }
        //如果集团号和token都为空，说明是非法请求
        if(StringUtils.isBlank(mall_no) && StringUtils.isBlank(token)){
            logger.error("【接口权限拦截】请求参数中mall_no和token不能都为空");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setOrder_no(order_no);
            baseResponse.setSign("sign");
            baseResponse.setRecode(BusinessMsg.NO_PERMISSION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_PERMISSION));
            throw new BusinessException(baseResponse);
        }
        //通过requestURI获取请求方法名称
        String methodName = StringUtils.removeEnd(requestURI,"/");
        if(methodName.indexOf("/") == 0){
            methodName = methodName.substring(methodName.indexOf("/")+1);
        }
        //初始情况默认所有请求接口都有权限
        boolean isHasPermission = true;
        if(StringUtils.isNotBlank(methodName)){
            //接口权限控制开关
            String privileges = sysParamterService.querySysParameter(SysParamterKey.FTDM,"api_permission");
            logger.info("【接口权限拦截】**************privileges:"+privileges);
            if(StringUtils.isBlank(privileges)){
                logger.info("【接口权限拦截】**************api_permission默认为所有接口的权限");
                return true;
            }
            if(StringUtils.isNotBlank(privileges)){
                String[] priviArray = privileges.split(",");
                for(String privilege:priviArray){
                    if(methodName.equalsIgnoreCase(privilege)){
                        isHasPermission = false;
                        break;
                    }
                }
            }
        }else{
            logger.error("【接口权限拦截】**************methodName为空,请确认请求参数");
        }
        logger.info("【接口权限拦截】**************isHasPermission："+isHasPermission);
        if(!isHasPermission){
            logger.info("【接口权限拦截】**************"+methodName+"接口没有访问权限");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setOrder_no(order_no);
            baseResponse.setSign("sign");
            baseResponse.setRecode(BusinessMsg.NO_PERMISSION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_PERMISSION));
            throw new BusinessException(baseResponse);
        }
        return true;
    }
    public Map<String,String> getParamMap(HttpServletRequest httpServletRequest){
        Enumeration parameterNames = httpServletRequest.getParameterNames();
        Map<String,String> paramMap = new HashMap<String,String>();
        while (parameterNames.hasMoreElements()){
            String paramName = (String) parameterNames.nextElement();
            String[] paramValues = httpServletRequest.getParameterValues(paramName);
            if(null!=paramValues && paramValues.length==1){
                String paramValue = paramValues[0];
                if(null!=paramValue){
                    paramMap.put(paramName,paramValue);
                }
            }
        }
        return paramMap;
    }
    public  String getIpAddr(HttpServletRequest httpServletRequest){
        String ipAddr = httpServletRequest.getHeader("X-Real-IP");
        if(!StringUtils.isBlank(ipAddr) &&!"unknown".equals(ipAddr) ){
            logger.info("RequestPermissionInterceptor preHandle executed IpAddr："+ipAddr);
            return ipAddr;
        }
        ipAddr = httpServletRequest.getHeader("X-Forwarded-For");
        if(!StringUtils.isBlank(ipAddr) && !"unknown".equals(ipAddr)){
            //多次反向代理之后会生成多个IP地址，第一个为真是IP
            int index = ipAddr.indexOf(",");
            if(index != -1){
                return ipAddr.substring(0,index);
            }else {
                return ipAddr;
            }
        }else {
            return httpServletRequest.getRemoteAddr();
        }
    }
    /**
     * 在controller方法调用之后，解析视图前调用，我们可以对视图和模型做进一步渲染或修改
     * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
            throws Exception {
        System.out.println("************RequestPermissionInterceptor postHandle executed**********");
    }

    /**
     * 在页面渲染完成返回给客户端之前执行
     * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String ipAddr = this.getIpAddr(request);
        String requestURI = request.getRequestURI();
        logger.info(String.format("【请求记录信息】访问IP:%s,请求地址:%s",ipAddr,requestURI));
    }

    /**
     *该方法用于处理异步请求，当Controller中有异步请求方法的时候会触发该方法时，
     * 异步请求先支持preHandle、然后执行afterConcurrentHandlingStarted。
     * 异步线程完成之后执行preHandle、postHandle、afterCompletion。
     */
    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }
}


