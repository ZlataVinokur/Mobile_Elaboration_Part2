package ru.mirea.vinokurovazo.domain.repository;

public interface WeatherRepository {
    String getWeatherInfo(String location);
    // Добавляем метод для AnalyzeWeatherUseCase
    String getCurrentWeather(double lat, double lon);
}