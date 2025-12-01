package ru.mirea.vinokurovazo.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Mood implements Serializable {
    private int id;
    private int catId;
    private String catName;
    private String mood;
    private String weather;
    private long timestamp;
    private String imageName;

    public Mood(int id, int catId, String catName, String mood, String weather, long timestamp) {
        this.id = id;
        this.catId = catId;
        this.catName = catName;
        this.mood = mood;
        this.weather = weather;
        this.timestamp = timestamp;
        this.imageName = getMoodImage(mood);
    }

    public Mood(int id, int catId, String catName, String mood, String weather, long timestamp, String imageName) {
        this.id = id;
        this.catId = catId;
        this.catName = catName;
        this.mood = mood;
        this.weather = weather;
        this.timestamp = timestamp;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public int getId() { return id; }

    public String getCatName() { return catName; }
    public String getMood() { return mood; }
    public String getWeather() { return weather; }

    public Date getDate() { return new Date(timestamp); }

    private String getMoodImage(String mood) {
        switch (mood.toLowerCase()) {
            case "счастливый":
            case "веселый":
                return "cat_happy";
            case "грустный":
            case "печальный":
                return "cat_sad";
            case "сонный":
            case "уставший":
                return "cat_sleepy";
            case "игривый":
            case "активный":
                return "cat_playful";
            case "голодный":
                return "cat_hungry";
            default:
                return "cat_default";
        }
    }
}