package com.contacts;

import com.contacts.DataBase.DataBase;
import com.contacts.model.Contact;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.view.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize the database
            DataBase db = DataBase.getDBInstance(2);
            db.connect("contact_management", "localhost", 3306, "root", "bfAhbm123456 /");

            // Print the tables to verify the connection
            db.printTables();
            SwingUtilities.invokeLater(() -> new MainFrame());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}