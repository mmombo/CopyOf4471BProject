package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class WelcomeToLoginActionContext {
	
	private final JFrame welcomeFrame;
	
	public WelcomeToLoginActionContext(JFrame welcomeFrame) {
		
		this.welcomeFrame = welcomeFrame;
		
	}
	
	public JFrame getWelcomeFrame() {
		
		return welcomeFrame;
		
	}

}