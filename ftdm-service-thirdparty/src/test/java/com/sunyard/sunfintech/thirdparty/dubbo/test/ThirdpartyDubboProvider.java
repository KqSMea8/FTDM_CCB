package com.sunyard.sunfintech.thirdparty.dubbo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo服务类测试接口；
 * 正式开启的服务用Dubbo提供的main方法
 */
public class ThirdpartyDubboProvider {
	
	private static final Log log = LogFactory.getLog(ThirdpartyDubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
			log.info("ThirdpartyDubboProvider context start");
		} catch (Exception e) {
			log.error("== ThirdpartyDubboProvider context start error:",e);
		}
		synchronized (ThirdpartyDubboProvider.class) {
			while (true) {
				try {
					ThirdpartyDubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}
}