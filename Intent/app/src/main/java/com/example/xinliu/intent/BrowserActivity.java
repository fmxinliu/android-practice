package com.example.xinliu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrowserActivity extends AppCompatActivity {
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        btnReturn = (Button) findViewById(R.id.btn_return_browser);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowserActivity.this, OpenedActivity.class);
                startActivity(intent);
            }
        });
    }
}
