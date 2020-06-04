package com.example.parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parcelable.databinding.ActivityMainBinding;

//<activity android:name=".Main2Activity"
//            android:process=":process2"></activity>
//进程之间，包括Activity之间传递对象数据的时候
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString().trim();
                int age = Integer.valueOf(binding.etAge.getText().toString());
                int math = Integer.valueOf(binding.etMath.getText().toString());
                int english = Integer.parseInt(binding.etEnglish.getText().toString());
                Student student = new Student(name, age, new Score(math, english));

                //破坏了程序的模块化
//                MyApplication application = (MyApplication) getApplication();
//                application.student = student;


                //把数据打包成Bundle
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("student", student);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

    }
}
