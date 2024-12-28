package com.example.contactsmanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.contactsmanager.databinding.ActivityAddNewContactBinding;
import com.example.contactsmanager.entity.Contact;
import com.example.contactsmanager.handler.AddNewContactClickHandler;

public class AddNewContactActivity extends AppCompatActivity {

    private ActivityAddNewContactBinding binding;
    private AddNewContactClickHandler handler;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            contact = new Contact();

            binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_contact);

            handler = new AddNewContactClickHandler(contact, this);

            binding.setContact(contact);

            binding.setClickHandler(handler);

            return insets;
        });
    }
}