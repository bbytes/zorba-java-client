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
package com.bbytes.zorba.jobworker.domain;

/**
 * Interface extension of {@link ZorbaResponse} for sending asynchronous response. 
 *
 * @author Dhanush Gopinath
 *
 * @version 0.0.1
 */
public interface ZorbaAsyncResponse extends ZorbaResponse {

	/**
	 * Sets the correlation id for the request
	 * 
	 * @param correlationId
	 */
	void setCorrelationId(String correlationId);
	
	/**
	 * Returns the correlation id
	 * 
	 * @return
	 */
	String getCorrelationId();
	
	/**
	 * Sets the reply queue
	 * @param replyQueue
	 */
	void setReplyQueue(String replyQueue);
	
	/**
	 * Returns the reply queue
	 * @return
	 */
	String getReplyQueue();
}
