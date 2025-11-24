package ru.mirea.vinokurovazo.retrofitapp;

import com.google.gson.annotations.SerializedName;

public class Todo {
    @SerializedName("userId")
    private Integer userId;

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("completed")
    private Boolean completed;

    public Integer getUserId() { return userId; }
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public Boolean getCompleted() { return completed; }

    public String getImageUrl() {
        return "https://example.com/image.jpg";
    }
}