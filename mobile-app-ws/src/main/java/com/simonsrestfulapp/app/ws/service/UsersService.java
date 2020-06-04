package com.simonsrestfulapp.app.ws.service;

import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;

public interface UsersService {
	
	 UserDTO createUser(UserDTO userDto);
	 UserDTO getUser(String id);
	 UserDTO getUserByUsername(String userName);

}
