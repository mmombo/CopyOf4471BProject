package com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests;

public class RegisterRequest extends AuthenticatorRequest {
	
	public RegisterRequest() {
	
	}
	
	public RegisterRequest(String username, String password, String subscriberIP, int subscriberPort) {
		
		super(username, password, subscriberIP, subscriberPort);
		
	}
	
}