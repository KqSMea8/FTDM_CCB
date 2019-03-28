/**
 * 
 */
package com.sunyard.sunfintech.core.base;

import com.alibaba.fastjson.JSON;
import com.sunyard.sunfintech.core.exception.BusinessMsg;
import com.sunyard.sunfintech.core.exception.BusinessException;
import com.sunyard.sunfintech.core.support.validator.BeanValidators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;


/**
 * 控制器基类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseController {
	protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private Validator validator;

    /**
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：抛出异常
     */
    protected boolean validate(Object object, Class<?>... groups) {
        try{
            BeanValidators.validateWithException(validator, object, groups);
        }catch(ConstraintViolationException ex){
            logger.debug(ex.getMessage());
            Map<String, String> map = BeanValidators.extractPropertyAndMessage(ex);
            throw new BusinessException(BusinessMsg.PARAMETER_LACK, JSON.toJSONString(map));
        }
        return true;
    }

    /**
     * 返回页面
     * @param viewName
     * @param context
     * @return
     */
    public ModelAndView forword(String viewName, @SuppressWarnings("rawtypes") Map context) {
        return new ModelAndView(viewName, context);
    }

}
