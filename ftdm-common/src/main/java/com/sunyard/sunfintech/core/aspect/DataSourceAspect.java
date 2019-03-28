package com.sunyard.sunfintech.core.aspect;

import com.sunyard.sunfintech.core.db.DbContextHolder;
import com.sunyard.sunfintech.core.db.DynamicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 切换数据源(不同方法调用不同数据源)
 * @author heroy
 * @version 20170401
 */
@Aspect
public class DataSourceAspect {
	private final Logger logger = LogManager.getLogger();

	/**
	 * 对service中的方法进行切面
	 */
	@Pointcut("execution(* com.sunyard.sunfintech.service..*.*(..))")
	public void aspect() {
	}

	/**
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 */
	@Before("aspect()")
	public void before(JoinPoint point) {
		String className = point.getTarget().getClass().getName();
		String method = point.getSignature().getName();
		logger.info(className + "." + method + "(" + StringUtils.join(point.getArgs(), ",") + ")");
		try {
			L: for (String key : DynamicDataSource.METHODTYPE.keySet()) {
				for (String type : DynamicDataSource.METHODTYPE.get(key)) {
					if (method.startsWith(type)) {
						logger.info(key);
						DbContextHolder.setDataSource(key);
						break L;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			DbContextHolder.setDataSource("write");
		}
	}

	@After("aspect()")
	public void after(JoinPoint point) {
		DbContextHolder.clear();
	}
}
