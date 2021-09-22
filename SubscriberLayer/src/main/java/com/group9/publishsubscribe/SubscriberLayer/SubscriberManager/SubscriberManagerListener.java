package com.group9.publishsubscribe.SubscriberLayer.SubscriberManager;

import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.PriceOverTime;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.StockInfo;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.TopVolume;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.VolumeOverTime;
import com.group9.publishsubscribe.CommonLayer.Models.Data.CurrencyExchange;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.UpdateDateResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.LoginResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.RegisterResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.UpdateResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.CurrencyExchangeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.PriceOverTimeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.StockInfoResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.TopVolumeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.VolumeOverTimeResponse;

public class SubscriberManagerListener implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(SubscriberManager.class.getName());
	
	private final App app;
	
	private final BlockingQueue<Message> queue;
	
	public SubscriberManagerListener(App app, BlockingQueue<Message> queue) {
		
		this.app = app;
		this.queue = queue;
		
	}
	
	public void run() {
		
		Message message = null;

		while (true) {

			try {

				message = queue.take();
				logger.info("Received a Message");

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			Class<? extends Message> messageClass = message.getClass();

			if (LoginResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a LoginResponse");

				if (((LoginResponse)message).isAuthenticated()) {

					logger.info("Login was accepted");
					app.loginResponse(((LoginResponse)message).getSubscriber());

				}

				else {

					logger.info("Login was denied");
					app.loginResponse(new Subscriber(null, null, null, null, -1));

				}

			}

			else if (RegisterResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a RegisterResponse");

				if (((RegisterResponse)message).isAuthenticated()) {

					logger.info("Register was accepted");
					app.registerResponse(((RegisterResponse)message).getSubscriber());

				}

				else {

					logger.info("Register was denied");
					app.registerResponse(new Subscriber(null, null, null, null, -1));

				}

			}
			
			else if (UpdateResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is an UpdateResponse");
				app.updateResponse(((UpdateResponse)message).getSubscriber());

			}
			
			else if (ListServicesResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is an ListServicesResponse");
				app.listServicesResponse(((ListServicesResponse)message).getServices());

			}

			else if (StockInfoResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a StockInfoResponse");
				StockInfoResponse stockInfoResponse = (StockInfoResponse)message;
				StockInfo stockInfo = new StockInfo();				
				stockInfo.setCompanyName(stockInfoResponse.getCompanyName());
				stockInfo.setCompanySymbol(stockInfoResponse.getCompanySymbol());
				stockInfo.setDate(stockInfoResponse.getDate());
				stockInfo.setTotalVolume(stockInfoResponse.getTotalVolume());
				stockInfo.setHigh(stockInfoResponse.getHigh());
				stockInfo.setLow(stockInfoResponse.getLow());
				stockInfo.setOpeningPrice(stockInfoResponse.getOpening());
				stockInfo.setClosingPrice(stockInfoResponse.getClosing());
				app.stockInfoResponse(stockInfo);

			}
			
			else if (PriceOverTimeResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a PriceOverTimeResponse");
				PriceOverTimeResponse priceOverTimeResponse = (PriceOverTimeResponse)message;
				PriceOverTime priceOverTime = new PriceOverTime();
				priceOverTime.setCompanyName(priceOverTimeResponse.getCompanyName());
				priceOverTime.setCompanySymbol(priceOverTimeResponse.getCompanySymbol());
				priceOverTime.setStartDate(priceOverTimeResponse.getStartDate());
				priceOverTime.setEndDate(priceOverTimeResponse.getEndDate());
				priceOverTime.setDays(priceOverTimeResponse.getDays());
				priceOverTime.setMonths(priceOverTimeResponse.getMonths());
				priceOverTime.setYears(priceOverTimeResponse.getYears());
				priceOverTime.setPrices(priceOverTimeResponse.getPrices());
				app.priceOverTimeResponse(priceOverTime);

			}
			
			else if (VolumeOverTimeResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a VolumeOverTimeResponse");
				VolumeOverTimeResponse volumeOverTimeResponse = (VolumeOverTimeResponse)message;
				VolumeOverTime volumeOverTime = new VolumeOverTime();
				volumeOverTime.setCompanyName(volumeOverTimeResponse.getCompanyName());
				volumeOverTime.setCompanySymbol(volumeOverTimeResponse.getCompanySymbol());
				volumeOverTime.setStartDate(volumeOverTimeResponse.getStartDate());
				volumeOverTime.setEndDate(volumeOverTimeResponse.getEndDate());
				volumeOverTime.setDays(volumeOverTimeResponse.getDays());
				volumeOverTime.setMonths(volumeOverTimeResponse.getMonths());
				volumeOverTime.setYears(volumeOverTimeResponse.getYears());
				volumeOverTime.setVolumes(volumeOverTimeResponse.getVolumes());
				app.volumeOverTimeResponse(volumeOverTime);

			}
			
			else if (TopVolumeResponse.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a TopVolumeResponse");
				TopVolumeResponse topVolumeResponse = (TopVolumeResponse)message;
				TopVolume topVolume = new TopVolume(topVolumeResponse.getBestTrades(),topVolumeResponse.getWorstTrades());
				app.topVolumeResponse(topVolume);

			}
			
			else if (CurrencyExchangeResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a TopVolumeResponse");
				CurrencyExchangeResponse currencyExchangeResponse = (CurrencyExchangeResponse)message;
				CurrencyExchange currencyExchange = new CurrencyExchange();	
				currencyExchange.setRates(currencyExchangeResponse.getRates());
				currencyExchange.setBase(currencyExchangeResponse.getBase());
				currencyExchange.setDate(currencyExchangeResponse.getDate());
				app.currencyExchangeResponse(currencyExchange);

			}
			
			else if (UpdateDateResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is an UpdateDateResponse");			
				app.updateDateResponse(((UpdateDateResponse)message).getCurrentDate());

			}
			
			else if (Notification.class.isAssignableFrom(messageClass)) {

				String serviceType = ((Notification)message).getServiceType();
				logger.info("The message is a Notification from the " + serviceType);
				app.updateViewTitle(serviceType);

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}

}