package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class WelcomeToRegisterActionContext {
	
	private final JFrame welcomeFrame;
	
	public WelcomeToRegisterActionContext(JFrame welcomeFrame) {
		
		this.welcomeFrame = welcomeFrame;
		
	}
	
	public JFrame getWelcomeFrame() {
		
		return welcomeFrame;
		
	}

}