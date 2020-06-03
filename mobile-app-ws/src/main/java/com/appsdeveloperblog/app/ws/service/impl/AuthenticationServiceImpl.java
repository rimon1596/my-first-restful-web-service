package com.appsdeveloperblog.app.ws.service.impl;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

public class AuthenticationServiceImpl implements AuthenticationService {

	DAO database;

	@Override
	public UserDTO authenticate(String username, String password) throws AuthenticationException {

		UsersService userService = new UsersServiceImpl();
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
		storeAccessToken(userDto);

		return returnValue;
	}

	private void storeAccessToken(UserDTO userProfile) {

		this.database = new MySQLDAO();
		try {
			database.openConnection();
			database.updateUserProfile(userProfile);
		} finally {
			database.closeConnection();
		}

	}

}
