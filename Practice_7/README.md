Отчет по практической работе №6
----
В рамках данной работы была поставлена задача освоить работу с фрагментами в Android-приложениях, включая создание статических и динамических фрагментов, управление навигацией и передачу данных между компонентами.

Задание №1
--
Для первого модуля FragmentApp реализовано приложение с фрагментом, который отображает номер студента по списку. Создан класс BlankFragment, передача данных во фрагмент осуществлена через Bundle.

**Приложение:**
<img width="1893" height="1072" alt="image" src="https://github.com/user-attachments/assets/69e5ed7c-4ff5-4da2-ba01-d713017c2133" />

```
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("my_number_student", 5);

            BlankFragment fragment = new BlankFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, fragment)
                    .commit();
        }
    }
}
```

Задание №2
--
Далее в модуле FragmentManagerApp реализована навигация между фрагментами с использованием ViewModel для передачи данных. Создан ShareViewModel для хранения выбранного элемента, что позволяет фрагментам обмениваться данными без прямых ссылок друг на друга.

```
public class ShareViewModel extends ViewModel {
    private final MutableLiveData<String> selectedItem = new MutableLiveData<>();

    public void selectItem(String item) {
        selectedItem.setValue(item);
    }

    public MutableLiveData<String> getSelectedItem() {
        return selectedItem;
    }
}
```

BookListFragment при выборе книги обновляет значение во ViewModel и заменяет текущий фрагмент на BookDetailsFragment с помощью метода replace FragmentManager.

**Приложение:**
<img width="1846" height="1090" alt="image" src="https://github.com/user-attachments/assets/111550b9-5a88-4e57-a67a-89a4dd8b6a3d" />
<img width="1880" height="1083" alt="image" src="https://github.com/user-attachments/assets/e6c10283-d131-4b70-9cda-a9936cc96d04" />

Задание №3
--
В третьем модуле ResultApiFragmentApp был использован Fragment Result API для передачи данных между фрагментами. Реализован DataFragment с полем ввода текста и BottomSheetFragment, который получает введенные данные через fragment result.

**Приложение:**
<img width="1862" height="1075" alt="image" src="https://github.com/user-attachments/assets/719ee70a-5e78-4018-8317-0d7699353847" />

Метод setFragmentResultListener позволяет BottomSheetFragment получать данные от DataFragment без прямых ссылок между компонентами.

```
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            String text = bundle.getString("key");
            TextView textView = view.findViewById(R.id.textViewResult);
            textView.setText("Получено: " + text);
        });

        return view;
    }
```

Контрольное задание
--
В проекте была добавлена архитектура на фрагментах с навигацией через бэк-стек, основная Activity теперь служит контейнером, все экраны переведены в отдельные фрагменты.

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthRepositoryImpl authRepository = new AuthRepositoryImpl(this);

        if (!authRepository.isLoggedIn()) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
    }
}
```

```
 @Override
    public void onMoodClick(Mood mood, int position) {
        MoodDetailFragment detailFragment = MoodDetailFragment.newInstance(mood);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack("mood_detail")
                .commit();
    }
```

Добавлен фрагмент профиля пользователя - отображает данные авторизации из SharedPreferences, включая email и тип учетной записи, с возможностью возврата через системную кнопку назад.

**Приложение:**
<img width="1891" height="1099" alt="image" src="https://github.com/user-attachments/assets/bbc4552c-c440-4e72-8f9c-4cfd009bba05" />

В данный момент приложение полностью функционально и имеет такие экраны:

Авторизация

<img width="444" height="931" alt="image" src="https://github.com/user-attachments/assets/9b866550-7111-48d9-a096-eeeb27311de3" />

Гостевой вход

<img width="447" height="941" alt="image" src="https://github.com/user-attachments/assets/48be69e7-2385-4c18-bdaa-880be29a7e1d" />

Личный кабинет с погодой

<img width="443" height="935" alt="image" src="https://github.com/user-attachments/assets/cede7de3-6387-4259-88e3-b8977f1e1689" />

Профиль

<img width="436" height="930" alt="image" src="https://github.com/user-attachments/assets/5381eee3-4b62-490b-bf41-87ba20842f67" />

История настроений с возможностью добавления и редактирования записей

<img width="452" height="939" alt="image" src="https://github.com/user-attachments/assets/c730ccaa-cc09-4c4d-8171-2bc9bdcb839e" />

Детали выбранной записи настроения

<img width="446" height="936" alt="image" src="https://github.com/user-attachments/assets/71fd2990-1696-4daa-b2f1-56c2a2c79cb1" />




