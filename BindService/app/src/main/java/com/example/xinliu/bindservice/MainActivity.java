package com.example.xinliu.bindservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyService.MyBinder myBinder;
    private MyConn myConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 绑定服务
    public void bindService(View view) {
        if (myConn == null) {
            myConn = new MyConn();
        }
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, myConn, BIND_AUTO_CREATE);
    }

    // 调用服务提供的方法
    public void callServiceMethod(View view) {
        if (myBinder != null) {
            myBinder.callMethodInService();
        }
    }

    // 解绑服务
    public void unbindService(View view) {
        if (myConn != null) {
            unbindService(myConn);
            myConn = null;
            myBinder = null;
        }
    }

    private class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            Log.i("BindService", "服务绑定成功:对象地址=" + myBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("BindService", "服务解除绑定");
        }
    }
}
