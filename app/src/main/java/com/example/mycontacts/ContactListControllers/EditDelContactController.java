package com.example.mycontacts.ContactListControllers;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.mycontacts.R;
import com.example.mycontacts.di.MyApplication;
import com.example.mycontacts.models.GetContactsClass;
import com.example.mycontacts.presenter.ContactListPresenter;

import javax.inject.Inject;

public class EditDelContactController extends Controller {
    private GetContactsClass contactModel;
    @Inject
    ContactListPresenter presenter;
    @Inject
    EditController editController;

    void setText(final GetContactsClass contactModel) {
        this.contactModel = contactModel;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.edit_del_contact, container, false);

        MyApplication.getComponent().inject(this);

        initViews(view);
        onClickEdit(view);
        onClickDelete(view);
        onClickCall(view);
        return view;
    }

    private void onClickCall(View view) {
        TextView call = view.findViewById(R.id.call_contact);

        call.setOnClickListener(view13 -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactModel.getContactNumber()));
            startActivity(intent);
        });

    }

    private void onClickDelete(View view) {
        TextView delete = view.findViewById(R.id.del_contact);

        delete.findViewById(R.id.del_contact).setOnClickListener(view12 -> {

            presenter.deleteContact(contactModel.getContactId());
            Toast.makeText(getApplicationContext(), "Contact Deleted!", Toast.LENGTH_LONG).show();
            getRouter().popToRoot();
        });

    }

    private void onClickEdit(View view) {
        TextView edit = view.findViewById(R.id.edit_contact);

        edit.setOnClickListener(view1 -> {
            editController.setText(contactModel);
            getRouter().pushController(RouterTransaction.with(editController));
        });
    }

    private void initViews(View view) {

        TextView name = view.findViewById(R.id.name_contact1);
        TextView phoneNo = view.findViewById(R.id.phone_contact1);
        name.setText(contactModel.getContactName());
        phoneNo.setText(contactModel.getContactNumber());
    }
}
