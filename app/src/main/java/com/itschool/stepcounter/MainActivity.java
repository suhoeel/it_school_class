package com.itschool.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textTodayCountNumber;
    private TextView textTodayGoal;
    private SeekBar seekBarTodayGoal;
    private Button buttonReset;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTodayCountNumber = findViewById(R.id.text_today_count_number);
        textTodayGoal = findViewById(R.id.text_today_goal);

        seekBarTodayGoal = findViewById(R.id.seekbar_today_goal);
        buttonReset = findViewById(R.id.button_reset);



        StepCounter stepCounter = new StepCounter(MainApplication.getInstance(),
                new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        int getData = (int) event.values[0];
                        count++;
                        textTodayCountNumber.setText("" + count);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                });


        stepCounter.registerStepCounter();


    }


    private void a() {

    }

    private int b() {
        return 10;
    }

    private boolean test() {
        return true;
    }


}