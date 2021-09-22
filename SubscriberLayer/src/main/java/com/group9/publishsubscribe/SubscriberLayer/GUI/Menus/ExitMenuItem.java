package com.group9.publishsubscribe.SubscriberLayer.GUI.Menus;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.ExitApplicationAction;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class ExitMenuItem extends JMenuItem {
	
	public ExitMenuItem(App backend) {
		
		super("Exit");
		this.setMnemonic(KeyEvent.VK_X);
		this.setToolTipText("Exit application");
		this.addActionListener(new ExitApplicationAction());
		
	}
	
}