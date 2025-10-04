package ru.mirea.vinokurovazo.moodycat.domain.repository;

public interface WeatherRepository {
    WeatherInfo getCurrentWeather(double lat, double lon);
}
