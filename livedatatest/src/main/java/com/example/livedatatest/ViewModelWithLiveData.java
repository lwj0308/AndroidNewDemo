package com.example.livedatatest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelWithLiveData extends ViewModel {
    private MutableLiveData<Integer> liveNumber;//对象类型，不是原始数据，要保证不是空的

    /*ViewModelWithLiveData (){
        liveNumber = new MutableLiveData<>();
        liveNumber.setValue(0);
    }*/

    //这样比上一个高效
    public MutableLiveData<Integer> getLiveNumber() {
        if (liveNumber == null) {
            liveNumber = new MutableLiveData<>();
            liveNumber.setValue(0);
        }
        return liveNumber;
    }

    //代替set
    public void addNumberLiveNumber(int i) {
        liveNumber.setValue(liveNumber.getValue() + i);
    }
}
