package com.example.xinliu.getotherpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button write, read;
    private Context useCount = null;
    private String TAG = "getPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // 获取其他程序所对应的Context
            useCount = createPackageContext("com.example.xinliu.clickcount",
                    Context.CONTEXT_IGNORE_SECURITY);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        write = (Button) this.findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用其他程序的Context获取对应的SharePreferences
                SharedPreferences prefs = useCount.getSharedPreferences("count",
                        Context.MODE_WORLD_WRITEABLE);
                // 读取数据
                int count = prefs.getInt("count", 0);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("count", ++count);
                editor.commit();

                TextView show = (TextView) findViewById(R.id.textView);
                show.setText("ClickCount应用程序点击了" + count + "次");
            }
        });

        read = (Button) this.findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用其他程序的Context获取对应的SharePreferences
                SharedPreferences prefs = useCount.getSharedPreferences("count",
                        Context.MODE_WORLD_WRITEABLE);
                // 读取数据
                int count = prefs.getInt("count", 0);

                TextView show = (TextView) findViewById(R.id.textView);
                show.setText("ClickCount应用程序点击了" + count + "次");
            }
        });
    }
}
