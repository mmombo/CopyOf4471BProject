package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class Notification extends Message {
	
	private String serviceType;
	
	public Notification() {
		
	}
	
	public Notification(String serviceType) {
		
		this.serviceType = serviceType;
		
	}
	
	public String getServiceType() {
		
		return serviceType;
		
	}
	
	public void setServiceType(String serviceType) {
		
		this.serviceType = serviceType;
		
	}

}