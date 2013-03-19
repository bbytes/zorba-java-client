/**
 * 
 */
package com.bbytes.zorba.domain;

/**
 * 
 * @author Dhanush Gopinath
 * 
 */
public class AsyncZorbaResponse extends ZorbaResponse {

	private static final long serialVersionUID = 6435597307710484854L;

	protected String correlationId;
	
	protected String replyQueue;

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
