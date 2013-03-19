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
package com.bbytes.zorba.jobworker.domain.impl;

import com.bbytes.zorba.jobworker.domain.ZorbaAsyncRequest;

/**
 * Implementation of {@link ZorbaAsyncRequest}
 * 
 * @author Dhanush Gopinath
 * 
 * @version 0.0.1
 */
public class ZorbaAsyncRequestImpl extends ZorbaRequestImpl implements ZorbaAsyncRequest {
	
	private String correlationId;
	private String replyQueue;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getReplyQueue() {
		return replyQueue;
	}

	public void setReplyQueue(String replyQueue) {
		this.replyQueue = replyQueue;
	}
}
