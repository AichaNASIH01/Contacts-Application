import javax.swing.*;
import java.awt.*;

public class MainFrame {

    private JFrame mainFrame;

    public MainFrame() {
        // Paths for the icons (Use relative paths or load as resources)
        String iconPath = "src/img.png";
        String labelPath = "src/img2.png";

        // Loading icons
        ImageIcon appIcon = new ImageIcon(iconPath);

        // Create and configure the main frame
        mainFrame = new JFrame();
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Contacts");
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));
        mainFrame.setIconImage(appIcon.getImage());

        // Adding the top panel
        JPanel topPanel = createTopPanel(labelPath);
        mainFrame.add(topPanel);

        // Making the frame visible after all components are added
        mainFrame.setVisible(true);
    }

    private JPanel createTopPanel(String labelPath) {
        // Load the icon for the title label
        ImageIcon titleIcon = new ImageIcon(labelPath);

        // Configure the top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(147, 145, 133));
        topPanel.setBounds(0, 0, 500, 50);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Configure the title label
        JLabel title = new JLabel("Contact");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setIcon(titleIcon);

        // Add the title label to the top panel
        topPanel.add(title);
        return topPanel;
    }

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
