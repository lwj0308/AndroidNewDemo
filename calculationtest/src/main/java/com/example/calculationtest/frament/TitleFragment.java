package com.example.calculationtest.frament;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculationtest.R;
import com.example.calculationtest.databinding.FragmentTitleBinding;
import com.example.calculationtest.util.MyViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {
    private MyViewModel myViewModel;
    private FragmentTitleBinding binding;

    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_title, container, false);
//        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        myViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_titleFragment_to_questionFragment);
                //刷新题目
                myViewModel.getCurrentScore().setValue(0);
                myViewModel.generator();
            }
        });
        return binding.getRoot();
    }

}
