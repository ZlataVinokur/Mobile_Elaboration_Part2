package ru.mirea.vinokurovazo.movieproject.domain.usecases;

import ru.mirea.vinokurovazo.movieproject.domain.models.Movie;
import ru.mirea.vinokurovazo.movieproject.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}