package com.group9.publishsubscribe.SubscriberLayer.GUI.Menus;

import com.group9.publishsubscribe.SubscriberLayer.App;
import javax.swing.*;

public class MenuBar extends JMenuBar {
	
	public MenuBar(App backend) {
		
		this.add(new FileMenu(backend));
		this.add(new EditMenu(backend));
		
	}

}