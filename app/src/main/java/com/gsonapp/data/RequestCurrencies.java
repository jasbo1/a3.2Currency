package com.gsonapp.data;

import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RequestCurrencies extends AsyncTask<String, Void, Void> {
    private ArrayList<String> ratesValues;


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {

        String url = strings[0];
        try {
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setChunkedStreamingMode(0);

            OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            writer.flush();
            writer.close();

            int status = httpURLConnection.getResponseCode();
            httpURLConnection.getInputStream();
            String response = parseResponse(httpURLConnection.getInputStream());




            JsonObject object = new Gson().fromJson(response,JsonObject.class);
            JsonObject rates = object.getAsJsonObject("rates");
            Object [] ratesTitles = rates.keySet().toArray();
            ratesValues = new ArrayList<>();


            for (Object ratesTitle  : ratesTitles) {
               ratesValues.add(String.valueOf(rates.getAsJsonPrimitive()));

            }

            Log.d("aaa", "bbb");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private String parseResponse(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
