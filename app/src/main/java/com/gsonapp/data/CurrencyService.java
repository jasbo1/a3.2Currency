package com.gsonapp.data;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyService {
    @GET("latest")
    Call<JsonObject> currency(@Query("access_key")String key);
}
