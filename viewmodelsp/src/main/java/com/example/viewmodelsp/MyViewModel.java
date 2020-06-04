package com.example.viewmodelsp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

//AndroidViewModel->ViewModel
@SuppressWarnings("ConstantConditions")
public class MyViewModel extends AndroidViewModel {
    //    Application application;
//    Context context;
    private SavedStateHandle handle;
    private String key = getApplication().getResources().getString(R.string.data_key);
    private String sp_name = getApplication().getResources().getString(R.string.sp_name);
    private SharedPreferences sp = getApplication().getSharedPreferences(sp_name, Context.MODE_PRIVATE);

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        if (!handle.contains(key)) {
            load();
        }
    }

    public LiveData<Integer> getNumber() {
        return handle.getLiveData(key);
    }

    private void load() {
        int spInt = sp.getInt(key, 0);
        handle.set(key, spInt);
    }

    void save() {
        sp.edit().putInt(key, getNumber().getValue()).apply();
    }

    public void changeNum(int n) {
        handle.set(key, getNumber().getValue() == null ? 0 : getNumber().getValue() + n);
    }
}
