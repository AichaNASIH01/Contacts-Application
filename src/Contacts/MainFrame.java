package Contacts;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    public static void main(String[] args) {
        // Paths for the icons
        String iconPath = "C:\\Users\\Aicha\\Documents\\Projects\\New one\\Contacts\\src\\img.png";
        String labelPath = "C:\\Users\\Aicha\\Documents\\Projects\\New one\\Contacts\\src\\img2.png";

        // Loading icons
        ImageIcon appIcon = new ImageIcon(iconPath);
        JPanel topPanel = getJPanel(labelPath);

        // Main frame configuration
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Contacts");
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));
        mainFrame.setIconImage(appIcon.getImage());

        // Adding the top panel to the main frame
        mainFrame.add(topPanel);

        // Making the frame visible after all components are added
        mainFrame.setVisible(true);
    }

    private static JPanel getJPanel(String labelPath) {
        ImageIcon titleIcon = new ImageIcon(labelPath);

        // Top panel configuration
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(147, 145, 133));
        topPanel.setBounds(0, 0, 500, 50);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Title label configuration
        JLabel title = new JLabel("Contact");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setIcon(titleIcon);
        title.setBounds(0, 0, 50, 10);

        // Adding the title to the top panel
        topPanel.add(title);
        return topPanel;
    }
}
