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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import com.bbytes.zorba.domain.AsyncZorbaRequest;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
public class ZorbaSendAsyncThread extends Thread {

	private final CountDownLatch latch = new CountDownLatch(1);

	private RabbitOperations rabbitOperations;

	private String queueName;

	private AsyncZorbaRequest request;

	private ZorbaAsyncResponseCallBackHandler zorbaAsyncResponseCallBackHandler;

	public ZorbaSendAsyncThread(RabbitOperations rabbitOperations, String queueName, AsyncZorbaRequest request,
			ZorbaAsyncResponseCallBackHandler zorbaAsyncResponseCallBackHandler) {
		this.rabbitOperations = rabbitOperations;
		this.queueName = queueName;
		this.request = request;
		this.zorbaAsyncResponseCallBackHandler = zorbaAsyncResponseCallBackHandler;
	}

	public void run() {
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
			latch.await(request.getTimeOut(), TimeUnit.SECONDS);
			if (latch.getCount() != 0) {
				zorbaAsyncResponseCallBackHandler.onRequestTimeoOut();
				latch.countDown();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
