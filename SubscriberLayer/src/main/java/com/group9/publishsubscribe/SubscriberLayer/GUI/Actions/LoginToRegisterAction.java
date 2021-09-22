package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginToRegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.WelcomeToLoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.LoginFrame;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.RegisterFrame;

public class LoginToRegisterAction implements ActionListener {
	
	private final App backend;
	
	private LoginToRegisterActionContext context;
	
	public LoginToRegisterAction(App backend, LoginToRegisterActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		new RegisterFrame(backend).setVisible(true);
		context.getLoginFrame().dispose();		
		
	}

}
