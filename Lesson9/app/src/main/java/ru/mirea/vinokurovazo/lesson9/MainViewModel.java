package ru.mirea.vinokurovazo.lesson9;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.vinokurovazo.domain.models.Movie;
import ru.mirea.vinokurovazo.domain.repository.MovieRepository;
import ru.mirea.vinokurovazo.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.vinokurovazo.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<String> favoriteMovie = new MutableLiveData<>();
    private static final String TAG = "MY_APP_VIEWMODEL";

    public MainViewModel(MovieRepository movieRepository) {
        Log.i(TAG, "=== MainViewModel CONSTRUCTOR called ===");
        this.movieRepository = movieRepository;
    }

    @Override
    protected void onCleared() {
        Log.i(TAG, "=== MainViewModel onCleared called ===");
        super.onCleared();
    }

    public MutableLiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }

    public void saveMovie(Movie movie) {
        Log.i(TAG, "saveMovie called with: " + movie.getName());
        Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        favoriteMovie.setValue("Save result: " + result);
    }

    public void getMovie() {
        Log.i(TAG, "getMovie called");
        Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
        favoriteMovie.setValue("My favorite movie is: " + movie.getName());
    }
}