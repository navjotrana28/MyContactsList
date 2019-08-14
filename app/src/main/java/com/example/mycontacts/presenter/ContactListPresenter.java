package com.example.mycontacts.presenter;

import android.content.Context;

import com.example.mycontacts.ContactListControllers.CreateContactController;
import com.example.mycontacts.ContactListControllers.EditController;
import com.example.mycontacts.database.DBHelper;
import com.example.mycontacts.di.MyApplication;
import com.example.mycontacts.interaces.InterfaceCallback;
import com.example.mycontacts.models.GetContactsClass;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactListPresenter {
    @Inject
    public DBHelper dbHelper;
    private Callback toastDisplayCallBack;

    @Inject
    public ContactListPresenter() {
        MyApplication.getComponent().inject(this);
    }

    public void getAllData(final InterfaceCallback interfaceCallback) {
        dbHelper.getAllData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GetContactsClass>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GetContactsClass> getContactsClasses) {
                        interfaceCallback.OnFetchCallback(getContactsClasses);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void insertData(String name, final String phone) {
        Single.fromCallable(() -> dbHelper.insertContacts(name, phone))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        toastDisplayCallBack.onUpdateContactSuccess("Contact Inserted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    public void updateContact(String id, String name, String phone) {
        Single.fromCallable(() -> dbHelper.updateContacts(id, name, phone))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        toastDisplayCallBack.onUpdateContactSuccess("Contact Updated");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteContact(String id) {
        dbHelper.deleteContact(id);

    }

    public void loadContactsIntoDataBase(Context context) {
        dbHelper.loadContactsIntoDataBase(context);
    }

    public void setOnUpdateCreateContact(CreateContactController toastCallBack) {
        this.toastDisplayCallBack = toastCallBack;
    }

    public void setOnUpdateEditController(EditController toastCallBack) {
        this.toastDisplayCallBack = toastCallBack;
    }

    public interface Callback {
        void onUpdateContactSuccess(String toast);
    }
}