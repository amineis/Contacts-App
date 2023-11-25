package com.example.gestioncontactsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button1;
    private SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(getApplicationContext());



        button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        button1 = (Button) findViewById(R.id.login);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });



    }
    protected void onResume() {
        super.onResume();

        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, ListContact.class);
            startActivity(intent);
            finish();
        }
    }

    public void openRegister() {
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);

    }

    public void openLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);

    }


}