package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.vinokurovazo.moodycat.R;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.MainViewModel;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.MainViewModelFactory;
import ru.mirea.vinokurovazo.data.repository.AuthRepositoryImpl;

public class HomeFragment extends Fragment {
    private MainViewModel mainViewModel;
    private AuthRepositoryImpl authRepository;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authRepository = new AuthRepositoryImpl(requireContext());
        setupViewModel();
        setupUI(view);
        setupObservers();
    }

    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(requireContext());
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    private void setupUI(View view) {

        ImageView catImageView = view.findViewById(R.id.iv_cat_image);
        TextView catNameText = view.findViewById(R.id.tv_cat_name);
        Button weatherButton = view.findViewById(R.id.btn_weather);
        Button logoutButton = view.findViewById(R.id.btn_logout);

        boolean isGuest = authRepository.isGuest();

        if (isGuest) {
            catImageView.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            catImageView.setImageResource(R.drawable.cat_default);
        }


        weatherButton.setOnClickListener(v -> updateWeather(view));

        logoutButton.setOnClickListener(v -> logout());
    }

    private void updateWeather(View view) {
        if (mainViewModel.getWeatherRepository() != null) {
            mainViewModel.getWeatherRepository().getWeatherFromApi(
                    new ru.mirea.vinokurovazo.data.repository.WeatherRepositoryImpl.WeatherCallback() {
                        @Override
                        public void onSuccess(String weatherInfo) {
                            requireActivity().runOnUiThread(() -> {
                                TextView weatherTemp = view.findViewById(R.id.tv_weather_temp);
                                weatherTemp.setText("Температура: " + weatherInfo);
                                Toast.makeText(requireContext(), "Погода обновлена!", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
                            });
                        }
                    }
            );
        }
    }

    private void logout() {
        authRepository.logout();
        Toast.makeText(requireContext(), "Вы вышли из системы", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(requireActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void setupObservers() {
        mainViewModel.getMoodsLiveData().observe(getViewLifecycleOwner(), moods -> {
            View view = getView();

        });

        mainViewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (progressBar != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        mainViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }
}