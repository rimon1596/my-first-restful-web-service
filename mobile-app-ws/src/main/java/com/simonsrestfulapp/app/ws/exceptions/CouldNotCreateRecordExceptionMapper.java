package com.simonsrestfulapp.app.ws.exceptions;

import javax.ws.rs.core.Response;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessage;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;


@Provider
public class CouldNotCreateRecordExceptionMapper implements ExceptionMapper<CouldNotCreateRecordException>{
	
	@Override
	public Response toResponse(CouldNotCreateRecordException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 
				ErrorMessages.RECORD_ALREADY_EXISTS.name(), "http://stackoverflow.com");
		
		
		return Response.status(Response.Status.BAD_REQUEST).
				entity(errorMessage).
				build();
		
	}

}
