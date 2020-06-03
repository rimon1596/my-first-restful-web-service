package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.service.UsersService;

import org.hibernate.HibernateException;

import com.appsdeveloperblog.app.ws.exceptions.CouldNotCreateRecordException;
import com.appsdeveloperblog.app.ws.exceptions.NoRecordFoundException;
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
		UserDTO existingUser = this.getUserByUsername(user.getEmail());
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
	
	@Override
	public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        
        try {
        	this.database.openConnection();
        	returnValue = this.database.getUser(id);
        }
        catch(Exception exception) {
        	exception.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOIND_EXCEPTION.getErrorMessage());
        }
        finally {
        	this.database.closeConnection();
        }
		return returnValue;
	}

	@Override
	public UserDTO getUserByUsername(String userName) {

		UserDTO userDto = null;

		if (userName == null || userName.isEmpty()) {
			return null;
		}

		/// connect to database
		try {
			this.database.openConnection();
			userDto = this.database.getUserByUserName(userName);
		} catch(HibernateException exception){
		     System.out.println("Problem creating session factory");
		     exception.printStackTrace();
		}
		finally {

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
