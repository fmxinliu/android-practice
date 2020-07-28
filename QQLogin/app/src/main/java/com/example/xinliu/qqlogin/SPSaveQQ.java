package com.example.xinliu.qqlogin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过SharedPreferences的方式保存获取data（key/value形式，xml格式，只能保存基本数据类型）
 */

public class SPSaveQQ {
    public static boolean saveUserInfo(Context context, String number, String password) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("number", number);
        editor.putString("password", password);
        editor.commit();
        return true;
    }

    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String number = sp.getString("number", "");
        String password = sp.getString("password", "");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("number", number);
        userInfo.put("password", password);
        return userInfo;
    }
}
