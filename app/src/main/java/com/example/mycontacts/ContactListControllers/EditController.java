package com.example.mycontacts.ContactListControllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.example.mycontacts.R;
import com.example.mycontacts.di.MyApplication;
import com.example.mycontacts.models.GetContactsClass;
import com.example.mycontacts.presenter.ContactListPresenter;

import javax.inject.Inject;

public class EditController extends Controller implements ContactListPresenter.Callback {


    private static final int NUMBERLIMIT = 14;
    private EditText name, phoneNo;
    private GetContactsClass contactModel;
    @Inject
    ContactListPresenter presenter;

    void setText(GetContactsClass contactModel) {
        this.contactModel = contactModel;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.edit_controller, container, false);

        MyApplication.getComponent().inject(this);
        presenter.setOnUpdateEditController(this);

        initViews(view);
        onClickUpdateContact(view);

        return view;
    }

    private void onClickUpdateContact(View view) {
        TextView update = view.findViewById(R.id.update_contact);
        update.setOnClickListener(view1 -> {

            if (phoneNo.length() < NUMBERLIMIT) {
                presenter.updateContact(contactModel.getContactId(), name.getText().toString(), phoneNo.getText().toString());

                getRouter().popToRoot();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect Phone No", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initViews(View view) {
        name = view.findViewById(R.id.name_edit);
        phoneNo = view.findViewById(R.id.phone_edit);
        name.setText(contactModel.getContactName());
        phoneNo.setText(contactModel.getContactNumber());
        name.requestFocus();
    }

    @Override
    public void onUpdateContactSuccess(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();

    }
}
