package com.group9.publishsubscribe.SubscriberLayer.GUI.Frames;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Menus.MenuBar;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.Fonts;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.CurrencyExchangePanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.ListServicesPanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.PriceOverTimePanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.StockInfoPanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.TopVolumePanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.UserDashboardPanel;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Panels.VolumeOverTimePanel;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	private final App backend;

	private final static int DIMEN_WIDTH = 1600;
	private final static int DIMEN_HEIGHT = 740;
	private final static int DIMEN_HEIGHT_USER_DASHBOARD = 60;
	private final static int DIMEN_HEIGHT_TITLE_BAR = 44;
	private final static Dimension WINDOW_SIZE = new Dimension(DIMEN_WIDTH, DIMEN_HEIGHT);
	
	private JTabbedPane tabbedPane;
	private Map<String, JPanel> panels;

	public MainFrame(App backend) {

		super("Publish-Subscribe Application - Group 9");

		this.backend = backend;
		backend.setMainFrame(this);

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setPreferredSize(WINDOW_SIZE);
		this.setMinimumSize(WINDOW_SIZE);
		this.setMaximumSize(WINDOW_SIZE);
		this.setJMenuBar(new MenuBar(backend));
		this.setLayout(new GridLayout(1, 1));
		
		panels = new HashMap<>();
		panels.put("ListServices", createListServicesPanel());
		panels.put("StockInfoService", createStockInfoPanel());
		panels.put("PriceOverTimeService", createPriceOverTimePanel());
		panels.put("VolumeOverTimeService", createVolumeOverTimePanel());
		panels.put("TopVolumeService", createTopVolumePanel());
		panels.put("CurrencyExchangeService", createCurrencyExchangePanel());

		this.add(createMainPanel());
		this.pack();

	}
	
	public JTabbedPane getTabbedPane() {
		
		return tabbedPane;
		
	}
	
	public void addView(String view) {
		
		tabbedPane.addTab(view, panels.get(view));
		
	}
	
	public void removeView(String view) {
		
		tabbedPane.remove(panels.get(view));
		
	}
	
	public void updateViewTitle(String view) {
		
		int index = tabbedPane.indexOfComponent(panels.get(view));
		tabbedPane.setTitleAt(index, view + "*");
		tabbedPane.setForegroundAt(index, Color.red);
		
	}

	private JComponent createMainPanel() {
		
		JPanel panel = new JPanel(new BorderLayout());
		JComponentLabel userDashboardPanel = createUserDashboardPanel();
		tabbedPane = new JTabbedPane();
		addView("ListServices");
		
		LinkedList<String> subscriptionList = backend.getSubscriber().getSubscriptionList();
		
		if (subscriptionList != null) {
			
			for (int i = 0; i < subscriptionList.size(); i++) {
				
				addView(subscriptionList.get(i));
				
			}	
			
		}
		
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	int index = tabbedPane.getSelectedIndex();
	        	
	        	for (Entry<String, JPanel> entry : panels.entrySet()) {
	                if (entry.getValue().equals(tabbedPane.getComponentAt(index))) {
	    	    		tabbedPane.setTitleAt(index, entry.getKey());
	    	    		tabbedPane.setForegroundAt(index, Color.black);
	                }
	            }
	        }
	    });
		
		panel.add(userDashboardPanel, BorderLayout.NORTH);
		panel.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		return panel;

	}

	private JComponentLabel createUserDashboardPanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		UserDashboardPanel userDashboardPanel = new UserDashboardPanel(backend);
		backend.registerUserDashboardPanel(userDashboardPanel);		
		backend.currentDateRequest();
		JComponentLabel panel = new JComponentLabel("View Your Dashboard", userDashboardPanel, alignment,
				Fonts.Large);
		
		final Dimension size = new Dimension((int)(DIMEN_WIDTH * .8), DIMEN_HEIGHT_USER_DASHBOARD);
		panel.setMinimumSize(size);
		panel.setPreferredSize(size);
		panel.setMaximumSize(size);
		panel.setSize(size);
		panel.revalidate();
		
		return panel;

	}
	
	private JComponentLabel createListServicesPanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		ListServicesPanel listServicesPanel = new ListServicesPanel(backend);
		backend.registerListServicesPanel(listServicesPanel);
		JComponentLabel panel = new JComponentLabel("List Services", listServicesPanel, alignment, Fonts.Large);
		
		final Dimension size = new Dimension((int)(DIMEN_WIDTH * .8), DIMEN_HEIGHT - DIMEN_HEIGHT_USER_DASHBOARD);
		panel.setMinimumSize(size);
		panel.setPreferredSize(size);
		panel.setMaximumSize(size);
		panel.setSize(size);
		panel.revalidate();
		
		return panel;

	}
	
	private JComponentLabel createStockInfoPanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		StockInfoPanel stockInfoPanel = new StockInfoPanel(backend);
		backend.registerStockInfoPanel(stockInfoPanel);
		JComponentLabel panel = new JComponentLabel("Stock Info", stockInfoPanel, alignment, Fonts.Large);
		
		final Dimension size = new Dimension((int)(DIMEN_WIDTH * .8), DIMEN_HEIGHT - DIMEN_HEIGHT_USER_DASHBOARD);
		panel.setMinimumSize(size);
		panel.setPreferredSize(size);
		panel.setMaximumSize(size);
		panel.setSize(size);
		panel.revalidate();
		
		return panel;

	}
	
	private JComponentLabel createPriceOverTimePanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		PriceOverTimePanel priceOverTimePanel = new PriceOverTimePanel(backend);
		backend.registerPriceOverTimePanel(priceOverTimePanel);
		JComponentLabel panel = new JComponentLabel("Price Over Time", priceOverTimePanel, alignment,
				Fonts.Large);
		return panel;

	}

	private JComponentLabel createVolumeOverTimePanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		VolumeOverTimePanel volumeOverTimePanel = new VolumeOverTimePanel(backend);
		backend.registerVolumeOverTimePanel(volumeOverTimePanel);
		JComponentLabel panel = new JComponentLabel("Volume Over Time", volumeOverTimePanel, alignment,
				Fonts.Large);
		return panel;

	}

	private JComponentLabel createCurrencyExchangePanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		CurrencyExchangePanel currencyExchangePanel = new CurrencyExchangePanel(backend);
		backend.registerCurrencyExchangePanel(currencyExchangePanel);
		JComponentLabel panel = new JComponentLabel("Currency Exchange", currencyExchangePanel, alignment,
				Fonts.Large);
		return panel;

	}

	private JComponentLabel createTopVolumePanel() {

		final float alignment = Component.CENTER_ALIGNMENT;
		TopVolumePanel topVolumePanel = new TopVolumePanel(backend);
		backend.registerTopVolumePanel(topVolumePanel);
		JComponentLabel panel = new JComponentLabel("Top Volume", topVolumePanel, alignment, Fonts.Large);
		return panel;

	}

}