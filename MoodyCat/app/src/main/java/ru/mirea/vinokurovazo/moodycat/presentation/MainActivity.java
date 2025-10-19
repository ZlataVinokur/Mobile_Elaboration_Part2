package ru.mirea.vinokurovazo.moodycat.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;
import ru.mirea.vinokurovazo.data.repository.WeatherRepositoryImpl;
import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.repository.AuthRepository;
import ru.mirea.vinokurovazo.domain.usecases.AnalyzeWeatherUseCase;
import ru.mirea.vinokurovazo.domain.usecases.ManageCatUseCase;
import ru.mirea.vinokurovazo.domain.usecases.TrackMoodUseCase;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;
import ru.mirea.vinokurovazo.moodycat.R;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ru.mirea.vinokurovazo.data.repository.AuthRepositoryImpl;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MoodyCatTest";
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация репозиториев
        Context context = this;
        authRepository = new AuthRepositoryImpl(context);

        // Проверяем авторизацию
        if (!authRepository.isLoggedIn()) {
            // Если не авторизован, возвращаемся на AuthActivity
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }

        setupUI();
        testRepositories();
    }

    private void setupUI() {
        Button logoutButton = findViewById(R.id.btn_logout);
        TextView welcomeText = findViewById(R.id.tv_welcome);

        // Устанавливаем приветствие
        welcomeText.setText("Добро пожаловать в MoodyCat!");

        // Кнопка выхода
        logoutButton.setOnClickListener(v -> {
            authRepository.logout();
            Toast.makeText(this, "Вы вышли из системы", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });

        // Кнопка для просмотра котов
        Button viewCatsButton = findViewById(R.id.btn_view_cats);
        viewCatsButton.setOnClickListener(v -> {
            // Здесь будет переход к списку котов
            Toast.makeText(this, "Функция в разработке", Toast.LENGTH_SHORT).show();
        });

        // Кнопка для добавления настроения
        Button addMoodButton = findViewById(R.id.btn_add_mood);
        addMoodButton.setOnClickListener(v -> {
            // Здесь будет добавление настроения
            Toast.makeText(this, "Функция в разработке", Toast.LENGTH_SHORT).show();
        });
    }

    private void testRepositories() {
        try {
            CatRepositoryImpl catRepository = new CatRepositoryImpl(this);
            WeatherRepositoryImpl weatherRepository = new WeatherRepositoryImpl();

            GetHistoryUseCase getHistoryUseCase = new GetHistoryUseCase(catRepository);
            TrackMoodUseCase trackMoodUseCase = new TrackMoodUseCase(catRepository);
            ManageCatUseCase manageCatUseCase = new ManageCatUseCase(catRepository);
            AnalyzeWeatherUseCase analyzeWeatherUseCase = new AnalyzeWeatherUseCase(weatherRepository);

            // Тестирование в фоновом потоке чтобы не блокировать UI
            new Thread(() -> {
                // Вывод списка
                Log.d(TAG, "~ Список котов ~");
                var cats = getHistoryUseCase.execute();
                for (var cat : cats) {
                    Log.d(TAG, cat.getId() + ": " + cat.getName());
                }

                // Тестирование погоды
                String weather = analyzeWeatherUseCase.execute(55.7558, 37.6173);
                Log.d(TAG, "Погода в Москве: " + weather);

                // Выводим в UI потоке
                runOnUiThread(() -> {
                    TextView debugText = findViewById(R.id.tv_debug);
                    debugText.setText("Загружено котов: " + cats.size() + "\nПогода: " + weather);
                });
            }).start();

        } catch (Exception e) {
            Log.e(TAG, "Ошибка в тестировании репозиториев", e);
            Toast.makeText(this, "Ошибка инициализации: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
//skunkfuck@eee.com,12345aaa