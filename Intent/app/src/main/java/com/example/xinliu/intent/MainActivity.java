package com.example.xinliu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnOpen;
    private Button btnDataPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = (Button) findViewById(R.id.btn_open);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpenedActivity.class);
                startActivity(intent);
            }
        });

        btnDataPass = (Button) findViewById(R.id.btn_data_pass);
        btnDataPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
    }
}
