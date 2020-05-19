package com.appsdeveloperblog.app.ws.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import com.appsdeveloperblog.app.ws.exceptions.MissingRequiredFieldException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;

public class UserProfileUtils {

	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public String generateUUID() {
		String returnValue = UUID.randomUUID().toString().replaceAll("-", "");
		return returnValue;
	}

	private String generateRandomString(int length) {

		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));

		}

		return new String(returnValue);
	}

	public String generateUserId(int length) {
		return generateRandomString(length);
	}

	public void validateRequiredFields(UserDTO userDto) throws MissingRequiredFieldException {

		if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty() || userDto.getLastName() == null
				|| userDto.getLastName().isEmpty() || userDto.getEmail() == null || userDto.getEmail().isEmpty()
				|| userDto.getPassword() == null || userDto.getPassword().isEmpty()) {

			throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}

	}

}
