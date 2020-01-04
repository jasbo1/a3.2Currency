package com.gsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gsonapp.data.RequestCurrencies;

public class MainActivity extends AppCompatActivity {
    private TextView tvShow;
    private EditText edInput;

    private String  URL = "http://data.fixer.io/api/latest?access_key=1fc2a9bd0a85d3520261791025761f74";
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = findViewById(R.id.tvShow);
        edInput = findViewById(R.id.edInput);
        edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                tvShow.setText(editable.toString());

            }
        });
        new RequestCurrencies().execute(URL);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,ratesTitles);
        spinner.setAdapter(arrayAdapter);
    }
}
