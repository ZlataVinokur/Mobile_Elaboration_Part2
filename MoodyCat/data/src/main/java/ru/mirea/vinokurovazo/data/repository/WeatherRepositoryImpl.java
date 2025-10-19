package ru.mirea.vinokurovazo.data.repository;

import ru.mirea.vinokurovazo.domain.repository.WeatherRepository;
import ru.mirea.vinokurovazo.data.storage.network.NetworkApi;

public class WeatherRepositoryImpl implements WeatherRepository {
    private NetworkApi networkApi;

    public WeatherRepositoryImpl() {
        this.networkApi = new NetworkApi();
    }

    @Override
    public String getWeatherInfo(String location) {
        return networkApi.getMockWeatherData();
    }

    @Override
    public String getCurrentWeather(double lat, double lon) {
        // Моковые данные для демонстрации
        return "Температура: 20°C, Влажность: 65%, Состояние: Солнечно";
    }
}