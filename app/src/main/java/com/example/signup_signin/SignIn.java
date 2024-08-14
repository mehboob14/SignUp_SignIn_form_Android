package com.example.signup_signin;

import android.content.Intent;
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

public class SignIn extends AppCompatActivity {
      Button signInBtn;
      EditText useremail;
      EditText userpassword;
      TextView forgotpass;
      TextView singUp;
      boolean validuser = false;
      JSONArray jsonArray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        useremail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        signInBtn = findViewById(R.id.signInBtn);
        forgotpass = findViewById(R.id.forgotPassword);
        singUp = findViewById(R.id.SignUp);

        Intent intent = getIntent();
        String jsonStringArry = intent.getStringExtra("jsonArray");
        try {
         jsonArray = new JSONArray(jsonStringArry);
        }catch(Exception e){
            //
        }

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterdemail = useremail.getText().toString();
                String enterdpassword = userpassword.getText().toString();
                validuser = validDateUser(enterdemail,enterdpassword);
                if(validuser){
                    Toast.makeText(getApplicationContext(), "Successfully Sign In", Toast.LENGTH_SHORT).show();
                //    Intent i = new Intent(getApplicationContext(), authenticated.class);
                  //  startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
forgotpass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), forgot_password.class);
        intent.putExtra("jsonArray", jsonArray.toString());
        startActivity(intent);
    }
});

singUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("jsonArray", jsonArray.toString());
       startActivity(intent);
    }
});
    }
    public boolean validDateUser(String usermail,String userpassword){
     for(int i = 0;i<jsonArray.length();i++){
         try {
             JSONObject user = jsonArray.getJSONObject(i);
             if(user.getString("Email").equals(usermail) && user.getString("Password").equals(userpassword)){
                 return true;
             }
         }catch(JSONException E){
            //
         }
     }
     return false;
    }
}