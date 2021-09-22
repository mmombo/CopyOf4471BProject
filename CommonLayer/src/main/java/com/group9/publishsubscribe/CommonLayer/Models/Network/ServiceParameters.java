package com.group9.publishsubscribe.CommonLayer.Models.Network;

public class ServiceParameters {
	
	private String companyName;
	private String startDate;
	private String endDate;
	
	public ServiceParameters() {
		
	}

	public ServiceParameters(String startDate) {
		
		this.startDate = startDate;
		
	}
	
	public ServiceParameters(String companyName, String startDate, String endDate) {
		
		this.companyName = companyName;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}
	
	public String getCompanyName() {
		
		return companyName;
		
	}
	
	public void setCompanySymbol(String companyName) {
		
		this.companyName = companyName;
		
	}
	
	public String getStartDate() {
		
		return startDate;
		
	}
	
	public void setStartDate(String startDate) {
		
		this.startDate = startDate;
		
	}
	
	public String getEndDate() {
		
		return endDate;
		
	}
	
	public void setEndDate(String endDate) {
		
		this.endDate = endDate;
		
	}

}
