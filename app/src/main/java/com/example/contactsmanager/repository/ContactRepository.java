package com.example.contactsmanager.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.contactsmanager.dao.ContactDAO;
import com.example.contactsmanager.database.ContactDatabase;
import com.example.contactsmanager.entity.Contact;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    //The Available Data Sources are:
    //- ROOM Database
    private final ContactDAO contactDAO;
    private ExecutorService executor;
    private Handler handler;
    public ContactRepository(Application application) {
        ContactDatabase contactDatabase = ContactDatabase.getInstance(application);
        this.contactDAO = contactDatabase.getContactDAO();

        //Used for Background Database Operations
        this.executor = Executors.newSingleThreadExecutor();
        //Used for updating the UI
        this.handler = new Handler(Looper.getMainLooper());
    }

    //Methods in DAO being executed from Repository
    public void addContact(Contact contact) {


        executor.execute(() -> {
            contactDAO.insert(contact);
        });

    }

    public void deleteContact(Contact contact) {
        executor.execute(() -> {
            contactDAO.delete(contact);
        });
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}
