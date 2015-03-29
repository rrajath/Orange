package com.rrajath.orange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rrajath.orange.data.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rrajath on 3/28/15.
 */
public class NavDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private NavDrawerAdapter navDrawerAdapter;
    private List<NavDrawerItem> mNavDrawerItems;

    public void setup(DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_drawer_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        navDrawerAdapter = new NavDrawerAdapter(getActivity(), buildNavDrawerItems());
        recyclerView.setAdapter(navDrawerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private List<NavDrawerItem> buildNavDrawerItems() {
        mNavDrawerItems = new ArrayList<>();
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_class_black, "Favorites"));
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_settings_applications_black, "Settings"));
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_grade_black, "Rate the app"));
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_comment_black, "Feedback"));
        return mNavDrawerItems;
    }
}
