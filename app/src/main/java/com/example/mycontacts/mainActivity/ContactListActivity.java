package com.example.mycontacts.mainActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.mycontacts.ContactListControllers.ContactListController;
import com.example.mycontacts.R;

public class ContactListActivity extends AppCompatActivity {
    Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup container = findViewById(R.id.main_conductor);

        router = Conductor.attachRouter(this, container, null);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new ContactListController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
