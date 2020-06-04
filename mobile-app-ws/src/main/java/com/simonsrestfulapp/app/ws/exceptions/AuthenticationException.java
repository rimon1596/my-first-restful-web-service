package com.simonsrestfulapp.app.ws.exceptions;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 2373664764845434623L;
	
    public AuthenticationException(String exceptionMessage) {
    	super(exceptionMessage);
    }

}
