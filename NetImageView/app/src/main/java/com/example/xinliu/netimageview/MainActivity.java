package com.example.xinliu.netimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    protected static final int CHANGE_UI = 1;
    protected static final int ERROR = 2;

    // 主线程创建消息处理器
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_UI) {
                Bitmap bitmap = (Bitmap) msg.obj;
                ivPic.setImageBitmap(bitmap);
            } else if (msg.what == ERROR) {
                Toast.makeText(MainActivity.this, "图片显示错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private EditText etPath;
    private ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPath = (EditText) findViewById(R.id.et_path);
        ivPic = (ImageView) findViewById(R.id.iv_pic);
    }

    public void onClick(View view) {
        try {
            final String path = etPath.getText().toString().trim();
            if (TextUtils.isEmpty(path)) {
                Toast.makeText(this, "图片路径不能为空", Toast.LENGTH_SHORT).show();
            } else {
                // 1.开启子线程，接收网络数据
                // 2.接收完成后给主线程发送 Message
                // 3.主线程通过 Handler 处理
                new Thread() {
                    private HttpURLConnection conn;
                    private Bitmap bitmap;
                    private Message message;

                    @Override
                    public void run() {
                        message = new Message();
                        try {
                            URL url = new URL(path);
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(3000);
                            int responseCode = conn.getResponseCode();
                            if (responseCode == 200) { // 响应OK
                                InputStream is = conn.getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);
                                message.what = CHANGE_UI;
                                message.obj = bitmap;
                                handler.sendMessage(message);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        bitmap = null;
                        message.what = ERROR;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                }.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
