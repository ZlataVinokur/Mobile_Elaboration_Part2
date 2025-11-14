package ru.mirea.vinokurovazo.recyclerviewapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageViewEvent;
    public TextView textViewEventName;
    public TextView textViewEventDescription;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewEvent = itemView.findViewById(R.id.imageViewEvent);
        textViewEventName = itemView.findViewById(R.id.textViewEventName);
        textViewEventDescription = itemView.findViewById(R.id.textViewEventDescription);
    }
}