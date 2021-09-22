package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.CurrencyExchangeGetRatesAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;
import com.group9.publishsubscribe.CommonLayer.Models.Data.CurrencyExchange;

public class CurrencyExchangePanel extends JPanel {
	
	private JPanel panelBottom;
	
	private JButton btnGetRates;
	
	public CurrencyExchangePanel(App backend) {
		
		btnGetRates = new JButton("Get Rates");
		
		JPanel panelOuter = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new BorderLayout());
		
		btnGetRates.addActionListener(new CurrencyExchangeGetRatesAction(backend));
		
		panelTop.setLayout(new GridLayout(1, 1));
		panelTop.add(btnGetRates);	
		
		panelBottom = new JPanel(new BorderLayout());
		
		panelOuter.add(panelTop, BorderLayout.NORTH);
		panelOuter.add(panelBottom, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelOuter, BorderLayout.CENTER);
		
	}
	
	public void updateView(CurrencyExchange currencyExchange) {
		
		panelBottom.removeAll();
		panelBottom.setLayout(new GridLayout(8, 1));
		
		JLabel lblBaseCurrency = new JLabel(currencyExchange.getBase());
		JLabel lblCADRate = new JLabel(Double.toString(currencyExchange.getRates().getCAD()));
		JLabel lblUSDRate = new JLabel(Double.toString(currencyExchange.getRates().getUSD()));
		JLabel lblGBPRate = new JLabel(Double.toString(currencyExchange.getRates().getGBP()));
		JLabel lblSEKRate = new JLabel(Double.toString(currencyExchange.getRates().getSEK()));
		JLabel lblAUDRate = new JLabel(Double.toString(currencyExchange.getRates().getAUD()));
		JLabel lblCNYRate = new JLabel(Double.toString(currencyExchange.getRates().getCNY()));
		JLabel lblRUBRate = new JLabel(Double.toString(currencyExchange.getRates().getRUB()));
		
		panelBottom.add(new JComponentLabel("Base Currency", lblBaseCurrency));
		panelBottom.add(new JComponentLabel("CAD Exchange Rate", lblCADRate));
		panelBottom.add(new JComponentLabel("USD Exchange Rate", lblUSDRate));
		panelBottom.add(new JComponentLabel("GBP Exchange Rate", lblGBPRate));
		panelBottom.add(new JComponentLabel("SEK Exchange Rate", lblSEKRate));
		panelBottom.add(new JComponentLabel("AUD Exchange Rate", lblAUDRate));
		panelBottom.add(new JComponentLabel("CNY Exchange Rate", lblCNYRate));
		panelBottom.add(new JComponentLabel("RUB Exchange Rate", lblRUBRate));
		
		panelBottom.revalidate();
		panelBottom.repaint();
		
	}

}