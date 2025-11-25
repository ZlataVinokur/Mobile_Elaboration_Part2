package ru.mirea.vinokurovazo.data.storage.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.room.ColumnInfo;

@Entity(tableName = "moods")
public class MoodEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "cat_id")
    public int catId;

    @ColumnInfo(name = "cat_name")
    public String catName;

    @ColumnInfo(name = "mood")
    public String mood;

    @ColumnInfo(name = "weather")
    public String weather;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @ColumnInfo(name = "image_name")
    public String imageName;

    // Основной конструктор для Room
    public MoodEntity(int id, int catId, String catName, String mood, String weather, long timestamp, String imageName) {
        this.id = id;
        this.catId = catId;
        this.catName = catName;
        this.mood = mood;
        this.weather = weather;
        this.timestamp = timestamp;
        this.imageName = imageName;
    }

    // Дополнительный конструктор - помечаем как @Ignore
    @Ignore
    public MoodEntity(int catId, String catName, String mood, String weather, long timestamp) {
        this(0, catId, catName, mood, weather, timestamp, getMoodImage(mood));
    }

    // Дополнительный конструктор - помечаем как @Ignore
    @Ignore
    public MoodEntity(int catId, String catName, String mood, String weather, long timestamp, String imageName) {
        this(0, catId, catName, mood, weather, timestamp, imageName);
    }

    // Статический метод для определения изображения по настроению
    private static String getMoodImage(String mood) {
        if (mood == null) return "cat_default";

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

    // Геттеры для Room
    public int getId() { return id; }
    public int getCatId() { return catId; }
    public String getCatName() { return catName; }
    public String getMood() { return mood; }
    public String getWeather() { return weather; }
    public long getTimestamp() { return timestamp; }
    public String getImageName() { return imageName; }
}