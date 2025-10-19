package ru.mirea.vinokurovazo.data.storage.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity(tableName = "moods")
public class MoodEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int catId;
    public String catName;
    public String mood;
    public String weather;
    public long timestamp;

    public MoodEntity(int catId, String catName, String mood, String weather, long timestamp) {
        this.catId = catId;
        this.catName = catName;
        this.mood = mood;
        this.weather = weather;
        this.timestamp = timestamp;
    }
}