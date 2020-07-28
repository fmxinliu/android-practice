package com.example.xinliu.news;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<NewsInfo> newsInfos;
    private Context context;

    public NewsAdapter(Context context, List<NewsInfo> newsInfos) {
        this.context = context;
        this.newsInfos = newsInfos;
    }

    @Override
    public int getCount() {
        return newsInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return newsInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1.获取 item 界面组件
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.sivPic = (SmartImageView) convertView.findViewById(R.id.siv_icon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tv_description);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 2.组件填充数据
        NewsInfo newsInfo = newsInfos.get(position);
        viewHolder.sivPic.setImageUrl(newsInfo.getIcon(), // 加载成功的图标
                R.mipmap.ic_launcher,  // 加载中的图标
                R.mipmap.ic_launcher); // 加载失败的图标
        viewHolder.tvTitle.setText(newsInfo.getTitle());
        viewHolder.tvDescription.setText(newsInfo.getContent());
        int type = newsInfo.getType();
        switch (type){
            case 1:
                viewHolder.tvType.setText("评论：" + newsInfo.getComment());
                break;
            case 2:
                viewHolder.tvType.setTextColor(Color.RED);
                viewHolder.tvType.setText("专题");
                break;
            case 3:
                viewHolder.tvType.setTextColor(Color.BLUE);
                viewHolder.tvType.setText("LIVE");
                break;
        }
        return convertView;
    }

    // 用于缓存组件，只加载一次
    private class ViewHolder {
        SmartImageView sivPic;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvType;
    }
}
