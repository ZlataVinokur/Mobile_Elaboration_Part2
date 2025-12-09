package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.vinokurovazo.moodycat.R;
import ru.mirea.vinokurovazo.data.repository.AuthRepositoryImpl;
import ru.mirea.vinokurovazo.data.storage.sharedprefs.SharedPrefsStorage;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AuthRepositoryImpl authRepository = new AuthRepositoryImpl(requireContext());
        SharedPrefsStorage sharedPrefsStorage = new SharedPrefsStorage(requireContext());

        setupUI(view, authRepository, sharedPrefsStorage);
    }

    private void setupUI(View view, AuthRepositoryImpl authRepository, SharedPrefsStorage sharedPrefsStorage) {
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvUserType = view.findViewById(R.id.tv_user_type);

        String userId = sharedPrefsStorage.getUserId();
        String userEmail = sharedPrefsStorage.getUserEmail();
        boolean isGuest = sharedPrefsStorage.isGuest();

        tvEmail.setText("Email: " + userEmail);
        tvUserType.setText("Тип пользователя: " + (isGuest ? "Гость" : "Зарегистрированный"));

    }
}