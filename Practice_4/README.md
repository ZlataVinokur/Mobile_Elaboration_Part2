Отчет по практической работе №4
----
В рамках данной работы была поставлена задача освоить три основных способа отображения списков в Android-приложениях.

Задание №1
--
Для ScrollViewApp реализован вертикальный скроллинг с отображением геометрической прогрессии, где каждый элемент динамически добавлялся через LayoutInflater.

**Приложение:**

<img width="1851" height="1030" alt="image" src="https://github.com/user-attachments/assets/def61bb3-a816-4a3d-b583-49cd8d12248a" />

Задание №2
--
В ListViewApp создан кастомный адаптер для отображения списка литературных произведений с авторами, что продемонстрировало работу с ArrayAdapter и переопределение метода getView.

**Приложение:**
<img width="1865" height="986" alt="image" src="https://github.com/user-attachments/assets/98cb7758-a0f7-4084-917f-89306c7b482b" />

Задание №3
--
Далее в RecyclerViewApp реализован ViewHolder и адаптер, отображающий исторические события с изображениями и описаниями.

**Приложение:**
<img width="1844" height="983" alt="image" src="https://github.com/user-attachments/assets/b33cb99f-f865-47c5-ad74-9e31cfee876c" />

В каждом из заданий в проект был приложен скрин экрана в res/drawable.

Контрольное задание
--
В этой части задания был создан новый макет карточки с изображением с CardView. В RecyclerView MoodAdapter теперь отображает изображения настроений и текстовые данные в карточках.

**Приложение:**
<img width="1841" height="1082" alt="image" src="https://github.com/user-attachments/assets/3ea138dc-3ba6-44dc-9777-7c2e5663eb2c" />

Добавлена система автоматического определения изображений по типу настроения кота. 
```
private String getMoodImage(String mood) {
        switch (mood.toLowerCase()) {
            case "счастливый":
            case "веселый":
                return "cat_happy";
            case "грустный":
            case "печальный":
                return "cat_sad";
            case "сонный":
            case "уставший":
                return "cat_sleepy";
            case "игривый":
            case "активный":
                return "cat_playful";
            case "голодный":
                return "cat_hungry";
            default:
                return "cat_default";
        }
    }
```
Добавлены примеры настроений с различными изображениями для демонстрации функциональности.
```
private List<Mood> createMockMoods() {
        List<Mood> moods = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        moods.add(new Mood(1, 1, "Барсик", "Счастливый", "Солнечно", currentTime));
        moods.add(new Mood(2, 1, "Барсик", "Игривый", "Ясно", currentTime - 86400000));
        moods.add(new Mood(3, 1, "Барсик", "Сонный", "Облачно", currentTime - 172800000));
        moods.add(new Mood(4, 1, "Барсик", "Грустный", "Дождь", currentTime - 259200000));
        return moods;
    }
```

Настроена работа с LiveData, реализовано наблюдение за изменениями данных и обновление интерфейса в реальном времени.

```
private void setupObservers() {
        progressBar.setVisibility(View.VISIBLE);

        mainViewModel.getMoodsLiveData().observe(this, moods -> {
            if (moods != null && !moods.isEmpty() && !authRepository.isGuest()) {
                TextView catMood = findViewById(R.id.tv_cat_mood);
                catMood.setText("Текущее настроение: " + moods.get(0).getMood());
            }
        });

        mainViewModel.getWeatherLiveData().observe(this, weather -> {
            if (weather != null) {
                TextView weatherTemp = findViewById(R.id.tv_weather_temp);
                weatherTemp.setText("Температура: " + weather);
            }
        });

        mainViewModel.isLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        mainViewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
```
