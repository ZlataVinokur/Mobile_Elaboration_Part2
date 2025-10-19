package ru.mirea.vinokurovazo.mireaproject.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundWorker extends Worker {
    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(3000);
            return Result.success();
        } catch (InterruptedException e) {
            return Result.failure();
        }
    }
}