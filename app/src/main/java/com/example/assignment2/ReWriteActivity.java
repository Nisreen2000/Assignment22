package com.example.assignment2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ReWriteActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_write);

        textView = findViewById(R.id.textview);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }

    public void rewrite(View view){
        RequestQueue queue = Volley.newRequestQueue(this);

        String apiUrl = "https://rewrite-blog-article.p.rapidapi.com/rewriteBlogSection";
        String encodedArticleText = "";
        try {
            encodedArticleText = URLEncoder.encode(editText.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalEncodedArticleText = encodedArticleText;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = jsonResponse.getBoolean("status");
                            if (status) {
                                String data = jsonResponse.getString("data");
                                Log.d("API Response", "Data: " + data);
                                textView.setText(data);
                            } else {
                                Log.e("API Error", "Status is false");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("API Error", "Error parsing JSON response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", "Error occurred", error);
                    }
                }
        ) {
            @Override
            public byte[] getBody() {
                String requestBody = "article=" + finalEncodedArticleText;
                return requestBody.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", "38cb4a7221mshc19587216ab1fb8p1114d1jsn611d23451fe5");
                headers.put("X-RapidAPI-Host", "rewrite-blog-article.p.rapidapi.com");
                return headers;
            }
        };

        queue.add(stringRequest);
    }
}