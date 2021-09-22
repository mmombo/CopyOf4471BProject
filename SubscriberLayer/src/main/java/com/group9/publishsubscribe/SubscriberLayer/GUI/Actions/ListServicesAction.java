package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;

public class ListServicesAction implements ActionListener {

	private final App backend;

	public ListServicesAction(App backend) {

		this.backend = backend;

	}

	public void actionPerformed(ActionEvent e) {
		
		backend.listServicesRequest();

	}

}