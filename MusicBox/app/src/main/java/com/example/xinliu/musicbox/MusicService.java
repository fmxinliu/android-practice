package com.example.xinliu.musicbox;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

import java.io.IOException;

/**
 * 定义播放音乐的服务
 */
public class MusicService extends Service {
    private MyReceiver serviceReceiver;
    private AssetManager assetManager;
    private String[] musics = new String[] {"for-love.mp3", "inmysong.mp3",
            "living-there.mp3", "when-I-thinking.mp3" };
    private MediaPlayer mediaPlayer;

    private int status = MusicBoxConstant.IDLE;
    private int current = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public  void onCreate() {
        assetManager = this.getAssets();
        serviceReceiver = new MyReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicBoxConstant.ACTION_CTL);
        this.registerReceiver(serviceReceiver, filter);

        mediaPlayer = new MediaPlayer();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Music Serivce is started!");
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current > 3) {
                    current = 0;
                }

                Intent sendIntent = new Intent(MusicBoxConstant.ACTION_CTL);
                sendIntent.putExtra("current", current);
                MusicService.this.sendBroadcast(sendIntent);
                prepareAndPlay(musics[current]);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("Music Service is destroyed!");
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MusicBoxConstant.ACTION_CTL)) { // 只监听MusicBoxConstant广播
                int control = intent.getIntExtra("control", -1);
                System.out.println("onReceive:" + control);
                switch (control) {
                    case MusicBoxConstant.PLAY_OR_PAUSE:
                        if (status == MusicBoxConstant.IDLE) {
                            // 开始播放
                            prepareAndPlay(musics[current]);
                            status = MusicBoxConstant.PLAYING;
                        } else if (status == MusicBoxConstant.PLAYING) {
                            // 暂停
                            mediaPlayer.pause();
                            status= MusicBoxConstant.PAUSING;
                        } else if (status == MusicBoxConstant.PAUSING) {
                            // 继续播放
                            mediaPlayer.start();
                            status = MusicBoxConstant.PLAYING;
                        }
                        break;
                    case MusicBoxConstant.STOP:
                        if (status == MusicBoxConstant.PLAYING
                                || status == MusicBoxConstant.PAUSING) {
                            // 停止
                            mediaPlayer.stop();
                            status = MusicBoxConstant.IDLE;
                        }
                        break;
                }

                Intent sendIntent = new Intent(MusicBoxConstant.ACTION_UPDATE);
                sendIntent.putExtra("update", status);
                sendIntent.putExtra("current", current);
                MusicService.this.sendBroadcast(sendIntent);
            }
        }
    }

    private void prepareAndPlay(String music) {
        try {
            AssetFileDescriptor afd = assetManager.openFd(music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
