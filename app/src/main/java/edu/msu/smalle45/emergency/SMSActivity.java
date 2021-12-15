package edu.msu.smalle45.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class SMSActivity extends AppCompatActivity {

    // List of selected contacts to receive message
    ArrayList<Contact> contacts;
    // TAG for debugging in Logcat
    private static final String TAG = "SMSActivity";
    // Button to send SMS message
    private Button send;
    // User's location
    private String userAddress;
    // EditText to send Emergency Message
    private EditText emergencyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsactivity);

        contacts = getIntent().getParcelableArrayListExtra("Contacts");
        Log.i(TAG, "Contacts Received: " + contacts);

        userAddress = getIntent().getStringExtra("Address");
        Log.i(TAG, "Address: " + userAddress);

        emergencyMessage = findViewById(R.id.emergencyMessage);

        // Pre-populate EditText with Default Message
        createDefaultMessage();

        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        // If user gave permission to send SMS messages
                        // Send message to each contact
                        for(int i=0; i < contacts.size(); i++) {
                            Contact currContact = contacts.get(i);
                            String contactName = currContact.getContactName();
                            String contactNumber = currContact.getContactNumber().trim();
                            String messageSMS = emergencyMessage.getText().toString().trim();
                            sendSMS(contactNumber, messageSMS);
                        }
                        // After the messages send, send user back to Home Page
                        goHomeActivity();

                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });
    }

    private void sendSMS(String number, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Create Default Emergency Message
     * in EditText
     */

    private void createDefaultMessage() {
        String user = ParseUser.getCurrentUser().getUsername();
        String message = "URGENT: " + user + " needs you to call 911. " + user + "'s address is: " + userAddress;
        emergencyMessage.setText(message);
    }

    private void goHomeActivity() {
        Intent intent = new Intent(SMSActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}