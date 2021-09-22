package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.SubscribeActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.MainFrame;

public class UnsubscribeAction implements ActionListener {
	
	private static final Logger logger = LogManager.getLogger(UnsubscribeAction.class.getName());
	
	private final App backend;
	private final SubscribeActionContext context;
	
	public UnsubscribeAction(App backend, SubscribeActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		backend.getSubscriber().removeSubscription(context.getServiceName());
		backend.updateRequest();
		((MainFrame)backend.getMainFrame()).removeView(context.getServiceName());
		
	}

}