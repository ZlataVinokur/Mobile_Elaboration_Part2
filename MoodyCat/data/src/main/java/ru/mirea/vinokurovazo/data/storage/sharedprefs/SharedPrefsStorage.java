package ru.mirea.vinokurovazo.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsStorage {
    private static final String PREFS_NAME = "moodycat_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_GUEST = "is_guest";
    private static final String KEY_LAST_CAT_ID = "last_cat_id";

    private SharedPreferences sharedPreferences;

    public SharedPrefsStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserSession(String uid, String email, boolean isGuest) {
        sharedPreferences.edit()
                .putString(KEY_USER_ID, uid)
                .putString(KEY_USER_EMAIL, email)
                .putBoolean(KEY_IS_GUEST, isGuest)
                .apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    public boolean isGuest() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }


    public void clearUserSession() {
        sharedPreferences.edit().clear().apply();
    }
}