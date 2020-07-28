package com.example.xinliu.tween;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAlpha;  // 透明
    private Button btnRotate; // 旋转
    private Button btnScale;  // 缩放
    private Button btnTranslate; // 平移

    private ImageView ivBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlpha = (Button) findViewById(R.id.btn_alpha);
        btnRotate = (Button) findViewById(R.id.btn_rotate);
        btnScale = (Button) findViewById(R.id.btn_scale);
        btnTranslate = (Button) findViewById(R.id.btn_translate);
        ivBean = (ImageView) findViewById(R.id.iv_bean);

        btnAlpha.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alpha:
                Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
                ivBean.startAnimation(alpha);
                break;
            case R.id.btn_rotate:
                Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
                ivBean.startAnimation(rotate);
                break;
            case R.id.btn_scale:
                Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
                ivBean.startAnimation(scale);
                break;
            case R.id.btn_translate:
                Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
                ivBean.startAnimation(translate);
                break;
        }
    }
}
