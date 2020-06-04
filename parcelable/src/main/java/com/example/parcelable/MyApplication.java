package com.example.parcelable;

import android.app.Application;

//单个进程，就有Application对象
//一个新的进程产生一个新的Application对象
//android:name=".MyApplication"
public class MyApplication extends Application {
    //共享数据放在这里，全局都看得到
     Student student;
}
