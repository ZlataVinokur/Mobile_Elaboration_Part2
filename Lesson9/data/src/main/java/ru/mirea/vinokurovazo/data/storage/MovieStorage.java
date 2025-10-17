package ru.mirea.vinokurovazo.data.storage;

import ru.mirea.vinokurovazo.data.storage.models.Movie;

public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
}