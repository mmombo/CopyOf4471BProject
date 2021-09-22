package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.group9.publishsubscribe.CommonLayer.Models.Data.Company;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.AutoComplete.AutoCompleteBehaviour;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.StockInfoSearchAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.TopVolumeSearchAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.StockInfoSearchActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.TopVolumeSearchActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.TopVolume;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.Fonts;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class TopVolumePanel extends JPanel {
	
	private JTextField txtDate;
	
	private JButton btnSearch;

	private JLabel b1;
	private JLabel b2;
	private JLabel b3;
	private JLabel b4;
	private JLabel b5;
	private JLabel w1;
	private JLabel w2;
	private JLabel w3;
	private JLabel w4;
	private JLabel w5;

	public TopVolumePanel(App backend) {
		
		txtDate = new JTextField();
		
		btnSearch = new JButton("Search");

		JLabel bTitle = new JLabel("Best Trades:");
		b1 = new JLabel("<> : <>");
		b2 = new JLabel("<> : <>");
		b3 = new JLabel("<> : <>");
		b4 = new JLabel("<> : <>");
		b5 = new JLabel("<> : <>");

		JLabel wTitle = new JLabel("Worst Trades:");
		w1 = new JLabel("<> : <>");
		w2 = new JLabel("<> : <>");
		w3 = new JLabel("<> : <>");
		w4 = new JLabel("<> : <>");
		w5 = new JLabel("<> : <>");

		bTitle.setFont(Fonts.Large);
		b1.setFont(Fonts.Medium);
		b2.setFont(Fonts.Medium);
		b3.setFont(Fonts.Medium);
		b4.setFont(Fonts.Medium);
		b5.setFont(Fonts.Medium);

		wTitle.setFont(Fonts.Large);
		w1.setFont(Fonts.Medium);
		w2.setFont(Fonts.Medium);
		w3.setFont(Fonts.Medium);
		w4.setFont(Fonts.Medium);
		w5.setFont(Fonts.Medium);
		
		JPanel panelOuter = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new BorderLayout());	
		
        DatePicker datePickerDate = new DatePicker();
        
		TopVolumeSearchActionContext context = new TopVolumeSearchActionContext();
		context.setDate(datePickerDate);
		
		btnSearch.addActionListener(new TopVolumeSearchAction(backend, context));
		
		panelTop.setLayout(new GridLayout(1, 2));
		panelTop.add(new JComponentLabel("Date", datePickerDate));
		panelTop.add(btnSearch);
		
		JPanel panelBottom = new JPanel(new GridLayout(1,2));		

		JPanel bestPanel = new JPanel(new GridLayout(6, 1));
		JPanel worstPanel = new JPanel(new GridLayout(6, 1));

		bestPanel.add(bTitle);
		bestPanel.add(b1);
		bestPanel.add(b2);
		bestPanel.add(b3);
		bestPanel.add(b4);
		bestPanel.add(b5);

		worstPanel.add(wTitle);
		worstPanel.add(w1);
		worstPanel.add(w2);
		worstPanel.add(w3);
		worstPanel.add(w4);
		worstPanel.add(w5);
		
		panelBottom.add(bestPanel);
		panelBottom.add(worstPanel);
		
		panelOuter.add(panelTop, BorderLayout.NORTH);
		panelOuter.add(panelBottom, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelOuter, BorderLayout.CENTER);

	}

	public void updateView(TopVolume topVolume) {

		HashMap<String, Integer> bestTrades = topVolume.getBestTrades();
		HashMap<String, Integer> worstTrades = topVolume.getWorstTrades();

		int count = 0;
		for (String name : bestTrades.keySet()) {
			String value = bestTrades.get(name).toString();
			switch (count) {
			case 0:
				b1.setText(name + " : " + value);
				break;
			case 1:
				b2.setText(name + " : " + value);
				break;
			case 2:
				b3.setText(name + " : " + value);
				break;
			case 3:
				b4.setText(name + " : " + value);
				break;
			case 4:
				b5.setText(name + " : " + value);
				break;
			default:
				break;
			}
			count++;
		}

		count = 0;
		for (String name : worstTrades.keySet()) {
			String value = worstTrades.get(name).toString();
			switch (count) {
			case 0:
				w1.setText(name + " : " + value);
				break;
			case 1:
				w2.setText(name + " : " + value);
				break;
			case 2:
				w3.setText(name + " : " + value);
				break;
			case 3:
				w4.setText(name + " : " + value);
				break;
			case 4:
				w5.setText(name + " : " + value);
				break;
			default:
				break;
			}
			count++;
		}

	}

}
