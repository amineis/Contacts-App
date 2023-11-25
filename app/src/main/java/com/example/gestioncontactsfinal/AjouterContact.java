package com.example.gestioncontactsfinal;



import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncontactsfinal.Model.Contact;
import com.example.gestioncontactsfinal.dao.ContactsContract;
import com.example.gestioncontactsfinal.dao.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AjouterContact extends AppCompatActivity {
    private EditText editNom;
    private EditText editPrenom;
    private EditText editEmail;
    private EditText editTel;
    private EditText editService;
    private Button buttonAdd;
    private DBHelper dbHelper;
    private ImageView prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_contact);
        dbHelper = new DBHelper(this);
        editNom = findViewById(R.id.editNom);
        editPrenom = findViewById(R.id.editPrenom);
        editEmail = findViewById(R.id.editEmail);
        editTel = findViewById(R.id.editTel);
        editService = findViewById(R.id.editService);
        buttonAdd = findViewById(R.id.buttonAdd);

        prev = findViewById(R.id.back);
        prev.setOnClickListener(view -> finish());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editNom.getText().toString().trim();
                String prenom = editPrenom.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String tel = editTel.getText().toString().trim();
                String service = editService.getText().toString().trim();
                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty() || service.isEmpty()) {
                    Toast.makeText(AjouterContact.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Contact newContact = new Contact(nom, prenom, service, email, tel);
                    long contactId = addContact(newContact);



                    if (contactId != -1) {


                        Toast.makeText(AjouterContact.this, "Contact added successfully", Toast.LENGTH_SHORT).show();

                        editNom.setText("");
                        editPrenom.setText("");
                        editEmail.setText("");
                        editTel.setText("");
                        editService.setText("");
                        finish();
                    } else {
                        Toast.makeText(AjouterContact.this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private long addContact(Contact c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contact.COLUMN_NAME_NOM, c.getNom());
        values.put(ContactsContract.Contact.COLUMN_NAME_PRENOM, c.getPrenom());
        values.put(ContactsContract.Contact.COLUMN_NAME_SERVICE, c.getService());
        values.put(ContactsContract.Contact.COLUMN_NAME_EMAIL, c.getEmail());
        values.put(ContactsContract.Contact.COLUMN_NAME_TEL, c.getTel());
        long newRowId = db.insert(ContactsContract.Contact.TABLE_NAME, null, values);
        Log.i("Contacts","Values inserted successfully !");
        return newRowId;
    }
}
