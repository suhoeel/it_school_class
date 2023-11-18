package com.itschool.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounter {

    private Context context;
    private SensorEventListener sensorEventListener;
    private SensorManager sensorManager;
    private Sensor sensor;

    public StepCounter(
            Context context,
            SensorEventListener sensorEventListener
    ) {
        this.context = context;
        this.sensorEventListener = sensorEventListener;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    public void registerStepCounter() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_UI
        );
    }

    public void unregisterStepCounter() {
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }

}
