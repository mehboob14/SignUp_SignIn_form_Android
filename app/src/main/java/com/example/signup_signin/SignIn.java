package com.example.signup_signin;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
public class SignIn extends AppCompatActivity {
    Button signInBtn;
    EditText useremail;
    EditText userpassword;
    TextView forgotpass;
    TextView singUp;
    DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);


        useremail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        signInBtn = findViewById(R.id.signInBtn);
        forgotpass = findViewById(R.id.forgotPassword);
        singUp = findViewById(R.id.SignUp);


        db = new DatabaseHandler(this);


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = useremail.getText().toString().trim();
                String enteredPassword = userpassword.getText().toString().trim();

                if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserInfo user = db.getuser(enteredEmail);

                if (user != null && user.getEmail().equals(enteredEmail)) {
                    Toast.makeText(getApplicationContext(), "Successfully Signed In", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), forgot_password.class);
                startActivity(intent);
            }
        });
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
