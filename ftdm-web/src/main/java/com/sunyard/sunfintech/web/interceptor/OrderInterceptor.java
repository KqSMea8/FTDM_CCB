package com.sunyard.sunfintech.web.interceptor;

import com.sunyard.sunfintech.web.business.SysBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wubin
 * @version 2017/7/31
 */
@Deprecated     //该注解将该类标记为已弃用
public class OrderInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SysBusiness sysParameterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        String order_on_off = sysParameterService.querySysParameter("FTDM","order_on_off");
//        if("1".equals(order_on_off)){//1是开启，默认为0关闭
//            //获取请求参数
//            Map<String,Object> map = ParamterUtil.getParamterMap(request);
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
    }

    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }
}
