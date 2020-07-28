package com.example.xinliu.musicplayer;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int requestCode = 123;
    private EditText etPath;

    private MusicService.MyBinder myBinder;
    private MyConn myConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPath = (EditText) findViewById(R.id.et_path);
        findViewById(R.id.tv_play).setOnClickListener(this);
        findViewById(R.id.tv_pause).setOnClickListener(this);

        this.queryAuthority();
    }

    @Override
    public void onClick(View v) {
        if (myBinder == null) {
            this.queryAuthority();
//            try { this.wait(200); } catch (InterruptedException e) {}
            if (myBinder == null) {
                Toast.makeText(MainActivity.this, "服务未启动，请重新开始", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // 2.使用服务
        String path = etPath.getText().toString().trim();
        File sdPath = Environment.getExternalStorageDirectory();
        File file = new File(sdPath, path);

        switch (v.getId()) {
            case R.id.tv_play:
                if (file.exists()) {
                    myBinder.play(file.getAbsolutePath());
                } else {
                    Toast.makeText(MainActivity.this, "找不到音频文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_pause:
                myBinder.pause();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除绑定
        if (myConn != null) {
            unbindService(myConn);
            myConn = null;
            myBinder = null;
        }
    }

    private class MyConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MusicService.MyBinder) service;
            if (myBinder != null) {
                Log.i("musicService", "服务启动成功");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private void queryAuthority() {
        int hasReadContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasReadContactsPermission = checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (hasReadContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},
                                MainActivity.requestCode);
            }
            return;
        }

        // 1.绑定服务
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("info", "启动服务");
        myConn = new MyConn();
        bindService(intent, myConn, BIND_AUTO_CREATE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MainActivity.requestCode:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    queryAuthority();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}
