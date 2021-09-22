package com.group9.publishsubscribe.CommonLayer.Models.Network.Error;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;

public class UnknownServiceError extends Message {
	
	private String errorMessage;
	
	public UnknownServiceError(String message) {
		
		super();
		this.errorMessage = message;
		
	}
	
	public String getMessage() {
		
		return errorMessage;
		
	}
	
	public void setMessage(String message) {
		
		this.errorMessage = message;
		
	}

}