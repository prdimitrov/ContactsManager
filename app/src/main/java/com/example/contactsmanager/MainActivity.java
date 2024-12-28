package com.example.contactsmanager;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsmanager.adapter.ContactAdapter;
import com.example.contactsmanager.database.ContactDatabase;
import com.example.contactsmanager.databinding.ActivityMainBinding;
import com.example.contactsmanager.entity.Contact;
import com.example.contactsmanager.handler.MainActivityClickHandler;
import com.example.contactsmanager.viewmodel.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Data Source
    private ContactDatabase contactDatabase;
    private ArrayList<Contact> contacts = new ArrayList<>();
    // Adapter
    private ContactAdapter myAdapter;
    // Binding
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

            // Data Binding
            mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            handlers = new MainActivityClickHandler(this);

            mainBinding.setClickHandler(handlers);

            // Recycler View
            RecyclerView recyclerView = mainBinding.recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            // Database:
            contactDatabase = ContactDatabase.getInstance(this);

            // ViewModel:
            ContactViewModel viewModel = new ViewModelProvider(this)
                    .get(ContactViewModel.class);

            // Load the data from ROOM DB
            viewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> newContacts) {
                    contacts.clear(); // Clear the old data
                    contacts.addAll(newContacts); // Add the new data to the list

                    // Log the contacts for debugging
                    for (Contact c : newContacts) {
                        Log.v("TAGY", "id: " + c.getId() + ", name: " + c.getFirstNameAndLastName());
                    }

                    myAdapter.updateContacts(newContacts); // Update the adapter with new data
                }
            });

            // Adapter
            myAdapter = new ContactAdapter(new ArrayList<>());

            // Linking the RecyclerView with the Adapter
            recyclerView.setAdapter(myAdapter);

            // Swipe to delete contact
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    // Swipe item to the left
                    Contact c = contacts.get(viewHolder.getAdapterPosition());

                    viewModel.deleteContact(c);


                }
            }).attachToRecyclerView(recyclerView);

            return insets;
        });
    }
}