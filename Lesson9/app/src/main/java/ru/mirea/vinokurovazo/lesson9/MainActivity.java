package ru.mirea.vinokurovazo.lesson9;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import ru.mirea.vinokurovazo.lesson9.R;
import ru.mirea.vinokurovazo.data.repository.MovieRepositoryImpl;
import ru.mirea.vinokurovazo.domain.repository.MovieRepository;
import ru.mirea.vinokurovazo.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.vinokurovazo.domain.usecases.SaveMovieToFavoriteUseCase;
import ru.mirea.vinokurovazo.domain.models.Movie;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private EditText text;
    private TextView textView;
    private static final String TAG = "MY_APP_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "=== MainActivity onCreate called ===");
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editTextMovie);
        textView = findViewById(R.id.textViewMovie);

        try {
            mainViewModel = new ViewModelProvider(this, new ViewModelFactory(this)).get(MainViewModel.class);
            Log.i(TAG, "ViewModel initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing ViewModel: " + e.getMessage());
            e.printStackTrace();
        }

        mainViewModel.getFavoriteMovie().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "LiveData changed: " + s);
                textView.setText(s);
            }
        });

        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Save button clicked");
                mainViewModel.saveMovie(new Movie(2, text.getText().toString()));
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Get button clicked");
                mainViewModel.getMovie();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "=== MainActivity onDestroy called ===");
    }
}