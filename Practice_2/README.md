Отчет по практической работе №2
----
В рамках данной работы были изучены модели данных на разных слоях программного обеспечения, также основной проект был переработан под модульную структуру для достижения принципов "чистой архитектуры".

Задание №1
--
В директории data был создан пакет Storage и в нем интерфейс MovieStorage с get и save. Далее была создана реализация данного интерфейса SharedPrefMovieStorage:

```
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
```
Также в data была создана модель Movie:

```
public class Movie {
    private int id;
    private String name;
    private String localDate;

    public Movie(int id, String name, String localDate) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocalDate() {
        return localDate;
    }
}
```
Далее в проекте были созданы новые модули app, domain, data, настроены зависимости:
<img width="654" height="1112" alt="image" src="https://github.com/user-attachments/assets/91795574-c258-4954-9398-3224b9d6ee12" />

**Приложение:**
<img width="1919" height="1113" alt="image" src="https://github.com/user-attachments/assets/21e422cc-dcb9-4453-9c30-50fc54f63908" />


Задание №2
--
**Приложение:**



