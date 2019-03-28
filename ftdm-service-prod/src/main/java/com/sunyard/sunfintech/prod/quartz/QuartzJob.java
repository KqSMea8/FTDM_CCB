package com.sunyard.sunfintech.prod.quartz;

import java.util.Map;

/**
 * job的基础信息
 * @author heroy
 * @version 20170213
 */
public class QuartzJob {
	/**
	 * 任务名称
	 */
	private String jobName;

	/**
	 * 任务分组
	 */
	private String jobGroup;

	/**
	 * 任务状态 0禁用 1启用 2删除
	 */
	private Integer jobStatus;

	/**
	 * 任务运行时间表达式
	 */
	private String cronExpression;
	
	/**
	 * 需要用的的数据
	 */
	private Map<String,Object> data;

	public QuartzJob(){
		super();
	}
	
	/**
	 * 初始化job三要素
	 * @param jobName 名称
	 * @param jobGroup 组
	 * @param cronExpression 时间表达式
	 */
	public QuartzJob(String jobName, String jobGroup, String cronExpression){
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.cronExpression = cronExpression;
	}

	public QuartzJob(String jobName, String cronExpression){
		this.jobName = jobName;
		this.jobGroup = "default";
		this.cronExpression = cronExpression;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup
	 *            the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * @return the jobStatus
	 */
	public Integer getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus
	 *            the jobStatus to set
	 */
	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression
	 *            the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
