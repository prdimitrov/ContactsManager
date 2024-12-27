package com.example.contactsmanager;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsmanager.adapter.ContactAdapter;
import com.example.contactsmanager.database.ContactDatabase;
import com.example.contactsmanager.databinding.ActivityMainBinding;
import com.example.contactsmanager.entity.Contact;
import com.example.contactsmanager.handler.MainActivityClickHandler;
import com.example.contactsmanager.viewmodel.ContactViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Data Source
    private ContactDatabase contactDatabase;
    private ArrayList<Contact> contacts = new ArrayList<>();
    //Adapter
    private ContactAdapter myAdapter;
    //Binding
    private ActivityMainBinding mainBinding;
    private MainActivityClickHandler handlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //Data Binding
            mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            handlers = new MainActivityClickHandler(this);

            mainBinding.setClickHandler(handlers);

            //Recycler View
            RecyclerView recyclerView = mainBinding.recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            //Adapter
            myAdapter = new ContactAdapter(contacts);

            //Database:
            contactDatabase = ContactDatabase.getInstance(this);

            //ViewModel:
            ContactViewModel viewModel = new ViewModelProvider(this)
                    .get(ContactViewModel.class);

            //Inserting a new Contact (Just For Testing):
            Contact c1 = new Contact("Edin", "Gospodin", "egn@abv.bg");
            viewModel.addNewContact(c1);

            //Load the data from ROOM DB
            viewModel.getAllContacts().observe(this, contacts -> {
                for (Contact c : contacts) {
                    Log.v("TAGY", c.getFirstNameAndLastName());
                }
            });

            recyclerView.setAdapter(myAdapter);

            return insets;
        });
    }
}