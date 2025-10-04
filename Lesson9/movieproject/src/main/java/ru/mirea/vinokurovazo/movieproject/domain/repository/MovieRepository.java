package ru.mirea.vinokurovazo.movieproject.domain.repository;

import ru.mirea.vinokurovazo.movieproject.domain.models.Movie;

public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}