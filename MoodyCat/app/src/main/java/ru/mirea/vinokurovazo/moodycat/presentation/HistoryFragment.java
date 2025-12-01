package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.moodycat.R;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.HistoryViewModel;
import ru.mirea.vinokurovazo.moodycat.presentation.viewmodel.HistoryViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HistoryFragment extends Fragment implements MoodAdapter.OnMoodClickListener, MoodAdapter.OnMoodLongClickListener {
    private HistoryViewModel historyViewModel;
    private MoodAdapter moodAdapter;
    private Mood selectedMoodForEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel();
        setupRecyclerView(view);
        setupFabButton(view);
        setupBackButton(view);
        setupObservers();

    }

    private void setupViewModel() {
        HistoryViewModelFactory factory = new HistoryViewModelFactory(requireContext());
        historyViewModel = new ViewModelProvider(this, factory).get(HistoryViewModel.class);
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        moodAdapter = new MoodAdapter(new java.util.ArrayList<>(), this, this);
        recyclerView.setAdapter(moodAdapter);
    }

    @Override
    public void onMoodClick(Mood mood, int position) {
        MoodDetailFragment detailFragment = MoodDetailFragment.newInstance(mood);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack("mood_detail")
                .commit();
    }

    @Override
    public void onMoodLongClick(Mood mood, int position) {
        selectedMoodForEdit = mood;
        showEditMoodDialog(mood);
    }

    private void setupFabButton(View view) {
        FloatingActionButton fabAddMood = view.findViewById(R.id.fab_add_mood);
        fabAddMood.setOnClickListener(v -> showAddMoodDialog());
    }

    private void setupBackButton(View view) {
        Button btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void setupObservers() {
        historyViewModel.getMoods().observe(getViewLifecycleOwner(), moods -> {
            if (moods != null) {
                moodAdapter.updateMoods(moods);
            }
        });
    }

    private void showAddMoodDialog() {
        showMoodDialog(null, "Добавить настроение кота");
    }

    private void showEditMoodDialog(Mood mood) {
        showMoodDialog(mood, "Редактировать настроение");
    }

    private void showMoodDialog(Mood existingMood, String title) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle(title);

        android.widget.LinearLayout layout = new android.widget.LinearLayout(requireContext());
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        android.widget.Spinner spinner = new android.widget.Spinner(requireContext());
        String[] cats = {"Барсик", "Мурка"};
        android.widget.ArrayAdapter<String> spinnerAdapter = new android.widget.ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, cats);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        android.widget.Spinner moodSpinner = new android.widget.Spinner(requireContext());
        String[] moods = {"Счастливый", "Грустный", "Сонный", "Игривый", "Голодный"};
        android.widget.ArrayAdapter<String> moodAdapter = new android.widget.ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, moods);
        moodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(moodAdapter);

        android.widget.Spinner weatherSpinner = new android.widget.Spinner(requireContext());
        String[] weatherOptions = {"Солнечно", "Облачно", "Дождь", "Снег", "Ветренно", "Туман"};
        android.widget.ArrayAdapter<String> weatherAdapter = new android.widget.ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, weatherOptions);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(weatherAdapter);

        if (existingMood != null) {
            for (int i = 0; i < cats.length; i++) {
                if (cats[i].equals(existingMood.getCatName())) {
                    spinner.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < moods.length; i++) {
                if (moods[i].equals(existingMood.getMood())) {
                    moodSpinner.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < weatherOptions.length; i++) {
                if (weatherOptions[i].equals(existingMood.getWeather())) {
                    weatherSpinner.setSelection(i);
                    break;
                }
            }
        }

        androidx.appcompat.widget.AppCompatTextView catLabel = new androidx.appcompat.widget.AppCompatTextView(requireContext());
        catLabel.setText("Выберите кота:");
        catLabel.setTextSize(16);
        layout.addView(catLabel);
        layout.addView(spinner);

        androidx.appcompat.widget.AppCompatTextView moodLabel = new androidx.appcompat.widget.AppCompatTextView(requireContext());
        moodLabel.setText("Выберите настроение:");
        moodLabel.setTextSize(16);
        moodLabel.setPadding(0, 30, 0, 0);
        layout.addView(moodLabel);
        layout.addView(moodSpinner);

        androidx.appcompat.widget.AppCompatTextView weatherLabel = new androidx.appcompat.widget.AppCompatTextView(requireContext());
        weatherLabel.setText("Выберите погоду:");
        weatherLabel.setTextSize(16);
        weatherLabel.setPadding(0, 20, 0, 0);
        layout.addView(weatherLabel);
        layout.addView(weatherSpinner);

        builder.setView(layout);

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String catName = spinner.getSelectedItem().toString();
            String mood = moodSpinner.getSelectedItem().toString();
            String weather = weatherSpinner.getSelectedItem().toString();

            if (existingMood != null) {
                historyViewModel.updateMood(existingMood.getId(), catName, mood, weather);
                Toast.makeText(requireContext(), "Настроение обновлено!", Toast.LENGTH_SHORT).show();
            } else {
                historyViewModel.addMood(catName, mood, weather);
                Toast.makeText(requireContext(), "Настроение добавлено!", Toast.LENGTH_SHORT).show();
            }
        });

        if (existingMood != null) {
            builder.setNeutralButton("Удалить", (dialog, which) -> {
                historyViewModel.deleteMood(existingMood.getId());
                Toast.makeText(requireContext(), "Настроение удалено!", Toast.LENGTH_SHORT).show();
            });
        }

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }
}