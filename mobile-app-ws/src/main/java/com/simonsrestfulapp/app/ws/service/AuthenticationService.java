package com.simonsrestfulapp.app.ws.service;

import com.simonsrestfulapp.app.ws.exceptions.AuthenticationException;
import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;

public interface AuthenticationService {
	
	UserDTO authenticate(String username, String password) throws AuthenticationException;
	String issueAccessToken(UserDTO userDto) throws AuthenticationException;
	void resetSecurityCredentials(String password, UserDTO authenticatedUser);

}
