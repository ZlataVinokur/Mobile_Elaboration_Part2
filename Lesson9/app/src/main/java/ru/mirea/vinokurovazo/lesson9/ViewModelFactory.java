package ru.mirea.vinokurovazo.lesson9;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.vinokurovazo.data.repository.MovieRepositoryImpl;
import ru.mirea.vinokurovazo.data.storage.SharedPrefMovieStorage;
import ru.mirea.vinokurovazo.domain.repository.MovieRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieRepository movieRepository = new MovieRepositoryImpl(context);
        return (T) new MainViewModel(movieRepository);
    }
}