package com.example.mycontacts.database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.mycontacts.models.GetContactsClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String CONTACTS = "contacts";

    public DBHelper(Context context) {
        super(context, "DataBases.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table contacts " + "(id integer primary key AUTOINCREMENT NOT NULL, name text,phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(sqLiteDatabase);
    }

    public Long insertContacts(String contactName, String contactPhone) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, contactName);
        contentValues.put(PHONE, contactPhone);
        return db.insert(CONTACTS, null, contentValues);

    }

    @SuppressLint("Recycle")
    public Observable<List<GetContactsClass>> getAllData() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts", null);
        List<GetContactsClass> contactList = new ArrayList();

        while (res.moveToNext()) {
            String id = res.getString(0);
            String name = res.getString(1);
            String phoneNo = res.getString(2);
            GetContactsClass contactListItem = new GetContactsClass();
            contactListItem.setContactId(id);
            contactListItem.setContactName(name);
            contactListItem.setContactNumber(phoneNo);
            contactList.add(contactListItem);
        }
        Collections.sort(contactList);
        return Observable.just(contactList);

    }


    public Integer updateContacts(String id, String contactsName, String contactPhone) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, contactsName);
        contentValues.put(PHONE, contactPhone);
        return db.update(CONTACTS, contentValues, "id = ? ", new String[]{id});
    }

    public void deleteContact(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS, "id = ?", new String[]{id});

    }

    public void loadContactsIntoDataBase(Context context) {
        ContentResolver cr = context.getContentResolver();
        DBHelper dbHelper = new DBHelper(context);
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (Objects.requireNonNull(pCur).moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(NAME, "Name: " + name);
                        Log.i(PHONE, "Phone Number: " + phoneNo);
                        dbHelper.insertContacts(name, phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }
}
