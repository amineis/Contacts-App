package com.example.gestioncontactsfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncontactsfinal.dao.DBHelper;

public class Signup extends AppCompatActivity {
    Button signup;
    ImageView prev;
    EditText First,Last,Email,Pass,Birth;
    DBHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MyDB = new DBHelper(this);
        prev = findViewById(R.id.back);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, MainActivity.class));
                finish();
            }
        });
        signup = findViewById(R.id.signup_button);
        First = findViewById(R.id.first);
        Last = findViewById(R.id.last);
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.password);
        Birth = findViewById(R.id.birth);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = First.getText().toString();
                String last = Last.getText().toString();
                String email = Email.getText().toString();
                String pass = Pass.getText().toString();
                String birth = Birth.getText().toString();
                if (first.isEmpty() || last.isEmpty() || email.isEmpty() || pass.isEmpty() || birth.isEmpty()) {
                    Toast.makeText(Signup.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean res = MyDB.insertData(first, last, birth, email, pass);
                    if(res==true)
                    {
                        Toast.makeText(Signup.this,"Registration Succesful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Signup.this,"Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });







    }

}
