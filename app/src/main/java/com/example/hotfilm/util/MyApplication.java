package com.example.hotfilm.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by yubin on 2017/2/17.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
