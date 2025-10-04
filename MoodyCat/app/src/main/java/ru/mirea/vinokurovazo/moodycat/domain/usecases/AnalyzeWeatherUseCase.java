package ru.mirea.vinokurovazo.moodycat.domain.usecases;

import ru.mirea.vinokurovazo.moodycat.domain.repository.WeatherInfo;
import ru.mirea.vinokurovazo.moodycat.domain.repository.WeatherRepository;

public class AnalyzeWeatherUseCase {
    private final WeatherRepository repository;

    public AnalyzeWeatherUseCase(WeatherRepository repository) {
        this.repository = repository;
    }

    public WeatherInfo execute(double lat, double lon) {
        return repository.getCurrentWeather(lat, lon);
    }
}