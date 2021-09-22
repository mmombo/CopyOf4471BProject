package com.group9.publishsubscribe.PublisherLayer.ServiceRegistry;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.CurrentDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.LookupRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.LookupResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.NextDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.PreviousDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ServiceInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Network.UpdateDateResponse;

public class ServiceRegistry extends ThreadPoolServer {

	private static final Logger logger = LogManager.getLogger(ServiceRegistry.class.getName());

	private static final String MASTER_SERVICE_LIST_ONLINE = ".." + File.separator + "data" + File.separator
			+ "ServiceListOnline.txt";
	private static final String MASTER_SERVICE_LIST_OFFLINE = ".." + File.separator + "data" + File.separator
			+ "ServiceListOffline.txt";

	private String config;

	private static String serviceManagerIP;
	private static int serviceManagerPort;
	private static String dataManagerIP;
	private static int dataManagerPort;
	private static String messageManagerIP;
	private static int messageManagerPort;
	private static String myIP;
	private static int myPort;

	private String currentDate;
	private LinkedList<ServiceInfo> serviceInfoList;

	public ServiceRegistry() {

		super();
		this.serviceInfoList = new LinkedList<>();
		logger.info("Starting ServiceRegistry");

	}

	private void readFromConfigFile(String configFile) {

		logger.info("Getting the services from the master list");

		In in = new In(configFile);

		currentDate = in.readLine();

		String registry = in.readLine();
		String[] registryTokens = registry.split(",");
		myIP = registryTokens[2];
		myPort = Integer.parseInt(registryTokens[3]);

		String serviceManager = in.readLine();
		String[] serviceManagerTokens = serviceManager.split(",");
		serviceManagerIP = serviceManagerTokens[2];
		serviceManagerPort = Integer.parseInt(serviceManagerTokens[3]);

		String dataManager = in.readLine();
		String[] dataManagerTokens = dataManager.split(",");
		dataManagerIP = dataManagerTokens[2];
		dataManagerPort = Integer.parseInt(dataManagerTokens[3]);

		String messageManager = in.readLine();
		String[] messageManagerTokens = messageManager.split(",");
		messageManagerIP = messageManagerTokens[2];
		messageManagerPort = Integer.parseInt(messageManagerTokens[3]);

		while (in.hasNextLine()) {

			String line = in.readLine();
			String lines[] = line.split(",");

			ServiceInfo newService = new ServiceInfo(lines[0], lines[1], lines[2], Integer.parseInt(lines[3]));
			
			boolean found = false;

			for (int i = 0; i < serviceInfoList.size(); i++) {

				if (newService.equals(serviceInfoList.get(i))) {

					found = true;

				}

			}
			
			if (!found) {
				
				logger.info("Added service to internal memory: " + lines[0]);
				serviceInfoList.add(newService);
				
			}

		}

		in.close();

	}

	private void refreshAll(String currentDate) {

		logger.info("Initiating a refresh on all services in the master list");

		RefreshRequest refresh;

		for (int i = 0; i < serviceInfoList.size(); i++) {

			refresh = new RefreshRequest(serviceInfoList.get(i), currentDate);
			refresh.setServiceRegistryIP(myIP);
			refresh.setServiceManagerIP(serviceManagerIP);
			refresh.setDataManagerIP(dataManagerIP);
			refresh.setMessageManagerIP(messageManagerIP);

			logger.info("Sending a RefreshRequest to the ServiceManager");
			logger.info("ServiceName: " + refresh.getServiceInfo().getServiceName() + ", ServiceIPAddress: "
					+ refresh.getServiceInfo().getServiceIP() + ", ServicePort: "
					+ refresh.getServiceInfo().getServicePort() + ", CurrentDate: " + refresh.getCurrentDate());

			sendMessage(refresh, serviceManagerIP, serviceManagerPort);

		}

	}

	private void getPreviousDate(String currentDate) {

		switch (currentDate) {

		case "20110113":

			this.currentDate = "20110113";
			break;

		case "20110124":

			this.currentDate = "20110113";
			break;

		case "20110203":

			this.currentDate = "20110124";
			break;

		case "20110215":

			this.currentDate = "20110203";
			break;

		case "20110310":

			this.currentDate = "20110215";
			break;

		case "20110316":

			this.currentDate = "20110310";
			break;

		case "20110413":

			this.currentDate = "20110316";
			break;

		case "20110426":

			this.currentDate = "20110413";
			break;

		case "20110503":

			this.currentDate = "20110426";
			break;

		case "20110518":

			this.currentDate = "20110503";
			break;

		case "20110602":

			this.currentDate = "20110518";
			break;

		case "20110616":

			this.currentDate = "20110602";
			break;

		case "20110706":

			this.currentDate = "20110616";
			break;

		case "20110721":

			this.currentDate = "20110706";
			break;

		case "20110818":

			this.currentDate = "20110721";
			break;

		case "20110829":

			this.currentDate = "20110818";
			break;

		case "20110919":

			this.currentDate = "20110829";
			break;

		case "20110930":

			this.currentDate = "20110919";
			break;

		case "20111020":

			this.currentDate = "20110930";
			break;

		case "20111031":

			this.currentDate = "20111020";
			break;

		case "20111121":

			this.currentDate = "20111031";
			break;

		case "20111125":

			this.currentDate = "20111121";
			break;

		case "20111129":

			this.currentDate = "20111125";
			break;

		case "20111206":

			this.currentDate = "20111129";
			break;

		case "20111207":

			this.currentDate = "20111206";
			break;

		default:

		}

	}

