package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.WelcomeToRegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.RegisterFrame;

public class WelcomeToRegisterAction implements ActionListener {
	
	private final App backend;
	
	private WelcomeToRegisterActionContext context;
	
	public WelcomeToRegisterAction(App backend, WelcomeToRegisterActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		new RegisterFrame(backend).setVisible(true);
		context.getWelcomeFrame().dispose();		
		
	}

}