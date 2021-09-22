package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Subscriber;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.ListServicesAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.SubscribeAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.UnsubscribeAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.SubscribeActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class ListServicesPanel extends JPanel {
	
	private static final Logger logger = LogManager.getLogger(ListServicesPanel.class.getName());
	
	private App backend;
	
	private JPanel panelBottom;
	
	private JButton btnListServices;
	
	private LinkedList<JLabel> lblServiceNames;
	private LinkedList<JLabel> lblServiceDescriptions;
	
	private LinkedList<JButton> btnSubscribes;
	private LinkedList<JButton> btnUnsubscribes;
	
	private LinkedList<SubscribeAction> actSubscribes;
	private LinkedList<UnsubscribeAction> actUnsubscribes;
	
	public ListServicesPanel(App backend) {
		
		this.backend = backend;
		
		JPanel panelOuter = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new BorderLayout());
		panelBottom = new JPanel(new BorderLayout());

		btnListServices = new JButton("List Available Services");
		btnListServices.addActionListener(new ListServicesAction(backend));
		panelTop.add(btnListServices, BorderLayout.CENTER);
		
		panelOuter.add(panelTop, BorderLayout.NORTH);
		panelOuter.add(panelBottom, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(panelOuter, BorderLayout.CENTER);	
		
	}	
	
	public void updateView(Map<String, String> services) {
		
		panelBottom.removeAll();
		panelBottom.setLayout(new GridLayout(services.size(), 4));
		
		lblServiceNames = new LinkedList<>();
		lblServiceDescriptions = new LinkedList<>();
		btnSubscribes = new LinkedList<>();
		btnUnsubscribes = new LinkedList<>();
		actSubscribes = new LinkedList<>();
		actUnsubscribes = new LinkedList<>();
		
		Subscriber subscriber = backend.getSubscriber();
		
		Iterator<Map.Entry<String, String>> it = services.entrySet().iterator();
		
		while (it.hasNext()) {
			
			Map.Entry<String, String> service = it.next();
			
			lblServiceNames.add(new JLabel(service.getKey()));
			lblServiceDescriptions.add(new JLabel(service.getValue()));
			
			SubscribeActionContext contextSubscribe = new SubscribeActionContext();
			contextSubscribe.setServiceName(service.getKey());
			
			SubscribeActionContext contextUnsubscribe = new SubscribeActionContext();
			contextUnsubscribe.setServiceName(service.getKey());
			
			JButton btnSubscribe = new JButton("Subscribe");
			btnSubscribe.addActionListener(new SubscribeAction(backend, contextSubscribe));
			
			JButton btnUnsubscribe = new JButton("Unsubscribe");
			btnUnsubscribe.addActionListener(new UnsubscribeAction(backend, contextUnsubscribe));
			
			if (subscriber.getSubscriptionList() == null) {
				
				btnSubscribe.setEnabled(true);
				btnUnsubscribe.setEnabled(false);
				
			}
			
			else if (subscriber.getSubscriptionList().contains(service.getKey())) {
				
				btnSubscribe.setEnabled(false);
				btnUnsubscribe.setEnabled(true);
				
			}
			
			else {
				
				btnSubscribe.setEnabled(true);
				btnUnsubscribe.setEnabled(false);
				
				
			}
			
			btnSubscribes.add(btnSubscribe);			
			btnUnsubscribes.add(btnUnsubscribe);
			
			panelBottom.add(new JComponentLabel("Service Name", lblServiceNames.getLast()));
			panelBottom.add(new JComponentLabel("Service Description", lblServiceDescriptions.getLast()));
			panelBottom.add(btnSubscribes.getLast());
			panelBottom.add(btnUnsubscribes.getLast());
			
			logger.info("Listing ServiceName: " + service.getKey() + ", ServiceDescription: " + service.getValue());
			
		}
		
		panelBottom.revalidate();
		panelBottom.repaint();
			
	}

}