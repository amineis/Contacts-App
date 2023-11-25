package com.example.gestioncontactsfinal.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gestioncontactsfinal.Model.Contact;
import com.example.gestioncontactsfinal.Model.User;

public class DBHelper extends SQLiteOpenHelper {
    private final String SQL_CREATE_CONTACT = "CREATE TABLE " + ContactsContract.Contact.TABLE_NAME + " (" +
            ContactsContract.Contact.COLUMN_NAME_NOM + " TEXT NOT NULL," +
            ContactsContract.Contact.COLUMN_NAME_PRENOM + " TEXT NOT NULL," +
            ContactsContract.Contact.COLUMN_NAME_SERVICE + " TEXT NOT NULL," +
            ContactsContract.Contact.COLUMN_NAME_EMAIL + " TEXT NOT NULL," +
            ContactsContract.Contact.COLUMN_NAME_FAVORITE + " INTEGER DEFAULT 0," +
            ContactsContract.Contact.COLUMN_NAME_TEL + " TEXT NOT NULL)" ;

    private final String SQL_CREATE_USER = "CREATE TABLE " + ContactsContract.User.TABLE_NAME + " (" +
            ContactsContract.User.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL," +
            ContactsContract.User.COLUMN_NAME_LASTNAME + " TEXT NOT NULL," +
            ContactsContract.User.COLUMN_NAME_PASSWORD + " TEXT NOT NULL," +
            ContactsContract.User.COLUMN_NAME_EMAIL + " TEXT NOT NULL," +
            ContactsContract.User.COLUMN_NAME_BIRTH + " TEXT NOT NULL)";

    Context context;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase MyDB) {
        Log.i("Contacts","Création de la BD Contact...");
        MyDB.execSQL(SQL_CREATE_CONTACT);
        MyDB.execSQL(SQL_CREATE_USER);
        Log.i("Contacts","Contact crée !");

    }
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        Log.i("Contacts","mise a jour des BD Contact...");
        MyDB.execSQL("drop table Contact");
        MyDB.execSQL("drop table User");
        onCreate(MyDB);
        Log.i("Contacts","mise a jour succés !");
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public Boolean insertData(String firstname, String lastname, String birth, String email, String pass){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName",firstname);
        contentValues.put("LastName",lastname);
        contentValues.put("BirthDate",birth);
        contentValues.put("Email",email);
        contentValues.put("Password",pass);
        long result = MyDB.insert("User",null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean Login(String email, String pass){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from User where Email = ? AND Password = ?",new String[]{email,pass});
        if(cursor.getCount()>0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] projection = {
                ContactsContract.User.COLUMN_NAME_FIRSTNAME,
                ContactsContract.User.COLUMN_NAME_LASTNAME,
                ContactsContract.User.COLUMN_NAME_BIRTH,
                ContactsContract.User.COLUMN_NAME_EMAIL
        };

        String selection = ContactsContract.User.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                ContactsContract.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int firstNameColumnIndex = cursor.getColumnIndexOrThrow(ContactsContract.User.COLUMN_NAME_FIRSTNAME);
            int lastNameColumnIndex = cursor.getColumnIndexOrThrow(ContactsContract.User.COLUMN_NAME_LASTNAME);
            int birthColumnIndex = cursor.getColumnIndexOrThrow(ContactsContract.User.COLUMN_NAME_BIRTH);

            String firstName = cursor.getString(firstNameColumnIndex);
            String lastName = cursor.getString(lastNameColumnIndex);
            String birth = cursor.getString(birthColumnIndex);

            user = new User(firstName, lastName, birth, email);
        }

        if (cursor != null) {
            cursor.close();
        }

        return user;
    }


}
