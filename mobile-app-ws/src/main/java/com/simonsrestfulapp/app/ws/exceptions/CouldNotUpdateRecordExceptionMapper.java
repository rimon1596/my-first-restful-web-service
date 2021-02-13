package com.simonsrestfulapp.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessage;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;

@Provider
public class CouldNotUpdateRecordExceptionMapper implements ExceptionMapper<CouldNotUpdateRecordException>{
	
	
	@Override
	public Response toResponse(CouldNotUpdateRecordException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 
				ErrorMessages.COULD_NOT_UPDATE_RECORD_EXCEPTION.name(), "http://stackoverflow.com");
		
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
				entity(errorMessage).
				build();
		
	}
	
	

}
