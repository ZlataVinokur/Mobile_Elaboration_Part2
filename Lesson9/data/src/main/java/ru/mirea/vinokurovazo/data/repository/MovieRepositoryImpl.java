package ru.mirea.vinokurovazo.data.repository;

import android.content.Context;

import java.time.LocalDate;

import ru.mirea.vinokurovazo.data.storage.MovieStorage;
import ru.mirea.vinokurovazo.data.storage.SharedPrefMovieStorage;
import ru.mirea.vinokurovazo.domain.models.Movie;
import ru.mirea.vinokurovazo.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(Context context) {
        this.movieStorage = new SharedPrefMovieStorage(context);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        ru.mirea.vinokurovazo.data.storage.models.Movie dataMovie = mapToStorage(movie);
        return movieStorage.save(dataMovie);
    }

    @Override
    public Movie getMovie() {
        ru.mirea.vinokurovazo.data.storage.models.Movie dataMovie = movieStorage.get();
        return mapToDomain(dataMovie);
    }

    private ru.mirea.vinokurovazo.data.storage.models.Movie mapToStorage(Movie movie) {
        return new ru.mirea.vinokurovazo.data.storage.models.Movie(
                movie.getId(),
                movie.getName(),
                LocalDate.now().toString()
        );
    }

    private Movie mapToDomain(ru.mirea.vinokurovazo.data.storage.models.Movie movie) {
        return new Movie(movie.getId(), movie.getName());
    }
}