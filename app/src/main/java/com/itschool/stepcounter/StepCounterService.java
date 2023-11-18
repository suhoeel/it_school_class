package com.itschool.stepcounter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class StepCounterService extends Service {

    /**
     * 걸음 수 저장을 위한 변수
     */
    private int currentCount = 0;

    /**
     * 안드로이드 SDK에서 제공하는 Notification 관련 클래스
     * notify(); 함수로 notification ui를 갱신 시킬 수 있다.
     */
    private NotificationManager notiManager;

    /**
     * 만보기를 개발할 수 있게 해준 Android Device Sensor의 데이터를 가져오게 해주는 Event Listener를 등록한 것
     * 우리 인터페이스 배웠지?
     * 인터페이스를 StepCounter() 클래스를 생성하는 동시에 초기화 하는 과정이고,
     * onCreate에서 서비스 생성될 때 stepCounter.registerStepCounter(); 를 호출하여 등록(register)해 준다.
     */
    private StepCounter stepCounter = new StepCounter(MainApplication.getInstance(), new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int currentStepCount = (int) event.values[0];
            Log.d("TEST", "currentStepCount " + currentStepCount);
            int lastSystemCount =
                    StepCounterPreference.getInstance().getLastSystemCountByPreference();
            Log.d("TEST", "lastSystemCount " + lastSystemCount);
            if(lastSystemCount == 0) {
                StepCounterPreference.getInstance().setLastSystemCount(currentStepCount);
                return;
            }

            int plusCount = currentStepCount - lastSystemCount;

            Log.d("TEST", "plusCount " + plusCount);

            if (plusCount > 0) {
                StepCounterPreference.getInstance().addCountToday(plusCount);
            }

            currentCount = StepCounterPreference.getInstance().getTodayCountByPreference();

            StepCounterPreference.getInstance().setLastSystemCount(currentStepCount);

            notiManager.notify(100, createNoti());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    });

    private PendingIntent notificationIntent;

    public Notification createNoti() {

        return new NotificationCompat.Builder(MainApplication.getInstance(), "step_counter_noti")
                .setContentTitle("만보기")
                .setContentText("걸음 수: " + currentCount)
                .setContentIntent(notificationIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setOngoing(true)
                .build();


    }

    @Override
    public void onCreate() {
        super.onCreate();
        notiManager = (NotificationManager) MainApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);
        notificationIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
        stepCounter.registerStepCounter();
        Log.d("TEST", "TEST");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        currentCount = StepCounterPreference.getInstance().getTodayCountByPreference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "step_counter_noti",
                    "notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("step_counter_notification");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            notiManager.createNotificationChannel(channel);
            // id 값은 의미로 세팅해 주는 것이고 0이 되면 안됨.
            startForeground(100, createNoti());
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        stepCounter.unregisterStepCounter();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
