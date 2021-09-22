package com.group9.publishsubscribe.SubscriberLayer;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**import com.group9.publishsubscribe.MessageLayer.MessageManager;
import com.group9.publishsubscribe.PublisherLayer.DataManager.DataManager;
import com.group9.publishsubscribe.PublisherLayer.ServiceManager.ServiceManager;
import com.group9.publishsubscribe.PublisherLayer.ServiceRegistry.ServiceRegistry;
import com.group9.publishsubscribe.PublisherLayer.Services.CurrencyExchangeService;
import com.group9.publishsubscribe.PublisherLayer.Services.PriceOverTimeService;
import com.group9.publishsubscribe.PublisherLayer.Services.StockInfoService;
import com.group9.publishsubscribe.PublisherLayer.Services.TopVolumeService;
import com.group9.publishsubscribe.PublisherLayer.Services.VolumeOverTimeService;*/
import com.group9.publishsubscribe.SubscriberLayer.SubscriberManager.SubscriberManager;

public class Main {
	
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		
		logger.info("Starting Main");
		
		int myPort = -1;
		String messageManagerIP = null;
		
		if (args.length == 0) {
			
			myPort = 2500;
			messageManagerIP = "127.0.0.1";
			
		}
		
		else if (args.length == 1) {
			
			myPort = Integer.parseInt(args[0]);
			
		}
		
		else if (args.length == 2) {
			
			myPort = Integer.parseInt(args[0]);
			messageManagerIP = args[1];
			
		}
		
		SubscriberManager subscriberManager = new SubscriberManager(myPort, messageManagerIP);
		App backend = new App(subscriberManager);
		subscriberManager.setApp(backend);

		backend.readCompanies();
		
		Runnable guiRunner = new GuiRunner(backend);
		SwingUtilities.invokeLater(guiRunner);

	}

}