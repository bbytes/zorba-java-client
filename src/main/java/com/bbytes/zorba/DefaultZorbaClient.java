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

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.domain.Priority;
import com.bbytes.zorba.domain.ZorbaRequest;
import com.bbytes.zorba.exception.ZorbaClientException;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;
import com.bbytes.zorba.handler.impl.ZorbaSendAsyncCallable;
import com.bbytes.zorba.listener.ZorbaAsyncResponseCallBackProcessor;
import com.bbytes.zorba.listener.ZorbaMessageListenerContainer;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
@Component
public class DefaultZorbaClient implements ZorbaClient,DisposableBean  {

	@Autowired
	private RabbitOperations rabbitOperations;

	@Autowired
	private ZorbaMessageListenerContainer zorbaMessageListenerContainer;

	@Autowired
	private ZorbaAsyncResponseCallBackProcessor asyncResponseCallBackProcessor;

	private ExecutorService executor = Executors.newCachedThreadPool();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#send(com.bbytes.zorba.domain.ZorbaRequest,
	 * com.bbytes.zorba.domain.Priority)
	 */
	@Override
	public void send(ZorbaRequest request, Priority priority) throws ZorbaClientException {
		if (request != null && priority != null) {
			processRequestBeforeSend(request);
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
	public void send(ZorbaRequest request, String queueName) throws ZorbaClientException {
		if (request != null && queueName != null) {
			processRequestBeforeSend(request);
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
	public Future<Boolean> sendAsync(final AsyncZorbaRequest request, Priority priority,
			ZorbaAsyncResponseCallBackHandler asyncResponseHandler) throws ZorbaClientException {
		if (request == null) {
			new AsyncResult<Boolean>(false);
		}

		return sendAsync(request, priority.getQueueName(), asyncResponseHandler);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#sendAsync(com.bbytes.zorba.domain.ZorbaRequest,
	 * java.lang.String, com.bbytes.zorba.AsyncResponseHandler)
	 */
	@Override
	public Future<Boolean> sendAsync(final AsyncZorbaRequest request, String queueName,
			ZorbaAsyncResponseCallBackHandler asyncResponseHandler) throws ZorbaClientException {
		Future<Boolean> responseRecieved = new AsyncResult<Boolean>(false);
		if (request != null && queueName != null) {
			try {
				processRequestBeforeSend(request);
				ZorbaSendAsyncCallable sendAsyncCallable = new ZorbaSendAsyncCallable(rabbitOperations, queueName,
						request, asyncResponseHandler);

				asyncResponseCallBackProcessor.setCallBackHandler(request.getCorrelationId(), sendAsyncCallable);
				responseRecieved = executor.submit(sendAsyncCallable);
			} catch (Exception e) {
				throw new ZorbaClientException(e);
			}
		}

		return responseRecieved;

	}

	private void processRequestBeforeSend(ZorbaRequest zorbaRequest) {
		String uniqueRequestId = UUID.randomUUID().toString();
		zorbaRequest.setId(uniqueRequestId);
		if (zorbaRequest instanceof AsyncZorbaRequest) {
			((AsyncZorbaRequest) zorbaRequest).setCorrelationId(uniqueRequestId);
			((AsyncZorbaRequest) zorbaRequest).setReplyQueue(zorbaMessageListenerContainer
					.getClientUniqueReplyQueueName());
		}
	}

	public void shutdown() {
		zorbaMessageListenerContainer.shutdown();
		executor.shutdown();
		zorbaMessageListenerContainer.destroy();
	}

	/**
	 * Calls {@link #shutdown()} when the BeanFactory destroys the container instance.
	 * 
	 * @see #shutdown()
	 */
	public void destroy() {
		shutdown();
	}

}
