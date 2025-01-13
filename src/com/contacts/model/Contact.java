package com.contacts.model;

public class Contact {
    private static int serial = 1;
    private int id;
    private String Name;
    private String phoneNumber;
    private String email;
    public Contact(int id, String name, String phoneNumber, String email) {
        this.id = serial++;
        this.Name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = serial++;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

}

