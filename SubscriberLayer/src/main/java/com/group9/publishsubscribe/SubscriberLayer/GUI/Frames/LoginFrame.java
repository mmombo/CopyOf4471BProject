package com.group9.publishsubscribe.SubscriberLayer.GUI.Frames;

import java.awt.Dimension;

import javax.swing.*;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.LoginPanel;

public class LoginFrame extends JFrame {

	private final App backend;

	public LoginFrame(App backend) {

		super("Login");
		
		this.backend = backend;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Dimension size = new Dimension(300, 200);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		
		this.add(new LoginPanel(backend, this));
		this.pack();

	}
	
}