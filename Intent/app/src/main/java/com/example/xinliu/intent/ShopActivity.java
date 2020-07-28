package com.example.xinliu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xinliu.equipment.ItemInfo;

import java.util.ArrayList;

import static com.example.xinliu.equipment.ConstantInfo.*;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivImage;
    private TextView tvName;
    private TextView tvLife;
    private TextView tvAttack;
    private TextView tvSpeed;

    private LinearLayout lLayout; // 内层第一个武器的布局
    private LinearLayout linearLayout; // 最外层武器列表的布局
    private ArrayList<ItemInfo> itemInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        init();
    }

    // 初始化
    private void init(){
        // 创建装备
        itemInfoList = new ArrayList<>();
        itemInfoList.add(new ItemInfo("金剑", 100, 20, 20));
        itemInfoList.add(new ItemInfo("银剑", 50, 8, 8));
        itemInfoList.add(new ItemInfo("铁剑", 25, 3, 5));
        itemInfoList.add(new ItemInfo("木棍", 12, 1, 1));

        // 1.获取xml上设置的第一个装备
        ItemInfo itemInfo = itemInfoList.get(0);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvLife = (TextView) findViewById(R.id.tv_life);
        tvAttack = (TextView) findViewById(R.id.tv_attack);
        tvSpeed = (TextView) findViewById(R.id.tv_speed);
        ivImage = (ImageView) findViewById(R.id.iv_image);

        // 2.单独将第一个装备显示出来
        tvName.setText(itemInfo.getName());
        tvLife.setText("生命值：" + itemInfo.getLife());
        tvAttack.setText("攻击力：" + itemInfo.getAttack());
        tvSpeed.setText("敏捷度：" + itemInfo.getSpeed());

        // 2.动态添加其余装备
        lLayout = (LinearLayout) findViewById(R.id.ll);
        linearLayout = (LinearLayout) findViewById(R.id.rl);
        for (int i = 1; i < itemInfoList.size(); ++i) {
            addItem(i, itemInfoList.get(i));
        }

        // 3.设置第一个装备的字体大小（sp与px转换）
        tvName.setTextSize(tvName.getTextSize());
        tvLife.setTextSize(tvLife.getTextSize());
        tvAttack.setTextSize(tvAttack.getTextSize());
        tvSpeed.setTextSize(tvSpeed.getTextSize());
        ivImage.setMaxWidth(ivImage.getMaxWidth());
        ivImage.setMaxHeight(ivImage.getMaxHeight());

        lLayout.setOnClickListener(this);
    }

    // 动态添加装备到界面
    private void addItem(int id, ItemInfo itemInfo) {
        LinearLayout lLayoutItem = new LinearLayout(this);
        lLayoutItem.setId(lLayout.getId() + lLayoutIdMask + id);
        lLayoutItem.setLayoutParams(lLayout.getLayoutParams());
        lLayoutItem.setOrientation(lLayout.getOrientation());
        lLayoutItem.setBackground(lLayout.getBackground());
        lLayoutItem.setGravity(lLayout.getGravity());
        lLayoutItem.setPadding(
                lLayout.getPaddingLeft(),
                lLayout.getPaddingTop(),
                lLayout.getPaddingRight(),
                lLayout.getPaddingBottom());

        // 1.Image
        ImageView ivItemImage = new ImageView(this);
        ivItemImage.setMaxWidth(ivImage.getMaxWidth());
        ivItemImage.setMaxHeight(ivImage.getMaxHeight());
        ivItemImage.setBackground(ivImage.getBackground());

        // 2.Name
        TextView tvItemName = new TextView(this);
        tvItemName.setId(tvName.getId() + (lLayoutIdMask + 1) + id);
        tvItemName.setLayoutParams(tvName.getLayoutParams());
        tvItemName.setTextSize(tvName.getTextSize()); // 单位 px
        tvItemName.setText(itemInfo.getName());

        // 3.Detail
        LinearLayout lLayoutDetail = (LinearLayout) findViewById(R.id.ll_first_detail);
        LinearLayout lLayoutItemDetail = new LinearLayout(this);
        lLayoutItemDetail.setLayoutParams(lLayoutDetail.getLayoutParams());
        lLayoutItemDetail.setOrientation(lLayoutDetail.getOrientation());

        // Life
        TextView tvItemLife = new TextView(this);
        tvItemLife.setId(tvName.getId() + (lLayoutIdMask + 2) + id);
        tvItemLife.setLayoutParams(tvLife.getLayoutParams());
        tvItemLife.setTextSize(tvLife.getTextSize());
        tvItemLife.setText("生命值：" + itemInfo.getLife());

        // Attack
        TextView tvItemAttack = new TextView(this);
        tvItemAttack.setId(tvAttack.getId() + (lLayoutIdMask + 3) + id);
        tvItemAttack.setLayoutParams(tvAttack.getLayoutParams());
        tvItemAttack.setTextSize(tvAttack.getTextSize());
        tvItemAttack.setText("攻击力：" + itemInfo.getAttack());

        // Speed
        TextView tvItemSpeed = new TextView(this);
        tvItemSpeed.setId(tvSpeed.getId() + (lLayoutIdMask + 4) + id);
        tvItemSpeed.setLayoutParams(tvSpeed.getLayoutParams());
        tvItemSpeed.setTextSize(tvSpeed.getTextSize());
        tvItemSpeed.setText("敏捷度：" + itemInfo.getSpeed());

        lLayoutItemDetail.addView(tvItemLife);
        lLayoutItemDetail.addView(tvItemAttack);
        lLayoutItemDetail.addView(tvItemSpeed);

        lLayoutItem.addView(ivItemImage);
        lLayoutItem.addView(tvItemName);
        lLayoutItem.addView(lLayoutItemDetail);

        linearLayout.addView(lLayoutItem); // 添加事件监听
        lLayoutItem.setOnClickListener(this);
    }

    // 将装备数据返回到BabyActivity页面
    @Override
    public void onClick(View v) {
        int id = v.getId();
        final int firstItemId = lLayout.getId();
        int idx;
        if (firstItemId == id) {
            idx = 0;
        } else {
            idx = id - firstItemId - lLayoutIdMask;
        }

        if (idx >=0 && idx < itemInfoList.size()) {
            Intent requestIntent = getIntent();
            Intent resultIntent = new Intent();
            int money = requestIntent.getIntExtra("money", 0); // 获取金额
            if (money > 0) {
                // 返回新建的 Intent
                requestIntent.putExtra("equipment", itemInfoList.get(idx));
                setResult(resultCodeSuccess, requestIntent);
            } else {
                // 返回传入的 Intent
                resultIntent.putExtra("failInfo", "钱不够，请充值");
                setResult(resultCodeFail, resultIntent);
            }
            finish(); // 关闭 Activity 并返回
        }
    }
}
