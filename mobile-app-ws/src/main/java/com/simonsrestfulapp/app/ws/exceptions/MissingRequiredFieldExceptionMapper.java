package com.simonsrestfulapp.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessage;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;


@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException>{

	@Override
	public Response toResponse(MissingRequiredFieldException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 
				ErrorMessages.MISSING_REQUIRED_FIELD.name(), "http://StackOverflow.com");
		
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
				entity(errorMessage).
				build();
		
	}

}