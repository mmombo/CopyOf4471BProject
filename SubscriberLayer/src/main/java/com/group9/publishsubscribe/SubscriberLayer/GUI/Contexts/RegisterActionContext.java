package com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts;

import javax.swing.*;

public class RegisterActionContext {
	
	private final JFrame registerFrame;
	
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtPasswordConfirm;
	
	public RegisterActionContext(JFrame registerFrame) {
		
		this.registerFrame = registerFrame;
		
	}
	
	public JTextField getRegisterUsername() {
		
		return txtUsername;
		
	}
	
	public void setRegisterUsername(JTextField txtUsername) {
		
		this.txtUsername = txtUsername;
		
	}
	
	public JTextField getRegisterPassword() {
		
		return txtPassword;
		
	}
	
	public void setRegisterPassword(JTextField txtPassword) {
		
		this.txtPassword = txtPassword;
		
	}
	
	public JTextField getRegisterPasswordConfirm() {
		
		return txtPasswordConfirm;
		
	}
	
	public void setRegisterPasswordConfirm(JTextField txtPasswordConfirm) {
		
		this.txtPasswordConfirm = txtPasswordConfirm;
		
	}
	
	public JFrame getRegisterFrame() {
		
		return registerFrame;
		
	}

}
