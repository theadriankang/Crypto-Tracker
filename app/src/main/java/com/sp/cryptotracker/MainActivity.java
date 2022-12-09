package com.sp.cryptotracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sp.cryptotracker.Adapter.CryptoAdapter;
import com.sp.cryptotracker.Adapter.CryptoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText searchEdt;
    private RecyclerView currenciesRV;
    private ProgressBar loadingPB;
    private ArrayList<CryptoModel> currencyRVModelArrayList;
    private CryptoAdapter currencyRVAdapter;
    private Button splashButton;
    private RelativeLayout CryptoRL;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getSupportActionBar().hide(); // Hide the top default bar

        searchEdt = findViewById(R.id.searchBar);
        currenciesRV = findViewById(R.id.CryptoRV);
        loadingPB = findViewById(R.id.PBLoading);

        splashButton = findViewById(R.id.toSplash);
        splashButton.setOnClickListener(showSplash);

        // cryptoRL = findViewById(R.id.cryptoRelativeLayout);
        // cryptoRL.setOnClickListener(cryptoSelect);

        currencyRVModelArrayList = new ArrayList<>();
        currencyRVAdapter = new CryptoAdapter(currencyRVModelArrayList, this);
        currenciesRV.setLayoutManager(new LinearLayoutManager(this));
        currenciesRV.setAdapter(currencyRVAdapter);

        getCurrencyData();

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCurrencies(s.toString());
            }
        });

    }

    private void filterCurrencies(String currency) {
        ArrayList<CryptoModel> filteredList = new ArrayList<>();
        for (CryptoModel item : currencyRVModelArrayList) {
            if (item.getName().toLowerCase().contains(currency.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()) {
            Toast.makeText(this, "No currency found for searched query", Toast.LENGTH_SHORT).show();
        } else {
            currencyRVAdapter.filterList(filteredList);
        }
    }

    private void getCurrencyData() {
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");        // Get Name of Cryptocurrency
                        String symbol = dataObj.getString("symbol");    // Get Symbol of Cryptocurrency

                        JSONObject quote = dataObj.getJSONObject("quote");      // Go into Quotes
                        JSONObject USD = quote.getJSONObject("USD");            // Under Quotes, Go into USD
                        double price = USD.getDouble("price");            // Get Price of Cryptocurrency
                        DecimalFormat twoDP = new DecimalFormat("0.00"); // Set Formatted Price
                        currencyRVModelArrayList.add(new CryptoModel(name, symbol, price));
                    }
                    currencyRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to get the data...", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failed to get the data...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY", "11614948-728f-48aa-9164-24d574126044");
                return headers;
            }

        };
        requestQueue.add(jsonObjectRequest);

    }

    View.OnClickListener showSplash = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.welcome);
            // startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        }
    };

    /*
    View.OnClickListener cryptoSelect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.crypto_description);
        }
    };
     */



}