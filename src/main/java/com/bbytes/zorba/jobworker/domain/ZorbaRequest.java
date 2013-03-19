/**
 * 
 */
package com.bbytes.zorba.jobworker.domain;

import java.util.Map;

/**
 * The interface for a ZorbaRequest. This encapsulates the data sent by the
 * clients
 * 
 * @author Dhanush Gopinath
 * @version 0.0.1
 * @since 0.0.1
 * 
 */
public interface ZorbaRequest {

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
	 * Sets the queue name for this request. If set as null, then it takes the
	 * default priority queue name based on the priority set.
	 * 
	 * @param queueName
	 */
	void setQueueName(String queueName);

	/**
	 * Sets the custom payload data on the request
	 * 
	 * @param data
	 */
	void setData(Map<String, ?> data);

	/**
	 * Sets the priority from the Priority enum, {@link Priority}
	 * @param priority
	 */
	void setPriority(Priority priority);

	/**
	 * Sets the type of the request
	 * 
	 * @param type
	 */
	void setType(String type);

	/**
	 * Returns the id
	 * @return
	 */
	String getId();

	/**
	 * Returns the job name
	 * @return
	 */
	String getJobName();

	/**
	 * Returns the queue name
	 * @return
	 */
	String getQueueName();

	/**
	 * Returns the data payload
	 * @return
	 */
	Map<String, ?> getData();

	/**
	 * Returns the priority
	 * 
	 * @return
	 */
	Priority getPriority();

	/**
	 * Returns the type
	 * @return
	 */
	String getType();

}
