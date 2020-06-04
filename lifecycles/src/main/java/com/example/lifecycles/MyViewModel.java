package com.example.lifecycles;

import android.os.SystemClock;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Long> elapsedTime;

    public MutableLiveData<Long> getElapsedTime() {
        if (elapsedTime == null) {
            elapsedTime = new MutableLiveData<>();
            elapsedTime.setValue((long) 0);
        }
        return elapsedTime;
    }
}
