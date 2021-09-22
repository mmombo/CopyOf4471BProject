package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginSkipActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.MainFrame;

public class LoginSkipAction implements ActionListener {

	private final App backend;
	private final LoginSkipActionContext context;

	public LoginSkipAction(App backend, LoginSkipActionContext context) {

		this.backend = backend;
		this.context = context;

	}

	public void actionPerformed(ActionEvent e) {
		
		backend.registerRequest("admin", "admin");
		BlockingQueue<Subscriber> queue = backend.getQueue();
		Subscriber subscriber = null;

		try {

			subscriber = queue.take();

		} catch (InterruptedException e1) {

			e1.printStackTrace();

		}
		
		if (subscriber.getUsername() == null) {
			
			backend.loginRequest("admin", "admin");
			
			try {

				subscriber = queue.take();

			} catch (InterruptedException e1) {

				e1.printStackTrace();

			}			
			
		}

		new MainFrame(backend).setVisible(true);
		context.getLoginFrame().dispose();

	}

}