	private void getNextDate(String currentDate) {

		switch (currentDate) {

		case "20110113":

			this.currentDate = "20110124";
			break;

		case "20110124":

			this.currentDate = "20110203";
			break;

		case "20110203":

			this.currentDate = "20110215";
			break;

		case "20110215":

			this.currentDate = "20110310";
			break;

		case "20110310":

			this.currentDate = "20110316";
			break;

		case "20110316":

			this.currentDate = "20110413";
			break;

		case "20110413":

			this.currentDate = "20110426";
			break;

		case "20110426":

			this.currentDate = "20110503";
			break;

		case "20110503":

			this.currentDate = "20110518";
			break;

		case "20110518":

			this.currentDate = "20110602";
			break;

		case "20110602":

			this.currentDate = "20110616";
			break;

		case "20110616":

			this.currentDate = "20110706";
			break;

		case "20110706":

			this.currentDate = "20110721";
			break;

		case "20110721":

			this.currentDate = "20110818";
			break;

		case "20110818":

			this.currentDate = "20110829";
			break;

		case "20110829":

			this.currentDate = "20110919";
			break;

		case "20110919":

			this.currentDate = "20110930";
			break;

		case "20110930":

			this.currentDate = "20111020";
			break;

		case "20111020":

			this.currentDate = "20111031";
			break;

		case "20111031":

			this.currentDate = "20111121";
			break;

		case "20111121":

			this.currentDate = "20111125";
			break;

		case "20111125":

			this.currentDate = "20111129";
			break;

		case "20111129":

			this.currentDate = "20111206";
			break;

		case "20111206":

			this.currentDate = "20111207";
			break;

		case "20111207":

			this.currentDate = "20111207";
			break;

		default:

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

			Class<? extends Message> messageClass = request.getClass();

			if (LookupRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a LookupRequest");
				readFromConfigFile(config);

				for (int i = 0; i < serviceInfoList.size(); i++) {

					if (((LookupRequest) request).getServiceName().equals(serviceInfoList.get(i))) {

						logger.info("Found the service in the master list, sending the IP in a LookupResponse");
						sendMessage(new LookupResponse(serviceInfoList.get(i)), serviceManagerIP, serviceManagerPort);

					}

				}

				logger.info("Could not find the service, sending a null LookupResponse");
				sendMessage(new LookupResponse(null), serviceManagerIP, serviceManagerPort);

			}

			if (ListServicesRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a ListServicesRequest");
				readFromConfigFile(config);

				Map<String, String> services = new HashMap<>();

				for (int i = 0; i < serviceInfoList.size(); i++) {

					services.put(serviceInfoList.get(i).getServiceName(),
							serviceInfoList.get(i).getServiceDescription());

				}

				sendMessage(new ListServicesResponse(services, request.getSubscriberIP(), request.getSubscriberPort()),
						serviceManagerIP, serviceManagerPort);

			}

			else if (CurrentDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a CurrentDateRequest, forwarding message to the ServiceManager");
				UpdateDateResponse response = new UpdateDateResponse(currentDate);
				response.setSubscriberIP(request.getSubscriberIP());
				response.setSubscriberPort(request.getSubscriberPort());
				sendMessage(response, serviceManagerIP, serviceManagerPort);

			}

			else if (PreviousDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a PreviousDateRequest, forwarding message to the ServiceManager");
				getPreviousDate(((PreviousDateRequest) request).getCurrentDate());
				UpdateDateResponse response = new UpdateDateResponse(currentDate);
				response.setSubscriberIP(request.getSubscriberIP());
				response.setSubscriberPort(request.getSubscriberPort());
				sendMessage(response, serviceManagerIP, serviceManagerPort);
				refreshAll(currentDate);

			}

			else if (NextDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a NextDateRequest, forwarding message to the ServiceManager");
				getNextDate(((NextDateRequest) request).getCurrentDate());
				UpdateDateResponse response = new UpdateDateResponse(currentDate);
				response.setSubscriberIP(request.getSubscriberIP());
				response.setSubscriberPort(request.getSubscriberPort());
				sendMessage(response, serviceManagerIP, serviceManagerPort);
				refreshAll(currentDate);

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}

	public static void main(String[] args) {

		ServiceRegistry serviceRegistry = new ServiceRegistry();

		if (args.length == 0) {

			serviceRegistry.config = MASTER_SERVICE_LIST_ONLINE;

		}

		else if (args[0].equals("online")) {

			serviceRegistry.config = MASTER_SERVICE_LIST_ONLINE;

		}

		else if (args[0].equals("offline")) {

			serviceRegistry.config = MASTER_SERVICE_LIST_OFFLINE;

		}

		else {

			serviceRegistry.config = MASTER_SERVICE_LIST_ONLINE;

		}

		serviceRegistry.readFromConfigFile(serviceRegistry.config);
		serviceRegistry.setPort(serviceRegistry.myPort);
		new Thread(serviceRegistry).start();
		serviceRegistry.refreshAll(serviceRegistry.currentDate);
		serviceRegistry.listen();

	}

}