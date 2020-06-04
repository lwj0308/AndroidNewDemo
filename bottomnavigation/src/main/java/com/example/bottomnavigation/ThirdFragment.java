package com.example.bottomnavigation;

import androidx.lifecycle.ViewModelProviders;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

public class ThirdFragment extends Fragment {
    private ImageView imageView;
    private ThirdViewModel mViewModel;

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, container, false);
        imageView = view.findViewById(R.id.imageView3);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ThirdViewModel.class);
        imageView.setX(imageView.getPivotX() + mViewModel.dX);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "x", 0, 0);
        animator.setDuration(500);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animator.isRunning()) {
                    float dX = new Random().nextBoolean() ? 100 : -100;
                    animator.setFloatValues(imageView.getX(), imageView.getX() + dX);
                    mViewModel.dX += dX;
                    animator.start();
                }
            }
        });
    }

}
