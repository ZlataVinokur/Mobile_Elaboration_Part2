package ru.mirea.vinokurovazo.data.storage.network;

import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private FirebaseAuth firebaseAuth;
    private WeatherApi weatherApi;

    public NetworkApi() {
        firebaseAuth = FirebaseAuth.getInstance();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public WeatherApi getWeatherApi() {
        return weatherApi;
    }

    // Моковые данные для демонстрации
    public String getMockWeatherData() {
        return "{\"temperature\": 20, \"condition\": \"Sunny\"}";
    }

    public String getMockCatData() {
        return "{\"cats\": [{\"id\": 1, \"name\": \"Барсик\", \"mood\": \"happy\"}]}";
    }
}