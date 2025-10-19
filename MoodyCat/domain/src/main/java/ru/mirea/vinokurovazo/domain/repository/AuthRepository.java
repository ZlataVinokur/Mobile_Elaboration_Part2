package ru.mirea.vinokurovazo.domain.repository;

public interface AuthRepository {
    void login(String email, String password, AuthCallback callback);
    void register(String email, String password, AuthCallback callback);
    void guestLogin(AuthCallback callback);
    void logout();
    boolean isLoggedIn();

    // Объявляем интерфейс callback внутри AuthRepository
    interface AuthCallback {
        void onSuccess();
        void onError(String message);
    }
}