package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class ServiceInfo {
	
	private String serviceName;
	private String serviceDescription;
	private String serviceIP;
	private int servicePort;
	
	public ServiceInfo() {
		
	}
	
	public ServiceInfo(String serviceName, String serviceDescription, String serviceIP, int servicePort) {
		
		this.serviceName = serviceName;
		this.serviceDescription = serviceDescription;
		this.serviceIP = serviceIP;
		this.servicePort = servicePort;
		
	}
	
	public String getServiceName() {
		
		return serviceName;
		
	}
	
	public void setServiceName(String serviceName) {
		
		this.serviceName = serviceName;
		
	}
	
	public String getServiceDescription() {
		
		return serviceDescription;
		
	}
	
	public void setServiceDesciption(String serviceDescription) {
		
		this.serviceDescription = serviceDescription;
		
	}
	
	public String getServiceIP() {
		
		return serviceIP;
		
	}
	
	public void setServiceIP(String serviceIP) {
		
		this.serviceIP = serviceIP;
		
	}
	
	public int getServicePort() {
		
		return servicePort;
		
	}
	
	public void setServicePort(int servicePort) {
		
		this.servicePort = servicePort;
		
	}
	
	public boolean equals(ServiceInfo other) {
		
		if (this.serviceName.equals(other.serviceName)) {
			
			return true;
			
		}
		
		else {
			
			return false;
			
		}
		
	}
	
	public int hashcode() {
		
		return servicePort;
		
	}
	
}