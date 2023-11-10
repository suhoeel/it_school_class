package com.itschool.stepcounter;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MainApplication extends Application {

    private static MainApplication instance;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = MainApplication.this;

    }



    public static Context getInstance() { return instance; }


}
