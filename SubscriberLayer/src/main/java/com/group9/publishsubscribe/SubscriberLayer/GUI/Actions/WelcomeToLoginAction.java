package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.WelcomeToLoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.LoginFrame;

public class WelcomeToLoginAction implements ActionListener {
	
	private final App backend;
	
	private WelcomeToLoginActionContext context;
	
	public WelcomeToLoginAction(App backend, WelcomeToLoginActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		new LoginFrame(backend).setVisible(true);
		context.getWelcomeFrame().dispose();
		
	}

}