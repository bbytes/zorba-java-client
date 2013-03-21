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

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.domain.Priority;
import com.bbytes.zorba.domain.ZorbaRequest;
import com.bbytes.zorba.exception.ZorbaClientException;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;
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
public class DefaultZorbaClient implements ZorbaClient {

	@Autowired
	private RabbitOperations rabbitOperations;

	@Autowired
	private ZorbaMessageListenerContainer zorbaMessageListenerContainer;

	@Autowired
	private ZorbaAsyncResponseCallBackProcessor asyncResponseCallBackProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#send(com.bbytes.zorba.domain.ZorbaRequest,
	 * com.bbytes.zorba.domain.Priority)
	 */
	@Override
	@Async
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
	@Async
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
	@Async
	public void sendAsync(final AsyncZorbaRequest request, Priority priority,
			ZorbaAsyncResponseCallBackHandler asyncResponseHandler) throws ZorbaClientException {
		if (request != null) {
			processRequestBeforeSend(request);
			asyncResponseCallBackProcessor.setCallBackHandler(request.getCorrelationId(), asyncResponseHandler);
			String queueName = priority.getQueueName();
			rabbitOperations.convertAndSend(queueName, request, new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					message.getMessageProperties().setReplyTo(request.getReplyQueue());
					try {
						message.getMessageProperties().setCorrelationId(request.getCorrelationId().getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						throw new AmqpException(e);
					}
					return message;
				}
			});

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbytes.zorba.ZorbaClient#sendAsync(com.bbytes.zorba.domain.ZorbaRequest,
	 * java.lang.String, com.bbytes.zorba.AsyncResponseHandler)
	 */
	@Override
	@Async
	public void sendAsync(final AsyncZorbaRequest request, String queueName,
			ZorbaAsyncResponseCallBackHandler asyncResponseHandler) throws ZorbaClientException {
		if (request != null && queueName != null) {
			processRequestBeforeSend(request);
			asyncResponseCallBackProcessor.setCallBackHandler(request.getCorrelationId(), asyncResponseHandler);
			rabbitOperations.convertAndSend(queueName, request, new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					message.getMessageProperties().setReplyTo(request.getReplyQueue());
					try {
						message.getMessageProperties().setCorrelationId(request.getCorrelationId().getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						throw new AmqpException(e);
					}
					return message;
				}
			});
		}

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

}
