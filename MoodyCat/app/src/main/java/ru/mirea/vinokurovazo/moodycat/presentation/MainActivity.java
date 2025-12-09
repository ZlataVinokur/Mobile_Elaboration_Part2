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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthRepositoryImpl authRepository = new AuthRepositoryImpl(this);
        if (!authRepository.isLoggedIn()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        if (authRepository.isGuest()) {
            navView.getMenu().findItem(R.id.navigation_history).setVisible(false);
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_history,
                R.id.navigation_profile
        ).build();

        NavigationUI.setupWithNavController(navView, navController);
    }
}
//skunkfuck@eee.com,12345aaa