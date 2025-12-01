package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;
import ru.mirea.vinokurovazo.domain.usecases.TrackMoodUseCase;
import ru.mirea.vinokurovazo.domain.usecases.AnalyzeWeatherUseCase;
import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;
import ru.mirea.vinokurovazo.data.repository.WeatherRepositoryImpl;

public class MainViewModel extends ViewModel {
    private final GetHistoryUseCase getHistoryUseCase;
    private final TrackMoodUseCase trackMoodUseCase;
    private final AnalyzeWeatherUseCase analyzeWeatherUseCase;
    private final WeatherRepositoryImpl weatherRepository;

    private final MutableLiveData<List<Mood>> moodsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> weatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public MainViewModel(GetHistoryUseCase getHistoryUseCase,
                         TrackMoodUseCase trackMoodUseCase,
                         AnalyzeWeatherUseCase analyzeWeatherUseCase,
                         WeatherRepositoryImpl weatherRepository) {
        this.getHistoryUseCase = getHistoryUseCase;
        this.trackMoodUseCase = trackMoodUseCase;
        this.analyzeWeatherUseCase = analyzeWeatherUseCase;
        this.weatherRepository = weatherRepository;

        loadData();
    }

    private void loadData() {
        isLoadingLiveData.setValue(true);

        new Thread(() -> {
            try {
                List<Mood> moods = ((CatRepositoryImpl) getHistoryUseCase.getRepository()).getAllMoods();
                moodsLiveData.postValue(moods);

                String weather = analyzeWeatherUseCase.execute(55.7558, 37.6173);
                weatherLiveData.postValue(weather);

                isLoadingLiveData.postValue(false);

            } catch (Exception e) {
                errorLiveData.postValue("Ошибка загрузки: " + e.getMessage());
                isLoadingLiveData.postValue(false);
            }
        }).start();
    }

    public WeatherRepositoryImpl getWeatherRepository() {
        return weatherRepository;
    }

    public LiveData<List<Mood>> getMoodsLiveData() {
        return moodsLiveData;
    }

    public LiveData<String> getWeatherLiveData() {
        return weatherLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoadingLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }
}