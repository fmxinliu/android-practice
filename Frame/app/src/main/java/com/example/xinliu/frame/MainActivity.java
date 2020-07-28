package com.example.xinliu.frame;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivFlower;
    private Button btnStart;

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivFlower = (ImageView) findViewById(R.id.iv_flower);
        btnStart = (Button) findViewById(R.id.btn_play);
        btnStart.setOnClickListener(this);

        animationDrawable = (AnimationDrawable) ivFlower.getBackground();
    }

    @Override
    public void onClick(View v) {
        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
            btnStart.setBackgroundResource(android.R.drawable.ic_media_pause);
        } else {
            animationDrawable.stop();
            btnStart.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }
}
