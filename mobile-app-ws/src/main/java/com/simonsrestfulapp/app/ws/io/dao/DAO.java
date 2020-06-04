package com.simonsrestfulapp.app.ws.io.dao;

import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;

public interface DAO {

	void openConnection();

	UserDTO getUserByUserName(String userName);

	UserDTO saveUser(UserDTO user);
	
	UserDTO getUser(String id);

	void closeConnection();

	void updateUserProfile(UserDTO userProfile);

}
