package com.group9.publishsubscribe.SubscriberLayer.GUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JOptionPane;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LoginActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.RegisterActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.MainFrame;

public class RegisterAction implements ActionListener {

	private final App backend;
	private final RegisterActionContext context;

	public RegisterAction(App backend, RegisterActionContext context) {

		this.backend = backend;
		this.context = context;

	}

	public void actionPerformed(ActionEvent e) {

		String username = context.getRegisterUsername().getText();
		String password = context.getRegisterPassword().getText();
		String passwordConfirm = context.getRegisterPasswordConfirm().getText();

		if (username == null || username.length() == 0) {

			JOptionPane.showMessageDialog(null, "Please enter a username.");

		}

		else if (password == null || password.length() == 0) {

			JOptionPane.showMessageDialog(null, "Please enter a password.");

		}

		else if (passwordConfirm == null || passwordConfirm.length() == 0) {

			JOptionPane.showMessageDialog(null, "Please confirm your password.");

		}

		else if (!password.equals(passwordConfirm)) {

			JOptionPane.showMessageDialog(null, "Please make sure your passwords match.");

		}

		else {

			backend.registerRequest(username, password);
			BlockingQueue<Subscriber> queue = backend.getQueue();
			Subscriber subscriber = null;

			try {

				subscriber = queue.take();

			} catch (InterruptedException e1) {

				e1.printStackTrace();

			}

			if (subscriber.getUsername() != null) {

				new MainFrame(backend).setVisible(true);
				context.getRegisterFrame().dispose();

			}

			else {

				JOptionPane.showMessageDialog(null, "This username/password already exists.");

			}

		}

	}

}