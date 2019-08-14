package com.example.mycontacts.models;

public class GetContactsClass implements Comparable {

    private String contactId, contactName, contactNumber;

    public GetContactsClass() {
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public int compareTo(Object o) {
        return this.getContactName().compareTo(((GetContactsClass) o).getContactName());

    }
}

