package com.example.chronometerdemo;

import androidx.lifecycle.ViewModel;

public class ChronometerViewModel extends ViewModel {
    private long StartTime;

    public long getStartTime() {
        return StartTime;
    }

    public void setStartTime(long StartTime) {
        this.StartTime = StartTime;
    }
}
