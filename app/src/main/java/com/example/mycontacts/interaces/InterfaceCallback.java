package com.example.mycontacts.interaces;

import com.example.mycontacts.models.GetContactsClass;

import java.util.List;

public interface InterfaceCallback {
    void OnFetchCallback(List<GetContactsClass> getContactsClass);

}

