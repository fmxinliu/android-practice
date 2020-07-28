package com.example.xinliu.helloworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvShow;
    private Button btnAsk1;
    private Button btnAsk2;
    private Button btnAsk;
    private RadioGroup rdgObjName;
    private String name;
    private RadioButton rbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = findViewById(R.id.tvShow);
        btnAsk1 = findViewById(R.id.btnAsk1);
        btnAsk2 = findViewById(R.id.btnAsk2);
        btnAsk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShow.setText(R.string.ask_content2);
            }
        });

        btnAsk = findViewById(R.id.btnAsk);
        rdgObjName = findViewById(R.id.rdgObjName);
        btnAsk.setOnClickListener(this);
        rdgObjName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnObj1) {
                    rbtn = findViewById(R.id.rbtnObj1);
                    name = rbtn.getText().toString();
                } else {
                    rbtn = findViewById(R.id.rbtnObj2);
                    name = rbtn.getText().toString();
                }
            }
        });
    }

    public void click(View view) {
        tvShow.setText(R.string.ask_content1);
    }

    @Override
    public void onClick(View v) {
        tvShow.setText(name);
    }
}
