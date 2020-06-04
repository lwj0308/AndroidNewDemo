package com.example.viewmodelsp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.viewmodelsp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MyViewModel myViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        myViewModel.application = getApplication();
//        myViewModel.context = getApplicationContext();
//        myViewModel = ViewModelProviders.of(this, new SavedStateViewModelFactory(getApplication(), this)).get(MyViewModel.class);
        myViewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(this);
    }

    /**
     * onStop()和onDestroy()存放数据不可靠
     */
    @Override
    protected void onPause() {
        super.onPause();
        myViewModel.save();
    }

}
