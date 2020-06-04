package com.example.pagingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    Button bt_populate, bt_clear;
    StudentsDatabase database;
    StudentDao dao;
    MyPagedAdapter adapter;
    LiveData<PagedList<Student>> allStudentsLivePaged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_main);
        bt_populate = findViewById(R.id.bt_populate);
        bt_clear = findViewById(R.id.bt_clear);

        adapter = new MyPagedAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//分隔符
        rv.setAdapter(adapter);

        database = StudentsDatabase.getInstance(this);
        dao = database.getStudentDao();

        allStudentsLivePaged = new LivePagedListBuilder<>(dao.getAllStudents(), 2)
                .build();
        allStudentsLivePaged.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(final PagedList<Student> students) {
                adapter.submitList(students);
                //防止内存泄漏，内存安全
                students.addWeakCallback(null, new PagedList.Callback() {
                    @Override
                    public void onChanged(int position, int count) {
                        Log.i("Observe", "onChanged: " + students);
                    }

                    @Override
                    public void onInserted(int position, int count) {

                    }

                    @Override
                    public void onRemoved(int position, int count) {

                    }
                });
            }
        });

        bt_populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student[] students = new Student[1000];
                for (int i = 0; i < 1000; i++) {
                    Student student = new Student();
                    student.setStudentNum(i);
                    students[i] = student;
                }
                new InsertAsyncTask(dao).execute(students);
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClearAsyncTask(dao).execute();
            }
        });
    }

    static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {
        StudentDao dao;//静态类无法访问外面的域

        InsertAsyncTask(StudentDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.insertStudents(students);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
        StudentDao dao;//静态类无法访问外面的域

        ClearAsyncTask(StudentDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllStudents();
            return null;
        }
    }
}
