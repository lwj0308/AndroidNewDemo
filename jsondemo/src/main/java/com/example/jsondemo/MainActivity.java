package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //JavaScript Object Notation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //序列化
//        Student student = new Student("Jack",20);
//        Gson gson = new Gson();
//        String jsonStudent = gson.toJson(student);

        //反序列化
//        Gson gson = new Gson();
//        String stu = "{\n" +
//                "  \"name\": \"tom\",\n" +
//                "  \"age\": 20\n" +
//                "}";
//        Student student = gson.fromJson(stu, Student.class);

        //Student+Score
//        Gson gson = new Gson();
//        Student stu = new Student("tom",19,new Score(80,90,80));
//        String jsonStudent = gson.toJson(stu);

//        Gson gson = new Gson();
//        String stu = "{\n" +
//                "  \"age\": 19,\n" +
//                "  \"name\": \"tom\",\n" +
//                "  \"score\": {\n" +
//                "    \"chinese\": 80,\n" +
//                "    \"english\": 90,\n" +
//                "    \"math\": 80\n" +
//                "  }\n" +
//                "}";
//        Student student = gson.fromJson(stu,Student.class);

        //array
//        Gson gson = new Gson();
//        Student student = new Student("Jack", 20, new Score(90, 89, 90));
//        Student student1 = new Student("Lili", 19, new Score(90, 89, 90));
//        Student[] students = {student,student1};
//        String jsonStu = gson.toJson(students);

//        Gson gson = new Gson();
//        String stus = "[\n" +
//                "  {\n" +
//                "    \"age\": 20,\n" +
//                "    \"name\": \"Jack\",\n" +
//                "    \"score\": {\n" +
//                "      \"chinese\": 90,\n" +
//                "      \"english\": 89,\n" +
//                "      \"math\": 90\n" +
//                "    }\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"age\": 19,\n" +
//                "    \"name\": \"Lili\",\n" +
//                "    \"score\": {\n" +
//                "      \"chinese\": 90,\n" +
//                "      \"english\": 89,\n" +
//                "      \"math\": 90\n" +
//                "    }\n" +
//                "  }\n" +
//                "]";
//        Student[] students = gson.fromJson(stus, Student[].class);//正列
//        List<Student> list = Arrays.asList(students);//添加，删除，排序。。。

//        Gson gson = new Gson();
//        Student student = new Student("Jack", 20, new Score(90, 89, 90));
//        Student student1 = new Student("Lili", 19, new Score(90, 89, 90));
//        List<Student> studentList = new ArrayList<>();
//        studentList.add(student);
//        studentList.add(student1);
//        String jsonStu = gson.toJson(studentList);

        //没法直接解析成List
        Gson gson = new Gson();
        List<Student> studentList = new ArrayList<>();
        String jsonStus = "[\n" +
                "  {\n" +
                "    \"age\": 20,\n" +
                "    \"name\": \"Jack\",\n" +
                "    \"score\": {\n" +
                "      \"chinese\": 90,\n" +
                "      \"english\": 89,\n" +
                "      \"math\": 90\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"age\": 19,\n" +
                "    \"name\": \"Lili\",\n" +
                "    \"score\": {\n" +
                "      \"chinese\": 90,\n" +
                "      \"english\": 89,\n" +
                "      \"math\": 90\n" +
                "    }\n" +
                "  }\n" +
                "]";
        //利用反射机制
        Type typeStu = new TypeToken<List<Student>>() {
        }.getType();
        studentList = gson.fromJson(jsonStus, typeStu);
    }
}
