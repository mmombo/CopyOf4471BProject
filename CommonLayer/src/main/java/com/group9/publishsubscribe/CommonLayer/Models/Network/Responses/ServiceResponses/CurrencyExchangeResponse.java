package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Rates;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;

public class CurrencyExchangeResponse extends ServiceResponse {

	private Rates rates;
	private String date;
	private String base;

	public CurrencyExchangeResponse() {

	}

	public CurrencyExchangeResponse(Rates rates) {

		super();
		this.setRates(rates);
	}

	public Rates getRates() {
		return rates;
	}

	public void setRates(Rates rates) {
		this.rates = rates;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

}