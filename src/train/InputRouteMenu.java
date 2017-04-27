package train;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("unused")
public class InputRouteMenu
{
	private RouteManager manager;

	private JDialog frame;
	
	private JPanel stopsPanel;
	
	private JComboBox<String> fromBox;
	private JComboBox<String> toBox;
	
	private ArrayList<JButton> deleteNewButtons;
	private ArrayList<JLabel> existingStops;
	
	private JPanel newStopPanel;
	private JTextField newStop;
	private JButton addButton;
	
	public InputRouteMenu(RouteManager rm)
	{
		manager = rm;
		setup();
	}
	
	private void setup()
	{
		deleteNewButtons = new ArrayList<JButton>();
		existingStops = new ArrayList<JLabel>();
		
		frame = new JDialog();
		frame.setTitle("Input Route");
		frame.setSize(200, 200);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setModal(true);
		
		stopsPanel = new JPanel();
		stopsPanel.setLayout(new BoxLayout(stopsPanel, BoxLayout.Y_AXIS));
		
		String[] stationNameList = manager.getAllStations().stream().filter(x -> x.isMain()).map(Station::getName).toArray(String[]::new);
		
		fromBox = new JComboBox<String>(stationNameList);		
		toBox = new JComboBox<String>(stationNameList);
		
		fromBox.setSelectedIndex(-1);
		toBox.setSelectedIndex(-1);
		
		fromBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (fromBox.getSelectedItem() == toBox.getSelectedItem())
				{
					toBox.setSelectedIndex(-1);
				}
				
				updateStops();
			}
		});
		
		toBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (fromBox.getSelectedItem() == toBox.getSelectedItem())
				{
					fromBox.setSelectedIndex(-1);
				}
				
				updateStops();
			}
		});
		
		frame.add(fromBox);
		frame.add(stopsPanel);
		frame.add(toBox);
		
		updateStops();
	}
	
	private void addNewStop()
	{
		Station s = manager.addStation(newStop.getText(), false);
		Route r = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
		manager.addRouteStop(r, s);
		updateStops();
	}
	
	private void removeCurrentStop()
	{
		Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
		Station s = manager.getStation(existingStops.get(currentRoute.getNumStops() - 1).getText());
		currentRoute.removeStation(s);
		updateStops();
	}
	
	private void updateStops()
	{
		existingStops.clear();
		stopsPanel.removeAll();
		
		if (fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1)
		{
			Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
			
			if (currentRoute != null)
			{
				for (int i = 0; i < currentRoute.getNumStops(); i++)
				{
					JPanel currentStopPanel = new JPanel();
					JLabel currentStop = new JLabel();
					JButton deleteButton = new JButton("-");
					
					deleteButton.setPreferredSize(new Dimension(deleteButton.getPreferredSize().width, 25));		
					currentStop.setText(currentRoute.getStop(i).getName());
					
					deleteButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e)
						{
							removeCurrentStop();
						}
					});
					
					existingStops.add(currentStop);
					
					currentStopPanel.add(currentStop);
					currentStopPanel.add(deleteButton);
					
					stopsPanel.add(currentStopPanel);
				}
			}
			
			newStopPanel = new JPanel();
			newStop = new JTextField();
			addButton = new JButton("+");
			
			newStop.setPreferredSize(new Dimension(100, newStop.getPreferredSize().height));
			addButton.setPreferredSize(new Dimension(addButton.getPreferredSize().width, 25));
			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					addNewStop();
				}
			});
			
			newStopPanel.add(newStop);
			newStopPanel.add(addButton);
			
			stopsPanel.add(newStopPanel);
		}

		stopsPanel.revalidate();
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	public void hide()
	{
		frame.setVisible(false);
	}
}
