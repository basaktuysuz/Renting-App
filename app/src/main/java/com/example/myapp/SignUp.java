package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class SignUp extends AppCompatActivity {
// variables
    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPassword;

    Button regBtn,regToLoginBtn;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        //hooks to all xml elements

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regToLoginBtn = findViewById(R.id.reg_login_btn);
        regBtn = findViewById(R.id.reg_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save data to firebase on button click
                //testing firebase data storing and update
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                Toast.makeText(SignUp.this,"Button Clicked",Toast.LENGTH_SHORT).show();

                //get all the values in String
                String name=regName.getEditText().getText().toString();
                String username=regUsername.getEditText().getText().toString();
                String email=regEmail.getEditText().getText().toString();
                String phoneNo=regPhoneNo.getEditText().getText().toString();
                String password=regPassword.getEditText().getText().toString();
                UserHelperClass helperClass=new UserHelperClass(name,username,email,phoneNo,password);
               //çünkü phone unique
                reference.child(username).setValue(helperClass);

            }//end methd
        });

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });
    }

    private  Boolean validateName(){
        String val=regName.getEditText().getText().toString();
if(val.isEmpty()){
    regName.setError("Field cannot be empty");
     return false;
}else{
    regName.setError(null);
    regName.setErrorEnabled(false);
    return true;
}
    }

    private  Boolean validateUsername(){
        String val=regUsername.getEditText().getText().toString();
        String noWhiteSpace="\"\\\\A\\\\w{4,20}\\\\z\"";
        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }else if (val.length()>=15){
            regUsername.setError("username too long" );
            return false;
  } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White spaces are not allowed");
            return false;
            
        } else{
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }


    // save data in firebase with go button
    public void registerUser(View view){
    if(!validateName() |!validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername())
    {
        return;
    }


    }
}