package com.appsdeveloperblog.app.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.impl.AuthenticationServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.LoginCrendentials;
import com.appsdeveloperblog.app.ws.ui.response.AuthenticationDetails;

@Path("/authentication")
public class AuthenticationEntryPoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public AuthenticationDetails userLogin (LoginCrendentials loginCredentials) {
		
		AuthenticationDetails returnValue = new AuthenticationDetails();
		
		AuthenticationService authService = new AuthenticationServiceImpl();
		UserDTO authenticatedUser = authService.authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
		
		String accessToken = authService.issueAccessToken(authenticatedUser);
		
		returnValue.setId(authenticatedUser.getUserId());
		returnValue.setToken(accessToken);
		
		return returnValue;
	}

}
