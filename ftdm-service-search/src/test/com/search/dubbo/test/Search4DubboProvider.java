package com.search.dubbo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dubbo服务类测试接口；
 * 正式开启的服务用Dubbo提供的main方法
 */
public class Search4DubboProvider {
	
	private static final Log log = LogFactory.getLog(Search4DubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== SearchDubboProvider context start error:",e);
		}
		synchronized (Search4DubboProvider.class) {
			while (true) {
				try {
					Search4DubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}
}