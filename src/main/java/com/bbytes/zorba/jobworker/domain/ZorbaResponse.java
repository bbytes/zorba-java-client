/**
 * 
 */
package com.bbytes.zorba.jobworker.domain;

import java.util.Map;


/**
 * The interface for a ZorbaResponse. This encapsulates the data received by the
 * clients
 * 
 * @author Dhanush Gopinath
 * @version 0.0.1
 * @since 0.0.1
 * 
 */
public interface ZorbaResponse {

	/**
	 * Sets the id for this request
	 * 
	 * @param id
	 */
	void setId(String id);

	/**
	 * Sets the unique job name for this request
	 * 
	 * @param jobName
	 */
	void setJobName(String jobName);

	/**
	 * Sets the custom payload data on the request
	 * 
	 * @param data
	 */
	void setData(Map<String, ?> data);
	/**
	 * Sets the type of the request
	 * 
	 * @param type
	 */
	void setType(String type);

	/**
	 * Returns the id
	 * 
	 * @return
	 */
	String getId();

	/**
	 * Returns the job name
	 * 
	 * @return
	 */
	String getJobName();

	/**
	 * Returns the data payload
	 * 
	 * @return
	 */
	Map<String, ?> getData();
	/**
	 * Returns the type
	 * 
	 * @return
	 */
	String getType();

}
