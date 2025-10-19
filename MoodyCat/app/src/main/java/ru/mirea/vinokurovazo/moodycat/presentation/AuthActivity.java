package ru.mirea.vinokurovazo.moodycat.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.vinokurovazo.domain.repository.AuthRepository;
import ru.mirea.vinokurovazo.domain.usecases.LoginUseCase;
import ru.mirea.vinokurovazo.domain.usecases.RegisterUseCase;
import ru.mirea.vinokurovazo.domain.usecases.GuestLoginUseCase;
import ru.mirea.vinokurovazo.data.repository.AuthRepositoryImpl;
import ru.mirea.vinokurovazo.moodycat.R;

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

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(AuthActivity.this, "Ошибка: " + message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void register() {
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

        registerUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(AuthActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                    navigateToMain();
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(AuthActivity.this, "Ошибка: " + message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void guestLogin() {
        guestLoginUseCase.execute(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(AuthActivity.this, "Гостевой вход успешен!", Toast.LENGTH_SHORT).show();
                    navigateToMain();
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(AuthActivity.this, "Ошибка: " + message, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}