package com.sunyard.sunfintech.core.annotation;

import java.lang.annotation.*;

/**
 * 切面用于参数验证
 * 签名串验证
 * Created by heroy on 2017/4/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sign {
    /**
     * 描述
     * @return 描述字符串
     */
    String description() default "";

    /**
     * 验证方式
     * @return SignType
     */
    SignType signType() default SignType.ALL;

    /**
     * 是否验密
     * @return
     */
    CheckPassword checkPassword() default CheckPassword.NO;

    /**
     * added by yanglei 20180302
     * 接口客户主体字段，默认platcust是接口的用户主体，密码验证是验证这个platcust字段对应用户的密码
     * 但是有例外，如：代偿人还款验证的是字段compensation_platcust的密码
     * @return 验密客户字段
     */
    String platcust() default "platcust";
}
