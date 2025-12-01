package ru.mirea.vinokurovazo.moodycat.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import ru.mirea.vinokurovazo.domain.model.Mood;
import ru.mirea.vinokurovazo.moodycat.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MoodDetailFragment extends Fragment {
    private static final String ARG_MOOD = "mood";
    private Mood mood;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    public static MoodDetailFragment newInstance(Mood mood) {
        MoodDetailFragment fragment = new MoodDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOOD, mood);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mood = (Mood) getArguments().getSerializable(ARG_MOOD);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mood_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mood == null) {
            requireActivity().getSupportFragmentManager().popBackStack();
            return;
        }

        setupUI(view);
    }

    private void setupUI(View view) {
        ImageView moodImage = view.findViewById(R.id.iv_mood_detail);
        TextView catName = view.findViewById(R.id.tv_cat_name_detail);
        TextView moodText = view.findViewById(R.id.tv_mood_detail);
        TextView weatherText = view.findViewById(R.id.tv_weather_detail);
        TextView dateText = view.findViewById(R.id.tv_date_detail);
        TextView adviceText = view.findViewById(R.id.tv_advice);
        Button btnBack = view.findViewById(R.id.btn_back);

        String imageName = mood.getImageName();
        int resId = view.getContext().getResources()
                .getIdentifier(imageName, "drawable", view.getContext().getPackageName());

        if (resId != 0) {
            Glide.with(view.getContext())
                    .load(resId)
                    .placeholder(R.drawable.cat_default)
                    .error(R.drawable.cat_default)
                    .into(moodImage);
        }

        catName.setText(mood.getCatName());
        moodText.setText("Настроение: " + mood.getMood());
        weatherText.setText("Погода: " + mood.getWeather());
        dateText.setText("Дата: " + dateFormat.format(mood.getDate()));

        adviceText.setText(getAdviceForMood(mood.getMood()));

        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private String getAdviceForMood(String mood) {
        switch (mood.toLowerCase()) {
            case "счастливый":
            case "веселый":
                return "🎉 Ваш кот в прекрасном настроении! Это идеальное время для совместной игры. " +
                        "Попробуйте поиграть с лазерной указкой или новой игрушкой. " +
                        "Коты особенно ценят внимание, когда они счастливы!";

            case "грустный":
            case "печальный":
                return "🤗 Похоже, вашему коту нужно немного заботы. Нежно погладьте его, " +
                        "предложите любимое лакомство. Проверьте, не болит ли что-то у кота, " +
                        "возможно, стоит обратить внимание на его здоровье.";

            case "сонный":
            case "уставший":
                return "😴 Котик хочет отдохнуть. Обеспечьте ему тихое и уютное место, " +
                        "можно положить мягкую подстилку у батареи. Не беспокойте его - " +
                        "коты спят до 16 часов в сутки, и это нормально!";

            case "игривый":
            case "активный":
                return "⚡ Энергия бьет ключом! Используйте это время для активных игр. " +
                        "Игрушка-удочка с перьями или мячик отлично подойдут. " +
                        "Такая активность помогает поддерживать форму и настроение кота.";

            case "голодный":
                return "🍗 Время еды! Проверьте миски с едой и водой. Убедитесь, что еда свежая, " +
                        "а вода чистая. Не перекармливайте - лучше кормить чаще, но меньшими порциями.";

            default:
                return "👀 Понаблюдайте за своим питомцем. Каждый кот уникален - " +
                        "обратите внимание на его привычки и предпочтения, чтобы лучше понять, что ему нужно.";
        }
    }
}