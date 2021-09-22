package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class RefreshResponse extends Message {
	
	private ServiceInfo serviceInfo;
	private String currentDate;
	private String[] fileNames;
	private String[] fileDatas;
	
	public RefreshResponse() {
		
	}
	
	public RefreshResponse(ServiceInfo serviceInfo, String currentDate, String[] fileNames, String[] fileDatas) {
		
		this.serviceInfo = serviceInfo;
		this.currentDate = currentDate;
		this.fileNames = fileNames;
		this.fileDatas = fileDatas;
		
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
	
	public String[] getFileNames() {
		
		return fileNames;
		
	}
	
	public void setFileNames(String[] fileNames) {
		
		this.fileNames = fileNames;
		
	}
	
	public String[] getFileDatas() {
		
		return fileDatas;
		
	}
	
	public void setFileDatas(String[] fileDatas) {
		
		this.fileDatas = fileDatas;
		
	}
	
}