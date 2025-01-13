package com.contacts.view;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.DataBase.DataBase;

import javax.swing.*;
import java.awt.*;

public class ShowContactsFrame {
    private JFrame frame;
    private ContactDAO contactDAO;

    public ShowContactsFrame(int contactId) {
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));
        Contact contact = contactDAO.getContactById(contactId);

        frame = new JFrame("Contact Details");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.add(new JLabel("Name:"));
        frame.add(new JLabel(contact.getName()));

        frame.add(new JLabel("Phone Number:"));
        frame.add(new JLabel(contact.getPhoneNumber()));

        frame.add(new JLabel("Email:"));
        frame.add(new JLabel(contact.getEmail()));

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // This is just an example to show how to open the frame
        SwingUtilities.invokeLater(() -> new ShowContactsFrame(1));
    }
}