package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class LoginSkipActionContext {
	
	private final JFrame loginFrame;
	
	public LoginSkipActionContext(JFrame loginFrame) {
		
		this.loginFrame = loginFrame;
		
	}
	
	public JFrame getLoginFrame() {
		
		return loginFrame;
		
	}

}