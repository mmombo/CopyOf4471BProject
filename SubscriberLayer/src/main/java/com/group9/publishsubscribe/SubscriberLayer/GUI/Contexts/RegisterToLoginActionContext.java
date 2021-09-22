package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class RegisterToLoginActionContext {
	
	private final JFrame registerFrame;
	
	public RegisterToLoginActionContext(JFrame registerFrame) {
		
		this.registerFrame = registerFrame;
		
	}
	
	public JFrame getRegisterFrame() {
		
		return registerFrame;
		
	}

}