/*
 * Copyright (C) 2013 The Zorba Open Source Project 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.bbytes.zorba.domain;

import java.io.Serializable;

import com.bbytes.zorba.ZorbaClient;

/**
 * Domain class for making a request to the Zorba Job Execution. The client should send data as an
 * instance of this class, while sending the request using {@link ZorbaClient}
 * 
 * @author Dhanush Gopinath
 * @version 0.0.1
 * 
 */
public class ZorbaRequest implements Serializable {

	private static final long serialVersionUID = -6789865933652864865L;

	protected String id;
	protected String jobName;
	protected String queueName;

	protected ZorbaData<String, Serializable> data;

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

	public ZorbaData<String, Serializable> getData() {
		return data;
	}

	public void setData(ZorbaData<String, Serializable> data) {
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
