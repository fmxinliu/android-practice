package com.example.xinliu.appmarket;

/**
 * item模板对应的data
 */

public class App {
    private String name;
    private int icon;

    public App(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
