package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class LoginActionContext {
	
	private final JFrame loginFrame;
	
	private JTextField txtUsername;
	private JTextField txtPassword;
	
	public LoginActionContext(JFrame loginFrame) {
		
		this.loginFrame = loginFrame;
		
	}
	
	public JTextField getLoginUsername() {
		
		return txtUsername;
		
	}
	
	public void setLoginUsername(JTextField txtUsername) {
		
		this.txtUsername = txtUsername;
		
	}
	
	public JTextField getLoginPassword() {
		
		return txtPassword;
		
	}
	
	public void setLoginPassword(JTextField txtPassword) {
		
		this.txtPassword = txtPassword;
		
	}
	
	public JFrame getLoginFrame() {
		
		return loginFrame;
		
	}

}