package com.prograpy.app2.appdev2;

import android.app.Application;

import com.prograpy.app2.appdev2.utils.PreferenceData;

public class AppDevApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceData.init(this);
    }
}
