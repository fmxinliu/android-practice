package com.example.xinliu.activitylife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

// ctrl + o 重写方法
public class MainActivity extends AppCompatActivity {

    /**
     * Activity 的 5 个状态， 7个方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Activity 启动，不可见
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("activity_life", "onCreate()");
    }

    @Override
    protected void onStart() { // Activity 可见，无法获得焦点
        super.onStart();
        Log.i("activity_life", "onStart()");
    }

    @Override
    protected void onResume() { // Activity 处于运行状态，可获得焦点
        super.onResume();
        Log.i("activity_life", "onResume()");
    }

    @Override
    protected void onPause() { // Activity 仍可见，但无法获得焦点
        super.onPause();
        Log.i("activity_life", "onPause()");
    }

    @Override
    protected void onStop() { // Activity 隐藏进入后台，不可见
        super.onStop();
        Log.i("activity_life", "onStop()");
    }

    @Override
    protected void onRestart() { // Activity 重新启动
        super.onRestart();
        Log.i("activity_life", "onRestart()");
    }

    @Override
    protected void onDestroy() { // Activity 被销毁
        super.onDestroy();
        Log.i("activity_life", "onDestroy()");
    }
}
