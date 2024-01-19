package com.example.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    private EditText Username, Password, Email, Phone;
    private Button signup;
    private String user, pass, mail, phn;
    private Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setup();
        signup.setVisibility(View.INVISIBLE);
    }


    public void swith(View view) {
        if(aSwitch.isChecked()){
            signup.setVisibility(View.VISIBLE);
        }else {
            signup.setVisibility(View.INVISIBLE);
        }
    }


    public void setup() {
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.phone);
        signup = findViewById(R.id.signup);
        aSwitch = findViewById(R.id.SigunUpSwitch);
    }

    public void adduser(View view) {
        if (validate()) {
            user = Username.getText().toString().trim();
            mail = Email.getText().toString().trim();
            pass = Password.getText().toString().trim();
            phn = Phone.getText().toString().trim();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String usersJson = preferences.getString("users", "[]");
            user[] usersArray = new Gson().fromJson(usersJson, user[].class);
            user[] updatedUsersArray = Arrays.copyOf(usersArray, usersArray.length + 1);
            updatedUsersArray[usersArray.length] = new user(user, mail, pass, phn);
            String updatedUsersJson = new Gson().toJson(updatedUsersArray);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("users", updatedUsersJson);
            editor.apply();

            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public boolean validate() {
        String user = Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        if (user.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}