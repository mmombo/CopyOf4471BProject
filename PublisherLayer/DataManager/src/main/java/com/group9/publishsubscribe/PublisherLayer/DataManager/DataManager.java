package com.group9.publishsubscribe.PublisherLayer.DataManager;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Utility.In;
import com.group9.publishsubscribe.CommonLayer.Utility.Out;
import com.group9.publishsubscribe.CommonLayer.Network.ThreadPoolServer;
import com.group9.publishsubscribe.CommonLayer.Models.Network.Message;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshRequest;
import com.group9.publishsubscribe.CommonLayer.Models.Network.RefreshResponse;
import com.group9.publishsubscribe.CommonLayer.Models.Network.ServiceInfo;

public class DataManager extends ThreadPoolServer {

	private static final Logger logger = LogManager.getLogger(DataManager.class.getName());
	
	private static String serviceManagerIP;
	private static final int serviceManagerPort = 2502;
	private static final int dataManagerPort = 2504;

	private static final String MASTER_COMPANY_LIST = ".." + File.separator + "data" + File.separator + "Companies.txt";
	private static final String MASTER_DIRECTORY_RAW = ".." + File.separator + "data" + File.separator + "MasterDirectoryRaw";
	private static final String MASTER_DIRECTORY_PREPROCESSED = ".." + File.separator + "data" + File.separator + "MasterDirectoryPreProcessed";

	private static Map<String, String> companyList = new LinkedHashMap<>();
	
	private String currentDate;

	public DataManager(int port) {

		super(port);
		currentDate = null;
		logger.info("Starting DataManager");

	}

	private File[] preProcessData() {
		
		logger.info("Initiating process to create new pre-processed data files");

		File[] dirs = readFromMasterDirectory();
		File[] preProcessedData = new File[dirs.length];
		
		int count = 0;

		for (int i = 0; i < dirs.length; i++) {
			
			if (Integer.parseInt(dirs[i].getName()) > Integer.parseInt(currentDate)) {
				
				continue;
				
			}
			
			count++;
			
			File file = new File(MASTER_DIRECTORY_PREPROCESSED + File.separator + dirs[i].getName() + ".txt");
			
			if (!file.exists()) {
				
				preProcessedData[i] = makeFile(dirs[i]);
				
			}
			
			else {
				
				preProcessedData[i] = file;
				
			}

		}
		
		File[] toReturn = new File[count];
		
		count = 0;
		
		for (int i = 0; i < dirs.length; i++) {
			
			if (preProcessedData[i] != null) {
				
				toReturn[count++] = preProcessedData[i];
				
			}
			
		}
		
		return toReturn;

	}
	
	private RefreshResponse getData(ServiceInfo serviceInfo, String currentDate, File[] preProcessedData) {
		
		String[] fileNames = new String[preProcessedData.length];
		String[] fileDatas = new String[preProcessedData.length];
		
		for (int i = 0; i < preProcessedData.length; i++) {
			
			fileNames[i] = preProcessedData[i].getName();
			In in = new In(preProcessedData[i]);
			fileDatas[i] = "";
			
			while (in.hasNextLine()) {

				fileDatas[i] += in.readLine() + "\n";

			}
			
			in.close();
			
			fileDatas[i] = fileDatas[i].substring(0, fileDatas[i].length() - 1);
			
		}
		
		logger.info("filenames length: " + fileNames.length);
		
		return new RefreshResponse(serviceInfo, currentDate, fileNames, fileDatas);
		
	}

	private File[] readFromMasterDirectory() {
		
		logger.info("Getting the list of raw data files from the master directory");
					
		File dir = new File(MASTER_DIRECTORY_RAW);
		return dir.listFiles();

	}

	private void populateCompanies() {

		In in = new In(MASTER_COMPANY_LIST);

		while (in.hasNextLine()) {

			String line = in.readLine();
			String lines[] = line.split(",");

			for (int i = 0; i < lines.length; i++) {

				companyList.put(lines[0], lines[1]);

			}

		}
		
		logger.info("Holding list of companies in internal memory: " + companyList.size());

		in.close();

	}

