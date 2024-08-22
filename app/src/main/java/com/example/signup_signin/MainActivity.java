package com.example.signup_signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button signup;
    EditText email, username, password, confirmPassword;
    CheckBox termsConditions;
    TextView alreadyuser,mainpage;
    JSONArray jsonArray = new JSONArray();
    String userEmail, userName, userPassword, userConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        List<UserInfo> obj = new ArrayList<UserInfo>();

        signup = findViewById(R.id.signupBtn);
       email = findViewById(R.id.email);
       username = findViewById(R.id.username);
       password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
       termsConditions = findViewById(R.id.terms_conditions);
        //signupactivity = findViewById(R.id.login);
        alreadyuser = findViewById(R.id.login);
        mainpage = findViewById(R.id.mainpage);


        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
               // intent.putExtra("jsonArray", jsonArray.toString());
                intent.putExtra("obj",obj.toString());
                startActivity(intent);
            }
        });

         mainpage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), Welcome_page.class);
                // i.putExtra("jsonArray", jsonArray.toString());
                 startActivity(i);
             }
         });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = email.getText().toString().trim();
                userName = username.getText().toString().trim();
                userPassword = password.getText().toString().trim();
                userConfirmPassword = confirmPassword.getText().toString().trim();

                boolean check = fieldsCheck();
                if (check) {

                db.AddUser(new UserInfo(userEmail,userName,userPassword));
                db.AddUser(new UserInfo("mehboob14@gmail.com","mehboob","boobhem@007"));


                   Toast.makeText(MainActivity.this, "Successfully signed up", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(getApplicationContext(), authenticated.class);
                    //startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean fieldsCheck() {
        if (userName.length() < 4 || userName.length() > 24) {
            Toast.makeText(getApplicationContext(), "Username must be between 4 and 24 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!userEmail.endsWith(".com") || !userEmail.contains("@")) {
            Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidPassword(userPassword)) {
            Toast.makeText(getApplicationContext(), "Password must be 8 characters, 1 special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!userPassword.equals(userConfirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!termsConditions.isChecked()) {
            Toast.makeText(getApplicationContext(), "You must agree with the terms and conditions", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean isUpper = false;
       // boolean isDigit = false;
        boolean isSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                isUpper = true;

            } else if (!Character.isLetterOrDigit(c)) {
                isSpecial = true;
            }

            if (isUpper && isSpecial) {
                return true;
            }
        }
        return false;
    }
}
