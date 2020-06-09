package com.simonsrestfulapp.app.ws.ui.entrypoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.simonsrestfulapp.app.ws.annotations.Secured;
import com.simonsrestfulapp.app.ws.service.UsersService;
import com.simonsrestfulapp.app.ws.service.impl.UsersServiceImpl;
import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;
import com.simonsrestfulapp.app.ws.ui.model.request.CreateUserRequestModel;
import com.simonsrestfulapp.app.ws.ui.model.response.UserProfileRest;

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
	
	@Secured
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
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start, 
			@DefaultValue("50") @QueryParam("limit") int limit){
		
		UsersService service = new UsersServiceImpl();
		List<UserDTO> users = service.getUsers(start, limit);
		
	
		//prepare return value
		List<UserProfileRest> returnValue = new ArrayList<UserProfileRest>();
		for(UserDTO user : users) {
			UserProfileRest userModel = new UserProfileRest();
			BeanUtils.copyProperties(user, userModel);
			userModel.setHref("/users/" + user.getUserId());
			returnValue.add(userModel);
		}
		
		return returnValue;
		
	}

}
   