package com.example.mycontacts.di;

import com.example.mycontacts.ContactListControllers.ContactListController;
import com.example.mycontacts.ContactListControllers.CreateContactController;
import com.example.mycontacts.ContactListControllers.EditController;
import com.example.mycontacts.ContactListControllers.EditDelContactController;
import com.example.mycontacts.presenter.ContactListPresenter;

import dagger.Component;

@Component(modules = {ModuleClass.class})
public interface ContactsGraph {


    void inject(ContactListPresenter presenter);

    void inject(ContactListController contactListController);

    void inject(EditController editController);

    void inject(CreateContactController createContactController);

    void inject(EditDelContactController editDelContactController);


}
