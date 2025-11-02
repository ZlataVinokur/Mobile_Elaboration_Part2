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
В этой части задания была внедрена архитектура MVVM в приложенин. В слое presentation создана MainViewModel, которая наследуется от Android ViewModel и управляет данными для MainActivity. Для наблюдения за изменениями состояния реализовано использование LiveData, включая MediatorLiveData для комбинирования информации из разных источников.

```
    public MainViewModel(GetHistoryUseCase getHistoryUseCase,
                         TrackMoodUseCase trackMoodUseCase,
                         AnalyzeWeatherUseCase analyzeWeatherUseCase) {
        this.getHistoryUseCase = getHistoryUseCase;
        this.trackMoodUseCase = trackMoodUseCase;
        this.analyzeWeatherUseCase = analyzeWeatherUseCase;

        setupMediatorLiveData();
        loadData();
    }

    private void setupMediatorLiveData() {
        combinedData.addSource(catsLiveData, cats -> combineData());
        combinedData.addSource(moodsLiveData, moods -> combineData());
        combinedData.addSource(weatherLiveData, weather -> combineData());
    }

    private void combineData() {
        List<Cat> cats = catsLiveData.getValue();
        List<Mood> moods = moodsLiveData.getValue();
        String weather = weatherLiveData.getValue();

        CombinedData data = new CombinedData(cats, moods, weather);
        combinedData.setValue(data);
    }
```

ViewModel использует Use Cases для получения данных из domain слоя, а Activity наблюдает за изменениями через LiveData observers. 

```
private void setupObservers() {

        mainViewModel.isLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        mainViewModel.getCombinedData().observe(this, combinedData -> {
            if (combinedData != null) {
                if (combinedData.weather != null) {
                    TextView weatherTemp = findViewById(R.id.tv_weather_temp);
                    weatherTemp.setText("Температура: " + combinedData.weather);
                }

                if (!authRepository.isGuest() && combinedData.moods != null && !combinedData.moods.isEmpty()) {
                    TextView catMood = findViewById(R.id.tv_cat_mood);
                    catMood.setText("Текущее настроение: " + combinedData.moods.get(0).getMood());
                }
            }
        });


        mainViewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
```

Для создания ViewModel с зависимостями реализована MainViewModelFactory.

```
public class HistoryViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public HistoryViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            CatRepositoryImpl catRepository = new CatRepositoryImpl(context);
            GetHistoryUseCase getHistoryUseCase = new GetHistoryUseCase(catRepository);

            @SuppressWarnings("unchecked")
            T result = (T) new HistoryViewModel(getHistoryUseCase);
            return result;
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
```
Интерфейс MainActivity был обновлен для отображения комбинированных данных через MediatorLiveData, показывая информацию о котах, их настроениях и погодных условиях вместе.

**Приложение:**
<img width="1906" height="1195" alt="image" src="https://github.com/user-attachments/assets/4e0a1a3b-a41c-435f-9aec-49a82778d37a" />

Для возможности просмотра истории был реализован HistoryActivity также по логике MVVM с использованием RecyclerView для отображения списка.

**Приложение:**
<img width="1919" height="1198" alt="image" src="https://github.com/user-attachments/assets/6d991d82-0471-4984-b7d1-f5d28ac5ed9e" />
