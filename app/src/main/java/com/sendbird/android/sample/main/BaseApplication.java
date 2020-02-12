package com.sendbird.android.sample.main;


import android.app.Application;

import com.sendbird.android.SendBird;
import com.sendbird.android.sample.utils.PreferenceUtils;

public class BaseApplication extends Application {

    private static final String APP_ID = "84447E0C-52FC-49AF-B772-2F3205A9FDAA"; // US-1 Demo
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());

        SendBird.init(APP_ID, getApplicationContext());
    }
}
