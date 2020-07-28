package com.example.xinliu.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xinliu.equipment.ConstantInfo;
import com.example.xinliu.equipment.ItemInfo;

public class ShowActivity extends AppCompatActivity {
    private TextView tvUsername;
    private TextView tvPassword;

    private ProgressBar pgbLife;
    private ProgressBar pgbAttack;
    private ProgressBar pgbSpeed;

    private TextView tvLifeProgress;
    private TextView tvAttackProgress;
    private TextView tvSpeedProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        registInit();
        buyInit();
    }

    private void registInit() {
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvPassword = (TextView) findViewById(R.id.tv_password);

        Intent intent = getIntent();
        tvUsername.setText("用户名：" + intent.getStringExtra("username"));
        tvPassword.setText("密    码：" + intent.getStringExtra("password"));
    }

    private void buyInit() {
        pgbLife = (ProgressBar) findViewById(R.id.pgb_life);
        pgbAttack = (ProgressBar) findViewById(R.id.pgb_attack);
        pgbSpeed = (ProgressBar) findViewById(R.id.pgb_speed);

        tvLifeProgress = (TextView) findViewById(R.id.tv_life_progress);
        tvAttackProgress = (TextView) findViewById(R.id.tv_attack_progress);
        tvSpeedProgress = (TextView) findViewById(R.id.tv_speed_progress);

        // 设置进度条最大值
        pgbLife.setMax(1000);
        pgbAttack.setMax(1000);
        pgbSpeed.setMax(1000);
    }

    // 点击购买按钮跳转到ShopActivity页面
    public void click(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("money", 666);
        startActivityForResult(intent, ConstantInfo.requestCode);
    }

    // 处理返回的装备数据
    @Override
    /**
     * @param requestCode 指示那个 Activity 获取返回值
     * @param resultCode 指示 Activity 的返回值
     * @param data  Activity 的返回数据
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ConstantInfo.requestCode == requestCode && data != null) {
            if (ConstantInfo.resultCodeSuccess == resultCode) {
                updateProgress((ItemInfo) data.getSerializableExtra("equipment"));
            } else {
                Log.i("FAIL", data.getStringExtra("failInfo"));
            }
        }
    }

    // 更新ProgressBar的值
    private void updateProgress(ItemInfo info){
        int currenLife = pgbLife.getProgress();
        int currentAttack = pgbAttack.getProgress();
        int currentSpeed = pgbSpeed.getProgress();

        pgbLife.setProgress(currenLife + info.getLife());
        pgbAttack.setProgress(currentAttack + info.getAttack());
        pgbSpeed.setProgress(currentSpeed + info.getSpeed());

        tvLifeProgress.setText(pgbLife.getProgress() + "");
        tvAttackProgress.setText(pgbAttack.getProgress() + "");
        tvSpeedProgress.setText(pgbSpeed.getProgress() + "");
    }
}
