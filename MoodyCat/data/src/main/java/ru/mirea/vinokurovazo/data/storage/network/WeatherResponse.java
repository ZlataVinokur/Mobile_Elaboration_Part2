package ru.mirea.vinokurovazo.data.storage.network;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private Weather[] weather;

    @SerializedName("name")
    private String cityName;

    public static class Main {
        @SerializedName("temp")
        private double temp;

        public double getTemp() { return temp; }
    }

    public static class Weather {
        @SerializedName("description")
        private String description;

        public String getDescription() { return description; }
    }

    public Main getMain() { return main; }
    public Weather[] getWeather() { return weather; }
    public String getCityName() { return cityName; }
}