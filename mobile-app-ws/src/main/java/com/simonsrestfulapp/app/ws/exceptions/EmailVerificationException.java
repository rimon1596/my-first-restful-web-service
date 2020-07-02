package com.simonsrestfulapp.app.ws.exceptions;

public class EmailVerificationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8249556138669230041L;
	
	public EmailVerificationException(String message) {
		super(message);
	}

}
