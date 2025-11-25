package ru.mirea.vinokurovazo.data.repository;

import ru.mirea.vinokurovazo.data.storage.ApiConfig;
import ru.mirea.vinokurovazo.domain.repository.WeatherRepository;
import ru.mirea.vinokurovazo.data.storage.network.NetworkApi;

import android.content.Context;
import ru.mirea.vinokurovazo.domain.model.WeatherData;
import ru.mirea.vinokurovazo.domain.repository.WeatherRepository;
import ru.mirea.vinokurovazo.data.storage.network.WeatherApiService;
import ru.mirea.vinokurovazo.data.storage.network.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepositoryImpl implements WeatherRepository {
    private WeatherApiService weatherApiService;
    private Context context;

    public WeatherRepositoryImpl(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApiService = retrofit.create(WeatherApiService.class);
    }

    @Override
    public String getWeatherInfo(String location) {
        return "Погода обновляется...";
    }

    @Override
    public String getCurrentWeather(double lat, double lon) {
        return "Загрузка...";
    }

    public void getWeatherFromApi(WeatherCallback callback) {
        Call<WeatherResponse> call = weatherApiService.getWeather(
                "Moscow",
                "metric",
                ApiConfig.WEATHER_API_KEY
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    double temp = weatherResponse.getMain().getTemp();
                    String description = weatherResponse.getWeather()[0].getDescription();
                    String city = weatherResponse.getCityName();

                    callback.onSuccess(temp + "°C, " + description);
                } else {
                    callback.onError("Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(String weatherInfo);
        void onError(String error);
    }

}