package com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests;

public class LoginRequest extends AuthenticatorRequest {
	
	public LoginRequest() {
		
	}
	
	public LoginRequest(String username, String password, String subscriberIP, int subscriberPort) {
		
		super(username, password, subscriberIP, subscriberPort);
		
	}

}