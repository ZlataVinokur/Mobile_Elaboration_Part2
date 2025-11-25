package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.repository.WeatherRepository;

public class AnalyzeWeatherUseCase {
    private WeatherRepository weatherRepository;

    public AnalyzeWeatherUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public String execute(double lat, double lon) {
        return weatherRepository.getCurrentWeather(lat, lon);
    }
}