package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.repository.AuthRepository;

public class GuestLoginUseCase {
    private final AuthRepository authRepository;

    public GuestLoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(AuthRepository.AuthCallback callback) {
        authRepository.guestLogin(callback);
    }
}