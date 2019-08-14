package com.example.mycontacts.ContactListControllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.example.mycontacts.R;
import com.example.mycontacts.di.MyApplication;
import com.example.mycontacts.presenter.ContactListPresenter;

import javax.inject.Inject;

public class CreateContactController extends Controller implements ContactListPresenter.Callback {
    private static final int CONTACTLIMIT = 13;
    private EditText name = null;
    private EditText phoneNo = null;
    @Inject
    ContactListPresenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        View view = inflater.inflate(R.layout.detail_list_contact, container, false);

        MyApplication.getComponent().inject(this);
        presenter.setOnUpdateCreateContact(this);
        initView(view);
        initListener(view);
        return view;
    }

    private void initView(View view) {
        name = view.findViewById(R.id.name_contact);
        name.requestFocus();
        phoneNo = view.findViewById(R.id.phone_contact);
    }

    private void initListener(View view) {
        view.findViewById(R.id.save_contact).setOnClickListener(btnView -> {
            handleSaveBtnClick();
        });
    }

    private void handleSaveBtnClick() {
        String name1 = name.getText().toString();
        String phone1 = phoneNo.getText().toString();

        if (!name1.equals(" ") && !phone1.equals("")) {
            if (phone1.length() < CONTACTLIMIT) {
                presenter.insertData( name1, phone1);
                getRouter().popToRoot();
            } else {
                Toast.makeText(getActivity(), "Enter correct Phone NO!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "invalid Content", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdateContactSuccess(String toast) {
        Toast.makeText(getApplicationContext(),toast, Toast.LENGTH_SHORT).show();
    }
}
