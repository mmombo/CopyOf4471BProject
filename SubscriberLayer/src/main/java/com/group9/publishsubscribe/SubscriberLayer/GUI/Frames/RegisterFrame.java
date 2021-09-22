package com.group9.publishsubscribe.SubscriberLayer.GUI.Frames;

import java.awt.Dimension;
import javax.swing.*;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.RegisterPanel;

public class RegisterFrame extends JFrame {
	
	private final App backend;

	public RegisterFrame(App backend) {

		super("Register");
		
		this.backend = backend;
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Dimension size = new Dimension(300, 200);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		
		this.add(new RegisterPanel(backend, this));
		this.pack();

	}

}