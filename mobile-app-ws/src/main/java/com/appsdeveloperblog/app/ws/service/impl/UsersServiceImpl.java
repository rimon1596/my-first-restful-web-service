package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.service.UsersService;

import com.appsdeveloperblog.app.ws.exceptions.CouldNotCreateRecordException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

public class UsersServiceImpl implements UsersService {

	DAO database;

	public UsersServiceImpl() {
		this.database = new MySQLDAO();
	}

	UserProfileUtils userProfileUtils = new UserProfileUtils();

	@Override
	public UserDTO createUser(UserDTO user) {

		UserDTO returnValue = new UserDTO();

		// validate the required fields
		userProfileUtils.validateRequiredFields(user);

		// check if the user already exists
		UserDTO existingUser = this.getUserByUserName(user.getEmail());
		if (existingUser != null) {

			throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
		}

		// Generate secure public user ID
		String userId = userProfileUtils.generateUserId(30);
		user.setUserId(userId);

		// Generate salt
		String salt = userProfileUtils.getSalt(30);

		// Generate secure user password
		String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setEncryptedPassword(encryptedPassword);

		// record data into a database
		returnValue = this.saveUser(user);

		// return back user profile
		return returnValue;
	}

	private UserDTO getUserByUserName(String userName) {

		UserDTO userDto = null;

		if (userName == null || userName.isEmpty()) {
			return null;
		}

		/// connect to database
		try {
			this.database.openConnection();
			userDto = this.database.getUserByUserName(userName);
		} finally {

			this.database.closeConnection();
		}

		return userDto;
	}

	private UserDTO saveUser(UserDTO user) {

		UserDTO returnValue = null;

		// Connect to DB
		try {
			this.database.openConnection();
			returnValue = this.database.saveUser(user);
		} finally {

			this.database.closeConnection();
		}

		return returnValue;
	}

}
