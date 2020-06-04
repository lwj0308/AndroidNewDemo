package com.example.viewmodeltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * ViewModelProviders方法已弃用，
 * 需要在build.gradle文件添加implementation 'android.arch.lifecycle:extensions:1.1.1'，
 * 更新项目之后才可以导入ViewModelProviders包。
 * <p>
 * Activity走了onDestroy()方法并不带表这个Activity就结束了。可以通过Activity的isFinishing()方法来判断。
 * 我们发现，在旋转屏幕的时候isFinishing()方法返回false。在按下返回键的时候isFinishing()为true。
 */
public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button button, button2;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        tv = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        tv.setText(String.valueOf(myViewModel.number));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(--myViewModel.number));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(++myViewModel.number));
            }
        });
    }
}
