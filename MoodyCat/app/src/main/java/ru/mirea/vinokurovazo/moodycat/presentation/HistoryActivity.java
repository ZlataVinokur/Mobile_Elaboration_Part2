package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.vinokurovazo.moodycat.R;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.HistoryViewModel;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.HistoryViewModelFactory;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private HistoryViewModel historyViewModel;
    private RecyclerView recyclerView;
    private MoodAdapter moodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setupViewModel();
        setupUI();
        setupObservers();
    }

    private void setupViewModel() {
        HistoryViewModelFactory factory = new HistoryViewModelFactory(this);
        historyViewModel = new ViewModelProvider(this, factory).get(HistoryViewModel.class);
    }

    private void setupUI() {
        TextView title = findViewById(R.id.tv_title);
        Button backButton = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.recycler_view);

        title.setText("История настроений кота");
        backButton.setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moodAdapter = new MoodAdapter(new ArrayList<>());
        recyclerView.setAdapter(moodAdapter);
    }

    private void setupObservers() {
        historyViewModel.getMoods().observe(this, moods -> {
            if (moods != null && !moods.isEmpty()) {
                moodAdapter.updateMoods(moods);
            }
        });
    }
}