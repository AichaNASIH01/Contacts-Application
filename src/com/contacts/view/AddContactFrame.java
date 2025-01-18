package com.contacts.view;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.DataBase.DataBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * This class represents a frame for adding new contacts.
 * It provides a graphical user interface for users to input contact details.
 */
public class AddContactFrame {
    private JFrame frame;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private ContactDAO contactDAO;

    /**
     * Constructor for AddContactFrame.
     * Initializes the frame and sets up the layout, components, and event handling.
     */
    public AddContactFrame() {
        // Initialize the DAO with the database instance
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));

        // Create the frame
        frame = new JFrame("Add Contact");
        frame.setSize(300, 200); // Set frame dimensions
        frame.setLayout(new GridLayout(4, 2, 5, 5)); // Use a grid layout with padding
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame on exit

        // Create a custom rounded border
        Border roundedBorder = BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true);

        // Add Name label and input field
        frame.add(new JLabel("Name:"));
        nameField = new JTextField();
        nameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Adjust font size
        nameField.setPreferredSize(new Dimension(80, 20)); // Adjust field size
        nameField.setBorder(roundedBorder);
        frame.add(nameField);

        // Add Phone Number label and input field
        frame.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        phoneField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Adjust font size
        phoneField.setPreferredSize(new Dimension(80, 20)); // Adjust field size
        phoneField.setBorder(roundedBorder);
        frame.add(phoneField);

        // Add Email label and input field
        frame.add(new JLabel("Email:"));
        emailField = new JTextField();
        emailField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Adjust font size
        emailField.setPreferredSize(new Dimension(80, 20)); // Adjust field size
        emailField.setBorder(roundedBorder);
        frame.add(emailField);

        // Add "Add" button
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Adjust font size
        addButton.setBackground(new Color(230, 185, 166));
        addButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        addButton.setBorder(BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true));
        addButton.setForeground(new Color(47, 54, 69)); // Set text color
        addButton.setPreferredSize(new Dimension(70, 20)); // Adjust button size
        addButton.setBorder(roundedBorder);

        // Set action listener for the "Add" button
        addButton.addActionListener(e -> addContact());
        frame.add(addButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
     * Handles adding a new contact.
     * Collects input from the text fields, creates a Contact object,
     * and passes it to the DAO for storage.
     */
    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        // Create a Contact object with user-provided details
        Contact contact = new Contact(name, phone, email);

        // Add the contact to the database
        contactDAO.addContact(contact);

        // Show confirmation message
        JOptionPane.showMessageDialog(frame, "Contact added successfully!");
    }
}
