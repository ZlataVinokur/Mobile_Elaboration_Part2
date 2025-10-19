package ru.mirea.vinokurovazo.data.test;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.vinokurovazo.domain.model.Cat;
import ru.mirea.vinokurovazo.domain.repository.WeatherInfo;

public class TestDataSource {
    public static List<Cat> Cats = new ArrayList<Cat>() {{
        add(new Cat(1, "кот рыжик", "часто философствует", "aaa.png", "2025-10-204"));
        add(new Cat(2, "мяу", "личный блог.", "bbb.png", "2025-10-04"));
    }};

    public static WeatherInfo weather = new WeatherInfo(18.5, "Солнечно");
}
