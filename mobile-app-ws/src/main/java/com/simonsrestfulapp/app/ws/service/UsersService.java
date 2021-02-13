package com.simonsrestfulapp.app.ws.service;

import java.util.List;

import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;

public interface UsersService {
	
	 UserDTO createUser(UserDTO userDto);
	 UserDTO getUser(String id);
	 UserDTO getUserByUsername(String userName);
	 List<UserDTO> getUsers(int start, int limit);
	 void updateUserDetails(UserDTO storedUserDetails);
	 void deleteUser(UserDTO storedUser);

}
