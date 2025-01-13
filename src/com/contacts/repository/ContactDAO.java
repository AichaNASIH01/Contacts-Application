package com.contacts.repository;

import com.contacts.model.Contact;
import java.util.List;

public interface ContactDAO {
    void addContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(Contact contact);
    List<Contact> getContacts();
    List<Contact> searchContacts(String keyword);
    Contact getContactById(int id); // Add this method
}