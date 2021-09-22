package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class LoginToRegisterActionContext {
	
	private final JFrame loginFrame;
	
	public LoginToRegisterActionContext(JFrame loginFrame) {
		
		this.loginFrame = loginFrame;
		
	}
	
	public JFrame getLoginFrame() {
		
		return loginFrame;
		
	}

}