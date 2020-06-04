package com.example.livedatatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewModelWithLiveData viewModelWithLiveData;
    private TextView tv_number;
    private ImageButton ib_up, ib_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_number = findViewById(R.id.tv);
        ib_down = findViewById(R.id.ib_down);
        ib_up = findViewById(R.id.ib_up);
        viewModelWithLiveData = ViewModelProviders.of(this).get(ViewModelWithLiveData.class);
        //rxJava?
        viewModelWithLiveData.getLiveNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tv_number.setText(String.valueOf(integer));
            }
        });

        ib_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModelWithLiveData.addNumberLiveNumber(1);
            }
        });
        ib_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModelWithLiveData.addNumberLiveNumber(-1);
            }
        });
    }
}
