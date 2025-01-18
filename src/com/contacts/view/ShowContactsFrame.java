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

    public ShowContactsFrame(String contactPhone) {
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));
        Contact contact = contactDAO.getContactByPhone(contactPhone);

        // Check if the contact is null to prevent NullPointerException
        if (contact == null) {
            JOptionPane.showMessageDialog(null, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create and configure the frame
        frame = new JFrame("Contact Details");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add contact details to the frame
        frame.add(new JLabel("Name:"));
        frame.add(new JLabel(contact.getName()));

        frame.add(new JLabel("Phone Number:"));
        frame.add(new JLabel(contact.getPhoneNumber()));

        frame.add(new JLabel("Email:"));
        frame.add(new JLabel(contact.getEmail()));

        // Add the bottom panel with the delete button
        JPanel bottomPanel = createBottomPanel(contact);
        frame.add(bottomPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }

    private JPanel createBottomPanel(Contact contact) {
        JPanel bottomPanel = new JPanel();

        JButton deleteContactButton = new JButton("Delete Contact");
        deleteContactButton .setBackground(new Color(230, 185, 166));
        deleteContactButton.setForeground(new Color(47, 54, 69));
        deleteContactButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));

        deleteContactButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete this contact?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                contactDAO.deleteContact(contact);
                JOptionPane.showMessageDialog(frame, "Contact deleted successfully.");
                frame.dispose(); // Close the frame after deletion
            }
        });

        bottomPanel.add(deleteContactButton);
        return bottomPanel;
    }

}
