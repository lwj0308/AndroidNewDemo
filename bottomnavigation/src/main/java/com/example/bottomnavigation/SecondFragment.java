package com.example.bottomnavigation;

import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SecondFragment extends Fragment {
    private ImageView imageView;
    private SecondViewModel mViewModel;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        imageView = view.findViewById(R.id.imageView2);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SecondViewModel.class);
        imageView.setScaleX(mViewModel.scaleFactor);
        imageView.setScaleY(mViewModel.scaleFactor);
        final ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 0, 0);
        final ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 0, 0);
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animatorX,animatorY);
        animatorX.setDuration(500);
        animatorY.setDuration(500);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animatorX.isRunning()) {
                    animatorX.setFloatValues(imageView.getScaleX() + 0.1f);
                    animatorY.setFloatValues(imageView.getScaleY() + 0.1f);
                    mViewModel.scaleFactor += 1;
                    animatorX.start();
                    animatorY.start();
                }
            }
        });
    }

}
