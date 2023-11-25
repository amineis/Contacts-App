package com.example.gestioncontactsfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncontactsfinal.Model.User;
import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView birthDateTextView;
    private TextView emailTextView;

    private SessionManager sessionManager;
    private static final String PREF_NAME = "Session";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_LASTNAME = "lastName";
    private static final String KEY_BIRTH_DATE = "birthDate";
    private static final String KEY_EMAIL = "email";
    private ImageView prev;
    private SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        firstNameTextView = findViewById(R.id.firstname);
        lastNameTextView = findViewById(R.id.lastname);
        birthDateTextView = findViewById(R.id.birthdate);
        emailTextView = findViewById(R.id.email);
        prev = findViewById(R.id.back);
        prev.setOnClickListener(view -> finish());


        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_FIRST_NAME,null);
        String last = sharedPreferences.getString(KEY_LAST_LASTNAME,null);
        String birth = sharedPreferences.getString(KEY_BIRTH_DATE,null);
        String email = sharedPreferences.getString(KEY_EMAIL,null);



        firstNameTextView.setText("First Name : " + name);
        lastNameTextView.setText("Last Name : " + last);
        birthDateTextView.setText("Birth Date : " + birth);
        emailTextView.setText("Email : " + email);



    }
}
