package com.group9.publishsubscribe.SubscriberLayer;

import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.WelcomeFrame;

public class GuiRunner implements Runnable {
	
	private final App backend;
	
	public GuiRunner(App backend) {
		
		this.backend = backend;
		
	}
	
	public void run() {
		
		new WelcomeFrame(backend).setVisible(true);
		
	}

}