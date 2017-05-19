package train;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The main menu that the program displays when it loads up.
 * @author Michael
 */
public class MainMenu
{
	private RouteManager manager;
	private AdminMenu adminMenu;
	
	// Form components
	private JFrame frame;
	
	private JPanel flowButtonPanel;
	private JPanel buttonPanel;
	private JPanel contentPanel;
	private JPanel datePanel;
	private JPanel stationPanel;
	
	private JButton timeButton;
	private JButton priceButton;
	private JButton routeButton;
	private JButton sortButton;
	private JButton adminButton;
	private JButton exitButton;
	
	private JLabel monthLabel;
	private JLabel dayLabel;
	private JLabel fromLabel;
	private JLabel toLabel;
	private JLabel infoLabel;
	
	private JComboBox<String> monthBox;
	private JComboBox<String> dayBox;
	private JComboBox<String> fromBox;
	private JComboBox<String> toBox;
	
	/**
	 * Constructor taking a route manager.
	 * @param rm	a route manager which stores all the routes managed by this system
	 */
	public MainMenu(RouteManager rm)
	{
		manager = rm;
		defaultSetup();
		
		adminMenu = new AdminMenu(rm);
	}
	
	/**
	 * Gets the current route that the user has selected.
	 * @return	the route the user has selected
	 */
	private Route getCurrentRoute()
	{
		if (monthBox.getSelectedIndex() > -1 && dayBox.getSelectedIndex() > -1 && fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1)
		{
			return manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Sets up the frame to display.
	 */
	private void defaultSetup()
	{
		frame = new JFrame();
		frame.setTitle("Train Route Finder");
		frame.setSize(500, 250);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width / 2) - (frame.getSize().width / 2), (screenSize.height / 2) - (frame.getSize().height / 2));

		flowButtonPanel = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 1, 5, 2));
		
		timeButton = new JButton("Time");
		priceButton = new JButton("Price");
		routeButton = new JButton("Route");
		sortButton = new JButton("Sort");
		adminButton = new JButton("Admin");
		exitButton = new JButton("Exit");
		
		timeButton.setPreferredSize(new Dimension(timeButton.getPreferredSize().width, 33));
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 4));
		
		monthLabel = new JLabel("Month:");
		dayLabel = new JLabel("Day:");
		
		monthBox = new JComboBox<String>(IntStream.rangeClosed(1, 12).mapToObj(Integer::toString).toArray(String[]::new));
		dayBox = new JComboBox<String>();
		
		monthBox.setSelectedIndex(-1);
		dayBox.setSelectedIndex(-1);
		
		((JLabel)monthBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		((JLabel)dayBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
		
		monthBox.setPreferredSize(new Dimension(50, monthBox.getMinimumSize().height));
		dayBox.setPreferredSize(new Dimension(50, dayBox.getMinimumSize().height));
		
		ArrayList<String> stationNameList = new ArrayList<String>();
		
		for (int i = 0; i < manager.getNumStations(); i++)
		{
			Station s = manager.getStation(i);
			
			if (s.isMain() && !stationNameList.contains(s))
			{
				stationNameList.add(s.getName());
			}
		}
		
		stationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
		
		fromLabel = new JLabel("From:");
		toLabel = new JLabel("To:");
		
		fromBox = new JComboBox<String>(stationNameList.toArray(new String[0]));		
		toBox = new JComboBox<String>(stationNameList.toArray(new String[0]));
		
		fromBox.setSelectedIndex(-1);
		toBox.setSelectedIndex(-1);
		
		fromBox.setPreferredSize(new Dimension(120, fromBox.getMinimumSize().height));
		toBox.setPreferredSize(new Dimension(120, toBox.getMinimumSize().height));
		
		infoLabel = new JLabel();
		infoLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		// Action listeners
		timeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Route currentRoute = getCurrentRoute();
				
				if (currentRoute != null)
				{
					infoLabel.setText("Time taken: " + currentRoute.getDuration().formatAsWords());
				}
				else
				{
					infoLabel.setText("Please enter a valid route.");
				}
			}
		});
		
		priceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Route currentRoute = getCurrentRoute();
				
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
				else
				{
					infoLabel.setText("Please enter a valid route.");
				}
			}
		});
		
		routeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Route currentRoute = getCurrentRoute();
				
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
				else
				{
					infoLabel.setText("Please enter a valid route.");
				}
			}
		});
		
		sortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// Yes this is all terrible
				if (infoLabel.getText().contains("Route:"))
				{
					String[] stationStrings = infoLabel.getText().substring("Route: ".length()).split(" -> ");
					Arrays.sort(stationStrings);
					infoLabel.setText("Sorted route: " + String.join(", ", stationStrings));
				}
				else if (!infoLabel.getText().contains("Sorted route:"))
				{
					infoLabel.setText("Please select the \"Route\" option first.");
				}
			}
		});
		
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				adminMenu.show();
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
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
		
		fromBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (fromBox.getSelectedItem() == toBox.getSelectedItem())
				{
					toBox.setSelectedIndex(-1);
				}
			}
		});
		
		toBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (fromBox.getSelectedItem() == toBox.getSelectedItem())
				{
					fromBox.setSelectedIndex(-1);
				}
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int exit = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the program?", "Exit", JOptionPane.YES_NO_OPTION);
				
				if (exit == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
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
		
		c.anchor = GridBagConstraints.PAGE_START;
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
	}
	
	/**
	 * Shows the frame.
	 */
	public void show()
	{
		frame.setVisible(true);
	}
	
	/**
	 * Hides the frame.
	 */
	public void hide()
	{
		frame.setVisible(false);
	}
}
