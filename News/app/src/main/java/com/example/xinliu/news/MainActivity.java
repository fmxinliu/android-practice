package com.example.xinliu.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llLoading;
    private ListView lvNews;
    private List<NewsInfo> newsInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fillData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        lvNews = (ListView) findViewById(R.id.lv_news);
        llLoading.setVisibility(View.VISIBLE);
        lvNews.setVisibility(View.INVISIBLE);
    }

    /**
     * 使用AsyncHttpClient访问网络
     */
    private void fillData() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(this.getString(R.string.server_url), // 获取strings.xml中定义的URL
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        try {
                            String json = new String(bytes, "utf-8");
                            newsInfos = JsonParse.getNewsInfo(json);
                            if (newsInfos != null) {
                                llLoading.setVisibility(View.INVISIBLE);
                                lvNews.setVisibility(View.VISIBLE);
                                lvNews.setAdapter(new NewsAdapter(MainActivity.this, newsInfos));
                            } else {
                                Toast.makeText(MainActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
