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


Контрольное задание
--

Первым этапом этого задания была разработка макета разрабатываемого приложения:

<img width="691" height="988" alt="image" src="https://github.com/user-attachments/assets/84c48c7e-1ec7-4eec-ad5b-0f678669f69d" />

В проекте было выполнено разделение его на модули app, domain, data, и произведена настройка зависимостей, также к проекту был подключен firebase для реализации авторизации.

<img width="531" height="1017" alt="image" src="https://github.com/user-attachments/assets/78a0ef28-44cb-48e0-8398-8533103cf5f1" />

Реализованы три способа обработки данных: 
SharedPreferences с информацией о клиенте, 

```
private SharedPreferences sharedPreferences;

    public SharedPrefsStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserSession(String uid, String email, boolean isGuest) {
        sharedPreferences.edit()
                .putString(KEY_USER_ID, uid)
                .putString(KEY_USER_EMAIL, email)
                .putBoolean(KEY_IS_GUEST, isGuest)
                .apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    public boolean isGuest() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }
```
Room,

```
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity(tableName = "moods")
public class MoodEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int catId;
    public String catName;
    public String mood;
    public String weather;
    public long timestamp;

    public MoodEntity(int catId, String catName, String mood, String weather, long timestamp) {
        this.catId = catId;
        this.catName = catName;
        this.mood = mood;
        this.weather = weather;
        this.timestamp = timestamp;
    }
}
```
Класс для работы с сетью с замоканными данными

```
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
```

На уровне app был создан AuthActivity для реализации авторизации в приложении, была настроена точка входа в приложение. Были добавлены необходимые usecases.

```
public class AuthActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton, guestButton;

    private AuthRepository authRepository;
    private LoginUseCase loginUseCase;
    private RegisterUseCase registerUseCase;
    private GuestLoginUseCase guestLoginUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Инициализация репозитория и use cases
        authRepository = new AuthRepositoryImpl(this);
        loginUseCase = new LoginUseCase(authRepository);
        registerUseCase = new RegisterUseCase(authRepository);
        guestLoginUseCase = new GuestLoginUseCase(authRepository);

        if (authRepository.isLoggedIn()) {
            navigateToMain();
            return;
        }

        setupUI();
    }

    private void setupUI() {
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);
        guestButton = findViewById(R.id.btn_guest);

        loginButton.setOnClickListener(v -> login());
        registerButton.setOnClickListener(v -> register());
        guestButton.setOnClickListener(v -> guestLogin());
    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        loginUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(AuthActivity.this, "Успешный вход!", Toast.LENGTH_SHORT).show();
                    navigateToMain();
                });
            }
```

**Вход в приложение:**

<img width="1798" height="838" alt="image" src="https://github.com/user-attachments/assets/b5e3c466-c4d6-4f23-964e-7fb929913302" />
<img width="1803" height="870" alt="image" src="https://github.com/user-attachments/assets/bcaaf612-a566-44fa-aa14-91ba59406248" />

**Firebase:**

<img width="1302" height="499" alt="image" src="https://github.com/user-attachments/assets/06a53a7b-4141-4ed9-bab6-ea1d7781a4f2" />

**Гостевой вход в приложение:**

<img width="1807" height="844" alt="image" src="https://github.com/user-attachments/assets/c46669bb-1813-43d0-a2c4-d0adeab9e6bf" />



