package com.group9.publishsubscribe.SubscriberLayer.GUI.Menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.NextDateAction;

public class NextDateMenuItem extends JMenuItem {
	
	public NextDateMenuItem(App backend) {
		
		super("Next Date");
		this.setMnemonic(KeyEvent.VK_N);
		this.setToolTipText("Next Date");
		this.addActionListener(new NextDateAction(backend));
		
	}	

}