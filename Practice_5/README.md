Отчет по практической работе №5
----
В рамках данной работы была поставлена задача освоить работу с сетевыми запросами с использованием библиотеки Retrofit и загрузку изображений через Picasso.

Задание №1
--
Для реализации сетевого взаимодействия создан новый модуль RetrofitApp с настройкой необходимых разрешений и зависимостей. Разработана модель данных Todo на основе структуры JSON-ответа от сервера, включающая поля для идентификатора пользователя, названия задачи и статуса выполнения.

**Приложение:**

<img width="1899" height="1107" alt="image" src="https://github.com/user-attachments/assets/e1a37c7b-4e54-4db7-bbf6-5ca4871bb52c" />

Реализован асинхронный запрос с обработкой успешных ответов и ошибок через методы onResponse и onFailure.

```
Call<List<Todo>> call = apiService.getTodos();
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Todo> todos = response.body();
                    todoAdapter = new TodoAdapter(MainActivity.this, todos, apiService);
                    recyclerView.setAdapter(todoAdapter);
                    Log.d(TAG, "Loaded " + todos.size() + " todos");
                }
                else {
                    Log.e(TAG, "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage());
            }
        });
```

Добавлена обработка пользовательских взаимодействий через обработчик кликов для чекбокса, демонстрирующий возможность обновления статуса задачи на сервере.

```
holder.checkBoxCompleted.setOnClickListener(v -> {
            boolean isChecked = holder.checkBoxCompleted.isChecked();

            android.widget.Toast.makeText(context,
                    "Задача '" + todo.getTitle() + "' " +
                            (isChecked ? "выполнена" : "не выполнена"),
                    android.widget.Toast.LENGTH_SHORT).show();
        });
```

Задание №2
--
Интегрирована библиотека Picasso для загрузки изображений из интернета. Настроены параметры отображения включая resize, centerCrop, а также placeholder и error изображения.

```
Picasso.get()
                .load(todo.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imageViewTodo);
```

**Приложение:**

<img width="1902" height="1144" alt="image" src="https://github.com/user-attachments/assets/4985cd5e-6565-49f0-9146-b50ca5ec4a4e" />

Контрольное задание
--
Было реализовано получение данных о погоде из внешнего API с использованием Retrofit, настроен сетевой слой с обработкой ошибок и асинхронными запросами к OpenWeatherMap.

```
public void getWeatherFromApi(WeatherCallback callback) {
        Call<WeatherResponse> call = weatherApiService.getWeather(
                "Moscow",
                "metric",
                ApiConfig.WEATHER_API_KEY
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    double temp = weatherResponse.getMain().getTemp();
                    String description = weatherResponse.getWeather()[0].getDescription();
                    String city = weatherResponse.getCityName();

                    callback.onSuccess(temp + "°C, " + description);
                } else {
                    callback.onError("Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }
```
**Приложение:**

<img width="536" height="954" alt="image" src="https://github.com/user-attachments/assets/f981b80d-6259-4cae-87b3-11b1ec9ce1f0" />


Интегрирована библиотека Glide для загрузки и отображения изображений настроений котов в RecyclerView.
```
String imageName = mood.getImageName();
int resId = holder.itemView.getContext().getResources()
        .getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());

if (resId != 0) {
    Glide.with(holder.itemView.getContext())
            .load(resId)
            .placeholder(R.drawable.cat_default)
            .error(R.drawable.cat_default)
            .into(holder.moodImage);
}
```

Добавлен функционал создания новых записей о настроениях котов, также реализован диалог с выбором кота и настроения через Spinner.
```
private void showAddMoodDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Добавить настроение кота");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        android.widget.Spinner spinner = new android.widget.Spinner(this);
        String[] cats = {"Барсик", "Мурка"};
        android.widget.ArrayAdapter<String> spinnerAdapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        android.widget.Spinner moodSpinner = new android.widget.Spinner(this);
        String[] moods = {"Счастливый", "Грустный", "Сонный", "Игривый", "Голодный"};
        android.widget.ArrayAdapter<String> moodAdapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moods);
        moodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(moodAdapter);
```

**Приложение:**

<img width="540" height="964" alt="image" src="https://github.com/user-attachments/assets/009bc815-4346-4217-82d7-d77d94aa4047" />
