package com.example.xinliu.videoview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int requestCode = 123;
    private EditText etPath;
    private ImageView btPlay;
    private VideoView videoView;

    // 可视化控制器
    private MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPath = (EditText) findViewById(R.id.et_path);
        btPlay = (ImageView) findViewById(R.id.bt_play);
        videoView = (VideoView) findViewById(R.id.video_view);
        btPlay.setOnClickListener(this);

        controller = new MediaController(this);
        videoView.setMediaController(controller);
        queryAuthority();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                play();
                break;
        }
    }

    // 播放视频
    private void play() {
        if (videoView != null && videoView.isPlaying()) {
            btPlay.setImageResource(android.R.drawable.ic_media_play);
            videoView.stopPlayback();
            return;
        }
        videoView.setVideoPath(etPath.getText().toString().trim());
        videoView.start();
        btPlay.setImageResource(android.R.drawable.ic_media_pause);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btPlay.setImageResource(android.R.drawable.ic_media_play);
            }
        });
    }

    private void queryAuthority() {
        int hasPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
            }
            return;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
