package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.StockInfoSearchActionContext;

public class StockInfoSearchAction implements ActionListener {
	
	private final App backend;
	private final StockInfoSearchActionContext context;
	
	public StockInfoSearchAction(App backend, StockInfoSearchActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String companyName = context.getCompanyName().getText().trim();
		String date = context.getDate().getText();
		String[] dateTokens = date.split(" ");
		String year = dateTokens[2];
		String month = dateTokens[0];
		String day = dateTokens[1].substring(0, dateTokens[1].length() - 1);
		
		if (month.equals("January")) {
			
			month = "01";
			
		}
		
		else if (month.equals("February")) {
			
			month = "02";
			
		}
		
		else if (month.equals("March")) {
			
			month = "03";
			
		}
		
		else if (month.equals("April")) {
			
			month = "04";
			
		}
		
		else if (month.equals("May")) {
			
			month = "05";
			
		}
		
		else if (month.equals("June")) {
			
			month = "06";
			
		}
		
		else if (month.equals("July")) {
			
			month = "07";
			
		}
		
		else if (month.equals("August")) {
			
			month = "08";
			
		}
		
		else if (month.equals("September")) {
			
			month = "09";
			
		}
		
		else if (month.equals("October")) {
			
			month = "10";
			
		}
		
		else if (month.equals("November")) {
			
			month = "11";
			
		}
		
		else if (month.equals("December")) {
			
			month = "12";
			
		}
		
		backend.stockInfoRequest(companyName, year + month + day);
		
	}

}