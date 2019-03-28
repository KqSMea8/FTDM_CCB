package com.sunyard.sunfintech.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UrlPropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(UrlPropertiesUtil.class);
    private static Properties props = new Properties();;

    private UrlPropertiesUtil() {}

    static{
        InputStream is = UrlPropertiesUtil.class.getClassLoader().getResourceAsStream("config_bak.properties");
        try {
            props.load(is);
        } catch (IOException e) {
            logger.error("读取配置文件出错", e);
        }
    }


    /**
     * 获取某个属性
     */
    public static String getVal(String key) {
        return props.getProperty(key);
    }

    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
    public static Map<String,Object> getAllVal() {
        Map<String,Object> map = new HashMap<>();
        Enumeration<?> enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getVal("cert_sign"));
    }
}

