package com.rrajath.orange;

import android.app.Application;
import android.content.Context;

/**
 * Created by rrajath on 3/18/15.
 */
public class OrangeApplication extends Application {

    // Metrics
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        // initialize metrics class to collect metrics
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // close metrics context

    }
}
