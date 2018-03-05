package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class PrivateInfoDetailActivity extends Activity {

    private TextView textView_name, textView_motto,textView_birth_date,textView_died_date, textView_relation,textView_education,textView_job, textView_content;
    private String string_name, string_motto,string_birth_date,string_died_date, string_relation,string_education,string_job, string_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_info_detail);

        //初始化数据
        Intent intent = getIntent();
        string_name = intent.getStringExtra("name");
        string_motto = intent.getStringExtra("motto");
        string_birth_date=intent.getStringExtra("birth_date");
        string_died_date=intent.getStringExtra("died_date");
        string_relation = intent.getStringExtra("relation");
        string_education=intent.getStringExtra("education");
        string_job=intent.getStringExtra("job");
        string_content = intent.getStringExtra("content");

        //初始化控件
        textView_name = (TextView) this.findViewById(R.id.textview_name);
        textView_motto = (TextView) this.findViewById(R.id.textview_motto);
        textView_birth_date=(TextView)this.findViewById(R.id.textview_birth_date);
        textView_died_date=(TextView)this.findViewById(R.id.textview_died_date);
        textView_relation = (TextView) this.findViewById(R.id.textview_relation);
        textView_education=(TextView)this.findViewById(R.id.textview_education);
        textView_job=(TextView)this.findViewById(R.id.textview_job);
        textView_content = (TextView) this.findViewById(R.id.textview_content);


        textView_name.setText(string_name);
        textView_motto.setText(string_motto);
        textView_birth_date.setText(string_birth_date);
        textView_died_date.setText(string_died_date);
        textView_relation.setText(string_relation);
        textView_education.setText(string_education);
        textView_job.setText(string_job);
        textView_content.setText(string_content);
    }

    public void image_back(View view) {
        finish();
    }

    public void image_qrcode(View view) {
        Intent intent = new Intent(PrivateInfoDetailActivity.this, QRcodeActivity.class);
        intent.putExtra("name", textView_name.getText().toString());
        intent.putExtra("motto", textView_motto.getText().toString());
        startActivity(intent);
    }

    public void create_memory_hall(View view) {
        Toast.makeText(PrivateInfoDetailActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
    }

}
