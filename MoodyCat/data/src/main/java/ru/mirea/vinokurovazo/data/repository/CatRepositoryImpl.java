package ru.mirea.vinokurovazo.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.domain.repository.CatRepository;
import ru.mirea.vinokurovazo.data.storage.database.AppDatabase;
import ru.mirea.vinokurovazo.data.storage.database.MoodEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CatRepositoryImpl implements CatRepository {
    private AppDatabase appDatabase;
    private ExecutorService executor;

    public CatRepositoryImpl(Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
        initializeMockDataOnce();
    }

    private void initializeMockDataOnce() {
        executor.execute(() -> {
            if (appDatabase.moodDao().getAllMoods().isEmpty()) {
                MoodEntity initialMood1 = new MoodEntity(0, 1, "Барсик", "Счастливый", "Солнечно",
                        System.currentTimeMillis(), "cat_happy");
                MoodEntity initialMood2 = new MoodEntity(0, 2, "Мурка", "Сонный", "Облачно",
                        System.currentTimeMillis(), "cat_sleepy");

                appDatabase.moodDao().insertMood(initialMood1);
                appDatabase.moodDao().insertMood(initialMood2);
            }
        });
    }


    @Override
    public List<Cat> getCats() {
        return new ArrayList<>();
    }

    @Override
    public Cat getCatById(int id) {
        return null; // Не используется
    }

    @Override
    public void addCat(Cat cat) {
        // Не используется
    }

    @Override
    public void addMood(int catId, String mood, String weather) {
        String catName = (catId == 1) ? "Барсик" : "Мурка";

        MoodEntity moodEntity = new MoodEntity(
                0,
                catId,
                catName,
                mood,
                weather,
                System.currentTimeMillis(),
                getMoodImage(mood)
        );

        executor.execute(() -> {
            appDatabase.moodDao().insertMood(moodEntity);
        });
    }

    private String getMoodImage(String mood) {
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

    // Основной метод для получения LiveData настроений
    public LiveData<List<Mood>> getAllMoodsLiveData() {
        MutableLiveData<List<Mood>> result = new MutableLiveData<>();

        // Преобразуем LiveData<MoodEntity> в LiveData<Mood>
        LiveData<List<MoodEntity>> entitiesLiveData = appDatabase.moodDao().getAllMoodsLiveData();

        // Наблюдаем за изменениями в Room и преобразуем их
        entitiesLiveData.observeForever(moodEntities -> {
            if (moodEntities != null) {
                List<Mood> moods = new ArrayList<>();
                for (MoodEntity entity : moodEntities) {
                    moods.add(new Mood(
                            entity.id,
                            entity.catId,
                            entity.catName,
                            entity.mood,
                            entity.weather,
                            entity.timestamp,
                            entity.imageName
                    ));
                }
                result.postValue(moods);
            }
        });

        return result;
    }

    public void updateMood(int moodId, String catName, String mood, String weather) {
        executor.execute(() -> {
            // Получаем все записи для поиска нужной
            List<MoodEntity> allMoods = appDatabase.moodDao().getAllMoods();
            MoodEntity existing = null;

            for (MoodEntity entity : allMoods) {
                if (entity.id == moodId) {
                    existing = entity;
                    break;
                }
            }

            if (existing != null) {
                int catId = catName.equals("Барсик") ? 1 : 2;

                MoodEntity updated = new MoodEntity(
                        moodId,
                        catId,
                        catName,
                        mood,
                        weather,
                        existing.timestamp, // Сохраняем оригинальное время
                        getMoodImage(mood)
                );
                appDatabase.moodDao().updateMood(updated);
            }
        });
    }

    public void deleteMood(int moodId) {
        executor.execute(() -> {
            appDatabase.moodDao().deleteMood(moodId);
        });
    }

    @Override
    public List<Mood> getMoodHistory(int catId) {
        // Синхронный метод для обратной совместимости
        List<MoodEntity> moodEntities = appDatabase.moodDao().getMoodsByCatId(catId);
        List<Mood> moods = new ArrayList<>();

        for (MoodEntity entity : moodEntities) {
            moods.add(new Mood(
                    entity.id,
                    entity.catId,
                    entity.catName,
                    entity.mood,
                    entity.weather,
                    entity.timestamp
            ));
        }
        return moods;
    }

    // Синхронный метод для обратной совместимости
    public List<Mood> getAllMoods() {
        List<MoodEntity> moodEntities = appDatabase.moodDao().getAllMoods();
        List<Mood> moods = new ArrayList<>();

        for (MoodEntity entity : moodEntities) {
            moods.add(new Mood(
                    entity.id,
                    entity.catId,
                    entity.catName,
                    entity.mood,
                    entity.weather,
                    entity.timestamp,
                    entity.imageName
            ));
        }
        return moods;
    }
}