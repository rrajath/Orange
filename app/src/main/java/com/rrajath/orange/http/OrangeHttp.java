package com.rrajath.orange.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rrajath.orange.utils.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rrajath on 3/18/15.
 */
public class OrangeHttp {

    private String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private Response.Listener listener = new DefaultListener();
    private Response.ErrorListener errorListener = new DefaultErrorListener();
    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    public OrangeHttp init() {
        return new OrangeHttp();
    }

    public <T> OrangeHttp listener(Response.Listener<T> listener) {
        this.listener = listener;
        return this;
    }

    public OrangeHttp errorListener(Response.ErrorListener listener) {
        this.errorListener = errorListener;
        return this;
    }

    public void makeRequest(final Context context) {
        final Runnable requestRunnable = new Runnable() {
            @Override
            public void run() {
                executeRequest(context);
            }
        };
    }

    private void executeRequest(final Context context) {
        final Response.Listener wrapperListener = new Response.Listener() {

            @Override
            public void onResponse(final Object response) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    }
                });
            }
        };

        final Response.ErrorListener wrapperErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (volleyError != null && volleyError.networkResponse != null) {
                            Log.d("error", volleyError.getMessage());
                        }
                        handleErrorOnMainThread(volleyError);
                    }
                });
            }
        };
    }

    private void handleErrorOnMainThread(final VolleyError error) {
        final Runnable mainThreadRunnableForErrorListener = new Runnable() {
            @Override
            public void run() {
                Log.e("error", error.getMessage());
                if (errorListener != null) {
                    errorListener.onErrorResponse(error);
                }
            }
        };
        ThreadUtils.runOnMainThread(mainThreadRunnableForErrorListener);
    }

    protected static class DefaultListener implements Response.Listener<String> {

        @Override
        public void onResponse(String s) {
            Log.d("Response Listener", s);
        }
    }

    protected static class DefaultErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.w("Error Listener", volleyError.getMessage());
        }
    }
}
