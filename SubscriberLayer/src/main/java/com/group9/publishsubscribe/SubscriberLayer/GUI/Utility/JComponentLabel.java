package com.group9.publishsubscribe.SubscriberLayer.GUI.Utility;

import java.awt.*;
import javax.swing.*;

public class JComponentLabel extends JPanel {
	
	public final float alignment;
	
	private final JComponent component;
	private JComponent labelComponent;
	private final Font font;
	private final String label;
	
	public JComponentLabel(String label, JComponent component) {
		
		this(label, component, Component.CENTER_ALIGNMENT);
		
	}
	
	public JComponentLabel(String label, JComponent component, float alignment) {
		
		this(label, component, alignment, Fonts.Small);
		
	}
	
	public JComponentLabel(String label, JComponent component, float alignment, Font font) {
		
		this.label = label;
		this.component = component;
		this.alignment = alignment;
		this.font = font;
		
		init();
		
	}
	
	private void init() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel lblLabel = new JLabel(label);
		lblLabel.setFont(font);
		lblLabel.setAlignmentX(alignment);
		
		labelComponent = new JPanel();
		labelComponent.setLayout(new BoxLayout(labelComponent, BoxLayout.PAGE_AXIS));
		labelComponent.add(lblLabel);
		labelComponent.setOpaque(true);
		labelComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int)(font.getSize() * 1.5)));
		labelComponent.setAlignmentX(alignment);
		
		component.setAlignmentX(alignment);
		
		this.add(labelComponent);
		this.add(component);
		
	}
	
	public void setLabelBackground(Color color) {
		
		labelComponent.setBackground(color);
		
	}

}