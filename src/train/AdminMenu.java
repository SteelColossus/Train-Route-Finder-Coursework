package train;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The admin menu that is used for administrator functions.
 * 
 * @author Michael
 */
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

    private JFileChooser fileChooser;

    /**
     * Constructor taking a route manager.
     * 
     * @param rm a route manager which stores all the routes managed by this
     *            system
     */
    public AdminMenu(RouteManager rm)
    {
        manager = rm;
        defaultSetup();

        inputRouteMenu = new InputRouteMenu(rm);
    }

    /**
     * Sets up the file chooser and frame to display.
     */
    private void defaultSetup()
    {
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Train Route Manager File (*.trm)", "trm"));
        fileChooser.setSelectedFile(new File("routes.trm"));

        frame = new JDialog();
        frame.setTitle("Admin Menu");
        frame.setSize(200, 200);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setModal(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width / 2) - (frame.getSize().width / 2),
                          (screenSize.height / 2) - (frame.getSize().height / 2));

        buttonPanel = new JPanel(new GridLayout(4, 1, 5, 2));
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

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
                int res = fileChooser.showSaveDialog(frame);

                if (res == JFileChooser.APPROVE_OPTION)
                {
                    String fileName = fileChooser.getSelectedFile().getName();
                    String path = fileChooser.getSelectedFile().getAbsolutePath();

                    if (new File(path).isFile())
                    {
                        int contRes = JOptionPane.showConfirmDialog(frame,
                                                                    "The file name that you are attempting to save the file as already exists. Would you like to overwrite the existing file?",
                                                                    "File already exists", JOptionPane.YES_NO_OPTION);

                        if (contRes != JOptionPane.YES_OPTION) return;
                    }

                    if (!fileName.contains("."))
                    {
                        path += ".trm";
                    }
                    else if (!fileName.contains(".trm"))
                    {
                        int contRes = JOptionPane.showConfirmDialog(frame,
                                                                    "The file type that you are attempting to save the file as appears to be unsupported. Are you sure you wish to continue?",
                                                                    "Unsupported file type", JOptionPane.YES_NO_OPTION);

                        if (contRes != JOptionPane.YES_OPTION) return;
                    }

                    boolean success = manager.updateFile(path);

                    if (success)
                    {
                        JOptionPane.showMessageDialog(frame, "The routes were saved successfully.",
                                                      "Saved successfully", JOptionPane.INFORMATION_MESSAGE);
                        inputRouteMenu.updateStops();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "There was an error in saving the routes.",
                                                      "Save was unsuccessful", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int res = fileChooser.showOpenDialog(frame);

                if (res == JFileChooser.APPROVE_OPTION)
                {
                    if (fileChooser.getSelectedFile().exists())
                    {
                        String fileName = fileChooser.getSelectedFile().getName();
                        String path = fileChooser.getSelectedFile().getAbsolutePath();

                        if (!fileName.contains(".trm"))
                        {
                            int contRes = JOptionPane.showConfirmDialog(frame,
                                                                        "The file type of the file that you are attempting to open appears to be unsupported. Are you sure you wish to continue?",
                                                                        "Unsupported file type",
                                                                        JOptionPane.YES_NO_OPTION);

                            if (contRes != JOptionPane.YES_OPTION) return;
                        }

                        boolean success = manager.updateSystemFromFile(path);

                        if (success)
                        {
                            JOptionPane.showMessageDialog(frame, "The current routes were updated successfully.",
                                                          "Updated successfully", JOptionPane.INFORMATION_MESSAGE);
                            inputRouteMenu.updateStops();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "There was an error in updating the current routes.",
                                                          "Update was unsuccessful", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "The selected file does not exist.",
                                                      "File opening was unsuccessful", JOptionPane.WARNING_MESSAGE);
                    }
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
