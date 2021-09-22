package com.group9.publishsubscribe.PublisherLayer.ServiceManager;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.CurrentDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.NextDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.PreviousDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.StockInfoRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.TopVolumeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.VolumeOverTimeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.CurrencyExchangeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.PriceOverTimeRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ServiceInfo;
import com.group9.publishsubscribe.CommonLayer.Models.Network.UpdateDateResponse;

public class ServiceManager extends ThreadPoolServer {
	
	private static final Logger logger = LogManager.getLogger(ServiceManager.class.getName());
	
	private static final int messageManagerPort = 2501;
	private static final int dataManagerPort = 2504;
	private static final int serviceRegistryPort = 2503;
	
	private String serviceRegistryIP;
	private String dataManagerIP;
	private String messageManagerIP;
	
	private String myIP;
	private static final int myPort = 2502;
	
	LinkedList<ServiceInfo> serviceInfoList;
	
	public ServiceManager(int port) {
		
		super(port);
		this.serviceInfoList = new LinkedList<>();
		logger.info("Starting ServiceManager");
		
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
			
			if (myIP != null) {
				
				request.setServiceManagerIP(myIP);
				
			}
			
			Class<? extends Message> messageClass = request.getClass();

			if (StockInfoRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a StockInfoRequest, forwarding it to the StockInfoService");
				
				for (int i = 0; i < serviceInfoList.size(); i++) {
					
					if (serviceInfoList.get(i).getServiceName().equals("StockInfoService")) {
						
						sendMessage(request, serviceInfoList.get(i).getServiceIP(), serviceInfoList.get(i).getServicePort());
						
					}
					
				}

			}
			
			else if (TopVolumeRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a TopVolumeRequest, forwarding it to the TopVolumeService");
				
				for (int i = 0; i < serviceInfoList.size(); i++) {
					
					if (serviceInfoList.get(i).getServiceName().equals("TopVolumeService")) {
						
						sendMessage(request, serviceInfoList.get(i).getServiceIP(), serviceInfoList.get(i).getServicePort());
						
					}
					
				}
				
			}
			
			else if (VolumeOverTimeRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a VolumeOverTimeRequest, forwarding it to the VolumeOverTimeService");
				
				for (int i = 0; i < serviceInfoList.size(); i++) {
					
					if (serviceInfoList.get(i).getServiceName().equals("VolumeOverTimeService")) {
						
						sendMessage(request, serviceInfoList.get(i).getServiceIP(), serviceInfoList.get(i).getServicePort());
						
					}
					
				}
				
			}
			
			else if (PriceOverTimeRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a PriceOverTimeRequest, forwarding it to the PriceOverTimeService");
				
				for (int i = 0; i < serviceInfoList.size(); i++) {
					
					if (serviceInfoList.get(i).getServiceName().equals("PriceOverTimeService")) {
						
						sendMessage(request, serviceInfoList.get(i).getServiceIP(), serviceInfoList.get(i).getServicePort());
						
					}
					
				}
				
			}
			
			else if (CurrencyExchangeRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a CurrencyExchangeRequest, forwarding it to the PriceOverTimeService");
				
				for (int i = 0; i < serviceInfoList.size(); i++) {
					
					if (serviceInfoList.get(i).getServiceName().equals("CurrencyExchangeService")) {
						
						sendMessage(request, serviceInfoList.get(i).getServiceIP(), serviceInfoList.get(i).getServicePort());
						
					}
					
				}
				
			}
			
			else if (ListServicesRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a ListServicesRequest, forwarding it to the ServiceRegistry");
				sendMessage(request, serviceRegistryIP, serviceRegistryPort);
				
			}
			
			else if (ListServicesResponse.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a ListServicesResponse, forwarding it to the MessageManager");
				sendMessage(request, messageManagerIP, messageManagerPort);
				
			}

			else if (ServiceResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a ServiceResponse, forwarding it to the MessageManager");
				sendMessage(request, messageManagerIP, messageManagerPort);

			}
			
			else if (RefreshRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a RefreshRequest from the ServiceRegistry, forwarding message to the DataManager");	
				serviceRegistryIP = ((RefreshRequest)request).getServiceRegistryIP();
				myIP = ((RefreshRequest)request).getServiceManagerIP();
				dataManagerIP = ((RefreshRequest)request).getDataManagerIP();
				messageManagerIP = ((RefreshRequest)request).getMessageManagerIP();
				sendMessage(request, dataManagerIP, dataManagerPort);
				
				boolean found = false;
				
				for (int i = 0; i < serviceInfoList.size(); i++) {

					if (((RefreshRequest)request).getServiceInfo().equals(serviceInfoList.get(i))) {

						found = true;

					}

				}
				
				if (!found) {
					
					serviceInfoList.add(((RefreshRequest)request).getServiceInfo());
					
				}
				
			}
			
			else if (RefreshResponse.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a RefreshResponse from the DataManager, forwarding message to the appropriate service");
				sendMessage(request, ((RefreshResponse)request).getServiceInfo().getServiceIP(), ((RefreshResponse)request).getServiceInfo().getServicePort());
				
			}
			
			else if (Notification.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a Notification, forwarding it to the MessageManager");
				sendMessage(request, messageManagerIP, messageManagerPort);

			}
			
			else if (CurrentDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a CurrentDateRequest, forwarding message to the ServiceRegistry");
				sendMessage(request, serviceRegistryIP, serviceRegistryPort);

			}
			
			else if (PreviousDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a PreviousDateRequest, forwarding message to the ServiceRegistry");
				sendMessage(request, serviceRegistryIP, serviceRegistryPort);

			}
			
			else if (NextDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a NextDateRequest, forwarding message to the ServiceRegistry");
				sendMessage(request, serviceRegistryIP, serviceRegistryPort);

			}
			
			else if (UpdateDateResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a UpdateDateResponse, forwarding message to the MessageManager");
				sendMessage(request, messageManagerIP, messageManagerPort);

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}
	
	public static void main(String[] args) {

		ServiceManager serviceManager = new ServiceManager(myPort);
		new Thread(serviceManager).start();
		serviceManager.listen();

	}

}