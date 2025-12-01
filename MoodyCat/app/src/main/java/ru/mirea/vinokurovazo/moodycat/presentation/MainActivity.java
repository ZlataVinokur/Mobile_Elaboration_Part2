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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthRepositoryImpl authRepository = new AuthRepositoryImpl(this);

        if (!authRepository.isLoggedIn()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
    }
}
//skunkfuck@eee.com,12345aaa