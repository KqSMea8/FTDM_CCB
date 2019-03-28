package com.sunyard.sunfintech.core.base;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.sunyard.sunfintech.core.Constants;
import com.sunyard.sunfintech.core.util.ExceptionUtil;

/**
 * 重要类
 * 统一提出接口后，利用反射去获取处理方法
 * @author heroy
 * @version 20170401
 */
public abstract class BaseProviderImpl implements ApplicationContextAware, BaseProvider {
    protected static Logger logger = LogManager.getLogger();
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /*
     * 更具参数内容，反射去获取处理的方法，并调用
     */
    @Override
    public Parameter execute(Parameter parameter) {
        String no = parameter.getNo();
        logger.info("{} request：{}", no, JSON.toJSONString(parameter));
        Object service = applicationContext.getBean(parameter.getService());
        try {
            Long id = parameter.getId();
            BaseModel model = parameter.getModel();

            List<?> list = parameter.getList();
            Map<?, ?> map = parameter.getMap();
            Object result = null;
            MethodAccess methodAccess = MethodAccess.get(service.getClass());
            if (id != null) {
                result = methodAccess.invoke(service, parameter.getMethod(), parameter.getId());
            } else if (model != null) {
                result = methodAccess.invoke(service, parameter.getMethod(), parameter.getModel());
            } else if (list != null) {
                result = methodAccess.invoke(service, parameter.getMethod(), parameter.getList());
            } else if (map != null) {
                result = methodAccess.invoke(service, parameter.getMethod(), parameter.getMap());
            } else {
                result = methodAccess.invoke(service, parameter.getMethod());
            }
            if (result != null) {
                Parameter response = new Parameter(result);
                logger.info("{} response：{}", no, JSON.toJSONString(response));
                return response;
            }
            logger.info("{} response empty.", no);
            return null;
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(no + " " + Constants.EXCEPTION_HEAD + msg, e);
            throw e;
        }
    }
}
