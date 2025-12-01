package ru.mirea.vinokurovazo.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class BookDetailsFragment extends Fragment {

    private ShareViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        TextView textView = view.findViewById(R.id.textViewDetails);

        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), book -> {
            textView.setText("Книга: " + book + "\n\nОписание книги");
        });

        return view;
    }
}