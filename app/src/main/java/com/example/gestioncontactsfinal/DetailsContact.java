package com.example.gestioncontactsfinal;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gestioncontactsfinal.Model.Contact;
import com.example.gestioncontactsfinal.dao.ContactsContract;
import com.example.gestioncontactsfinal.dao.DAOContact;
import com.example.gestioncontactsfinal.dao.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DetailsContact extends AppCompatActivity {
    private ImageView call;
    private ImageView sms;
    private ImageView email;
    private ImageView prev;
    private TextView nom;
    private TextView service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        call = (ImageView) findViewById(R.id.call);
        sms = (ImageView) findViewById(R.id.sms);
        email = (ImageView) findViewById(R.id.email);
        nom = findViewById(R.id.textNom);
        service = findViewById(R.id.textService);
        prev = findViewById(R.id.back);
        prev.setOnClickListener(view -> finish());




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Nom = extras.getString("nom");
            String Service = extras.getString("service");
            nom.setText("Name : "+Nom);
            service.setText("Service : "+Service);
        }
        String number = extras.getString("tel");
        String mail = extras.getString("email");
        Contact c = (Contact) getIntent().getSerializableExtra("contact");




        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri smsUri = Uri.parse("smsto:" + number);
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
                startActivity(intent);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri emailUri = Uri.parse("mailto:" + mail);
                Intent intent = new Intent(Intent.ACTION_SENDTO, emailUri);
                startActivity(intent);
            }
        });




    }




}
