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
      boolean validuser = false;
      JSONArray jsonArray = new JSONArray();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        DatabaseHandler db = new DatabaseHandler(this);


        useremail = findViewById(R.id.email);
        userpassword = findViewById(R.id.password);
        signInBtn = findViewById(R.id.signInBtn);
        forgotpass = findViewById(R.id.forgotPassword);
        singUp = findViewById(R.id.SignUp);

      //  db.getuser(useremail,userpassword);
        Intent intent = getIntent();
        String obj = intent.getStringExtra("obj");


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterdemail = useremail.getText().toString();
                String enterdpassword = userpassword.getText().toString();
               List<UserInfo> users = db.getAllUsers();
               //Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
         for(UserInfo usr :users){
             if(usr.getEmail().equals(enterdemail)){
                 Toast.makeText(getApplicationContext(), "Successfully Sign In", Toast.LENGTH_SHORT).show();

             }else {
                 Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
             }
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
     /*for(int i = 0;i<jsonArray.length();i++){
         try {
             JSONObject user = jsonArray.getJSONObject(i);
             if(user.getString("Email").equals(usermail) && user.getString("Password").equals(userpassword)){
                 return true;
             }
         }catch(JSONException E){
            //
         }
     } */


     return false;
    }
}