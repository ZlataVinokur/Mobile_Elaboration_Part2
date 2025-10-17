package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.models.Movie;
import ru.mirea.vinokurovazo.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}