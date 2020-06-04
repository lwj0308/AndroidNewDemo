package com.example.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.databinding.databinding.ActivityMainBinding;

/**
 * 1.在Gradle添加
 * dataBinding.enabled = true
 * dataBinding {
 *             enabled true
 *         }
 * 2.layout转换为binding类的
 * 3.在<data>里使用变量
 * 4.在Activity得到新的类：Layout文件名+Binding
 * 5.通过DataBindingUtil绑定布局
 *  通过binding得到成员控制控件（一般少回访，一般回绑Layout）
 *  设置binding数据：ViewModel的数据:setData()
 *  设置LiveData:setLifecycleOwner()
 */
public class MainActivity extends AppCompatActivity {
//    private Button button;
//    private TextView textView;
    private MyViewModel myViewModel;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(this);
    }
}
