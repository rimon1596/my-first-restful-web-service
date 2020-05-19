package com.appsdeveloperblog.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;


@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException>{

	@Override
	public Response toResponse(MissingRequiredFieldException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 
				ErrorMessages.INTERNAL_SERVER_ERROR.name(), "http://google.com");
		
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
				entity(errorMessage).
				build();
		
	}

}