package com.example.xinliu.autostartwhenboot;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private AutoStartWhenBootReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // 动态注册广播监听
//        receiver = new AutoStartWhenBootReceiver();
//        String action = "android.intent.action.BOOT_COMPLETED";
//        IntentFilter intentFilter = new IntentFilter(action);
//        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
    }
}
