package com.group9.publishsubscribe.CommonLayer.Models.Data;

public class Company {

	private String companyName;
	private String companySymbol;
	
	public Company(String companyName, String companySymbol) {
		
		this.companyName = companyName;
		this.companySymbol = companySymbol;
		
	}
	
	public String getCompanyName() {
		
		return companyName;
		
	}
	
	public void setCompanyName(String companyName) {
		
		this.companyName = companyName;
		
	}
	
	public String getCompanySymbol() {
		
		return companySymbol;
		
	}
	
	public void setCompanySymbol(String companySymbol) {
		
		this.companySymbol = companySymbol;
		
	}
	
}