package com.group9.publishsubscribe.SubscriberLayer.GUI.Models;

public class StockInfo {
	
	private String companyName;
	private String companySymbol;
	private String date;
	private String totalVolume;
	private String high;
	private String low;
	private String openingPrice;
	private String closingPrice;
	
	public StockInfo() {
		
		
		
	}
	
	public StockInfo(String companyName, String companySymbol, String date, String totalVolume, String high, String low, String openingPrice, String closingPrice) {
		
		this.companyName = companyName;
		this.companySymbol = companySymbol;
		this.date = date;
		this.totalVolume = totalVolume;
		this.high = high;
		this.low = low;
		this.openingPrice = openingPrice;
		this.closingPrice = closingPrice;
		
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
	
	public String getDate() {
		
		return date;
		
	}
	
	public void setDate(String date) {
		
		this.date = date;
		
	}
	
	public String getTotalVolume() {
		
		return totalVolume;
		
	}
	
	public void setTotalVolume(String totalVolume) {
		
		this.totalVolume = totalVolume;
		
	}
	
	public String getHigh() {
		
		return high;
		
	}
	
	public void setHigh(String high) {
		
		this.high = high;
		
	}
	
	public String getLow() {
		
		return low;
		
	}
	
	public void setLow(String low) {
		
		this.low = low;
		
	}
	
	public String getOpeningPrice() {
		
		return openingPrice;
		
	}
	
	public void setOpeningPrice(String openingPrice) {
		
		this.openingPrice = openingPrice;
		
	}
	
	public String getClosingPrice() {
		
		return closingPrice;
		
	}
	
	public void setClosingPrice(String closingPrice) {
		
		this.closingPrice = closingPrice;
		
	}

}