	private File makeFile(File dir) {

		int numFiles = dir.list().length;
		File[] files = dir.listFiles();
		
		String filename = MASTER_DIRECTORY_PREPROCESSED + File.separator + dir.getName() + ".txt";
		logger.info("Making pre-processed data file: " + filename);
		
		Out out = new Out(filename);

		String companyName = null;
		String companySymbol = null;
		String totalVolume = null;
		String high = null;
		String low = null;
		String openingPrice = null;
		String closingPrice = null;

		for (int i = 0; i < numFiles; i++) {

			In in = new In(files[i]);

			companySymbol = files[i].getName().substring(0, files[i].getName().length() - 4);
			companyName = companyList.get(companySymbol);

			int totalVolumeInt = 0;
			double highDouble = -1;
			double lowDouble = Double.MAX_VALUE;

			int count = 0;

			while (in.hasNextLine()) {

				String line = in.readLine();
				String lines[] = line.split(",");

				int firstDecimalPos = lines[1].length() - 4;

				String price;

				if (lines[1].length() < 2) {

					price = "0.000" + lines[1].substring(firstDecimalPos + 2, lines[1].length());

				} else if (lines[1].length() < 3) {

					price = "0.00" + lines[1].substring(firstDecimalPos + 2, lines[1].length());

				}

				else if (lines[1].length() < 4) {

					price = "0.0" + lines[1].substring(firstDecimalPos + 1, lines[1].length());

				}

				else if (lines[1].length() < 5) {

					price = "0." + lines[1].substring(firstDecimalPos, lines[1].length());

				}

				else {

					price = lines[1].substring(0, firstDecimalPos) + "." + lines[1].substring(firstDecimalPos, lines[1].length());

				}

				if (count == 0) {

					openingPrice = price;

				}

				if (!in.hasNextLine()) {

					closingPrice = price;

				}

				double currentPrice = Double.parseDouble(price);

				if (currentPrice < lowDouble) {

					lowDouble = currentPrice;
					low = price;

				}

				if (currentPrice > highDouble) {

					highDouble = currentPrice;
					high = price;

				}

				totalVolumeInt += Integer.parseInt(lines[2]);

				count++;

			} // end while

			totalVolume = Integer.toString(totalVolumeInt);

			in.close();

			String newLine = "";
			newLine += companyName + ",";
			newLine += companySymbol + ",";
			newLine += totalVolume + ",";
			newLine += high + ",";
			newLine += low + ",";
			newLine += openingPrice + ",";
			newLine += closingPrice + ",";
			out.println(newLine.substring(0, newLine.length() - 1));

		} // end for

		out.close();
		
		return new File(filename);

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
			
			if (RefreshRequest.class.isAssignableFrom(messageClass)) {
				
				logger.info("The message is a RefreshRequest from the ServiceManager");	
				
				populateCompanies();
				
				RefreshRequest refresh = (RefreshRequest)request;
				currentDate = refresh.getCurrentDate();
				
				if (refresh.getServiceInfo().getServiceName().equals("StockInfoService")) {
					
					logger.info("The message is a RefreshRequest for the StockInfoService");
					logger.info("Sending a RefreshResponse containing the StockInfoService's pre-processed data files to the ServiceManager");
					sendMessage(getData(refresh.getServiceInfo(), currentDate, preProcessData()), serviceManagerIP, serviceManagerPort);
					
				}
				
				else if (refresh.getServiceInfo().getServiceName().equals("PriceOverTimeService")) {
					
					logger.info("The message is a RefreshRequest for the PriceOverTimeService");
					logger.info("Sending a RefreshResponse containing the PriceOverTimeService's pre-processed data files to the ServiceManager");
					sendMessage(getData(refresh.getServiceInfo(), currentDate, preProcessData()), serviceManagerIP, serviceManagerPort);
					
				}
				
				else if (refresh.getServiceInfo().getServiceName().equals("VolumeOverTimeService")) {
					
					logger.info("The message is a RefreshRequest for the VolumeOverTimeService");
					logger.info("Sending a RefreshResponse containing the VolumeOverTimeService's pre-processed data files to the ServiceManager");
					sendMessage(getData(refresh.getServiceInfo(), currentDate, preProcessData()), serviceManagerIP, serviceManagerPort);
					
				}
				
				else if (refresh.getServiceInfo().getServiceName().equals("TopVolumeService")) {
					
					// Currently using the StockInfo pre-processed files, perhaps this is fine for all of the services?
					logger.info("The message is a RefreshRequest for the TopVolumeService");
					logger.info("Sending a RefreshResponse containing the TopVolumeService's pre-processed data files to the ServiceManager");
					sendMessage(getData(refresh.getServiceInfo(), currentDate, preProcessData()), serviceManagerIP, serviceManagerPort);
					
				}
				
				else if (refresh.getServiceInfo().getServiceName().equals("CurrencyExchangeService")) {
					
					
					
				}
				
				// unknown service
				else {
					
					
					
				}
				
			}

			else {

				logger.info("I don't currently recognize this message type");

			}

		}

	}
	
	public static void main(String[] args) {

		DataManager dataManager = new DataManager(dataManagerPort);
		new Thread(dataManager).start();
		dataManager.listen();

	}

}