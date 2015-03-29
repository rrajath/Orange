package com.rrajath.orange;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrajath.orange.data.NavDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by rrajath on 3/28/15.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.MyViewHolder> {

    Context mContext;
    private LayoutInflater inflater;
    List<NavDrawerItem> data = Collections.emptyList();

    public NavDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem currentItem = data.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.icon.setImageResource(currentItem.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nav_drawer_item_title);
            icon = (ImageView) itemView.findViewById(R.id.nav_drawer_item_icon);
        }
    }
}
