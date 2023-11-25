package com.example.gestioncontactsfinal.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gestioncontactsfinal.Model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DAOContact {
    private Context ctx;
    private static SQLiteDatabase mDB;

    public DAOContact(Context ctx) {
        this.ctx = ctx;
        mDB = new DBHelper(ctx).getWritableDatabase();
        mDB = new DBHelper(ctx).getReadableDatabase();
    }

    public static Contact getContact(String Email){
        String selection = ContactsContract.Contact.COLUMN_NAME_EMAIL + "=?";
        String[] selectionArgs = { Email };

        Cursor cursor = mDB.query(
                ContactsContract.Contact.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Contact contact = null;

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_NOM));
            @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_PRENOM));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_EMAIL));
            @SuppressLint("Range") String service = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_SERVICE));
            @SuppressLint("Range") String tel = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_TEL));


            contact = new Contact(nom, prenom, service, email, tel);

            cursor.close();
        }

        return contact;
    }
    public static List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = mDB.query(ContactsContract.Contact.TABLE_NAME, null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_EMAIL));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_NOM));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_PRENOM));
                @SuppressLint("Range") String service = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_SERVICE));
                @SuppressLint("Range") String tel = cursor.getString(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_TEL));
                @SuppressLint("Range") boolean fav = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contact.COLUMN_NAME_FAVORITE)) > 0;
                Contact contact = new Contact(nom, prenom, service, email, tel, fav);
                contactList.add(contact);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return contactList;
    }
    public static void deleteContact(Contact contact) {

        String selection = ContactsContract.Contact.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(contact.getEmail())};

        long delete= mDB.delete(ContactsContract.Contact.TABLE_NAME, selection, selectionArgs);

    }
    public static boolean updateContactByEmail(String email, Contact updatedContact) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contact.COLUMN_NAME_NOM, updatedContact.getNom());
        values.put(ContactsContract.Contact.COLUMN_NAME_PRENOM, updatedContact.getPrenom());
        values.put(ContactsContract.Contact.COLUMN_NAME_EMAIL, updatedContact.getEmail());
        values.put(ContactsContract.Contact.COLUMN_NAME_SERVICE, updatedContact.getService());
        values.put(ContactsContract.Contact.COLUMN_NAME_TEL, updatedContact.getTel());

        String selection = ContactsContract.Contact.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        int rowsAffected = mDB.update(
                ContactsContract.Contact.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        return rowsAffected > 0;
    }
    public static void updateFavoriteState(String email, boolean isFavorite) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contact.COLUMN_NAME_FAVORITE, isFavorite ? 1 : 0);

        String selection = ContactsContract.Contact.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { String.valueOf(email) };

        mDB.update(ContactsContract.Contact.TABLE_NAME, values, selection, selectionArgs);

    }


}
