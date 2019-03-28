/**
 * 
 */
package com.sunyard.sunfintech.prod.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @author heroy
 * @version 20170213
 */
public class QuartzJobFactory extends AdaptableJobFactory implements ApplicationContextAware {

	// 这个对象Spring会帮我们自动注入进来,也属于Spring技术范畴.
	// @Resource
	// private AutowireCapableBeanFactory capableBeanFactory;

	ApplicationContext applicationContext;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);

		// 进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
		AutowireCapableBeanFactory acb = applicationContext.getAutowireCapableBeanFactory();
		acb.autowireBeanProperties(jobInstance, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
		return jobInstance;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
