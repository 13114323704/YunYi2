package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hq.yunyi2.utils.PrivateInfoHttpUtils;

import java.util.HashMap;
import java.util.Map;

public class CreatePrivateInfoActivity extends Activity {

    private Spinner spinner_relation;
    private EditText editText_name,editText_motto,editText_detailinfo,editText_birth_date,editText_died_date,editText_education,editText_job;
    private String string_name,string_motto,string_relation,string_detailinfo;
    private String string_birth_date,string_died_date,string_education,string_job;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_result=(String)msg.obj;
            if(string_result.equals("success")){
                Toast.makeText(CreatePrivateInfoActivity.this,"添加词条成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatePrivateInfoActivity.this, PrivateInfoActivity.class);
                setResult(1,intent);
                finish();
            }else{
                Toast.makeText(CreatePrivateInfoActivity.this,"添加词条失败,可能网络开小差啦",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_info);
        spinner_relation = (Spinner) this.findViewById(R.id.relation_spinner);
        editText_name=(EditText)this.findViewById(R.id.editext_name);
        editText_motto=(EditText)this.findViewById(R.id.edittext_motto);
        editText_birth_date=(EditText)this.findViewById(R.id.edittext_birth_date);
        editText_died_date=(EditText)this.findViewById(R.id.edittext_died_date);
        editText_education=(EditText)this.findViewById(R.id.edittext_education);
        editText_job=(EditText)this.findViewById(R.id.edittext_job);
        editText_detailinfo=(EditText)this.findViewById(R.id.edittext_detailinfo);
    }

    public void image_back(View view) {
        Intent intent = new Intent(CreatePrivateInfoActivity.this, PrivateInfoActivity.class);
        setResult(2,intent);
        finish();
    }

    public void create(View view) {
        string_name=editText_name.getText().toString();
        string_motto=editText_motto.getText().toString();
        string_birth_date=editText_birth_date.getText().toString();
        string_died_date=editText_died_date.getText().toString();
        string_relation=spinner_relation.getSelectedItem().toString();
        string_education=editText_education.getText().toString();
        string_job=editText_job.getText().toString();
        string_detailinfo=editText_detailinfo.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> params=new HashMap<String, String>();
                params.put("flag","2");
                params.put("name", string_name);
                params.put("motto", string_motto);
                params.put("birth_date",string_birth_date);
                params.put("died_date",string_died_date);
                params.put("relation", string_relation);
                params.put("education",string_education);
                params.put("job",string_job);
                params.put("experience_content", string_detailinfo);
                String result = PrivateInfoHttpUtils.sendPostMessage(params, "utf-8");
                Message message = Message.obtain();
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }
}
