package com.example.contactsmanager.handler;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.contactsmanager.MainActivity;
import com.example.contactsmanager.entity.Contact;

public class AddNewContactClickHandler {
    private Contact contact;
    private Context context;

    public AddNewContactClickHandler(Contact contact, Context context) {
        this.contact = contact;
        this.context = context;
    }

    public void onSubmitButtonClicked(View view) {
        if (isFieldEmpty()) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("FirstName", contact.getFirstName());
            i.putExtra("lastName", contact.getLastName());
            i.putExtra("Email", contact.getEmail());
            context.startActivity(i);
        }
    }

    private boolean isFieldEmpty() {
        if (contact.getFirstName() == null || contact.getFirstName().isEmpty()
                || contact.getLastName() == null || contact.getLastName().isEmpty()
                || contact.getEmail() == null || contact.getEmail().isEmpty()) {
            return true;
        }
        return false;
    }
}
