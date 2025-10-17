package ru.mirea.vinokurovazo.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.vinokurovazo.data.storage.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {
    private static final String SHARED_PREFS_NAME = "movie_prefs";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_DATE = "movie_date";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Movie get() {
        String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, "unknown");
        int movieId = sharedPreferences.getInt(KEY_MOVIE_ID, -1);
        String movieDate = sharedPreferences.getString(KEY_MOVIE_DATE, "");
        return new Movie(movieId, movieName, movieDate);
    }

    @Override
    public boolean save(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        editor.putInt(KEY_MOVIE_ID, movie.getId());
        editor.putString(KEY_MOVIE_DATE, movie.getLocalDate());
        return editor.commit();
    }
}