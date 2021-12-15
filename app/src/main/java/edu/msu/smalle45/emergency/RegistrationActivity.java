package edu.msu.smalle45.emergency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


/**
 * Activity for Registering a user to the app
 * Registration source code: https://github.com/Smalley2001/Tout_le_Monde
 */

public class RegistrationActivity extends AppCompatActivity {

    // User's username
    private EditText etUsername;
    // User's password
    private EditText etPassword;
    // User's confirm password
    private EditText etConfirmPassword;
    // TextView if user already has an account
    private TextView alreadyHaveAccount;
    // Button to register user to Parse
    private Button btnRegister;
    // TAG used for debugging on Logcat
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = findViewById(R.id.inputRegUsername);
        etPassword = findViewById(R.id.inputRegPassword);
        etConfirmPassword = findViewById(R.id.inputRegConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLoginActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // Validate Registration form
                if (isEmpty(username, password, confirmPassword)) {
                    Toast.makeText(RegistrationActivity.this, "At least one field is empty", Toast.LENGTH_SHORT).show();
                } else if (passwordNotEqual(password, confirmPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Valid Registration
                    validRegistration();
                }

            }
        });
    }

    private void SignUpUser(String username, String password) {

        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // User successfully signed up
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.i(TAG, "New user created");
                        }
                    });

                    // User registered successfully, send to Home Page
                    goHomeActivity();

                } else {
                    // Sign up failed
                    Log.i(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    private void validRegistration() {
        // Registration is valid
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        // Save user to parse server
        SignUpUser(username, password);
    }

    private boolean isEmpty(String name, String pass, String confirm) {

        return name.isEmpty() || pass.isEmpty() || confirm.isEmpty();
    }

    private boolean passwordNotEqual(String pass, String confirm) {
        return !pass.equals(confirm);
    }

    private void goHomeActivity() {
        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void goLoginActivity() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}