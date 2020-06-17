package com.simonsrestfulapp.app.ws.exceptions;

public class CouldNotDeleteRecordException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6770081202494664457L;
	
	CouldNotDeleteRecordException(String message){
		super(message);
	}
	

}
