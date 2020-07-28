package com.example.xinliu.monitordata;

import android.database.ContentObservable;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.example.xinliu.contentobserver/info");
        getContentResolver().registerContentObserver(uri, true, new MyObserver(new Handler()));
    }

    private class MyObserver extends ContentObserver {
        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.i("监测数据变化","有人动了你的数据");
            super.onChange(selfChange);
        }
    }
}
