package com.appsdeveloperblog.app.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;

@Path("/users")
public class UsersEntryPoint {
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	
	public UserProfileRest createUser(CreateUserRequestModel requestObject) {
		
		UserProfileRest returnValue = new UserProfileRest();
		
	    return returnValue;	
	}

}
