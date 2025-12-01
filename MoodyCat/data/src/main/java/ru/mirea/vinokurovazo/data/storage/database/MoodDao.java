package ru.mirea.vinokurovazo.data.storage.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MoodDao {
    @Query("SELECT * FROM moods ORDER BY timestamp DESC")
    LiveData<List<MoodEntity>> getAllMoodsLiveData();

    @Query("SELECT * FROM moods ORDER BY timestamp DESC")
    List<MoodEntity> getAllMoods(); // для обратной совместимости

    @Query("SELECT * FROM moods WHERE cat_id = :catId ORDER BY timestamp DESC")
    List<MoodEntity> getMoodsByCatId(int catId);

    @Insert
    void insertMood(MoodEntity mood);

    @Update
    void updateMood(MoodEntity mood);

    @Query("DELETE FROM moods WHERE id = :id")
    void deleteMood(int id);

    @Query("DELETE FROM moods")
    void deleteAllMoods();
}