package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;
import ru.mirea.vinokurovazo.domain.usecases.TrackMoodUseCase;
import ru.mirea.vinokurovazo.domain.usecases.AnalyzeWeatherUseCase;

public class MainViewModel extends ViewModel {
    private final GetHistoryUseCase getHistoryUseCase;
    private final TrackMoodUseCase trackMoodUseCase;
    private final AnalyzeWeatherUseCase analyzeWeatherUseCase;

    private final MutableLiveData<List<Cat>> catsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Mood>> moodsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> weatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    // MediatorLiveData для комбинирования данных из разных источников
    private final MediatorLiveData<CombinedData> combinedData = new MediatorLiveData<>();

    public MainViewModel(GetHistoryUseCase getHistoryUseCase,
                         TrackMoodUseCase trackMoodUseCase,
                         AnalyzeWeatherUseCase analyzeWeatherUseCase) {
        this.getHistoryUseCase = getHistoryUseCase;
        this.trackMoodUseCase = trackMoodUseCase;
        this.analyzeWeatherUseCase = analyzeWeatherUseCase;

        setupMediatorLiveData();
        loadData();
    }

    private void setupMediatorLiveData() {
        // Комбинируем данные из котов и настроений
        combinedData.addSource(catsLiveData, cats -> combineData());
        combinedData.addSource(moodsLiveData, moods -> combineData());
        combinedData.addSource(weatherLiveData, weather -> combineData());
    }

    private void combineData() {
        List<Cat> cats = catsLiveData.getValue();
        List<Mood> moods = moodsLiveData.getValue();
        String weather = weatherLiveData.getValue();

        CombinedData data = new CombinedData(cats, moods, weather);
        combinedData.setValue(data);
    }

    private void loadData() {
        isLoadingLiveData.setValue(true);

        new Thread(() -> {
            try {
                // Получаем данные из разных источников
                List<Cat> cats = getHistoryUseCase.execute();
                String weather = analyzeWeatherUseCase.execute(55.7558, 37.6173);

                // Получаем настроения из Room
                List<Mood> moods = ((ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl)
                        getHistoryUseCase.getRepository()).getAllMoods();

                // Обновляем LiveData
                catsLiveData.postValue(cats);
                moodsLiveData.postValue(moods);
                weatherLiveData.postValue(weather);
                isLoadingLiveData.postValue(false);

            } catch (Exception e) {
                errorLiveData.postValue("Ошибка загрузки: " + e.getMessage());
                isLoadingLiveData.postValue(false);
            }
        }).start();
    }

    // Getters for LiveData
    public LiveData<CombinedData> getCombinedData() { return combinedData; }
    public LiveData<List<Cat>> getCats() { return catsLiveData; }
    public LiveData<List<Mood>> getMoods() { return moodsLiveData; }
    public LiveData<String> getWeather() { return weatherLiveData; }
    public LiveData<Boolean> isLoading() { return isLoadingLiveData; }
    public LiveData<String> getError() { return errorLiveData; }

    // Класс для комбинированных данных
    public static class CombinedData {
        public final List<Cat> cats;
        public final List<Mood> moods;
        public final String weather;

        public CombinedData(List<Cat> cats, List<Mood> moods, String weather) {
            this.cats = cats;
            this.moods = moods;
            this.weather = weather;
        }
    }
}