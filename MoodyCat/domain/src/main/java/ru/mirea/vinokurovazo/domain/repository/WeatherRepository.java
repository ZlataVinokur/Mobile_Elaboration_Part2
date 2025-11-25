package ru.mirea.vinokurovazo.domain.repository;

public interface WeatherRepository {
    String getWeatherInfo(String location);
    String getCurrentWeather(double lat, double lon);
}