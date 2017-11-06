package train;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * The menu that is used for inputting and deleting routes in the system.
 * 
 * @author SteelColossus
 */
public class InputRouteMenu
{
    private RouteManager manager;

    private JDialog frame;

    private JPanel stopsPanel;
    private JPanel endPanel;
    private JPanel forceMinSizePanel;

    private JComboBox<String> fromBox;
    private JComboBox<String> toBox;

    private JButton exitButton;

    private ArrayList<JLabel> existingStops;

    private JPanel newStopPanel;
    private JTextField newStop;
    private JButton addButton;

    /**
     * Constructor taking a route manager.
     * 
     * @param rm a route manager which stores all the routes managed by this
     *            system
     */
    public InputRouteMenu(RouteManager rm)
    {
        manager = rm;
        existingStops = new ArrayList<JLabel>();

        defaultSetup();
    }

    /**
     * Sets up the frame to display.
     */
    private void defaultSetup()
    {
        frame = new JDialog();
        frame.setTitle("Input Route");
        frame.setSize(200, 200);
        frame.setMinimumSize(new Dimension(200, 70));
        frame.setLayout(new BorderLayout(5, 5));
        frame.getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setModal(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width / 2) - (frame.getSize().width / 2),
                          (screenSize.height / 2) - (frame.getSize().height / 2));

        stopsPanel = new JPanel();
        stopsPanel.setLayout(new BoxLayout(stopsPanel, BoxLayout.Y_AXIS));

        endPanel = new JPanel();
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));

        forceMinSizePanel = new JPanel();

        ArrayList<String> stationNameList = new ArrayList<String>();

        for (int i = 0; i < manager.getNumStations(); i++)
        {
            Station s = manager.getStation(i);

            if (s.isMain() && !stationNameList.contains(s))
            {
                stationNameList.add(s.getName());
            }
        }

        fromBox = new JComboBox<String>(stationNameList.toArray(new String[0]));
        toBox = new JComboBox<String>(stationNameList.toArray(new String[0]));

        fromBox.setSelectedIndex(-1);
        toBox.setSelectedIndex(-1);

        exitButton = new JButton("Exit");

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

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        forceMinSizePanel.add(exitButton);

        endPanel.add(toBox);
        endPanel.add(forceMinSizePanel);

        frame.add(fromBox, BorderLayout.PAGE_START);
        frame.add(stopsPanel, BorderLayout.CENTER);
        frame.add(endPanel, BorderLayout.PAGE_END);

        updateStops();
    }

    /**
     * Adds a new stop to the system.
     */
    private void addNewStop()
    {
        String newStopText = newStop.getText();

        if (newStopText != null && newStopText.matches("[A-Za-z][A-Za-z&\\- ]*"))
        {
            Station s = manager.addStation(newStopText, false);
            Route r = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
            manager.addRouteStop(r, s);
            updateStops();
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "Please enter a valid name for this station.", "Invalid station name",
                                          JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Removes an existing stop from the system.
     * 
     * @param stopNum the number of the stop to remove
     */
    private void removeStop(int stopNum)
    {
        Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(), toBox.getSelectedItem().toString());
        currentRoute.removeStation(stopNum + 1);
        updateStops();
    }

    /**
     * Updates the GUI to show all the stops for this route.
     */
    public void updateStops()
    {
        existingStops.clear();
        stopsPanel.removeAll();

        if (fromBox.getSelectedIndex() > -1 && toBox.getSelectedIndex() > -1)
        {
            Route currentRoute = manager.getRoute(fromBox.getSelectedItem().toString(),
                                                  toBox.getSelectedItem().toString());

            if (currentRoute != null)
            {
                for (int i = 0; i < currentRoute.getNumStops(); i++)
                {
                    JPanel currentStopPanel = new JPanel();
                    JLabel currentStop = new JLabel();
                    JButton deleteButton = new JButton("-");

                    deleteButton.setPreferredSize(new Dimension(deleteButton.getPreferredSize().width, 25));
                    currentStop.setText(currentRoute.getStop(i).getName());

                    final int stopNum = i;

                    deleteButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e)
                        {
                            removeStop(stopNum);
                        }
                    });

                    existingStops.add(currentStop);

                    currentStopPanel.add(currentStop);
                    currentStopPanel.add(deleteButton);

                    stopsPanel.add(currentStopPanel);
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
        }

        frame.pack();
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
