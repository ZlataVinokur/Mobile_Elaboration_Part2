package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import java.util.List;

import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.Mood;

public class MainViewState {
    private final List<Cat> cats;
    private final List<Mood> moods;
    private final String weather;
    private final boolean isLoading;
    private final String error;

    public MainViewState(List<Cat> cats, List<Mood> moods, String weather,
                         boolean isLoading, String error) {
        this.cats = cats;
        this.moods = moods;
        this.weather = weather;
        this.isLoading = isLoading;
        this.error = error;
    }

    public List<Cat> getCats() { return cats; }
    public List<Mood> getMoods() { return moods; }
    public String getWeather() { return weather; }
    public boolean isLoading() { return isLoading; }
    public String getError() { return error; }
    public boolean hasError() { return error != null && !error.isEmpty(); }
}