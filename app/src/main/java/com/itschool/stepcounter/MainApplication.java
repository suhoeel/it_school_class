package com.itschool.stepcounter;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MainApplication extends Application {

    /**
     * 싱글턴 패턴을 사용 (자세한 내용은 자바 디자인패턴 공부)
     * 싱글턴 패턴은 멀티 스레드 환경 혹은, 여러번 호출 시 단 한번의 호출로 단 하나의 인스턴스만 유지되게 하는 방법이며
     * 가끔 instance가 날라가도 다시 생성하며 오로지 단 하나의 인스턴스만 유지시킨다.
     */

    /**
     * 안드로이드의 Context 여러 컴포넌트 사이에서 안드로이드의 글로벌한 정보를 저장 및 제공해주는 인터페이스 역할을 하며,
     * Application, Activity는 각각의 context를 가지고 있기 때문에 특정 함수 호출시 넘겨주는 형식으로 사용되기도 한다.
     * 하지만 Context는 잘못 사용하면 Memory Leak 이슈를 야기하기도 함으로 주의해서 사용해야 한다.
     */

    private static MainApplication instance;

    public static Context getInstance() {
        if (instance == null) {
            synchronized (MainApplication.class) {
                if (instance == null)
                    instance = new MainApplication();
            }
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = MainApplication.this;

    }


}
