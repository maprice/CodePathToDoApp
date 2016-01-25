package com.example.mprice.mptodo;

/**
 * Created by mprice on 1/13/16.
 */

import android.app.Application;

import com.example.mprice.mptodo.model.MPTaskDatabase;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
