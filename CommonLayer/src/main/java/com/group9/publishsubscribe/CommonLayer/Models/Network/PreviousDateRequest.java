package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class PreviousDateRequest extends Message {
	
	private String currentDate;
	
	public PreviousDateRequest() {
		
		
		
	}
	
	public PreviousDateRequest(String currentDate) {
		
		this.currentDate = currentDate;
		
	}
	
	public String getCurrentDate() {
		
		return currentDate;
		
	}
	
	public void setCurrentDate(String currentDate) {
		
		this.currentDate = currentDate;
		
	}	

}