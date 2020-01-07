package com.gsonapp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gsonapp.R;
import com.gsonapp.data.CurrencyConvert;
import com.gsonapp.data.RequestCurrencies;
import com.gsonapp.data.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gsonapp.BuildConfig.API_KEY;

public class MainActivity extends AppCompatActivity {
    private TextView tvShow;
    private EditText edInput;
    private ArrayList<String> ratesValues;
    private double spinner1, spinner2;
    Object[] ratesKey;

    private String URL = "http://data.fixer.io/api/latest?access_key=1fc2a9bd0a85d3520261791025761f74";
    Spinner spinnerOne, spinnerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow = findViewById(R.id.tvShow);
        edInput = findViewById(R.id.edInput);
        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        ClickListener();
        takeCurrency();
    }

    private void takeCurrency() {
        RetrofitBuilder
                .getService()
                .currency(API_KEY)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject object = response.body();
                        if (response.isSuccessful() && response.body() != null) {
                            JsonObject rates = object.getAsJsonObject("rates");
                            ratesKey = rates.keySet().toArray();
                            ratesValues = new ArrayList<>();
                            for (Object ratesObject : ratesKey) {
                                ratesValues.add(String.valueOf(rates.getAsJsonPrimitive(ratesObject.toString())));
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ratesKey);
                            spinnerOne.setAdapter(adapter);
                            spinnerTwo.setAdapter(adapter);
                        }
                    }


                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("asasdsd", "bbbbb");

                    }
                });
    }

    public void ClickListener() {
        if(edInput.getText().toString().trim().equalsIgnoreCase("")){
           edInput.setError("Enter FirstName");

        edInput.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable != null && editable.length() > 0) {
                    tvShow.setText(CurrencyConvert.mConveter(String.valueOf(editable),
                            spinner1,
                            spinner2));
                } else {
                    tvShow.setText("");
                }

            }

        });

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner1 = Double.parseDouble(ratesValues.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner2 = Double.parseDouble(ratesValues.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });

    }
}}




