package edu.msu.smalle45.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    // Button to create a new emergency
    private Button btnNewEmergency;

    // Button to logout of Parse
    private Button btnLogout;

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnNewEmergency = findViewById(R.id.btnNewEmergency);
        btnLogout = findViewById(R.id.btnLogout);
        username = findViewById(R.id.name);

        String currUserName = ParseUser.getCurrentUser().getUsername();
        username.setText(currUserName);

        btnNewEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChooseEmergencyServiceActivity();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout of Parse
                ParseUser.logOut();
                // Current user should be null
                ParseUser currentUser = ParseUser.getCurrentUser();
                goWelcomeActivity();

            }
        });
    }

    private void goWelcomeActivity() {
        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    private void goChooseEmergencyServiceActivity() {
        Intent intent = new Intent(HomeActivity.this, ChooseEmergencyServiceActivity.class);
        startActivity(intent);
    }
}