package com.group9.publishsubscribe.SubscriberLayer.GUI.Menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import com.group9.publishsubscribe.SubscriberLayer.App;

public class EditMenu extends JMenu {
	
	public EditMenu(App backend) {
		
		super("Edit");
		this.setMnemonic(KeyEvent.VK_E);
		this.add(new NextDateMenuItem(backend));
		this.add(new PreviousDateMenuItem(backend));
		
	}

}