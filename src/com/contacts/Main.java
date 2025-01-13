package com.contacts;

import com.contacts.DataBase.DataBase;
import com.contacts.model.Contact;
import com.contacts.repository.ContactDAOImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize the database
            DataBase db = DataBase.getDBInstance(2);
            db.connect("contact_management", "localhost", 3306, "root", "bfAhbm123456 /");

            // Print the tables to verify the connection
            //db.printTables();

            // Initialize ContactDAOImpl with the existing database instance
            ContactDAOImpl contactDAO = new ContactDAOImpl(db);

            // Add a new contact
            Contact contact1 = new Contact(9, "saly", "87542700", null);

            contactDAO.deleteContact(contact1);
            //contactDAO.addContact(contact);
            contactDAO.getContacts();
            //System.out.println("Contact added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
