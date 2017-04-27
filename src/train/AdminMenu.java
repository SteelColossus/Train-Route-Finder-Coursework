package train;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdminMenu
{
	private RouteManager manager;
	private InputRouteMenu inputRouteMenu;

	private JDialog frame;
	
	private JPanel buttonPanel;
	
	private JButton inputButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton exitButton;
	
	public AdminMenu(RouteManager rm)
	{
		manager = rm;
		setup();
		
		inputRouteMenu = new InputRouteMenu(rm);
	}
	
	private void setup()
	{
		frame = new JDialog();
		frame.setTitle("Admin Menu");
		frame.setSize(200, 200);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setModal(true);
		
		buttonPanel = new JPanel(new GridLayout(4, 1, 5, 2));
		
		inputButton = new JButton("Input route");
		saveButton = new JButton("Save routes");
		loadButton = new JButton("Retrieve routes");
		exitButton = new JButton("Exit");
		
		inputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				inputRouteMenu.show();
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				boolean success = manager.updateFile();
				
				if (success)
				{
					JOptionPane.showMessageDialog(frame, "The routes were saved successfully.", "Saved successfully", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "There was an error in saving the routes.", "Save was unsuccessful", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				boolean success = manager.updateSystem();
				
				if (success)
				{
					JOptionPane.showMessageDialog(frame, "The current routes were updated successfully.", "Updated successfully", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "There was an error in updating the current routes.", "Update was unsuccessful", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		buttonPanel.add(inputButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(exitButton);
		
		frame.add(buttonPanel);
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
