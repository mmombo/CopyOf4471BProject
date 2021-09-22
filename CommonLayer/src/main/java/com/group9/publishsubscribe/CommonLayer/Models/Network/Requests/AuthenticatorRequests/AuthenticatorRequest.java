package com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LoginRequest.class, name = "LoginRequest"),
	@JsonSubTypes.Type(value = RegisterRequest.class, name = "RegisterRequest"),
	@JsonSubTypes.Type(value = UpdateRequest.class, name = "UpdateRequest")}
)
public class AuthenticatorRequest extends Message {
	
	private String username;
	private String password;
	
	public AuthenticatorRequest() {
		
	}
	
	public AuthenticatorRequest(String username, String password, String subscriberIP, int subscriberPort) {
		
		super(subscriberIP, subscriberPort);
		
		this.username = username;
		this.password = password;
		
	}
	
	public String getUsername() {
		
		return username;
		
	}
	
	public void setUsername(String username) {
		
		this.username = username;
		
	}
	
	public String getPassword() {
		
		return password;
		
	}
	
	public void setPassword(String password) {
		
		this.password = password;
		
	}

}