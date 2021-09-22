package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.RegisterToLoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.LoginFrame;

public class RegisterToLoginAction implements ActionListener {
	
	private final App backend;
	private final RegisterToLoginActionContext context;
	
	public RegisterToLoginAction(App backend, RegisterToLoginActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		new LoginFrame(backend).setVisible(true);
		context.getRegisterFrame().dispose();		
		
	}

}