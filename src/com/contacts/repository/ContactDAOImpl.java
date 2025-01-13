package com.contacts.repository;
import com.contacts.model.Contact;
import com.contacts.DataBase.DataBase;

import java.sql.*;
import java.util.*;

public class ContactDAOImpl implements ContactDAO {
    private final DataBase db;

    public ContactDAOImpl(DataBase db) {
        this.db = db;
    }

    @Override
    public void addContact(Contact contact) {
        String table = "contacts";
        String[] columns = {"id", "name", "email", "phoneNumber"};
        String[] values = {
                String.valueOf(contact.getId()), // Convert int to String
                contact.getName(),
                contact.getEmail(),
                contact.getPhoneNumber()
        };


        db.insert(table, columns, values);
    }

    @Override
    public void updateContact(Contact contact) {
        String table = "contacts";
        String[] columns = {"id", "name", "email", "phoneNumber"};
        String[] values = {
                String.valueOf(contact.getId()), // Convert int to String
                contact.getName(),
                contact.getEmail(),
                contact.getPhoneNumber()
        };
        String condition = "id = " + contact.getId();
        db.update(table,columns,values,condition);
    }

    @Override
    public void deleteContact(Contact contact) {
        String table = "contacts";
        String condition = "id = " + contact.getId();
        db.delete(table,condition);
    }
    public List<Contact> searchContacts(String keyword) {
        String query = "SELECT id, name, email, phoneNumber FROM contacts WHERE " +
                "name LIKE ? OR email LIKE ? OR phoneNumber LIKE ?";
        List<Contact> contacts = new ArrayList<>();
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public List<Contact> getContacts() {
        String query = "SELECT id, name, email, phoneNumber FROM contacts";
        List<Contact> contacts = new ArrayList<>();

        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery(query);
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
