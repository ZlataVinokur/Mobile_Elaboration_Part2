Отчет по практической работе №7
----
В рамках данной работы была поставлена задача освоить работу с разными типами навигации в Android Studio.

Задание №1
--
Был создан новый модуль "BottomNavigationApp" с использованием шаблона Bottom Navigation Views Activity. В приложении реализована навигация между тремя основными разделами: Home, Info и Profile. Для навигации использован Navigation Component, который обеспечивает управление переходами между фрагментами через нижнюю панель навигации. Были добавлены новые иконки для каждого раздела. Цветовая гамма приложения обновлена в файле themes.xml и подключена в манифест файле.

**Приложение:**
<img width="1919" height="1173" alt="image" src="https://github.com/user-attachments/assets/da53e43d-3ac9-495e-9782-9479e28d5e4f" />

```
    <item
        android:id="@+id/navigation_home"
        android:icon="@drawable/baseline_home_24"
        android:title="@string/title_home" />

    <item
        android:id="@+id/navigation_dashboard"
        android:icon="@drawable/baseline_assessment_24"
        android:title="@string/title_info" />

    <item
        android:id="@+id/navigation_notifications"
        android:icon="@drawable/baseline_account_box_24"
        android:title="@string/title_profile" />

</menu>
```

Навигационный граф настроен в файле mobile_navigation.xml. Меню нижней навигации описано в файле bottom_nav_menu.xml.

```
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/mobile_navigation"
    app:startDestination="@+id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="ru.mirea.vinokurovazo.bottomnavigationapp.ui.home.HomeFragment"
        android:label="@string/title_home"
        tools:layout="@layout/fragment_home" />

    <fragment
        android:id="@+id/navigation_dashboard"
        android:name="ru.mirea.vinokurovazo.bottomnavigationapp.ui.dashboard.DashboardFragment"
        android:label="@string/title_info"
        tools:layout="@layout/fragment_dashboard" />

    <fragment
        android:id="@+id/navigation_notifications"
        android:name="ru.mirea.vinokurovazo.bottomnavigationapp.ui.notifications.NotificationsFragment"
        android:label="@string/title_profile"
        tools:layout="@layout/fragment_notifications" />
</navigation>
```

Задание №2
--
Далее в модуле NavigationDrawerApp была реализована навигация с помощью боковой шторки. В приложении реализована навигация между тремя основными разделами: Главная, Информация и Профиль. Цветовая гамма приложения обновлена в файле themes.xml, где заданы новые цвета.

**Приложение:**
<img width="1906" height="1129" alt="image" src="https://github.com/user-attachments/assets/b4c1ad16-4aaf-4b5d-a78b-10f980a4568a" />
<img width="1862" height="1111" alt="image" src="https://github.com/user-attachments/assets/8c10706c-fa88-4fae-a7a0-cdcc17eea92c" />

Была реализована функциональность закрытия боковой шторки системной кнопкой "назад". При нажатии кнопки, когда шторка открыта, она закрывается, а не происходит выход из приложения.

```
@Override
public void onBackPressed() {
    DrawerLayout drawer = binding.drawerLayout;
    if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
    } else {
        super.onBackPressed();
    }
}
```

Контрольное задание
--
В проекте была реализована навигация с использованием Navigation Component и Bottom Navigation. Создан граф навигации nav_graph.xml с тремя основными пунктами меню - главная страница, история настроений и профиль пользователя. Ручные кнопки были удалены.

**Приложение:**
<img width="1881" height="1113" alt="image" src="https://github.com/user-attachments/assets/7c1461c4-3704-4fd5-8169-1ea5bd8fab3a" />

```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="ru.mirea.vinokurovazo.moodycat.presentation.HomeFragment"
        android:label="Главная"
        tools:layout="@layout/fragment_home" />

    <fragment
        android:id="@+id/navigation_history"
        android:name="ru.mirea.vinokurovazo.moodycat.presentation.HistoryFragment"
        android:label="История"
        tools:layout="@layout/activity_history">

        <action
            android:id="@+id/action_history_to_mood_detail"
            app:destination="@id/moodDetailFragment" />

    </fragment>

    <fragment
        android:id="@+id/navigation_profile"
        android:name="ru.mirea.vinokurovazo.moodycat.presentation.ProfileFragment"
        android:label="Профиль"
        tools:layout="@layout/fragment_profile" />

    <fragment
        android:id="@+id/moodDetailFragment"
        android:name="ru.mirea.vinokurovazo.moodycat.presentation.MoodDetailFragment"
        android:label="Детали настроения"
        tools:layout="@layout/fragment_mood_detail">

        <action
            android:id="@+id/action_mood_detail_to_history"
            app:destination="@id/navigation_history"
            app:popUpTo="@id/navigation_history"
            app:popUpToInclusive="false" />

    </fragment>

</navigation>
```

Также было настроено ограничение доступа к истории настроений для гостевых пользователей, пункт "История" скрывается в меню если вы гость.

**Приложение:**
<img width="1870" height="1124" alt="image" src="https://github.com/user-attachments/assets/5221c858-81aa-4315-9085-80ad49eee78c" />








