package com.example.xinliu.qqlogin;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过读写文件的方式保存获取data（adb.exe root打开ADM查看：data > data > xxx > file > data.txt）
 */

public class FileSaveQQ {
    // 保存用户信息（保存到机内，需要Context上下文）
    public static boolean saveUserInfo(Context context, String number, String password) {
        try {
            FileOutputStream fos = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            String content = number + ":" + password;
            fos.write(content.getBytes());
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取用户信息
    public static Map<String, String> getUserInfo(Context context) {
        Map<String, String> userInfo = new HashMap<>();
        try {
            FileInputStream fis = context.openFileInput("data.txt");
            byte[] data = new byte[fis.available()];
            fis.read(data);
            String content = new String(data);
            String[] info = content.split(":");
            if (info.length >= 2) {
                userInfo.put("number", info[0]);
                userInfo.put("password", info[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // 保存用户信息到SD
    public static boolean saveUserInfoToSD(String number, String password) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File SDPath = Environment.getExternalStorageDirectory();
            File file = new File(SDPath, "data.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                String content = number + ":" + password;
                fos.write(content.getBytes());
                fos.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 获取用户信息从SD
    public static Map<String, String> getUserInfoFromSD() {
        Map<String, String> userInfo = new HashMap<>();
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File SDPath = Environment.getExternalStorageDirectory();
            File file = new File(SDPath, "data.txt");
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String content = br.readLine();
                String[] info = content.split(":");
                if (info.length >= 2) {
                    userInfo.put("number", info[0]);
                    userInfo.put("password", info[1]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }
}
