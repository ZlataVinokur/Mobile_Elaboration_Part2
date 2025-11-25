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
    private List<Cat> mockCats;
    private ExecutorService executor;

    public CatRepositoryImpl(Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
        initializeMockData();
    }

    private void initializeMockData() {
        mockCats = new ArrayList<>();
        mockCats.add(new Cat(1, "Барсик", "Веселый кот", "cat1.jpg", "2024-01-15"));
        mockCats.add(new Cat(2, "Мурка", "Сонный кот", "cat2.jpg", "2024-01-14"));

        executor.execute(() -> {
            if (appDatabase.moodDao().getAllMoods().isEmpty()) {
                // Используем основной конструктор с id=0 (autoGenerate=true сам установит id)
                MoodEntity initialMood1 = new MoodEntity(0, 1, "Барсик", "Счастливый", "Солнечно",
                        System.currentTimeMillis(), "cat_happy");
                MoodEntity initialMood2 = new MoodEntity(0, 2, "Мурка", "Сонный", "Облачно",
                        System.currentTimeMillis(), "cat_sleepy");
                MoodEntity initialMood3 = new MoodEntity(0, 1, "Барсик", "Грустный", "Дождь",
                        System.currentTimeMillis() - 86400000, "cat_sad");
                MoodEntity initialMood4 = new MoodEntity(0, 2, "Мурка", "Игривый", "Ясно",
                        System.currentTimeMillis() - 172800000, "cat_playful");

                appDatabase.moodDao().insertMood(initialMood1);
                appDatabase.moodDao().insertMood(initialMood2);
                appDatabase.moodDao().insertMood(initialMood3);
                appDatabase.moodDao().insertMood(initialMood4);
            }
        });
    }

    @Override
    public List<Cat> getCats() {
        return mockCats;
    }

    @Override
    public Cat getCatById(int id) {
        for (Cat cat : mockCats) {
            if (cat.getId() == id) {
                return cat;
            }
        }
        return null;
    }

    @Override
    public void addCat(Cat cat) {
        mockCats.add(cat);
    }

    @Override
    public void addMood(int catId, String mood, String weather) {
        Cat cat = getCatById(catId);
        if (cat != null) {
            MoodEntity moodEntity = new MoodEntity(
                    0, // id будет сгенерирован автоматически
                    catId,
                    cat.getName(),
                    mood,
                    weather,
                    System.currentTimeMillis(),
                    getMoodImage(mood)
            );

            executor.execute(() -> {
                appDatabase.moodDao().insertMood(moodEntity);
            });
        }
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

    @Override
    public List<Mood> getMoodHistory(int catId) {
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

    public LiveData<List<Mood>> getAllMoodsLiveData() {
        MutableLiveData<List<Mood>> liveData = new MutableLiveData<>();

        executor.execute(() -> {
            List<MoodEntity> moodEntities = appDatabase.moodDao().getAllMoods();
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
            liveData.postValue(moods);
        });

        return liveData;
    }
}