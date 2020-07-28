package com.example.xinliu.calculator;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText firstText;
    private EditText secondText;
    private TextView calcTypeText;

    private int firstNumber = 0;
    private int secondNumber = 0;
    // 0: +, 1: -, 2: * , 3: /
    private int calcType = -1;

    // button声明
    private Button selectBtn;
    private Button calcBtn;

    // request code
    public static final int requstCode = 0x123;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstText = this.findViewById(R.id.firstNumber);
        secondText = this.findViewById(R.id.secondNumber);

        selectBtn = this.findViewById(R.id.selectType);
        calcBtn = this.findViewById(R.id.calculate);

        selectBtn.setOnClickListener(new SelectCalculationTypeListener());
        calcBtn.setOnClickListener(new CalculationListener());

        calcTypeText = this.findViewById(R.id.calc_type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requstCode == requestCode &&
                CalculationTypeActivity.resultCode == resultCode) {
            Bundle bundle = data.getExtras();
            calcType = data.getIntExtra("type1", -1);
            Log.d(TAG, "CalcType:" + calcType);
            System.out.println("CalcType:" + calcType);
            calcTypeText.setText(ConstantInfo.typeMap.get(calcType));
        }
    }

    class SelectCalculationTypeListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,
                    CalculationTypeActivity.class);
            MainActivity.this.startActivityForResult(intent, requstCode);
        }
    }

    class CalculationListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,
                    ResultActivity.class);
            intent.putExtra("firstNumber", firstText.getText().toString());
            intent.putExtra("secondNumber", secondText.getText().toString());
            intent.putExtra("calcType", calcType);
            int result = 0;
            int firstNumber = Integer.parseInt(firstText.getText().toString());
            int secondNumber = Integer.parseInt(secondText.getText().toString());
            switch (calcType) {
            case 1:
                result = firstNumber + secondNumber;
                break;
             case 2:
                 result = firstNumber - secondNumber;
                 break;
            case 3:
                result = firstNumber * secondNumber;
                break;
            case 4:
                result = firstNumber / secondNumber;
                break;
            default:
                result = 1;
                break;
            }
            intent.putExtra("result", result);
            startActivity(intent);
        }
    }
}
