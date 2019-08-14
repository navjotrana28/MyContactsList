package com.example.mycontacts.di;

import android.content.Context;

import com.example.mycontacts.ContactListControllers.EditController;
import com.example.mycontacts.ContactListControllers.EditDelContactController;
import com.example.mycontacts.database.DBHelper;
import com.example.mycontacts.presenter.ContactListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
class ModuleClass {

    private Context context;

    ModuleClass(Context context) {
        this.context = context;
    }

    @Provides
    ContactListPresenter providePresenter() {

        return new ContactListPresenter();
    }

    @Provides
    DBHelper provideContactDatabase() {
        return new DBHelper(context);
    }

    @Provides
    EditController provideEditController() {
        return new EditController();
    }

    @Provides
    EditDelContactController provideEditDelContactController() {
        return new EditDelContactController();
    }
}
