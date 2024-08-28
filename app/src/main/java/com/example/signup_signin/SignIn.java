package com.example.signup_signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class SignIn extends AppCompatActivity {
    Button signInBtn;
    EditText useremail;
    EditText userpassword;
    TextView forgotpass;
    TextView singUp;
    DatabaseHandler db;
    CheckBox checkBox;
    Button fingerprintBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        useremail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        signInBtn = findViewById(R.id.signInBtn);
        forgotpass = findViewById(R.id.forgotPassword);
        singUp = findViewById(R.id.SignUp);
        checkBox = findViewById(R.id.checkbox);
        fingerprintBtn = findViewById(R.id.fingerprint);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        db = new DatabaseHandler(this);


        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();


                String savedEmail = prefs.getString("username", null);
                String savedPassword = prefs.getString("password", null);

                if (savedEmail != null && savedPassword != null) {
                    useremail.setText(savedEmail);
                    userpassword.setText(savedPassword);
                    signInBtn.performClick();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for My App")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use password instead")
                .build();


        BiometricManager biometricManager = BiometricManager.from(this);
        //BiometricManager.BIOMETRIC_SUCCESS
            fingerprintBtn.setVisibility(View.VISIBLE);
            fingerprintBtn.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));


        signInBtn.setOnClickListener(view -> {
            String enteredEmail = useremail.getText().toString().trim();
            String enteredPassword = userpassword.getText().toString().trim();

            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            UserInfo user = db.getuser(enteredEmail);
            if (user != null && user.getEmail().equals(enteredEmail)) {
                boolean isChecked = checkBox.isChecked();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", enteredEmail);
                editor.putString("password", enteredPassword);
                editor.putBoolean("checkbox", isChecked);
                editor.apply();

                displaySharedPreferences(prefs);

                Toast.makeText(getApplicationContext(), "Successfully Signed In", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        });

        forgotpass.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), forgot_password.class);
            startActivity(intent);
        });

        singUp.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    public void displaySharedPreferences(SharedPreferences prefs) {
        String savedEmail = prefs.getString("username", "No email found");
        String savedPassword = prefs.getString("password", "No password foun");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Saved Credentials");
        builder.setMessage("Email: " + savedEmail + "\nPassword: " + savedPassword);
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
