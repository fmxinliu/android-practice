package com.example.xinliu.appmarket;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * ListView界面与数据之间的桥梁，将item模板和data组装到一起。
 * 当列表里的每一项显示到页面时，都会调用adapter的getView方法返回一个View，用来将不同的数据映射到View上。
 */

public class MyAdapter extends BaseAdapter {
    private List<App> list;
    private Context context;

    public MyAdapter(Context context, List<App> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) { // 第一次创建
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.item_icon);
            convertView.setTag(viewHolder);
        } else { // 非第一次，重用之前的View
            viewHolder = (ViewHolder) convertView.getTag();
        }
        App app = list.get(position);
        viewHolder.itemName.setText(app.getName());
        viewHolder.itemIcon.setBackgroundResource(app.getIcon());
        return convertView;
    }

    private class ViewHolder {
        TextView itemName;
        ImageView itemIcon;
    }

    // 每添加一个item都需要重新获取组件
    private View get(int position) {
        View view = View.inflate(context, R.layout.list_item, null); // 将布局填充成View对象
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        ImageView itemIcon = (ImageView) view.findViewById(R.id.item_icon);
        App app = list.get(position);
        itemName.setText(app.getName());
        itemIcon.setBackgroundResource(app.getIcon());
        return view;
    }
}
