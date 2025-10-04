package ru.mirea.vinokurovazo.moodycat.data.repository;

import ru.mirea.vinokurovazo.moodycat.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private boolean authorized = false;

    @Override
    public boolean login(String username, String password) {
        // Заглушка: принимает любые данные
        authorized = true;
        return true;
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }
}