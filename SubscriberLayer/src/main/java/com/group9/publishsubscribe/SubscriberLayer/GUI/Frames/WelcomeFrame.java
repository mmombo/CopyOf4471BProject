package com.group9.publishsubscribe.SubscriberLayer.GUI.Frames;

import java.awt.Dimension;
import javax.swing.*;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.WelcomePanel;

public class WelcomeFrame extends JFrame {
	
	private final App backend;

	public WelcomeFrame(App backend) {

		super("Welcome");
		
		this.backend = backend;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Dimension size = new Dimension(500, 185);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		
		this.add(new WelcomePanel(backend, this));
		this.pack();

	}

}