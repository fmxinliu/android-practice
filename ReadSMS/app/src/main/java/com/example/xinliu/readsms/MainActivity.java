package com.example.xinliu.readsms;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int requestCode = 123;
    private TextView tvSms;
    private TextView tvDes;
    private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSms = (TextView) findViewById(R.id.tv_sms);
        tvDes = (TextView) findViewById(R.id.tv_des);
        queryAuthority();
    }

    public void readSMS(View view) {
        // 获取权限
        queryAuthority();
        // 查询系统信息的uri
        Uri uri = Uri.parse("content://sms//");
        // 获取ContentResolver对象
        ContentResolver resolver = this.getContentResolver();
        // 通过ContentResolver对象查询系统短信
        Cursor cursor = resolver.query(uri,
                new String[] {"_id", "address", "type", "body", "date"}, null, null, null);
        List<SmsInfo> smsInfos = new ArrayList<SmsInfo>();
        if (cursor != null && cursor.getCount() > 0) {
            tvDes.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(0);
                String address = cursor.getString(1);
                int type = cursor.getInt(2);
                String body = cursor.getString(3);
                long date = cursor.getLong(4);
                SmsInfo smsInfo = new SmsInfo(_id,address,type,body,date);
                smsInfos.add(smsInfo);
            }

            cursor.close();
        }

        // 将查询到的短信内容显示到界面上
        for(int i = 0; i < smsInfos.size(); i++){
            text += "手机号码：" + smsInfos.get(i).getAddress() + "\n";
            text += "类型：" + smsInfos.get(i).getType() + "\n";
            text += "日期：" + smsInfos.get(i).getDate() + "\n";
            text += "短信内容：" + smsInfos.get(i).getBody() + "\n\n";
        }
        tvSms.setText(text);
    }

    private void queryAuthority() {
        int hasPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission = checkSelfPermission(Manifest.permission.READ_SMS);
        }
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, requestCode);
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
