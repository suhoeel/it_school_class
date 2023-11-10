package com.itschool.stepcounter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class StepCounterService extends Service {
    private NotificationManager notificationManager = (NotificationManager) MainApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);

    /*private StepCounter stepCounter = new StepCounter(MainApplication.getInstance(), new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    });*/

    private PendingIntent notificationIntent;

    private Notification notificationBuilder;

    StepCounterService() {
        notificationIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder = new NotificationCompat.Builder(MainApplication.getInstance(), "step_counter_noti")
//                .setContentTitle()
//                .setContentText()
                .build();

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
