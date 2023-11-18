package com.itschool.stepcounter;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textTodayCountNumber, textTodayGoal;
    private SeekBar seekBarTodayGoal;
    private Button buttonStartEnd, buttonReset, buttonRequestActivityRecognition, buttonRequestPushNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTodayCountNumber = findViewById(R.id.text_today_count_number);
        textTodayGoal = findViewById(R.id.text_today_goal);

        seekBarTodayGoal = findViewById(R.id.seekbar_today_goal);
        buttonStartEnd = findViewById(R.id.button_start_end);
        buttonReset = findViewById(R.id.button_reset);
        buttonRequestActivityRecognition = findViewById(R.id.button_request_permission_activity_recognition);
        buttonRequestPushNotification = findViewById(R.id.button_request_permission_push_notification);


        textTodayCountNumber.setText("" + StepCounterPreference.getInstance().getTodayCountByPreference());

        initButtons();


    }

    /**
     * 모든 버튼 초기화
     * 변경
     */
    private void initButtons() {
        buttonStartEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isStepCounterAvailable()) {
                    Toast.makeText(MainActivity.this, "이 기기는 만보기 서비스를 이용할 수 없는 기기입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainApplication.getInstance(), StepCounterService.class);

                if (foregroundServiceRunning()) {
                    Log.d("TEST", "1");

                    stopService(intent);
                } else {
                    Log.d("TEST", "2");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                }
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepCounterPreference.getInstance().clearAll();
            }
        });

        buttonRequestActivityRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionActivityRecognition();
            }
        });

        buttonRequestPushNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionPushNotification();
            }
        });
    }

    /**
     * 데이터 변경을 감지하는 Event Listener(Interface) 등록/해제
     * 등록 -> registerDBListener
     * 해제 -> unregisterDBListener
     * 안드로이드의 생명주기에 맞게 사용해야함
     */

    private SharedPreferences.OnSharedPreferenceChangeListener dbListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Timestamp today = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String todayDate = sdf.format(today);
            Log.d("TEST", "key " + key);
            Log.d("TEST", "todayDate " + todayDate);
            if(key.equals(todayDate)) {
                textTodayCountNumber.setText("" + StepCounterPreference.getInstance().getTodayCountByPreference());
            }
        }
    };

    private void registerDBListener() {
        StepCounterPreference.getInstance().registerSharedPreferenceChangedListener(dbListener);
    }

    private void unregisterDBListener() {
        StepCounterPreference.getInstance().unregisterSharedPreferenceChangedListener(dbListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerDBListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterDBListener();
    }

    private boolean isStepCounterAvailable() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        return (stepCounterSensor != null);
    }

    private void checkPermissionActivityRecognition() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
        ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1001);
        }
    }

    private void checkPermissionPushNotification() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1002);
        }
    }

    private boolean foregroundServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (StepCounterService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}