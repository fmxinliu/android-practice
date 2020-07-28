package com.example.xinliu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_third);

        btnReturn = (Button) findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, OpenedActivity.class);
                startActivity(intent);
            }
        });
    }
}
