package com.rrajath.orange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MaterialTabHost materialTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set main content view
        setContentView(R.layout.activity_main);

        // Set Toolbar
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set fragment for navigation drawer
        NavDrawerFragment drawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        drawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        mViewPager = (ViewPager)findViewById(R.id.tab_pager);
        mViewPager.setAdapter(new SlidingTabsPagerAdapter(getSupportFragmentManager()));
        materialTabHost = (MaterialTabHost) findViewById(R.id.material_tab_host);
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount() - 1);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                materialTabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            materialTabHost.addTab(
                    materialTabHost.newTab()
                            .setText(mViewPager.getAdapter().getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_favorites:
                startActivity(new Intent(this, WebViewActivity.class));
                return true;

            case R.id.action_rate_app:
                return true;

            case R.id.action_feedback:
                return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    class SlidingTabsPagerAdapter extends FragmentStatePagerAdapter {

        String[] hnCategories;
        public SlidingTabsPagerAdapter(FragmentManager fm) {
            super(fm);
            hnCategories = getResources().getStringArray(R.array.sliding_tabs_array);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return hnCategories[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CategoryFragment.newInstance("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
                case 1:
                    return CategoryFragment.newInstance("https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty");
                case 2:
                    return CategoryFragment.newInstance("https://hacker-news.firebaseio.com/v0/askstories.json?print=pretty");
                case 3:
                    return CategoryFragment.newInstance("https://hacker-news.firebaseio.com/v0/showstories.json?print=pretty");
                case 4:
                    return CategoryFragment.newInstance("https://hacker-news.firebaseio.com/v0/jobstories.json?print=pretty");
            }
            return CategoryFragment.newInstance("");
        }

        @Override
        public int getCount() {
            return hnCategories.length;
        }
    }

}
