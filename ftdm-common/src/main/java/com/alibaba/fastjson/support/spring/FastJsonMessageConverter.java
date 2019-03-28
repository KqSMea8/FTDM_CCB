package com.alibaba.fastjson.support.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

/**
 * @author heroy
 * @version 2018/1/19
 */
public class FastJsonMessageConverter extends AbstractJsonMessageConverter {
    @Override
    protected Message createMessage(Object o, MessageProperties messageProperties) {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return null;
    }
}
