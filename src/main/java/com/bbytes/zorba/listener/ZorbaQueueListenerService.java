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
package com.bbytes.zorba.listener;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.zorba.handler.impl.ZorbaQueueResponseErrorHandlerImpl;
import com.bbytes.zorba.handler.impl.ZorbaQueueResponseHandlerImpl;

/**
 * This service class will create a unique reply queue that will be set on request so that the job
 * server will set the correct reply queue from where the request was generated The container
 * listener will listen to this unique reply queue
 * 
 * @author Thanneer
 * 
 * @version
 */
@Service
public class ZorbaQueueListenerService {

	@Autowired
	private ZorbaMessageListenerContainer container;

	
	@Autowired
	private MessageConverter messageConverter;
	
	@Autowired
	private ZorbaAsyncResponseCallBackProcessor asyncResponseCallBackProcessor;



	public void afterPropertiesSet() {

		// spring bean initializer
		// pseudo-code
		container.setMessageListener(new ZorbaQueueResponseHandlerImpl(messageConverter,asyncResponseCallBackProcessor));
		container.setErrorHandler(new ZorbaQueueResponseErrorHandlerImpl());
		// Start by default
		container.start();

	}

	
	
}
