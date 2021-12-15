package edu.msu.smalle45.emergency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter used to inflate the listview in the
 * GetContactsActivity. Researched implementation
 * at: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {


    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_list_view, parent, false);
        }
        // Lookup view for data population
        TextView contactName =  convertView.findViewById(R.id.itemContactName);
        TextView contactNumber = convertView.findViewById(R.id.itemContactNumber);
        // Populate the data into the template view using the data object
        contactName.setText(contact.contactName);
        contactNumber.setText(contact.contactNumber);

        // Return the completed view to render on screen
        return convertView;
    }
}
