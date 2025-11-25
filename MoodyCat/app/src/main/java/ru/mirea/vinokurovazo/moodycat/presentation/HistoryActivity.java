package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.vinokurovazo.moodycat.R;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.HistoryViewModel;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.HistoryViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private HistoryViewModel historyViewModel;
    private MoodAdapter moodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setupViewModel();
        setupRecyclerView();
        setupFabButton();
        setupBackButton();
        setupObservers();

        historyViewModel.loadMoods();
    }

    private void setupViewModel() {
        HistoryViewModelFactory factory = new HistoryViewModelFactory(this);
        historyViewModel = new ViewModelProvider(this, factory).get(HistoryViewModel.class);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        moodAdapter = new MoodAdapter(new ArrayList<>());
        recyclerView.setAdapter(moodAdapter);
    }

    private void setupFabButton() {
        FloatingActionButton fabAddMood = findViewById(R.id.fab_add_mood);
        fabAddMood.setOnClickListener(v -> showAddMoodDialog());
    }

    private void setupBackButton() {
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void setupObservers() {
        historyViewModel.getMoods().observe(this, moods -> {
            if (moods != null) {
                moodAdapter.updateMoods(moods);
            }
        });
    }

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

        androidx.appcompat.widget.AppCompatTextView catLabel = new androidx.appcompat.widget.AppCompatTextView(this);
        catLabel.setText("Выберите кота:");
        catLabel.setTextSize(16);
        layout.addView(catLabel);
        layout.addView(spinner);

        androidx.appcompat.widget.AppCompatTextView moodLabel = new androidx.appcompat.widget.AppCompatTextView(this);
        moodLabel.setText("Выберите настроение:");
        moodLabel.setTextSize(16);
        moodLabel.setPadding(0, 30, 0, 0);
        layout.addView(moodLabel);
        layout.addView(moodSpinner);

        builder.setView(layout);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String catName = spinner.getSelectedItem().toString();
            String mood = moodSpinner.getSelectedItem().toString();

            historyViewModel.addMood(catName, mood, "Солнечно");
            Toast.makeText(this, "Настроение добавлено!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
}