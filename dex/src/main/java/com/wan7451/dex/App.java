package com.wan7451.dex;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            PlugIn.loadClass(this, "");
            PlugIn.hookAMS();
            PlugIn.hookHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
