/**
 * 
 */
package com.bbytes.zorba.jobworker.domain.impl;

import java.util.Map;

import com.bbytes.zorba.jobworker.domain.ZorbaResponse;

/**
 * Default implementation of {@link ZorbaResponse}
 * 
 * @author Dhanush Gopinath
 * 
 * 
 */
public class ZorbaResponseImpl implements ZorbaResponse {

	private String id;
	private String jobName;
	private Map<String, ?> data;
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

	public Map<String, ?> getData() {
		return data;
	}

	public void setData(Map<String, ?> data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
