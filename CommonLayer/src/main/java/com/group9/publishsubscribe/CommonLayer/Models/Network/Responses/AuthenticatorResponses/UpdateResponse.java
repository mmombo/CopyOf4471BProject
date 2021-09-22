package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;

public class UpdateResponse extends AuthenticatorResponse {
	
	public UpdateResponse() {
		
		
		
	}
	
	public UpdateResponse(boolean authenticated, Subscriber subscriber, String subscriberIP, int subscriberPort) {
		
		super(authenticated, subscriber, subscriberIP, subscriberPort);
		
	}

}