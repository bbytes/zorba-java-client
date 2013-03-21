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

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;

import com.bbytes.zorba.domain.AsyncZorbaResponse;
import com.bbytes.zorba.handler.ZorbaQueueResponseHandler;
import com.bbytes.zorba.listener.ZorbaAsyncResponseCallBackProcessor;

/**
 * 
 * @author Thanneer
 * 
 * @version 0.0.1
 */
public class ZorbaQueueResponseHandlerImpl implements ZorbaQueueResponseHandler {

	private MessageConverter messageConverter;
	
	private ZorbaAsyncResponseCallBackProcessor asyncResponseCallBackProcessor;

	public ZorbaQueueResponseHandlerImpl(MessageConverter messageConverter,ZorbaAsyncResponseCallBackProcessor asyncResponseCallBackProcessor) {
		this.messageConverter = messageConverter;
		this.asyncResponseCallBackProcessor = asyncResponseCallBackProcessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.amqp.core.MessageListener#onMessage(org.springframework.amqp.core.Message
	 * )
	 */
	@Override
	public void onMessage(Message message) {
		try {
			handleZorbaResponse((AsyncZorbaResponse) messageConverter.fromMessage(message));
		} catch (Exception e) {
			//need to log the exception
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bbytes.zorba.handler.ZorbaResponseHandler#handleZorbaResponse(com.bbytes.zorba.domain
	 * .AsyncZorbaResponse)
	 */
	@Override
	public void handleZorbaResponse(AsyncZorbaResponse response) throws Exception {
		System.out.println(response.getJobName());
		asyncResponseCallBackProcessor.delegateCall(response.getCorrelationId(), response);

	}

}
