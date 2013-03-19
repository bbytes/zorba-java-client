/**
 * 
 */
package com.bbytes.zorba.domain;


/**
 * 
 * @author Dhanush Gopinath
 * 
 * 
 */
public class AsyncZorbaRequest extends ZorbaRequest{

	private static final long serialVersionUID = -4670186306988893496L;
	
	protected String correlationId;
	
	protected String replyQueue;
	
	protected long timeOut=0;


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
	
	/**
	 * @return the timeOut
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	
}
