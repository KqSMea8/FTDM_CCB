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
public @interface Log {
    /**
     * 描述
     * @return 描述字符串
     */
    String description() default "description";

    /**
     * 验证方式
     * @return SignType
     */
    String method() default "method";

    /**
     * 验证方式
     * @return SignType
     */
    String transCode() default "";

    /**
     * 批量数据字段名称
     * @return
     */
    String batchDataName() default "";
}
