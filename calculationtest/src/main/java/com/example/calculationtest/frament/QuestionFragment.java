package com.example.calculationtest.frament;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculationtest.databinding.FragmentQuestionBinding;
import com.example.calculationtest.util.MyViewModel;
import com.example.calculationtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    private MyViewModel myViewModel;
    private FragmentQuestionBinding binding;
    private StringBuilder builder;
    private View.OnClickListener listener;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_question, container, false);
//        myViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        setListener();
        setBtListener();
        return binding.getRoot();
    }

    private void setListener() {
        builder = new StringBuilder();
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bt_0:
                        builder.append("0");
                        break;
                    case R.id.bt_1:
                        builder.append("1");
                        break;
                    case R.id.bt_2:
                        builder.append("2");
                        break;
                    case R.id.bt_3:
                        builder.append("3");
                        break;
                    case R.id.bt_4:
                        builder.append("4");
                        break;
                    case R.id.bt_5:
                        builder.append("5");
                        break;
                    case R.id.bt_6:
                        builder.append("6");
                        break;
                    case R.id.bt_7:
                        builder.append("7");
                        break;
                    case R.id.bt_8:
                        builder.append("8");
                        break;
                    case R.id.bt_9:
                        builder.append("9");
                        break;
                    case R.id.bt_clear:
                        builder.setLength(0);
//                        builder = new StringBuilder();
                        break;
                }

                if (builder.length() == 0) {
                    binding.tvYourAnswer.setText(getString(R.string.input_indicator));
                } else {
                    binding.tvYourAnswer.setText(builder.toString());
                }
            }
        };
    }

    private void setBtListener() {
        binding.bt0.setOnClickListener(listener);
        binding.bt1.setOnClickListener(listener);
        binding.bt2.setOnClickListener(listener);
        binding.bt3.setOnClickListener(listener);
        binding.bt4.setOnClickListener(listener);
        binding.bt5.setOnClickListener(listener);
        binding.bt6.setOnClickListener(listener);
        binding.bt7.setOnClickListener(listener);
        binding.bt8.setOnClickListener(listener);
        binding.bt9.setOnClickListener(listener);
        binding.btClear.setOnClickListener(listener);
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View v) {
                if (builder.length() == 0) {
                    builder.append("-1");
                }
                if (Integer.valueOf(builder.toString()).intValue() == myViewModel.getAnswer().getValue()) {
                    myViewModel.answerCorrect();
                    builder.setLength(0);
                    binding.tvYourAnswer.setText(R.string.answer_correct_message);
                } else {
                    NavController controller = Navigation.findNavController(v);
                    if (myViewModel.win_flag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        myViewModel.win_flag = false;
                        myViewModel.save();
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            }
        });
    }

}
