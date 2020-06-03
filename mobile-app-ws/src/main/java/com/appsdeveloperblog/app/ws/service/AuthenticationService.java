package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface AuthenticationService {
	
	UserDTO authenticate(String username, String password) throws AuthenticationException;
	String issueAccessToken(UserDTO userDto) throws AuthenticationException;

}
