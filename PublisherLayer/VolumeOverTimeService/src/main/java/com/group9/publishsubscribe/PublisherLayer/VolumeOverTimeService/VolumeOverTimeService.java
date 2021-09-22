package com.group9.publishsubscribe.PublisherLayer.VolumeOverTimeService;

import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.VolumeOverTimeResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.VolumeOverTimeRequest;
import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Utility.Out;

import java.io.File;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VolumeOverTimeService extends ThreadPoolServer {

	private static final Logger logger = LogManager.getLogger(VolumeOverTimeService.class.getName());

	private static final String MASTER_DIRECTORY_PREPROCESSED_VOLUMEOVERTIME = ".." + File.separator + "data";

	private static String serviceManagerIP;
	private static final int serviceManagerPort = 2502;
	private static final int volumeOverTimeServicePort = 2506;

	private String currentDate;

	public VolumeOverTimeService(int port) {

		super(port);
		logger.info("Starting VolumeOverTimeService");

	}

	private Notification notifySubscribers() {

		return new Notification("VolumeOverTimeService");

	}

	private VolumeOverTimeResponse getData(Message request) {

		VolumeOverTimeRequest volumeOverTimeRequest = (VolumeOverTimeRequest) request;

		String companyName = volumeOverTimeRequest.getServiceParameters().getCompanyName();
		String companySymbol = null;
		String startDate = null;
		String endDate = null;

		logger.info("Current Request: CompanyName: " + companyName);

		File[] files = new File(MASTER_DIRECTORY_PREPROCESSED_VOLUMEOVERTIME).listFiles();
		Arrays.sort(files);
		logger.info("Number of files: " + files.length);

		int[] days = new int[files.length];
		int[] months = new int[files.length];
		int[] years = new int[files.length];
		String[] volumes = new String[files.length];

		for (int i = 0; i < files.length; i++) {

			String volume = null;

			String date = files[i].getName().substring(0, files[i].getName().length() - 4);

			if (Integer.parseInt(date) > Integer.parseInt(currentDate)) {

				logger.info("Current Date: " + currentDate + ", Data Date: " + date + ", so I am skipping it");
				continue;

			}

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

					volume = tokens[2];

					logger.info("Compiling response... Date: " + date + ", Volume: " + tokens[2]);

				}

			}

			volumes[i] = volume;

			if (i == 0) {

				startDate = date;

			}

			endDate = date;

			in.close();

		}

		logger.info("Sucessfully found the data, sending a VolumeOverTimeResponse");

		VolumeOverTimeResponse response = new VolumeOverTimeResponse();
		response.setCompanyName(companyName);
		response.setCompanySymbol(companySymbol);
		response.setStartDate(startDate);
		response.setEndDate(endDate);
		response.setDays(days);
		response.setMonths(months);
		response.setYears(years);
		response.setVolumes(volumes);
		response.setSubscriberIP(request.getSubscriberIP());
		response.setSubscriberPort(request.getSubscriberPort());

		return response;

	}

	private void writeData(Message request) {

		RefreshResponse refreshResponse = (RefreshResponse) request;

		String[] fileNames = refreshResponse.getFileNames();
		String[] fileDatas = refreshResponse.getFileDatas();

		Out out;

		for (int i = 0; i < fileNames.length; i++) {
			
			File file = new File(MASTER_DIRECTORY_PREPROCESSED_VOLUMEOVERTIME + File.separator + fileNames[i]);
			
			if (!file.exists()) {
				
				out = new Out(MASTER_DIRECTORY_PREPROCESSED_VOLUMEOVERTIME + File.separator + fileNames[i]);
				logger.info("Making pre-processed data file for the VolumeOverTimeService: " + fileNames[i]);
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

			else if (VolumeOverTimeRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a VolumeOverTimeRequest");
				Message response = getData(request);
				logger.info("Sending a VolumeOverTimeResponse to the ServiceManager");
				sendMessage(response, serviceManagerIP, serviceManagerPort);

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}

	public static void main(String[] args) {

		VolumeOverTimeService volumeOverTimeService = new VolumeOverTimeService(volumeOverTimeServicePort);
		new Thread(volumeOverTimeService).start();
		volumeOverTimeService.listen();

	}

}