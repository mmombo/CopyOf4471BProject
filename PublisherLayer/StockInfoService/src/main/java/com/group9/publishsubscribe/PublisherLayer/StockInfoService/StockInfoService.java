package com.group9.publishsubscribe.PublisherLayer.StockInfoService;

import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.StockInfoResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.StockInfoRequest;
import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Utility.Out;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StockInfoService extends ThreadPoolServer {

	private static final Logger logger = LogManager.getLogger(StockInfoService.class.getName());

	private static final String MASTER_DIRECTORY_PREPROCESSED_STOCKINFO = ".." + File.separator + "data";

	private static String serviceManagerIP;
	private static final int serviceManagerPort = 2502;
	private static final int stockInfoServicePort = 2505;

	private String currentDate;

	public StockInfoService(int port) {

		super(port);
		logger.info("Starting StockInfoService");

	}

	private Notification notifySubscribers() {

		return new Notification("StockInfoService");

	}

	private StockInfoResponse getData(Message request) {

		StockInfoRequest stockInfoRequest = (StockInfoRequest) request;

		String companyName = stockInfoRequest.getServiceParameters().getCompanyName();
		String startDate = stockInfoRequest.getServiceParameters().getStartDate();
		String endDate = stockInfoRequest.getServiceParameters().getEndDate();

		logger.info("Current Request: CompanyName: \"" + companyName + "\", startDate: " + startDate + ", endDate: "
				+ endDate + "\", currentDate: " + currentDate);

		if (Integer.parseInt(startDate) > Integer.parseInt(currentDate)) {

			logger.info(
					"My current date is behind the requested date, and because I cannot see into the future, I cannot give you any information.");
			return null;

		}

		else {

			// For StockInfo, only 1 date is required
			In in = new In(MASTER_DIRECTORY_PREPROCESSED_STOCKINFO + File.separator + startDate + ".txt");

			while (in.hasNextLine()) {

				String line = in.readLine();
				String[] tokens = line.split(",");

				System.out.println("Looking at: " + tokens[0]);

				if (tokens[0].equals(companyName)) {

					String companySymbol = tokens[1];
					String totalVolume = tokens[2];
					String high = tokens[3];
					String low = tokens[4];
					String opening = tokens[5];
					String closing = tokens[6];

					in.close();

					String year = startDate.substring(0, 4);
					int monthNum = Integer.parseInt(startDate.substring(4, 6));
					int dayNum = Integer.parseInt(startDate.substring(6, startDate.length()));

					String month;
					String date;

					switch (monthNum) {

					case 1:

						month = "January";
						break;

					case 2:

						month = "February";
						break;

					case 3:

						month = "March";
						break;

					case 4:

						month = "April";
						break;

					case 5:

						month = "May";
						break;

					case 6:

						month = "June";
						break;

					case 7:

						month = "July";
						break;

					case 8:

						month = "August";
						break;

					case 9:

						month = "September";
						break;

					case 10:

						month = "October";
						break;

					case 11:

						month = "November";
						break;

					case 12:

						month = "December";
						break;

					default:

						month = null;
						break;

					}

					date = month + " " + dayNum + ", " + year;

					logger.info("Sucessfully found the data, sending a StockInfoResponse");
					StockInfoResponse response = new StockInfoResponse();
					response.setCompanyName(companyName);
					response.setCompanySymbol(companySymbol);
					response.setDate(date);
					response.setTotalVolume(totalVolume);
					response.setHigh(high);
					response.setLow(low);
					response.setOpening(opening);
					response.setClosing(closing);
					response.setSubscriberIP(request.getSubscriberIP());
					response.setSubscriberPort(request.getSubscriberPort());

					in.close();

					return response;

				}

			}

			in.close();

		}

		logger.info("Something went wrong and I had to return null, maybe I couldn't find the file?");
		return null;

	}

	private void writeData(Message request) {

		RefreshResponse refreshResponse = (RefreshResponse) request;

		String[] fileNames = refreshResponse.getFileNames();
		String[] fileDatas = refreshResponse.getFileDatas();

		Out out;

		for (int i = 0; i < fileNames.length; i++) {
			
			File file = new File(MASTER_DIRECTORY_PREPROCESSED_STOCKINFO + File.separator + fileNames[i]);
			
			if (!file.exists()) {
				
				out = new Out(MASTER_DIRECTORY_PREPROCESSED_STOCKINFO + File.separator + fileNames[i]);
				logger.info("Making pre-processed data file for the StockInfoService: " + fileNames[i]);
				out.println(fileDatas[i]);
				out.close();
				
			}

		}

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

			if (serviceManagerIP == null) {
				
				serviceManagerIP = request.getServiceManagerIP();
				
			}

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

			else if (StockInfoRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a StockInfoRequest");
				Message response = getData(request);
				logger.info("Sending a StockInfoResponse to the ServiceManager");
				sendMessage(response, serviceManagerIP, serviceManagerPort);

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}

	public static void main(String[] args) {

		StockInfoService stockInfoService = new StockInfoService(stockInfoServicePort);
		new Thread(stockInfoService).start();
		stockInfoService.listen();

	}

}