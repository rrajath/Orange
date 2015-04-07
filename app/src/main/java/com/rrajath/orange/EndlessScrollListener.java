package com.rrajath.orange;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by rrajath on 4/6/15.
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    int previousTotal = 0;
    int visibleThreshold = 5;
    private LinearLayoutManager mLinearLayoutManager;

    public EndlessScrollListener(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            loadMore();
            loading = true;
        }
    }

    public abstract void loadMore();
}
