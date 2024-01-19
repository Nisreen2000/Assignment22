package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebServiessActivity extends AppCompatActivity {
    private String username =null;
    private TextView textView;
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_serviess);
        textView = findViewById(R.id.wle);
        username = getIntent().getStringExtra("username");
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);


        if (savedInstanceState != null) {
            username = savedInstanceState.getString("username");
        } else {
            username = getIntent().getStringExtra("username");
        }
        textView.setText("Welcome " + username);




    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", username);
    }



    public void goToConvert(View view){
        Toast.makeText(this, "Welcome " + username, Toast.LENGTH_SHORT).show();
         Intent intent = new Intent(this, ConvertActivity.class);
         intent.putExtra("username", username);


         startActivity(intent);

    }
    public void goToJoke(View view){
        Intent intent = new Intent(this, ReWriteActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }



}