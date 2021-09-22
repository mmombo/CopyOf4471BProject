package com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ServiceParameters;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = StockInfoRequest.class, name = "StockInfoRequest"),
	@JsonSubTypes.Type(value = PriceOverTimeRequest.class, name = "PriceOverTimeRequest"),
	@JsonSubTypes.Type(value = VolumeOverTimeRequest.class, name = "VolumeOverTimeRequest"),
	@JsonSubTypes.Type(value = TopVolumeRequest.class, name = "TopVolumeRequest"),
	@JsonSubTypes.Type(value = CurrencyExchangeRequest.class, name = "CurrencyExchangeRequest")}
)
public class ServiceRequest extends Message {
	
	private ServiceParameters serviceParameters;
	
	public ServiceRequest(ServiceParameters parameters) {
		
		super();
		this.serviceParameters = parameters;
		
	}
	
	public ServiceParameters getServiceParameters() {
		
		return serviceParameters;
		
	}
	
	public void setServiceParameters(ServiceParameters parameters) {
		
		this.serviceParameters = parameters;
		
	}

}