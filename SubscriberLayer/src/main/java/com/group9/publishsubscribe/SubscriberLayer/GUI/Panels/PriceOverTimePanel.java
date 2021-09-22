package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Company;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.AutoComplete.AutoCompleteBehaviour;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.PriceOverTimeSearchAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.PriceOverTimeSearchActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.PriceOverTime;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class PriceOverTimePanel extends JPanel {

	private JPanel panelBottom;
	private JTextField txtCompany;
	private JButton btnSearch;
	
	public PriceOverTimePanel(App backend) {
		
		txtCompany = new JTextField();
		
		btnSearch = new JButton("Search");
		
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
        
        PriceOverTimeSearchActionContext context = new PriceOverTimeSearchActionContext();
		context.setCompanyName(txtCompany);
		
		btnSearch.addActionListener(new PriceOverTimeSearchAction(backend, context));
		
		panelTop.setLayout(new GridLayout(1, 2));
		panelTop.add(new JComponentLabel("Company Name", txtCompany));
		panelTop.add(btnSearch);	
		
		panelBottom = new JPanel(new BorderLayout());
		
		panelOuter.add(panelTop, BorderLayout.NORTH);
		panelOuter.add(panelBottom, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelOuter, BorderLayout.CENTER);
		
	}
	
	public void updateView(PriceOverTime priceOverTime) {
		
		panelBottom.removeAll();
		
		int[] days = priceOverTime.getDays();
		int[] months = priceOverTime.getMonths();
		int[] years = priceOverTime.getYears();
		String[] prices = priceOverTime.getPrices();
		
		TimeSeries series = new TimeSeries("Price Over Time");
		
		for (int i = 0; i < prices.length; i++) {
			
			if (prices[i] != null) {
				
				Day day = new Day(days[i], months[i], years[i]);				
				series.add(day, Double.parseDouble(prices[i]));
				
			}
			
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart(priceOverTime.getCompanyName() + " Price Over Time", "Date", "Price", dataset, true, true, false);
		ChartPanel panel = new ChartPanel(chart);
		
		panelBottom.setLayout(new BorderLayout());
		panelBottom.add(panel, BorderLayout.CENTER);		
		
		panelBottom.revalidate();
		panelBottom.repaint();
		
	}

}