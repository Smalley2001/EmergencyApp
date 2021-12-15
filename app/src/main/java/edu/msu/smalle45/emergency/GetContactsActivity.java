package edu.msu.smalle45.emergency;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Activity for accessing device contacts
 * Source code: https://stackoverflow.com/questions/33954358/how-to-select-contact-number-from-contact-list-using-android-studio/33956788
 */

public class GetContactsActivity extends AppCompatActivity {

    // Emergency Service that the user chose in ChooseEmergencyServiceActivity
    private String emergencyService;

    // Button to add a new contact to send SMS emergency message
    private Button btnAddContact;

    // TAG used for debugging in Logcat
    private static final String TAG = "GetContactsActivity";

    // Listview for the user's selected contacts
    private ListView contactsList;

    // ArrayList of contact objects to be inflated in contactsList
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    // Request code for Contact Action Pick Intent
    private static final int RESULT_PICK_CONTACT = 1;

    // Adapter used for inflating contactsList listview
    private ContactsAdapter adapter;

    // EditText for user's location
    private EditText userAddress;

    // Button to submit all the selected contacts
    private Button submitContacts;

    // Emergency Service Requested
    private String service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contacts);

        emergencyService = getIntent().getStringExtra("Service");
        btnAddContact = findViewById(R.id.addContact);
        contactsList = findViewById(R.id.contactsList);
        userAddress = findViewById(R.id.userAddress);
        submitContacts = findViewById(R.id.submitContacts);

        service = getIntent().getStringExtra("Service");

        // Create the adapter to convert the array to views
        adapter = new ContactsAdapter(this, contacts);
        // Attach the adapter to a ListView
        contactsList.setAdapter(adapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Source code: https://stackoverflow.com/questions/18444671/remove-item-in-arrayadapterstring-in-listview
                // If item is clicked in listview
                Contact contact = contacts.get(position);
                // Remove the contact from the listview
                contacts.remove(position);
                // Alert the adapter that a contact was removed
                adapter.notifyDataSetChanged();
            }
        });

        submitContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send contacts to the SMS Activity
                goSMSActivity();
            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fire implicit intent to get device contacts
                selectSingleContact();

            }
        });
    }

    private void selectSingleContact() {
        /**
         * Fire implicit intent to access device contacts.
         * Start activity for result so we can get data returned.
         */
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Get data back once Contacts Implicit Intent fired
         */

        // If we get a result
        if (resultCode == RESULT_OK) {

            if (requestCode == RESULT_PICK_CONTACT) {
                // If the request was to get contact
                // Store contact information
                contactPicked(data);
            }
        }
    }

    private void contactPicked(Intent data) {

        /**
         * Get contact information from Android database
         * and inflate listview with contact data
         */

        Uri uri = data.getData();
        Log.i(TAG, "contactPicked() uri " + uri.toString());
        Cursor cursor;
        ContentResolver contentResolver = getContentResolver();

        Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // If we got something back
        if (cur.getCount() > 0 ) {
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the contact name
            @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            // column index of the phone number
            @SuppressLint("Range") String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i(TAG, "Name: " + contactName + " Number: " + contactNumber);

            // Add to contact to contact list
            Contact newContact = new Contact(contactName, contactNumber);
            adapter.add(newContact);
            Log.i(TAG, "Contacts: " + contacts.size());

        }
    }

    private void goSMSActivity() {

        /**
         * Go to the SMS Activity
         */

        String address = userAddress.getText().toString();
        Intent intent = new Intent(GetContactsActivity.this, SMSActivity.class);
        // Send list of Contact Objects to SMS Activity
        intent.putParcelableArrayListExtra("Contacts", contacts);
        intent.putExtra("Address", address);
        intent.putExtra("Service", service);
        startActivity(intent);
    }
}