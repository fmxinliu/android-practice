package com.example.xinliu.musicbox;

public class MusicBoxConstant {
    // music box status flags:
    public static final int IDLE = 0x01; // 没有播放
    public static final int PLAYING = 0x02; // 正在播放
    public static final int PAUSING = 0x03; // 暂停

    public static final String[] titles = new String[]{
        "因为爱情",
        "在我的歌声里",
        "存在",
        "当我想起你的时候"
    };

    public static final String[] authors = new String[]{
        "王菲,陈奕迅",
        "曲婉婷",
        "汪峰",
        "汪峰1"
    };

    public static final String ACTION_CTL = "com.example.xinliu.action.ACTION_CTL";
    public static final String ACTION_UPDATE = "com.example.xinliu.action.ACTION_UPDATE";

    public static final String TOKEN_CONTROL = "control";
    public static final String TOKEN_UPDATE = "update";
    public static final String TOKEN_CURRENT = "current";

    public static final int STOP = 2;
    public static final int PLAY_OR_PAUSE = 1;

    public static final String MUSIC_SERVICE = "com.example.xinliu.MUSIC_SERVICE";
}
