package com.rrajath.orange.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by rrajath on 3/31/15.
 */
public class HttpUtils {

    static ArrayList<Long> topStoryIds;

    public static ArrayList<Long> fetchTopStories(final Context context) {
        Ion.with(context)
                .load("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            Toast.makeText(context, "Error fetching top stories!", Toast.LENGTH_SHORT).show();
                        } else {
                            topStoryIds = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                topStoryIds.add(result.get(i).getAsLong());
                            }
//                            Toast.makeText(context, topStoryIds.toString(), Toast.LENGTH_SHORT).show();
                            load(context);
                        }
                    }
                });
        return topStoryIds;
    }

    private static void load(final Context context) {
        for (int i = 0; i < topStoryIds.size(); i++) {
            final int finalI = i;
            Ion.with(context)
                    .load("https://hacker-news.firebaseio.com/v0/item/" + String.valueOf(topStoryIds.get(i)) + ".json?print=pretty")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e != null) {
                                Toast.makeText(context, "Error fetching story " + String.valueOf(topStoryIds.get(finalI)), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, result.get("by").getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
