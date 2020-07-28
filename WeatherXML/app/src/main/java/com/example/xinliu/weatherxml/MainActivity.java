package com.example.xinliu.weatherxml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinliu on 2019/1/11.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCity;
    private TextView tvWeather;
    private TextView tvTemp;
    private TextView tvWind;
    private TextView tvPm;
    private ImageView ivIcon;

    private Map<String, WeatherInfo> weatherInfoMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadWeatherXml();
        getMap("bj");
    }

    private void init() {
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        tvTemp = (TextView) findViewById(R.id.tv_temp);
        tvWind = (TextView) findViewById(R.id.tv_wind);
        tvPm = (TextView) findViewById(R.id.tv_pm);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        findViewById(R.id.btn_bj).setOnClickListener(this);
        findViewById(R.id.btn_sh).setOnClickListener(this);
        findViewById(R.id.btn_gz).setOnClickListener(this);
    }

    private void loadWeatherXml() {
        try {
            InputStream is2 = getResources().openRawResource(R.raw.weather2);
            WeatherService.getInfosFromJson(is2);
            InputStream is = getResources().openRawResource(R.raw.weather);
            List<WeatherInfo> infos = WeatherService.getInfosFromXML(is);
            weatherInfoMap = new HashMap<String, WeatherInfo>();
            for (WeatherInfo info : infos) {
                weatherInfoMap.put(info.getId(), info);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bj:
                getMap("bj");
                break;
            case R.id.btn_sh:
                getMap("sh");
                break;
            case R.id.btn_gz:
                getMap("gz");
                break;
        }
    }

    // 将城市天气信息分条展示到界面上
    private void getMap(String id) {
        WeatherInfo weatherInfo = weatherInfoMap.get(id);

        tvCity.setText(weatherInfo.getName());
        tvWeather.setText(weatherInfo.getWeather());
        tvTemp.setText("" + weatherInfo.getTemp());
        tvWind.setText("风力  : " + weatherInfo.getWind());
        tvPm.setText("pm: " + weatherInfo.getPm());

        switch (weatherInfo.getWeather()) {
            case "晴天":
                ivIcon.setImageResource(R.drawable.sun);
                break;
            case "多云":
                ivIcon.setImageResource(R.drawable.clouds);
                break;
            case "晴天多云":
                ivIcon.setImageResource(R.drawable.cloud_sun);
                break;
        }
    }
}
