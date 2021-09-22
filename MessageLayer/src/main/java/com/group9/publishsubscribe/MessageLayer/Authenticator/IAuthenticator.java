package com.group9.publishsubscribe.MessageLayer.Authenticator;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.AuthenticatorRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.AuthenticatorResponse;

public interface IAuthenticator {
	
	public AuthenticatorResponse authenticate(AuthenticatorRequest request);
	
	public Subscriber[] getSubscribers();

}