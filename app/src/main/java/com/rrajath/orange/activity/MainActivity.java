package com.rrajath.orange.activity;

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

import com.crashlytics.android.Crashlytics;
import com.rrajath.orange.R;
import com.rrajath.orange.fragment.CategoryFragment;
import com.rrajath.orange.fragment.NavDrawerFragment;
import com.rrajath.orange.utils.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.fabric.sdk.android.Fabric;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    @InjectView(R.id.app_bar) Toolbar mToolbar;
    @InjectView(R.id.tab_pager) ViewPager mViewPager;
    @InjectView(R.id.material_tab_host) MaterialTabHost materialTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        // Set main content view
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Set Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set fragment for navigation drawer
        NavDrawerFragment drawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        drawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        mViewPager.setAdapter(new SlidingTabsPagerAdapter(getSupportFragmentManager()));
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
                    return CategoryFragment.newInstance(Constants.TOP_STORIES_URL);
                case 1:
                    return CategoryFragment.newInstance(Constants.NEW_STORIES_URL);
                case 2:
                    return CategoryFragment.newInstance(Constants.ASK_HN_URL);
                case 3:
                    return CategoryFragment.newInstance(Constants.SHOW_HN_URL);
                case 4:
                    return CategoryFragment.newInstance(Constants.JOBS_HN_URL);
            }
            return CategoryFragment.newInstance("");
        }

        @Override
        public int getCount() {
            return hnCategories.length;
        }
    }

}
