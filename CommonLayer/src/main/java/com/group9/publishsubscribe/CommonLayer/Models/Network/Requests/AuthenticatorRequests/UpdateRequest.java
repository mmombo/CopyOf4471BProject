package com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests;

import java.util.LinkedList;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;

public class UpdateRequest extends AuthenticatorRequest {
	
	private LinkedList<String> subscriptionList;
	
	public UpdateRequest() {
		
		
		
	}
	
	public UpdateRequest(Subscriber subscriber) {
		
		super(subscriber.getUsername(), subscriber.getPassword(), subscriber.getSubscriberIP(), subscriber.getSubscriberPort());
		this.subscriptionList = subscriber.getSubscriptionList();
		
	}
	
	public LinkedList<String> getSubscriptionList() {
		
		return subscriptionList;
		
	}
	
	public void setSubscriber(LinkedList<String> subscriptionList) {
		
		this.subscriptionList = subscriptionList;
		
	}

}