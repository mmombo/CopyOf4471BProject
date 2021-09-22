package com.group9.publishsubscribe.SubscriberLayer.GUI.Menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.PreviousDateAction;

public class PreviousDateMenuItem extends JMenuItem {
	
	public PreviousDateMenuItem(App backend) {
		
		super("Previous Date");
		this.setMnemonic(KeyEvent.VK_P);
		this.setToolTipText("Previous Date");
		this.addActionListener(new PreviousDateAction(backend));
		
	}	

}