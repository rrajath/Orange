package com.rrajath.orange;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrajath.orange.data.NavDrawerItem;

/**
 * Created by rrajath on 3/28/15.
 */
public class NavDrawerAdapter extends ArrayAdapter {

    Context mContext;
    int layoutResourceId;
    NavDrawerItem[] data = null;

    public NavDrawerAdapter(Context context, int resource, NavDrawerItem[] data) {
        super(context, resource);
        this.layoutResourceId = resource;
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        view = inflater.inflate(layoutResourceId, parent, false);

        ImageView icon = (ImageView) view.findViewById(R.id.nav_drawer_item_icon);
        TextView title = (TextView) view.findViewById(R.id.nav_drawer_item_title);

        NavDrawerItem currentItem = data[position];

        title.setText(currentItem.getTitle());
        icon.setImageResource(currentItem.getIconId());

        return view;
    }

    @Override
    public int getCount() {
        return data.length;
    }
}
