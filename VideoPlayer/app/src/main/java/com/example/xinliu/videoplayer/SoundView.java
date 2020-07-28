package com.example.xinliu.videoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SoundView extends View {

    public final static String TAG = "SoundView";

    private Context mContext;
    private Bitmap bm, bm1;
    private int bitmapWidth, bitmapHeight;
    private int index;
    private OnVolumeChangedListener mOnVolumeChangedListener; // 接口的引用

    public final static int HEIGHT = 11;
    public final static int MY_HEIGHT = 163;
    public final static int MY_WIDTH = 44;

    public interface OnVolumeChangedListener { // 接口,不能被实例化。但包含接口的类可以被实例化
        public void setYourVolume(int index);
    }

    public void setOnVolumeChangedListener(OnVolumeChangedListener l) {
        mOnVolumeChangedListener = l;
    }

    public SoundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public SoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public SoundView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        bm = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.sound_line);
        bm1 = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.sound_line1);
        bitmapWidth = bm.getWidth();
        bitmapHeight = bm.getHeight();
        setIndex(5);
        AudioManager am = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        setIndex(am.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int n = y * 15 / MY_HEIGHT;
        setIndex(15 - n);
        Log.d(TAG, "setIndex: " + (15 - n));

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) { // 绘制音量显示
        int reversetIndex = 15 - index;
        for (int i = 0; i != reversetIndex; ++i) {
            canvas.drawBitmap(bm1, new Rect(0, 0, bitmapWidth, bitmapHeight),
                    new Rect(0, i * HEIGHT, bitmapWidth, i * HEIGHT
                             + bitmapHeight), null);
        }
        for (int i = reversetIndex; i != 15; ++i) {
            canvas.drawBitmap(bm, new Rect(0, 0, bitmapWidth, bitmapHeight),
                    new Rect(0, i * HEIGHT, bitmapWidth, i * HEIGHT
                            + bitmapHeight), null);
        }

        super.onDraw(canvas);
    }

    private void setIndex(int n) {
        if (n > 15) {
            n = 15;
        } else if (n < 0) {
            n = 0;
        }
        if (index != n) {
            index = n;
            if (mOnVolumeChangedListener != null) {
                mOnVolumeChangedListener.setYourVolume(n);
            }
            invalidate();
        }
    }
}
