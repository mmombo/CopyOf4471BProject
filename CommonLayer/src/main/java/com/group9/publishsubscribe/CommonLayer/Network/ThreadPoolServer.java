package com.group9.publishsubscribe.CommonLayer.Network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Serialization.ISerializer;
import com.group9.publishsubscribe.CommonLayer.Serialization.SerializerJackson;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer implements Runnable {

	protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
	protected Thread runningThread = null;
	protected ServerSocket serverSocket = null;
	protected int port;
	protected boolean isStopped = false;
	
	protected BlockingQueue<Message> queue;
	
	private final ISerializer serializer;
	
	private PrintWriter writer;
	
	public ThreadPoolServer() {
		
		this.queue = new LinkedBlockingQueue<>(10);
		this.serializer = new SerializerJackson();
		
	}
	
	public ThreadPoolServer(int port) {
		
		this.port = port;
		this.queue = new LinkedBlockingQueue<>(10);
		this.serializer = new SerializerJackson();
		
	}
	
	public void setPort(int port) {
		
		this.port = port;
		
	}

	public void run() {
		
		synchronized (this) {
			
			this.runningThread = Thread.currentThread();
			
		}
		
		openServerSocket();
		
		while (!isStopped()) {
			
			Socket clientSocket = null;
			
			try {
				
				clientSocket = this.serverSocket.accept();
				
			} catch (IOException e) {
				
				if (isStopped()) {
					
					System.out.println("Server Stopped.");
					break;
					
				}
				
				throw new RuntimeException("Error accepting client connection", e);
				
			}
			
			this.threadPool.execute(new WorkerRunnable(serializer, clientSocket, queue));
			
		}
		
		this.threadPool.shutdown();
		System.out.println("Server Stopped.");
		
	}
	
	private void openServerSocket() {
		
		try {
			
			this.serverSocket = new ServerSocket(this.port);
			
		} catch (IOException e) {
			
			throw new RuntimeException("Cannot open port " + port, e);
			
		}
		
	}
	
	protected synchronized void stop() {
		
		this.isStopped = true;
		
		try {
			
			this.serverSocket.close();
			
		} catch (IOException e) {
			
			throw new RuntimeException("Error closing server", e);
			
		}
		
	}

	protected synchronized boolean isStopped() {
		
		return this.isStopped;
		
	}
	
	protected void sendMessage(Message message, String destinationIP, int port) {
		
		Socket destination = null;
		
		try {
			
			destination = new Socket(destinationIP, port);
			writer = new PrintWriter(destination.getOutputStream(), true);
			writer.println(serializer.serialize(message));
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			if (writer != null) {
				
				writer.close();
				
			}
			
			if (destination != null) {
				
				try {
					
					destination.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}
		
	}
	
}