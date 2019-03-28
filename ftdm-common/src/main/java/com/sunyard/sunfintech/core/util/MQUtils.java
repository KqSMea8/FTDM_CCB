package com.sunyard.sunfintech.core.util;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.config.GlobalConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author heroy
 * @version 2018/1/3
 */
public class MQUtils {

    private static final Logger logger = LogManager.getLogger(MQUtils.class);

    /**
     * 消息发送
     * @param amqpTemplate 消息模板类
     * @param exchange 交换机名称
     * @param routingKey 队列key名称
     * @param object 消息体
     */
    public static boolean send(final AmqpTemplate amqpTemplate, final String exchange, final String routingKey, final Object object){
        try{
            amqpTemplate.send(exchange,routingKey,getMessge(object));
            return true;
        }catch (AmqpException e){
            logger.error("【发送消息】失败：" + e.getMessage());
            return false;
        }
    }

    public static Message getMessge(Object object){
        logger.info("【转换消息】消息内容：" + object);
        try {
            byte [] msgBody = JSON.toJSONString(object, GlobalConfig.serializerFeature).getBytes("UTF-8");
            return new Message(msgBody, new MessageProperties());
        } catch (UnsupportedEncodingException e) {
            logger.error("【转换消息】失败：" + e.getMessage());
        }
        return null;
    }

    public static <T>  T getObject(Message message, Class<T> clazz){
        try {
            String msg = new String(message.getBody(),"UTF-8");
            logger.info("【消息接收】消息内容body：" + msg);
            return JSON.parseObject(msg, clazz);
        } catch (UnsupportedEncodingException e) {
            logger.error("【转换消息】失败：" + e.getMessage());
        }
        return null;
    }

//    public static <T> List<T> getObjectArray(Message message, Class<T> clazz){
    public static <T> List<T> getArrayObject(Message message, Class<T> clazz){
        try {
            String msg = new String(message.getBody(),"UTF-8");
            logger.info("【消息接收】消息内容body：" + msg);
            return JSON.parseArray(msg, clazz);
        } catch (UnsupportedEncodingException e) {
            logger.error("【转换消息】失败：" + e.getMessage());
        }
        return null;
    }

}
