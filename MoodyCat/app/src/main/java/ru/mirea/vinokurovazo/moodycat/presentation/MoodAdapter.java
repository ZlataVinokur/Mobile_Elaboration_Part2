package ru.mirea.vinokurovazo.moodycat.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.moodycat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {
    private List<Mood> moods = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
    private OnMoodClickListener moodClickListener;
    private OnMoodLongClickListener moodLongClickListener;

    public interface OnMoodClickListener {
        void onMoodClick(Mood mood, int position);
    }

    public interface OnMoodLongClickListener {
        void onMoodLongClick(Mood mood, int position);
    }

    public MoodAdapter(List<Mood> moods, OnMoodClickListener listener, OnMoodLongClickListener longClickListener) {
        if (moods != null) {
            this.moods = moods;
        }
        this.moodClickListener = listener;
        this.moodLongClickListener = longClickListener;
    }

    public void updateMoods(List<Mood> moods) {
        if (moods != null) {
            this.moods = moods;
        } else {
            this.moods = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mood, parent, false);
        return new MoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
        Mood mood = moods.get(position);

        String imageName = mood.getImageName();
        int resId = holder.itemView.getContext().getResources()
                .getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());

        if (resId != 0) {
            Glide.with(holder.itemView.getContext())
                    .load(resId)
                    .placeholder(R.drawable.cat_default)
                    .error(R.drawable.cat_default)
                    .into(holder.moodImage);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.cat_default)
                    .into(holder.moodImage);
        }

        holder.catName.setText(mood.getCatName());
        holder.moodText.setText("Настроение: " + mood.getMood());
        holder.weatherText.setText("Погода: " + mood.getWeather());
        holder.dateText.setText(dateFormat.format(mood.getDate()));

        holder.itemView.setOnClickListener(v -> {
            if (moodClickListener != null) {
                moodClickListener.onMoodClick(mood, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (moodLongClickListener != null) {
                moodLongClickListener.onMoodLongClick(mood, position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return moods.size();
    }

    static class MoodViewHolder extends RecyclerView.ViewHolder {
        ImageView moodImage;
        TextView catName, moodText, weatherText, dateText;

        MoodViewHolder(View itemView) {
            super(itemView);
            moodImage = itemView.findViewById(R.id.iv_mood_image);
            catName = itemView.findViewById(R.id.tv_cat_name);
            moodText = itemView.findViewById(R.id.tv_mood);
            weatherText = itemView.findViewById(R.id.tv_weather);
            dateText = itemView.findViewById(R.id.tv_date);
        }
    }
}