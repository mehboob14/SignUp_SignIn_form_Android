package com.example.signup_signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;

public class Welcome_page extends AppCompatActivity {
        Button signUp;
        Button login;
        JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUp = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        Intent intent = getIntent();
        String jsonStringArry = intent.getStringExtra("jsonArray");
        try {
            jsonArray = new JSONArray(jsonStringArry);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error occured!", Toast.LENGTH_SHORT).show();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                intent.putExtra("jsonArray",jsonArray.toString());
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("jsonArray",jsonArray.toString());
                startActivity(intent);
            }
        });

    }
}