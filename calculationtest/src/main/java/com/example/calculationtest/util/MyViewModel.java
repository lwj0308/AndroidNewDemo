package com.example.calculationtest.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private final static String KEY_HIGH_SCORE = "key_high_score"; // 最高分
    private final static String KEY_LEFT_NUMBER = "key_left_number";// 运算符左边数字
    private final static String KEY_RIGHT_NUMBER = "key_right_number";// 运算符右边数字
    private final static String KEY_OPERATOR = "key_operator";// 运算符
    private final static String KEY_ANSWER = "key_answer";// 答案
    private final static String KEY_CURRENT_SCORE = "key_current_score";//当前分数
    private final static String SAVE_SP_NAME = "save_sp_name";// SharedPreferences 名字
    public SharedPreferences sp;
    public boolean win_flag = false;// 做题情况，为 true 则当前为获胜，false 则当前为失败

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {
            sp = getApplication().getSharedPreferences(SAVE_SP_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE, sp.getInt(KEY_HIGH_SCORE, 0));
            handle.set(KEY_LEFT_NUMBER, 0);
            handle.set(KEY_RIGHT_NUMBER, 0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_ANSWER, 0);
            handle.set(KEY_CURRENT_SCORE, 0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(KEY_ANSWER);
    }

    public void generator() {
        int LEVEL = 100;
        Random random = new Random();
        int x, y;
        x = random.nextInt(LEVEL) + 1;
        y = random.nextInt(LEVEL) + 1;

        if (x % 2 == 0) {
            getOperator().setValue("+"); // x 为偶数则运算符为"+"
            if (x > y) {//x=10 y=8
                getAnswer().setValue(x); // 将较大的数设为答案，则加数与被加数都可以表达出来
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x - y);
            } else {//x=8 y=10
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x > y) { //x=10 y=8
                getAnswer().setValue(x - y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            } else {//x=8 y=10
                getAnswer().setValue(y - x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }

    }

    @SuppressWarnings("ConstantConditions")
    public void save() {
        sp.edit().putInt(KEY_HIGH_SCORE, getHighScore().getValue()).apply();
    }

    @SuppressWarnings("ConstantConditions")
    public void answerCorrect() {// 答对问题
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generator();
    }

}
