package com.contacts.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {

    private JFrame mainFrame;
    private JTable contactTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        // Paths for the icons (Use relative paths or load as resources)
        String iconPath = "src/img.png";
        String labelPath = "src/img2.png";

        // Loading icons
        ImageIcon appIcon = new ImageIcon(iconPath);

        // Create and configure the main frame
        mainFrame = new JFrame();
        mainFrame.setSize(600, 600);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Contacts");
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));
        mainFrame.setIconImage(appIcon.getImage());

        // Adding the top panel
        JPanel topPanel = createTopPanel(labelPath);
        mainFrame.add(topPanel);

        // Adding the contact form
        JPanel formPanel = createFormPanel();
        mainFrame.add(formPanel);

        // Adding the contact table
        JPanel tablePanel = createTablePanel();
        mainFrame.add(tablePanel);

        // Adding the action buttons
        JPanel buttonPanel = createButtonPanel();
        mainFrame.add(buttonPanel);

        // Making the frame visible after all components are added
        mainFrame.setVisible(true);
    }

    private JPanel createTopPanel(String labelPath) {
        // Load the icon for the title label
        ImageIcon titleIcon = new ImageIcon(labelPath);

        // Configure the top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(147, 145, 133));
        topPanel.setBounds(0, 0, 600, 50);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Configure the title label
        JLabel title = new JLabel("Contact");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setIcon(titleIcon);

        // Add the title label to the top panel
        topPanel.add(title);
        return topPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBounds(20, 60, 560, 100);

        // Add labels and text fields
        formPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        formPanel.add(emailField);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(20, 180, 560, 300);

        // Configure the table
        String[] columnNames = {"ID", "Name", "Phone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        contactTable = new JTable(tableModel);
        contactTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(contactTable);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBounds(20, 500, 560, 50);

        // Add buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to add a contact
                System.out.println("Add button clicked!");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to update a contact
                System.out.println("Update button clicked!");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to delete a contact
                System.out.println("Delete button clicked!");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to view contacts
                System.out.println("View button clicked!");
            }
        });

        return buttonPanel;
    }

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
