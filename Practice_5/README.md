Отчет по практической работе №5
----
В рамках данной работы была поставлена задача освоить три основных способа отображения списков в Android-приложениях.

Задание №1
--
Для ScrollViewApp реализован вертикальный скроллинг с отображением геометрической прогрессии, где каждый элемент динамически добавлялся через LayoutInflater.

**Приложение:**

<img width="1851" height="1030" alt="image" src="https://github.com/user-attachments/assets/def61bb3-a816-4a3d-b583-49cd8d12248a" />

Задание №2
--

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
