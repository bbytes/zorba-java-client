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
package com.bbytes.zorba.handler.impl;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;

/**
 * The request is send by client to job server using this {@link Callable} class , the thread waits
 * till the response is sent or till the time out mentioned in the request. The wait and notify is
 * done using {@link CountDownLatch}
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public class ZorbaSendAsyncCallable implements Callable<Boolean> {

	/** Countdown latch */
	private final CountDownLatch latch = new CountDownLatch(1);

	private RabbitOperations rabbitOperations;

	private String queueName;

	private AsyncZorbaRequest request;

	private ZorbaAsyncResponseCallBackHandler zorbaAsyncResponseCallBackHandler;

	public ZorbaSendAsyncCallable(RabbitOperations rabbitOperations, String queueName, AsyncZorbaRequest request,
			ZorbaAsyncResponseCallBackHandler zorbaAsyncResponseCallBackHandler) {
		this.rabbitOperations = rabbitOperations;
		this.queueName = queueName;
		this.request = request;
		this.zorbaAsyncResponseCallBackHandler = zorbaAsyncResponseCallBackHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Boolean call() throws Exception {
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

		try {

			long timeOut = Long.MAX_VALUE;
			if (request.getTimeOut() != -1) {
				timeOut = request.getTimeOut();
			}

			if (!latch.await(timeOut, TimeUnit.SECONDS)) {
				// if the code is reached here then the request is timed out so call the
				// onRequestTimeoOut on call back method
				zorbaAsyncResponseCallBackHandler.onRequestTimeoOut();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void releaseLatch() {
		latch.countDown();
	}

	/**
	 * @return the zorbaAsyncResponseCallBackHandler
	 */
	public ZorbaAsyncResponseCallBackHandler getZorbaAsyncResponseCallBackHandler() {
		return zorbaAsyncResponseCallBackHandler;
	}

}
