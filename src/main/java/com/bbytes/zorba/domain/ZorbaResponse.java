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
 * @version 0.0.1
 */
public class ZorbaResponse implements Serializable{


	private static final long serialVersionUID = -6754862309951736253L;

	protected String id;
	protected String jobName;
	protected Map<String, ?> result;
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

	public Map<String, ?> getResult() {
		return result;
	}

	public void setResult(Map<String, ?> result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
