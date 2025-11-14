package ru.mirea.vinokurovazo.recyclerviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<Event> events;
    private Context context;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);

        holder.textViewEventName.setText(event.getEventName());
        holder.textViewEventDescription.setText(event.getEventDescription());
        holder.imageViewEvent.setImageResource(event.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}