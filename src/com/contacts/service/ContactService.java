package com.contacts.service;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;

import java.util.List;

public class ContactService {
    private ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }

    public List<Contact> getAllContacts() {
        return contactDAO.getContacts();
    }

    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

    public void deleteContact(Contact contact) {
        contactDAO.deleteContact(contact);
    }

    public List<Contact> searchContacts(String keyword) {
        return contactDAO.searchContacts(keyword);
    }
}