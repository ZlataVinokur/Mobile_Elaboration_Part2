package ru.mirea.vinokurovazo.listviewapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String[] books = {
            "Маятник Фуко", "Факультет ненужных вещей", "Петербург",
            "Кысь", "Жизнь и судьба", "Дар", "Приглашение на казнь",
            "Игра в бисер", "Солярис", "Эдип-царь",
            "Человек без свойств", "Палата №6", "Пена дней", "Осень патриарха",
            "Слепота", "Возвышение Ханумана", "Школа для дураков",
            "Между собакой и волком", "Казус Кукоцкого", "Пушкинский дом",
            "Облава", "Синий салоп", "Чевенгур", "Котлован",
            "Хождение по мукам", "Доктор Живаго", "Андеграунд",
            "Generation П", "Омон Ра", "Превращение", "Лавр"
    };

    private String[] authors = {
            "Умберто Эко", "Доминик Орсе", "Андрей Белый",
            "Татьяна Толстая", "Василий Гроссман", "Владимир Набоков",
            "Владимир Набоков", "Герман Гессе", "Станислав Лем",
            "Софокл", "Роберт Музиль", "Антон Чехов",
            "Борис Виан", "Габриэль Гарсиа Маркес", "Жозе Сарамаго",
            "Александр Гольдштейн", "Саша Соколов",
            "Юрий Мамлеев", "Людмила Улицкая", "Андрей Битов",
            "Леонид Андреев", "Алексей Варламов", "Андрей Платонов",
            "Андрей Платонов", "Алексей Толстой", "Борис Пастернак",
            "Владимир Маканин", "Виктор Пелевин", "Виктор Пелевин",
            "Франц Кафка", "Евгений Водолазкин"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksListView = findViewById(R.id.books_list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.item_book, R.id.textBookTitle, books) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textTitle = view.findViewById(R.id.textBookTitle);
                TextView textAuthor = view.findViewById(R.id.textBookAuthor);

                textTitle.setText(getItem(position));
                textAuthor.setText("Автор: " + authors[position]);
                return view;
            }
        };

        booksListView.setAdapter(adapter);
    }
}