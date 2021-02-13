package com.simonsrestfulapp.app.ws.filters;

import com.simonsrestfulapp.app.ws.annotations.Secured;
import com.simonsrestfulapp.app.ws.service.UsersService;
import com.simonsrestfulapp.app.ws.service.impl.UsersServiceImpl;
import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;
import com.simonsrestfulapp.app.ws.utils.UserProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.security.sasl.AuthenticationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{

	@Autowired
	UsersService usersService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		
		//check the header for the token
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader == null || !authorizationHeader.startsWith("Carrier")) {
			throw new AuthenticationException("Authorization header must be provided with this request");
		}
		
		//Extract said token
		String token = authorizationHeader.substring("Carrier".length()).trim();
		
		//Extract the user id 
		String userId = requestContext.getUriInfo().getPathParameters().getFirst("id");
		
		validateToken(token, userId);
	}
	
	private void validateToken(String token, String userId) throws AuthenticationException {
		
		
	   //Get user profile details
		UserDTO userProfile = usersService.getUser(userId);
		
		//Assemble Access token using two parts. One from the DB and one from http request
		String completeToken = token + userProfile.getAccessToken();
		
		//Create Access token material out of the userId received and salt kept in DB
		String securePassword = userProfile.getEncryptedPassword();
		String salt = userProfile.getSalt();
		String accessTokenMaterial = userId + salt;
		byte[] encryptedAccessToken = null;
		
		try {
			encryptedAccessToken = new UserProfileUtils().encrypt(securePassword, accessTokenMaterial);
		}catch(InvalidKeySpecException ex) {
			Logger.getLogger(AuthenticationFilter.class.getName()).log(java.util.logging.Level.SEVERE,null , ex);
			throw new AuthenticationException("Failed to issue secure access token");
		}
		
		String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
		
		//compare the two access tokens
		if(!encryptedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken)) {
			throw new AuthenticationException("Authorization token did not match");
		}
		
	}

}
