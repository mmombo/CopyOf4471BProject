package com.group9.publishsubscribe.CommonLayer.Models.Data;

public class CurrencyExchange {
	
	private Rates rates;
	private String base; 
	private String date;	
	private boolean success;
	private int timestamp;
	
	public CurrencyExchange() {
		
	}
	
	public CurrencyExchange(Rates rates, String base, String date) {
		
		this.rates = rates;
		this.base = base;
		this.date = date;
		
	}
	
	public Rates getRates() {
		return rates;
	}
	public void setRates(Rates rates) {
		this.rates = rates;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	} 	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
}