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

import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bbytes.zorba.domain.AsyncZorbaResponse;
import com.bbytes.zorba.handler.ZorbaAsyncResponseCallBackHandler;
import com.bbytes.zorba.handler.impl.ZorbaSendAsyncCallable;

/**
 * This class holds the map of correlation id to callback class {@link ZorbaAsyncResponseCallBackHandler} instance via {@link ZorbaSendAsyncCallable}
 * Once the response is received the correct call back method is given the response. 
 * 
 * @author Thanneer
 * 
 * @version
 */
@Component
public class ZorbaAsyncResponseCallBackProcessor {

	private Map<String, ZorbaSendAsyncCallable> mapCorrelationIdToSendAysnThreadObject = new Hashtable<String, ZorbaSendAsyncCallable>();

	public void setCallBackHandler(String correlationId, ZorbaSendAsyncCallable sendAsyncThread) {
		mapCorrelationIdToSendAysnThreadObject.put(correlationId, sendAsyncThread);
	}

	public ZorbaSendAsyncCallable getCallAsyncThreadExecutor(String correlationId) {
		return mapCorrelationIdToSendAysnThreadObject.get(correlationId);
	}

	public void delegateCall(String correlationId, AsyncZorbaResponse response) {
		ZorbaSendAsyncCallable sendAsyncThread = getCallAsyncThreadExecutor(correlationId);
		if (sendAsyncThread != null) {
			ZorbaAsyncResponseCallBackHandler responseCallBackHandler = sendAsyncThread
					.getZorbaAsyncResponseCallBackHandler();
			// calling onResponse method in callback
			responseCallBackHandler.onResponse(response);
			// release latch is called to release the thread from wait using countDownLatch mechanism
			sendAsyncThread.releaseLatch();
			mapCorrelationIdToSendAysnThreadObject.remove(correlationId);
		}
	}

}
