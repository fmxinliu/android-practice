package com.example.xinliu.scratchcard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView ivGv;
    private Bitmap alterBitmap;
    private double nX, nY; // 缩放比例因子

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivGv = (ImageView) findViewById(R.id.iv_gv);

        // 屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 状态栏高度
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        // ActionBar高度
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }

        // 加载资源图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scratch_card);
        alterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        // 获取缩放比例
        nX = bitmap.getWidth() * 1.0 / dm.widthPixels;
        nY = bitmap.getHeight() * 1.0 / (dm.heightPixels - statusBarHeight - actionBarHeight);

        // 绘图准备
        Canvas canvas = new Canvas(alterBitmap); // 画布
        Paint paint = new Paint(); // 画刷
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        Matrix matrix = new Matrix(); // 变换
        canvas.drawBitmap(bitmap, matrix, paint);

        // 触摸绘图
        ivGv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    // 获取屏幕触摸位置
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    // 绘图宽度
                    for (int i = -50; i < 50; i++) {
                        for (int j = -50; j < 50; j++) {
                            if (Math.sqrt(i * i + j * j) <= 50) { // 以触摸点为中心的圆
                                // 触摸区域做局部透明，显示背景
                                int pixelX = (int) (x * nX + i);
                                int pixelY = (int) (y * nY + j);
                                alterBitmap.setPixel(pixelX, pixelY, Color.TRANSPARENT);
                            }
                        }
                    }
                    // 显示绘图
                    ivGv.setImageBitmap(alterBitmap);
                } catch (Exception e) { // 捕获超出屏幕的异常
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
}
