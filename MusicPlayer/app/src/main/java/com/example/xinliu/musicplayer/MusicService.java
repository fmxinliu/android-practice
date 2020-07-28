package com.example.xinliu.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("musicService", intent.getStringExtra("info"));
        return new MyBinder();
    }

    class MyBinder extends Binder {
        // 播放音乐
        public void play(String path) {
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                } else if (!mediaPlayer.isPlaying()){
                    int position = getCurrentPosition();
                    mediaPlayer.seekTo(position);
//                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
            } catch (Exception e) {
                mediaPlayer = null;
                e.printStackTrace();
            }
        }

        // 暂停播放
        public void pause() {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        }
    }

    // 获取当前进度
    public int getCurrentPosition() {
        int position = 0;
        if (mediaPlayer != null) {
            position = mediaPlayer.getCurrentPosition();
            if (position >= mediaPlayer.getDuration()) {
                position = 0;
            }
        }
        return position;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
