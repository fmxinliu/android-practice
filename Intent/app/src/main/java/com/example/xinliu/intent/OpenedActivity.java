package com.example.xinliu.intent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpenedActivity extends AppCompatActivity {
    private Button btnOpenThird;
    private Button btnOpenBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened);

        btnOpenThird = (Button) findViewById(R.id.btn_open_third);
        btnOpenThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Action需在AndroidManifest.xml中自定义
                intent.setAction("com.example.xinliu.myAction.LAUNCHER");
                startActivity(intent);
            }
        });

        btnOpenBrowser = (Button) findViewById(R.id.btn_open_browser);
        btnOpenBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // 隐式意图，会列出所有匹配的Activity，让用户选择
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
    }
}
