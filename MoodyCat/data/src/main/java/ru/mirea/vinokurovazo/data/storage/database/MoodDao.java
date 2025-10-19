package ru.mirea.vinokurovazo.data.storage.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoodDao {
    @Query("SELECT * FROM moods ORDER BY timestamp DESC")
    List<MoodEntity> getAllMoods();

    @Query("SELECT * FROM moods WHERE catId = :catId ORDER BY timestamp DESC")
    List<MoodEntity> getMoodsByCatId(int catId);

    @Insert
    void insertMood(MoodEntity mood);

    @Query("DELETE FROM moods WHERE id = :id")
    void deleteMood(int id);
}