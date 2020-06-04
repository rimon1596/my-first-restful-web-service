package com.simonsrestfulapp.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessage;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;
	
	@Provider
	public class NoRecordFoundExceptionMapper implements ExceptionMapper<NoRecordFoundException>{

		@Override
		public Response toResponse(NoRecordFoundException exception) {
			
			ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 
					ErrorMessages.NO_RECORD_FOIND_EXCEPTION.name(), "http://StackOverflow.com");
			  
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity(errorMessage).
					build();
			
		}

}
