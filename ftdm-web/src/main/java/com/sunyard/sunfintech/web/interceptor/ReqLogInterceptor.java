package com.sunyard.sunfintech.web.interceptor;

import com.sunyard.sunfintech.core.util.IpUtil;
import com.sunyard.sunfintech.core.util.ParamterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wubin
 * @version 2017/7/31
 */
public class ReqLogInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LogManager.getLogger(this.getClass());
//    @Autowired
//    private JmsTemplate jmsQueueTemplate;
    private Long startTime;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("`" + request.getRequestURI() + "`");
        startTime = System.currentTimeMillis();
//        String order_on_off = sysParameterService.querySysParameter("FTDM","order_on_off");
//        if("1".equals(order_on_off)){//1是开启，默认为0关闭
//            //获取请求参数
        Map<String, Object> map = ParamterUtil.getParamterMap(request);
        logger.info("`" + request.getRequestURI() + "`，请求参数：" + map);
//            String order_no = "";
//            if(map!=null && map.size()>0){
//                //获取订单号
//                order_no = (String) map.get("order_no");
//            }
//            Boolean flag = true;
//            if(!StringUtils.isEmpty(order_no)){
//                //有就返回false，没有返回true
//                flag = CacheUtil.getCache().setnx(order_no,order_no);
//                if(flag){
//                    //设置order_no键的有效时间为24小时，单位为秒
//                    CacheUtil.getCache().expire(order_no,24*60*60);
//                }
//            }
//            if(!flag){
//                //false，已经存在
//                BaseResponse baseResponse = new BaseResponse();
//                baseResponse.setOrder_no(order_no);
//                baseResponse.setSign("");
//                baseResponse.setRecode(BusinessMsg.ORDERNUMBER_REPEATED);
//                baseResponse.setRemsg(BusinessMsg.getMsg(BusinessMsg.ORDERNUMBER_REPEATED));
//                throw new BusinessException(baseResponse);
//            }
//        }
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
        String ip = IpUtil.getRemoteIP(request);
        String url = request.getRequestURI();
        //访问时间
        Long durationTime = System.currentTimeMillis() - startTime;
        String accessTime = Long.toString(TimeUnit.MILLISECONDS.toMillis(durationTime)) + "ms";
        logger.info(String.format("【请求记录】访问ip:%s,请求地址:%s,耗时：%s", ip, url, accessTime));
    }

    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }
}
