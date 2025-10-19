package ru.mirea.vinokurovazo.domain.model;

public class Mood {
    private int id;
    private int catId;
    private String catName;
    private String mood;
    private String weather;
    private long timestamp;

    public Mood(int id, int catId, String catName, String mood, String weather, long timestamp) {
        this.id = id;
        this.catId = catId;
        this.catName = catName;
        this.mood = mood;
        this.weather = weather;
        this.timestamp = timestamp;
    }

    // Геттеры
    public int getId() { return id; }
    public int getCatId() { return catId; }
    public String getCatName() { return catName; }
    public String getMood() { return mood; }
    public String getWeather() { return weather; }
    public long getTimestamp() { return timestamp; }
}