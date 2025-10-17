package ru.mirea.vinokurovazo.domain.repository;

import ru.mirea.vinokurovazo.domain.models.Movie;

public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}