package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class TimeRecordActivity extends Activity {

    private ImageView imageView1, imageView2, imageView3, imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record);
        imageView1 = (ImageView) this.findViewById(R.id.image1);
        imageView2 = (ImageView) this.findViewById(R.id.image2);
        imageView3 = (ImageView) this.findViewById(R.id.image3);
        imageView4 = (ImageView) this.findViewById(R.id.image4);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeRecordActivity.this, TimeRecordDetailActivity.class);
                intent.putExtra("flag", "1");
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeRecordActivity.this, TimeRecordDetailActivity.class);
                intent.putExtra("flag", "2");
                startActivity(intent);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeRecordActivity.this, TimeRecordDetailActivity.class);
                intent.putExtra("flag", "3");
                startActivity(intent);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeRecordActivity.this, TimeRecordDetailActivity.class);
                intent.putExtra("flag", "4");
                startActivity(intent);
            }
        });
    }

    public void image_back(View view) {
        Intent intent=new Intent(TimeRecordActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(TimeRecordActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
