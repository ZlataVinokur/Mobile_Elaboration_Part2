package ru.mirea.vinokurovazo.moodycat.presentation.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;

import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;

public class HistoryViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public HistoryViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            CatRepositoryImpl catRepository = new CatRepositoryImpl(context);
            return (T) new HistoryViewModel(catRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}