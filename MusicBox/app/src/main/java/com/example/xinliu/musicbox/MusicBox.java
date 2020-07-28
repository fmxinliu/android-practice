package com.example.xinliu.musicbox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MusicBox extends Activity {
    private TextView title, author;
    private ImageButton play, stop;
    ActivityReceiver actReceiver;

    private int status = MusicBoxConstant.IDLE;
//    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicListener musuicListener = new MusicListener();

        play = this.findViewById(R.id.play);
        stop = this.findViewById(R.id.stop);
        title = this.findViewById(R.id.title);
        author = this.findViewById(R.id.author);

        play.setOnClickListener(musuicListener);
        stop.setOnClickListener(musuicListener);
        actReceiver = new ActivityReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicBoxConstant.ACTION_UPDATE);
        this.registerReceiver(actReceiver, filter);

        //Intent actionIntent = new Intent(this, MusicService.class);
        Intent actionIntent = new Intent();
        actionIntent.setAction(MusicBoxConstant.MUSIC_SERVICE);
        actionIntent.setPackage("com.example.xinliu.musicbox"); // Android5.0后service不能采用隐式启动，故此处加上包名
        this.startService(actionIntent);
    }

    /**
     * 自定义的BroadcastReceiver, 负责监听从Service传回来的广播(里面不能执行耗时操作, 可以由Service在后台执行)
     */
    public class ActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MusicBoxConstant.ACTION_UPDATE)) { // 只监听ACTION_UPDATE广播
                // 获取intent中的update信息, update代表播放状态
                int update = intent.getIntExtra("update", -1);
                // 获取intent中的current消息, current代表当前正在播放的歌曲
                int current = intent.getIntExtra("current", -1);

                if (update != MusicBoxConstant.IDLE && current >= 0) {
                    System.out.println("Title:" + MusicBoxConstant.titles[current]);
                    title.setText(MusicBoxConstant.titles[current]);
                    author.setText(MusicBoxConstant.authors[current]);
                }
                switch (update) {
                    case MusicBoxConstant.IDLE:
                        play.setImageResource(R.mipmap.play);
                        status = MusicBoxConstant.IDLE;
//                        stopService(serviceIntent);
                        break;
                    case MusicBoxConstant.PLAYING:
                        // 设置播放状态下的暂停图标
                        play.setImageResource(R.mipmap.pause);
                        status = MusicBoxConstant.PLAYING;
                        break;
                    case MusicBoxConstant.PAUSING:
                        // 暂停状态下设置使用播放图标
                        play.setImageResource(R.mipmap.play);
                        status = MusicBoxConstant.PAUSING;
                        break;
                }
            }
        }
    }

    /**
     * 音乐播放器按钮事件监听器
     */
    class MusicListener implements OnClickListener {
        @Override
        public void onClick(View source) {
            // 创建Intent
            Intent intent = new Intent(MusicBoxConstant.ACTION_CTL);
            switch (source.getId()) {
                case R.id.play:
                    intent.putExtra("control", 1);
                    break;
                case R.id.stop:
                    intent.putExtra("control", 2);
                    break;
            }
            // 发送广播, 将被Service组件中的BroadcastReceiver接收到
            System.out.println("OnClick and send Broadcast...");
            MusicBox.this.sendBroadcast(intent);
        }
    }
}
