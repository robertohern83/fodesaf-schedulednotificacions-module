package com.fodesaf.scheduledtask.module.notifications;

public class NotificationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5793429576402038362L;
	
	public NotificationException(String message, Exception exception) {
		super.getMessage();
		this.initCause(exception);
		
	}
	
	public NotificationException(String message) {
		super(message);
	}

}
