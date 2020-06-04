package com.example.parcelable;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.parcelable.databinding.ActivityMain2Binding;

public class Main2Activity extends AppCompatActivity {
    //1->2还不算，它们在同一个进程内
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        Bundle data = getIntent().getBundleExtra("data");
        Student student = data.getParcelable("student");

        //破坏了程序的模块化
        //如果在另一个进程，出错：Application对象不一样
//        MyApplication application = (MyApplication) getApplication();
//        Student student = application.student;


        binding.tvName.setText(student.getName());
        binding.tvAge.setText(String.valueOf(student.getAge()));
        binding.tvMath.setText(String.valueOf(student.getScore().getMath()));
        binding.tvEnglish.setText(String.valueOf(student.getScore().getEnglish()));

        //创建Application子类
    }
}
