package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.hq.yunyi2.adapter.FamilyCircleAdapter;
import com.hq.yunyi2.bean.FamilyCircleBean;
import com.hq.yunyi2.utils.ImageCacheUtil;
import com.hq.yunyi2.utils.StatusHeightUtils;

import java.util.ArrayList;
import java.util.List;


public class FamilyCircleActivity extends Activity implements View.OnClickListener {

    private ListView listView_family_circle;
    private PullRefreshLayout pullRefreshLayout;
    private FloatingActionButton btn_publish;
    private List<FamilyCircleBean> list;
    private FamilyCircleAdapter familyCircleAdapter;
    private PopupWindow popupWindow_publish;
    private EditText editText_publish_content;
    private ImageView imageView_close,popup_image_select,popup_image_content;
    private Button popup_btn_publish;
    private View view_popupwindow;
    private int screenHeight;
    private Handler handler_pullrefreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_circle);
        initData();
        initView();
        initListener();
    }

    private void initData(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenHeight = size.y;

        list = new ArrayList<>();
        list.add(new FamilyCircleBean(R.mipmap.user_image_woman, "小红", "11:30", "这是去姥姥家的必经之路。", ContextCompat.getDrawable(FamilyCircleActivity.this, R.mipmap.passage_image1)));
        list.add(new FamilyCircleBean(R.mipmap.user_image_man, "小白", "10:30", "这是小时候和大伯一起来过的水族馆。", ContextCompat.getDrawable(FamilyCircleActivity.this, R.mipmap.passage_image2)));
        list.add(new FamilyCircleBean(R.mipmap.user_image_man, "小亮", "9:00", "曾经姥爷家乡的亭子，小时候最喜欢去的地方了。", ContextCompat.getDrawable(FamilyCircleActivity.this, R.mipmap.passage_image3)));
        familyCircleAdapter = new FamilyCircleAdapter(FamilyCircleActivity.this, list);

        view_popupwindow =LayoutInflater.from(FamilyCircleActivity.this).inflate(R.layout.popupwindow_publish_family, null);
        popupWindow_publish=new PopupWindow(view_popupwindow, WindowManager.LayoutParams.MATCH_PARENT, screenHeight - StatusHeightUtils.getStatusBarHeight(FamilyCircleActivity.this));
        popupWindow_publish.setAnimationStyle(R.style.mystyle);
        popupWindow_publish.setFocusable(true);
        handler_pullrefreshlayout=new Handler();
    }

    private void initView(){
        listView_family_circle = (ListView) this.findViewById(R.id.familylistview);
        pullRefreshLayout=(PullRefreshLayout) this.findViewById(R.id.pullRefreshLayout);
        btn_publish = (FloatingActionButton) this.findViewById(R.id.floatingActionButton);
        listView_family_circle.setAdapter(familyCircleAdapter);

        editText_publish_content=(EditText)view_popupwindow.findViewById(R.id.edittext_content);
        imageView_close=(ImageView)view_popupwindow.findViewById(R.id.image_close);
        popup_btn_publish=(Button)view_popupwindow.findViewById(R.id.popup_btn_publish);
        popup_image_content=(ImageView)view_popupwindow.findViewById(R.id.image_content);
        popup_image_select=(ImageView)view_popupwindow.findViewById(R.id.image_select);
    }

    private void initListener(){
        btn_publish.setOnClickListener(this);
        imageView_close.setOnClickListener(this);
        popup_btn_publish.setOnClickListener(this);
        popup_image_select.setOnClickListener(this);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler_pullrefreshlayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        familyCircleAdapter.notifyDataSetChanged();
                        pullRefreshLayout.setRefreshing(false);
                        Toast.makeText(FamilyCircleActivity.this,"刷新完成啦",Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Bitmap bitmap = ImageCacheUtil.getResizedBitmap(null, null,
                    FamilyCircleActivity.this, data.getData(), 100, true);
            BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
            popup_image_content.setImageDrawable(drawable);
        }
    }

    public void image_back(View view){
        Intent intent=new Intent(FamilyCircleActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
               popupWindow_publish.showAtLocation(v, Gravity.CENTER,0,0);
                break;
            case R.id.image_close:
                popupWindow_publish.dismiss();
                break;
            case R.id.popup_btn_publish:
                popupWindow_publish.dismiss();
                if (popup_image_content.getDrawable() == null) {
                    list.add(0, new FamilyCircleBean(R.mipmap.user_image_man, "李四", "10:45", editText_publish_content.getText().toString(), null));
                } else {
                    list.add(0, new FamilyCircleBean(R.mipmap.user_image_man, "李四", "10:45", editText_publish_content.getText().toString(), popup_image_content.getDrawable()));
                }
                familyCircleAdapter.notifyDataSetChanged();
                break;
            case R.id.image_select:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                intent.putExtra("crop", "circle");
                // 使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 取得相片后返回本画面
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(FamilyCircleActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
