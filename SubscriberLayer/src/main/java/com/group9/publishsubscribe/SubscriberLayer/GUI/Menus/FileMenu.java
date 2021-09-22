package com.group9.publishsubscribe.SubscriberLayer.GUI.Menus;

import com.group9.publishsubscribe.SubscriberLayer.App;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class FileMenu extends JMenu {
	
	public FileMenu(App backend) {
		
		super("File");
		this.setMnemonic(KeyEvent.VK_F);
		this.add(new ExitMenuItem(backend));
		
	}

}