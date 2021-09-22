package com.group9.publishsubscribe.SubscriberLayer.GUI.Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.group9.publishsubscribe.CommonLayer.Models.Data.Company;
import com.group9.publishsubscribe.SubscriberLayer.App;
import com.group9.publishsubscribe.SubscriberLayer.AutoComplete.AutoCompleteBehaviour;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Actions.VolumeOverTimeSearchAction;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Contexts.VolumeOverTimeSearchActionContext;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Models.VolumeOverTime;
import com.group9.publishsubscribe.SubscriberLayer.GUI.Utility.JComponentLabel;

public class VolumeOverTimePanel extends JPanel {
	
	private JPanel panelBottom;
	
	private JTextField txtCompany;
	
	private JButton btnSearch;
	
	public VolumeOverTimePanel(App backend) {
		
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
        
        VolumeOverTimeSearchActionContext context = new VolumeOverTimeSearchActionContext();
		context.setCompanyName(txtCompany);
		
		btnSearch.addActionListener(new VolumeOverTimeSearchAction(backend, context));
		
		panelTop.setLayout(new GridLayout(1, 2));
		panelTop.add(new JComponentLabel("Company Name", txtCompany));
		panelTop.add(btnSearch);	
		
		panelBottom = new JPanel(new BorderLayout());
		
		panelOuter.add(panelTop, BorderLayout.NORTH);
		panelOuter.add(panelBottom, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelOuter, BorderLayout.CENTER);
		
	}
	
	public void updateView(VolumeOverTime volumeOverTime) {
		
		panelBottom.removeAll();
		
		int[] days = volumeOverTime.getDays();
		int[] months = volumeOverTime.getMonths();
		int[] years = volumeOverTime.getYears();
		String[] volumes = volumeOverTime.getVolumes();	
		
		TimeSeries series = new TimeSeries("Volume Over Time");
		
		for (int i = 0; i < volumes.length; i++) {
			
			if (volumes[i] != null) {
				
				Day day = new Day(days[i], months[i], years[i]);				
				series.add(day, Double.parseDouble(volumes[i]));
				
			}
			
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart(volumeOverTime.getCompanyName() + " Price Over Time", "Date", "Price", dataset, true, true, false);
		ChartPanel panel = new ChartPanel(chart);
		
		panelBottom.setLayout(new BorderLayout());
		panelBottom.add(panel, BorderLayout.CENTER);		
		
		panelBottom.revalidate();
		panelBottom.repaint();
	
	}

}
