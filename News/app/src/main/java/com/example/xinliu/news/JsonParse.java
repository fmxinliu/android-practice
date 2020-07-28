package com.example.xinliu.news;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParse {
    /**
     * 简析网络传回的 JSON 文件
     */
    public static List<NewsInfo> getNewsInfo(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<NewsInfo>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
