package com.rrajath.orange;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrajath.orange.data.Item;
import com.rrajath.orange.utils.DateUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rrajath on 3/31/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.TopStoriesViewHolder> {

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
    public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.top_stories_list_item, parent, false);
        TopStoriesViewHolder viewHolder = new TopStoriesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopStoriesViewHolder holder, int position) {
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

    public class TopStoriesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView comments;
        TextView time;
        TextView urlDomain;

        public TopStoriesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.top_stories_title);
            comments = (TextView) itemView.findViewById(R.id.top_stories_num_comments);
            time = (TextView) itemView.findViewById(R.id.top_stories_time_elapsed);
            urlDomain = (TextView) itemView.findViewById(R.id.top_stories_url);
        }
    }
}
