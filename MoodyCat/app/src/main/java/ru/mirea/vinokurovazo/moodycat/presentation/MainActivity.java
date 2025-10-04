package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import ru.mirea.vinokurovazo.moodycat.R;
import ru.mirea.vinokurovazo.moodycat.data.repository.CatRepositoryImpl;
import ru.mirea.vinokurovazo.moodycat.domain.model.Cat;
import ru.mirea.vinokurovazo.moodycat.domain.usecases.ManageCatUseCase;
import ru.mirea.vinokurovazo.moodycat.domain.usecases.TrackMoodUseCase;
import ru.mirea.vinokurovazo.moodycat.domain.usecases.GetHistoryUseCase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MoodyCatTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CatRepositoryImpl repository = new CatRepositoryImpl();

        GetHistoryUseCase GetHistoryUseCase = new GetHistoryUseCase(repository);
        TrackMoodUseCase TrackMoodUseCase = new TrackMoodUseCase(repository);
        ManageCatUseCase ManageCatUseCase = new ManageCatUseCase(repository);

        //Вывод списка
        Log.d(TAG, "~ Список котов ~");
        List<Cat> Cats = GetHistoryUseCase.execute();
        for (Cat p : Cats) {
            Log.d(TAG, p.getId() + ": " + p.getName());
        }

        //Добавление нового
        Cat newCat = new Cat(
                3,
                "Кит",
                "Декоративный кот.",
                "ccc.png",
                "2025-10-04"
        );
        ManageCatUseCase.execute(newCat);

        //Повторная проверка списка
        Log.d(TAG, "~ После добавления ~");
        for (Cat p : GetHistoryUseCase.execute()) {
            Log.d(TAG, p.getId() + ": " + p.getName());
        }

        //Поиск настроения кота по его id ---
        Cat found = TrackMoodUseCase.execute(3);
        if (found != null) {
            Log.d(TAG, "Найден кот: " + found.getName() + " - " + found.getDescription());
        } else {
            Log.d(TAG, "Кота не найдено");
        }
    }
}