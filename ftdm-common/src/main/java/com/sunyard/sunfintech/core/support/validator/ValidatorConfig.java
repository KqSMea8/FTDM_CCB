package com.sunyard.sunfintech.core.support.validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * 配置 JSR303 Bean Validator 定义
 *
 * @author heroy
 * @version 2017/4/10
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(HibernateValidator.class);
        return validator;
    }
}
