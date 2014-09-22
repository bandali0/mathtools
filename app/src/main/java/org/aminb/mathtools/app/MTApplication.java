package org.aminb.mathtools.app;

import android.app.Application;

public class MTApplication extends Application {
    private static MTApplication instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static MTApplication getInstance() {
        return instance;
    }
}
