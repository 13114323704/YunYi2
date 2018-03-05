package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.hq.yunyi2.adapter.PrivateExperienceAdapter;
import com.hq.yunyi2.bean.PrivateExperienceBean;
import com.hq.yunyi2.customview.MyListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class PrivateExperienceActivity extends Activity {

    private MyListView myListView;
    private List<PrivateExperienceBean> list=new ArrayList<>();
    private PrivateExperienceAdapter privateExperienceAdapter;
    private Button btn_add;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_experience);

        initData();
        initView();
        initListener();
    }

    private void initData(){
        sharedPreferences=getSharedPreferences("image_data",MODE_PRIVATE);

        list.add(new PrivateExperienceBean("If I Knew","5月11日",getResources().getString(R.string.passage_content1_p1), ContextCompat.getDrawable(PrivateExperienceActivity.this, R.mipmap.passage_image1)));
        list.add(new PrivateExperienceBean("A Father And A Son","6月7日",getResources().getString(R.string.passage_content2_p1), ContextCompat.getDrawable(PrivateExperienceActivity.this, R.mipmap.passage_image2)));
        list.add(new PrivateExperienceBean("Never For Love","7月11日",getResources().getString(R.string.passage_content3_p1), ContextCompat.getDrawable(PrivateExperienceActivity.this, R.mipmap.passage_image3)));
        privateExperienceAdapter=new PrivateExperienceAdapter(PrivateExperienceActivity.this,list);
    }

    private void initView(){
        btn_add=(Button)this.findViewById(R.id.btn_add);
        myListView = (MyListView) this.findViewById(R.id.listview);
        myListView.setAdapter(privateExperienceAdapter);
    }

    private void initListener(){
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PrivateExperienceActivity.this,CreatePrivateExperienceActivity.class),1);
            }
        });
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PrivateExperienceActivity.this,PrivateExperienceDetailinfoActivity.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("time",list.get(position).getTime());
                intent.putExtra("content",list.get(position).getContent());
                saveDrawable(list.get(position).getImage());
                startActivity(intent);
            }
        });
    }

    public void image_back(View view){
        Intent intent=new Intent(PrivateExperienceActivity.this,PrivateCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            String string_title=data.getStringExtra("title");
            String string_content=data.getStringExtra("content");
            String string_time=data.getStringExtra("time");
            PrivateExperienceBean privateExperienceBean=new PrivateExperienceBean(string_title,string_time,string_content,loadDrawable());
            list.add(0,privateExperienceBean);
            privateExperienceAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 使用sharedPreferences保存listview背景图片
    private void saveDrawable(Drawable drawable) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String imageBase64 = new String(Base64.encodeToString(
                baos.toByteArray(), Base64.DEFAULT));
        editor.putString("list_image", imageBase64);
        editor.commit();
    }

    // 加载用sharedPreferences保存的图片
    private Drawable loadDrawable() {
        String temp = sharedPreferences.getString("private_experience_image", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(
                temp.getBytes(), Base64.DEFAULT));
        return Drawable.createFromStream(bais, "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(PrivateExperienceActivity.this,PrivateCenterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
