package ru.mirea.vinokurovazo.domain.repository;

import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.Mood;

import java.util.List;

public interface CatRepository {
    List<Cat> getCats();
    Cat getCatById(int id);
    void addCat(Cat cat);
    void addMood(int catId, String mood, String weather);
    List<Mood> getMoodHistory(int catId); // Используем доменную модель Mood
}