package com.hq.yunyi2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ConversationActivity extends FragmentActivity{
    private TextView textView;
    private ImageView image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        textView=(TextView)this.findViewById(R.id.text_title);
        image_back=(ImageView)this.findViewById(R.id.image_back);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConversationActivity.this,ChatHomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });

        String sId=getIntent().getData().getQueryParameter("targetId");//Id
        String sName=getIntent().getData().getQueryParameter("title");//昵称
        if(!TextUtils.isEmpty(sName)){
            textView.setText(sName);
        }else{
            //拿到id 去请求自己的服务器
        }
    }
}
