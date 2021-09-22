package com.group9.publishsubscribe.CommonLayer.Models.Data;

import java.util.LinkedList;

public class Subscriber {
	
	private String username;
	private String password;
	private LinkedList<String> subscriptionList;
	private String subscriberIP;
	private int subscriberPort;
	
	public Subscriber() {
		
	}
	
	public Subscriber(String username, String password, String subscriberIP, int subscriberPort) {
		
		this.username = username;
		this.password = password;
		this.subscriptionList = new LinkedList<String>();
		this.subscriberIP = subscriberIP;
		this.subscriberPort = subscriberPort;
		
	}
	
	public Subscriber(String username, String password, LinkedList<String> subscriptionList, String subscriberIP, int subscriberPort) {
		
		this.username = username;
		this.password = password;
		this.subscriptionList = subscriptionList;
		this.subscriberIP = subscriberIP;
		this.subscriberPort = subscriberPort;
		
	}
	
	public String getUsername() {
		
		return username;
		
	}
	
	public void setUsername(String username) {
		
		this.username = username;
		
	}
	
	public String getPassword() {
		
		return password;
		
	}
	
	public void setPassword(String password) {
		
		this.password = password;
		
	}
	
	public LinkedList<String> getSubscriptionList() {
		
		return subscriptionList;
		
	}
	
	public void setSubscriptionList(LinkedList<String> subscriptionList) {
		
		this.subscriptionList = subscriptionList;
		
	}
	
	public void addSubscription(String subscription) {
		
		if (subscriptionList == null) {
			
			subscriptionList = new LinkedList<>();
			
		}
		
		if (!subscriptionList.contains(subscription)) {
			
			subscriptionList.add(subscription);
			
		}
		
	}
	
	public void removeSubscription(String subscription) {
		
		if (subscriptionList == null) {
			
			subscriptionList = new LinkedList<>();
			
		}
		
		if (subscriptionList.contains(subscription)) {
			
			subscriptionList.remove(subscription);
			
		}
		
	}
	
	public String getSubscriberIP() {
		
		return subscriberIP;
		
	}
	
	public void setSubscriberIP(String subscriberIP) {
		
		this.subscriberIP = subscriberIP;
		
	}
	
	public int getSubscriberPort() {
		
		return subscriberPort;
		
	}
	
	public void setSubscriberPort(int subscriberPort) {
		
		this.subscriberPort = subscriberPort;
		
	}

}