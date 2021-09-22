package com.group9.publishsubscribe.SubscriberLayer.GUI.Models;

import java.util.LinkedHashMap;

public class TopVolume {
	
	private LinkedHashMap<String, Integer> bestTrades;
	private LinkedHashMap<String, Integer> worstTrades;

	public TopVolume(LinkedHashMap<String, Integer> bestTrades, LinkedHashMap<String, Integer> worstTrades) {

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