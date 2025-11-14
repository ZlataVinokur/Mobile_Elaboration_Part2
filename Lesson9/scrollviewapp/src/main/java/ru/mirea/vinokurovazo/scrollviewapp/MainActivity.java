package ru.mirea.vinokurovazo.scrollviewapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout wrapper = findViewById(R.id.wrapper);

        int value = 1;
        for (int i = 1; i <= 100; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_progress, null, false);
            TextView text = view.findViewById(R.id.textView);
            text.setText(String.format("Элемент %d: %d", i, value));
            wrapper.addView(view);
            value *= 2;
        }
    }
}