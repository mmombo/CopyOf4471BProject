package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;

public class VolumeOverTimeResponse extends ServiceResponse {
	
	private String companyName;
	private String companySymbol;
	private String startDate;
	private String endDate;
	private int[] days;
	private int[] months;
	private int[] years;
	private String[] volumes;
	
	public VolumeOverTimeResponse() {
		
	}
	
	public VolumeOverTimeResponse(String companyName, String companySymbol, String startDate, String endDate,
			int[] days, int[] months, int[] years, String[] volumes) {
		
		super();
		this.companyName = companyName;
		this.companySymbol = companySymbol;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.months = months;
		this.years = years;
		this.volumes = volumes;
		
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

	public String[] getVolumes() {
		
		return volumes;
		
	}

	public void setVolumes(String[] volumes) {
		
		this.volumes = volumes;
		
	}

}