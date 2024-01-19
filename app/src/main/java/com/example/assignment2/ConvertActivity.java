package com.example.assignment2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConvertActivity extends AppCompatActivity {
    Button btn;
    EditText et;
    TextView tv;
    Spinner sp;
    Spinner sp2;
    RequestQueue requestQueue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);


        btn = findViewById(R.id.button2);
        et = findViewById(R.id.editTextText2);
        tv = findViewById(R.id.res);
        sp = findViewById(R.id.spinner2);
        sp2 = findViewById(R.id.spinner3);

        requestQueue = Volley.newRequestQueue(this);
        String currencyData = "EUR,Euro\nUSD,US Dollar\nJPY,Japanese Yen\nBGN,Bulgarian Lev\nCZK,Czech Republic Koruna\n" +
                "DKK,Danish Krone\nGBP,British Pound Sterling\nHUF,Hungarian Forint\nPLN,Polish Zloty\nRON,Romanian Leu\n" +
                "SEK,Swedish Krona\nCHF,Swiss Franc\nISK,Icelandic Króna\nNOK,Norwegian Krone\nHRK,Croatian Kuna\n" +
                "RUB,Russian Ruble\nTRY,Turkish Lira\nBRL,Brazilian Real\nCAD,Canadian Dollar\nCNY,Chinese Yuan\n" +
                "HKD,Hong Kong Dollar\nIDR,Indonesian Rupiah\nILS,Israeli New Sheqel\nINR,Indian Rupee\n" +
                "KRW,South Korean Won\nMXN,Mexican Peso\nMYR,Malaysian Ringgit\nNZD,New Zealand Dollar\n" +
                "PHP,Philippine Peso\nSGD,Singapore Dollar\nTHB,Thai Baht\nZAR,South African Rand";

        // Populate the spinners
        populateSpinner(sp, currencyData);
        populateSpinner(sp2, currencyData);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency();

            }
        });
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("username", username);
//    }


    private void convertCurrency() {
        final String amount = et.getText().toString();
        final String fromCurrency = sp.getSelectedItem().toString();
        final String toCurrency = sp2.getSelectedItem().toString();
        final String apiKey = "fca_live_lqEkYjov6Oz0qQqvh6PB7xboHK4AuDiU0jgTjWPv";

        String url = "https://api.freecurrencyapi.com/v1/latest?apikey=" + apiKey + "&base_currency=" + fromCurrency;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            double rate = data.getDouble(toCurrency);
                            double convertedAmount = Double.parseDouble(amount) * rate;

                            // Update UI with results
                            tv.setText("Result: " + String.format("%.2f", convertedAmount) + " " + toCurrency);

                            // You can also log the rate if needed
                            System.out.println("Value of " + toCurrency + ": " + rate);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void populateSpinner(Spinner spinner, String currencyData) {
        String[] lines = currencyData.trim().split("\n");
        List<String> currencyCodeList = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            currencyCodeList.add(parts[0]); // Add the currency code to the list
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyCodeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}