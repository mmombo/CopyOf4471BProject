package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class RefreshRequest extends Message {

	private ServiceInfo serviceInfo;
	private String currentDate;
	private String serviceRegistryIP;
	private String dataManagerIP;
	private String messageManagerIP;
	
	public RefreshRequest() {
		
	}

	public RefreshRequest(ServiceInfo serviceInfo, String currentDate) {
		
		super();
		this.serviceInfo = serviceInfo;
		this.currentDate = currentDate;
		
	}

	public ServiceInfo getServiceInfo() {
		
		return serviceInfo;
		
	}
	
	public void setServiceInfo(ServiceInfo serviceInfo) {
		
		this.serviceInfo = serviceInfo;
		
	}
	
	public String getCurrentDate() {
		
		return currentDate;
		
	}
	
	public void setCurrentDate(String currentDate) {
		
		this.currentDate = currentDate;
		
	}
	
	public String getServiceRegistryIP() {
		
		return serviceRegistryIP;
		
	}
	
	public void setServiceRegistryIP(String serviceRegistryIP) {
		
		this.serviceRegistryIP = serviceRegistryIP;
		
	}
	
	public String getDataManagerIP() {
		
		return dataManagerIP;
		
	}
	
	public void setDataManagerIP(String dataManagerIP) {
		
		this.dataManagerIP = dataManagerIP;
		
	}
	
	public String getMessageManagerIP() {
		
		return messageManagerIP;
		
	}
	
	public void setMessageManagerIP(String messageManagerIP) {
		
		this.messageManagerIP = messageManagerIP;
		
	}

}