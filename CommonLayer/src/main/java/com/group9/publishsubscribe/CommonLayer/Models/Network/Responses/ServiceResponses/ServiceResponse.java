package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.CurrencyExchangeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.PriceOverTimeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.StockInfoResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.TopVolumeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.VolumeOverTimeResponse;

@JsonSubTypes({
	@JsonSubTypes.Type(value = StockInfoResponse.class, name = "StockInfoResponse"),
	@JsonSubTypes.Type(value = PriceOverTimeResponse.class, name = "PriceOverTimeResponse"),
	@JsonSubTypes.Type(value = VolumeOverTimeResponse.class, name = "VolumeOverTimeResponse"),
	@JsonSubTypes.Type(value = TopVolumeResponse.class, name = "TopVolumeResponse"),
	@JsonSubTypes.Type(value = CurrencyExchangeResponse.class, name = "CurrencyExchangeResponse")}
)
public class ServiceResponse extends Message {
	
	public ServiceResponse() {
		
		super();
		
	}

}