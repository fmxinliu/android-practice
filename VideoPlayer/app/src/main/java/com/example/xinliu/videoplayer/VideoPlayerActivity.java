package com.example.xinliu.videoplayer;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


import com.example.xinliu.videoplayer.SoundView.OnVolumeChangedListener;
import com.example.xinliu.videoplayer.VideoView.MySizeChangeLinstener;

/**
 * 视频播放器主界面。
 * @author chenjunfeng
 *
 */
public class VideoPlayerActivity extends Activity {
    private final static String TAG = "VideoPlayerActivity";
    private boolean isOnline = false;
    private boolean isChangedVideo = false;
    public static LinkedList<MovieInfo> playList = new LinkedList<MovieInfo>();

    private Uri videoListUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private static int position;
    private static boolean backFromAD = false;
    private int playedTime;

    private VideoView vv = null;
    private SeekBar seekBar = null;
    private TextView durationTextView = null;
    private TextView playedTextView = null;
    private GestureDetector mGestureDetector = null;
    private AudioManager mAudioManager = null;

    private int maxVolume = 0;
    private int currentVolume = 0;

    private ImageButton selectVideoBtn = null;
    private ImageButton backBtn = null;
    private ImageButton playBtn = null;
    private ImageButton forwardBtn = null;
    private ImageButton volumeBtn = null;

    private View controlView = null;
    private PopupWindow controler = null;

    private SoundView mSoundView = null;
    private PopupWindow mSoundWindow = null;

    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static int controlHeight = 0;

    private final static int TIME = 6868;

    private boolean isControllerShow = true;
    private boolean isPaused = false;
    private boolean isFullScreen = false;
    private boolean isSilent = false;
    private boolean isSoundShow = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.d("OnCreate", getIntent().toString());

        this.initIdleHandler();
        this.initVideoController();
        this.initVideoView();
        this.initVolumeView();
        this.initPlayingStatus();

        this.initUrlVideo();
        this.initVideoFileList();

        getScreenSize();
        initGestureDector();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            vv.stopPlayback();

            int result = data.getIntExtra("CHOOSE", -1);
            Log.d("RESULT", "" + result);
            if (result != -1) {
                isOnline = false;
                isChangedVideo = true;
                vv.setVideoPath(playList.get(result).getPath());
                position = result;
            } else {
                String url = data.getStringExtra("CHOOSE_URL");
                if (url != null) {
                    vv.setVideoPath(url);
                    isOnline = true;
                    isChangedVideo = true;
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final static int PROGRESS_CHANGED = 0;
    private final static int HIDE_CONTROLER = 1;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_CHANGED:
                    int i = vv.getCurrentPosition();
                    seekBar.setProgress(i);

                    if (isOnline) {
                        int j = vv.getBufferPercentage();
                        seekBar.setSecondaryProgress(j * seekBar.getMax() / 100);
                    } else {
                        seekBar.setSecondaryProgress(0);
                    }

                    i /= 1000;
                    int minute = i / 60;
                    int hour = minute / 60;
                    int second = i % 60;
                    minute %= 60;
                    playedTextView.setText(String.format("%02d:%02d:%02d", hour,
                            minute, second));

                    sendEmptyMessageDelayed(PROGRESS_CHANGED, 100);
                    break;
                case HIDE_CONTROLER:
                    hideController();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mGestureDetector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
            }
            result = super.onTouchEvent(event);
        }

        return result;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        getScreenSize();
        if (isControllerShow) {
            cancelDelayHide();
            hideController();
            showController();
            hideControllerDelay();
        }

        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        playedTime = vv.getCurrentPosition();
        vv.pause();
        playBtn.setImageResource(R.drawable.play);
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (!isChangedVideo) {
            vv.seekTo(playedTime);
            vv.start();
        } else {
            isChangedVideo = false;
        }

        if (vv.isPlaying()) {
            playBtn.setImageResource(R.drawable.pause);
            hideControllerDelay();
        }
        Log.d("REQUEST", "NEW AD !");

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (controler.isShowing()) {
            controler.dismiss();
        }
        if (mSoundWindow.isShowing()) {
            mSoundWindow.dismiss();
        }

        myHandler.removeMessages(PROGRESS_CHANGED);
        myHandler.removeMessages(HIDE_CONTROLER);

        if (vv.isPlaying()) {
            vv.stopPlayback();
        }

