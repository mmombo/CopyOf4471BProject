package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.SubscribeActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.MainFrame;

public class SubscribeAction implements ActionListener {
	
	private final App backend;
	private final SubscribeActionContext context;
	
	public SubscribeAction(App backend, SubscribeActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		backend.getSubscriber().addSubscription(context.getServiceName());
		backend.updateRequest();
		((MainFrame)backend.getMainFrame()).addView(context.getServiceName());
		
	}

}