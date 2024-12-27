package com.example.contactsmanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.contactsmanager.dao.ContactDAO;
import com.example.contactsmanager.entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDAO();

    //Singleton Pattern
    private static ContactDatabase dbInstance;

    public static synchronized ContactDatabase getInstance (Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context, ContactDatabase.class, "contacts_db")
                    .fallbackToDestructiveMigration().build();
        }
        return dbInstance;
    }

}
