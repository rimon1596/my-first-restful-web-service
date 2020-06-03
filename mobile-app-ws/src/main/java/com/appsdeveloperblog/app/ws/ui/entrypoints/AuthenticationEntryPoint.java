package com.appsdeveloperblog.app.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.appsdeveloperblog.app.ws.ui.model.request.LoginCrendentials;
import com.appsdeveloperblog.app.ws.ui.response.AuthenticationDetails;
import com.sun.research.ws.wadl.Application;

@Path("/authentication")
public class AuthenticationEntryPoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public AuthenticationDetails userLogin (LoginCrendentials loginCredentials) {
		
		AuthenticationDetails returnValue = new AuthenticationDetails();
		
		return returnValue;
	}

}
