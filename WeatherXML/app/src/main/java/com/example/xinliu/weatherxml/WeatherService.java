package com.example.xinliu.weatherxml;

import android.util.Xml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    /**
     * 1.next方法将读取指针定位到下一个Token，可能遇到一个“开始标签”，也可能是一个“结束标签”，
     * 也可能是标签之间的文本或其他内容，返回一个int值，部分常见返回值意义如下：
     * int START_DOCUMENT = 0;//遇到文档开始
     * int END_DOCUMENT = 1;//遇到文档结束
     * int START_TAG = 2;//遇到开始标签
     * int END_TAG = 3;//遇到结束标签
     * int TEXT = 4;//遇到标签之间的文本
     *
     * 2.nextText方法读取“开始标签”和“结束标签”之间的文本，返回String值，并将指针定位到下一Token位置。
     * 需要在刚读取完“开始标签”后调用，且“开始标签”后有文本。如果下一个Token读到的不是“结束标签”，会抛出异常。
     * 如果“开始标签”和“结束标签”之间没有文本，则返回空串。
     */
    public static List<WeatherInfo> getInfosFromXML(InputStream is)
            throws Exception {
        // 得到pull解析器
        XmlPullParser parser = Xml.newPullParser();
        // 初始化解析器,第一个参数代表包含xml的数据
        parser.setInput(is, "utf-8");
        List<WeatherInfo> weatherInfos = null;
        WeatherInfo weatherInfo = null;
        // 得到当前事件的类型
        int type = parser.getEventType();
        // END_DOCUMENT文档结束标签
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                // 一个节点的开始标签
                case XmlPullParser.START_TAG:
                    if ("infos".equals(parser.getName())) {
                        weatherInfos = new ArrayList<WeatherInfo>();
                    } else if ("city".equals(parser.getName())) {
                        weatherInfo = new WeatherInfo();
                        weatherInfo.setId(parser.getAttributeValue(0));
                    } else if ("temp".equals(parser.getName())) {
                        // 1.现在标签：START_TAG
                        // 2.nextText()向后next()跳到：TEXT，获取内容，再向后next跳到：END_TAG
                        weatherInfo.setTemp(parser.nextText()); // nextText()源码碰到TEXT会next()
                    } else if ("weather".equals(parser.getName())) {
                        weatherInfo.setWeather(parser.nextText());
                    } else if ("name".equals(parser.getName())) {
                        weatherInfo.setName(parser.nextText());
                    } else if ("pm".equals(parser.getName())) {
                        weatherInfo.setPm(parser.nextText());
                    } else if ("wind".equals(parser.getName())) {
                        weatherInfo.setWind(parser.nextText());
                    }
                    break;
                // 一个节点结束的标签
                case XmlPullParser.END_TAG:
                    // 一个城市的信息处理完毕，city的结束标签
                    if ("city".equals(parser.getName())) {
                        weatherInfos.add(weatherInfo);
                    }
                    break;
            }
            type = parser.next();
        }
        return weatherInfos;
    }

    /**
     *  JSON JavaScript对象表示，是基于纯文本的数据格式，有JSON对象、JSON数组 2 种数据格式
     */
    public static List<WeatherInfo> getInfosFromJson(InputStream is)
            throws Exception {
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        String json = new String(buffer, "utf-8");
        List<WeatherInfo> r1 = parseJson1(json);
        List<WeatherInfo> r2 = parseJson2(json);
        List<WeatherInfo> r3 = parseJson3(json);
        return r2;
    }

    // 导入gson.jar
    private static List<WeatherInfo> parseJson1(String json) {
        Gson gson = new Gson();
        Type listType= new TypeToken<List<WeatherInfo>>() {
        }.getType(); // 这里采用了匿名类的方式，把Json数据转换成List<AppVersion>类型
        // 直接按类型简析
        List<WeatherInfo> weatherInfos = gson.fromJson(json, listType);
        return weatherInfos;
    }

    // 导入gson.jar
    private static List<WeatherInfo> parseJson2(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();
        for (int i = 0; i < jsonArray.length(); i++) {
            // 按对象方式简析
            Gson gson = new Gson();
            WeatherInfo weatherInfo = gson.fromJson(jsonArray.getString(i), WeatherInfo.class);
            weatherInfos.add(weatherInfo);
        }
        return weatherInfos;
    }

    // android已集成
    private static List<WeatherInfo> parseJson3(String json) throws JSONException {
        // 1.先简析数组
        JSONArray jsonArray = new JSONArray(json);
        List<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();
        for (int i = 0; i < jsonArray.length(); i++) {
            // 2.依次简析每个对象
            JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
            WeatherInfo weatherInfo = new WeatherInfo();
            weatherInfo.setId(jsonObject.optString("id"));
            weatherInfo.setTemp(jsonObject.optString("temp"));
            weatherInfo.setWeather(jsonObject.optString("weather"));
            weatherInfo.setName(jsonObject.optString("name"));
            weatherInfo.setPm(jsonObject.optString("pm"));
            weatherInfo.setWind(jsonObject.optString("wind"));
            weatherInfos.add(weatherInfo);
        }
        return weatherInfos;
    }
}
