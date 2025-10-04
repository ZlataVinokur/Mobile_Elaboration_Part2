package ru.mirea.vinokurovazo.moodycat.domain.repository;

public interface AuthRepository {
    boolean login(String username, String password);
    boolean isAuthorized();
}
