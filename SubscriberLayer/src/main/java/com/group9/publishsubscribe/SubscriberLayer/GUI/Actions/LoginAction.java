package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

public class LoginAction implements ActionListener {
	
	private final App backend;
	private final LoginActionContext context;
	
	public LoginAction(App backend, LoginActionContext context) {
		
		this.backend = backend;
		this.context = context;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String username = context.getLoginUsername().getText();
		String password = context.getLoginPassword().getText();
		
		if (username == null || username.length() == 0) {
			
			JOptionPane.showMessageDialog(null, "Please enter a username.");
			
		}
		
		else if (password == null || password.length() == 0) {
			
			JOptionPane.showMessageDialog(null, "Please enter a password.");
			
		}
		
		else {
			
			backend.loginRequest(username, password);
			BlockingQueue<Subscriber> queue = backend.getQueue();
			Subscriber subscriber = null;
			
			try {
				
				subscriber = queue.take();
				
			} catch (InterruptedException e1) {
								
				e1.printStackTrace();
				
			}
			
			if (subscriber.getUsername() != null) {
				
				new MainFrame(backend).setVisible(true);
				context.getLoginFrame().dispose();	
				
			}
			
			else {
				
				JOptionPane.showMessageDialog(null, "The username/password is not recognized.");
				
			}
			
		}
		
	}

}