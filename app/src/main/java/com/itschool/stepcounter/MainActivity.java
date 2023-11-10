package com.itschool.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textTodayCount;
    private TextView textTodayGoal;
    private SeekBar seekBarTodayGoal;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTodayCount = findViewById(R.id.text_today_count);
        textTodayGoal = findViewById(R.id.text_today_goal);

        seekBarTodayGoal = findViewById(R.id.seekbar_today_goal);
        buttonReset = findViewById(R.id.button_reset);


    }



}