        playList.clear();
        super.onDestroy();
    }

    private void getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        screenHeight = display.getHeight();
        screenWidth = display.getWidth();
        controlHeight = screenHeight / 4;
    }

    private void hideController() {
        if (controler.isShowing()) {
            controler.update(0, 0, 0, 0);
            isControllerShow = false;
        }
        if (mSoundWindow.isShowing()) {
            mSoundWindow.dismiss();
            isSoundShow = false;
        }
    }

    private void hideControllerDelay() {
        myHandler.sendEmptyMessageDelayed(HIDE_CONTROLER, TIME);
    }

    private void showController() {
        controler.update(0, 0, screenWidth, controlHeight);
        isControllerShow = true;
    }

    private void cancelDelayHide() {
        myHandler.removeMessages(HIDE_CONTROLER);
    }

    private final static int SCREEN_FULL = 0;
    private final static int SCREEN_DEFAULT = 1;

    private void setVideoScale(int flag) {
        LayoutParams lp = vv.getLayoutParams();

        switch (flag) {
            case SCREEN_FULL:
                Log.d(TAG, "screenWidth: " + screenWidth + " screenHeight: "
                        + screenHeight);
                vv.setVideoScale(screenWidth, screenHeight);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;

            case SCREEN_DEFAULT:
                int videoWidth = vv.getVideoWidth();
                int videoHeight = vv.getVideoHeight();
                int mWidth = screenWidth;
                int mHeight = screenHeight - 25;

                if (videoWidth > 0 && videoHeight > 0) {
                    if (videoWidth * mHeight > mWidth * videoHeight) {
                        mHeight = mWidth * videoHeight / videoWidth;
                    } else if (videoWidth * mHeight < mWidth * videoHeight) {
                        mWidth = mHeight * videoWidth / videoHeight;
                    }
                }
                vv.setVideoScale(mWidth, mHeight);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
        }
    }

    private int findAlphaFromSound() {
        if (mAudioManager != null) {
            int alpha = currentVolume * (0xCC - 0x55) / maxVolume + 0x55;
            return alpha;
        } else {
            return 0xCC;
        }
    }

    private void updateVolume(int index) {
        if (mAudioManager != null) {
            if (isSilent) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            } else {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index,	0);
            }
            currentVolume = index;
            volumeBtn.setAlpha(findAlphaFromSound());
        }
    }

    /**
     * 读取视频文件。
     *
     * @param list
     * @param file
     */
    private void getVideoFile(final LinkedList<MovieInfo> list, File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")) {

                        MovieInfo mi = new MovieInfo();
                        mi.setDisplayName(file.getName());
                        mi.setPath(file.getAbsolutePath());
                        list.add(mi);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
    }

    /**
     * load the video file list in local media database.
     */
    private void initVideoFileList() {
        getVideoFile(playList, new File("/sdcard/"));
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            Cursor cursor = getContentResolver().query(videoListUri,
                    new String[] { "_display_name", "_data" }, null,
                    null, null);
            int n = cursor.getCount();
            cursor.moveToFirst();

            LinkedList<MovieInfo> playList2 = new LinkedList<MovieInfo>();
            for (int i = 0; i != n; ++i) {
                MovieInfo mInfo = new MovieInfo();
                mInfo.setDisplayName(cursor.getString(cursor
                        .getColumnIndex("_display_name")));
                mInfo.setPath(cursor.getString(cursor.getColumnIndex("_data")));
                playList2.add(mInfo);
                cursor.moveToNext();
            }

            if (playList2.size() > playList.size()) {
                playList = playList2;
            }
        }

        this.selectVideoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(VideoPlayerActivity.this,
                        VideoChooseActivity.class);
                VideoPlayerActivity.this.startActivityForResult(intent, 0);
                cancelDelayHide();
            }
        });
    }

    private void initVideoController() {
        controlView = getLayoutInflater().inflate(R.layout.controler, null);
        controler = new PopupWindow(controlView);

        position = -1;
        selectVideoBtn = (ImageButton) controlView
                .findViewById(R.id.selectVideoBtn);
        backBtn = (ImageButton) controlView.findViewById(R.id.backBtn);
        playBtn = (ImageButton) controlView.findViewById(R.id.playBtn);
        forwardBtn = (ImageButton) controlView.findViewById(R.id.forwardBtn);

        selectVideoBtn.setAlpha(0xBB);
        this.backBtn.setAlpha(0xBB);
        this.playBtn.setAlpha(0xBB);
        this.forwardBtn.setAlpha(0xBB);

        initVideoControllerEvent();
    }

    private void initVideoView() {
        vv = (VideoView) findViewById(R.id.vv);
        vv.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                vv.stopPlayback();
                isOnline = false;

                new AlertDialog.Builder(VideoPlayerActivity.this)
                        .setTitle("对不起")
                        .setMessage("您播放的视频格式不正确！")
                        .setPositiveButton("知道了",
                                new AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        vv.stopPlayback();
                                    }
                                }).setCancelable(false).show();
                return false;
            }
        });

        vv.setMySizeChangeLinstener(new MySizeChangeLinstener() {
            @Override
            public void doMyThings() {
                setVideoScale(SCREEN_DEFAULT);
            }
        });

        vv.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer arg0) {
                setVideoScale(SCREEN_DEFAULT);
                isFullScreen = false;
                if (isControllerShow) {
                    showController();
                }

                int i = vv.getDuration();
                Log.d("onCompletion", "" + i);
                seekBar.setMax(i);
                i /= 1000;
                int minute = i / 60;
                int hour = minute / 60;
                int second = i % 60;
                minute %= 60;
                durationTextView.setText(String.format("%02d:%02d:%02d", hour,
                        minute, second));

                vv.start();
                playBtn.setImageResource(R.drawable.pause);
                hideControllerDelay();
                myHandler.sendEmptyMessage(PROGRESS_CHANGED);
            }
        });

        vv.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                // TODO Auto-generated method stub
                int n = playList.size();
                isOnline = false;
                if (++position < n) {
                    vv.setVideoPath(playList.get(position).getPath());
                } else {
                    vv.stopPlayback();
                    VideoPlayerActivity.this.finish();
                }
            }
        });
    }

    private void initVideoControllerEvent() {
        this.forwardBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = playList.size();
                isOnline = false;
                if (++position < n) {
                    vv.setVideoPath(playList.get(position).getPath());
                    cancelDelayHide();
                    hideControllerDelay();
                } else {
                    VideoPlayerActivity.this.finish();
                }
            }
        });

        this.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDelayHide();
                if (isPaused) {
                    vv.start();
                    playBtn.setImageResource(R.drawable.pause);
                    hideControllerDelay();
                } else {
                    vv.pause();
                    playBtn.setImageResource(R.drawable.play);
                }
                isPaused = !isPaused;
            }
        });

        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnline = false;
                if (--position >= 0) {
                    vv.setVideoPath(playList.get(position).getPath());
                    cancelDelayHide();
                    hideControllerDelay();
                } else {
                    VideoPlayerActivity.this.finish();
                }
            }
        });


    }

    /**
     * 初始化声音播放的View.
     */
    private void initVolumeView() {
        mSoundView = new SoundView(this);
        mSoundView.setOnVolumeChangedListener(new OnVolumeChangedListener() {
            @Override
            public void setYourVolume(int index) {
                cancelDelayHide();
                updateVolume(index);
                hideControllerDelay();
            }
        });
        mSoundWindow = new PopupWindow(mSoundView);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeBtn = (ImageButton) controlView.findViewById(R.id.volumeBtn);
        this.volumeBtn.setAlpha(findAlphaFromSound());

        this.initVolumeBtnEvent();
    }

    private void initVolumeBtnEvent() {
        volumeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSoundShow) {
                    mSoundWindow.dismiss();
                } else {
                    if (mSoundWindow.isShowing()) {
                        mSoundWindow.update(15, 0, SoundView.MY_WIDTH,
                                SoundView.MY_HEIGHT);
                    } else {
                        mSoundWindow.showAtLocation(vv, Gravity.RIGHT
                                | Gravity.CENTER_VERTICAL, 15, 0);
                        mSoundWindow.update(15, 0, SoundView.MY_WIDTH,
                                SoundView.MY_HEIGHT);
                    }
                }
                isSoundShow = !isSoundShow;
                hideControllerDelay();
            }
        });

        volumeBtn.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                if (isSilent) {
                    volumeBtn.setImageResource(R.drawable.soundenable);
                } else {
                    volumeBtn.setImageResource(R.drawable.sounddisable);
                }
                isSilent = !isSilent;
                updateVolume(currentVolume);
                cancelDelayHide();
                hideControllerDelay();
                return true;
            }
        });
    }

    /**
     * 初始化从URL获取到的视频。
     *
     */
    private void initUrlVideo() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            vv.stopPlayback();
            vv.setVideoURI(uri);
            isOnline = true;

            this.playBtn.setImageResource(R.drawable.pause);
        } else {
            this.playBtn.setImageResource(R.drawable.play);
        }
    }

    private void initGestureDector() {
        mGestureDetector = new GestureDetector(new SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isFullScreen) {
                    setVideoScale(SCREEN_DEFAULT);
                } else {
                    setVideoScale(SCREEN_FULL);
                }
                isFullScreen = !isFullScreen;
                Log.d(TAG, "onDoubleTap");

                if (isControllerShow) {
                    showController();
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (!isControllerShow) {
                    showController();
                    hideControllerDelay();
                } else {
                    cancelDelayHide();
                    hideController();
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (isPaused) {
                    vv.start();
                    playBtn.setImageResource(R.drawable.pause);
                    cancelDelayHide();
                    hideControllerDelay();
                } else {
                    vv.pause();
                    playBtn.setImageResource(R.drawable.play);
                    cancelDelayHide();
                    showController();
                }
                isPaused = !isPaused;
            }
        });
    }

    private void initPlayingStatus() {
        seekBar = (SeekBar) controlView.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress,
                                          boolean fromUser) {
                if (fromUser) {
                    if (!isOnline) {
                        vv.seekTo(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                myHandler.removeMessages(HIDE_CONTROLER);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myHandler.sendEmptyMessageDelayed(HIDE_CONTROLER, TIME);
            }
        });

        durationTextView = (TextView) controlView.findViewById(R.id.duration);
        playedTextView = (TextView) controlView.findViewById(R.id.has_played);
    }

    /**
     * 初始化Controler的位置。
     */
    private void initIdleHandler() {
        Looper.myQueue().addIdleHandler(new IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (controler != null && vv.isShown()) {
                    controler.showAtLocation(vv, Gravity.BOTTOM, 0, 0);
                    controler.update(0, 0, screenWidth, controlHeight);
                }
                return false;
            }
        });
    }
}