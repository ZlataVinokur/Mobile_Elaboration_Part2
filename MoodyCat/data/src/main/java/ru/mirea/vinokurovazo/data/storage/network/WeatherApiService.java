package ru.mirea.vinokurovazo.data.storage.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}