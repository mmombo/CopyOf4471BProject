package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
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
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.StockInfoSearchActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.StockInfo;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.Fonts;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class StockInfoPanel extends JPanel {
	
	private JTextField txtCompany;
	private JTextField txtDate;
	
	private JButton btnSearch;
	
	private JLabel lblCompanyName;
	private JLabel lblCompanySymbol;
	private JLabel lblDate;
	private JLabel lblTotalVolume;
	private JLabel lblHigh;
	private JLabel lblLow;
	private JLabel lblOpeningPrice;
	private JLabel lblClosingPrice;
	
	public StockInfoPanel(App backend) {
		
		txtCompany = new JTextField();
		txtDate = new JTextField();
		
		btnSearch = new JButton("Search");
		
		lblCompanyName = new JLabel("<>");
		lblCompanySymbol = new JLabel("<>");
		lblDate = new JLabel("<>");
		lblTotalVolume = new JLabel("<>");
		lblHigh = new JLabel("<>");
		lblLow = new JLabel("<>");
		lblOpeningPrice = new JLabel("<>");
		lblClosingPrice = new JLabel("<>");
		
		lblCompanyName.setFont(Fonts.Medium);
		lblCompanySymbol.setFont(Fonts.Medium);
		lblDate.setFont(Fonts.Medium);
		lblTotalVolume.setFont(Fonts.Medium);
		lblHigh.setFont(Fonts.Medium);
		lblLow.setFont(Fonts.Medium);
		lblOpeningPrice.setFont(Fonts.Medium);
		lblClosingPrice.setFont(Fonts.Medium);
		
		JPanel panelOuter = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new BorderLayout());
		
		final AutoCompleteBehaviour<Company> autoComplete = new AutoCompleteBehaviour<Company>();
		
		autoComplete.setCallback( new AutoCompleteBehaviour.DefaultAutoCompleteCallback<Company>() {
			
            @Override
            public List<Company> getProposals(String input) {
            	
                if ( input.length() < 1 ) {
                	
                    return Collections.emptyList();
                    
                }
                
                final String lower = input.toLowerCase();

                final List<Company>  result = new ArrayList<>();
                
                for (Company company : backend.getCompanyList()) {
                	
                    if (company.getCompanyName().toLowerCase().contains(lower) || company.getCompanySymbol().contains(lower)) {
                    	
                        result.add(company);
                        
                    }
                    
                }
                
                return result;
                
            }

            @Override
            public String getStringToInsert(Company company) {
            	
                return company.getCompanyName();
                
            }
            
        }); 
		
        // set a custom renderer for our proposals
        final DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
        	
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            	
                final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final Company company = (Company) value;
                setText(company.getCompanyName() + " <" + company.getCompanySymbol() + ">");
                return result;
                
            }
            
        };
        
        autoComplete.setListCellRenderer(renderer);
        autoComplete.setInitialPopupSize(new Dimension(150,1000));
        autoComplete.setVisibleRowCount(5);
        autoComplete.attachTo(txtCompany);		
		
        DatePicker datePickerDate = new DatePicker();
        
		StockInfoSearchActionContext context = new StockInfoSearchActionContext();
		context.setCompanyName(txtCompany);
		context.setDate(datePickerDate);
		
		btnSearch.addActionListener(new StockInfoSearchAction(backend, context));
		
		panelTop.setLayout(new GridLayout(1, 3));
		panelTop.add(new JComponentLabel("Company Name", txtCompany));
		panelTop.add(new JComponentLabel("Date", datePickerDate));
		panelTop.add(btnSearch);		
		
		JPanel panelBottom = new JPanel(new GridLayout(2,4));		
		panelBottom.add(new JComponentLabel("Company Name", lblCompanyName));
		panelBottom.add(new JComponentLabel("Company Symbol", lblCompanySymbol));
		panelBottom.add(new JComponentLabel("Date", lblDate));
		panelBottom.add(new JComponentLabel("Total Volume", lblTotalVolume));
		panelBottom.add(new JComponentLabel("High", lblHigh));
		panelBottom.add(new JComponentLabel("Low", lblLow));
		panelBottom.add(new JComponentLabel("Opening Price", lblOpeningPrice));
		panelBottom.add(new JComponentLabel("Closing Price", lblClosingPrice));
		
		panelOuter.add(panelTop, BorderLayout.NORTH);
		panelOuter.add(panelBottom, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelOuter, BorderLayout.CENTER);
		
	}
	
	public void updateView(StockInfo stockInfo) {
		
		lblCompanyName.setText(stockInfo.getCompanyName());
		lblCompanySymbol.setText(stockInfo.getCompanySymbol());
		lblDate.setText(stockInfo.getDate());
		lblTotalVolume.setText(stockInfo.getTotalVolume());
		lblHigh.setText(stockInfo.getHigh());
		lblLow.setText(stockInfo.getLow());
		lblOpeningPrice.setText(stockInfo.getOpeningPrice());
		lblClosingPrice.setText(stockInfo.getClosingPrice());
		
	}

}
