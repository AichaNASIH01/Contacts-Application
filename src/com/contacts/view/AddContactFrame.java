package com.contacts.view;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.DataBase.DataBase;

import javax.swing.*;
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
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.add(new JLabel("Name:"));
        nameField.setText("Name");
        nameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        frame.add(nameField);


        frame.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        frame.add(phoneField);

        frame.add(new JLabel("Email:"));
        emailField = new JTextField();
        frame.add(emailField);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(230, 185, 166));
        addButton.setForeground(new Color(47, 54, 69));
        addButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
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