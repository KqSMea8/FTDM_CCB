package com.sunyard.sunfintech.core.annotation;

import java.lang.annotation.*;

/**
 * 切面用于参数验证
 * 签名串验证
 * Created by heroy on 2017/4/7.
 */

@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SerialNoDetail {
    /**
     * 描述
     * @return 描述字符串
     */
    String description() default "";



    /**
     * 验证方式
     * @return SignType
     */
    String transCode() default "";


    /**
     * 是否批量
     * @return SignType
     */
    boolean isBatch() default false;

    /**
     * 忽略如果一条
     * @return SignType
     */
    boolean isIgnoreIfOneDetail() default false;
}
