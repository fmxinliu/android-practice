package com.example.xinliu.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CalculationTypeActivity extends Activity {
    private RadioGroup group;

    private RadioButton additionBtn;
    private RadioButton minusBtn;
    private RadioButton multiplyBtn;
    private RadioButton dividerBtn;

    private int calculationType = -1;

    // response Code
    public static final int resultCode = 0x321;

    private Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_type);

        additionBtn = (RadioButton)this.findViewById(R.id.addition);
        minusBtn = (RadioButton)this.findViewById(R.id.minus);
        multiplyBtn = (RadioButton)this.findViewById(R.id.multiply);
        dividerBtn = (RadioButton)this.findViewById(R.id.divider);

        group = (RadioGroup)this.findViewById(R.id.group);
        group.setOnCheckedChangeListener(new GroupSelectionListener());

        okBtn = (Button)this.findViewById(R.id.ok);
        okBtn.setOnClickListener(new OkListener());
    }

    class GroupSelectionListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radioButtonId = group.getCheckedRadioButtonId();
            RadioButton btn = (RadioButton)CalculationTypeActivity.this.findViewById(radioButtonId);
            // initialize the calculation Type
            if (btn.getId() == additionBtn.getId()) {
                calculationType = 1;
            }
            else if (btn.getId() == minusBtn.getId()) {
                calculationType = 2;
            }
            else if (btn.getId() == multiplyBtn.getId()) {
                calculationType = 3;
            }
            else if (btn.getId() == dividerBtn.getId()) {
                calculationType = 4;
            }
            System.out.println("current value:" + calculationType);
        }
    }

    class OkListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            intent.putExtra("type1", calculationType);
            Bundle bundle = new Bundle();
            bundle.putInt("type", calculationType);
            intent.putExtras(bundle);

            CalculationTypeActivity.this.setResult(resultCode, intent);
            CalculationTypeActivity.this.finish();
        }
    }
}
