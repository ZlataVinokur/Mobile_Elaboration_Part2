package ru.mirea.vinokurovazo.moodycat.data.repository;

import ru.mirea.vinokurovazo.moodycat.data.test.TestDataSource;
import ru.mirea.vinokurovazo.moodycat.domain.repository.WeatherInfo;
import ru.mirea.vinokurovazo.moodycat.domain.repository.WeatherRepository;

public class WeatherRepositoryImpl implements WeatherRepository {
    @Override
    public WeatherInfo getCurrentWeather(double lat, double lon) {
        return TestDataSource.weather;
    }
}