package com.example.xinliu.swiperefresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvShow;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = (TextView) findViewById(R.id.tv_show);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvShow.setText("正在刷新");
                new Handler().postDelayed(new Runnable() {
                        @Override
                    public void run() {
                        /**
                         * Handler 必须依附于主线程，才能更新 UI
                         */
                        tvShow.setText("刷新完成");
                        swipeContainer.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }
}
