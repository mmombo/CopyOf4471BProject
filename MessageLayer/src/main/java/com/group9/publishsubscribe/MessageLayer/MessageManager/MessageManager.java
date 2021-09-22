package com.group9.publishsubscribe.MessageLayer.MessageManager;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.CommonLayer.Models.Network.CurrentDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ListServicesResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.NextDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Notification;
import com.group9.publishsubscribe.CommonLayer.Models.Network.PreviousDateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.UpdateDateResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.ServiceRequests.ServiceRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.AuthenticatorRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.AuthenticatorResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.ServiceResponses.ServiceResponse;
import com.group9.publishsubscribe.CommonLayer.Serialization.ISerializer;
import com.group9.publishsubscribe.CommonLayer.Serialization.SerializerJackson;
import com.group9.publishsubscribe.MessageLayer.Authenticator.AuthenticatorJSON;
import com.group9.publishsubscribe.MessageLayer.Authenticator.IAuthenticator;

public class MessageManager extends ThreadPoolServer {
	
	private static final Logger logger = LogManager.getLogger(MessageManager.class.getName());
	
	private final IAuthenticator authenticator;
	private final ISerializer serializer;
	
	private static String serviceManagerIP;
	private static final int serviceManagerPort = 2502;
	private static final int messageManagerPort = 2501;
	
	public MessageManager(int port) {
		
		super(port);
		serializer = new SerializerJackson();
		authenticator = new AuthenticatorJSON(serializer);
		logger.info("Starting MessageManager");
		
	}
	
	private void listen() {

		Message message = null;

		while (true) {

			try {

				message = queue.take();
				logger.info("Received a Message");

			} catch (InterruptedException e) {

				e.printStackTrace();

			}
			
			if (serviceManagerIP == null) {
				
				serviceManagerIP = message.getServiceManagerIP();
				
			}

			Class<? extends Message> messageClass = message.getClass();

			if (AuthenticatorRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a AuthenticatorRequest, forwarding message to the Authenticator");
				AuthenticatorResponse response = authenticator.authenticate((AuthenticatorRequest)message);
				logger.info("Sending an AuthenticatorResponse to the SubscriberManager");
				sendMessage(response, response.getSubscriberIP(), response.getSubscriberPort());

			}
			
			else if (ListServicesRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a ListServicesRequest, forwarding it to the ServiceManager");
				sendMessage(message, serviceManagerIP, serviceManagerPort);

			}
			
			else if (ListServicesResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a ListServicesResponse, forwarding message to the SubscriberManager");
				sendMessage(message, message.getSubscriberIP(), message.getSubscriberPort());

			}

			else if (ServiceRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a ServiceRequest, forwarding it to the ServiceManager");
				sendMessage(message, serviceManagerIP, serviceManagerPort);

			}
			
			else if (ServiceResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a ServiceResponse, forwarding message to the SubscriberManager");
				sendMessage(message, message.getSubscriberIP(), message.getSubscriberPort());

			}
			
			else if (Notification.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a Notification, checking for interested subscribers...");
				
				Subscriber[] subscribers = authenticator.getSubscribers();
				
				if (subscribers != null) {
					
					for (int i = 0; i < subscribers.length; i++) {
						
						Subscriber subscriber = subscribers[i];
						LinkedList<String> subscriptionList = subscriber.getSubscriptionList();
						
						if (subscriptionList != null) {
							
							for (int j = 0; j < subscriptionList.size(); j++) {
								
								if (subscriptionList.get(j).equals(((Notification)message).getServiceType())) {
									
									sendMessage(message, subscriber.getSubscriberIP(), subscriber.getSubscriberPort());
									
								}
								
							}
							
						}
					
					}
					
				}
			
			}
			
			else if (CurrentDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a CurrentDateRequest, forwarding message to the ServiceManager");
				sendMessage(message, serviceManagerIP, serviceManagerPort);

			}
			
			else if (PreviousDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a PreviousDateRequest, forwarding message to the ServiceManager");
				sendMessage(message, serviceManagerIP, serviceManagerPort);

			}
			
			else if (NextDateRequest.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a NextDateRequest, forwarding message to the ServiceManager");
				sendMessage(message, serviceManagerIP, serviceManagerPort);

			}
			
			else if (UpdateDateResponse.class.isAssignableFrom(messageClass)) {

				logger.info("The message is a UpdateDateResponse, forwarding message to the SubscriberManager");
				
				Subscriber[] subscribers = authenticator.getSubscribers();
				
				if (subscribers != null) {
					
					for (int i = 0; i < subscribers.length; i++) {
						
						Subscriber subscriber = subscribers[i];
						sendMessage(message, subscriber.getSubscriberIP(), subscriber.getSubscriberPort());
						
					}
					
				}

			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}
	
	public static void main(String[] args) {

		MessageManager messageManager = new MessageManager(messageManagerPort);
		new Thread(messageManager).start();
		messageManager.listen();

	}

}