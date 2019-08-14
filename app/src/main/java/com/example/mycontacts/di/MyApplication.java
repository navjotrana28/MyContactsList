package com.example.mycontacts.di;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {

    private static ContactsGraph applicationComponent;


    public static ContactsGraph getComponent() {
        return applicationComponent;
    }

    public MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerContactsGraph
                .builder()
                .moduleClass(new ModuleClass(this))
                .build();
    }
}
