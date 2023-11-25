package com.example.gestioncontactsfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestioncontactsfinal.Model.Contact;
import com.example.gestioncontactsfinal.adapters.Adapter;
import com.example.gestioncontactsfinal.dao.ContactsContract;
import com.example.gestioncontactsfinal.dao.DAOContact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListContact extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter contactAdapter;
    private List<Contact> contactList;
    private DAOContact contactDAO;
    EditText search;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        sessionManager = new SessionManager(getApplicationContext());
        search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    contactAdapter = new Adapter(ListContact.this, contactList);
                    retrieveContact();
                    recyclerView.setAdapter(contactAdapter);
                }
                if (s.length() != 0) {
                    List<Contact> Contacts = new ArrayList<>();
                    for (Contact c : contactList){
                        final String title = c.getNom().toLowerCase();
                        if (title.contains(s.toString().toLowerCase()))
                            Contacts.add(c);
                    }
                    contactAdapter = new Adapter(ListContact.this, Contacts);
                    retrieveContact();
                    recyclerView.setAdapter(contactAdapter);
                }
            }
        });
        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContact.this, AjouterContact.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.list_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactAdapter = new Adapter(this, contactList);
        retrieveContact();
        recyclerView.setAdapter(contactAdapter);
        contactDAO = new DAOContact(this);
        retrieveContacts();

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveContacts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    boolean clicked = false;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.favorit) {
            if (clicked) {
                contactAdapter = new Adapter(ListContact.this, contactList);
                retrieveContact();
                recyclerView.setAdapter(contactAdapter);
                item.setIcon(R.drawable.favorit_menu_border);
                clicked = false;
            } else {
                List<Contact> Contacts = new ArrayList<>();
                for (Contact c : contactList){
                    if (c.isFavorite()){
                        Contacts.add(c);
                    }
                }
                contactAdapter = new Adapter(ListContact.this, Contacts);
                retrieveContact();
                recyclerView.setAdapter(contactAdapter);
                item.setIcon(R.drawable.favorit_menu);
                clicked = true;
            }
            return true;
        }
        if(itemId == R.id.logout){
            sessionManager.setLoggedIn(false);
            Intent intent = new Intent(ListContact.this, Login.class);
            startActivity(intent);
            finish();
        }
        if(itemId == R.id.profil){
            Intent intent = new Intent(ListContact.this, Profile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveContact(){
        contactAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(String email) {
                Contact selectedContact = DAOContact.getContact(email);
                Intent intent = new Intent(ListContact.this, DetailsContact.class);
                intent.putExtra("nom", selectedContact.getNom());
                intent.putExtra("service", selectedContact.getService());
                intent.putExtra("tel", selectedContact.getTel());
                intent.putExtra("email", selectedContact.getEmail());
                intent.putExtra("contact" , selectedContact);
                startActivity(intent);
            }
        });
    }

    private void retrieveContacts() {
        contactList.clear();
        contactList.addAll(DAOContact.getAllContacts());
        contactAdapter.notifyDataSetChanged();
    }

}
