package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class LogoutActionContext {
	
	private final JFrame mainFrame;
	
	public LogoutActionContext(JFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
	}
	
	public JFrame getMainFrame() {
		
		return mainFrame;
		
	}

}