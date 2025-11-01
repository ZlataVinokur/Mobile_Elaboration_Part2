Отчет по практической работе №3
----
В рамках данной работы были изучены архитектурные шаблоны, также был сделан переход от прямого взаимодействия Activity с use cases к использованию паттерна MVVM с LiveData.

Задание №1
--
Был создан класс MainViewModel, унаследованный от Android ViewModel, для выноса бизнес-логики из Activity и управления состоянием интерфейса.

```
public class MainViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<String> favoriteMovie = new MutableLiveData<>();
    private static final String TAG = "MY_APP_VIEWMODEL";

    public MainViewModel(MovieRepository movieRepository) {
        Log.i(TAG, "=== MainViewModel CONSTRUCTOR called ===");
        this.movieRepository = movieRepository;
    }

    @Override
    protected void onCleared() {
        Log.i(TAG, "=== MainViewModel onCleared called ===");
        super.onCleared();
    }

    public MutableLiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }

    public void saveMovie(Movie movie) {
        Log.i(TAG, "saveMovie called with: " + movie.getName());
        Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        favoriteMovie.setValue("Save result: " + result);
    }

    public void getMovie() {
        Log.i(TAG, "getMovie called");
        Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
        favoriteMovie.setValue("My favorite movie is: " + movie.getName());
    }
}
```
Также была реализована ViewModelFactory, инкапсулирующая создание зависимостей MovieRepository и обеспечивающая правильную передачу данных в ViewModel.

```
public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieRepository movieRepository = new MovieRepositoryImpl(context);
        return (T) new MainViewModel(movieRepository);
    }
}
```
В MainActivity была удалена прямая работа с use cases, добавлена подписка на LiveData и перенаправление пользовательских действий во ViewModel.

```
public class MainViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<String> favoriteMovie = new MutableLiveData<>();
    private static final String TAG = "MY_APP_VIEWMODEL";

    public MainViewModel(MovieRepository movieRepository) {
        Log.i(TAG, "=== MainViewModel CONSTRUCTOR called ===");
        this.movieRepository = movieRepository;
    }

    @Override
    protected void onCleared() {
        Log.i(TAG, "=== MainViewModel onCleared called ===");
        super.onCleared();
    }

    public MutableLiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }
```
**Приложение:**

<img width="1919" height="1199" alt="image" src="https://github.com/user-attachments/assets/ded82f04-82d8-4aaa-8d5c-225baf2086cc" />

**Logcat при повороте экрана:**

<img width="1919" height="1199" alt="image" src="https://github.com/user-attachments/assets/124e7cfb-03f6-49bb-aaaf-8c34d92090aa" />

Контрольное задание
--





