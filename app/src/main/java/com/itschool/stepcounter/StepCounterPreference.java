package com.itschool.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;

public class StepCounterPreference {

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    public StepCounterPreference(Context context) {
        prefs = context.getSharedPreferences("STEP_COUNTER_PREFERENCES", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public void registerSharedPreferenceChangedListener() {

    }

    public void unregisterSharedPreferenceChangedListener() {

    }



}
