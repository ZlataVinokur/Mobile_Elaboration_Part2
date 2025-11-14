package ru.mirea.vinokurovazo.recyclerviewapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Event> events = getEventsData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        EventAdapter adapter = new EventAdapter(events);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<Event> getEventsData() {
        List<Event> events = new ArrayList<>();

        events.add(new Event("Падение Западной Римской империи",
                "476 год - Одоакр свергает Ромула Августула", R.drawable.fall_rome));
        events.add(new Event("Черная смерть в Европе",
                "1347-1351 годы - пандемия чумы", R.drawable.black_death));
        events.add(new Event("Изобретение книгопечатания",
                "1440 год - Иоганн Гутенберг", R.drawable.printing_press));
        events.add(new Event("Великая французская революция",
                "1789-1799 годы - свержение монархии", R.drawable.french_revolution));
        events.add(new Event("Первая мировая война",
                "1914-1918 годы - Великая война", R.drawable.ww1));

        return events;
    }
}