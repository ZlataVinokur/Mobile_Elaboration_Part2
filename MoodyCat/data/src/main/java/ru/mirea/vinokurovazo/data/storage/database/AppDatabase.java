package ru.mirea.vinokurovazo.data.storage.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MoodEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MoodDao moodDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "moodycat_database"
                            ).fallbackToDestructiveMigration() // Добавляем для избежания миграционных ошибок
                            .allowMainThreadQueries() // Разрешаем запросы в главном потоке для отладки
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}