package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class TimeRecordDetailActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private ImageView imageView1,imageView2,imageView3,imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record_detail);
        imageView1=(ImageView)this.findViewById(R.id.image1);
        imageView2=(ImageView)this.findViewById(R.id.image2);
        imageView3=(ImageView)this.findViewById(R.id.image3);
        imageView4=(ImageView)this.findViewById(R.id.image4);

        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");

        if (flag.equals("1") || flag.equals("4")) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.music_bg1);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            } else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            imageView1.setImageResource(R.mipmap.life);
            imageView2.setImageResource(R.mipmap.life2);
            imageView3.setImageResource(R.mipmap.life3);
            imageView4.setImageResource(R.mipmap.life8);
        } else {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.music_bg2);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            } else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();

            }
            imageView1.setImageResource(R.mipmap.life4);
            imageView2.setImageResource(R.mipmap.life5);
            imageView3.setImageResource(R.mipmap.life6);
            imageView4.setImageResource(R.mipmap.life7);
        }
        mediaPlayer.setLooping(true);
    }

    public void image_back(View view){
        mediaPlayer.stop();
        mediaPlayer=null;
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            mediaPlayer.stop();
            mediaPlayer=null;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
