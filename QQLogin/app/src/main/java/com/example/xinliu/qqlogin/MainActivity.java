package com.example.xinliu.qqlogin;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends Activity {
    private EditText edNumber;
    private EditText edPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNumber = (EditText) findViewById(R.id.ed_number);
        edPassword = (EditText) findViewById(R.id.ed_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = edNumber.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(MainActivity.this, "请输入QQ号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "请输入QQ密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 保存信息
                boolean isSaveSuccess = FileSaveQQ.saveUserInfo(MainActivity.this, number, password);
                if (isSaveSuccess) {
                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }

                isSaveSuccess = FileSaveQQ.saveUserInfoToSD(number, password);
                if (isSaveSuccess) {
                    Toast.makeText(MainActivity.this, "保存到SD成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "保存到SD失败", Toast.LENGTH_SHORT).show();
                }

                isSaveSuccess = SPSaveQQ.saveUserInfo(MainActivity.this, number, password);
                if (isSaveSuccess) {
                    Toast.makeText(MainActivity.this, "数据共享方式保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "数据共享方式保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 加载保存的信息
        Map<String, String> userInfo = FileSaveQQ.getUserInfo(this);
        if (!userInfo.isEmpty()) {
            edNumber.setText(userInfo.get("number"));
            edPassword.setText(userInfo.get("password"));
        }

        Map<String, String> userInfoSD = FileSaveQQ.getUserInfoFromSD();
        if (!userInfoSD.isEmpty()) {
            Log.i("###", userInfoSD.get("number"));
            Log.i("###", userInfoSD.get("password"));
        }

        userInfo = SPSaveQQ.getUserInfo(this);
        if (!userInfo.isEmpty()) {
            Log.i("###sp", userInfoSD.get("number"));
            Log.i("###sp", userInfoSD.get("password"));
        }
    }
}
