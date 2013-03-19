/*
 * Copyright (C) 2013 The Zorba Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bbytes.zorba.domain;

/**
 * An {@link Enum} for priority of the queues
 * 
 * @author Dhanush Gopinath
 * @since 0.0.1
 * @version 0.0.1
 * 
 */
public enum Priority {

	CRITICAL(4, "zorba.queue.pr.critical"), HIGH(3, "zorba.queue.pr.high"), MEDIUM(2, "zorba.queue.pr.medium"), LOW(1,
			"zorba.queue.pr.low");

	int priority;
	
	String queueName;

	Priority(int priority,String queueName) {
		this.priority = priority;
		this.queueName=queueName;
	}

	public int getPriority() {
		return priority;
	}
	
	public String getQueueName() {
		return queueName;
	}
}
