package com.rrajath.orange;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rrajath.orange.data.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rrajath on 3/28/15.
 */
public class NavDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private ClickListener mClickListener;
    private NavDrawerAdapter mNavDrawerAdapter;
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
//                if (slideOffset < 0.6) {
//                    toolbar.setAlpha(1 - slideOffset);
//                }
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.drawerList);
        mNavDrawerAdapter = new NavDrawerAdapter(getActivity(), buildNavDrawerItems());
        mRecyclerView.setAdapter(mNavDrawerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "onClick", Toast.LENGTH_SHORT).show();
            }
        }));
        return view;
    }

    private List<NavDrawerItem> buildNavDrawerItems() {
        mNavDrawerItems = new ArrayList<>();
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_class_grey600_36dp, "Favorites"));
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_settings_applications_grey600_36dp, "Settings"));
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_grade_grey600_36dp, "Rate the app"));
        mNavDrawerItems.add(new NavDrawerItem(R.drawable.ic_comment_grey600_36dp, "Feedback"));
        return mNavDrawerItems;
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
