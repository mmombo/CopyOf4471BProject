package com.group9.publishsubscribe.SubscriberLayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Company;
import com.group9.publishsubscribe.CommonLayer.Models.Data.CurrencyExchange;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.PriceOverTime;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.StockInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Frames.MainFrame;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.TopVolumePanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.UserDashboardPanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.TopVolume;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.VolumeOverTime;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.CurrencyExchangePanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.ListServicesPanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.PriceOverTimePanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.StockInfoPanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.VolumeOverTimePanel;
import com.group9.publishsubscribe.SubscriberLayer.SubscriberManager.SubscriberManager;

public class App {
	
	private static final Logger logger = LogManager.getLogger(App.class);
	
	private static final String MASTER_COMPANY_LIST = ".." + File.separator + "data" + File.separator + "Companies.txt";
	
	private final SubscriberManager subscriberManager;
	
	private JFrame mainFrame;

	private BlockingQueue<Subscriber> queue = new LinkedBlockingQueue<>();
	
	private List<Company> companyList = new ArrayList<>();

	private Subscriber subscriber;
	
	private UserDashboardPanel userDashboardPanel;
	private ListServicesPanel listServicesPanel;

	private StockInfoPanel stockInfoPanel;
	private StockInfo stockInfo;
	
	private PriceOverTimePanel priceOverTimePanel;
	private PriceOverTime priceOverTime;
	
	private VolumeOverTimePanel volumeOverTimePanel;
	private VolumeOverTime volumeOverTime;
	
	private TopVolumePanel topVolumePanel;
	private TopVolume topVolume;
	
	private CurrencyExchangePanel currencyExchangePanel;
	private CurrencyExchange currencyExchange;
	
	private String currentDate;

	public App(SubscriberManager subscriberManager) {

		this.subscriberManager = subscriberManager;
		companyList = new ArrayList<>();
		logger.info("Starting App");

	}
	
	public void registerUserDashboardPanel(UserDashboardPanel userDashboardPanel) {
		
		this.userDashboardPanel = userDashboardPanel;
		logger.info("Setting the UserDashboardPanel");
		
	}
	
	public UserDashboardPanel getUserDashboard() {
		
		return userDashboardPanel;
		
	}
	
	public void registerListServicesPanel(ListServicesPanel listServicesPanel) {
		
		this.listServicesPanel = listServicesPanel;
		
	}
	
	public void registerStockInfoPanel(StockInfoPanel stockInfoPanel) {
		
		this.stockInfoPanel = stockInfoPanel;
		
	}
	
	public void registerPriceOverTimePanel(PriceOverTimePanel priceOverTimePanel) {
		
		this.priceOverTimePanel = priceOverTimePanel;
		
	}
	
	public void registerVolumeOverTimePanel(VolumeOverTimePanel volumeOverTimePanel) {
		
		this.volumeOverTimePanel = volumeOverTimePanel;
		
	}
	
	public void registerTopVolumePanel(TopVolumePanel topVolumePanel) {
		
		this.topVolumePanel = topVolumePanel;
		
	}
	
	public void registerCurrencyExchangePanel(CurrencyExchangePanel currencyExchangePanel) {
		
		this.currencyExchangePanel = currencyExchangePanel;
		
	}

	public Subscriber getSubscriber() {

		return subscriber;

	}

	public void setSubscriber(Subscriber subscriber) {

		this.subscriber = subscriber;

	}

	public JFrame getMainFrame() {

		return mainFrame;

	}

	public void setMainFrame(JFrame mainFrame) {

		this.mainFrame = mainFrame;

	}
	
	public void updateViewTitle(String view) {
		
		if (mainFrame != null) {
			
			((MainFrame)mainFrame).updateViewTitle(view);
			
		}
		
	}

	public void loginRequest(String username, String password) {
		
		logger.info("Received login request from user");
		subscriberManager.loginSubscriber(username, password);

	}
	
	public void loginResponse(Subscriber subscriber) {
		
		logger.info("Received login response from SubscriberManager");
		this.subscriber = subscriber;
		queue.add(subscriber);
		
	}
	
	public void registerRequest(String username, String password) {
		
		logger.info("Received register request from user");
		subscriberManager.registerSubscriber(username, password);

	}
	
