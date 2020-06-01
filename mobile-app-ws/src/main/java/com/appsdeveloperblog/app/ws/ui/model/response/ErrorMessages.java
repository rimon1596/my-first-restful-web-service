package com.appsdeveloperblog.app.ws.ui.model.response;


public enum ErrorMessages {
	
	INTERNAL_SERVER_ERROR("Internal Server Error"),
   MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
   NO_RECORD_FOIND_EXCEPTION("No such record was found in our database"),
   RECORD_ALREADY_EXISTS("Record already exists");

	
	private String errorMessage;
	
	ErrorMessages(String errorMessage){
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
