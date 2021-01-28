package com.example.weatherapp.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather?appid=581f6a05353689369bf33b094a666135&units=metric")
    Call<Example> getWeatherData(@Query("q") String name);
}
