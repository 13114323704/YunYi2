package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hq.yunyi2.customview.TypesetTextView;
import com.hq.yunyi2.utils.MyTextUtils;

import java.io.ByteArrayInputStream;

public class PrivateExperienceDetailinfoActivity extends Activity {
    private TextView textView_title, textView_time;
    private TypesetTextView textView_content_p1,textView_content_p2,textView_content_p3;
    private String string_title, string_time, string_content;
    private ImageView imageView_top;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_experience_detailinfo);

        sharedPreferences = getSharedPreferences("image_data", MODE_PRIVATE);

        Intent intent = getIntent();
        string_title = intent.getStringExtra("title");
        string_time = intent.getStringExtra("time");
        string_content = intent.getStringExtra("content");
        textView_title = (TextView) this.findViewById(R.id.textview_title);
        textView_time = (TextView) this.findViewById(R.id.textview_time);
        textView_content_p1 = (TypesetTextView) this.findViewById(R.id.textview_content_p1);
        textView_content_p2 = (TypesetTextView) this.findViewById(R.id.textview_content_p2);
        textView_content_p3 = (TypesetTextView) this.findViewById(R.id.textview_content_p3);
        imageView_top = (ImageView) this.findViewById(R.id.imageview_top);
        textView_title.setText(string_title);
        textView_time.setText(string_time);

        if(string_title.equals("If I Knew")){
            textView_content_p1.setText("\u3000\u3000"+getResources().getString(R.string.passage_content1_p1));
            textView_content_p2.setText("\u3000\u3000"+getResources().getString(R.string.passage_content1_p2));
            textView_content_p3.setText("\u3000\u3000"+getResources().getString(R.string.passage_content1_p3));
        }else if(string_title.equals("A Father And A Son")){
            textView_content_p1.setText("\u3000\u3000"+getResources().getString(R.string.passage_content2_p1));
            textView_content_p2.setText("\u3000\u3000"+getResources().getString(R.string.passage_content2_p2));
            textView_content_p3.setText("\u3000\u3000"+getResources().getString(R.string.passage_content2_p3));
        }else if(string_title.equals("Never For Love")){
            textView_content_p1.setText("\u3000\u3000"+getResources().getString(R.string.passage_content3_p1));
            textView_content_p2.setText("\u3000\u3000"+getResources().getString(R.string.passage_content3_p2));
            textView_content_p3.setText("\u3000\u3000"+getResources().getString(R.string.passage_content3_p3));
        }else{
            textView_content_p1.setText("\u3000\u3000"+string_content);
        }

        imageView_top.setImageDrawable(loadDrawable());
    }

    public void image_back(View view) {
        finish();
    }

    private Drawable loadDrawable() {
        String temp = sharedPreferences.getString("list_image", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(
                temp.getBytes(), Base64.DEFAULT));
        return Drawable.createFromStream(bais, "");
    }
}
