package com.rrajath.orange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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
