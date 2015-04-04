package com.rrajath.orange.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rrajath.orange.R;
import com.rrajath.orange.activity.WebViewActivity;
import com.rrajath.orange.adapter.CategoryAdapter;
import com.rrajath.orange.data.Item;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rrajath on 3/29/15.
 */
public class CategoryFragment extends Fragment {

    private ArrayList<Item> items = new ArrayList<>();
    private CategoryAdapter adapter;
    private ClickListener mClickListener;
    @InjectView(R.id.category_list) RecyclerView categoryList;
    @InjectView(R.id.loading_error_text) TextView loadingErrorText;

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
        final ArrayList<Long> stories = new ArrayList<>();
        Ion.with(getActivity())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            loadingErrorText.setVisibility(View.VISIBLE);
                            loadingErrorText.setText("Error fetching stories!");
                        } else {
                            for (int i = 0; i < 10; i++) {
                                stories.add(result.get(i).getAsLong());
                            }
                            load(stories);
                        }
                    }
                });

    }

    public void load(final ArrayList<Long> stories) {
        for (int i = 0; i < stories.size(); i++) {
            Ion.with(getActivity())
                    .load("https://hacker-news.firebaseio.com/v0/item/" + String.valueOf(stories.get(i)) + ".json?print=pretty")
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
                                if (item.title.length() != 0) {
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
        item.id = isNull(result, "id") ? -1 : result.get("id").getAsLong();
        item.username = isNull(result, "by") ? "" : result.get("by").getAsString();
        item.score = isNull(result, "score") ? 0 : result.get("score").getAsLong();
        item.time = isNull(result, "time") ? 0 : result.get("time").getAsLong();
        item.title = isNull(result, "title") ? "" : result.get("title").getAsString();
        item.descendants = isNull(result, "descendants") ? 0 : result.get("descendants").getAsInt();
        item.url = isNull(result, "url") ? "" : result.get("url").getAsString();

        return item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        ButterKnife.inject(this, view);
        categoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), categoryList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Item item = items.get(position);
                if (!"".equals(item.url)) {
                    Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                    intent.putExtra("url", item.url);
                    view.getContext().startActivity(intent);
                }
            }
        }));

        adapter = new CategoryAdapter(getActivity());
        categoryList.setAdapter(adapter);
        String url = getArguments().getString("url");
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

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        GestureDetector gestureDetector;
        public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener) {
            mClickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mClickListener != null && gestureDetector.onTouchEvent(e)) {
                mClickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
    }
}
