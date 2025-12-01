package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;

public class HistoryViewModel extends ViewModel {
    private CatRepositoryImpl catRepository;
    private LiveData<List<Mood>> moodsLiveData;

    public HistoryViewModel(CatRepositoryImpl catRepository) {
        this.catRepository = catRepository;
        this.moodsLiveData = catRepository.getAllMoodsLiveData();
    }

    public LiveData<List<Mood>> getMoods() {
        return moodsLiveData;
    }

    public void addMood(String catName, String mood, String weather) {
        int catId = catName.equals("Барсик") ? 1 : 2;
        catRepository.addMood(catId, mood, weather);
    }

    public void updateMood(int moodId, String catName, String mood, String weather) {
        catRepository.updateMood(moodId, catName, mood, weather);
    }

    public void deleteMood(int moodId) {
        catRepository.deleteMood(moodId);
    }

}