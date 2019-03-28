package com.sunyard.test;

import com.sunyard.sunfintech.dao.entity.ClearResult;
import com.sunyard.sunfintech.schedule.bean.BillcheckBean;
import com.sunyard.sunfintech.schedule.service.ScheduleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Dubbo服务类测试接口；
 * 正式开启的服务用Dubbo提供的main方法
 */
public class SpringBootstrap {
	
	private static final Logger log = LogManager.getLogger(SpringBootstrap.class);
	public static void main(String[] args) throws InterruptedException {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-context.xml");
			context.start();
			ScheduleService scheduleService = context.getBean(ScheduleService.class);
			List<ClearResult> a = scheduleService.queryNoCheckedClearResultByDate(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("== BillCheckDubboProvider context start error:",e);
		}
		synchronized (SpringBootstrap.class) {
			SpringBootstrap.class.wait();
		}
	}
}