package com.simonsrestfulapp.app.ws.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.simonsrestfulapp.app.ws.exceptions.EmailVerificationException;
import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;

public class AmazonSES {

	final String FROM = "rimon1596@gmail.com";

	final String SUBJECT = "One last step to complete your registration...";

	final String HTMLBODY = "<h1>Please verify your email address</h1>"
			+ "<p> thanks for registering. To complete registration process and be able to log in click on the following link:"
			+ "<a href='http://localhost:8080/mobile-app-ws/verify-email.jsp'>"
			+ "Final step to complete your registration" + "</a><br/><br/>";

	// email body for recipients with non-HTML email clients
	final String TEXTBODY = "Please verify your email address"
			+ "thanks for registering. To complete registration process and be able to log in click on the following link:"
			+ "<a href='http://localhost:8080/mobile-app-ws/verify-email.jsp'>"
			+ "Final step to complete your registration";

	public void verifyEmail(UserDTO userDto) {

		try {
			
			AmazonSimpleEmailService client = 
					AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.EU_WEST_2).build();
			
			SendEmailRequest request = new SendEmailRequest().withDestination(
					new Destination().withToAddresses(userDto.getEmail()))
					.withMessage(new Message()
							.withBody(new Body()
									.withHtml(new Content()
											.withCharset("UTF-8").withData(HTMLBODY))
									.withText(new Content()
											.withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content()
									.withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM);
			
			client.sendEmail(request);
			
			System.out.println("Email Sent!");
					

		} catch (Exception e) {
		  e.printStackTrace();
          throw new EmailVerificationException(e.getMessage());
        
		}
	}
	
	public static void main(String[] args) {
		
		//test
		UserDTO userDto = new UserDTO();
		userDto.setEmail("rimon1596@gmail.com");
		new AmazonSES().verifyEmail(userDto);
	}

}
