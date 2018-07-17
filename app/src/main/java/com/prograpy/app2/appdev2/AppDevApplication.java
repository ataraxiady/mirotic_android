package com.prograpy.app2.appdev2;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.prograpy.app2.appdev2.utils.PreferenceData;

public class AppDevApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceData.init(this);

    }
}
