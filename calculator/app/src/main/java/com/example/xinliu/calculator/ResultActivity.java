package com.example.xinliu.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class ResultActivity extends Activity {
    private Button backBtn;
    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_result);

        Intent intent = this.getIntent();
        infoText = (TextView)this.findViewById(R.id.resultTxt);
        StringBuilder builder = new StringBuilder();
        builder.append(intent.getStringExtra("firstNumber"))
                .append(" ")
                .append(ConstantInfo.infoMap.get(intent.getIntExtra("calcType", 0)))
                .append(" ").append(intent.getStringExtra("secondNumber"))
                .append(" ").append("=").append(" ")
                .append(intent.getIntExtra("result", -1));
        infoText.setText(builder.toString());
        backBtn = (Button)this.findViewById(R.id.back);
        backBtn.setOnClickListener(new BackListener());
    }

    class BackListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            ResultActivity.this.finish();
        }
    }
}
