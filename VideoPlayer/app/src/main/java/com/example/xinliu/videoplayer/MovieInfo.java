package com.example.xinliu.videoplayer;

/**
 * 存放电影信息。
 *
 * @author chenjunfeng
 *
 */
public class MovieInfo {
    private String displayName;
    private String path;

    public MovieInfo() {

    }

    public MovieInfo(String displayName, String path) {
        this.displayName = displayName;
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String psth) {
        this.path = path;
    }
}
