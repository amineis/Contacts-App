package com.example.gestioncontactsfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncontactsfinal.Model.Contact;
import com.example.gestioncontactsfinal.dao.DAOContact;

public class UpdateContact extends AppCompatActivity {
    private EditText editNom;
    private EditText editPrenom;
    private ImageView prev;
    private EditText editTel;
    private EditText editEmail;
    private EditText editService;
    private Button updateButton;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        Bundle extras = getIntent().getExtras();

        editNom = findViewById(R.id.editNom);
        editEmail = findViewById(R.id.editEmail);
        editTel = findViewById(R.id.editTel);
        editPrenom = findViewById(R.id.editPrenom);
        editService = findViewById(R.id.editService);
        updateButton = findViewById(R.id.update);
        prev = findViewById(R.id.back);
        prev.setOnClickListener(view -> finish());

        if (extras != null) {
            String Nom = extras.getString("nom");
            String Prenom = extras.getString("prenom");
            String Email = extras.getString("email");
            String Service = extras.getString("service");
            String Tel = extras.getString("tel");
            editNom.setText(Nom);
            editEmail.setText(Email);
            editTel.setText(Tel);
            editPrenom.setText(Prenom);
            editService.setText(Service);
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedNom = editNom.getText().toString().trim();
                String updatedEmail = editEmail.getText().toString().trim();
                String updatedTel = editTel.getText().toString().trim();
                String updatedPrenom = editPrenom.getText().toString().trim();
                String updatedService = editService.getText().toString().trim();

                Contact c = new Contact(updatedNom, updatedPrenom, updatedService, updatedEmail, updatedTel);
                if(DAOContact.updateContactByEmail(extras.getString("email"), c)){
                    Toast.makeText(UpdateContact.this, "Contact updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateContact.this, "Failed to update contact", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
