package com.fodesaf.scheduledtask.module.notifications;

public class NotificationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5793429576402038362L;
	
	
	private int errorCode;


	public NotificationException(String message, Exception exception) {
		super.getMessage();
		this.initCause(exception);
		
	}
	
	public NotificationException(String message) {
		super(message);
	}
	
	public NotificationException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;		
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
