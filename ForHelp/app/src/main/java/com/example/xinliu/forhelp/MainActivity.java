package com.example.xinliu.forhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent();

        // 自定义广播的事件类型
        intent.setAction("cn.edu.jzsz.Help");

        // 发送广播
        sendBroadcast(intent);
    }

    public void sendOrderedBroadcast(View view) {
        Intent intent = new Intent();

        // 自定义广播的事件类型
        intent.setAction("cn.edu.jzsz.OrderedHelp");

        // 发送有序广播
//        sendOrderedBroadcast(intent, null);

        // 保证在广播被拦截的情况下，也能收到广播
        MyReceiverTwo myReceiverTwo = new MyReceiverTwo();
        sendOrderedBroadcast(intent, null, myReceiverTwo, null, 0, null, null);

        // |---One---|---Three---|---Two---|---Four---|
        //     接收       拦截         接收      未接收
    }
}
