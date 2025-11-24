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
В этой части задания был создан новый макет карточки с изображением с CardView. В RecyclerView MoodAdapter теперь отображает изображения настроений и текстовые данные в карточках.

**Приложение:**

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
