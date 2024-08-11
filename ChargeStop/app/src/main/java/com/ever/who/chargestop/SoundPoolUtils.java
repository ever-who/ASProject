package com.ever.who.chargestop;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class SoundPoolUtils implements SoundPool.OnLoadCompleteListener {

    private static final int MAX_STREAMS = 2;
    private static final int DEFAULT_QUALITY = 0;
    private static final int DEFAULT_PRIORITY = 1;
    private static final int LEFT_VOLUME = 1;
    private static final int RIGHT_VOLUME = 1;
    private static final int LOOP = 0;
    private static final float RATE = 1.0f;

    private static final String TAG = "SoundPoolUtils";

    private static SoundPoolUtils sSoundPoolUtils;

    /**
     * 音频的相关类
     */
    private SoundPool mSoundPool;
    private Context mContext;
    private Vibrator mVibrator;
    private int loadId;


    private SoundPoolUtils(Context context) {
        mContext = context;
        //初始化行营的音频类
        intSoundPool();
        initVibrator();
    }

    /**
     * @author Angle
     * 创建时间: 2018/11/4 13:02
     * 方法描述: 初始化短音频的内容
     */
    private void intSoundPool() {
        //根据不同的版本进行相应的创建
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setLegacyStreamType(AudioManager.STREAM_ALARM)
//                    .build();
//            mSoundPool = new SoundPool.Builder()
//                    .setMaxStreams(MAX_STREAMS)
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        } else {
            mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_ALARM, DEFAULT_QUALITY);
        //}
        mSoundPool.setOnLoadCompleteListener(this);
    }

    /**
     * @author Angle
     * 创建时间: 2018/11/4 13:03
     * 方法描述: 初始化震动的对象
     */
    private void initVibrator() {
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static SoundPoolUtils getInstance(Context context) {
        if (sSoundPoolUtils == null) {
            synchronized (SoundPoolUtils.class) {
                if (sSoundPoolUtils == null) {
                    sSoundPoolUtils = new SoundPoolUtils(context);
                }
            }
        }
        return sSoundPoolUtils;
    }

    /**
     * @param resId 音频的资源ID
     * @author Angle
     * 创建时间: 2018/11/4 13:03
     * 方法描述: 开始播放音频
     */
    public void playVideo(int resId) {
        if (mSoundPool == null) {
            intSoundPool();
        }
        loadId = mSoundPool.load(mContext, resId, DEFAULT_PRIORITY);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Log.d(TAG, "onLoadComplete() called with:sampleId = [" + sampleId
                + "], status = [" + status + "]");
        int streamID = soundPool.play(loadId, LEFT_VOLUME, RIGHT_VOLUME, DEFAULT_PRIORITY, LOOP, RATE);
        Log.d(TAG, "playVideo: streamID = " + streamID);
    }

    /**
     * @param milliseconds 震动时间
     * @author Angle
     * 创建时间: 2018/11/4 13:04
     * 方法描述: 开启相应的震动
     */
    public void startVibrator(long milliseconds) {
        if (mVibrator == null) {
            initVibrator();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(milliseconds, 100);
            mVibrator.vibrate(vibrationEffect);
        } else {
            mVibrator.vibrate(1000);
        }
    }

    /**
     * @param resId        资源id
     * @param milliseconds 震动时间
     * @author Angle
     * 创建时间: 2018/11/4 13:06
     * 方法描述: 同时开始音乐和震动
     */
    public void startVideoAndVibrator(int resId, long milliseconds) {
        playVideo(resId);
        startVibrator(milliseconds);
    }

    /**
     * @author Angle
     * 创建时间: 2018/11/4 13:05
     * 方法描述:  释放相应的资源
     */
    public void release() {
        //释放所有的资源
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }

        if (mVibrator != null) {
            mVibrator.cancel();
            mVibrator = null;
        }
    }

}
