package com.itschool.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 날짜(Key)로 데이터(value)를 저장
 * SharedPreference란 안드로이드SDK 에서 제공하는 데이터를 local file로 저장 가능하게 해주는 인메모리 데이터베이스
 */

public class StepCounterPreference {

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    public StepCounterPreference(Context context) {
        prefs = context.getSharedPreferences("STEP_COUNTER_PREFERENCES", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public void registerSharedPreferenceChangedListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener
    ) {
        this.onSharedPreferenceChangeListener = listener;
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterSharedPreferenceChangedListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener
    ) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
        onSharedPreferenceChangeListener = null;
    }

    /**
     * 마지막 시스템 값 저장
     */

    public void setLastSystemCount(int count) {
        prefsEditor.putInt(STEP_COUNTER_LAST_SYSTEM_COUNT, count).apply();
    }


    /**
     * 마지막 시스템 값 불러오기
     */
    public int getLastSystemCountByPreference() {
        return prefs.getInt(STEP_COUNTER_LAST_SYSTEM_COUNT, 0);
    }


    /**
     * 오늘 날짜 걸음수 + 1
     */

    public void addOneStepToday() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(timestamp);

        int preCount = getTodayCountByPreference();
        int nextCount = preCount + 1;
        prefsEditor.putInt(date, nextCount).apply();
    }

    /**
     *  오늘 날짜 걸음수 + count
     */
    public void addCountToday(int count) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(timestamp);

        int preCount = getTodayCountByPreference();
        int nextCount = preCount + count;
        prefsEditor.putInt(date, nextCount).apply();
    }

    /**
     * 오늘 날짜 걸음수 가져오기
     */
    public int getTodayCountByPreference() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(timestamp);
        return prefs.getInt(date, 0);
    }

    /**
     * 만보기 데이터 전체 삭제
     */
    public void clearAll() {
        prefsEditor.clear().apply();
    }

    /**
     * 앞서 MainApplication.class 에서 설명한 것과 같은 Singleton 객체 생성
     */
    private static StepCounterPreference instance;

    private static final String STEP_COUNTER_PREFERENCE = "step_counter_preference";
    private static final String STEP_COUNTER_LAST_SYSTEM_COUNT = "step_counter_last_system_count";
    private static final String STEP_COUNTER_REBOOT_AVAILABLE = "step_counter_reboot_available";

    public static StepCounterPreference getInstance() {
        if (instance == null) {
            synchronized (MainApplication.class) {
                if (instance == null)
                    instance = new StepCounterPreference(MainApplication.getInstance());
            }
        }
        return instance;
    }


}
