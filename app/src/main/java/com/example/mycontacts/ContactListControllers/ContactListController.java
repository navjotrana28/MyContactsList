package com.example.mycontacts.ContactListControllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.mycontacts.ContactListAdapter.MyContactAdapter;
import com.example.mycontacts.R;
import com.example.mycontacts.di.MyApplication;
import com.example.mycontacts.interaces.AdapterClicklistener;
import com.example.mycontacts.models.GetContactsClass;
import com.example.mycontacts.presenter.ContactListPresenter;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ContactListController extends Controller implements AdapterClicklistener {
    private RecyclerView recyclerView;
    @Inject
    ContactListPresenter presenter;
    @Inject
    EditDelContactController editDelContactController;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        View view = inflater.inflate(R.layout.list_of_contacts, container, false);
        MyApplication.getComponent().inject(this);
        TextView addContact = view.findViewById(R.id.addContact);
        recyclerView = view.findViewById(R.id.recycler_view_list);

        requestForContactPermission();
        permissionCheck();
        onClickAddButton(addContact);

        return view;
    }

    private void onClickAddButton(TextView addcontact) {
        addcontact.setOnClickListener(view1 -> getRouter().pushController(RouterTransaction.with(new CreateContactController())));

    }

    private void permissionCheck() {
        int permissionCheck = ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            presenter.getAllData(this::setUpAdapter);

        } else {
            Toast.makeText(getApplicationContext(), "Give permission to access contacts", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpAdapter(List<GetContactsClass> getContactsClass) {

        MyContactAdapter myContactAdapter = new MyContactAdapter(getApplicationContext(), getContactsClass, this);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myContactAdapter);
        myContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(GetContactsClass contactModel) {

        Log.d("poss", String.valueOf(contactModel.getContactNumber()));

        editDelContactController.setText(contactModel);
        getRouter().pushController(RouterTransaction.with(editDelContactController));
    }


    private void requestForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  ContactListPresenter presenter = new ContactListPresenter();
                presenter.loadContactsIntoDataBase(getApplicationContext());
                presenter.getAllData(this::setUpAdapter);
            } else {
                Toast.makeText(getApplicationContext(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
