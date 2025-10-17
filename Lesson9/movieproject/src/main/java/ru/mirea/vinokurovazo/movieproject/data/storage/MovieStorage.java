package ru.mirea.vinokurovazo.movieproject.data.storage;

import ru.mirea.vinokurovazo.movieproject.data.storage.models.Movie;

public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
}