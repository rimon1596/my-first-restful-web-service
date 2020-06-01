package com.appsdeveloperblog.app.ws.ui.entrypoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;

@Path("/users")
public class UsersEntryPoint {
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserProfileRest createUser(CreateUserRequestModel requestObject) {
		
		UserProfileRest returnValue = new UserProfileRest();
		
		// Prepare userDTO
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(requestObject, userDto);
		
		//create new user - pass to the service layer
		UsersService userService = new UsersServiceImpl();
		UserDTO createdUserProfile = userService.createUser(userDto);
		
		//prepare response
		BeanUtils.copyProperties(createdUserProfile, returnValue);
		
	    return returnValue;	
	}
	
	@GET
    @Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserProfileRest getUserInfo(@PathParam("id") String id) {
		
		UserProfileRest returnValue = null;
		UsersServiceImpl userService = new UsersServiceImpl();
		
		UserDTO userProfile = userService.getUser(id);
		
		returnValue = new UserProfileRest();
		BeanUtils.copyProperties(userProfile, returnValue);
		
		return returnValue;
	}

}
   