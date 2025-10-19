package ru.mirea.vinokurovazo.domain.model;

public class User {
    private String uid;
    private String email;
    private boolean isGuest;

    public User(String uid, String email, boolean isGuest) {
        this.uid = uid;
        this.email = email;
        this.isGuest = isGuest;
    }

    // Геттеры
    public String getUid() { return uid; }
    public String getEmail() { return email; }
    public boolean isGuest() { return isGuest; }
}