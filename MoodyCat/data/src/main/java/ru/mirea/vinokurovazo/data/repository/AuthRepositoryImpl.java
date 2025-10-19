package ru.mirea.vinokurovazo.data.repository;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.vinokurovazo.domain.repository.AuthRepository;
import ru.mirea.vinokurovazo.data.storage.sharedprefs.SharedPrefsStorage;
import ru.mirea.vinokurovazo.data.storage.network.NetworkApi;

public class AuthRepositoryImpl implements AuthRepository {
    private final SharedPrefsStorage sharedPrefsStorage;
    private final NetworkApi networkApi;
    private final FirebaseAuth firebaseAuth;

    public AuthRepositoryImpl(Context context) {
        this.sharedPrefsStorage = new SharedPrefsStorage(context);
        this.networkApi = new NetworkApi();
        this.firebaseAuth = networkApi.getFirebaseAuth();
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            sharedPrefsStorage.saveUserSession(
                                    user.getUid(),
                                    user.getEmail(),
                                    false
                            );
                            callback.onSuccess();
                        }
                    } else {
                        String errorMessage = task.getException() != null ?
                                task.getException().getMessage() : "Неизвестная ошибка";
                        callback.onError("Ошибка входа: " + errorMessage);
                    }
                });
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            sharedPrefsStorage.saveUserSession(
                                    user.getUid(),
                                    user.getEmail(),
                                    false
                            );
                            callback.onSuccess();
                        }
                    } else {
                        String errorMessage = task.getException() != null ?
                                task.getException().getMessage() : "Неизвестная ошибка";
                        callback.onError("Ошибка регистрации: " + errorMessage);
                    }
                });
    }

    @Override
    public void guestLogin(AuthCallback callback) {
        sharedPrefsStorage.saveUserSession("guest_" + System.currentTimeMillis(), "Гость", true);
        callback.onSuccess();
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        sharedPrefsStorage.clearUserSession();
    }

    @Override
    public boolean isLoggedIn() {
        return sharedPrefsStorage.getUserId() != null || firebaseAuth.getCurrentUser() != null;
    }
}