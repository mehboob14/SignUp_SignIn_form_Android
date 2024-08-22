package com.example.signup_signin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class forgot_password extends AppCompatActivity {
 EditText email;
 Button findaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        DatabaseHandler db;
        email = findViewById(R.id.email);
        findaccount = findViewById(R.id.findaccount);
        db = new DatabaseHandler(this);

        findaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterdEmial = email.getText().toString().trim();
                if(enterdEmial == null){
                    return;
                }
                UserInfo user = db.getuser(enterdEmial);
                if(user.getEmail().equals(enterdEmial)){
                    Toast.makeText(forgot_password.this, "Account Recoverd", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(forgot_password.this, "Account Not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}