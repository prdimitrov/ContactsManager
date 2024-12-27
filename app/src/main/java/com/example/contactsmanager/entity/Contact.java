package com.example.contactsmanager.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts_table")
public class Contact {
    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "contact_first_name")
    private String firstName;
    @ColumnInfo(name = "contact_last_name")
    private String lastName;
    @ColumnInfo(name = "contact_email")
    private String email;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstNameAndLastName() {
        String fn = firstName != null ? firstName : "";
        String ln = lastName != null ? lastName : "";
        return fn + " " + ln;
        //        return this.firstName + " " + this.lastName;
    }
}
