package com.rrajath.orange.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrajath.orange.R;
import com.rrajath.orange.data.Item;
import com.rrajath.orange.utils.DateUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rrajath on 3/31/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    LayoutInflater inflater;
    ArrayList<Item> itemList = new ArrayList<>();

    public CategoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);

    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
        notifyItemRangeChanged(0, itemList.size());
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.comments.setText("" + currentItem.getDescendants() + " comments");
        holder.time.setText(DateUtils.getTimeElapsed(currentItem.getTime()));
        try {
            holder.urlDomain.setText(new URL(currentItem.getUrl()).getHost());
        } catch (MalformedURLException e) {
            holder.urlDomain.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.category_title) TextView title;
        @InjectView(R.id.category_num_comments) TextView comments;
        @InjectView(R.id.category_time_elapsed) TextView time;
        @InjectView(R.id.category_url) TextView urlDomain;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
