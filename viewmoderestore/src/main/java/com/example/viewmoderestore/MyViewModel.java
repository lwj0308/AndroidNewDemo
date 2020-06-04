package com.example.viewmoderestore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private SavedStateHandle handle;
    private final static String KEY = "NUMBER";

    public MyViewModel(SavedStateHandle handle) {
        if (!handle.contains(KEY)){
            handle.set(KEY,0);
        }
        this.handle = handle;
    }
    public LiveData<Integer> getNumber(){
        return handle.getLiveData(KEY);
    }

    public void add(){
//        handle.set(KEY,(int) handle.get(KEY)+1);
        handle.set(KEY,getNumber().getValue()+1);
    }
}
