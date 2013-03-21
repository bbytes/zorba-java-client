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

import java.util.UUID;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * 
 * @author Thanneer
 * 
 * @version
 */
public class ZorbaMessageListenerContainer extends SimpleMessageListenerContainer {

	@Autowired
	private RabbitAdmin rabbitAdmin;

	protected String clientUniqueReplyQueueName = null;

	public ZorbaMessageListenerContainer() {
		super();
		clientUniqueReplyQueueName = "zorba.client.reply.queue." + UUID.randomUUID().toString();
		setQueueNames(clientUniqueReplyQueueName);
	}

	protected void doInitialize() throws Exception {
		rabbitAdmin.declareQueue(new Queue(clientUniqueReplyQueueName));
		super.doInitialize();
	}

	/**
	 * @return the clientUniqueReplyQueueName
	 */
	public String getClientUniqueReplyQueueName() {
		return clientUniqueReplyQueueName;
	}

	public boolean deleteClientReplyQueue() {
		return rabbitAdmin.deleteQueue(clientUniqueReplyQueueName);
	}

	/**
	 * Calls {@link #shutdown()} when the BeanFactory destroys the container instance.
	 * 
	 * @see #shutdown()
	 */
	public void destroy() {
		deleteClientReplyQueue();
		super.destroy();

	}
}
