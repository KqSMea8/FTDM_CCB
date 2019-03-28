package com.sunyard.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleProvider {

	private static final Log log = LogFactory.getLog(ScheduleProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== ScheduleProvider context start error:",e);
		}
		synchronized (ScheduleProvider.class) {
			while (true) {
				try {
					ScheduleProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:",e);
				}
			}
		}
	}

}
