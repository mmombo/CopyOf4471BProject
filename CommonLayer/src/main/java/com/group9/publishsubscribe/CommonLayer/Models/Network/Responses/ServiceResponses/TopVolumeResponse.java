package com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses;

import java.util.LinkedHashMap;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;;

public class TopVolumeResponse extends ServiceResponse {

	LinkedHashMap<String, Integer> bestTrades;
	LinkedHashMap<String, Integer> worstTrades;

	public TopVolumeResponse() {
		
	}
	
    public TopVolumeResponse(LinkedHashMap<String, Integer> bestTrades, LinkedHashMap<String, Integer> worstTrades) {	
		super();
		this.bestTrades = bestTrades;	
		this.worstTrades = worstTrades;		
	}
    
	public LinkedHashMap<String, Integer> getBestTrades() {
		return this.bestTrades;	
	}
	
	public void setBestTrades(LinkedHashMap<String, Integer> bestTrades) {	
		this.bestTrades = bestTrades;	
	}
	
	public LinkedHashMap<String, Integer> getWorstTrades() {
		return this.worstTrades;	
	}
	
	public void setWorstTrades(LinkedHashMap<String, Integer> worstTrades) {	
		this.worstTrades = worstTrades;	
	}
}