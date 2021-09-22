package com.group9.publishsubscribe.CommonLayer.Models.Network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Error.UnknownServiceError;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.AuthenticatorRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.ServiceRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.AuthenticatorResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = AuthenticatorRequest.class, name = "AuthenticatorRequest"),
	@JsonSubTypes.Type(value = AuthenticatorResponse.class, name = "AuthenticatorResponse"),
	@JsonSubTypes.Type(value = ServiceRequest.class, name = "ServiceRequest"),
	@JsonSubTypes.Type(value = ServiceResponse.class, name = "ServiceResponse"),
	@JsonSubTypes.Type(value = RefreshRequest.class, name = "RefreshRequest"),
	@JsonSubTypes.Type(value = RefreshResponse.class, name = "RefreshResponse"),
	@JsonSubTypes.Type(value = Notification.class, name = "Notification"),
	@JsonSubTypes.Type(value = LookupRequest.class, name = "LookupRequest"),
	@JsonSubTypes.Type(value = LookupResponse.class, name = "LookupResponse"),
	@JsonSubTypes.Type(value = ListServicesRequest.class, name = "ListServicesRequest"),
	@JsonSubTypes.Type(value = ListServicesResponse.class, name = "ListServicesResponse"),
	@JsonSubTypes.Type(value = CurrentDateRequest.class, name = "CurrentDateRequest"),
	@JsonSubTypes.Type(value = NextDateRequest.class, name = "NextDateRequest"),
	@JsonSubTypes.Type(value = PreviousDateRequest.class, name = "PreviousDateRequest"),
	@JsonSubTypes.Type(value = UpdateDateResponse.class, name = "UpdateDateResponse"),
	@JsonSubTypes.Type(value = UnknownServiceError.class, name = "UnknownServiceError")}
)
public class Message {
	
	private String subscriberIP;
	private int subscriberPort;
	private String serviceManagerIP;
	
	public Message() {
		
	}
	
	public Message(String subscriberIP) {
		
		this.subscriberIP = subscriberIP;
		
	}
	
	public Message(String subscriberIP, int subscriberPort) {
		
		this.subscriberIP = subscriberIP;
		this.subscriberPort = subscriberPort;
		
	}
	
	public String getSubscriberIP() {
		
		return subscriberIP;
		
	}
	
	public void setSubscriberIP(String subscriberIP) {
		
		this.subscriberIP = subscriberIP;
		
	}
	
	public int getSubscriberPort() {
		
		return subscriberPort;
		
	}
	
	public void setSubscriberPort(int subscriberPort) {
		
		this.subscriberPort = subscriberPort;
		
	}
	
	public String getServiceManagerIP() {
		
		return serviceManagerIP;
		
	}
	
	public void setServiceManagerIP(String serviceManagerIP) {
		
		this.serviceManagerIP = serviceManagerIP;
		
	}

}