package ru.mirea.vinokurovazo.data.storage.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}

class WeatherResponse {
    Main main;
    Weather[] weather;

    static class Main {
        double temp;
        int humidity;
    }

    static class Weather {
        String main;
        String description;
    }
}