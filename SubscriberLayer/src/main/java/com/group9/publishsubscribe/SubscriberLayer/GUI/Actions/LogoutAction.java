package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LogoutActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.WelcomeFrame;

public class LogoutAction implements ActionListener {
	
	private final App backend;
	private final LogoutActionContext context;
	
	public LogoutAction(App backend, LogoutActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		backend.logout();
		new WelcomeFrame(backend).setVisible(true);
		context.getMainFrame().dispose();	
		
	}

}