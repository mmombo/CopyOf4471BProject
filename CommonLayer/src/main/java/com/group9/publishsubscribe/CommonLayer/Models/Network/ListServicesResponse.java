package com.group9.publishsubscribe.CommonLayer.Models.Network;

import java.util.Map;

public class ListServicesResponse extends Message {
	
	Map<String, String> services;
	
	public ListServicesResponse() {
		
		
		
	}
	
	public ListServicesResponse(Map<String, String> services, String subscriberIP, int subscriberPort) {
		
		super(subscriberIP, subscriberPort);
		this.services = services;
		
	}
	
	public Map<String, String> getServices() {
		
		return services;
		
	}
	
	public void setServices(Map<String, String> services) {
		
		this.services = services;
		
	}

}