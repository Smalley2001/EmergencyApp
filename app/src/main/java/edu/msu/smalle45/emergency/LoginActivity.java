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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Activity for letting a user login to the app
 * Registration source code: https://github.com/Smalley2001/Tout_le_Monde
 */
public class LoginActivity extends AppCompatActivity {

    // User's username
    private EditText etUsername;
    // User's password
    private EditText etPassword;
    // Button to login into to Parse
    private Button btnLogin;
    // Button to go to Registration Activity
    private TextView signUp;
    // TAG used for debugging on Logcat
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // If user already logged in, send them to Main Activity
        if (ParseUser.getCurrentUser() != null) {

            goHomeActivity();
        }

        etUsername = findViewById(R.id.inputLoginUsername);
        etPassword = findViewById(R.id.inputUserPassword);
        btnLogin = findViewById(R.id.btnLoginButton);
        signUp = findViewById(R.id.textViewSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegistrationActivity();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If user clicks login button, login into Parse
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });


    }

    private void goHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginUser(String username, String password) {

        // Navigate to the main activity if the user has signed properly
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // Login Failed
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Login Success
                // Go to Home Page
                goHomeActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void goRegistrationActivity() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}