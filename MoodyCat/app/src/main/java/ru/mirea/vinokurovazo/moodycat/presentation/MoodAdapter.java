package ru.mirea.vinokurovazo.moodycat.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.moodycat.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {
    private List<Mood> moods;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    public MoodAdapter(List<Mood> moods) {
        this.moods = moods;
    }

    public void updateMoods(List<Mood> moods) {
        this.moods = moods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mood, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mood mood = moods.get(position);
        holder.moodText.setText(mood.getMood());
        holder.dateText.setText(dateFormat.format(mood.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return moods.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView moodText, dateText;

        ViewHolder(View itemView) {
            super(itemView);
            moodText = itemView.findViewById(R.id.tv_mood);
            dateText = itemView.findViewById(R.id.tv_date);
        }
    }
}