package com.contacts.view;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.DataBase.DataBase;

import javax.swing.*;
import javax.swing.border.Border; // Import the Border class
import java.awt.*;

public class AddContactFrame {
    private JFrame frame;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private ContactDAO contactDAO;

    public AddContactFrame() {
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));

        frame = new JFrame("Add Contact");
        frame.setSize(300, 200); // Smaller frame size
        frame.setLayout(new GridLayout(4, 2, 5, 5)); // Add padding between rows/columns
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a custom rounded border
        Border roundedBorder = BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true);

        frame.add(new JLabel("Name:"));
        nameField = new JTextField();
        nameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Smaller font
        nameField.setPreferredSize(new Dimension(80, 20)); // Smaller size
        nameField.setBorder(roundedBorder);
        frame.add(nameField);

        frame.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        phoneField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Smaller font
        phoneField.setPreferredSize(new Dimension(80, 20)); // Smaller size
        phoneField.setBorder(roundedBorder);
        frame.add(phoneField);

        frame.add(new JLabel("Email:"));
        emailField = new JTextField();
        emailField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Smaller font
        emailField.setPreferredSize(new Dimension(80, 20)); // Smaller size
        emailField.setBorder(roundedBorder);
        frame.add(emailField);

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9)); // Smaller font
        addButton.setForeground(new Color(47, 54, 69));
        addButton.setPreferredSize(new Dimension(80, 25)); // Smaller size
        addButton.setBorder(roundedBorder);

        addButton.addActionListener(e -> addContact());
        frame.add(addButton);

        frame.setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        Contact contact = new Contact(name, phone, email);
        contactDAO.addContact(contact);
        JOptionPane.showMessageDialog(frame, "Contact added successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddContactFrame::new);
    }
}
