package com.example.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private EditText Password, Email;
    private Button signin;
    private String pass, mail;
    private Intent intent;
    private String usernameh;

    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;

    private CheckBox chk;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        setupSharedPrefs();
        checkPrefs();
    }
    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            Email.setText(name);
            Password.setText(password);
            chk.setChecked(true);
        }
    }
    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    public void btnLoginOnClick(View view) {
        String name = Email.getText().toString();
        String password = Password.getText().toString();
        if(chk.isChecked()){
            if(!flag) {
                editor.putString(NAME, name);
                editor.putString(PASS, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }

        }

    }

    public void setup() {
        Password = findViewById(R.id.passwordsignin);
        Email = findViewById(R.id.Emailsginin);
        signin = findViewById(R.id.signin);
        chk = findViewById(R.id.chk);
    }

    public void SingUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void CheackUser(View view) {
        if (valdate()) {
            String enteredEmail = Email.getText().toString().trim();
            String enteredPassword = Password.getText().toString().trim();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String usersJson = preferences.getString("users", "[]");

            user[] usersArray = new Gson().fromJson(usersJson, user[].class);
            boolean credentialsMatch = false;
            for (user storedUser : usersArray) {
                if (enteredEmail.equals(storedUser.getEmail()) && enteredPassword.equals(storedUser.getPassword())) {
                    credentialsMatch = true;
                    usernameh = storedUser.getUsername();
                    break;
                }
            }

            if (credentialsMatch) {
                Intent intent = new Intent(this, WebServiessActivity.class);
                intent.putExtra("username", usernameh);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean valdate() {
        String Em = Email.getText().toString();
        String Pass = Password.getText().toString();

        if (Em.isEmpty()) {
            Email.setError("Email is required");
            return false;
        }
        if (Pass.isEmpty()) {
            Password.setError("Password is required");
            return false;
        }

        return true;
    }



}