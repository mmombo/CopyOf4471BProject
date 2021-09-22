package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class ListServicesRequest extends Message {
	
	public ListServicesRequest() {
		
		
		
	}
	
	public ListServicesRequest(String subscriberIP, int subscriberPort) {
		
		super(subscriberIP, subscriberPort);
		
	}

}