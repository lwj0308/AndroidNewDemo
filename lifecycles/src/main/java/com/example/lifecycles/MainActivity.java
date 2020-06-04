package com.example.lifecycles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    MyChronometer chronometer;
    static MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        chronometer = findViewById(R.id.meter);
        getLifecycle().addObserver(chronometer);
    }
}
