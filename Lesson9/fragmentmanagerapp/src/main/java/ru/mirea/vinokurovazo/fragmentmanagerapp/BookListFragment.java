package ru.mirea.vinokurovazo.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.Arrays;
import java.util.List;

public class BookListFragment extends Fragment {

    private ShareViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);

        ListView listView = view.findViewById(R.id.listViewBooks);
        List<String> books = Arrays.asList(
                "Имя розы",
                "Мадам Бовари",
                "Тошнота",
                "Уловка 22",
                "Отрицание смерти"
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                books
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedBook = books.get(position);
            viewModel.selectItem(selectedBook);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BookDetailsFragment())
                    .commit();
        });

        return view;
    }
}