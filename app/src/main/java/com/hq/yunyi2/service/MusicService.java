package com.hq.yunyi2.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hq.yunyi2.R;


public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int operate = intent.getIntExtra("operate", 3);

        switch (operate) {
            case -1:
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.music_bg1);
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();

                }


                break;
            case 0:
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.music_bg2);
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();

                }

                break;
            case 1:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case 2:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
