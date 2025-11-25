package ru.mirea.vinokurovazo.moodycat.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.vinokurovazo.data.repository.CatRepositoryImpl;
import ru.mirea.vinokurovazo.data.repository.WeatherRepositoryImpl;
import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.model.WeatherData;
import ru.mirea.vinokurovazo.domain.repository.AuthRepository;
import ru.mirea.vinokurovazo.domain.usecases.AnalyzeWeatherUseCase;
import ru.mirea.vinokurovazo.domain.usecases.ManageCatUseCase;
import ru.mirea.vinokurovazo.domain.usecases.TrackMoodUseCase;
import ru.mirea.vinokurovazo.domain.usecases.GetHistoryUseCase;
import ru.mirea.vinokurovazo.moodycat.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ru.mirea.vinokurovazo.data.repository.AuthRepositoryImpl;

import android.widget.ProgressBar;

import androidx.lifecycle.ViewModelProvider;

import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.MainViewModel;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.MainViewState;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.MainViewModelFactory;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private AuthRepository authRepository;

    private ProgressBar progressBar;
    private Button logoutButton, viewCatsButton, addMoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authRepository = new AuthRepositoryImpl(this);

        if (!authRepository.isLoggedIn()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }

        setupViewModel();
        setupUI();
        setupObservers();
    }

    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(this);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    private void setupUI() {
        progressBar = findViewById(R.id.progress_bar);

        TextView catNameText = findViewById(R.id.tv_cat_name);
        TextView catMoodText = findViewById(R.id.tv_cat_mood);
        Button historyButton = findViewById(R.id.btn_history);
        Button weatherButton = findViewById(R.id.btn_weather);
        Button logoutButton = findViewById(R.id.btn_logout);


        boolean isGuest = authRepository.isGuest();

        if (isGuest) {
            catNameText.setText("Гость");
            catMoodText.setVisibility(View.GONE);
            historyButton.setVisibility(View.GONE);
        } else {
            catNameText.setText("Барсик");
            catMoodText.setText("Текущее настроение: Счастливый");
            historyButton.setVisibility(View.VISIBLE);
            catMoodText.setVisibility(View.VISIBLE);
        }

        historyButton.setOnClickListener(v -> {
            if (!isGuest) {
                startActivity(new Intent(this, HistoryActivity.class));
            }
        });

        weatherButton.setOnClickListener(v -> {
            updateWeather();
        });

        logoutButton.setOnClickListener(v -> logout());
    }

    private void updateWeather() {
        WeatherRepositoryImpl weatherRepo = mainViewModel.getWeatherRepository();

        weatherRepo.getWeatherFromApi(new WeatherRepositoryImpl.WeatherCallback() {
            @Override
            public void onSuccess(String weatherInfo) {
                runOnUiThread(() -> {
                    TextView weatherTemp = findViewById(R.id.tv_weather_temp);
                    weatherTemp.setText("Москва: " + weatherInfo);
                    Toast.makeText(MainActivity.this, "Погода обновлена!", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void logout() {
        authRepository.logout();
        Toast.makeText(this, "Вы вышли из системы", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    private void setupObservers() {
        progressBar.setVisibility(View.VISIBLE);

        mainViewModel.getMoodsLiveData().observe(this, moods -> {
            if (moods != null && !moods.isEmpty() && !authRepository.isGuest()) {
                TextView catMood = findViewById(R.id.tv_cat_mood);
                catMood.setText("Текущее настроение: " + moods.get(0).getMood());
            }
        });

        mainViewModel.getWeatherLiveData().observe(this, weather -> {
            if (weather != null) {
                TextView weatherTemp = findViewById(R.id.tv_weather_temp);
                weatherTemp.setText("Температура: " + weather);
            }
        });

        mainViewModel.isLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        mainViewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

}
//skunkfuck@eee.com,12345aaa