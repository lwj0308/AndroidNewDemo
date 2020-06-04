package com.example.lifecycles;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class MyChronometer extends Chronometer implements LifecycleObserver {
    /**
     * 用static，旋转就不会被初始化了，不是最好的办法
     */
//    private long elapsedTime;
    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pauseMeter() {
        MainActivity.myViewModel.getElapsedTime().setValue(SystemClock.elapsedRealtime() - getBase());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resumeMeter() {
        setBase(SystemClock.elapsedRealtime() - MainActivity.myViewModel.getElapsedTime().getValue());
        start();
    }
}
