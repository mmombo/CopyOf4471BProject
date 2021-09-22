package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;

public class PriceOverTimeResponse extends ServiceResponse {
	
	// Company information
	private String companyName;
	private String companySymbol;
	
	// Start and end date of the date range
	private String startDate;
	private String endDate;
	
	// Arrays of dates and prices for the company
	private int[] days;
	private int[] months;
	private int[] years;
	private String[] prices;
	
	/*
	 * Create an instance of PriceOverTimeResponseClass
	 */
	public PriceOverTimeResponse() {
		
	}	
	
	/*
	 * Create an instance of PriceOverTimeResponseClass
	 * @param companyName
	 * @param companySymbol
	 * @param startDate
	 * @param endDate
	 * @param dates
	 * @param prices
	 */
	public PriceOverTimeResponse(String companyName, String companySymbol, String startDate, String endDate,
			int[] days, int[] months, int[] years, String[] prices) {
		
		super();
		this.companyName = companyName;
		this.companySymbol = companySymbol;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.months = months;
		this.years = years;
		this.prices = prices;
		
	}
	
	/*
	 * Getter method for company name
	 * @return companyName
	 */
	public String getCompanyName() {
		
		return companyName;
		
	}

	/*
	 * Setter method for company name
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		
		this.companyName = companyName;
		
	}

	/*
	 * Getter method for company symbol
	 * @return companySymbol
	 */
	public String getCompanySymbol() {
		
		return companySymbol;
		
	}

	/*
	 * Setter method for company symbol
	 * @param companySymbol
	 */
	public void setCompanySymbol(String companySymbol) {
		
		this.companySymbol = companySymbol;
		
	}

	/*
	 * Getter method for start date
	 * @return startDate
	 */
	public String getStartDate() {
		
		return startDate;
		
	}

	/*
	 * Setter method for start date
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		
		this.startDate = startDate;
		
	}

	/*
	 * Getter method for end date
	 * @return endDate
	 */
	public String getEndDate() {
		
		return endDate;
		
	}

	/*
	 * Setter method for end date
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		
		this.endDate = endDate;
		
	}

	/*
	 * Getter method for days array
	 * @return days
	 */
	public int[] getDays() {
		
		return days;
		
	}

	/*
	 * Setter method for days array
	 * @param days
	 */
	public void setDays(int[] days) {
		
		this.days = days;
		
	}
	
	/*
	 * Getter method for months array
	 * @return months
	 */
	public int[] getMonths() {
		
		return months;
		
	}

	/*
	 * Setter method for months array
	 * @param months
	 */
	public void setMonths(int[] months) {
		
		this.months = months;
		
	}
	
	/*
	 * Getter method for years array
	 * @return years
	 */
	public int[] getYears() {
		
		return years;
		
	}

	/*
	 * Setter method for years array
	 * @param years
	 */
	public void setYears(int[] years) {
		
		this.years = years;
		
	}

	/*
	 * Getter method for prices array
	 * @return prices
	 */
	public String[] getPrices() {
		
		return prices;
		
	}

	/*
	 * Setter method for prices array
	 * @param prices
	 */
	public void setPrices(String[] prices) {
		
		this.prices = prices;
		
	}
	

}