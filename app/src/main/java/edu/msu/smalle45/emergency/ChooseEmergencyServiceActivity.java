package edu.msu.smalle45.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseEmergencyServiceActivity extends AppCompatActivity {

    // Button to request the police
    private Button btnPolice;

    // Button to request the Fire Department
    private Button btnFire;

    // Button to request Medical/EMS Services
    private Button btnMedical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_emergency_service);

        btnPolice = findViewById(R.id.btnPolice);
        btnFire = findViewById(R.id.btnFire);
        btnMedical = findViewById(R.id.btnMedical);

        btnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User chose to request the Fire Department
                String emergency = "Fire Department";
                goGetContactsActivity(emergency);
            }
        });

        btnMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User chose to request Medical/EMS Services
                String emergency = "EMS";
                goGetContactsActivity(emergency);
            }
        });

        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User chose to request Police
                String emergency = "The Police";
                goGetContactsActivity(emergency);
            }
        });
    }

    private void goGetContactsActivity(String service) {
        Intent intent = new Intent(ChooseEmergencyServiceActivity.this, GetContactsActivity.class);
        intent.putExtra("Service", service);
        startActivity(intent);
    }
}