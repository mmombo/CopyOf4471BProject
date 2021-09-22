package com.group9.publishsubscribe.SubscriberLayer.SubscriberManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.CurrentDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.NextDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.PreviousDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ServiceParameters;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.LoginRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.RegisterRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.UpdateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.StockInfoRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.TopVolumeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.VolumeOverTimeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.CurrencyExchangeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.PriceOverTimeRequest;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;

public class SubscriberManager extends ThreadPoolServer {

	/**
	 * This is the logger that allows more robust logging to the console (or not)
	 * and to a debug file.
	 */
	private static final Logger logger = LogManager.getLogger(SubscriberManager.class.getName());

	private static final int messageManagerPort = 2501;
	
	private String messageManagerIP;

	private String myIP;
	private int myPort;

	private App app;

	/**
	 * Creates a new SubscriberManager. This needs to instantiate a library the can
	 * turn objects into JSON String and back again. This temporarily makes a
	 * MessageManager object.
	 * 
	 * @param messageManager
	 */
	public SubscriberManager(int port, String messageManagerIP) {

		super();
		myPort = port;
		this.messageManagerIP = messageManagerIP;
		super.setPort(myPort);

		URL whatismyip;

		try {

			whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			myIP = in.readLine();

		} catch (IOException e) {

			e.printStackTrace();

		}

		logger.info("Starting SubscriberManager");

	}

	public void setApp(App app) {

		this.app = app;
		new Thread(this).start();
		new Thread(new SubscriberManagerListener(app, queue)).start();

	}

	/**
	 * Takes the user's input from the GUI login page and sends it across the
	 * network to the MessageManager.
	 * 
	 * @param username the user's username
	 * @param password the user's password
	 */
	public void loginSubscriber(String username, String password) {

		LoginRequest request = new LoginRequest(username, password, myIP, myPort);
		logger.info("Sending a LoginRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Takes the user's input from the GUI register page and sends it across the
	 * network to the MessageManager.
	 * 
	 * @param username the user's username
	 * @param password the user's password
	 */
	public void registerSubscriber(String username, String password) {

		RegisterRequest request = new RegisterRequest(username, password, myIP, myPort);
		logger.info("Sending a RegisterRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Takes the current subscriber, plus or minus the service that was added or
	 * removed, and sends it across the network to the MessageManager.
	 * 
	 * @param subscriber the subscriber object, which includes the username,
	 *                   password, and subscriptionlist
	 */
	public void updateSubscriber(Subscriber subscriber) {

		UpdateRequest request = new UpdateRequest(subscriber);
		logger.info("Sending an UpdateRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Gets a list of available services from the ServiceRegistry, by forwarding the
	 * message to the MessageManager.
	 */
	public void listServices() {

		ListServicesRequest request = new ListServicesRequest(myIP, myPort);
		logger.info("Sending a ListServicesRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Takes the user's parameter selections and sends them to the StockInfoService,
	 * by forwarding the message to the MessageManager.
	 * 
	 * @param companyName the name of the company
	 * @param startDate   the startDate in the date range
	 * @param endDate     the endDate in the date range
	 * @return a StockInfo object holding all of the appropriate data matching the
	 *         user's parameter selections
	 */
	public void getStockInfo(String companyName, String startDate) {

		StockInfoRequest request = new StockInfoRequest(new ServiceParameters(companyName, startDate, null));
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a StockInfoRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Takes the user's parameter selections and sends them to the
	 * PriceOverTimeService, by forwarding the message to the MessageManager.
	 * 
	 * @param companyName the name of the company
	 */
	public void getPriceOverTime(String companyName) {
		PriceOverTimeRequest request = new PriceOverTimeRequest(new ServiceParameters(companyName, null, null));
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a PriceOverTimRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);
	}

	/**
	 * Takes the user's parameter selections and sends them to the
	 * VolumeOverTimeService, by forwarding the message to the MessageManager.
	 * 
	 * @param companyName the name of the company
	 */
	public void getVolumeOverTime(String companyName) {

		VolumeOverTimeRequest request = new VolumeOverTimeRequest(new ServiceParameters(companyName, null, null));
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a VolumeOverTimeRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Takes the user's parameter selections and sends them to the TopVolumeService,
	 * by forwarding the message to the MessageManager.
	 * 
	 * @param startDate the startDate in the date range
	 * @param endDate   the endDate in the date range
	 */
	public void getTopVolume(String startDate) {

		TopVolumeRequest request = new TopVolumeRequest(new ServiceParameters(startDate));
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a TopVolumeRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	/**
	 * Takes the user's parameter selections and sends them to the
	 * CurrencyExchangeService, by forwarding the message to the MessageManager.
	 * 
	 * @param startDate the startDate in the date range
	 */
	public void getCurrencyExchange() {

		CurrencyExchangeRequest request = new CurrencyExchangeRequest();
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a CurrencyExchangeRequest to the MessageManager");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	public void currentDateRequest() {

		CurrentDateRequest request = new CurrentDateRequest();
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a CurrentDateRequest to get the current date");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	public void previousDateRequest(String currentDate) {

		PreviousDateRequest request = new PreviousDateRequest(currentDate);
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a PreviousDateRequest to go to the previous date");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

	public void nextDateRequest(String currentDate) {

		NextDateRequest request = new NextDateRequest(currentDate);
		request.setSubscriberIP(myIP);
		request.setSubscriberPort(myPort);
		logger.info("Sending a NextDateRequest to go to the next date");
		sendMessage(request, messageManagerIP, messageManagerPort);

	}

}