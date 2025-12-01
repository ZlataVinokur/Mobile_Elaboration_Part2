package ru.mirea.vinokurovazo.fragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BlankFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        int numberStudent = requireArguments().getInt("my_number_student");

        TextView textView = view.findViewById(R.id.textView);
        textView.setText("Номер студента: " + numberStudent);

        Log.d("BlankFragment", "Номер студента: " + numberStudent);

        return view;
    }
}