package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * navigation中的fragment的id 和 menu中的item的id要保持一致
 * :你是说底部的那个字体吗？   是可以取消变大的。你在styles.xml里头加一条，比如:
 * <style name="label_size">
 * <item name="android:textSize">14sp</item>
 * </style>    ，然后在activity_main里头将BottomNavigationView的参数设为：app:itemTextAppearanceActive="@style/label_size"
 * app:itemTextAppearanceInactive="@style/label_size"
 */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavController navController = Navigation.findNavController(this, R.id.fragment);
        //bottomNavigationView.getMenu(),navController.getGraph(),或者传页面ID
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();

        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
