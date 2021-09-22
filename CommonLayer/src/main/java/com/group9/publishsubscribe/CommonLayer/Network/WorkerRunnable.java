package com.group9.publishsubscribe.CommonLayer.Network;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Serialization.ISerializer;
import com.group9.publishsubscribe.CommonLayer.Utility.BetterInputStreamReader;

public class WorkerRunnable implements Runnable {

	protected Socket clientSocket = null;
	
	private BlockingQueue<Message> queue;
	
	private final ISerializer serializer;

	public WorkerRunnable(ISerializer serializer, Socket clientSocket, BlockingQueue<Message> queue) {

		this.serializer = serializer;
		this.clientSocket = clientSocket;
		this.queue = queue;

	}

	public void run() {

		try {
			
			Message message = serializer.deserialize(new BetterInputStreamReader(clientSocket.getInputStream()).toString(), Message.class);			
			queue.add(message);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}