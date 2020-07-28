package com.example.xinliu.appmarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvApps;
    private List<App> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // 创建Adapter
        MyAdapter myAdapter = new MyAdapter(this, list);
        // 将Adapter绑定到ListView
        lvApps.setAdapter(myAdapter);
    }

    private void init() {
        lvApps = (ListView) findViewById(R.id.lv_apps);

        // 初始化数据
        list = new ArrayList<App>();
        list.add(new App("京东", R.drawable.jd));
        list.add(new App("QQ", R.drawable.qq));
        list.add(new App("QQ斗地主", R.drawable.dz));
        list.add(new App("新浪微博", R.drawable.xl));
        list.add(new App("天猫", R.drawable.tm));
        list.add(new App("UC浏览器", R.drawable.uc));
        list.add(new App("微信", R.drawable.wx));
    }
}
