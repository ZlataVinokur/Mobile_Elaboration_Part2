package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;

import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;

public class HistoryViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public HistoryViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            CatRepositoryImpl catRepository = new CatRepositoryImpl(context);
            GetHistoryUseCase getHistoryUseCase = new GetHistoryUseCase(catRepository);

            @SuppressWarnings("unchecked")
            T result = (T) new HistoryViewModel(getHistoryUseCase);
            return result;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}