package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class LookupResponse extends Message {
	
	private ServiceInfo serviceInfo;
	
	public LookupResponse() {
		
		
		
	}
	
	public LookupResponse(ServiceInfo serviceInfo) {
		
		this.serviceInfo = serviceInfo;
		
	}
	
	public ServiceInfo getServiceInfo() {
		
		return serviceInfo;
		
	}
	
	public void setServiceInfo(ServiceInfo serviceInfo) {
		
		this.serviceInfo = serviceInfo;
		
	}

}