package com.sunyard.sunfintech.core.aspect;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 切面配置类
 * @author heroy
 * @version 2017/4/10
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {
}
