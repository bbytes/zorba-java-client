/**
 * 
 */
package com.bbytes.zorba.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author Dhanush Gopinath
 * 
 * 
 */
public class ZorbaRequest implements Serializable{

	private static final long serialVersionUID = -6789865933652864865L;

	protected String id;
	protected String jobName;
	protected String queueName;
	protected Map<String, ?> data;
	protected Priority priority;
	protected String type;

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
