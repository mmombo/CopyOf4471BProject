package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.Component;

import javax.swing.*;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.WelcomeToLoginAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.WelcomeToRegisterAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.WelcomeToLoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.WelcomeToRegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.Fonts;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class WelcomePanel extends JPanel {
	
	private final App backend;
	
	private final JFrame welcomeFrame;
	
	private final JButton btnLogin;
	private final JButton btnRegister;
	
	private final JLabel lblWelcome;
	
	public WelcomePanel(App backend, JFrame welcomeFrame) {
		
		this.backend = backend;	
		this.welcomeFrame = welcomeFrame;
		
		WelcomeToLoginActionContext contextLogin = new WelcomeToLoginActionContext(welcomeFrame);
		WelcomeToRegisterActionContext contextRegister = new WelcomeToRegisterActionContext(welcomeFrame);
		
		btnLogin = new JButton("Login");
		btnRegister = new JButton("Register");
		
		lblWelcome = new JLabel("Welcome to our app!");
		lblWelcome.setFont(Fonts.Large);
		
		btnLogin.addActionListener(new WelcomeToLoginAction(backend, contextLogin));
		btnRegister.addActionListener(new WelcomeToRegisterAction(backend, contextRegister));
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		
		this.add(lblWelcome);
		this.add(new JComponentLabel("Returning user? Log in again!", btnLogin, Component.LEFT_ALIGNMENT));
		this.add(new JComponentLabel("New user? Register with us today!", btnRegister, Component.LEFT_ALIGNMENT));
		
		this.setLayout(layout);		
		
	}

}