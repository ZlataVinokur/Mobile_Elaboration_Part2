package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;

import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;
import ru.mirea.vinokurovazo.data.repository.WeatherRepositoryImpl;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;
import ru.mirea.vinokurovazo.domain.usecases.TrackMoodUseCase;
import ru.mirea.vinokurovazo.domain.usecases.AnalyzeWeatherUseCase;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public MainViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            CatRepositoryImpl catRepository = new CatRepositoryImpl(context);
            WeatherRepositoryImpl weatherRepository = new WeatherRepositoryImpl(context);

            GetHistoryUseCase getHistoryUseCase = new GetHistoryUseCase(catRepository);
            TrackMoodUseCase trackMoodUseCase = new TrackMoodUseCase(catRepository);
            AnalyzeWeatherUseCase analyzeWeatherUseCase = new AnalyzeWeatherUseCase(weatherRepository);

            @SuppressWarnings("unchecked")
            T result = (T) new MainViewModel(
                    getHistoryUseCase,
                    trackMoodUseCase,
                    analyzeWeatherUseCase,
                    weatherRepository
            );
            return result;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}