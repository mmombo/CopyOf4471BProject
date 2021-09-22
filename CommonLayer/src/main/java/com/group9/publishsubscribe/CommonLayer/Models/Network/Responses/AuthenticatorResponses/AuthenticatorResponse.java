package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LoginResponse.class, name = "LoginResponse"),
	@JsonSubTypes.Type(value = RegisterResponse.class, name = "RegisterResponse"),
	@JsonSubTypes.Type(value = UpdateResponse.class, name = "UpdateResponse")}
)
public class AuthenticatorResponse extends Message {
	
	private boolean authenticated;
	private Subscriber subscriber;
	
	public AuthenticatorResponse() {
		
	}
	
	public AuthenticatorResponse(boolean authenticated, Subscriber subscriber, String subscriberIP, int subscriberPort) {
		
		super(subscriberIP, subscriberPort);
		this.authenticated = authenticated;
		this.subscriber = subscriber;
		
	}
	
	public boolean isAuthenticated() {
		
		return authenticated;
		
	}
	
	public Subscriber getSubscriber() {
		
		return subscriber;
		
	}

}