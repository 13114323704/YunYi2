package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hq.yunyi2.adapter.MessageLeaveAdapter;
import com.hq.yunyi2.bean.MessageLeaveBean;
import com.hq.yunyi2.customview.MyListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MemoryHallActivity extends Activity{

    private MyListView myListView;
    private List<MessageLeaveBean> list_message_leave;
    private MessageLeaveAdapter messageLeaveAdapter;
    private Calendar c = Calendar.getInstance();
    private Button btn_submit;
    private EditText editText_message_leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_hall);

        editText_message_leave=(EditText)this.findViewById(R.id.edittext_message_leave);
        btn_submit=(Button)this.findViewById(R.id.btn_submit);
        list_message_leave = new ArrayList<>();
        list_message_leave.add(new MessageLeaveBean(R.mipmap.user_image_woman, "小红", "9:30", "想念姥姥了!"));
        list_message_leave.add(new MessageLeaveBean(R.mipmap.user_image_woman, "小白", "12:30", "我也想念姥姥了!"));
        messageLeaveAdapter = new MessageLeaveAdapter(MemoryHallActivity.this, list_message_leave);

        myListView = (MyListView)this.findViewById(R.id.listview_message_leave);
        myListView.setAdapter(messageLeaveAdapter);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_message_leave.add(0, new MessageLeaveBean(R.mipmap.user_image_man, "小明", c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE), editText_message_leave.getText().toString()));
                messageLeaveAdapter.notifyDataSetChanged();
            }
        });
    }

    public void image_back(View view){
        Intent intent=new Intent(MemoryHallActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(MemoryHallActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
