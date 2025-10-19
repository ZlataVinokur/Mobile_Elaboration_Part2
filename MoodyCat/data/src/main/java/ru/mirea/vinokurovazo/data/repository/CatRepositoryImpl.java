package ru.mirea.vinokurovazo.data.repository;

import android.content.Context;
import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.domain.repository.CatRepository;
import ru.mirea.vinokurovazo.data.storage.database.AppDatabase;
import ru.mirea.vinokurovazo.data.storage.database.MoodEntity;

import java.util.ArrayList;
import java.util.List;

public class CatRepositoryImpl implements CatRepository {
    private AppDatabase appDatabase;
    private List<Cat> mockCats;

    public CatRepositoryImpl(Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
        initializeMockData();
    }

    private void initializeMockData() {
        mockCats = new ArrayList<>();
        mockCats.add(new Cat(1, "Барсик", "Веселый кот", "cat1.jpg", "2024-01-15"));
        mockCats.add(new Cat(2, "Мурка", "Сонный кот", "cat2.jpg", "2024-01-14"));
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
            // Сохраняем в Room
            MoodEntity moodEntity = new MoodEntity(
                    catId,
                    cat.getName(),
                    mood,
                    weather,
                    System.currentTimeMillis()
            );
            new Thread(() -> {
                appDatabase.moodDao().insertMood(moodEntity);
            }).start();
        }
    }

    @Override
    public List<Mood> getMoodHistory(int catId) {
        // Получаем данные из Room и преобразуем в доменные модели
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
}