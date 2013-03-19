/**
 * 
 */
package com.bbytes.zorba.jobworker.domain.impl;

import java.util.Map;

import com.bbytes.zorba.jobworker.domain.Priority;
import com.bbytes.zorba.jobworker.domain.ZorbaRequest;

/**
 * Default implementation of {@link ZorbaRequest}
 * 
 * @author Dhanush Gopinath
 * 
 * 
 */
public class ZorbaRequestImpl implements ZorbaRequest {

	private String id;
	private String jobName;
	private String queueName;
	private Map<String, ?> data;
	private Priority priority;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Map<String, ?> getData() {
		return data;
	}

	public void setData(Map<String, ?> data) {
		this.data = data;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
