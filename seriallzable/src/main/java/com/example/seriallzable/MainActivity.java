package com.example.seriallzable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "myfile_data";
    private EditText et_name, et_age, et_math, et_english, et_chinese;
    private TextView tv_grade;
    private Button bt_save, bt_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_math = findViewById(R.id.et_math);
        et_english = findViewById(R.id.et_english);
        et_chinese = findViewById(R.id.et_chinese);
        tv_grade = findViewById(R.id.tv_grade);
        bt_save = findViewById(R.id.bt_save);
        bt_load = findViewById(R.id.bt_load);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNull = TextUtils.isEmpty(et_name.getText())
                        || TextUtils.isEmpty(et_age.getText())
                        || TextUtils.isEmpty(et_math.getText())
                        || TextUtils.isEmpty(et_english.getText())
                        || TextUtils.isEmpty(et_chinese.getText());
                if (isNull) {
                    Toast.makeText(MainActivity.this, "请输入数据！", Toast.LENGTH_SHORT).show();
                    return;
                }
                int math = Integer.valueOf(et_math.getText().toString());
                int english = Integer.valueOf(et_english.getText().toString());
                int chinese = Integer.valueOf(et_chinese.getText().toString());
                Score score = new Score(math, english, chinese);
                String name = et_name.getText().toString();
                int age = Integer.valueOf(et_age.getText().toString());
                Student student = new Student(name, age, score);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput(FILE_NAME, MODE_PRIVATE));
                    objectOutputStream.writeObject(student);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    et_name.getText().clear();
                    et_age.getText().clear();
                    et_math.getText().clear();
                    et_english.getText().clear();
                    et_chinese.getText().clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bt_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(openFileInput(FILE_NAME));
                    Student student = (Student) objectInputStream.readObject();
                    et_name.setText(student.getName());
                    et_age.setText(String.valueOf(student.getAge()));
                    et_math.setText(String.valueOf(student.getScore().getMath()));
                    et_english.setText(String.valueOf(student.getScore().getEnglish()));
                    et_chinese.setText(String.valueOf(student.getScore().getChinese()));
                    tv_grade.setText(student.getScore().getGrade());
                    Toast.makeText(MainActivity.this, "读取成功！", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
