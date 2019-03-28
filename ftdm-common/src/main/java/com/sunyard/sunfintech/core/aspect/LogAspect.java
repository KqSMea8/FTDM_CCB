package com.sunyard.sunfintech.core.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.annotation.Log;
import com.sunyard.sunfintech.core.base.BaseRequest;
import com.sunyard.sunfintech.core.base.BaseResponse;
import com.sunyard.sunfintech.core.threads.SingleThreadPool;
import com.sunyard.sunfintech.core.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.AmqpTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 验签切面
 * Created by heroy on 2017/4/7.
 */
@Aspect
//@Component
public class LogAspect {

    private final Logger logger = LogManager.getLogger();

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    public LogAspect() {
        super();
    }

    //Controller层切点
    @Pointcut("@annotation(com.sunyard.sunfintech.core.annotation.Log)")
    public void controllerAspect() {

    }
    @Before(value = "controllerAspect() && args(httpServletRequest,baseModel)&& @annotation(log)")
    public void doBefore(final JoinPoint joinPoint, final HttpServletRequest httpServletRequest, BaseRequest baseModel, final Log log) {
        try {
            final Map<String, Object> map = ParamterUtil.getParamterMap(httpServletRequest);
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Map<String, Object> msg = new HashMap<>();
                    msg.put("plat_no", MapUtils.getString(map, "plat_no", "plat_no"));
                    msg.put("req_ip", Constants.IP);
                    msg.put("req_date", MapUtils.getString(map, "trans_date", DateUtils.getyyyyMMddDate()));
                    msg.put("method", log.method()==null?"method":log.method());
                    msg.put("method_name", log.method()==null?"method_name":log.method());
                    msg.put("class_name", log.method()==null?"class_name":log.method());

                    msg.put("create_time", new Date());
                    msg.put("create_by", SeqUtil.RANDOM_KEY);
                    msg.put("remark", "begin");
                    if(StringUtils.isNotBlank(log.batchDataName())){
                        JSONArray jsonArray=JSON.parseArray(String.valueOf(map.get(log.batchDataName())));
                        //添加明细日志
                        for(Object object:jsonArray){
                            JSONObject jo= (JSONObject) object;
                            msg.put("order_no", StringUtils.isNotBlank(jo.getString("detail_no"))?jo.getString("detail_no"):SeqUtil.getSeqNum());
                            msg.put("req_param", JSON.toJSONString(jo));
                            sendRedLogMsg(msg);
                        }
                        //添加批次日志
                        msg.put("order_no", MapUtils.getString(map, "order_no", SeqUtil.getSeqNum()));
                        map.remove(log.batchDataName());
                        msg.put("req_param", JSON.toJSONString(map));
                        sendRedLogMsg(msg);
                    }else{
                        msg.put("order_no", MapUtils.getString(map, "order_no", SeqUtil.getSeqNum()));
                        msg.put("req_param", JSON.toJSONString(map));
                        sendRedLogMsg(msg);
                    }
                }
            });
        } catch (Exception e) {
            logger.info("【日志记录】【进入】失败：" + e.getMessage());
        }
    }

    @AfterReturning(pointcut = "controllerAspect()&&args(httpServletRequest,..)", returning = "returnValue")
    public void doAfter(JoinPoint joinPoint, final BaseResponse returnValue, HttpServletRequest httpServletRequest) {
        try {
            final String order_no = returnValue.getOrder_no();
            if (StringUtils.isNotBlank(order_no)) {
                SingleThreadPool.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> msg = new HashMap<>();
                        msg.put("plat_no", "plat_no");
                        msg.put("order_no", order_no);
                        msg.put("req_date", returnValue.getTrans_date());
                        msg.put("req_ip", Constants.IP);
                        msg.put("method", "method");
                        msg.put("class_name", "class_name");
                        msg.put("method_name", "method_name");

                        msg.put("req_result", JSON.toJSONString(returnValue));
                        msg.put("remark","return");
                        msg.put("update_time", new Date());
                        sendRedLogMsg(msg);
                    }
                });
            }
        } catch (Exception e) {
            logger.info("【日志记录】【失败】失败：" + e.getMessage());
        }
    }

    private void sendRedLogMsg(final Map<String, Object> map) {
        try {
//            jmsQueueTemplate.send("InsertReqLogQueue", new MessageCreator() {
//                @Override
//                public Message createMessage(Session session) throws JMSException {
//                    Message message = jmsQueueTemplate.getMessageConverter().toMessage(JSON.toJSONString(map), session);
//                    return message;
//                }
//            });
            MQUtils.send(amqpTemplate, "ftdm.web.direct.exchange", "InsertReqLogQueue", map);
        } catch (Exception e) {
            logger.info("【日志记录】【消息队列】失败：" + e.getMessage());
        }
    }
}
