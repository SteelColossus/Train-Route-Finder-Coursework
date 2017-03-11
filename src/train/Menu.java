package train;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.stream.IntStream;

import javax.swing.*;

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
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel datePanel = new JPanel();
		
		JLabel monthLabel = new JLabel("Month: ");
		JLabel dayLabel = new JLabel("Day: ");
		
		JComboBox<String> monthBox = new JComboBox<String>(IntStream.rangeClosed(1, 12).mapToObj(Integer::toString).toArray(String[]::new));
		JComboBox<String> dayBox = new JComboBox<String>();
		monthBox.setSelectedIndex(-1);
		dayBox.setSelectedIndex(-1);
		((JLabel)monthBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		((JLabel)dayBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		
		String[] stationNameList = manager.getStations().stream().filter(x -> x.isMain()).map(Station::getName).toArray(String[]::new);
		
		JComboBox<String> fromBox = new JComboBox<String>(stationNameList);
		fromBox.setSelectedIndex(-1);
		
		JComboBox<String> toBox = new JComboBox<String>(stationNameList);
		toBox.setSelectedIndex(-1);
		
		JLabel routeLabel = new JLabel();
		JLabel timeLabel = new JLabel();
		JLabel singlePriceLabel = new JLabel();
		JLabel returnPriceLabel = new JLabel();
		JLabel lastDayLabel = new JLabel();
		
		JPanel controlPanel = new JPanel();
		
		JButton adminButton = new JButton("Admin");
		JButton exitButton = new JButton("Exit");
		
		monthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int endDay;
				
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
			}
		});
		
		ActionListener routeListener = new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1 && monthBox.getSelectedIndex() > -1 && dayBox.getSelectedIndex() > -1)
				{
					Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
					
					if (currentRoute != null)
					{
						String routeStr = "";
						
						for (int i = 0; i < currentRoute.getNumStops(); i++)
						{
							routeStr += currentRoute.getStation(i).getName();
							if (i < currentRoute.getNumStops() - 1) routeStr += " -> ";
						}
						
						routeLabel.setText("Route: " + routeStr);
						timeLabel.setText("Time taken: " + currentRoute.getDuration().formatAsWords());
						
						Money singlePrice = new Money(currentRoute.getSinglePrice().getPounds(), currentRoute.getSinglePrice().getPennies());
						Money returnPrice = new Money(currentRoute.getReturnPrice().getPounds(), currentRoute.getReturnPrice().getPennies());
						
						if (dayBox.getSelectedIndex() == dayBox.getItemCount() - 1)
						{
							int discount = 10;
							
							singlePrice.applyDiscount(discount);
							returnPrice.applyDiscount(discount);
							lastDayLabel.setText("By travelling on the last day of the month, you receive a " + discount + "% discount!");
						}
						
						singlePriceLabel.setText("Single price: " + singlePrice.formatCurrency());
						returnPriceLabel.setText("Return price: " + returnPrice.formatCurrency());
					}
				}
			}
		};

		fromBox.addActionListener(routeListener);
		toBox.addActionListener(routeListener);
		
		monthBox.addActionListener(routeListener);
		dayBox.addActionListener(routeListener);
		
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// Insert code to move to an admin frame
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		datePanel.add(monthLabel);
		datePanel.add(monthBox);
		datePanel.add(dayLabel);
		datePanel.add(dayBox);
		controlPanel.add(adminButton);
		controlPanel.add(exitButton);
		
		frame.add(datePanel);
		frame.add(fromBox);
		frame.add(toBox);
		frame.add(routeLabel);
		frame.add(timeLabel);
		frame.add(singlePriceLabel);
		frame.add(returnPriceLabel);
		frame.add(lastDayLabel);
		frame.add(controlPanel);
		
		frame.setVisible(true);
	}
}
