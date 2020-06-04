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
import android.widget.ImageButton;
import android.widget.ImageView;

public class FirstFragment extends Fragment {
    private ImageView imageView;
    private FirstViewModel mViewModel;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        imageView = view.findViewById(R.id.imageView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //管当前页面
        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        imageView.setRotation(mViewModel.rotationPosition);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0, 0);
        animator.setDuration(500);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animator.isRunning()) {
                    animator.setFloatValues(imageView.getRotation(), imageView.getRotation() + 100);
                    mViewModel.rotationPosition += 100;
                    animator.start();
                }
            }
        });
    }

}
