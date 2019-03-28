package com.sunyard.sunfintech.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 白名单验证移到ApiPermissionInterceptor里面
 * @author heroy
 * @version 2017/7/4
 */
@Deprecated
public class WhiteListInterceptor extends HandlerInterceptorAdapter{

//    private final Logger logger = LogManager.getLogger(this.getClass());

//    @Autowired
//    private SysBusiness sysParameterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        String is_sign = sysParameterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.IS_SIGN);
//        logger.info("是否验签：" + is_sign);
//        if(is_sign.equals("false")){
//            request.setAttribute("in_white_list", true);
//            return true;
//        }
//        String whiteList = sysParameterService.querySysParameter(SysParamterKey.FTDM,SysParamterKey.WHITE_LIST);
//        String ip = getIpAddr(request);
//        if(whiteList.contains(ip)){
//            request.setAttribute("in_white_list", true);
//        }else {
//            request.setAttribute("in_white_list", false);
//        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }


//    public static String getIpAddr(HttpServletRequest request) throws Exception{
//
//        String ip = request.getHeader("X-Real-IP");
//        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
////            logger.info("请求IP："+ip);
//            return ip;
//        }
//        ip = request.getHeader("X-Forwarded-For");
//        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//            // 多次反向代理后会有多个IP值，第一个为真实IP。
//            int index = ip.indexOf(',');
//            if (index != -1) {
//                return ip.substring(0, index);
//            } else {
////                logger.info("请求IP："+ip);
//                return ip;
//            }
//        } else {
////            logger.info("请求IP："+request.getRemoteAddr());
//            return request.getRemoteAddr();
//        }
//    }
}
