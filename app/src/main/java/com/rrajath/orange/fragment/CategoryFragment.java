package com.rrajath.orange.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rrajath.orange.R;
import com.rrajath.orange.adapter.CategoryAdapter;
import com.rrajath.orange.data.Item;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by rrajath on 3/29/15.
 */
public class CategoryFragment extends Fragment {

    private ArrayList<Item> items = new ArrayList<>();
    private CategoryAdapter adapter;
    private RecyclerView categoryList;
    private TextView loadingErrorText;

    public static Fragment newInstance(String url) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable wrapped = Parcels.wrap(items);
        outState.putParcelable("items", wrapped);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void fetchStories(String url) {
        final ArrayList<Long> topStoryIds = new ArrayList<>();
        Ion.with(getActivity())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            loadingErrorText.setVisibility(View.VISIBLE);
                            loadingErrorText.setText("Error fetching top stories!");
                        } else {
                            for (int i = 0; i < 10; i++) {
                                topStoryIds.add(result.get(i).getAsLong());
                            }
                            load(topStoryIds);
                        }
                    }
                });

    }

    public void load(final ArrayList<Long> topStoryIds) {
        for (int i = 0; i < topStoryIds.size(); i++) {
            Ion.with(getActivity())
                    .load("https://hacker-news.firebaseio.com/v0/item/" + String.valueOf(topStoryIds.get(i)) + ".json?print=pretty")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e != null) {
                                loadingErrorText.setVisibility(View.VISIBLE);
                                loadingErrorText.setText("Error fetching stories!");
                            } else {
                                // Parse JSON object
                                Item item = setItem(result);
                                if (item.getTitle().length() != 0) {
                                    items.add(item);
                                    adapter.setItemList(items);
                                }
                            }
                        }
                    });
        }

    }

    private Item setItem(JsonObject result) {
        Item item = new Item();
        item.setId(
                isNull(result, "id") ? -1 : result.get("id").getAsLong()
        );
        item.setUsername(
                isNull(result, "by") ? "" : result.get("by").getAsString()
        );
        item.setScore(
                isNull(result, "score") ? 0 : result.get("score").getAsLong()
        );
        item.setTime(
                isNull(result, "time") ? 0 : result.get("time").getAsLong()
        );
        item.setTitle(
                isNull(result, "title") ? "" : result.get("title").getAsString()
        );
        item.setDescendants(
                isNull(result, "descendants") ? 0 : result.get("descendants").getAsInt()
        );
        item.setUrl(
                isNull(result, "url") ? "" : result.get("url").getAsString()
        );

        return item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        categoryList = (RecyclerView) view.findViewById(R.id.category_list);
        categoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CategoryAdapter(getActivity());
        categoryList.setAdapter(adapter);
        String url = getArguments().getString("url");
        loadingErrorText = (TextView) view.findViewById(R.id.loading_error_text);
        if (savedInstanceState != null) {
            items = Parcels.unwrap(savedInstanceState.getParcelable("items"));
            adapter.setItemList(items);
        } else {
            fetchStories(url);
        }
        return view;
    }

    public boolean isNull(JsonObject result, String key) {
        if (result.has(key)) {
            if (result.get(key) != null)
                return false;
        }
        return true;
    }

}
