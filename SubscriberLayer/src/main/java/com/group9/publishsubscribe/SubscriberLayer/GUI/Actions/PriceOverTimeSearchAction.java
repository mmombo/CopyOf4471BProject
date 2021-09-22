package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.PriceOverTimeSearchActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.StockInfoSearchActionContext;

public class PriceOverTimeSearchAction implements ActionListener {
	
	private final App backend;
	private final PriceOverTimeSearchActionContext context;
	
	public PriceOverTimeSearchAction(App backend, PriceOverTimeSearchActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String companyName = context.getCompanyName().getText().trim();		
		backend.priceOverTimeRequest(companyName);
		
	}

}