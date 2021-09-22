package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class LookupRequest extends Message {
	
	private String serviceName;
	
	public LookupRequest() {
		
		
		
	}
	
	public LookupRequest(String serviceName) {
		
		this.serviceName = serviceName;
		
	}
	
	public String getServiceName() {
		
		return serviceName;
		
	}
	
	public void setServiceName(String serviceName) {
		
		this.serviceName = serviceName;
		
	}

}