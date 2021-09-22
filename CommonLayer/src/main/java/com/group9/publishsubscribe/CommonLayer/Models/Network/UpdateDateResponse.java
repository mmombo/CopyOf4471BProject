package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class UpdateDateResponse extends Message {
	
	private String currentDate;
	
	public UpdateDateResponse() {
		
		
		
	}
	
	public UpdateDateResponse(String currentDate) {
		
		this.currentDate = currentDate;
		
	}
	
	public String getCurrentDate() {
		
		return currentDate;
		
	}
	
	public void setCurrentDate(String currentDate) {
		
		this.currentDate = currentDate;
		
	}	

}