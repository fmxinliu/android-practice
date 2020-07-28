package com.example.xinliu.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    // 将MyService对外提供的服务通过MyBinder返回
    class MyBinder extends Binder {
        public void callMethodInService() {
            methodInService();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        MyBinder myBinder = new MyBinder();
        Log.i("BindService", "onBind():对象地址=" + myBinder);
        return myBinder;
    }

    // 对外提供的方法
    public void methodInService() {
        Log.i("BindService", "=>Service提供的服务");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BindService", "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("BindService", "onDestroy()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("BindService", "onUnbind()");
        Toast.makeText(this, "onUnbind()", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }
}
