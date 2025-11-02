package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;

public class HistoryViewModel extends ViewModel {
    private final GetHistoryUseCase getHistoryUseCase;
    private final MutableLiveData<List<Mood>> moodsLiveData = new MutableLiveData<>();

    public HistoryViewModel(GetHistoryUseCase getHistoryUseCase) {
        this.getHistoryUseCase = getHistoryUseCase;
        loadMoods();
    }

    private void loadMoods() {
        new Thread(() -> {
            try {
                List<Mood> moods = createMockMoods();
                moodsLiveData.postValue(moods);
            } catch (Exception e) {
                moodsLiveData.postValue(new ArrayList<>());
            }
        }).start();
    }

    private List<Mood> createMockMoods() {
        List<Mood> moods = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        moods.add(new Mood(1, 1, "Барсик", "Счастливый", "Солнечно", currentTime));
        moods.add(new Mood(2, 1, "Барсик", "Игривый", "Ясно", currentTime - 86400000));
        moods.add(new Mood(3, 1, "Барсик", "Сонный", "Облачно", currentTime - 172800000));
        moods.add(new Mood(4, 1, "Барсик", "Грустный", "Дождь", currentTime - 259200000));
        return moods;
    }

    public LiveData<List<Mood>> getMoods() {
        return moodsLiveData;
    }
}