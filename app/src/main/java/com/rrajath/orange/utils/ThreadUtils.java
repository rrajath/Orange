package com.rrajath.orange.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rrajath on 3/18/15.
 */
public class ThreadUtils {
    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void runOnMainThread(Runnable task) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            task.run();
        } else {
            postOnMainThread(task);
        }
    }

    public static final void runOnBackgroundThread(Runnable task) {
        if (executorService == null) {
            return;
        }
        executorService.submit(task);
    }
    private static void postOnMainThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }
}
