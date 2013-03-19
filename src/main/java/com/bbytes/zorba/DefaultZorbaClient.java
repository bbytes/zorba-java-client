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

import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.domain.Priority;
import com.bbytes.zorba.domain.ZorbaRequest;
import com.bbytes.zorba.exception.ClientException;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
@Component
public class DefaultZorbaClient implements ZorbaClient {

	@Autowired(required=true)
	private RabbitOperations rabbitOperations;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#send(com.bbytes.zorba.domain.ZorbaRequest,
	 * com.bbytes.zorba.domain.Priority)
	 */
	@Override
	public void send(ZorbaRequest request, Priority priority) throws ClientException {
		if (request != null && priority != null) {
			String queueName = priority.getQueueName();
			rabbitOperations.convertAndSend(queueName, request);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#send(com.bbytes.zorba.domain.ZorbaRequest,
	 * java.lang.String)
	 */
	@Override
	public void send(ZorbaRequest request, String queueName) throws ClientException {
		if (request != null && queueName != null) {
			rabbitOperations.convertAndSend(queueName, request);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#sendAsync(com.bbytes.zorba.domain.ZorbaRequest,
	 * com.bbytes.zorba.domain.Priority, com.bbytes.zorba.AsyncResponseHandler)
	 */
	@Override
	public void sendAsync(final AsyncZorbaRequest request, Priority priority, AsyncResponseHandler asyncResponseHandler)
			throws ClientException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#sendAsync(com.bbytes.zorba.domain.ZorbaRequest,
	 * java.lang.String, com.bbytes.zorba.AsyncResponseHandler)
	 */
	@Override
	public void sendAsync(final AsyncZorbaRequest request, String queueName, AsyncResponseHandler asyncResponseHandler)
			throws ClientException {
		// TODO Auto-generated method stub

	}

}
