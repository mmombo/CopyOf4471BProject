package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.Component;

import javax.swing.*;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.LoginAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.LoginSkipAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.LoginToRegisterAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginSkipActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginToRegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class LoginPanel extends JPanel {
	
	private final App backend;
	
	private final JFrame loginFrame;
	
	private final JButton btnLogin;
	private final JButton btnRegister;
	private final JButton btnSkip;
	
	private final JTextField txtUsername;
	private final JTextField txtPassword;
	
	public LoginPanel(App backend, JFrame loginFrame) {
		
		this.backend = backend;	
		this.loginFrame = loginFrame;
		
		txtUsername = new JTextField();
		txtPassword = new JTextField();
		
		LoginActionContext context = new LoginActionContext(loginFrame);
		context.setLoginUsername(txtUsername);
		context.setLoginPassword(txtPassword);
		
		LoginToRegisterActionContext context2 = new LoginToRegisterActionContext(loginFrame);
		LoginSkipActionContext context3 = new LoginSkipActionContext(loginFrame);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginAction(backend, context));
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new LoginToRegisterAction(backend, context2));
		
		btnSkip = new JButton("Skip");
		btnSkip.addActionListener(new LoginSkipAction(backend, context3));
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		
		this.add(new JComponentLabel("Username", txtUsername, Component.LEFT_ALIGNMENT));
		this.add(new JComponentLabel("Password", txtPassword, Component.LEFT_ALIGNMENT));
		this.add(btnLogin);
		this.add(btnRegister);
		this.add(btnSkip);
		
		this.setLayout(layout);
		
	}

}