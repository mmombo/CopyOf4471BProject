package com.group9.publishsubscribe.MessageLayer.Authenticator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.LoginRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.AuthenticatorRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.RegisterRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Requests.AuthenticatorRequests.UpdateRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.LoginResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.AuthenticatorResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.RegisterResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Responses.AuthenticatorResponses.UpdateResponse;
import com.group9.publishsubscribe.CommonLayer.Serialization.ISerializer;

public class AuthenticatorJSON implements IAuthenticator {
	
	private static final Logger logger = LogManager.getLogger(AuthenticatorJSON.class.getName());

	private final ISerializer serializer;

	private final String subscriberListFilePath = "subscribers.json";

	public AuthenticatorJSON(ISerializer serializer) {

		this.serializer = serializer;
		
		logger.info("Starting AuthenticatorJSON");

	}

	public AuthenticatorResponse authenticate(AuthenticatorRequest request) {

		Class requestClass = request.getClass();
		logger.info("Received a Message");
		
		AuthenticatorResponse response = null;

		if (LoginRequest.class.isAssignableFrom(requestClass)) {
			
			logger.info("The message is a LoginRequest, details: " + request.getUsername() + ", " + request.getPassword());
			logger.info("Authenticating...");
			
			Subscriber subscriber = authenticateSubscriber(request.getUsername(), request.getPassword());
			
			if (subscriber == null) {
				
				response = new LoginResponse(false, null, request.getSubscriberIP(), request.getSubscriberPort());
				logger.info("Created LoginResponse, access denied");
			}
			
			else {
				
				response = new LoginResponse(true, subscriber, request.getSubscriberIP(), request.getSubscriberPort());
				logger.info("Created LoginResponse, access accepted");
				
			}

		}

		else if (RegisterRequest.class.isAssignableFrom(requestClass)) {
			
			logger.info("The message is a RegisterRequest, details: " + request.getUsername() + ", " + request.getPassword());
			logger.info("Authenticating...");
			
			Subscriber subscriber = registerSubscriber(new Subscriber(request.getUsername(), request.getPassword(), null, request.getSubscriberIP(), request.getSubscriberPort()));
			
			if (subscriber == null) {
				
				response = new RegisterResponse(false, null, request.getSubscriberIP(), request.getSubscriberPort());
				logger.info("Created RegisterResponse, access denied");
				
			}
			
			else {
				
				response = new RegisterResponse(true, subscriber, request.getSubscriberIP(), request.getSubscriberPort());
				logger.info("Created RegisterResponse, access accepted");
				
			}

		}
		
		else if (UpdateRequest.class.isAssignableFrom(requestClass)) {
			
			logger.info("The message is a UpdateRequest, details: " + request.getUsername() + ", " + request.getPassword());
			logger.info("Authenticating...");
			
			Subscriber subscriber = updateSubscriber(new Subscriber(request.getUsername(), request.getPassword(), ((UpdateRequest)request).getSubscriptionList(), request.getSubscriberIP(), request.getSubscriberPort()));
			
			if (subscriber == null) {
				
				response = new UpdateResponse(false, null, request.getSubscriberIP(), request.getSubscriberPort());
				logger.info("Created UpdateResponse, update failed");
				
			}
			
			else {
				
				response = new UpdateResponse(true, subscriber, request.getSubscriberIP(), request.getSubscriberPort());
				logger.info("Created UpdateResponse, update successful");
				
			}

		}
		
		logger.info("Returning AuthenticatorResponse");
		return response;

	}

	private Subscriber authenticateSubscriber(String username, String password) {

		Subscriber[] subscribers = loadSubscriberList();

		if (subscribers == null) {

			logger.info("There are no currently registered subscribers");
			return null;

		}

		// Don't authenticate a non-registered subscriber
		for (int i = 0; i < subscribers.length; i++) {

			if (subscribers[i] != null && subscribers[i].getUsername().equals(username)
					&& subscribers[i].getPassword().equals(password)) {

				logger.info("The username and password were found in the subscriber list");
				return subscribers[i];

			}

		}

		logger.info("The username and password were not found in the subscriber list");
		return null;

	}

	private Subscriber registerSubscriber(Subscriber subscriber) {

		Subscriber[] subscribers = loadSubscriberList();

		if (subscribers == null) {

			logger.info("The subscriber list is empty, creating a new subscriber list");
			subscribers = new Subscriber[1];
			subscribers[0] = subscriber;

		}

		else {

			// Don't register a duplicate subscriber
			for (int i = 0; i < subscribers.length; i++) {

				if (subscribers[i] != null && subscribers[i].getUsername().equals(subscriber.getUsername())) {

					logger.info("The username is already registered in the subscriber list");
					return null;

				}

			}

			int length = subscribers.length;
			Subscriber[] tempSubscribers = new Subscriber[length + 1];
			System.arraycopy(subscribers, 0, tempSubscribers, 0, length);
			subscribers = tempSubscribers;
			subscribers[length] = subscriber;
			
			logger.info("The username and password were saved as a new subscriber in the list");

		}

		saveSubscriberList(subscribers);

		return subscriber;

	}

	private Subscriber updateSubscriber(Subscriber subscriber) {

		Subscriber[] subscribers = loadSubscriberList();

		if (subscribers == null) {

			logger.info("There are no currently registered subscribers, cannot perform update");
			return null;

		}

		for (int i = 0; i < subscribers.length; i++) {

			if (subscribers[i] != null && subscribers[i].getUsername().equals(subscriber.getUsername())
					&& subscribers[i].getPassword().equals(subscriber.getPassword())) {

				subscribers[i] = subscriber;
				saveSubscriberList(subscribers);
				
				logger.info("The information associated with the username and password was saved in the list");
				return subscriber;

			}

		}

		logger.info("The username and password were not found in the subscriber list");
		return null;

	}
	
	public Subscriber[] getSubscribers() {
		
		return loadSubscriberList();
		
	}

	private Subscriber[] loadSubscriberList() {

		File file = new File(subscriberListFilePath);

		if (!file.exists()) {

			return null;

		}

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(file));
			return serializer.deserialize(reader, Subscriber[].class);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} finally {

			try {

				if (reader != null) {

					reader.close();

				}

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return null;

	}

	private void saveSubscriberList(Subscriber[] subscribers) {

		File file = new File(subscriberListFilePath);
		Writer writer = null;

		try {

			writer = new FileWriter(file);
			writer.write(serializer.serialize(subscribers));

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (writer != null) {

					writer.close();

				}

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

}