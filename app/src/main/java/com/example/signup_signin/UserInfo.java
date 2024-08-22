package com.example.signup_signin;

public class UserInfo {
    private String Email;
    private String Username;
    private String Password;
    UserInfo(){ }
   UserInfo(String email, String username,String password){
       this.Email = email;
       this.Username = username;
       this.Password = password;

   }
   public void setPassword(String pass){
       this.Password = pass;
   }
   public String getEmail(){
       return Email;
   }
   public String getPassword(){
       return Password;
   }
   public String getUsername(){
       return Username;
   }
}
