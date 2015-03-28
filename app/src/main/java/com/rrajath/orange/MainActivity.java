package com.rrajath.orange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.rrajath.orange.data.NavDrawerItem;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private NavDrawerItem[] mNavDrawerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set main content view
        setContentView(R.layout.activity_main_appbar);

        // Set toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String[] navDrawerTitles = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        buildNavDrawerItems();
        // Set adapter for list view
        NavDrawerAdapter navDrawerAdapter = new NavDrawerAdapter(this, R.layout.nav_drawer_list_item, mNavDrawerItems);
        mDrawerList.setAdapter(navDrawerAdapter);
/*
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }

            private void selectItem(int position) {
            }
        });
*/

/*
        Ion.with(this)
                .load("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "Error fetching top stories", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        System.out.println(result.get(0));
                    }
                });
*/
    }

    private NavDrawerItem[] buildNavDrawerItems() {
        mNavDrawerItems = new NavDrawerItem[4];
        mNavDrawerItems[0] = new NavDrawerItem(R.drawable.ic_class_black, "Favorites");
        mNavDrawerItems[1] = new NavDrawerItem(R.drawable.ic_settings_applications_black, "Settings");
        mNavDrawerItems[2] = new NavDrawerItem(R.drawable.ic_grade_black, "Rate the app");
        mNavDrawerItems[3] = new NavDrawerItem(R.drawable.ic_comment_black, "Feedback");
        return mNavDrawerItems;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
}
