package com.group9.publishsubscribe.PublisherLayer.TopVolumeService;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Utility.Out;
import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.TopVolumeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.TopVolumeResponse;

public class TopVolumeService extends ThreadPoolServer {

	private static final Logger logger = LogManager.getLogger(TopVolumeService.class.getName());
	private static final String MASTER_DIRECTORY_PREPROCESSED_TOPVOLUME = ".." + File.separator + "data";
	private static String serviceManagerIP;
	private static final int serviceManagerPort = 2502;
	private static final int TopVolumeServicePort = 2509;

	private String currentDate;

	public TopVolumeService(int port) {
		super(port);
		logger.info("Starting TopVolumeService");
	}

	private Notification notifySubscribers() {

		return new Notification("TopVolumeService");
	}

	private TopVolumeResponse getData(Message request) {

		TopVolumeRequest topVolumeRequest = (TopVolumeRequest) request;

		String startDate = topVolumeRequest.getServiceParameters().getStartDate();

		logger.info("Current Request: startDate: " + startDate);

		if (Integer.parseInt(startDate) > Integer.parseInt(currentDate)) {

			logger.info(
					"My current date is behind the requested date, and because I cannot see into the future, I cannot give you any information.");
			return null;

		}

		else {

			// For TopVolumeService, only 1 date is required
			In in = new In(MASTER_DIRECTORY_PREPROCESSED_TOPVOLUME + File.separator + startDate + ".txt");
			HashMap<String, Integer> trades = new HashMap<String, Integer>();

			while (in.hasNextLine()) {

				String line = in.readLine();
				String[] tokens = line.split(",");
				trades.put(tokens[0], Integer.parseInt(tokens[2]));
				
			}

			List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(trades.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return (o1.getValue()).compareTo(o2.getValue());
				}
			});
			HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
			for (Map.Entry<String, Integer> aa : list) {
				temp.put(aa.getKey(), aa.getValue());
			}

			int count = 0;

			LinkedHashMap<String, Integer> bestTrades = new LinkedHashMap<String, Integer>();
			LinkedHashMap<String, Integer> worstTrades = new LinkedHashMap<String, Integer>();

			for (String name : temp.keySet()) {
				String value = temp.get(name).toString();
				if (count < 5) {
					worstTrades.put(name, Integer.parseInt(value));
				}
				if (temp.size() - count < 6) {
					bestTrades.put(name, Integer.parseInt(value));
				}
				count++;
			}

			logger.info("Sucessfully found the data, sending a TopVolumeResponse");
			in.close();
			return new TopVolumeResponse(bestTrades, worstTrades);

		}

	}

	private void writeData(Message request) {
		RefreshResponse refreshResponse = (RefreshResponse) request;

		String[] fileNames = refreshResponse.getFileNames();
		String[] fileDatas = refreshResponse.getFileDatas();

		Out out;

		for (int i = 0; i < fileNames.length; i++) {
			File file = new File(MASTER_DIRECTORY_PREPROCESSED_TOPVOLUME + File.separator + fileNames[i]);
			if (!file.exists()) {
				out = new Out(MASTER_DIRECTORY_PREPROCESSED_TOPVOLUME + File.separator + fileNames[i]);
				logger.info("Making pre-processed data file for the TopVolumeService: " + fileNames[i]);
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
				} else {
					logger.info("We traveled back in time!");
				}
				currentDate = newDate;
			} else if (TopVolumeRequest.class.isAssignableFrom(messageClass)) {
				logger.info("The message is a TopVolumeRequest");
				Message response = getData(request);
				response.setSubscriberIP(request.getSubscriberIP());
				response.setSubscriberPort(request.getSubscriberPort());
				logger.info("Sending a TopVolumeResponse to the ServiceManager");
				sendMessage(response, serviceManagerIP, serviceManagerPort);
			} else {
				logger.info("I don't currently recognize this message type");
			}
		}
	}

	public static void main(String[] args) {

		TopVolumeService topVolumeService = new TopVolumeService(TopVolumeServicePort);
		new Thread(topVolumeService).start();
		topVolumeService.listen();

	}

}