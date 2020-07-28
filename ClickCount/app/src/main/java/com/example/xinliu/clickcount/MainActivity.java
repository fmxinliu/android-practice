package com.example.xinliu.clickcount;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("count", MODE_WORLD_READABLE);

        btn = (Button) this.findViewById(R.id.clickBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 读取SharedPreferences里的count数据(xml里的key-value)
                int count = preferences.getInt("count", 1);
                // 显示程序以前使用的次数
                Toast.makeText(MainActivity.this,
                        "您已经点击了" + count + "次.", 10000)
                        .show();
                SharedPreferences.Editor editor = preferences.edit();
                // 存入数据
                editor.putInt("count", ++count);
                // 提交修改
                editor.commit();
            }
        });
    }
}
