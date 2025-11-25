package ru.mirea.vinokurovazo.domain.model;

public class WeatherData {
    private String temperature;
    private String description;
    private String city;

    public WeatherData(String temperature, String description, String city) {
        this.temperature = temperature;
        this.description = description;
        this.city = city;
    }

    public String getTemperature() { return temperature; }
    public String getDescription() { return description; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return temperature + "°C, " + description;
    }
}