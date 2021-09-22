package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;;

public class StockInfoResponse extends ServiceResponse {
	
	private String companyName;
	private String companySymbol;
	private String date;
	private String totalVolume;
	private String high;
	private String low;
	private String opening;
	private String closing;
	
	public StockInfoResponse() {
		
	}
	
	public StockInfoResponse(String companyName, String companySymbol, String date, String totalVolume, String high, String low, String opening, String closing) {
		
		super();
		this.companyName = companyName;
		this.companySymbol = companySymbol;
		this.date = date;
		this.totalVolume = totalVolume;
		this.high = high;
		this.low = low;
		this.opening = opening;
		this.closing = closing;
		
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
	
	public String getOpening() {
		
		return opening;
		
	}
	
	public void setOpening(String opening) {
		
		this.opening = opening;
		
	}
	
	public String getClosing() {
		
		return closing;
		
	}
	
	public void setClosing(String closing) {
		
		this.closing = closing;
		
	}

}