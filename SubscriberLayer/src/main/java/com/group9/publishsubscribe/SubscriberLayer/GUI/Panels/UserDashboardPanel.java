package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.LogoutAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.UpdateProfileAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.LogoutActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.Fonts;

public class UserDashboardPanel extends JPanel {
	
	App backend;
	
	private JLabel lblMock;
	
	private JButton btnUpdateProfile;
	private JButton btnSeeSubscriptions;
	private JButton btnLogout;
	
	public UserDashboardPanel(App backend) {
		
		this.backend = backend;
		
		lblMock = new JLabel("Welcome " + backend.getSubscriber().getUsername() + "!");
		lblMock.setFont(Fonts.Medium);
		
		LogoutActionContext context = new LogoutActionContext(backend.getMainFrame());
		
		btnUpdateProfile = new JButton("Update Profile");
		btnUpdateProfile.addActionListener(new UpdateProfileAction(backend));
		
		btnSeeSubscriptions = new JButton("See Subscriptions");
		
		btnLogout = new JButton("Log out");
		btnLogout.addActionListener(new LogoutAction(backend, context));
		
		this.setLayout(new GridLayout(1, 4));
		this.add(lblMock);
		this.add(btnUpdateProfile);
		this.add(btnSeeSubscriptions);
		this.add(btnLogout);	
		
	}
	
	public void updateView(String currentDate) {
		
		String year = currentDate.substring(0, 4);
		int monthNum = Integer.parseInt(currentDate.substring(4, 6));
		int dayNum = Integer.parseInt(currentDate.substring(6, currentDate.length()));
		String month;

		switch (monthNum) {

		case 1:

			month = "January";
			break;

		case 2:

			month = "February";
			break;

		case 3:

			month = "March";
			break;

		case 4:

			month = "April";
			break;

		case 5:

			month = "May";
			break;

		case 6:

			month = "June";
			break;

		case 7:

			month = "July";
			break;

		case 8:

			month = "August";
			break;

		case 9:

			month = "September";
			break;

		case 10:

			month = "October";
			break;

		case 11:

			month = "November";
			break;

		case 12:

			month = "December";
			break;

		default:

			month = null;
			break;

		}
		
		String date = month + " " + dayNum + ", " + year;
		lblMock.setText("Welcome " + backend.getSubscriber().getUsername() + "! Today's date is " + date);
		
	}

}