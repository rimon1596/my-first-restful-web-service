package com.simonsrestfulapp.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessage;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {

		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.MISSING_REQUIRED_FIELD.name(), "http://google.com");
		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	
	}

}
