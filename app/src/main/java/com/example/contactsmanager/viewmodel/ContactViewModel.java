package com.example.contactsmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactsmanager.entity.Contact;
import com.example.contactsmanager.repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    //If you need to use context inside your ViewModel, you
    //should use AndroidViewModel (AVM), because it contains
    //the application context.

    private ContactRepository repository;
    //LiveData
    private LiveData<List<Contact>> allContacts;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContactRepository(application);
    }

    public LiveData<List<Contact>> getAllContacts () {
        allContacts = repository.getAllContacts();
        return allContacts;
    }

    public void addNewContact(Contact contact) {
        repository.addContact(contact);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }
    //AndroidViewModel class is a subclass of ViewModel and
    //similar to them, they are designed to tstore and manage
    //UI-related data are responsible to prepare and provide
    //data for UI and automatically allow data to survive
    //configuration change.
}
