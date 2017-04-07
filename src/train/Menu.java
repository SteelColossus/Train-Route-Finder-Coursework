package train;

import java.awt.*;
import java.awt.event.*;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu
{
	private RouteManager manager;
	
	public Menu(RouteManager rm)
	{
		this.manager = rm;
	}
	
	public void start()
	{
		JFrame frame = new JFrame();
		frame.setTitle("Train Route Finder");
		frame.setSize(500, 250);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		JPanel flowButtonPanel = new JPanel(new FlowLayout());
		JPanel buttonPanel = new JPanel();
		GridLayout gb = new GridLayout(6, 1, 5, 2);
		buttonPanel.setLayout(gb);
		
		JButton timeButton = new JButton("Time");
		JButton priceButton = new JButton("Price");
		JButton routeButton = new JButton("Route");
		JButton sortButton = new JButton("Sort");
		JButton adminButton = new JButton("Admin");
		JButton exitButton = new JButton("End");
		
		timeButton.setPreferredSize(new Dimension(timeButton.getPreferredSize().width, 33));
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		
		JLabel monthLabel = new JLabel("Month:");
		JLabel dayLabel = new JLabel("Day:");
		
		JComboBox<String> monthBox = new JComboBox<String>(IntStream.rangeClosed(1, 12).mapToObj(Integer::toString).toArray(String[]::new));
		JComboBox<String> dayBox = new JComboBox<String>();
		
		monthBox.setSelectedIndex(-1);
		dayBox.setSelectedIndex(-1);
		
		((JLabel)monthBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		((JLabel)dayBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		
		monthBox.setPreferredSize(new Dimension(50, monthBox.getMinimumSize().height));
		dayBox.setPreferredSize(new Dimension(50, dayBox.getMinimumSize().height));
		
		String[] stationNameList = manager.getAllStations().stream().filter(x -> x.isMain()).map(Station::getName).toArray(String[]::new);
		
		JPanel stationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		
		JLabel fromLabel = new JLabel("From:");
		JLabel toLabel = new JLabel("To:");
		
		JComboBox<String> fromBox = new JComboBox<String>(stationNameList);		
		JComboBox<String> toBox = new JComboBox<String>(stationNameList);
		
		fromBox.setSelectedIndex(-1);
		toBox.setSelectedIndex(-1);
		
		fromBox.setPreferredSize(new Dimension(120, fromBox.getMinimumSize().height));
		toBox.setPreferredSize(new Dimension(120, toBox.getMinimumSize().height));
		
		JLabel infoLabel = new JLabel();
		infoLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		monthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int endDay;
				int prevIndex = dayBox.getSelectedIndex();
				
				switch (monthBox.getSelectedIndex())
				{
					case 1:
						endDay = 28;
						break;
					case 3:
					case 5:
					case 8:
					case 10:
						endDay = 30;
						break;
					default:
						endDay = 31;
						break;
				}
				
				dayBox.removeAllItems();
				IntStream.rangeClosed(1, endDay).mapToObj(Integer::toString).forEach(x -> dayBox.addItem(x));
				
				if (prevIndex < 0) prevIndex = 0;
				else if (prevIndex >= endDay) prevIndex = endDay - 1;
				
				dayBox.setSelectedIndex(prevIndex);
			}
		});
		
		timeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (monthBox.getSelectedIndex() > -1 && dayBox.getSelectedIndex() > -1 && fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1)
				{
					Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
					
					if (currentRoute != null)
					{
						infoLabel.setText("Time taken: " + currentRoute.getDuration().formatAsWords());
					}
				}
			}
		});
		
		priceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (monthBox.getSelectedIndex() > -1 && dayBox.getSelectedIndex() > -1 && fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1)
				{
					Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
					
					if (currentRoute != null)
					{
						String priceStr = "";
						
						Money singlePrice = new Money(currentRoute.getSinglePrice().getPounds(), currentRoute.getSinglePrice().getPennies());
						Money returnPrice = new Money(currentRoute.getReturnPrice().getPounds(), currentRoute.getReturnPrice().getPennies());
						
						priceStr += "<html>" + "Single price: " + singlePrice.formatCurrency() + "<br>";
						priceStr += "Return price: " + returnPrice.formatCurrency();
						
						if (dayBox.getSelectedIndex() == dayBox.getItemCount() - 1)
						{
							int discount = 10;
							
							singlePrice.applyDiscount(discount);
							returnPrice.applyDiscount(discount);
							priceStr += "<br>" + "By travelling on the last day of the month,<br>you receive a " + discount + "% discount!";
						}
						
						priceStr += "</html>";
						infoLabel.setText(priceStr);
					}
				}
			}
		});
		
		routeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (monthBox.getSelectedIndex() > -1 && dayBox.getSelectedIndex() > -1 && fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1)
				{
					Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
					
					if (currentRoute != null)
					{
						String routeStr = "";
						
						for (int i = 0; i < currentRoute.getNumStations(); i++)
						{
							routeStr += currentRoute.getStation(i).getName();
							if (i < currentRoute.getNumStations() - 1) routeStr += " -> ";
						}
						
						infoLabel.setText("Route: " + routeStr);
					}
				}
			}
		});
		
		sortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			}
		});
		
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		buttonPanel.add(timeButton);
		buttonPanel.add(priceButton);
		buttonPanel.add(routeButton);
		buttonPanel.add(sortButton);
		buttonPanel.add(adminButton);
		buttonPanel.add(exitButton);
		
		flowButtonPanel.add(buttonPanel);
		
		datePanel.add(monthLabel);
		datePanel.add(monthBox);
		datePanel.add(dayLabel);
		datePanel.add(dayBox);
		
		stationPanel.add(fromLabel);
		stationPanel.add(fromBox);
		stationPanel.add(toLabel);
		stationPanel.add(toBox);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		
		contentPanel.add(datePanel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		
		contentPanel.add(stationPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 1.0;
		
		contentPanel.add(infoLabel, c);
		
		frame.add(flowButtonPanel);
		frame.add(contentPanel);
		
		frame.setVisible(true);
	}
}
