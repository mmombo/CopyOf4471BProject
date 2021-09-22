package com.group9.publishsubscribe.PublisherLayer.PriceOverTimeService;

import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Utility.Out;
import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.PriceOverTimeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.PriceOverTimeRequest;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PriceOverTimeService extends ThreadPoolServer {

	// Logging tool
	private static final Logger logger = LogManager.getLogger(PriceOverTimeService.class.getName());
	// Location of locally stored pre-processed files
	private static final String MASTER_DIRECTORY_PREPROCESSED_PRICEOVERTIME = ".." + File.separator + "data";
	// IP address of service manager
	private static String serviceManagerIP;
	// Port of service manager (temp)
	private static final int serviceManagerPort = 2502;
	// Port of price over time service (temp)
	private static final int priceOverTimeServicePort = 2507;

	private String currentDate;

	/*
	 * Create an instance of PriceOverTimeClass
	 * 
	 * @param port is the port of the service manager
	 */
	public PriceOverTimeService(int port) {
		super(port);
		logger.info("Starting PriceOverTimeService");
	}

	/*
	 * Generates a notification message containing service name
	 * 
	 * @return notification message containing service name
	 */
	private Notification notifySubscribers() {
		return new Notification("PriceOverTimeService");
	}

	/*
	 * Takes a request, checks local files, and produces a PriceOverTimeResponse for
	 * the user
	 * 
	 * @param message request
	 * 
	 * @return PriceOverTimeResponse
	 */
	private PriceOverTimeResponse getData(Message request) {

		PriceOverTimeRequest priceOverTimeRequest = (PriceOverTimeRequest) request;

		// Get user request parameters
		String companyName = priceOverTimeRequest.getServiceParameters().getCompanyName();
		String companySymbol = null;
		String startDate = null;
		String endDate = null;

		logger.info("Current Request: CompanyName: " + companyName);

		// Create an array of the preprocessed files in the local PriceOverTime
		// directory
		File[] files = new File(MASTER_DIRECTORY_PREPROCESSED_PRICEOVERTIME).listFiles();
		Arrays.sort(files);
		logger.info("Number of files: " + files.length);

		// Initialize arrays to store the dates and prices for the company requested
		int[] days = new int[files.length];
		int[] months = new int[files.length];
		int[] years = new int[files.length];
		String[] prices = new String[files.length];

		// For each preprocessed file, get the date from the file name, find the line
		// containing the company name and
		for (int i = 0; i < files.length; i++) {

			String price = null;

			// Get date from the current file name
			String date = files[i].getName().substring(0, files[i].getName().length() - 4);

			if (Integer.parseInt(date) > Integer.parseInt(currentDate)) {

				continue;

			}

			// Extract year, month and day values from date	
			days[i] = Integer.parseInt(date.substring(6, date.length()));
			months[i] = Integer.parseInt(date.substring(4, 6));
			years[i] = Integer.parseInt(date.substring(0, 4));

			In in = new In(files[i]);

			while (in.hasNextLine()) {

				String line = in.readLine();
				String[] tokens = line.split(",");

				if (tokens[0].equals(companyName)) {

					if (companySymbol == null) {
						companySymbol = tokens[1];
					}

					// Get closing price
					price = tokens[6];

					logger.info("Compiling response... Date: " + date + ", Price: " + price);

				}

			}

			// Store the price in the prices array
			prices[i] = price;

			// Make startDate the date of the first preprocessed file
			if (i == 0) {
				startDate = date;
			}

			// Make endDate the date of the last preprocessed file
			if (i == files.length - 1) {
				endDate = date;
			}

			in.close();

		}

		logger.info("Sucessfully found the data, sending a PriceOverTimeResponse");

		// Create the PriceOverTime response to be returned to the user
		PriceOverTimeResponse response = new PriceOverTimeResponse();
		response.setCompanyName(companyName);
		response.setCompanySymbol(companySymbol);
		response.setStartDate(startDate);
		response.setEndDate(endDate);
		response.setDays(days);
		response.setMonths(months);
		response.setYears(years);
		response.setPrices(prices);
		response.setSubscriberIP(request.getSubscriberIP());
		response.setSubscriberPort(request.getSubscriberPort());

		return response;

	}

	/*
	 * Takes a request and writes the files to be stored within the local system
	 * 
	 * @param message request
	 */
	private void writeData(Message request) {

		RefreshResponse refreshResponse = (RefreshResponse) request;

		String[] fileNames = refreshResponse.getFileNames();
		String[] fileDatas = refreshResponse.getFileDatas();

		Out out;

		for (int i = 0; i < fileNames.length; i++) {
			
			File file = new File(MASTER_DIRECTORY_PREPROCESSED_PRICEOVERTIME + File.separator + fileNames[i]);
			
			if (!file.exists()) {
				
				out = new Out(MASTER_DIRECTORY_PREPROCESSED_PRICEOVERTIME + File.separator + fileNames[i]);
				logger.info("Making pre-processed data file for the PriceOverTimeService: " + fileNames[i]);
				out.println(fileDatas[i]);
				out.close();
				
			}

		}

	}

	/*
	 * Infinitely listens to the BlockingQueue<Message> and handles any messages it
	 * receives
	 */
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

				logger.info("The message is a RefreshResponse");
				String newDate = ((RefreshResponse) request).getCurrentDate();

				if (currentDate == null || Integer.parseInt(currentDate) < Integer.parseInt(newDate)) {

					logger.info("It contains new data for me!");
					writeData(request);
					Message response = notifySubscribers();
					logger.info("Publishing new data: sending a Notification to the ServiceManager");
					sendMessage(response, serviceManagerIP, serviceManagerPort);

				}

				else {

					logger.info("We traveled back in time!");

				}

				currentDate = newDate;

			}

			else if (PriceOverTimeRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a PriceOverTimeRequest");
				Message response = getData(request);
				logger.info("Sending a PriceOverTimeRequest to the ServiceManager");
				sendMessage(response, serviceManagerIP, serviceManagerPort);

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}

	/*
	 * Calls the start method on a new PriceOverTimeService object and then calls
	 * the method listen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		PriceOverTimeService priceOverTimeService = new PriceOverTimeService(priceOverTimeServicePort);
		new Thread(priceOverTimeService).start();
		priceOverTimeService.listen();

	}

}