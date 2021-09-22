package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class NextDateRequest extends Message {
	
	private String currentDate;
	
	public NextDateRequest() {
		
		
		
	}
	
	public NextDateRequest(String currentDate) {
		
		this.currentDate = currentDate;
		
	}
	
	public String getCurrentDate() {
		
		return currentDate;
		
	}
	
	public void setCurrentDate(String currentDate) {
		
		this.currentDate = currentDate;
		
	}	

}