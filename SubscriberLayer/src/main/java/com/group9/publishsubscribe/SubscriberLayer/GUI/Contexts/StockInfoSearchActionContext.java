package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

public class StockInfoSearchActionContext {
	
	private JTextField txtCompanyName;
	private DatePicker txtDate;
	
	public StockInfoSearchActionContext() {
		
		
		
	}
	
	public JTextField getCompanyName() {
		
		return txtCompanyName;
		
	}
	
	public void setCompanyName(JTextField txtCompanyName) {
		
		this.txtCompanyName = txtCompanyName;
		
	}
	
	public DatePicker getDate() {
		
		return txtDate;
		
	}
	
	public void setDate(DatePicker txtDate) {
		
		this.txtDate = txtDate;
		
	}

}