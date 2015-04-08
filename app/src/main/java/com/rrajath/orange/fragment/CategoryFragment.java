package com.rrajath.orange.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.rrajath.orange.EndlessScrollListener;
import com.rrajath.orange.R;
import com.rrajath.orange.activity.WebViewActivity;
import com.rrajath.orange.adapter.CategoryAdapter;
import com.rrajath.orange.data.Item;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rrajath on 3/29/15.
 */
public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = CategoryFragment.class.getSimpleName();
    private ArrayList<Item> items = new ArrayList<>();
    private CategoryAdapter adapter;
    private ClickListener mClickListener;
    @InjectView(R.id.category_list) RecyclerView categoryList;
    @InjectView(R.id.loading_error_text) TextView loadingErrorText;
    @InjectView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.progress_bar) ProgressBar progressBar;
    private ArrayList<Long> stories;
    private Future<JsonArray> downloading;

    public static Fragment newInstance(String url) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Parcelable wrappedItems = Parcels.wrap(items);
        Parcelable wrappedStories = Parcels.wrap(stories);
        outState.putParcelable("items", wrappedItems);
        outState.putParcelable("stories", wrappedStories);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void fetchStories(String url) {
        if (stories != null && items.size() != 0) {
            stories.clear();
            items.clear();
        } else {
            stories = new ArrayList<>();
        }
        downloading = Ion.with(getActivity())
                .load(url)
                .progressBar(progressBar)
                .progressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                })
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            loadingErrorText.setVisibility(View.VISIBLE);
                            loadingErrorText.setText("Error fetching stories!");
                        } else {
                            for (JsonElement element : result) {
                                stories.add(element.getAsLong());
                            }
                            load(stories.subList(0, 10));
                            if (swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            resetProgress();
                        }
                    }
                });

    }

    private void resetProgress() {
        downloading.cancel();
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);
    }

    public void load(final List<Long> stories) {
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
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_color);
        adapter = new CategoryAdapter(getActivity());
        categoryList.setAdapter(adapter);
        String url = getArguments().getString("url");
        if (savedInstanceState != null) {
            items = Parcels.unwrap(savedInstanceState.getParcelable("items"));
            stories = Parcels.unwrap(savedInstanceState.getParcelable("stories"));
            adapter.setItemList(items);
        } else {
            fetchStories(url);
        }

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        categoryList.setLayoutManager(linearLayoutManager);
        categoryList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), new ClickListener() {
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
        categoryList.setOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void loadMore() {
                // is more available?
                int itemsLoaded = items.size();
                if (stories.size() > (itemsLoaded + 10)) {
                    itemsLoaded += 10;
                    load(stories.subList(items.size(), itemsLoaded));
                } else if (items.size() == stories.size()) {
                    Log.d(TAG, "End of list");
                } else {
                    itemsLoaded = stories.size();
                    load(stories.subList(items.size(), itemsLoaded));
                }
            }
        });
        return view;
    }

    public boolean isNull(JsonObject result, String key) {
        if (result.has(key)) {
            if (result.get(key) != null)
                return false;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        fetchStories(getArguments().getString("url"));
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        GestureDetector gestureDetector;
        public RecyclerTouchListener(Context context, ClickListener clickListener) {
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

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
