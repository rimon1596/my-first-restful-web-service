package com.simonsrestfulapp.app.ws.service.impl;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.simonsrestfulapp.app.ws.exceptions.AuthenticationException;
import com.simonsrestfulapp.app.ws.io.dao.DAO;
import com.simonsrestfulapp.app.ws.io.dao.impl.MySQLDAO;
import com.simonsrestfulapp.app.ws.service.AuthenticationService;
import com.simonsrestfulapp.app.ws.service.UsersService;
import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;
import com.simonsrestfulapp.app.ws.ui.model.response.ErrorMessages;
import com.simonsrestfulapp.app.ws.utils.UserProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	UsersService userService;
	DAO database;

	@Override
	public UserDTO authenticate(String username, String password) throws AuthenticationException {

		UserDTO storedUser = userService.getUserByUsername(username);
		if (storedUser == null) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}

		String encryptedPassword = null;

		encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());

		boolean authenticated = false;
		if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword()))
			if (username != null && username.equalsIgnoreCase(storedUser.getEmail())) {
				authenticated = true;
			}
		if (!authenticated)
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());

		return storedUser;
	}

	@Override
	public String issueAccessToken(UserDTO userDto) throws AuthenticationException { 

		String returnValue = null;

		String existingSalt = userDto.getSalt();
		String material = userDto.getUserId() + existingSalt;

		byte[] encryptedAccessToken = null;
		try {

			encryptedAccessToken = new UserProfileUtils().encrypt(userDto.getEncryptedPassword(), material);

		} catch (InvalidKeySpecException ex) {
			Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AuthenticationException("Failed while trying to issue a secure access token");
		}

		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

		int tokenLength = encryptedAccessTokenBase64Encoded.length();

		returnValue = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
		String tokenForDB = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

		userDto.setAccessToken(tokenForDB);
		updateUserProfile(userDto);

		return returnValue;
	}

	private void updateUserProfile(UserDTO userProfile) {

		this.database = new MySQLDAO();
		try {
			database.openConnection();
			database.updateUserProfile(userProfile);
		} finally {
			database.closeConnection();
		}

	}
	
	public void resetSecurityCredentials(String password, UserDTO userProfile) {
		
		//Generate fresh salt 
		UserProfileUtils utils = new UserProfileUtils();
		String salt = utils.getSalt(30);
		
		//generate a new hashed password
		String securePassword = utils.generateSecurePassword(password, salt);
		userProfile.setSalt(salt);
		userProfile.setEncryptedPassword(securePassword);
		
		//store the new values
		updateUserProfile(userProfile);
		
		
	}

}
