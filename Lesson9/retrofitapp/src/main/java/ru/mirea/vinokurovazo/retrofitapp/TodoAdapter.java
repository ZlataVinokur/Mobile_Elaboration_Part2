package ru.mirea.vinokurovazo.retrofitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private Context context;
    private List<Todo> todos;
    private ApiService apiService;

    public TodoAdapter(Context context, List<Todo> todos, ApiService apiService) {
        this.context = context;
        this.todos = todos;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);

        holder.textViewTitle.setText(todo.getTitle());
        holder.checkBoxCompleted.setChecked(todo.getCompleted());

        Picasso.get()
                .load(todo.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imageViewTodo);

        holder.checkBoxCompleted.setOnClickListener(v -> {
            boolean isChecked = holder.checkBoxCompleted.isChecked();

            android.widget.Toast.makeText(context,
                    "Задача '" + todo.getTitle() + "' " +
                            (isChecked ? "выполнена" : "не выполнена"),
                    android.widget.Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}