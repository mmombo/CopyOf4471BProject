package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.Component;

import javax.swing.*;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.LoginAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.RegisterAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.RegisterToLoginAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginToRegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.RegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.RegisterToLoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class RegisterPanel extends JPanel {
	
	private final App backend;
	
	private final JFrame registerFrame;
	
	private final JButton btnRegister;
	private final JButton btnLogin;
	
	private final JTextField txtUsername;
	private final JTextField txtPassword;
	private final JTextField txtPasswordConfirm;
	
	public RegisterPanel(App backend, JFrame registerFrame) {
		
		this.backend = backend;	
		this.registerFrame = registerFrame;
		
		txtUsername = new JTextField();
		txtPassword = new JTextField();
		txtPasswordConfirm = new JTextField();
		
		RegisterActionContext context = new RegisterActionContext(registerFrame);
		context.setRegisterUsername(txtUsername);
		context.setRegisterPassword(txtPassword);
		context.setRegisterPasswordConfirm(txtPasswordConfirm);
		
		RegisterToLoginActionContext context2 = new RegisterToLoginActionContext(registerFrame);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new RegisterAction(backend, context));
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new RegisterToLoginAction(backend, context2));
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		
		this.add(new JComponentLabel("Username", txtUsername, Component.LEFT_ALIGNMENT));
		this.add(new JComponentLabel("Password", txtPassword, Component.LEFT_ALIGNMENT));
		this.add(new JComponentLabel("Confirm Password", txtPasswordConfirm, Component.LEFT_ALIGNMENT));
		this.add(btnRegister);
		this.add(btnLogin);
		
		this.setLayout(layout);
		
	}

}
