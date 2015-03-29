package com.rrajath.orange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set main content view
        setContentView(R.layout.activity_main_appbar);

        // Set toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set fragment for navigation drawer
        NavDrawerFragment drawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        drawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
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