	public void registerResponse(Subscriber subscriber) {
		
		logger.info("Received register response from SubscriberManager");
		this.subscriber = subscriber;
		queue.add(subscriber);
		
	}
	
	public void updateRequest() {
		
		logger.info("Received update request from user");
		subscriberManager.updateSubscriber(subscriber);

	}
	
	public void updateResponse(Subscriber subscriber) {
		
		logger.info("Received update response from SubscriberManager");
		this.subscriber = subscriber;
		subscriberManager.listServices();
		
	}
	
	public void logout() {
		
		logger.info("Received logout request from user");
		subscriber = null;
		
	}
	
	public void listServicesRequest() {
		
		logger.info("Received list services request from user");
		subscriberManager.listServices();

	}
	
	public void listServicesResponse(Map<String, String> services) {
		
		logger.info("Received list services response from SubscriberManager");
		listServicesPanel.updateView(services);

	}
	
	public void stockInfoRequest(String companyName, String date) {
		
		logger.info("Received a stock info search request from user");
		subscriberManager.getStockInfo(companyName, date);
		
	}
	
	public void stockInfoResponse(StockInfo stockInfo) {
		
		this.stockInfo = stockInfo;		
		stockInfoPanel.updateView(stockInfo);
		
	}
	
	public void topVolumeRequest(String date) {
		
		logger.info("Received a top volume search request from user");
		subscriberManager.getTopVolume(date);
		
	}
	
	public void topVolumeResponse(TopVolume topVolume) {
		
		this.topVolume = topVolume;		
		topVolumePanel.updateView(topVolume);
		
	}
	
	public void priceOverTimeRequest(String companyName) {
		
		logger.info("Received a price over time search request from user");
		subscriberManager.getPriceOverTime(companyName);
		
	}
	
	public void priceOverTimeResponse(PriceOverTime priceOverTime) {
		
		this.priceOverTime = priceOverTime;
		priceOverTimePanel.updateView(priceOverTime);
		
	}
	
	public void volumeOverTimeRequest(String companyName) {
		
		logger.info("Received a volume over time search request from user");
		subscriberManager.getVolumeOverTime(companyName);
		
	}
	
	public void volumeOverTimeResponse(VolumeOverTime volumeOverTime) {
		
		this.volumeOverTime = volumeOverTime;
		volumeOverTimePanel.updateView(volumeOverTime);
		
	}
	
	public void currencyExchangeRequest() {
		
		logger.info("Received a currency exchange search request from user");
		subscriberManager.getCurrencyExchange();
		
	}
	
	public void currencyExchangeResponse(CurrencyExchange currencyExchange) {
		
		this.currencyExchange = currencyExchange;
		currencyExchangePanel.updateView(currencyExchange);
		
	}
	
	public void currentDateRequest() {
		
		logger.info("Received a current date request from the UserDashBoardPanel");
		subscriberManager.currentDateRequest();
		
	}
	
	public void previousDateRequest() {
		
		subscriberManager.previousDateRequest(currentDate);
		
	}
	
	public void nextDateRequest() {
		
		subscriberManager.nextDateRequest(currentDate);
		
	}
	
	public void updateDateResponse(String currentDate) {
		
		this.currentDate = currentDate;		
		
		if (userDashboardPanel == null) {
			
			logger.info("Waiting for the GUI to be built...");
			
			int i = 0;
			
			while (userDashboardPanel == null) {
				
				if (i % 1000 == 0) {
					
					System.out.print(".");
					
				}
				
				i++;
				
			}
			
			System.out.println();
			
		}
		
		userDashboardPanel.updateView(currentDate);
		
	}

	public void readCompanies() {
		
		logger.info("Reading the list of companies from a file to populate the autocomplete module");
		
		In in = new In(MASTER_COMPANY_LIST);
		
		while (in.hasNextLine()) {

			String line = in.readLine();
			String tokens[] = line.split(",");
			companyList.add(new Company(tokens[1], tokens[0]));

		}

	}

	public List<Company> getCompanyList() {

		return companyList;

	}
	
	public BlockingQueue<Subscriber> getQueue() {
		
		return queue;
		
	}

}