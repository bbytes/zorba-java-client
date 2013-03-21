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
package com.bbytes.zorba;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.domain.Priority;
import com.bbytes.zorba.domain.ZorbaRequest;
import com.bbytes.zorba.exception.ZorbaClientException;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;

/**
 * The zorba client interface that can send zorba request to job server
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public interface ZorbaClient {

	/**
	 * Sends the request to the Queue based on the priority
	 * 
	 * @param request
	 * @throws ZorbaClientException
	 */
	void send(final ZorbaRequest request, Priority priority) throws ZorbaClientException;

	/**
	 * Sends the request to the specified Queue
	 * 
	 * @param request
	 * @throws ZorbaClientException
	 */
	void send(final ZorbaRequest request, String queueName) throws ZorbaClientException;

	/**
	 * Sends async request to the Queue based on the priority
	 * 
	 * @param request
	 * @throws ZorbaClientException
	 */
	void sendAsync(final AsyncZorbaRequest request, Priority priority, ZorbaAsyncResponseCallBackHandler asyncResponseHandler)
			throws ZorbaClientException;

	/**
	 * Sends async request to the specified Queue
	 * 
	 * @param request
	 * @throws ZorbaClientException
	 */
	void sendAsync(final AsyncZorbaRequest request, String queueName, ZorbaAsyncResponseCallBackHandler asyncResponseHandler)
			throws ZorbaClientException;


}
