package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.VolumeOverTimeSearchActionContext;

public class VolumeOverTimeSearchAction implements ActionListener {
	
	private final App backend;
	private final VolumeOverTimeSearchActionContext context;
	
	public VolumeOverTimeSearchAction(App backend, VolumeOverTimeSearchActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String companyName = context.getCompanyName().getText().trim();		
		backend.volumeOverTimeRequest(companyName);
		
	}

}