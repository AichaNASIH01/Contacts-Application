package com.contacts.repository;
import com.contacts.model.Contact;
import java.util.List;

public interface ContactDAO {
    void addContact(Contact contact);
    void updateContact(Contact contact);
    List<Contact> searchContacts(String keyword);
    void deleteContact(Contact contact);
    List<Contact> getContacts();
}
