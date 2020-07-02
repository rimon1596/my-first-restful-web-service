package com.simonsrestfulapp.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessage;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;

@Provider
public class EmailVerificationExceptionMapper implements ExceptionMapper<EmailVerificationException>{
	
	@Override
	public Response toResponse(EmailVerificationException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 
				ErrorMessages.EMAIL_DOES_NOT_EXIST_ERROR.name(), "http://stackoverflow.com");
		
		
		return Response.status(Response.Status.BAD_REQUEST).
				entity(errorMessage).
				build();
		
	}
	
	

}
