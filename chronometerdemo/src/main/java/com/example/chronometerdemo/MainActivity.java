package com.example.chronometerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * 计时器
 * ViewModel的唯一的作用是管理UI的数据。ViewModel不能访问UI或者持有Activity/Fragment的引用
 * 一个Activity由于旋转而被销毁，但是ViewModel并不会销毁，新创建的Activity的实例仅仅是重新关联到已经存在的ViewModel。
 * ViewModel通常通过LiveData或者Data Binding来暴露信息
 */
public class MainActivity extends AppCompatActivity {
    private ChronometerViewModel chronometerViewModel;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建或者直接返回一个已经存在的ViewModel
        chronometerViewModel = ViewModelProviders.of(this).get(ChronometerViewModel.class);

        //在onCreate()方法中开始倒计时
        chronometer = findViewById(R.id.chronometer);

        if (chronometerViewModel.getStartTime() == 0) {
            //chronometerViewModel如果没设置过开始时间，那么说明这个新的ViewModel,
            //所以给它设置开始时间
            long startTime = SystemClock.elapsedRealtime();
            chronometerViewModel.setStartTime(startTime);
            //每次onCreate()方法都会重新设置base
            chronometer.setBase(startTime);
        } else {
            //否则ViewModel已经在上个Activity的onCreate()方法中创建过了，屏幕旋转以后，
            //ViewModel会被保存,我们直接获取ViewModel里持有的时间
            chronometer.setBase(chronometerViewModel.getStartTime());
        }
        chronometer.start();
    }
}
