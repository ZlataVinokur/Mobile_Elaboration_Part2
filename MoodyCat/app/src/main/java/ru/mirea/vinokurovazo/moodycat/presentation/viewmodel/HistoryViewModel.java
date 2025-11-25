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
    private MutableLiveData<List<Mood>> moodsLiveData = new MutableLiveData<>();

    public HistoryViewModel(CatRepositoryImpl catRepository) {
        this.catRepository = catRepository;
        moodsLiveData.setValue(new ArrayList<>());
    }

    public LiveData<List<Mood>> getMoods() {
        return moodsLiveData;
    }

    public void addMood(String catName, String mood, String weather) {
        int catId = catName.equals("Барсик") ? 1 : 2;
        catRepository.addMood(catId, mood, weather);
        loadMoods();
    }

    public void loadMoods() {
        new Thread(() -> {
            List<Mood> moods = catRepository.getAllMoods();
            if (moods == null) {
                moods = new ArrayList<>();
            }
            moodsLiveData.postValue(moods);
        }).start();
    }
}