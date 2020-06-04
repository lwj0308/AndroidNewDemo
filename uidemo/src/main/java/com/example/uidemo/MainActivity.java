package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private EditText et_number;
    private ImageView imageView;
    private Button left, right, confirm;
    private Switch aSwitch;
    private ProgressBar pb_edit;
    private RadioGroup rg;
    private SeekBar sb;
    private CheckBox checkBox, checkBox2, checkBox3;
    private RatingBar ratingBar;

    private String yuWen = "";
    private String shuXue = "";
    private String yingYu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void setListener() {
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(R.string.left);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(R.string.right);
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    display.setText("开");
                } else {
                    display.setText("关");
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et_number.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    pb_edit.setProgress(0);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pb_edit.setProgress(Integer.parseInt(num), true);
                    }
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        imageView.setImageResource(R.mipmap.android);
                        break;
                    case R.id.radioButton2:
                        imageView.setImageResource(R.mipmap.apple);
                        break;
                    default:
                        break;
                }
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                display.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yuWen = "语文";
                } else {
                    yuWen = "";
                }
                display.setText(yuWen+shuXue+yingYu);
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    shuXue = "数学";
                } else {
                    shuXue = "";
                }
                display.setText(yuWen+shuXue+yingYu);
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yingYu = "英语";
                } else {
                    yingYu = "";
                }
                display.setText(yuWen+shuXue+yingYu);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(rating)+"星评价！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM,0,25);
                toast.show();
            }
        });
    }

    private void initView() {
        display = findViewById(R.id.display);
        et_number = findViewById(R.id.et_number);
        imageView = findViewById(R.id.imageView);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        confirm = findViewById(R.id.confirm);
        aSwitch = findViewById(R.id.aSwitch);
        pb_edit = findViewById(R.id.pb_edit);
        rg = findViewById(R.id.rg);
        sb = findViewById(R.id.sb);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        ratingBar = findViewById(R.id.ratingBar);
    }

}
