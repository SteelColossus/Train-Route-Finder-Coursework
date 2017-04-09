package train;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

public class AdminMenu
{
	@SuppressWarnings("unused")
	private RouteManager manager;

	private JDialog frame;
	
	private JButton inputButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton exitButton;
	
	public AdminMenu(RouteManager rm)
	{
		this.manager = rm;
		this.setup();
	}
	
	private void setup()
	{
		frame = new JDialog();
		frame.setTitle("Admin Menu");
		frame.setSize(200, 200);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		inputButton = new JButton("Input route");
		saveButton = new JButton("Save route");
		loadButton = new JButton("Retrieve route");
		exitButton = new JButton("Exit");
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		frame.add(inputButton);
		frame.add(saveButton);
		frame.add(loadButton);
		frame.add(exitButton);
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
