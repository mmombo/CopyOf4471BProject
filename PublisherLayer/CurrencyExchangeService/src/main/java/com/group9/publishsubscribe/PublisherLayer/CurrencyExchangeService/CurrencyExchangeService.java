package com.group9.publishsubscribe.PublisherLayer.CurrencyExchangeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.group9.publishsubscribe.CommonLayer.Models.Data.CurrencyExchange;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.CurrencyExchangeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.CurrencyExchangeResponse;
import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Serialization.SerializerJackson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CurrencyExchangeService extends ThreadPoolServer {

	private static final Logger logger = LogManager.getLogger(CurrencyExchangeService.class.getName());
	private static String serviceManagerIP;
	private static final int serviceManagerPort = 2502;
	// not sure what this port number should be
	private static final int CurrencyExchangeServicePort = 2511;
	// holds the json string from the latest exchange rates API call
	private static String exchangeRatesCache = null;
	// holds the time the last ex rates api call was made
	private static long lastUpdateTime;

	public CurrencyExchangeService(int port) {

		super(port);
		logger.info("Starting CurrencyExchangeService");

	}

	private Notification notifySubscribers() {

		return new Notification("CurrencyExchangeService");
	}

	private static void UpdateCache() {

		// update if the API has never been cached
		if (exchangeRatesCache == null || (System.nanoTime() / 1000000000 - lastUpdateTime) > 1800) {

			try {
				
				String baseURL = "http://api.exchangeratesapi.io/v1/";
				String endpoint = "latest";
				String key = "?access_key=29e7bd9bd5ddd8ebe93c65fd88717421";
				String defaultSymbols = "&symbols=CAD,USD,JPY,GBP,SEK,AUD,CNY,RUB";
				
				HttpResponse<String> response = Unirest.get(baseURL + endpoint + key + defaultSymbols).asString();
				exchangeRatesCache = response.getBody().toLowerCase();
				lastUpdateTime = System.nanoTime() / 1000000000;

			} catch (UnirestException e) {
				e.printStackTrace();
			}
		}

		// else return without updating
		else
			return;

	}

	private CurrencyExchangeResponse getData() {

		// checks and updates the cached data if needed
		UpdateCache();
		String currentExchange = exchangeRatesCache;

		SerializerJackson serializer = new SerializerJackson();
		CurrencyExchange exchange = serializer.deserialize(currentExchange, CurrencyExchange.class);

		CurrencyExchangeResponse currencyExchangeResponse = new CurrencyExchangeResponse();

		currencyExchangeResponse.setRates(exchange.getRates());
		currencyExchangeResponse.setBase(exchange.getBase());
		currencyExchangeResponse.setDate(exchange.getDate());

		return currencyExchangeResponse;

	}

	private void listen() {
		Message request = null;
		while (true) {
			try {
				request = queue.take();
				logger.info("Received a Message");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			serviceManagerIP = request.getServiceManagerIP();
			Class<? extends Message> messageClass = request.getClass();
			if (RefreshResponse.class.isAssignableFrom(messageClass)) {
				logger.info(
						"The message is a RefreshResponse, it probably shouldnt have been sent to me because I use an API for data");
				// writeData(request);
				// Message response = notifySubscribers();
				// logger.info("Publishing new data: sending a Notification to the
				// ServiceManager");
				// sendMessage(response, serviceManagerIP, serviceManagerPort);
			} else if (CurrencyExchangeRequest.class.isAssignableFrom(messageClass)) {
				logger.info("The message is a CurrencyExchangeRequest");
				Message response = getData();
				response.setSubscriberIP(request.getSubscriberIP());
				response.setSubscriberPort(request.getSubscriberPort());
				logger.info("Sending a CurrencyExchangeResponse to the ServiceManager");
				sendMessage(response, serviceManagerIP, serviceManagerPort);
			} else {
				logger.info("I don't currently recognize this message type");
			}
		}
	}

	public static void main(String[] args) {

		CurrencyExchangeService stockInfoService = new CurrencyExchangeService(CurrencyExchangeServicePort);
		new Thread(stockInfoService).start();
		stockInfoService.listen();

	}

}