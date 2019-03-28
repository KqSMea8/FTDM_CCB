package com.sunyard.sunfintech.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author heroy
 * @version 2017/5/4
 */
public interface GlobalConfig {

    /**
     * fastjson 配置
     */
    public static final SerializerFeature[] serializerFeature = new SerializerFeature[]{
            SerializerFeature.QuoteFieldNames, //输出key时是否使用双引号,默认为true
            SerializerFeature.WriteDateUseDateFormat, //是否输出值为null的字段,默认为false
            //SerializerFeature.WriteNullStringAsEmpty, //字符类型字段如果为null,输出为”“,而非null
            SerializerFeature.WriteNonStringValueAsString,//
            //SerializerFeature.WriteMapNullValue //是否输出值为null的字段,默认为false
    };

}
