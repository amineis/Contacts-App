package com.example.gestioncontactsfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncontactsfinal.Model.User;
import com.example.gestioncontactsfinal.dao.DBHelper;

public class Login extends AppCompatActivity {
    Button signup,login;
    private EditText pass,mail;
    private DBHelper MyDB;
    private SessionManager sessionManager;
    private static final String PREF_NAME = "Session";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_LASTNAME = "lastName";
    private static final String KEY_BIRTH_DATE = "birthDate";
    private static final String KEY_EMAIL = "email";
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());
        signup = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);
        pass = findViewById(R.id.password);
        mail = findViewById(R.id.email);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        MyDB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString();
                String password = pass.getText().toString();

                if(email.equals("") || password.equals(""))
                {
                    Toast.makeText(Login.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean result = MyDB.Login(email, password);
                    if(result == false)
                    {
                        Toast.makeText(Login.this, "User does not exists. Please Register", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        User user = MyDB.getUserByEmail(email);
                        editor.putString(KEY_FIRST_NAME,user.getFirstname());
                        editor.putString(KEY_LAST_LASTNAME,user.getLastname());
                        editor.putString(KEY_BIRTH_DATE,user.getBirth());
                        editor.putString(KEY_EMAIL,email);
                        editor.apply();
                        sessionManager.setLoggedIn(true);
                        Intent intent = new Intent(getApplicationContext(), ListContact.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });


    }


}
