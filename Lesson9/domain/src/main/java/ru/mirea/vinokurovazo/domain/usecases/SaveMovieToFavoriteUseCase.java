package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.models.Movie;
import ru.mirea.vinokurovazo.domain.repository.MovieRepository;
public class SaveMovieToFavoriteUseCase {
    private MovieRepository movieRepository;
    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public boolean execute(Movie movie){
        return movieRepository.saveMovie(movie);
    }
}