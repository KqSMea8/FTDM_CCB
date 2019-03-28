package com.sunyard.sunfintech.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.core.util.ParamterUtil;
import com.sunyard.sunfintech.core.util.StringUtils;
import com.sunyard.sunfintech.core.util.SysParamterKey;
import com.sunyard.sunfintech.web.business.SysBusiness;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用于接口权限控制
 * @author yanglei
 * @version 2018/03/10
 */
public class ApiPermissionInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SysBusiness sysParameterService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestUri = request.getRequestURI();
        logger.info("requestUri：" + requestUri);

        String whiteList = sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.WHITE_LIST);
        String url=sysParameterService.querySysParameter(SysParamterKey.FTDM, SysParamterKey.SKIP_CHECK_PWD);
        CacheUtil.getCache().set(Constants.SKIP_CHECK_PWD_KEY+"123456789" , url);

        String ip = getIpAddr(request);

        //获取请求参数
        Map<String, Object> map = ParamterUtil.getParamterMap(request);
        logger.info("`" + requestUri + "`，请求参数：" + map);
        String mall_no = "";
        String order_no = "";
        String token= "";
        if (map != null && map.size() > 0) {
            //获取订单号
            order_no = MapUtils.getString(map,"order_no","");
            mall_no = MapUtils.getString(map,"mall_no","");
            token = MapUtils.getString(map,"token","");
        }
        //判断是否在白名单
        logger.info("IP白名单："+whiteList+"|IP:"+ip);
        if(whiteList.contains(ip)){
            //如果请求地址在白名单中，设置订单在白名单中
            logger.info("【请求在白名单】：" + ip);
            if(StringUtils.isNotBlank(order_no)) {
                CacheUtil.getCache().set(Constants.ORDER_NO_IN_WHITELIST_KEY + order_no, true);
            }
            if(StringUtils.isBlank(mall_no)){
                return true;
            }
        }

        //如果集团号和token为空的，说明是非法请求
        if (StringUtils.isBlank(mall_no)&&StringUtils.isBlank(token)){
            logger.error("【接口权限拦截】mall_no和token不能都为空!" );
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setOrder_no(order_no);
            baseResponse.setSign("sign");
            baseResponse.setRecode(BusinessMsg.NO_PERMISSION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_PERMISSION));
            throw new BusinessException(baseResponse);
        }

        //获取方法URL
        String methodName =StringUtils.removeEnd(requestUri,"/");
        if (methodName.indexOf("/") >= 0) {
            methodName = methodName.substring(methodName.lastIndexOf("/") + 1);
        }

        boolean isHasPermission = true;
        if (StringUtils.isNotBlank(methodName)) {
            String privileges = sysParameterService.querySysParameter(mall_no, "api_permission");
            logger.info("`" + requestUri + "`，请求参数：" + map + ",privileges=" + privileges);
            if (StringUtils.isBlank(privileges)){
                logger.info("`" + requestUri + "`，请求参数：" + map + ",api_permission，默认为全部接口的权限");
                return true;
            }
            if (StringUtils.isNotBlank(privileges)) {
                String[] priviArr = StringUtils.split(privileges, ",");
                for (String privilege : priviArr) {
                    if (methodName.equalsIgnoreCase(privilege)) {
                        isHasPermission = false;
                        break;
                    }
                }
            }

        } else {
            logger.info("`" + requestUri + "`，请求参数：" + map + ",methodName=null");
        }

        logger.info("`" + requestUri + "`，请求参数：" + map + ",isHasPermission=" + isHasPermission);
        if (!isHasPermission) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setOrder_no(order_no);
            baseResponse.setSign("");
            baseResponse.setRecode(BusinessMsg.NO_PERMISSION);
            baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.NO_PERMISSION));
            throw new BusinessException(baseResponse);
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String ip = getIpAddr(request);
        String url = request.getRequestURI();

        logger.info(String.format("【请求记录】访问ip:%s,请求地址:%s", ip, url));
    }

    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }

    public static String getIpAddr(HttpServletRequest request) throws Exception{

        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//            logger.info("请求IP："+ip);
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
//                logger.info("请求IP："+ip);
                return ip;
            }
        } else {
//            logger.info("请求IP："+request.getRemoteAddr());
            return request.getRemoteAddr();
        }
    }
}
